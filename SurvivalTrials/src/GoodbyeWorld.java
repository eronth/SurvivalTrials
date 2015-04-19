import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
  
public class GoodbyeWorld {
	
	private float XWindowPadding = 0.95f;
	private float YWindowPadding = 0.90f;
	private double WorldSize = 50.0;
  
    private long window;
  
    public void execute() {
        Sys.getVersion();
  
        try {
            init();
            loop();
            glfwDestroyWindow(window);
        } finally {
            glfwTerminate();
        }
    }
  
    private void init() {
  
        if ( glfwInit() != GL11.GL_TRUE )
            throw new IllegalStateException("Unable to initialize GLFW");
  
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
  
        int WIDTH = 1024;
        int HEIGHT = 768;
  
        window = glfwCreateWindow(WIDTH, HEIGHT, "Game", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");
        
        @SuppressWarnings("unused")
		GLFWKeyCallback escapekeyCallback;
		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, escapekeyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                    glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
            }
        });
        
        @SuppressWarnings("unused")
		GLFWWindowRefreshCallback refreshCallback;
        glfwSetWindowRefreshCallback(window, refreshCallback = new GLFWWindowRefreshCallback(){
			@Override
			public void invoke(long window) {
				// TODO Auto-generated method stub
				ByteBuffer a = BufferUtils.createByteBuffer(8),b = BufferUtils.createByteBuffer(8);
	            glfwGetWindowSize(window, a, b);
	            GL11.glViewport(0, 0, a.getInt(), b.getInt());
			}
        });
 
  
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(
            window,
            (GLFWvidmode.width(vidmode) - WIDTH) / 2,
            (GLFWvidmode.height(vidmode) - HEIGHT) / 2
        );
  
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
  
        glfwShowWindow(window);
    }
  
    private void loop() {
        GLContext.createFromCurrent();
  
        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        while ( glfwWindowShouldClose(window) == GL_FALSE ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            
            float xStart = -XWindowPadding, yStart = YWindowPadding;
            float blockWidth, blockHeight;
            blockWidth = (float) (1/WorldSize*1.5);
            blockHeight = (float) (1/WorldSize*1.5);
            float xOffset = (float) (blockWidth/0.95), yOffset = (float) (blockHeight/0.95);
            for( int i = 0; i < WorldSize; i++)
            {
            	for( int j = 0; j < WorldSize; j++)
                {
            		int randomNum = 0 + (int)(Math.random()*6);
                	//if(j%2 == 0)
            		if(randomNum <= 4)
                		//drawSolidColoredBlock(xStart, yStart, xOffset, yOffset, 0.1f,0.8f,0.1f);
                		drawSolidColoredBlock(xStart, yStart, blockWidth, blockHeight, 0.1f,0.2f,0.8f);
            		else if(randomNum == 5)
                		//drawSolidColoredBlock(xStart, yStart, xOffset, yOffset, 0.1f,0.8f,0.1f);
                		drawSolidColoredBlock(xStart, yStart, blockWidth, blockHeight, 0.1f,0.8f,0.1f);
                	else
                		//drawSolidColoredBlock(xStart, yStart, xOffset, yOffset, 0.7f,0.8f,0.1f);
            			drawSolidColoredBlock(xStart, yStart, blockWidth, blockHeight, 0.7f,0.8f,0.1f);
                	xStart+=xOffset;
                }
            	xStart = -XWindowPadding;
            	yStart-=yOffset;
            }
             
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }
    
    public static void drawSolidColoredBlock(float x, float y, float width, float height, float red, float green, float blue) {
    	  //set the color using the 3 float values that we passed in
    	  glColor3f(red, green, blue);

    	  //Begin drawing the square with the assigned coordinates and size
    	  glBegin(GL_QUADS);
    	   glVertex2f(x, y);//bottom left of the square
    	   glVertex2f(x + width, y);//bottom right of the square
    	   glVertex2f(x + width, y + height);//top right of the square
    	   glVertex2f(x, y + height);//top left of the square
    	  glEnd();
    	  //Finish drawing the square
    	}
  
    public static void main(String[] args) {
        new GoodbyeWorld().execute();
    }
}