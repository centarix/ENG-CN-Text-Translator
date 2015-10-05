/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.centarix.simpletranslatorgui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.centarix.translationpackage.*;

/**
 *
 * @author klove
 */
public class SimpleTranslatorGUI extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        final TextField resultTextField = new TextField("");
        Button btn = new Button();
        btn.setText("Translate");
        

        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        //grid.getChildren().add(btn);
        grid.add(btn, 0, 3);
        
        grid.add(resultTextField, 1, 3);
        
        Text sceneTitle = new Text("欢迎！输入的文本");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        grid.add(sceneTitle, 0, 0, 2, 1);  
        
        Text sceneDescription = new Text(getWelcomePageDescriptionText());
        sceneDescription.setFont(Font.font("Times New Roman",FontWeight.NORMAL , 15));
        
        grid.add(sceneDescription, 0, 1, 3, 1);
        
        
        Label userName = new Label("Enter text to translate:");
        grid.add(userName, 0, 2);
        
        final TextField textToTranslateField = new TextField();
        grid.add(textToTranslateField, 1, 2);
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                String textToTranslate = textToTranslateField.getText();
                TranslationHandler translation = new TranslationHandler(textToTranslate);
                resultTextField.setText(translation.translatedText);
                
            }
        });
        
        textToTranslateField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                String textToTranslate = textToTranslateField.getText();
                TranslationHandler translation = new TranslationHandler(textToTranslate);
                resultTextField.setText(translation.translatedText);
                }
            }
            
        });
        
        Scene scene = new Scene(grid, 500, 500);
        
        
        primaryStage.setTitle("Simple English-Chinese Translator");
        //primaryStage.initModality(Modality.WINDOW_MODAL);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(SimpleTranslatorGUI.class.getResource("main.css").toExternalForm());
        primaryStage.show();
    }
    
    private static String getWelcomePageDescriptionText()
    {
        return "This program is designed to translate English words to Simplified Chinese.";
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
