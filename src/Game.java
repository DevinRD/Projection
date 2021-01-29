import java.util.ArrayList;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

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
	
	private Triple[] squares = new Triple[] {
			new Triple(-0.5f, -0.5f,  0.1f), new Triple(0.5f, -0.5f,  0.1f), new Triple(0.5f,  0.5f,  0.1f), new Triple(-0.5f, 0.5f, 0.1f)
	};
	
	private Triple[] squareColors = new Triple[] {
			new Triple(0.0f,  1.0f,  1.0f), new Triple(0.0f,  1.0f,  0.0f), new Triple(0.0f,  0.0f,  1.0f), new Triple(1.0f, 0.0f, 0.0f)
	};
	
	private TriangleModel tri = new TriangleModel(tris, triColors);
	private SquareModel square = new SquareModel(squares, squareColors);
	private SquareModel mySquare = new SquareModel(0.0f, 0.0f, 0.0f, 0.25f, 1.0f, 1.0f, 1.0f);
	
	private Shader vertexShader;
	
	private Shader fragmentShader;
	
	public Game() {
		windowWidth = 600;
		windowHeight = 600;
		windowTitle = "Game";
		
		clearColor = new float[] {1.0f, 0.0f, 0.0f};
		
		FPS = 60; UPS = 60;
		
		objects = new ArrayList<Model>();
		objects.add(tri);
		objects.add(square);
		
		try {
			vertexShader = new Shader("./res/shaders/VertexShader.txt");
			fragmentShader = new Shader("./res/shaders/FragmentShader.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (Model m:objects) System.out.println(m);
		
		
		window = new Window(windowWidth, windowHeight, windowTitle);
		window.setClearColor(0.01f, 0.01f, 0.1f, 1.0f);
		window.setShader(vertexShader);
		window.setShader(fragmentShader);
	}
	
	public Game(String mapPath) {
		windowWidth = 600;
		windowHeight = 600;
		windowTitle = "Game";
		
		clearColor = new float[] {1.0f, 0.0f, 0.0f};
		
		FPS = 60; UPS = 60;
		
		objects = new ArrayList<Model>();
		
		try {
			loadMap(mapPath);
			
			vertexShader = new Shader("./res/shaders/VertexShader.txt");
			fragmentShader = new Shader("./res/shaders/FragmentShader.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (Model m:objects) System.out.println(m);
		System.out.println();
		System.out.println();
		
		window = new Window(windowWidth, windowHeight, windowTitle);
		window.setClearColor(0.01f, 0.01f, 0.15f, 1.0f);
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
		
	}
	
	private void render() {
		for (Model m:objects) window.render(m.getVertices(), m.getColors());
		
		window.display();
	}
	
	private void loadMap(String path) throws IOException, FileNotFoundException {
		String line;
		
		BufferedReader map = new BufferedReader(new FileReader(path));
		while ((line = map.readLine()) != null) {
			if (line.equals("triangle")) {
				loadTriangle(map, path);
			}
			else if (line.equals("square")) {
				loadSquare(map, path);
			}
			else {
				objects = new ArrayList<Model>();
				System.out.println("Unable to read " + path + ". Incorrect format?");
			}
		}
		
		map.close();		
	}
	
	private void loadTriangle(BufferedReader map, String path) throws IOException {
		String line;
		
		line = map.readLine();
		String[] nums = line.split(" ");
		
		if (nums != null && nums.length != 9) {
			objects = new ArrayList<Model>();
			System.out.println("Unable to read " + path + ". Incorrect format?");
			return;
		}
		
		float[] vertices = new float[9];
		for (int i = 0; i < nums.length; i++)
			vertices[i] = Float.valueOf(nums[i]);
		
		line = map.readLine();
		nums = line.split(" ");
		
		if (nums != null && nums.length != 9) {
			objects = new ArrayList<Model>();
			System.out.println("Unable to read " + path + ". Incorrect format?");
			return;
		}
		
		float[] colors = new float[9];
		for (int i = 0; i < nums.length; i++)
			colors[i] = Float.valueOf(nums[i]);
		
		objects.add(new TriangleModel(vertices, colors));
	}
	
	private void loadSquare(BufferedReader map, String path) throws IOException {
		String line;
		
		line = map.readLine();
		String[] nums = line.split(" ");
		
		if (nums != null && nums.length != 12) {
			objects = new ArrayList<Model>();
			System.out.println("Unable to read " + path + ". Incorrect format?");
			return;
		}
		
		float[] vertices = new float[12];
		for (int i = 0; i < nums.length; i++)
			vertices[i] = Float.valueOf(nums[i]);
		
		line = map.readLine();
		nums = line.split(" ");
		
		if (nums != null && nums.length != 12) {
			objects = new ArrayList<Model>();
			System.out.println("Unable to read " + path + ". Incorrect format?");
			return;
		}
		
		float[] colors = new float[12];
		for (int i = 0; i < nums.length; i++)
			colors[i] = Float.valueOf(nums[i]);
		
		objects.add(new SquareModel(vertices, colors));
	}
	
	public static void main(String[] args) {
		new Game("./res/maps/map.txt").run();
		new Game().run();
	}

}
