package ui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import dao.TamagotchiDao;
import domain.Tamagotchi;
import domain.TamagotchiService;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author milla
 */
public class TamagotchiGameUi extends Application {

    //private Tamagotchi tamagotchi;
    //private TamagotchiDao tamagotchiDao;
    private TamagotchiService tamagotchiservice;
    private String name;


  

    @Override
    public void start(Stage stage) throws Exception{
        
        TamagotchiDao tamagotchiDao = new TamagotchiDao();
        tamagotchiservice= new TamagotchiService((tamagotchiDao));

        Button createTamagotchi = new Button("Create");
        Button getTamagotchi = new Button("Load");

        //alkunäkymä
        Label nameText = new Label("Name of your tamagotchi: ");
        TextField nameField = new TextField();
        Label oldone = new Label("Load your old tamagotchi: ");
        TextField field = new TextField();

        GridPane group = new GridPane();
        group.add(nameText, 0, 0);
        group.add(nameField, 1, 0);
        group.add(createTamagotchi, 1, 1);
        group.add(oldone, 0, 2);
        group.add(field, 1, 2);
        group.add(getTamagotchi, 1, 3);

        group.setHgap(10);
        group.setVgap(10);
        group.setPadding(new Insets(10, 10, 10, 10));

        //pelinäkymää
        BorderPane state = new BorderPane();

        Image happy = new Image("file:tamag(3).gif");
        ImageView happypicture = new ImageView(happy);

        Image sad = new Image("file:tamag(12).gif");
        ImageView sadpicture = new ImageView(sad);
        
        Image eat= new Image("file:tamag(6).gif");
        ImageView eatpicture=new ImageView(eat);

        //napit
        HBox buttons = new HBox();
        buttons.setSpacing(50);

        Button feedbutton = new Button("Feed");
        Button playbutton = new Button("Play");
        Button cleanbutton = new Button("Clean");
        Button returnbutton = new Button("Exit");

        buttons.getChildren().add(feedbutton);
        buttons.getChildren().add(playbutton);
        buttons.getChildren().add(cleanbutton);
        buttons.getChildren().add(returnbutton);

        FlowPane frameHappy = new FlowPane();
        FlowPane fmareSad = new FlowPane();
        FlowPane frameEat = new FlowPane();
        FlowPane framePlay=new FlowPane();
        

        frameHappy.getChildren().add(happypicture);
        fmareSad.getChildren().add(sadpicture);
        frameEat.getChildren().add(eatpicture);
        

        state.setTop(buttons);
        state.setCenter(frameHappy);   
        

        Scene eka = new Scene(group);
        Scene toka = new Scene(state);

        createTamagotchi.setOnAction((event) -> {
            //luo lisäksi tama

            try {

                name=nameField.getText();
                tamagotchiservice.newTamagotchi(name);
               

                if (tamagotchiservice.TamagotchiAlive(name)) { 
                    update(state, fmareSad, frameHappy, frameEat);
                }

            } catch (Exception ex) {
                Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);

            }
            stage.setScene(toka);
        });
        
        
        
        //KESKEN kun haetaan vanha päivitä tiekannasta eka
        getTamagotchi.setOnAction((event) -> {
            //luo lisäksi tama

            try {

                name=field.getText();
                System.out.println("nimi haettu: "+name);

                tamagotchiservice.getTamagotchi(name);
               

                if (tamagotchiservice.TamagotchiAlive(name)) { 
                    update(state, fmareSad, frameHappy,frameEat);
                }

            } catch (Exception ex) {
                Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);

            }
            stage.setScene(toka);
        });
        
        
        
        
        
        
        
        
        

   
        returnbutton.setOnAction((event) -> {
            stage.setScene(eka);
        });
        
        

        stage.setScene(eka);
        stage.show();

        
        feedbutton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                try {
                    
                    state.setCenter(frameEat);
                    tamagotchiservice.updateTamagotchiHunger(name);
                 //TESTITULOSTUS
                    System.out.println("tila:  " + tamagotchiservice.getMood(name));

                } catch (Exception ex) {
                    Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

         

    }

    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        launch(args);
    }

    
    
    
    public void update(BorderPane state, FlowPane ruutusad, FlowPane ruutuhappy, FlowPane ruutueat) {

        AnimationTimer animationtimer = new AnimationTimer() {

            long before = 0;

            @Override
            public void handle(long l) {

                if (l - before < 1_000_000_000_0L) {
                    return;
                }
                try {
                   
                    
                    if (tamagotchiservice.getMood(name).equals("sad")) {
                        state.setCenter(ruutusad);

                    } else if (tamagotchiservice.getMood(name).equals("happy")) {
                        state.setCenter(ruutuhappy);
                    } 
                } catch (Exception ex) {
                    Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
                }

                before = l;
            }

        };
        animationtimer.start();
    }

}
