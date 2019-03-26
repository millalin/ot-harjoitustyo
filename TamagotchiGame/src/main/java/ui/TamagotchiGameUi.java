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
    private String nimi;


  

    @Override
    public void start(Stage stage) throws Exception{
        
        TamagotchiDao tamagotchiDao = new TamagotchiDao();
        tamagotchiservice= new TamagotchiService((tamagotchiDao));

        Button createTamagotchi = new Button("Create");
        Button getTamagotchi = new Button("Load");

        //alkunäkymä
        Label nimiTeksti = new Label("Name of your tamagotchi: ");
        TextField nimiKentta = new TextField();
        Label oldone = new Label("Load your old tamagotchi: ");
        TextField kentta = new TextField();

        GridPane group = new GridPane();
        group.add(nimiTeksti, 0, 0);
        group.add(nimiKentta, 1, 0);
        group.add(createTamagotchi, 1, 1);
        group.add(oldone, 0, 2);
        group.add(kentta, 1, 2);
        group.add(getTamagotchi, 1, 3);

        group.setHgap(10);
        group.setVgap(10);
        group.setPadding(new Insets(10, 10, 10, 10));

        //pelinäkymää
        BorderPane state = new BorderPane();

        Image happy = new Image("file:GIFMaker.org_KWB9ba.gif");
        ImageView happypicture = new ImageView(happy);

        Image sad = new Image("file:GIFMaker.org_z09dXD.gif");
        ImageView sadpicture = new ImageView(sad);

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

        FlowPane ruutuhappy = new FlowPane();
        FlowPane ruutusad = new FlowPane();

        ruutuhappy.getChildren().add(happypicture);
        ruutusad.getChildren().add(sadpicture);

        state.setTop(buttons);
        state.setCenter(ruutuhappy);

           
        

        Scene eka = new Scene(group);
        Scene toka = new Scene(state);

        createTamagotchi.setOnAction((event) -> {
            //luo lisäksi tama

            try {
                //     tamagotchiDao.alustaTietokanta();

                nimi=nimiKentta.getText();

                //     tamagotchi = new Tamagotchi(nimi);
                tamagotchiservice.newTamagotchi(nimi);
                
                System.out.println("nimi: "+nimi);
                //  tamagotchiDao = new TamagotchiDao(tamagotchi);
                //tamagotchiDao.create(tamagotchi);

                if (tamagotchiservice.TamagotchiAlive(nimi)) { //muuta serv puolelle
                    paivita(state, ruutusad, ruutuhappy);
                }

            } catch (Exception ex) {
                Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);

            }
            stage.setScene(toka);
        });

        /*        if (tamagotchi.isAlive()) {
            //timer
            tamagotchiservice.updateTamagotchi();

        } */
        returnbutton.setOnAction((event) -> {
            stage.setScene(eka);
        });

        stage.setScene(eka);
        stage.show();

        feedbutton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //nälkä

                //tamagotchi.setHunger(tamagotchi.getHunger() - 20); //kun syötetään nälkä vähenee 20
                try {
                    //  tamagotchiDao.update(tamagotchi);
                    tamagotchiservice.updateTamagotchi(nimi);
                    System.out.println("tila:  " + tamagotchiservice.getMood(nimi));

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

    public void paivita(BorderPane state, FlowPane ruutusad, FlowPane ruutuhappy) {

        AnimationTimer animationtimer = new AnimationTimer() {

            long before = 0;

            @Override
            public void handle(long l) {

                if (l - before < 100000000) {
                    return;
                }
                try {
                    if (tamagotchiservice.getMood(nimi).equals("sad")) {
                        state.setCenter(ruutusad);

                    } else if (tamagotchiservice.getMood(nimi).equals("happy")) {
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
