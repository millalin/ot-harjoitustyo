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
    private boolean sick;

    public Tamagotchi(String name) {
        this.id = 1;
        this.name = name;

        this.hunger = 50;
        this.happiness = 50;
        this.energy = 80;
        this.clean = 80;
        this.alive = true;
        this.age = 0;
        this.sick = false;

        this.dateOfBirth = System.currentTimeMillis();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public boolean isSick() {
        return sick;
    }

    public void setSick(boolean sick) {
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

    public void setName(String name) {
        this.name = name;
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
