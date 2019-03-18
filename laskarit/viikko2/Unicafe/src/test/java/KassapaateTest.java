/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mycompany.unicafe.Kassapaate;
import com.mycompany.unicafe.Maksukortti;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author milla
 */
public class KassapaateTest {

    Kassapaate paate;
    Maksukortti kortti;

    public KassapaateTest() {
        paate = new Kassapaate();
        kortti = new Maksukortti(500);
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void luodunKassapaatteenSaldoOikein() {

        int kassassaRahaa = paate.kassassaRahaa();
        assertEquals(100000, kassassaRahaa);
    }

    @Test
    public void luodunKassapaatteenMyytyjenLounaidenMaaraOikea() {

        int myytyjaLounaita = paate.edullisiaLounaitaMyyty() + paate.maukkaitaLounaitaMyyty();
        assertEquals(0, myytyjaLounaita);
    }

    @Test
    public void vaihtorahaOikeinEdullisetKunMaksuRiittava() {

        int vaihtoraha = paate.syoEdullisesti(400);
        assertEquals(160, vaihtoraha);
    }

    @Test
    public void kassaOikeinEdullisetKunMaksuRiittava() {

        paate.syoEdullisesti(400);
        int kassassaRahaa = paate.kassassaRahaa();
        assertEquals(100240, kassassaRahaa);
    }

    @Test
    public void vaihtorahaOikeinMaukkaatKunMaksuRiittava() {

        int vaihtoraha = paate.syoMaukkaasti(500);
        assertEquals(100, vaihtoraha);
    }

    @Test
    public void kassaOikeinMaukkaatKunMaksuRiittava() {

        paate.syoMaukkaasti(400);
        int kassassaRahaa = paate.kassassaRahaa();
        assertEquals(100400, kassassaRahaa);
    }

    @Test
    public void vaihtorahaOikeinEdullisetKunMaksuEIRiittava() {

        int vaihtoraha = paate.syoEdullisesti(200);
        assertEquals(200, vaihtoraha);
    }

    @Test
    public void kassaOikeinEdullisetKunMaksuEiRiittava() {

        paate.syoEdullisesti(200);
        int kassassaRahaa = paate.kassassaRahaa();
        assertEquals(100000, kassassaRahaa);
    }

    @Test
    public void vaihtorahaOikeinMaukkaatKunMaksuEiRiittava() {

        int vaihtoraha = paate.syoMaukkaasti(300);
        assertEquals(300, vaihtoraha);
    }

    @Test
    public void kassaOikeinMaukkaatKunMaksuEiRiittava() {

        paate.syoMaukkaasti(300);
        int kassassaRahaa = paate.kassassaRahaa();
        assertEquals(100000, kassassaRahaa);
    }

    @Test
    public void lounasmaaraKasvaaEdullisetKunMaksuRiittava() {
        paate.syoEdullisesti(300);
        int myydyt = paate.edullisiaLounaitaMyyty();
        assertEquals(1, myydyt);
    }

    @Test
    public void lounasmaaraKasvaaMaukkaatKunMaksuRiittava() {

        paate.syoMaukkaasti(400);
        int myydyt = paate.maukkaitaLounaitaMyyty();
        assertEquals(1, myydyt);
    }

    @Test
    public void lounasmaaraEiKasvaEdullisetKunMaksuEiRiittava() {
        paate.syoEdullisesti(200);
        int myydyt = paate.edullisiaLounaitaMyyty();
        assertEquals(0, myydyt);
    }

    @Test
    public void lounasmaaraEiKasvaMaukkaatKunMaksuEiRiittava() {

        paate.syoMaukkaasti(300);
        int myydyt = paate.maukkaitaLounaitaMyyty();
        assertEquals(0, myydyt);
    }

    @Test
    public void veloitusToimiiEdulliset() {

        boolean tosi = paate.syoEdullisesti(kortti);
        assertTrue(tosi == true);
    }

    @Test
    public void lounasmaaraKasvaaKunKortillaRahaaEdulliset() {

        paate.syoEdullisesti(kortti);
        int myydyt = paate.edullisiaLounaitaMyyty();
        assertEquals(1, myydyt);
    }

    @Test
    public void veloitusToimiiMaukkaat() {

        boolean tosi = paate.syoMaukkaasti(kortti);
        assertTrue(tosi == true);
    }

    @Test
    public void lounasmaaraKasvaaKunKortillaRahaaMaukkaat() {

        paate.syoMaukkaasti(kortti);
        int myydyt = paate.maukkaitaLounaitaMyyty();
        assertEquals(1, myydyt);
    }

    @Test
    public void veloitusEiVeloitaJosEiRahaaEdulliset() {
        paate.syoEdullisesti(kortti);
        paate.syoEdullisesti(kortti);
        boolean tosi = paate.syoEdullisesti(kortti);
        assertTrue(tosi == false);
    }

    @Test
    public void lounasmaaraEiKasvaKunKortillaEiRahaaEdulliset() {
        paate.syoEdullisesti(kortti);
        paate.syoEdullisesti(kortti); //kahteen ensimmäiseen on rahaa
        paate.syoEdullisesti(kortti);
        int myydyt = paate.edullisiaLounaitaMyyty();
        assertEquals(2, myydyt);
    }

    @Test
    public void veloitusEiVeloitaJosEiRahaaMaukkaat() {
        paate.syoMaukkaasti(kortti);
        boolean tosi = paate.syoMaukkaasti(kortti);
        assertTrue(tosi == false);
    }

    @Test
    public void lounasmaaraEiKasvaKunKortillaEiRahaaMaukkaat() {
        paate.syoMaukkaasti(kortti); //ensimmäiseen on rahaa
        paate.syoMaukkaasti(kortti);
        int myydyt = paate.maukkaitaLounaitaMyyty();
        assertEquals(1, myydyt);
    }

    @Test
    public void kassanRahamaaraEiMuutuKorttiostossaEdulliset() {
        paate.syoEdullisesti(kortti);
        int kassa = paate.kassassaRahaa();
        assertEquals(100000, kassa);
    }

    @Test
    public void kassanRahamaaraEiMuutuKorttiostossaMaukkaat() {
        paate.syoMaukkaasti(kortti);
        int kassa = paate.kassassaRahaa();
        assertEquals(100000, kassa);
    }

    @Test
    public void korttilatauksessaKorttiSaldoMuuttuu() {
        paate.lataaRahaaKortille(kortti, 100);
        int summa = kortti.saldo();
        assertEquals(600, summa);
    }

    @Test
    public void korttilatauksessaKassaSaldoMuuttuu() {
        paate.lataaRahaaKortille(kortti, 100);
        int summa = paate.kassassaRahaa();
        assertEquals(100100, summa);
    }

     @Test
    public void korttilatauksessaKassaSaldoEiMuuttuJosNeg() {
        paate.lataaRahaaKortille(kortti, -100);
        int summa = paate.kassassaRahaa();
        assertEquals(100000, summa);
    }
}
