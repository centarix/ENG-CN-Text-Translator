package net.centarix.translationpackage;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;

import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TranslationHandler {
        public String translatedText = "";
	public String AdmAccessToken;
	public String from = LanguageCodes.English;
	public String to = LanguageCodes.Chinese;
	public static String CLIENT_ID;
	public static String CLIENT_SECRET;
        public TranslationDBService translationService = new TranslationDBService();

	public TranslationHandler(String textToTranslate)
	{
            LoadPropertyValues();
            TranslationItem translation = null;
            DBLookup(textToTranslate);
            if(translation == null)
            {
                WSCall(textToTranslate);
            } else {
                translatedText = translation.getChineseText();
            }
	}

	public void WSCall(String textToTranslate)
	{
		URL url;
		HttpURLConnection connection = null;

		try {
			String strTranslatorAccessURI = "https://datamarket.accesscontrol.windows.net/v2/OAuth2-13";
			String strRequestDetails = String.format("grant_type=client_credentials&client_id=%1$2s&client_secret=%2$2s&scope="
					+ "http://api.microsofttranslator.com", URLEncoder.encode(CLIENT_ID, "UTF-8"), URLEncoder.encode(CLIENT_SECRET, "UTF-8"));

			//Create connection
			url = new URL(strTranslatorAccessURI);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type",  "application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length",  "" + strRequestDetails.getBytes().length);

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			//Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.write(strRequestDetails.getBytes(), 0 , strRequestDetails.getBytes().length);
			wr.flush();
			wr.close();

			//Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;


			AdmAccessToken admToken = new AdmAccessToken();
			StringBuffer response = new StringBuffer();
			while((line = rd.readLine()) != null) {
				//Deserialize JSON response
				Map<String, String> map = new Gson().fromJson(line, new TypeToken<Map<String, String>>() {}.getType());
				String key = map.get("access_token");
				//System.out.println("access_token: " + URLDecoder.decode(key, "UTF-8")); //this line for debug purposes
				admToken.access_token = key;
				response.append(line);
				response.append('\r');
			}
			rd.close();


			String headerValue = "Bearer " + admToken.access_token;
			translatedText = getTranslation(headerValue, textToTranslate);
			System.out.println(textToTranslate + " translates to " + translatedText);

		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
                        saveTranslation(textToTranslate, translatedText);
			if(connection != null) {
				connection.disconnect();
			}
		}
	}
        
        public Optional<TranslationItem> DBLookup(String textToTranslate)
        {
            return translationService.findTranslationByEnglishText(textToTranslate);
        }
        
        public void saveTranslation(String textToTranslate, String translation)
        {
            translationService.createTranslationItem(textToTranslate, translation);
        }

	public String getTranslation(String headerValue, String textToTranslate)
	{
		URL url;
		HttpURLConnection connection;
		try {
			//Bing Translator URL
			String uri = "http://api.microsofttranslator.com/v2/Http.svc/Translate?text=" + 
					URLEncoder.encode(textToTranslate, "UTF-8") + String.format("&from=%s&to=%s", from, to);
			
			//Create connection
			url = new URL(uri);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Authorization", headerValue);
			connection.setRequestProperty("Content-Encoding", "utf-8");

			connection.setDoOutput(true);
			connection.setDoInput(true);

			//Get Response
			InputStream is = connection.getInputStream();

			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			
			String line;
			InputStream xmlIS = null;
			
			while((line = rd.readLine()) != null) {
				xmlIS = new ByteArrayInputStream(line.getBytes(StandardCharsets.UTF_8));
				//xmlParser = new XMLParser(xmlIS);
			}
			XMLParser xmlParser = new XMLParser(xmlIS);
			return xmlParser.result;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
        
        private void LoadPropertyValues()
        {
            GetBingPropertyValues propValues = new GetBingPropertyValues();
            try 
            {
                propValues.getPropValues();
            } catch (IOException ex) {
                Logger.getLogger(TranslationHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            CLIENT_ID = propValues.getClientId();
            CLIENT_SECRET = propValues.getClientSecret();
        }
}
