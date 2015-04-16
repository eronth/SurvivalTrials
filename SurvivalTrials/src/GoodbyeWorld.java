import org.lwjgl.Sys;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import java.nio.ByteBuffer;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
  
public class GoodbyeWorld {
  
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
            
            float xStart = -0.9f, yStart = 0.75f;
            float xOffset = 0.15f, yOffset = 0.2f;
            for( int i = 0; i < 10; i++)
            {
            	drawSolidColoredBlock(xStart, yStart, xOffset, yOffset, 0.1f,0.8f,0.1f);
            	xStart+=xOffset+0.005;
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