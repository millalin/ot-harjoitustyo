#Tamagotchi vaativuusmäärittely#

##Sovelluksen tarkoitus##

Tamagotchi on virtuaalilemmikki, joka elää tietokoneen näytöllä. Sitä tulee kuitenkin leikittää, syöttää ja hoitata yhtälailla kuin tavallista lemmikkiä tai se muuttuu surulliseksi, nälkäiseksi tai voi jopa kuolla. Tamagotchipelillä voi virtuaalisesti hoivata ikiomaa lemmikkiä. Sovellukseen on mahdollista luoda eri Tamagotchi eri nimillä. 

##Käyttäjät##
Sovelluksella on normaalikäyttäjä, joka on pelin pelaaja. 

##Käyttöliittymäluonnos##

![luonnos](~/Downloads/luonnos.png)

##Perusversion tarjoama toiminnallisuus##

Mitä toiminnallisuuksia käyttäjän halutaan pystyä tekevän:

Ensimmäisessä näkymässä:
- käyttäjä voi luoda uuden lemmikkitamagotchin ja sille nimen. Nimen tulee olla uniikki ja pituudeltaan vähintään 2 merkkiä ja enintään 20 merkkiä.
- käyttäjä voi syöttää olemassaolevan lemmikin nimen, jolloin sen tila ja näkymä aukeavat näytölle
	- jos syötetyn nimistä tamagothia ei ole olemassa, järjestelmä ilmoittaa tästä

Pelinäkymässä:
- luomisen jälkeen näytölle ilmestyy vauva-vaiheen tamagotchilemmikki
- pelissä on nappeja, joilla toi tehdä eri toimintoja
- käyttäjä voi syöttää tamagotchia
- käyttäjä voi leikittää tamagotchia
- käyttäjä voi siivota tamagochin jätteet
- käyttäjä voi lääkitä tamagotchin, jos se on kipeä
- käyttäjä voi laittaa tamagotchin nukkumaan
- käyttäjä voi herättää tamagotchin
- käyttäjä voi poistua alkunäkymään


Jokaisella ominaisuudella on arvo välillä 0-100 ja jokaiselle ominaisuudelle määritellään, milloin virtuaalilemmikki on missäkin tilassa näiden mukaan.

Jatkoakehitysideoita:
- tamagotchin nälkä lisääntyy, energia vähenee, likaisuus lisääntyy, iloisuus vähenee ajan kuluessa
- tamagotchi kuolee jos elossaolokriteerit alittuvat


Vielä yöhäisempiä jatkokehitysideoita:
-lisäkehitys: mitä isompi sitä enemmän leikkejä/pelejä, voi valita mitä syö
tietokantaan voitaisiin tallentaa kaikki edelliset tamagotchit ja luoda tilastoja

Toimintaympäristön rajoitteet:
- tulee toimia Linux käyttöjärjestelmässä
- tamagotchin tila tallennetaan tietokantaan

