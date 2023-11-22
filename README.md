# Gausss
DOG filter – Gauss szűrő különbsége
Specifikáció - Kocsis Dávid

1. Specifikáció
1.Feladat rövid ismertetése:
	A Difference of Gauss szűrő egy olyan algoritmus amely hangok (1D) és képek (2D) zajszűrésére, illetve csakis képeknél éldetektálásra is szolgál, bár a LoG alkalmasabb az utóbbira és ez a folyamat inkább formákat emel ki az eredeti képből. A DOG két különböző súlyú Gaussian torzítást használ a kiválasztott képre, majd a (raszterenkénti/pixelenkénti) különbségük lesz az algoritmus vége. Maga az algoritmus alkalmazásakor már rengeteg bővítést alkalmaznak változók és egyéb műveletek szempontjából, de a lényegük főleg az egyéb zajok kiszűrésére és az élek felerősítésére szolgálnak.
 (Egy rövid péda ezekre a kiegészítésekre: https://www.youtube.com/watch?v=5EuYKEvugLU&t=165s)
Maga a Gaussian torzítás kétdimenzióban egy Gauss-függvény értékeket tartalmazó FIR-kernellel konvolválunk(két függvényt vagy jellemzőt kombinálunk, hogy új eredményt hozzunk létre). 
Két lépésre bonjuk a folyamatot:
1.egy egydimenziós kernelt használunk a kép elhomályosításához csak vízszintesen vagy függőlegesen. 
2.Ugyanazt az egydimenziós kernelt használjuk a maradék irányban történő elhomályosításhoz. 
Az eredményezett hatás ugyanaz, mint egy kétdimenziós kernellel történő konvolúció egyetlen lépésben, de kevesebb számítást igényel.
Az alkalmazott gauss függvény:  (ahol a súly általunk választott)
A feladatom ezt a komplex algoritmust alkalmazni képekre, egy felhasználói grafikus felülettel egybevonva, amelyen képeket lehet betölteni, súlyokat megadni majd kirajzolni a torzított vagy az algoritmus által már differenciált képet.

Példa az alábbi feladatra: Difference of Gaussians Edge Enhancement Algorithm - Java Tutorial | Olympus LS

2.	Use-case-ek felsorolása és kifejtése
Elsősorban, ha csak a DoG-ot implementálom, akkor az elképzelés az, hogy a felhasználó A főpanelen állíthatja majd be az algoritmushoz kellő változókat, és külön képernyőkőn meglehet jeleníteni gombokkal az eredeti képet, illetve az algoritmus által elkészített, torzított vagy a végigfutott, szűrt képet.

A főképernyőn:
•	Comboboxal vagy textfildel megadhatjuk az algoritmus alkalmazására használt képet
•	Egy textfildben vagy hasonló száminputokra alkalmas objekten megadhatóak lesznek a torzításaink súlyai 
•	Egy fordító gomb foglya tesztelni, hogy alkalmasak voltak e a megadott változók és hogy sikeresen lefutott e az algoritmus, ha hiba van azt felugró ablakkal jelzi.
•	Ha sikeresen lefutott az algoritmus, gombok segítségével megjeleníthető lesz a két torzított változata is a képnek, az eredeti megjeleníthető már az eleje óta gomb segítségével, és a végső kép is (ha bármely kép nem létezik, azt felugró ablakkal jelzi).
•	(Még plusz funkciókét az egydimenziós változatát a függvénynek is előállítom amelyből látható lesz a differencia, ennek elérése is egy új panel előjövetelével a lefutás gomb lenyomása után).
3.	Megoldási ötlet vázlatos ismertetése, amely többek között felsorolja az alkalmazott technológiai megoldásokat, bemtatja a tervezett fájlformátumokat, stb. Kerüljük a "majd kitalálom implementáció közben" jellegű specifikációt.
-Cell osztály: pixelek intenzivitását tárolja el illetve a segédértékeket, melyek az algoritmus többszöri lefutásához szükséges.
-Raszter osztály: a képek raszterpixeleit tárolja el egy tömbben illetve a képek hosszát, szélességét, a képek beolvasásával, elmentésével kapcsolatos függvényeket tartalmazza.
-Gauss osztály a kerner kiszámítását, a képek torzítását és a torzított képek különbségének előállítását végzi.
-Panel osztály a JFrame leszármazottja amely majd a grafikus felületért szolgál. 
	Egyéb osztályok még létrejöhetnek a projekt során például egy kerner osztály de ezek majd csak ha szükségesnek bizonyulnak csak akkor kerülnek bele a programba.

A fileformátumok elsősorban képformátumok lesznek bemenetre és kimenetre mint például png mivel képfeldolgozó programról beszélünk.
A képek feldolgozására BufferedImage osztály alkalmazása, Bufferedbyte, vagy akár bytemap alkalmazására lehet szükség de egy float tömb is megteszi. 
2. Dokumentáció
1.Osztálydiagram:
 

 
2.Függvények
Raster osztály
1.	set(int x, int y, float z): Ez a metódus egy adott pozícióra beállít egy Cell objektumot a Raster képében. Ellenőrzi, hogy az adott pozíció a kép határain belül van-e, és ha igen, létrehoz egy új Cell objektumot a megadott z értékkel és 0.0f második paraméterrel.
2.	get(int x, int y): Ez a metódus egy adott pozícióból visszaadja a megfelelő Cell objektumot a Raster képéből. Szintén ellenőrzi, hogy a megadott pozíció a kép határain belül van-e, és ha igen, visszaadja a megfelelő Cell objektumot. Ha a pozíció érvénytelen, a metódus null értékkel tér vissza.
3.	Raster(BufferedImage original): Ez a konstruktor egy BufferedImage objektumból inicializál egy Raster objektumot. A kép méretét beállítja a BufferedImage méretére, majd minden pixelt átmásol a BufferedImage-ből egy új Cell objektumba, és azokat beállítja a Raster-ben lévő megfelelő helyre. A getSample metódussal kéri le, hogy az adott pixelen 0-255 intervallumon mekkora a grayscale intenzitása.
4.	toImage(Raster raster): Ez a statikus metódus egy Raster objektumból létrehoz és ad vissza egy BufferedImage objektumot. A kép méretét a Raster méretére állítja be, majd minden pixelt átmásol a Raster-ből a megfelelő helyre a BufferedImage objektumban. A kép típusát BufferedImage.TYPE_BYTE_GRAY-re állítja be.
Ezen metódusok révén a Raster osztály lehetővé teszi a képi adatok kezelését, beleértve a kép létrehozását BufferedImage objektumból, a pixelek beállítását és lekérdezését, valamint a Raster objektumból kép létrehozását.
BufferFunctions osztály
1.	toGrayScale(BufferedImage img): Ez a statikus metódus egy BufferedImage-t konvertál szürkeárnyalatos képpé. Létrehoz egy új BufferedImage objektumot a megadott kép méretével, de csak egyetlen szürkeárnyalatos csatornával (TYPE_BYTE_GRAY). A képet a Graphics objektum segítségével másolja át az új képbe.
2.	loadImage(File path): Ez a statikus metódus egy File objektumból beolvas egy képet (BufferedImage-t) a ImageIO segítségével. Amennyiben a kép beolvasása nem sikerül, kivételt dob.
3.	reSizer(BufferedImage img): Ez a statikus metódus átméretezi a megadott képet a megadott küszöbértékek szerint. Ha a kép mérete kisebb, mint 600x600 pixel, akkor visszaadja az eredeti képet. Különben kiszámolja a méretarányt (sigm), majd az új méretekkel létrehoz egy új BufferedImage objektumot és átmásolja a képet az új méretekkel. A skálázás során az Image.SCALE_SMOOTH opcióval sima skálázást alkalmaz.
DifferenceOfGaussian osztály
1.	CannyEdge(Raster img): Ez a statikus metódus a Canny éldetektálás algoritmusát valósítja meg a kapott Raster objektumon. A kép pixelein végigiterál, számítja az élszűrőt mindkét tengelyre, majd kiszámolja a gradienst és skálázza az élek intenzitását. Végül a módosított értékeket beállítja a kép megfelelő pixeleibe.
2.	GaussBlur(Raster img, int weight): Ez a statikus metódus gaussi életsimítást végez a kapott Raster objektumon. A súlyozott szűrőkoefficiensekkel elvégzi a kép 1D-s oszlop és sor szűrését. A weight paraméter szabályozza a simítás mértékét.
3.	DifferenceOfBruls(Raster blur1, Raster blur2): Ez a statikus metódus két életsimított kép közötti különbség képét hozza létre. A két kép pixeleinek értékeiből kivonja egymást, majd hozzáadja 128-at, hogy az értékek pozitívak legyenek.
4.	GaussBlur2(Raster img, int weight): Hasonlóan a GaussBlur metódushoz, de itt az eredményt egy másik adattagba, a z értékek helyett a s értékekbe tárolja.
Ezen metódusok kombinációjával az osztály különböző képmanipulációs eljárásokat valósít meg, beleértve az éldetektálást, gaussi életsimítást és a két kép közötti különbség számítását.
3.Felhasználói kézikönyv
 Főpanel:
	Bal szélső comboboxal képes a felhasználó a formálni kívánt képet. Ez meg is jeleni azonnal a felette lévő ikon felületen.
	A box mellett elhelyezkedő két textfildben adhatjuk meg a súlyát (egyben a kerner nagyságát) a gauss-blur képletéhez. 
	Emellett még megtalálható az eddigi futtatások adatai a textfieldek után egy comboboxban.
	A megjelenített kép mellett található gomb pedig a képmanipulációs algorimusok futtatására szolgál, ezáltal megnyílik egy képekkel teli panel a módosított képekkel.
Képek panel:
	4 gombbal találkozik a felhasználó és 4 képpel, az első kép az első súly szerinti blur az eredeti képről, a második a második súly szerint készült blur, a harmadik a két blurred kép differenciája, illetve a 4. kép a canny edge detection metódus által készült kép. A gombok a megfelelő kép mentésére szolgál, melyet az outpics mappába ment el.
