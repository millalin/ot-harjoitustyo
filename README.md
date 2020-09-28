# Ohjelmistotekniikka, TamagotchiGame


TamagotchiGame on virtuaalilemmikkpeli. Tamagotchi on lemmikki, jota pitää hoivata. Sitä pitää syöttää, leikittää ja lääkitä säännöllisesti, jottei se menehdy. 
Sovelluksessa voi olla monta samanaikaista tamagotcia, jolloin jokaista pitää käydä hoitamassa erikseen. 

### Dokumentaatio

[Käyttöohje](https://github.com/millalin/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)

[Vaatimusmäärittely](https://github.com/millalin/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/millalin/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Testausdokumentti](https://github.com/millalin/ot-harjoitustyo/blob/master/dokumentaatio/testausdokumentti.md)

[Työaikakirjanpito](https://github.com/millalin/ot-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)


### Releaset

[Viikko 5](https://github.com/millalin/ot-harjoitustyo/releases/tag/v0.2)

[Viikko 6](https://github.com/millalin/ot-harjoitustyo/releases/tag/v0.3)

[Loppupalautus](https://github.com/millalin/ot-harjoitustyo/releases/tag/1.0)

### Komentorivikomennot

#### Testaus

Testit suoritetaan komennolla

    mvn test

Testikattavuusrapotti luodaan komennolla

    mvn jacoco:report

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto target/site/jacoco/index.html

#### Suoritettavan jarin generointi

Komento

    mvn package

generoi hakemistoon target suoritettavan jar-tiedoston TamagotchiGame-1.0-SNAPSHOT.jar

#### JavaDoc

JavaDoc generoidaan komennolla

    mvn javadoc:javadoc

JavaDocia voi tarkastella avaamalla selaimella tiedoston target/site/apidocs/index.html

#### Checkstyle

Tiedostoon [checkstyle.xml](https://github.com/millalin/ot-harjoitustyo/blob/master/TamagotchiGame/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla

    mvn jxr:jxr checkstyle:checkstyle

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto target/site/checkstyle.html


Tehty Java 8. Java 11 käyttöön tarvitsee ladata [JavaFX runtime](https://openjfx.io/openjfx-docs/#install-javafx), asettaa export PATH_TO_FX=path/to/javafx-sdk-VERSION/lib sekä ajaa jar komennolla java --module-path $PATH_TO_FX --add-modules javafx.controls -jar TamagotchiGame.jar 


