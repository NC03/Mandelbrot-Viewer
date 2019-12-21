all: build pack clean

export msg="Auto Commit from Makefile"

run:
	java -jar MandelbrotViewer.jar

build:
	javac *.java

clean:
	rm *.class

pack:
	jar -cfe MandelbrotViewer.jar MandelbrotViewer *.class

commit:
	git add -A
	git commit -m $(msg)
	git push
