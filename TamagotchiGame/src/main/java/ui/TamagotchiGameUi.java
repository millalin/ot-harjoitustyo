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
    Button statistics;

    TextField nameField;
    TextField field;
    TextField del;

    Label nameText;
    Label oldone;
    boolean ignoreEvents;
    String style;

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

                if (tamagotchiservice.alreadyExists(name)) {
                    // ilmoitus että on ja valitse toinen
                    nameText.setText("Name already exists, choose other one");
                } else {
                    tamagotchiservice.newTamagotchi(name);
                    update(state, stage, deadScene);
                    stage.setScene(playScene);
                }
            } catch (Exception ex) {
                Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        getTamagotchi.setOnAction((event) -> {
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
                } else  {
                    oldone.setText("There is no such tamagotchi");
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

                } catch (Exception ex) {
                    Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        sleepbutton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                try {

                    state.setCenter(frames.getFrameSleep());
                    tamagotchiservice.setMood(name, "sleep");
                    disableButtons(true);

                } catch (Exception ex) {
                    Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        wakeup.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                try {

                    tamagotchiservice.setMood(name, "happy");
                    update(state, stage, deadScene);
                    disableButtons(false);

                } catch (Exception ex) {
                    Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    public HBox buttons() {
        style
                = "-fx-background-color: \n"
                + "        #ecebe9,\n"
                + "        rgba(0,0,0,0.05),\n"
                + "        linear-gradient(#dcca8a, #c7a740),\n"
                + "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\n"
                + "        linear-gradient(#f6ebbe, #e6c34d);\n"
                + "    -fx-background-insets: 0,9 9 8 9,9,10,11;\n"
                + "    -fx-background-radius: 50;\n"
                + "    -fx-padding: 15 30 15 30;\n"
                + "    -fx-font-family: \"Helvetica\";\n"
                + "    -fx-font-size: 18px;\n"
                + "    -fx-text-fill: #311c09;\n"
                + "    -fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);";
        //napit
        HBox buttons = new HBox();

        buttons.setSpacing(200);
        feedbutton = new Button("Feed");
        playbutton = new Button("Play");
        cleanbutton = new Button("Clean");
        returnbutton = new Button("Exit");

        feedbutton.setStyle(style);
        playbutton.setStyle(style);
        cleanbutton.setStyle(style);
        returnbutton.setStyle(style);

        buttons.getChildren().add(feedbutton);
        buttons.getChildren().add(playbutton);
        buttons.getChildren().add(cleanbutton);
        buttons.getChildren().add(returnbutton);

        return buttons;
    }

    public HBox buttons2() {
        HBox buttons2 = new HBox();
        buttons2.setSpacing(180);
        medicatebutton = new Button("Medicate");
        sleepbutton = new Button("Sleep");
        wakeup = new Button("Wake up");
        statistics = new Button("Statistics");

        medicatebutton.setStyle(style);
        sleepbutton.setStyle(style);
        wakeup.setStyle(style);
        statistics.setStyle(style);

        buttons2.getChildren().add(medicatebutton);
        buttons2.getChildren().add(sleepbutton);
        buttons2.getChildren().add(wakeup);
        buttons2.getChildren().add(statistics);

        return buttons2;
    }

    public GridPane startGridPane() throws Exception {
        createTamagotchi = new Button("Create");
        getTamagotchi = new Button("Load");
        deleteTamagotchi = new Button("Delete");

        //alkunäkymä
        nameText = new Label("New tamagotchi: ");
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

    public void disableButtons(boolean t) {
        medicatebutton.setDisable(t);
        playbutton.setDisable(t);
        feedbutton.setDisable(t);
        cleanbutton.setDisable(t);
    }

    /**
     * @param args the command line arguments
     */
    public void update(BorderPane state, Stage stage, Scene deadScene) {

        AnimationTimer animationtimer = new AnimationTimer() {
            long before = 0;

            @Override
            public void handle(long l) {

                if (ignoreEvents) {
                    return;
                }
                if (l - before < 1_000_000_000_0L) {  //10 000 ms eli 10sec 
                    return;
                }
                try {

                    if (tamagotchiservice.tamagotchiAlive(name) == false) {
                        stage.setScene(deadScene);
                    }

                    if (tamagotchiservice.getMood(name).equals("sleep")) {
                        state.setCenter(frames.getFrameSleep());
                        tamagotchiservice.updateTamagotchiSleep(name);
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
