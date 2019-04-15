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

    private TamagotchiService tamagotchiservice;
    private String name;
    private TamagotchiFrames frames;
    private Buttons allbuttons;

    TextField nameField;
    TextField field;
    TextField del;

    Label nameText;
    Label oldone;

    int count = 9;

    @Override
    public void start(Stage stage) throws Exception {

        TamagotchiDao tamagotchiDao = new TamagotchiDao();
        tamagotchiservice = new TamagotchiService((tamagotchiDao));
        frames = new TamagotchiFrames();
        allbuttons = new Buttons();

        GridPane startGroup = startGridPane();

        BorderPane state = new BorderPane();
        BorderPane deadState = new BorderPane();

        HBox buttons = allbuttons.getButtons();
        HBox buttons2 = allbuttons.getButtons2();

        state.setTop(buttons);
        state.setPadding(new Insets(30, 20, 20, 30));
        state.setCenter(frames.getFrameHappy());
        state.setBottom(buttons2);

        //deadState.setTop(); paluunappi? 
        deadState.setCenter(frames.getFrameDead());

        Scene startScene = new Scene(startGroup);
        Scene playScene = new Scene(state);
        Scene deadScene = new Scene(deadState);

        allbuttons.getCreateTamagotchi().setOnAction((event) -> {
            try {
                name = nameField.getText();
                if (tamagotchiservice.alreadyExists(name)) {
                    nameText.setText("Name already exists, please choose another one");
                } else {
                    tamagotchiservice.newTamagotchi(name);
                    update(state, stage, deadScene);
                    stage.setScene(playScene);
                }
            } catch (Exception ex) {
                Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        allbuttons.getGetTamagotchi().setOnAction((event) -> {
            try {
                name = field.getText();
                if (tamagotchiservice.alreadyExists(name)) {
                    tamagotchiservice.getTamagotchi(name);
                    if (tamagotchiservice.tamagotchiAlive(name)) {
                        update(state, stage, deadScene);
                        stage.setScene(playScene);
                    } else {
                        stage.setScene(deadScene);
                    }
                } else {
                    oldone.setText("There is no such tamagotchi");
                }

            } catch (Exception ex) {
                Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        allbuttons.getDeleteTamagotchi().setOnAction((event) -> {
            try {
                name = del.getText();
                tamagotchiservice.delete(name);
            } catch (Exception ex) {
                Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        allbuttons.getReturnbutton().setOnAction((event) -> {
            nameField.clear();
            field.clear();
            del.clear();
            stage.setScene(startScene);
        });

        stage.setScene(startScene);
        stage.show();

        allbuttons.getFeedbutton().setOnAction((ActionEvent event) -> {
            count = 0;
            state.setCenter(frames.getFrameEat());
            try {
                tamagotchiservice.updateTamagotchiHunger(name);
            } catch (Exception ex) {
                Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        allbuttons.getCleanbutton().setOnAction((ActionEvent event) -> {
            try {
                count = 0;
                state.setCenter(frames.getFrameClean());
                tamagotchiservice.updateTamagotchiClean(name);

            } catch (Exception ex) {
                Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        allbuttons.getPlaybutton().setOnAction((ActionEvent event) -> {
            try {
                count = 0;
                state.setCenter(frames.getFramePlay());
                tamagotchiservice.updateTamagotchiHappiness(name);

            } catch (Exception ex) {
                Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        allbuttons.getMedicatebutton().setOnAction((ActionEvent event) -> {
            try {
                count = 0;
                state.setCenter(frames.getFrameMedicate());
                tamagotchiservice.updateTamagotchiMedicate(name);

            } catch (Exception ex) {
                Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        allbuttons.getSleepbutton().setOnAction((ActionEvent event) -> {
            try {

                state.setCenter(frames.getFrameSleep());
                tamagotchiservice.setMood(name, "sleep");
                allbuttons.disableButtons(true);

            } catch (Exception ex) {
                Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        allbuttons.getWakeup().setOnAction((ActionEvent event) -> {
            try {
                tamagotchiservice.setMood(name, "happy");
                allbuttons.disableButtons(false);

            } catch (Exception ex) {
                Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    public void update(BorderPane state, Stage stage, Scene deadScene) {

        AnimationTimer animationtimer = new AnimationTimer() {
            long before = 0;

            @Override
            public void handle(long l) {

                if (l - before < 1_000_000_000L) {  //10 00 ms eli 1sec 
                    return;
                }

                try {

                    count++;
                    System.out.println(count);

                    if (tamagotchiservice.tamagotchiAlive(name) == false) {
                        stage.setScene(deadScene);
                    }

                    if (tamagotchiservice.getMood(name).equals("sleep")) {
                        state.setCenter(frames.getFrameSleep());
                        tamagotchiservice.updateTamagotchiSleep(name);
                        allbuttons.disableButtons(true);
                    } else {
                        allbuttons.disableButtons(false);
                    }
                    if (count > 8) {
                        if (tamagotchiservice.getMood(name).equals("sad")) {
                            state.setCenter(frames.getFrameSad());
                        } else if (tamagotchiservice.getMood(name).equals("hungry")) {
                            state.setCenter(frames.getFrameHungry());
                        } else if (tamagotchiservice.getMood(name).equals("dirty")) {
                            state.setCenter(frames.getFrameDirty());
                        } else if (tamagotchiservice.getMood(name).equals("sick")) {
                            state.setCenter(frames.getFrameSad()); //tee kipeä kuva ja muuta
                        } else if (tamagotchiservice.getMood(name).equals("happy")) {
                            state.setCenter(frames.frameHappy);
                        }
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

    public GridPane startGridPane() throws Exception {

        //alkunäkymä
        nameText = new Label("Create tamagotchi: ");
        nameField = new TextField();
        oldone = new Label("Load your old tamagotchi: ");
        field = new TextField();
        Label delete = new Label("Delete tamagotchi: ");
        del = new TextField();
        Label tamas = new Label("Tamagotchis:");
        Label names = new Label(tamagotchiservice.tamaslist().toString());

        GridPane group = new GridPane();
        group.setMinSize(600, 300);
        group.add(nameText, 0, 0);
        group.add(nameField, 1, 0);
        group.add(allbuttons.getCreateTamagotchi(), 2, 0);
        group.add(oldone, 0, 1);
        group.add(field, 1, 1);
        group.add(allbuttons.getGetTamagotchi(), 2, 1);
        group.add(delete, 0, 2);
        group.add(del, 1, 2);
        group.add(allbuttons.getDeleteTamagotchi(), 2, 2);
        group.add(tamas, 1, 4);
        group.add(names, 1, 5);
        group.setHgap(10);
        group.setVgap(25);
        group.setPadding(new Insets(10, 10, 10, 10));
        return group;
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }

}
