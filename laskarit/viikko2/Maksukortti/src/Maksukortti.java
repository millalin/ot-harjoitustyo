/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author milla
 */
public class Maksukortti {
    private double arvo;
    private final double EDULLINEN = 2.5;
    private final double MAUKAS = 4.0;
 
    public Maksukortti(double arvoaAlussa) {
        this.arvo = arvoaAlussa;
    }

    public void setArvo(double arvo) {
        this.arvo = arvo;
    }
 
    public void syoEdullisesti() {
        if (this.arvo >= EDULLINEN) {
            this.arvo -= EDULLINEN;
        }
    }
 
    public void syoMaukkaasti() {
        if (this.arvo >= MAUKAS) {
            this.arvo -= MAUKAS;
        }
    }
 
    public void lataaRahaa(double rahamaara) {
        if (rahamaara < 0) {
            return;
        }
 
        this.arvo += rahamaara;
        if (this.arvo > 150) {
            this.arvo = 150;
        }
    }
 
    @Override
    public String toString() {
        return "Kortilla on rahaa " + this.arvo + " euroa";
    }
} 
