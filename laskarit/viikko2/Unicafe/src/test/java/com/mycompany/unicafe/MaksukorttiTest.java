package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti != null);
    }

    @Test
    public void kortinSaldoAlussaOikein() {
        String vastaus = kortti.toString();
        assertEquals("saldo: 0.10", vastaus);
    }

    @Test
    public void rahanLataaminenKasvattaaArvoa() {
        kortti.lataaRahaa(10);
        String vastaus = kortti.toString();
        assertEquals("saldo: 0.20", vastaus);
    }

    @Test
    public void saldoEiMuutuJosRahaaEiTarpeeksi() {
        kortti.otaRahaa(100);
        String vastaus = kortti.toString();
        assertTrue(vastaus.equals("saldo: 0.10"));
    }

    @Test
    public void saldoVaheneeOikein() {

        kortti.otaRahaa(10);
        String vastaus = kortti.toString();
        assertTrue(vastaus.equals("saldo: 0.0"));
    }

    @Test
    public void korttiPalauttaaSaldon() {

        int vastaus = kortti.saldo();
        assertTrue(vastaus == 10);
    }
    
     @Test
    public void korttiPalauttaaOikeanSaldon() {
        int saldo=kortti.saldo();
       
        assertEquals(10, saldo);
    }
}
