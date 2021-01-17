import java.util.ArrayList;

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
		
		window = new Window(windowWidth, windowHeight, windowTitle);
		window.setClearColor(1.0f, 0.0f, 0.0f, 1.0f);
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
	            if (RENDER_TIME) {
	                System.out.println(String.format("UPS: %s, FPS: %s", ticks, frames));
	            }
	            frames = 0;
	            ticks = 0;
	            timer += 1000;
	        }
		}
	}
	
	private void update() {
		int num = 5; // TODO: delete
	}
	
	private void render() {
		for (Model m:objects) window.render(m.getVertices(), m.getColors());
		
		window.display();
	}
	
	public static void main(String[] args) {
		new Game().run();
	}

}
