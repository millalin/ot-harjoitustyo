/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.Timestamp;

/**
 *
 * @author milla
 */
public class Tamagotchi {

    /**
     *
     * @author milla
     */
    private String name;
    private int id;
    private long dateOfBirth;
    private long currentTime;
    private int hunger;
    private int happiness;
    private int energy;
    private int clean;
    private boolean alive;
    private int age; //days
    private int sick;

    public Tamagotchi(String name) {
        
        this.name = name;

        this.hunger =700000;
        this.happiness = 500000;
        this.energy = 800000;
        this.clean = 800000;
        this.alive = true;
        this.age = 0;
        this.sick = 200000;

        this.dateOfBirth = System.currentTimeMillis();
        this.currentTime=0;
    }


    public  long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public int isSick() {
        return sick;
    }

    public void setSick(int sick) {
        this.sick = sick;
    }

    public long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getClean() {
        return clean;
    }

    public void setClean(int clean) {
        this.clean = clean;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
