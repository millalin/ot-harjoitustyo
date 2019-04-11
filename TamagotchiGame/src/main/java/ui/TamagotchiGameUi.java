package ui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import dao.TamagotchiDao;
import domain.TamagotchiService;
import java.io.FileInputStream;
import java.util.Properties;

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
import javafx.scene.layout.BorderPane;
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
    private TamagotchiFrames frames;

    Button createTamagotchi;
    Button getTamagotchi;
    Button deleteTamagotchi;

    Button feedbutton;
    Button playbutton;
    Button cleanbutton;
    Button returnbutton;
    Button medicatebutton;
    Button sleepbutton;
    Button wakeup;

    TextField nameField;
    TextField field;
    TextField del;


    
    @Override
    public void start(Stage stage) throws Exception {

        // start = new TamagotchiStart();
        //tamagotchiservice = start.getTamagotchiservice();
        TamagotchiDao tamagotchiDao = new TamagotchiDao();
        tamagotchiservice = new TamagotchiService((tamagotchiDao));
      
        frames = new TamagotchiFrames();
       // frames.frames();

        GridPane startGroup = startGridPane();

        BorderPane state = new BorderPane();
        BorderPane deadState = new BorderPane();

        HBox buttons = buttons();
        HBox buttons2 = buttons2();

        state.setTop(buttons);
        state.setPadding(new Insets(30, 20, 20, 30));
        state.setCenter(frames.getFrameHappy());
        state.setBottom(buttons2);

        //deadState.setTop(); paluunappi? 
        deadState.setCenter(frames.getFrameDead());

        Scene startScene = new Scene(startGroup);
        Scene playScene = new Scene(state);
        Scene deadScene = new Scene(deadState);

        createTamagotchi.setOnAction((event) -> {
            try {
                
                name = nameField.getText();
                tamagotchiservice.newTamagotchi(name);

                if (tamagotchiservice.tamagotchiAlive(name)) {
                    update(state, stage, deadScene);
                }
            } catch (Exception ex) {
                Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
            }
            stage.setScene(playScene);
        });

        getTamagotchi.setOnAction((event) -> {
            try {
                name = field.getText();
                System.out.println("nimi haettu: " + name);
                tamagotchiservice.getTamagotchi(name);
                if (tamagotchiservice.tamagotchiAlive(name)) {
                    update(state, stage, deadScene);
                    stage.setScene(playScene);
                } else {
                    stage.setScene(deadScene);
                }
            } catch (Exception ex) {
                Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        deleteTamagotchi.setOnAction((event) -> {
            try {
                name = del.getText();
                tamagotchiservice.delete(name);
            } catch (Exception ex) {
                Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        returnbutton.setOnAction((event) -> {
            nameField.clear();
            field.clear();
            del.clear();
            stage.setScene(startScene);
        });

        stage.setScene(startScene);
        stage.show();

        feedbutton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                state.setCenter(frames.getFrameEat());
                try {
                    tamagotchiservice.updateTamagotchiHunger(name);
                } catch (Exception ex) {
                    Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        cleanbutton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                try {
                    state.setCenter(frames.getFrameClean());
                    tamagotchiservice.updateTamagotchiClean(name);
                } catch (Exception ex) {
                    Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        playbutton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                try {

                    state.setCenter(frames.getFramePlay());
                    tamagotchiservice.updateTamagotchiHappiness(name);
                } catch (Exception ex) {
                    Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        medicatebutton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                try {
                    state.setCenter(frames.getFrameMedicate());
                    tamagotchiservice.updateTamagotchiMedicate(name);
                    System.out.println("tila:  " + tamagotchiservice.getMood(name));
                } catch (Exception ex) {
                    Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    public HBox buttons2() {
        HBox buttons2 = new HBox();
        buttons2.setSpacing(100);
        medicatebutton = new Button("Medicate");
        sleepbutton = new Button("Sleep");
        wakeup = new Button("Wake up");
        buttons2.getChildren().add(medicatebutton);
        buttons2.getChildren().add(sleepbutton);
        buttons2.getChildren().add(wakeup);
        return buttons2;
    }

    public HBox buttons() {
        //napit
        HBox buttons = new HBox();
        buttons.setSpacing(100);
        feedbutton = new Button("Feed");
        playbutton = new Button("Play");
        cleanbutton = new Button("Clean");
        returnbutton = new Button("Exit");
        buttons.getChildren().add(feedbutton);
        buttons.getChildren().add(playbutton);
        buttons.getChildren().add(cleanbutton);
        buttons.getChildren().add(returnbutton);
        return buttons;
    }

    public GridPane startGridPane() throws Exception {
        createTamagotchi = new Button("Create");
        getTamagotchi = new Button("Load");
        deleteTamagotchi = new Button("Delete");

        //alkunäkymä
        Label nameText = new Label("New tamagotchi: ");
        nameField = new TextField();
        Label oldone = new Label("Load your old tamagotchi: ");
        field = new TextField();
        Label delete = new Label("Delete tamagotchi: ");
        del = new TextField();
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
        return group;
    }

    /**
     * @param args the command line arguments
     */
    public void update(BorderPane state, Stage stage, Scene deadScene) {

        AnimationTimer animationtimer = new AnimationTimer() {
            long before = 0;

            @Override
            public void handle(long l) {

                if (l - before < 1_000_000_000_0L) {  //10 000 ms eli 10sec 
                    return;
                }
                try {

                    if (tamagotchiservice.tamagotchiAlive(name) == false) {
                        stage.setScene(deadScene);
                    }
                    if (tamagotchiservice.getMood(name).equals("sad")) {
                        state.setCenter(frames.getFrameSad());
                    } else if (tamagotchiservice.getMood(name).equals("hungry")) {
                        state.setCenter(frames.getFrameHungry());
                    } else if (tamagotchiservice.getMood(name).equals("dirty")) {
                        state.setCenter(frames.getFrameDirty());
                    } else if (tamagotchiservice.getMood(name).equals("happy")) {
                        state.setCenter(frames.frameHappy);
                    } else if (tamagotchiservice.getMood(name).equals("sick")) {
                        state.setCenter(frames.getFrameSad()); //tee kipeä kuva ja muuta
                    }

                    //AIKA KULUU
                    tamagotchiservice.time(name);

                } catch (Exception ex) {
                    Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
                }

                before = l;
            }

        };
        animationtimer.start();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }

}
