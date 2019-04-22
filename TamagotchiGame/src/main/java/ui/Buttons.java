package ui;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * Sovelluksen napeista ja niiden muotoilusta vastaava luokka.
 */
public class Buttons {

    HBox buttons;
    HBox buttons2;

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
    Button age;
    String style;

    public Buttons() {

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

        buttons = new HBox();

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

        buttons2 = new HBox();
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

        createTamagotchi = new Button("Create");
        getTamagotchi = new Button("Load");
        deleteTamagotchi = new Button("Delete");
        age = new Button("Statistics");
    }

    public Button getAge() {
        return age;
    }

    public HBox getButtons() {
        return buttons;
    }

    public HBox getButtons2() {
        return buttons2;
    }

    public Button getCreateTamagotchi() {
        return createTamagotchi;
    }

    public Button getGetTamagotchi() {
        return getTamagotchi;
    }

    public Button getDeleteTamagotchi() {
        return deleteTamagotchi;
    }

    public Button getFeedbutton() {
        return feedbutton;
    }

    public Button getPlaybutton() {
        return playbutton;
    }

    public Button getCleanbutton() {
        return cleanbutton;
    }

    public Button getReturnbutton() {
        return returnbutton;
    }

    public Button getMedicatebutton() {
        return medicatebutton;
    }

    public Button getSleepbutton() {
        return sleepbutton;
    }

    public Button getWakeup() {
        return wakeup;
    }

    public Button getStatistics() {
        return statistics;
    }

    /**
     * Asettaa nappien toiminnan pois päältä ja takaisin toimintaan.
     */
    public void disableButtons(boolean t) {
        medicatebutton.setDisable(t);
        playbutton.setDisable(t);
        feedbutton.setDisable(t);
        cleanbutton.setDisable(t);
    }
}
