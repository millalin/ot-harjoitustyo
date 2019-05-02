package ui;

import database.Database;
import domain.TamagotchiService;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Ohjelman pääluokka, joka vastaa graafisen käyttöliittymän luomisesta ja
 * toiminnasta. Määrittää painikkeiden toiminnallisuuden. Luokka käyttää
 * TamagotchiServise luokkaa käyttöliittymälogiikan toteuttamiseen.
 */
public class TamagotchiGameUi extends Application {

    private TamagotchiService tamagotchiservice;
    private Database database;
    private String name;
    private TamagotchiFrames frames;
    private Buttons allbuttons;

    TextField nameField;
    TextField field;
    TextField del;

    Label names;
    Label nameText;
    Label oldone;
    Label delete;
    Label tamas;
    Label stats;
    Label tamaAges;
    int count;
    String databaseName;
    AnimationTimer animationtimer;

    /**
     * Valmistelee tietokantayhteyden ja tamagotchiService luokan.
     *
     * @throws Exception
     */
    @Override
    public void init() throws Exception {
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream("config.properties"));
            databaseName = properties.getProperty("database");
        } catch (FileNotFoundException ex) {
            databaseName = "./src/main/resources/database/tamagotchitietokanta";
        }

        String todoFile = properties.getProperty("todoFile");

        database = new Database(databaseName);
        tamagotchiservice = new TamagotchiService(database);

    }

    /**
     * Ohjelman käynnistysmetodi, joka hallinnoi ikkunoiden vaihtamista.
     *
     * @param stage Ohjelman pääikkuna
     *
     * @throws SQLException virhe tietokannanhallinnassa
     */
    @Override
    public void start(Stage stage) throws SQLException {

        frames = new TamagotchiFrames(tamagotchiservice);
        allbuttons = new Buttons();

        GridPane startGroup = startGridPane();
        GridPane ages = agesGridPane();

        GridPane main = new GridPane();
        BorderPane state = new BorderPane();
        BorderPane deadState = new BorderPane();

        HBox buttons = allbuttons.getButtons();
        HBox buttons2 = allbuttons.getButtons2();

        state.setTop(buttons);
        state.setPadding(new Insets(30, 20, 20, 30));
        state.setCenter(frames.getFrameEgg());
        state.setBottom(buttons2);
        state.getCenter();
        main.getChildren().add(state);
        main.setAlignment(Pos.CENTER);

        //deadState.setTop(); paluunappi? 
        deadState.setCenter(frames.getFrameDead());
        deadState.setTop(allbuttons.getReturnDead());

        Scene startScene = new Scene(startGroup);
        Scene playScene = new Scene(main);
        Scene deadScene = new Scene(deadState);
        Scene agesScene = new Scene(ages);

        allbuttons.getCreateTamagotchi().setOnAction((event) -> {
            try {
                name = nameField.getText();
                tamagotchiservice.setName(name);
                if (tamagotchiservice.alreadyExists()) {
                    nameText.setText("Name already exists, please choose another one");
                } else if (tamagotchiservice.tooShortName()) {
                    nameText.setText("Name must be atleast 3 characters");
                } else if (tamagotchiservice.tooLongName()) {
                    nameText.setText("Name can't be over 25 characters");
                } else {
                    count = 0;
                    tamagotchiservice.newTamagotchi();
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
                tamagotchiservice.setName(name);
                count = 39;
                if (tamagotchiservice.alreadyExists()) {
                    tamagotchiservice.getTamagotchi();
                    if (tamagotchiservice.tamagotchiAlive()) {
                        update(state, stage, deadScene);
                        stage.setScene(playScene);
                    } else {
                        tamagotchiservice.ageUpdate();
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
                tamagotchiservice.setName(name);
                del.clear();
                tamagotchiservice.delete();
                names.setText(tamagotchiservice.tamaslist());
            } catch (Exception ex) {
                Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        allbuttons.getAge().setOnAction((event) -> {
            try {

                tamaAges.setText(tamagotchiservice.ages());
                stage.setScene(agesScene);

            } catch (Exception ex) {
                Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        allbuttons.getBack().setOnAction((event) -> {
            try {
                stage.setScene(startScene);
            } catch (Exception ex) {
                Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        allbuttons.getReturnbutton().setOnAction((event) -> {
            try {
                nameField.clear();
                field.clear();
                del.clear();
                names.setText(tamagotchiservice.tamaslist());

                stage.setScene(startScene);
                animationtimer.stop();
            } catch (Exception ex) {
                Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        allbuttons.getReturnDead().setOnAction((event) -> {
            try {
                nameField.clear();
                field.clear();
                del.clear();
                names.setText(tamagotchiservice.tamaslist());

                stage.setScene(startScene);
                animationtimer.stop();
            } catch (Exception ex) {
                Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        stage.setScene(startScene);
        stage.show();

        allbuttons.getFeedbutton().setOnAction((ActionEvent event) -> {
            count = 30;
            state.setCenter(frames.getFrameEat());
            try {
                tamagotchiservice.updateTamagotchiHunger();
            } catch (Exception ex) {
                Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        allbuttons.getCleanbutton().setOnAction((ActionEvent event) -> {
            try {
                count = 30;
                state.setCenter(frames.getFrameClean());
                tamagotchiservice.updateTamagotchiClean();

            } catch (Exception ex) {
                Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        allbuttons.getPlaybutton().setOnAction((ActionEvent event) -> {
            try {
                count = 30;
                state.setCenter(frames.getFramePlay());
                tamagotchiservice.updateTamagotchiHappiness();

            } catch (Exception ex) {
                Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        allbuttons.getMedicatebutton().setOnAction((ActionEvent event) -> {
            try {
                count = 30;
                state.setCenter(frames.getFrameMedicate());
                tamagotchiservice.updateTamagotchiMedicate();

            } catch (Exception ex) {
                Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        allbuttons.getSleepbutton().setOnAction((ActionEvent event) -> {
            try {

                state.setCenter(frames.getFrameSleep());
                tamagotchiservice.setMood("sleep");
                allbuttons.disableButtons(true);

            } catch (Exception ex) {
                Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        allbuttons.getWakeup().setOnAction((ActionEvent event) -> {
            try {
                tamagotchiservice.setMood("happy");
                allbuttons.disableButtons(false);

            } catch (Exception ex) {
                Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        allbuttons.getStatistics().setOnAction((ActionEvent event) -> {
            try {
                count = 30;
                state.setCenter(frames.statistics(name));

            } catch (Exception ex) {
                Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    /**
     * Metodi, joka hallinnoi ajan kulumista pelissä. Sisältää animationtimerin,
     * joka päivittää tilannetta sekunnin väliajoin.
     *
     * @param state BorderPane asettelu johon, asetetaan gif-kuvia tamagotchin
     * tilan mukaan
     * @param stage pääikkuna
     * @param deadScene Scene-olio, joka asetetaan, jos tamagotchi on kuollut
     */
    public void update(BorderPane state, Stage stage, Scene deadScene) {

        animationtimer = new AnimationTimer() {
            long before = 0;

            @Override
            public void handle(long l) {

                if (l - before < 1_000_000_000L) {  //10 00 ms eli 1sec 
                    return;
                }

                try {

                    count++;

                    if (count < 30) {
                        allbuttons.disableAllButtons(true);
                        state.setCenter(frames.getFrameEgg());
                    } else {
                        if (!tamagotchiservice.getMood().equals("sleep")) {
                            allbuttons.disableButtons(false);
                            allbuttons.disableAllButtons(false);
                        } else {
                            state.setCenter(frames.getFrameSleep());
                            tamagotchiservice.updateTamagotchiSleep();
                            allbuttons.disableAllButtons(false);
                            allbuttons.disableButtons(true);
                        }
                    }
                    if (tamagotchiservice.tamagotchiAlive() == false) {
                        stage.setScene(deadScene);
                    }

                    if (count > 38) {
                        if (tamagotchiservice.getMood().equals("sad")) {
                            state.setCenter(frames.getFrameSad());
                        } else if (tamagotchiservice.getMood().equals("hungry")) {
                            state.setCenter(frames.getFrameHungry());
                        } else if (tamagotchiservice.getMood().equals("dirty")) {
                            state.setCenter(frames.getFrameDirty());
                        } else if (tamagotchiservice.getMood().equals("sick")) {
                            state.setCenter(frames.getFrameSick());
                        } else if (tamagotchiservice.getMood().equals("happy")) {
                            state.setCenter(frames.getFrameHappy());
                        }
                    }

                    tamagotchiservice.time();
                    tamagotchiservice.ageUpdate();
                } catch (Exception ex) {
                    Logger.getLogger(TamagotchiGameUi.class.getName()).log(Level.SEVERE, null, ex);
                }
                before = l;
            }

        };
        animationtimer.start();
    }

    /**
     * Ohjelman alkunäkymän palauttava metodi, joka luo alkunäkymän graafisen
     * ulkoasun.
     *
     * @throws SQLException
     *
     * @return GripPane olio, joka sisältää alkunäkymän
     */
    public GridPane startGridPane() throws SQLException {

        //alkunäkymä
        nameText = new Label("Create tamagotchi: ");
        nameField = new TextField();
        oldone = new Label("Load your old tamagotchi: ");
        field = new TextField();
        delete = new Label("Delete tamagotchi: ");
        del = new TextField();
        tamas = new Label("Tamagotchis:");
        names = new Label(tamagotchiservice.tamaslist());
        stats = new Label("History of all tamagotchis");

        GridPane group = new GridPane();
        group.setAlignment(Pos.CENTER);
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
        group.add(stats, 0, 3);
        group.add(allbuttons.getAge(), 2, 3);
        group.add(tamas, 1, 4);
        group.add(names, 1, 5);
        group.setHgap(10);
        group.setVgap(25);
        group.setPadding(new Insets(10, 10, 10, 10));
        return group;
    }

    /**
     * Ohjelman kaikkien tamagotchien historianäkymän palauttava metodi, joka
     * luo alkunäkymän graafisen ulkoasun.
     *
     * @throws SQLException
     *
     * @return GripPane olio, joka sisältää historiatilastonäkymän
     */
    public GridPane agesGridPane() throws SQLException {
        GridPane tamagotchiAges = new GridPane();

        Label headline = new Label("History of all tamagotchis");
        tamagotchiAges.add(headline, 1, 1);
        tamaAges = new Label(tamagotchiservice.ages());
        tamagotchiAges.add(tamaAges, 1, 3);
        tamagotchiAges.add(allbuttons.getBack(), 5, 0);
        tamagotchiAges.setMinSize(400, 300);
        tamagotchiAges.setVgap(10);
        tamagotchiAges.setHgap(10);
        tamagotchiAges.setPadding(new Insets(10, 10, 10, 10));

        return tamagotchiAges;
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }

}
