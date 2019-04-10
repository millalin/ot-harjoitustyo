/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

/**
 *
 * @author milla
 */
public class TamagotchiFrames {

    FlowPane frameHappy;
    FlowPane frameSad;
    FlowPane frameEat;
    FlowPane framePlay;
    FlowPane frameDead;
    FlowPane frameMedicate;
    FlowPane frameHungry;
    FlowPane frameDirty;
    FlowPane frameClean;

    public TamagotchiFrames() {

   

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

    public FlowPane getFrameHappy() {
        return frameHappy;
    }

    public FlowPane getFrameSad() {
        return frameSad;
    }

    public FlowPane getFrameEat() {
        return frameEat;
    }

    public FlowPane getFramePlay() {
        return framePlay;
    }

    public FlowPane getFrameDead() {
        return frameDead;
    }

    public FlowPane getFrameMedicate() {
        return frameMedicate;
    }

    public FlowPane getFrameHungry() {
        return frameHungry;
    }

    public FlowPane getFrameDirty() {
        return frameDirty;
    }

    public FlowPane getFrameClean() {
        return frameClean;
    }

}
