/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.centarix.translationpackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

/**
 *
 * @author kcor
 */
public class GetBingPropertyValues {
    private String _clientId;
    private String _clientSecret;
    
    String result = "";
	InputStream inputStream;
 
	public String getPropValues() throws IOException {
 
		try {
			Properties prop = new Properties();
                        File currentDirectory = new File(new File(".").getAbsolutePath());
                        String propFileName = currentDirectory.getCanonicalPath() + "\\config.properties";
                        
			inputStream = new FileInputStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
  
			// get the property value and print it out
			setClientId(prop.getProperty("clientId"));
			setClientSecret(prop.getProperty("clientSecret"));
 
			result = "Bing Keys = " + getClientId() + ", " + getClientSecret();
			//System.out.println(result); //Debug purposes
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return result;
	}
        
        public void setClientId(String clientId)
        {
            _clientId = clientId;
        }
        
        public String getClientId()
        {
            return _clientId;
        }
        
        public void setClientSecret(String clientSecret)
        {
            _clientSecret = clientSecret;
        }
        
        public String getClientSecret()
        {
            return _clientSecret;
        }

}
