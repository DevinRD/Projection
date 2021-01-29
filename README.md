# Projection

In order to compile and run without ide

1. clone repository
2. move into repository
3. create directory ./lib
4. create directory ./lib./lwjgl
5. put lwjgl.jar, lwjgl-glfw.jar, and lwjgl-opengl.jar along with their native jar files in ./lib/lwjgl
7. run from root repository directory to compile: 'javac -cp ./:./src:./lib/lwjgl/lwjgl.jar:./lib/lwjgl/lwjgl-glfw.jar:./lib/lwjgl/lwjgl-opengl.jar src/Game.java'
8. run from root repository directory to run: 'java -cp ./:./src:./bin:./lib/lwjgl/lwjgl.jar:./lib/lwjgl/lwjgl-glfw.jar:./lib/lwjgl/lwjgl-glfw-natives-linux.jar:./lib/lwjgl/lwjgl-natives-linux.jar:./lib/lwjgl/lwjgl-opengl.jar:./lib/lwjgl/lwjgl-opengl-natives-linux.jar -Djava.library.path=./lib/lwjgl Game'

Note: Mac users may need to add the -XstartOnFirstThread flag when running.
