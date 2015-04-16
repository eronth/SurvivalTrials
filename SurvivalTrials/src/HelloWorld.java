import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import java.nio.ByteBuffer;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
 
public class HelloWorld {
 
    // We need to strongly reference callback instances.
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback   keyCallback;
 
    // The window handle
    private long window;
    
    private boolean eIsPressed = false;
 
    public void run() {
        System.out.println("Hello LWJGL " + Sys.getVersion() + "!");
 
        try {
            init();
            loop();
 
            // Release window and window callbacks
            glfwDestroyWindow(window);
            keyCallback.release();
        } finally {
            // Terminate GLFW and release the GLFWerrorfun
            glfwTerminate();
            errorCallback.release();
        }
    }
 
    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
 
        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( glfwInit() != GL11.GL_TRUE )
            throw new IllegalStateException("Unable to initialize GLFW");
 
        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable
 
        int WIDTH = 1200;
        int HEIGHT = 900;
 
        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");
 
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                    glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
            }
        });
 
        // Get the resolution of the primary monitor
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
            window,
            (GLFWvidmode.width(vidmode) - WIDTH) / 2,
            (GLFWvidmode.height(vidmode) - HEIGHT) / 2
        );
 
        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);
 
        // Make the window visible
        glfwShowWindow(window);
    }
 
    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the ContextCapabilities instance and makes the OpenGL
        // bindings available for use.
        GLContext.createFromCurrent();
 
        // Set the clear color
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
 
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( glfwWindowShouldClose(window) == GL_FALSE ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            
            ByteBuffer a = BufferUtils.createByteBuffer(8),b = BufferUtils.createByteBuffer(8);
            glfwGetWindowSize(window, a, b);
            if( !a.equals(null) && a.getInt(0) > 0 && !b.equals(null) && b.getInt(0) > 0) {
                System.out.println("A: =" + a.getInt() + " B: =" + b.getInt());
            	glfwSetWindowSize(window, a.getInt(0)-1, b.getInt(0)-1 );
            }
            else {
            	if( a.equals(null) || b.equals(null)) {
                	System.out.println("Null error");
            	}
            	System.out.println("One hit zero");
            }

            int state = glfwGetKey(window, GLFW_KEY_E);
            if(state == GLFW_PRESS && !eIsPressed) {
            	eIsPressed = true;
            	glClearColor(0.0f, 1.0f, 0.0f, 0.0f);
            }
            else if(state == GLFW_RELEASE && eIsPressed ) {
            	glClearColor(0.1f, 0.0f, 0.9f, 0.0f);
            	eIsPressed = false;
            }
            glfwSwapBuffers(window); // swap the color buffers
            
            Draw();
            //Square();
            
 
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }
    void Draw_Square(float red, float green, float blue, int xOff, int yOff)
    {
      // Draws a square with a gradient color at coordinates 0, 10
      glBegin(GL_QUADS);
      {
        glColor3f(red, green, blue);
        glVertex2i(1+xOff, 11+yOff);
        glVertex2i(-1+xOff, 11+yOff);
        glVertex2i(-1+xOff, 9+yOff);
        glVertex2i(1+xOff, 9+yOff);
      }
      glEnd();
    }
    void Draw()
    {
      // reset view matrix
      glLoadIdentity();
      // by repeatedly rotating the view matrix during drawing, the
      // squares end up in a circle
      int i = 0, squares = 15;
      float red = 0, blue = 1;
      int xOff = 0, yOff = 0;
      for (; i < squares; ++i){
    	  xOff += 10;
    	  System.out.println("Made square");
        Draw_Square(red, (float).1, blue, xOff, yOff);
      }
    }
 
    public static void main(String[] args) {
        new HelloWorld().run();
    }
 
}