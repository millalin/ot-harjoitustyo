/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;


import domain.Tamagotchi;
import domain.TamagotchiService;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

/**
 * Tamagotchin eri tiloista kuvina vastaava luokka
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
    FlowPane frameSleep;
    FlowPane frameSick;
    FlowPane frameEgg;
    FlowPane frameBabyhappy;
    FlowPane frameBabyeat;
    FlowPane frameBabysad;
    FlowPane frameBabysick;
    FlowPane frameBabyplay;
    FlowPane frameBabydirty;
    FlowPane frameBabyhungry;
    FlowPane frameBabymedicate;
    TamagotchiService tamagotchiservice;
    String name;
    
   

    public TamagotchiFrames(TamagotchiService tamagotchiservice) {
        
        this.tamagotchiservice = tamagotchiservice;
        
        

        Image happy = new Image("/Gifs/tamag(3).gif");
        ImageView happypicture = new ImageView(happy);

        Image sad = new Image("/Gifs/tamag(12).gif");
        ImageView sadpicture = new ImageView(sad);

        Image eat = new Image("/Gifs/tamag(6).gif");
        ImageView eatpicture = new ImageView(eat);

        Image play = new Image("/Gifs/tamag(8).gif");
        ImageView playpicture = new ImageView(play);

        Image dead = new Image("/Gifs/tamag(13).gif");
        ImageView deadpicture = new ImageView(dead);

        Image sick = new Image("/Gifs/tamag(14).gif");
        ImageView sickpicture = new ImageView(sick);

        Image sleep = new Image("/Gifs/tamag(15).gif");
        ImageView sleeppicture = new ImageView(sleep);

        Image medicate = new Image("/Gifs/tamag(16).gif");
        ImageView medicatepicture = new ImageView(medicate);

        Image hungry = new Image("/Gifs/tamag(17).gif");
        ImageView hungrypicture = new ImageView(hungry);

        Image dirty = new Image("/Gifs/tamag(18).gif");
        ImageView dirtypicture = new ImageView(dirty);

        Image clean = new Image("/Gifs/tamag(19).gif");
        ImageView cleanpicture = new ImageView(clean);
        
        Image egg = new Image("/Gifs/tamag(20).gif");
        ImageView eggpicture = new ImageView(egg);
        
        Image babyhappy = new Image("/Gifs/tamagbaby.gif");
        ImageView babyhappypicture = new ImageView(babyhappy);
        
        Image babyeat = new Image("/Gifs/tamagbaby(1).gif");
        ImageView babyeatpicture = new ImageView(babyeat);
        
        Image babysad = new Image("/Gifs/tamagbaby(2).gif");
        ImageView babysadpicture = new ImageView(babysad);
        
        Image babysick = new Image("/Gifs/tamagbaby(3).gif");
        ImageView babysickpicture = new ImageView(babysick);

         Image babyplay = new Image("/Gifs/tamababy.gif");
        ImageView babyplaypicture = new ImageView(babyplay);
        
        Image babydirty = new Image("/Gifs/poop2.gif");
        ImageView babydirtypicture = new ImageView(babydirty);
        
          Image babyhungry = new Image("/Gifs/tamahungry.gif");
        ImageView babyhungrypicture = new ImageView(babyhungry);
        
        Image babymedicate = new Image("/Gifs/tamamedi.gif");
        ImageView babymedicatepicture = new ImageView(babymedicate);
        
        frameHappy = new FlowPane();
        frameSad = new FlowPane();
        frameEat = new FlowPane();
        framePlay = new FlowPane();
        frameMedicate = new FlowPane();
        frameDead = new FlowPane();
        frameHungry = new FlowPane();
        frameDirty = new FlowPane();
        frameClean = new FlowPane();
        frameSleep = new FlowPane();
        frameSick = new FlowPane();
        frameEgg = new FlowPane();
        frameBabyhappy = new FlowPane();
        frameBabysick = new FlowPane();
        frameBabysad = new FlowPane();
        frameBabyeat = new FlowPane();
        frameBabyplay = new FlowPane();
        frameBabydirty = new FlowPane();
        frameBabyhungry = new FlowPane();
        frameBabymedicate = new FlowPane();

        
        frameHappy.getChildren().add(happypicture);
        frameSad.getChildren().add(sadpicture);
        frameEat.getChildren().add(eatpicture);
        framePlay.getChildren().add(playpicture);
        frameMedicate.getChildren().add(medicatepicture);
        frameDead.getChildren().add(deadpicture);
        frameHungry.getChildren().add(hungrypicture);
        frameDirty.getChildren().add(dirtypicture);
        frameClean.getChildren().add(cleanpicture);
        frameSleep.getChildren().add(sleeppicture);
        frameSick.getChildren().add(sickpicture);
        frameEgg.getChildren().add(eggpicture);
        frameBabyhappy.getChildren().add(babyhappypicture);
        frameBabyeat.getChildren().add(babyeatpicture);
        frameBabysad.getChildren().add(babysadpicture);
        frameBabysick.getChildren().add(babysickpicture);
        frameBabyplay.getChildren().add(babyplaypicture);
        frameBabydirty.getChildren().add(babydirtypicture);
        frameBabyhungry.getChildren().add(babyhungrypicture);
        frameBabymedicate.getChildren().add(babymedicatepicture);
        
    }

    public FlowPane getFrameEgg() {
        return frameEgg;
    }

    public FlowPane getFrameSick() {
        if (tamagotchiservice.baby())   {
            return frameBabysick;
        }
        return frameSick;
    }

    public FlowPane getFrameSleep() {
        return frameSleep;
    }

  /*  public void setFrameSleep(FlowPane frameSleep) {
        this.frameSleep = frameSleep;
    } */

    public FlowPane getFrameHappy() {
        if (tamagotchiservice.baby())   {
            return frameBabyhappy;
        }
        return frameHappy;
    }

    public FlowPane getFrameSad() {
        if (tamagotchiservice.baby())   {
            return frameBabysad;
        }
        return frameSad;
    }

    public FlowPane getFrameEat() {
        if (tamagotchiservice.baby())   {
            return frameBabyeat;
        }
        return frameEat;
    }

    public FlowPane getFramePlay() {
         if (tamagotchiservice.baby())   {
            return frameBabyplay;
        }
        return framePlay;
    }

    public FlowPane getFrameDead() {
        return frameDead;
    }

    public FlowPane getFrameMedicate() {
          if (tamagotchiservice.baby())   {
            return frameBabymedicate;
        }
        return frameMedicate;
    }

    public FlowPane getFrameHungry() {
          if (tamagotchiservice.baby())   {
            return frameBabyhungry;
        }
        return frameHungry;
    }

    public FlowPane getFrameDirty() {
         if (tamagotchiservice.baby())   {
            return frameBabydirty;
        }
        return frameDirty;
    }

    public FlowPane getFrameClean() {
        return frameClean;
    }

      public VBox statistics(String name) throws Exception {
         
        CategoryAxis x = new CategoryAxis();
        NumberAxis y = new NumberAxis(0,1000000, 100000);
        BarChart<String, Number> chart = new BarChart<>(x, y);
        

        chart.setTitle("Tamagotchi");
        chart.setLegendVisible(false);
        Tamagotchi t = tamagotchiservice.Tamagotchi();

        XYChart.Series stats = new XYChart.Series();
        stats.getData().add(new XYChart.Data("Hunger", t.getHunger()));
        stats.getData().add(new XYChart.Data("Sadness", t.getSadness())); //
        stats.getData().add(new XYChart.Data("Dirtiness", t.getDirtiness()));
        stats.getData().add(new XYChart.Data("Sick", t.getSick()));
        stats.getData().add(new XYChart.Data("Tiredness", t.getTiredness()));

        chart.getData().add(stats);
        VBox box = new VBox(chart);

        return box;
    }

}
