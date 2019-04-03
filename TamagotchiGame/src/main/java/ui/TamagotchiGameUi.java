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
    
    FlowPane frameHappy;
    FlowPane frameSad;
    FlowPane frameEat;
    FlowPane framePlay;
    FlowPane frameDead;
    FlowPane frameMedicate;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        TamagotchiDao tamagotchiDao = new TamagotchiDao();
        tamagotchiservice = new TamagotchiService((tamagotchiDao));
        
        Button createTamagotchi = new Button("Create");
        Button getTamagotchi = new Button("Load");
        Button deleteTamagotchi = new Button("Delete");

        //alkunäkymä
        Label nameText = new Label("New tamagotchi: ");
        TextField nameField = new TextField();
        Label oldone = new Label("Load your old tamagotchi: ");
        TextField field = new TextField();
        Label delete = new Label("Delete tamagotchi: ");
        TextField del = new TextField();
        Label tamas = new Label("Tamagotchis:");
        Label names = new Label(tamagotchiservice.tamaslist().toString());
        
        GridPane group = new GridPane();
        group.add(nameText, 0, 0);
        group.add(nameField, 1, 0);
        group.add(createTamagotchi, 2, 0);
        group.add(oldone, 0, 1);
        group.add(field, 1, 1);
        group.add(getTamagotchi, 2, 1);
        group.add(delete, 0, 2);
        group.add(del, 1, 2);
        group.add(deleteTamagotchi, 2, 2);
        group.add(tamas, 1, 4);
        group.add(names, 1, 5);
        
        group.setHgap(10);
        group.setVgap(25);
        group.setPadding(new Insets(10, 10, 10, 10));

        //pelinäkymää
        BorderPane state = new BorderPane();
        BorderPane deadState = new BorderPane();
        
        Image happy = new Image("file:tamag(3).gif");
        ImageView happypicture = new ImageView(happy);
        
        Image sad = new Image("file:tamag(12).gif");
        ImageView sadpicture = new ImageView(sad);
        
        Image eat = new Image("file:tamag(6).gif");
        ImageView eatpicture = new ImageView(eat);
        
        Image play = new Image("file:tamag(8).gif");
        ImageView playpicture = new ImageView(play);
        
        Image dead = new Image("file:tamag(13).gif");
        ImageView deadpicture = new ImageView(dead);
        
        Image sick = new Image("file:tamag(14).gif");
        ImageView sickpicture = new ImageView(sick);
        
        Image sleep = new Image("file:tamag(15).gif");
        ImageView sleeppicture = new ImageView(sleep);
        
        Image medicate = new Image("file:tamag(16).gif");
        ImageView medicatepicture = new ImageView(medicate);

        //napit
        HBox buttons = new HBox();
        buttons.setSpacing(100);
        
        HBox buttons2 = new HBox();
        buttons2.setSpacing(100);
        
        Button feedbutton = new Button("Feed");
        Button playbutton = new Button("Play");
        Button cleanbutton = new Button("Clean");
        Button returnbutton = new Button("Exit");
        
        buttons.getChildren().add(feedbutton);
        buttons.getChildren().add(playbutton);
        buttons.getChildren().add(cleanbutton);
        buttons.getChildren().add(returnbutton);
        
        Button medicatebutton = new Button("Medicate");
        Button sleepbutton = new Button("Sleep");
        Button wakeup = new Button("Wake up");
        
        buttons2.getChildren().add(medicatebutton);
        buttons2.getChildren().add(sleepbutton);
        buttons2.getChildren().add(wakeup);
        
        frameHappy = new FlowPane();
        frameSad = new FlowPane();
        frameEat = new FlowPane();
        framePlay = new FlowPane();
        frameMedicate = new FlowPane();
        frameDead = new FlowPane();
        
        frameHappy.getChildren().add(happypicture);
        frameSad.getChildren().add(sadpicture);
        frameEat.getChildren().add(eatpicture);
        framePlay.getChildren().add(playpicture);
        frameMedicate.getChildren().add(medicatepicture);
        frameDead.getChildren().add(deadpicture);
        
        state.setTop(buttons);
        state.setCenter(frameHappy);
        state.setBottom(buttons2);

        //deadState.setTop(); paluunappi? 
        deadState.setCenter(frameDead);
        
        Scene eka = new Scene(group);
        Scene toka = new Scene(state);
        Scene deadScene = new Scene(deadState);
        
        createTamagotchi.setOnAction((event) -> {
            //luo lisäksi tama

            try {
                
                name = nameField.getText();
                tamagotchiservice.newTamagotchi(name);
                
                if (tamagotchiservice.TamagotchiAlive(name)) {
                    update(state, stage, deadScene);
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
                
                name = field.getText();
                System.out.println("nimi haettu: " + name);
                
                tamagotchiservice.getTamagotchi(name);
                System.out.println("elossa? " + tamagotchiservice.TamagotchiAlive(name));
                if (tamagotchiservice.TamagotchiAlive(name)) {
                    update(state, stage, deadScene);
                    stage.setScene(toka);
                } else {
                    System.out.println("KUOLLU");
                    stage.setScene(deadScene);
                }
                
            } catch (Exception ex) {
                Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            
        });
        
        deleteTamagotchi.setOnAction((event) -> {
            //luo lisäksi tama

            try {                
                name = del.getText();
                tamagotchiservice.delete(name);
                
            } catch (Exception ex) {
                Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);          
            }     
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
        
        playbutton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
                try {
                    
                    state.setCenter(framePlay);
                    //      tamagotchiservice.updateTamagotchiHunger(name);
                    System.out.println("tila:  " + tamagotchiservice.getMood(name));
                    
                } catch (Exception ex) {
                    Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        
           medicatebutton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
                try {
                    
                    state.setCenter(frameMedicate);
                    //      tamagotchiservice.updateTamagotchiHunger(name);
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
    
    public void update(BorderPane state, Stage stage, Scene deadScene) {
        
        AnimationTimer animationtimer = new AnimationTimer() {
            long before = 0;
            
            @Override
            public void handle(long l) {
                //10 000 ms eli 10sec 
                if (l - before < 1_000_000_000_0L) {
                    return;
                }
                try {
                    
                    if (tamagotchiservice.TamagotchiAlive(name) == false) {
                        stage.setScene(deadScene);
                        System.out.println("pitäisi loppua, huonosti kävi");
                        
                    }
                    
                    if (tamagotchiservice.getMood(name).equals("sad")) {
                        state.setCenter(frameSad);
                        
                    } else if (tamagotchiservice.getMood(name).equals("happy")) {
                        state.setCenter(frameHappy);
                    }

                    //AIKA KULUU?
                    tamagotchiservice.time(name);
                    System.out.println("hae aika: " + System.currentTimeMillis());
                    
                } catch (Exception ex) {
                    Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                before = l;
            }
            
        };
        animationtimer.start();
    }
    
}
