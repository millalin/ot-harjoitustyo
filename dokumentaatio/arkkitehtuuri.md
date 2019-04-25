# Arkkitehtuurikuvaus

## Rakenne

Pakkaus ui sisältää javaFX:llä toteutetun käyttöliittymän, pakkaus domain sisältää sovelluslogiikan ja pakkaus dao sisältää tietokannanhallinnasta vastaavat luokat. Database pakkaus sisältää tietokantayhteyden hallinnan. 

## Käyttöliittymä

Käyttöliittymä sisältää neljä erillistä näkymää: 

- alkunäkymä
- pelinäkymä
- kuollut näkymä
- historia ja ikänäkymä

Jokainen näkymistä on toteutettu omana Scene-oliona. Jokaisesta näkymästä on näkyvänä yksi sijoitettuna sovelluksen stageen. Graafinen käyttöliittymä on luokassa ui.TamagotchiGameUi. 

Käyttöliittymä on eriytetty sovelluslogiikasta. Käyttöliittymäluokka TamagotchiGameUi käyttää TamagotchiService-luokan metoden sovelluslogiikan suorittamiseen. 

## Sovelluslogiikka 

Sovelluslogiikan suorittamisesta vastaa TamagotchiService luokka. Dao pakkauksessa olevien luokkien kautta, pääsee TamagotchiService käsiksi tietokannan sisältämiin tietoihin. TamagotchiService tuntee kulloinkin pelissä olevan tamagotchin. 

TamagotchiServicen sekä muiden luokkien ja pakkausten suhdetta kuvaava luokka/pakkauskaavio:

![alt.text](kaavio.png)

## Tietojen pysyväistallennus

Dao pakkauksen luokat vastaavat tamagotchin tietojen tallentamisesta tietokantaan. Tietokanta käyttää kahta tietokantataulua: Tamagotchi ja TamagotchiAges. Ohjelma luo tietokantataulut alussa, jos niitä ei ole olemassa. Sovellus ottaa tietokantayhdeyden aina kun tamagotchi lisätään, poistetaan tai sitä päivitetään. Ohjelman suoritusaikana tietokantaan päivitetään tamagotchin hoitotoiminnot, ajan kulumisen vaikutus päivitetään ohjelmassa tai kun tamagotchi ladataan ohjelman oltua kiinni. Sovelluksen testeissä käytetään testitietokantaa. 


## Päätoiminnallisuudet

### Uuden tamagotchin luominen

Kun alkunäkymä on päällä ja käyttäjä on kirjoittanut tekstikenttään uuden tamagotchin nimen, jota ei ole jo olemassa tietokannassa, etenee uuden tamagotchin luominen seuraavasti: 

![alt.text](sekvenssikaavio.new.png)

### Vanhan tamagotchin hakeminen

Kun alkunäkymä on päällä ja käyttäjä on kirjoittanut hakukenttään tamagotchin nimen, joka on olemassa jo pelissä etenee sovellus seuraavasti:

![alt.text](sekvenssikaavio_gettama.png)

Kun alkunäkymä on päällä ja käyttäjä on kirjoittanut hakukenttään tamagotchin nimen, jota ei ole vielä luotu etenee sovellus seuraavalla tavalla:

![alt.text](sekvenssikaavio.alreadyexists.png)

