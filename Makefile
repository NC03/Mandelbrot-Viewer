all: build pack clean

export msg="Auto Commit from Makefile"

run:
	java -jar MandelbrotViewer.jar

build:
	javac source/*.java -d .

clean:
	rm *.class

pack:
	jar -cfe MandelbrotViewer.jar MandelbrotViewer *.class

commit:
	git add -A
	git commit -m "$(msg)"
	git push

doc:
	javadoc source/*.java -d docs/