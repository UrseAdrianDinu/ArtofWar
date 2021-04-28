
build: classes permissions
	jar cvmf ./MANIFEST.MF ArtOfWar.jar ./com/chess/*.class
classes:
	javac -d . ./src/com/chess/*.java

permissions:
	chmod 777 ./com/chess/*.class

run:
	chmod 777 ./ArtOfWar.jar
	java -jar ArtOfWar.jar

xboard:
	xboard -fcp "java -jar ArtOfWar.jar" -debug

clean:build
	rm -r ./com
	rm  ./ArtOfWar.jar
