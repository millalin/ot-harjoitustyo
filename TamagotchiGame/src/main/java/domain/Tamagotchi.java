/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *  Tamagotchi virtuaalilemmikkiä edustava luokka
 */

public class Tamagotchi {

    /**
     *
     * @author milla
     */
    private String name;
    private long dateOfBirth;
    private long currentTime;
    private int hunger;
    private int sadness;
    private int tiredness;
    private int dirtiness;
    private boolean alive;
    private int age; //days
    private int sick;
    private String mood;

    public Tamagotchi(String name) {
        
        this.name = name;

        this.hunger = 600000;
        this.sadness = 400000;
        this.tiredness = 200000;
        this.dirtiness = 500000;
        this.alive = true;
        this.age = 0;
        this.sick = 200000;

        this.dateOfBirth = System.currentTimeMillis();
        this.currentTime = 0;
        this.mood = "sad";
    }

    public void setDateOfBirth(long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }


    public  long getCurrentTime() {
        return currentTime;
    }

   

    public int getSick() {
        return sick;
    }

    public void setSick(int sick) {
        this.sick = sick;
    }

    public long getDateOfBirth() {
        return dateOfBirth;
    }

  

    public String getName() {
        return name;
    }


    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
       
        this.hunger = hunger;
    }

    public int getSadness() {
        return sadness;
    }

    public void setSadness(int happiness) {
        this.sadness = happiness;
    }

    public int getTiredness() {
        return tiredness;
    }

    public void setTiredness(int energy) {
        this.tiredness = energy;
    }

    public int getDirtiness() {
        return dirtiness;
    }

    public void setClean(int clean) {
        this.dirtiness = clean;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Palauttaa iän laskettuna päiviksi
     *
     * @return tamagotchin iän päivinä
     */
    
    public int getAge() {
        long a = (System.currentTimeMillis() - this.dateOfBirth) / 1000; //sekkuntia
        int currentAge = (int)a/60/60/24;
        return currentAge;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
