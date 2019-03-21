package ui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import dao.TamagotchiDao;
import domain.Tamagotchi;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;
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

    private Tamagotchi tamagotchi;
    private TamagotchiDao tamagotchiDao;

    @Override
    public void start(Stage stage) {
        
        Button createTamagotchi = new Button("Create");
        Button getTamagotchi = new Button("Load");

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

        BorderPane asettelu = new BorderPane();

        Scene eka = new Scene(group);
        Scene toka = new Scene(asettelu);

        Image kuvatiedosto = new Image("file:GIFMaker.org_KWB9ba.gif");
        ImageView kuva = new ImageView(kuvatiedosto);

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

        FlowPane ruutu = new FlowPane();

        ruutu.getChildren().add(kuva);

        asettelu.setTop(buttons);
        asettelu.setCenter(ruutu);

        createTamagotchi.setOnAction((event) -> {
            //luo lisäksi tama
            String nimi=nimiKentta.getText();
            tamagotchi = new Tamagotchi(nimi);
            
            
            try {
                //     tamagotchiDao.alustaTietokanta();
                tamagotchiDao=new TamagotchiDao(tamagotchi);
                tamagotchiDao.create(tamagotchi);
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
                //nälkä
                tamagotchi.setHunger(tamagotchi.getHunger() - 20); //kun syötetään nälkä vähenee 20
                try {
                    tamagotchiDao.update(tamagotchi);
                } catch (Exception ex) {
                    Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("hunger: " + tamagotchi.getHunger());
            }
        });

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        try (Connection conne = DriverManager.getConnection("jdbc:h2:./tamagotchitietokanta", "sa", "")) {
          
                conne.prepareStatement("CREATE TABLE IF NOT EXISTS Tamagotchi(id serial, name varchar(25), hunger integer, energy integer);").executeUpdate();
            System.out.println("onnistui");
        } catch (SQLException ex) {
            Logger.getLogger(TamagotchiDao.class.getName()).log(Level.SEVERE, null, ex);
        }

     
        launch(args);
    }

}
