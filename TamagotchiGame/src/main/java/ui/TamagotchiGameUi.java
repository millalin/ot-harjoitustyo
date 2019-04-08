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
    FlowPane frameHungry;
    FlowPane frameDirty;
    FlowPane frameClean;

    @Override
    public void start(Stage stage) throws Exception {

        TamagotchiDao tamagotchiDao = new TamagotchiDao();
        tamagotchiservice = new TamagotchiService((tamagotchiDao));

        frames();

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

        BorderPane state = new BorderPane();
        BorderPane deadState = new BorderPane();

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

        state.setTop(buttons);
        state.setPadding(new Insets(30, 20, 20, 30));
        state.setCenter(frameHappy);
        state.setBottom(buttons2);

        //deadState.setTop(); paluunappi? 
        deadState.setCenter(frameDead);

        Scene startScene = new Scene(group);
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
                System.out.println("elossa? " + tamagotchiservice.tamagotchiAlive(name));
                if (tamagotchiservice.tamagotchiAlive(name)) {
                    update(state, stage, deadScene);
                    stage.setScene(playScene);
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

        cleanbutton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                try {

                    state.setCenter(frameClean);
                    tamagotchiservice.updateTamagotchiClean(name);
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
                    tamagotchiservice.updateTamagotchiHappiness(name);
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
                          tamagotchiservice.updateTamagotchiMedicate(name);
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

                    if (tamagotchiservice.tamagotchiAlive(name) == false) {
                        stage.setScene(deadScene);
                        System.out.println("pitäisi loppua, huonosti kävi");

                    }

                    if (tamagotchiservice.getMood(name).equals("sad")) {
                        state.setCenter(frameSad);

                    } else if (tamagotchiservice.getMood(name).equals("hungry")) {
                        state.setCenter(frameHungry);
                    } else if (tamagotchiservice.getMood(name).equals("dirty")) {
                        state.setCenter(frameDirty);
                    } else if (tamagotchiservice.getMood(name).equals("happy")) {
                        state.setCenter(frameHappy);
                    } else if (tamagotchiservice.getMood(name).equals("sick"))  {
                        state.setCenter(frameSad); //tee kipeä kuva ja muuta
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

    public void frames() {

        Image happy = new Image("file:Gifs/tamag(3).gif");
        ImageView happypicture = new ImageView(happy);

        Image sad = new Image("file:Gifs/tamag(12).gif");
        ImageView sadpicture = new ImageView(sad);

        Image eat = new Image("file:Gifs/tamag(6).gif");
        ImageView eatpicture = new ImageView(eat);

        Image play = new Image("file:Gifs/tamag(8).gif");
        ImageView playpicture = new ImageView(play);

        Image dead = new Image("file:Gifs/tamag(13).gif");
        ImageView deadpicture = new ImageView(dead);

        Image sick = new Image("file:Gifs/tamag(14).gif");
        ImageView sickpicture = new ImageView(sick);

        Image sleep = new Image("file:Gifs/tamag(15).gif");
        ImageView sleeppicture = new ImageView(sleep);

        Image medicate = new Image("file:Gifs/tamag(16).gif");
        ImageView medicatepicture = new ImageView(medicate);

        Image hungry = new Image("file:Gifs/tamag(17).gif");
        ImageView hungrypicture = new ImageView(hungry);

        Image dirty = new Image("file:Gifs/tamag(18).gif");
        ImageView dirtypicture = new ImageView(dirty);

        Image clean = new Image("file:Gifs/tamag(19).gif");
        ImageView cleanpicture = new ImageView(clean);

        frameHappy = new FlowPane();
        frameSad = new FlowPane();
        frameEat = new FlowPane();
        framePlay = new FlowPane();
        frameMedicate = new FlowPane();
        frameDead = new FlowPane();
        frameHungry = new FlowPane();
        frameDirty = new FlowPane();
        frameClean = new FlowPane();

        frameHappy.getChildren().add(happypicture);
        frameSad.getChildren().add(sadpicture);
        frameEat.getChildren().add(eatpicture);
        framePlay.getChildren().add(playpicture);
        frameMedicate.getChildren().add(medicatepicture);
        frameDead.getChildren().add(deadpicture);
        frameHungry.getChildren().add(hungrypicture);
        frameDirty.getChildren().add(dirtypicture);
        frameClean.getChildren().add(cleanpicture);
    }

}
