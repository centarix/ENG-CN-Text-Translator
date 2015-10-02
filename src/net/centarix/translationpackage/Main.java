package net.centarix.translationpackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter text to translate: ");
		String textToTranslate = br.readLine();
		Translate(textToTranslate);
	}
	
	public static void Translate(String textToTranslate)
	{
		TranslationHandler translation = new TranslationHandler(textToTranslate);
	}

}
