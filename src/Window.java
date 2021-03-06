import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.lang.reflect.Array;
import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {

	// The window handle
	private long window;
	
	private int p;
	private int vao;
	
	private int positionHandler;
	private int colorHandler;
	
	private FloatBuffer positionsBuffer;
	private FloatBuffer colorsBuffer;
	
	private int windowWidth, windowHeight;
	
	private String windowTitle;
	
	// auxiliary variable to mark where the place in a buffer
	private int marker; 
	
	
	// private float[] positions;
	
	// private float[] colors;
	
	private final int MAXTRIS = 10;
	
	public Window(int windowWidth, int windowHeight, String windowTitle) {
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		this.windowTitle = windowTitle;
		
		// positions = new float[MAXTRIS * 3 * 3]; // triangles * coordinates * vertices
		// colors = new float[MAXTRIS * 3 * 3]; // triangles * rgb * vertices
		
				
		init();
	}

	private void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
	    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
	    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
	    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
	    
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow(windowWidth, windowHeight, windowTitle, NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
		
		// This line is critical for LWJGL's interoperation with GLFW's
		GL.createCapabilities();
		
		openGlInit();
	}
	
	private void openGlInit() {
		p = GL20.glCreateProgram();
		GL20.glLinkProgram(p);
		GL20.glUseProgram(p);
		
		vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		
		positionHandler = GL15.glGenBuffers();
		colorHandler = GL15.glGenBuffers();
		
		positionsBuffer = FloatBufferUtil.createFloatBuffer(MAXTRIS * 3 * 3);// triangles * coordinates * vertices
		colorsBuffer = FloatBufferUtil.createFloatBuffer(MAXTRIS * 3 * 3); // triangles * rgb * vertices
		
		
		GL11.glEnable( GL11.GL_DEPTH_TEST );
		GL11.glClearDepth( 1.0f );
		GL11.glDepthFunc( GL11.GL_LESS );
		
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	}
	
	public boolean open() {
		return !glfwWindowShouldClose(window);
	}
	
	public void render(float[] vertices, float[] colors) {
		FloatBufferUtil.putArray(vertices, positionsBuffer);
		FloatBufferUtil.putArray(colors, colorsBuffer);
	}
	
	public void render(Model model) {
		FloatBufferUtil.putArray(model.getVertices(), positionsBuffer);
		FloatBufferUtil.putArray(model.getColors(), colorsBuffer);
	}

	public void display() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
		
		
		// FloatBufferUtil.sendArrayToBuffer(positions, positionsBuffer);
		// FloatBufferUtil.sendArrayToBuffer(colors, colorsBuffer);
		marker = positionsBuffer.position();
		positionsBuffer.rewind();
		colorsBuffer.rewind();
		
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionHandler);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, positionsBuffer, GL15.GL_STATIC_DRAW);
		GL20.glEnableVertexAttribArray(0);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorHandler);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colorsBuffer, GL15.GL_STATIC_DRAW);
		GL20.glEnableVertexAttribArray(1);
		GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 0, 0);
		
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, marker/3); // should never round
		
		
		glfwSwapBuffers(window); // swap the color buffers

		// Poll for window events. The key callback above will only be
		// invoked during this call.
		glfwPollEvents();
	}
	
	public void close() {
		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	
	public void setClearColor(float r, float g, float b, float a) {
		glClearColor(r, g, b, a);
	}
	
	public void setShader(Shader shader) {
		int handle;
		
		if (shader.getType().equals("vertex")) {
			handle = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		}
		else if(shader.getType().equals("fragment")) {
			handle = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		}
		else {
			throw new IllegalArgumentException();
		}
		
		GL20.glShaderSource(handle, shader.getCode());
		GL20.glCompileShader(handle);
		
		GL20.glAttachShader(p, handle);
		
		GL20.glLinkProgram(p);
		GL20.glUseProgram(p);
	}

}
