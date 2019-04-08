# Ohjelmistotekniikka, TamagotchiGame


Tamagotchi on virtuaalilemmikki, jota pitää hoivata. Sitä pitää syöttää, leikittää ja lääkitä säännöllisesti, jottei se menehdy. 
Sovelluksessa voi olla monta samanaikaista tamagotcia, jolloin jokaista pitää käydä hoitamassa. 


### Dokumentaatio

[Vaatimusmäärittely](https://github.com/millalin/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/millalin/ot-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)

[Arkkitehtuurikuvaus](https://github.com/millalin/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

### Releaset


### Komentorivikomennot

####Testaus

Testit suoritetaan komennolla

    mvn test

Testikattavuusrapotti luodaan komennolla

    mvn jacoco:report


#### Checkstyle

Tiedostoon checkstyle.xml määrittelemät tarkistukset suoritetaan komennolla

    mvn jxr:jxr checkstyle:checkstyle

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto target/site/checkstyle.html



