1. Mine kodulehele ja ava link, et laadida alla projekti genereerimis tööriist
https://libgdx.com/dev/project-generation/
lae alla
2. jooksuta terminalis järgnev käsklus
java -jar ./gdx-setup_latest.jar
(vaata, et oleksid samas kaustas, kus on alla laetud jar file)
3. android interface ei ole vajalik, kuid vali kõik main extensionid, võid teha eraldi kausta kuhu talletad projekti kausta. Peaasi, et tead kus see asub sinu arvutis.
4. Klooni masteri repo alla ja ava intellijga 
5. intellij peaks uuel projektil ise ka soovitama paar setingut muuta luba, kuid ilmselt pead manuaalselt seda tegema. 
jooksutamiseks vaja projekti compiler panna 11 asemel 15 peale 
ava File|Project settings| seal olev project SDK vaheta 15 peale ilmselt pead selle jaoks valima add valiku ja alla laadima
6. buildima peab desktop launcher faili aga muuda tema configuratsioone.
build kõrval vajuta DEsktopLauncher peale ja vali edit configurations. Seal vaheta working directory selleks kaustaks kuhu 3. punktis salvestasid oma projekti enginei NB (tal on vaja ligipääsu /core/assets kaustani)
7. buildi ja jooksuta (kui ei lähe läbi proovi jdk vahetada 14 peale, see sõltub gradlei variandist)
