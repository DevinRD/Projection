import java.util.ArrayList;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

public class Game {
	
	private int windowWidth, windowHeight;
	private String windowTitle;
	
	private float[] clearColor;
	
	private int UPS, FPS;
	
	private Window window;
	
	private boolean RENDER_TIME = false;
	
	private ArrayList<Model> objects;
	
	private Triple[] tris = new Triple[] {
			new Triple(-0.5f, -0.5f,  0.0f), new Triple(0.5f, -0.5f,  0.0f), new Triple(0.0f,  0.5f,  0.0f)
	};
	
	private Triple[] triColors = new Triple[] {
			new Triple(0.0f,  1.0f,  1.0f), new Triple(0.0f,  1.0f,  0.0f), new Triple(0.0f,  0.0f,  1.0f)
	};
	
	private TriangleModel tri = new TriangleModel(tris, triColors);
	
	private Shader vertexShader = new Shader(
			"vertex",
			
			"#version 330 core\n" +
			"layout (location = 0 ) in vec3 vertexPosition;\n" +
			"layout (location = 1 ) in vec3 vertexColor;\n" +
			"out vec3 color;\n" +
			"void main(void)\n" +
			"{\n" +
			"  color = vertexColor;\n" +
			"  gl_Position = vec4( vertexPosition, 1.0);\n" +
			"}\n"
	);
	
	private Shader fragmentShader = new Shader(
			"fragment",
			
			"#version 330 core\n" +
			"in vec3 color;\n" +
			"layout (location = 0 ) out vec4 fragColor;\n" +
			"void main(void)\n" +
			"{\n" +
			"  fragColor = vec4(color, 1.0 );\n" +
			"}\n"
	);
	
	public Game() {
		windowWidth = 500;
		windowHeight = 500;
		windowTitle = "Game";
		
		clearColor = new float[3];
		clearColor[0] = 1.0f;
		clearColor[1] = 0.0f;
		clearColor[2] = 0.0f;
		
		FPS = 60; UPS = 60;
		
		objects = new ArrayList<Model>();
		objects.add(tri);
		System.out.println(tri);
		
		window = new Window(windowWidth, windowHeight, windowTitle);
		window.setClearColor(1.0f, 0.0f, 0.0f, 1.0f);
		window.setShader(vertexShader);
		window.setShader(fragmentShader);
	}
	
	public Game(String mapPath) {
		windowWidth = 500;
		windowHeight = 500;
		windowTitle = "Game";
		
		clearColor = new float[3];
		clearColor[0] = 1.0f;
		clearColor[1] = 0.0f;
		clearColor[2] = 0.0f;
		
		FPS = 60; UPS = 60;
		
		objects = new ArrayList<Model>();
		loadMap(mapPath);
		
		window = new Window(windowWidth, windowHeight, windowTitle);
		window.setClearColor(1.0f, 0.0f, 0.0f, 1.0f);
		window.setShader(vertexShader);
		window.setShader(fragmentShader);
	}
	
	private void run() {
		long initialTime = System.nanoTime();
		final double timeU = 1000000000 / UPS;
		final double timeF = 1000000000 / FPS;
		double deltaU = 0, deltaF = 0;
		int frames = 0, ticks = 0;
		long timer = System.currentTimeMillis();
		
		while (window.open()) {
			
	        long currentTime = System.nanoTime();
	        deltaU += (currentTime - initialTime) / timeU;
	        deltaF += (currentTime - initialTime) / timeF;
	        initialTime = currentTime;

	        if (deltaU >= 1) {
	            update();
	            ticks++;
	            deltaU--;
	        }

	        if (deltaF >= 1) {
	            render();
	            frames++;
	            deltaF--;
	        }

	        if (System.currentTimeMillis() - timer > 1000) {
	            if (RENDER_TIME)
	                System.out.println(String.format("UPS: %s, FPS: %s", ticks, frames));
	            frames = 0;
	            ticks = 0;
	            timer += 1000;
	        }
		}
		
		window.close();
	}
	
	private void update() {
		int num = 5; // TODO: delete
	}
	
	private void render() {
		for (Model m:objects) window.render(m.getVertices(), m.getColors());
		
		window.display();
	}
	
	private void loadMap(String path) {
		String line;
		
		try {
			BufferedReader map = new BufferedReader(new FileReader(path));
			while ((line = map.readLine()) != null) {
				if (line.equals("triangle")) {
					line = map.readLine();
					String[] nums = line.split(" ");
					
					if (nums != null && nums.length != 9) {
						objects = new ArrayList<Model>();
						System.out.println("Unable to read " + path + ". Incorrect format?");
					}
					
					float[] vertices = new float[9];
					for (int i = 0; i < nums.length; i++)
						vertices[i] = Float.valueOf(nums[i]);
					
					line = map.readLine();
					nums = line.split(" ");
					
					if (nums != null && nums.length != 9) {
						objects = new ArrayList<Model>();
						System.out.println("Unable to read " + path + ". Incorrect format?");
					}
					
					float[] colors = new float[9];
					for (int i = 0; i < nums.length; i++)
						colors[i] = Float.valueOf(nums[i]);
					
					TriangleModel m = new TriangleModel(vertices, colors);
					System.out.println(m);
					objects.add(m);
				}
				else {
					objects = new ArrayList<Model>();
					System.out.println("Unable to read " + path + ". Incorrect format?");
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println(path + " was not found.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Game("./res/maps/map.txt").run();
		//new Game().run();
	}

}
