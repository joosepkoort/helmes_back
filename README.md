# proovitöö

Projekt koosneb kahest osast:
1. Esirakendusest
2. Tagarakendusest

## Üldine kirjeldus ja tagarakendus
Tagarakendus sisaldab mälus olevat andmebaasi, mis pannakse tööle rakenduse käivitamisel. Seejärel loetakse index.html failist välja puustruktuur ning lisatakse andmebaasi. Hiljem kuvatakse esirakenduses andmebaasi põhjal ülesehitatud puustruktuur, kus on võimalik teha valikuid.
Rakendusse on külge ehitatud ka kasutajate süsteem koos registreerumisvõimalusega. API-endpointid on ligipääsuga turvatud ja midagi üleliigset ei ole avalikkusele saadaval. Kõige esimese sammuna viisin html faili vastavusse W3C standarditega. (lisasin DOCTYPE deklaratsiooni faili algusesse, lisasin puudoleva head alguselemendi ja muutsin kodeeringu windows-1252 pealt UTF-8 peale). Välju valideeritakse nii esirakenduse kui tagarakenduse poolel. Peale seda kasutades JSOUP libraryt, koostasin funktsiooni, mis loeb sisse faili ja koostab select elemendi põhjal puu. Lõpuks lisatakse see andmebaasi kujul: id, parent_id, child_id ja name.

## Esirakendus
Esirakendus on kirjutatud Typescriptis ja kasutab angular raamistikku. Rakendusse on külge ehitatud sisselogimise süsteem ning kasutajal on endale võimalik kasutaja luua. Peale edukat sisselogimist avaneb kasutajale vaade, kus tal on võimalik endale nimi määrata/muuta, sobivad valikud puustruktuurist valida ning tingimustega nõustuda. Peale save nupu valimist saadetakse info tagarakendusse ja andmed salvestatakse selle kasutaja jaoks. Kui sessioon aegub (15 minutit), logitakse kasutaja automaatselt välja ning kasutajale kuvatakse teade väljalogimise kohta.

## Andmebaas
Andmebaas koosneb kolmest tabelist:
- Authorities : sisaldab kasutajarolle nagu näiteks ROLE_USER või ROLE_ADMIN
- tree : sisaldab eelnevalt mainitud puustruktuuri seostena.
- users : sisaldab kasutajate infot, sealhulgas parooli räsikujul. Samuti sisaldab see tabel tehtud puustruktuuri valikut ning kas kasutaja on tingimustega nõustunud. <br>
Täpsemalt on võimalik näha andmebaasi välju juurdelisatud exceli failist.<br>
Struktuuri kohta olen lisanud ka pildi.

## API endpoints:
Samuti on võimalik näha API-endpointe ka aadressilt http://turing.cs.ttu.ee:9998/swagger-ui/index.html

## Olemasolevat kasutajad:
- kasutajanimi: **userhelmes**, password: **userhelmes**
- kasutajanimi: **adminhelmes**, password: **adminhelmes**

## Lehekülg rakendusega:
http://turing.cs.ttu.ee/~jokoor 
