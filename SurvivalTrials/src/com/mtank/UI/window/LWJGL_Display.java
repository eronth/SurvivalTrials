package com.mtank.UI.window;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowRefreshCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.awt.Color;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWWindowRefreshCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import com.mtank.constants.GraphicColor;
import com.mtank.constants.TypeValue;
import com.mtank.game.mainClass;
import com.mtank.world.World;
  
public class LWJGL_Display {
	
	private float XWindowPadding = 0.95f;
	private float YWindowPadding = 0.90f;
	private double WorldSize = 100.0;
	private World GameWorld;
  
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
  
    	GameWorld = mainClass.island;
    	WorldSize = GameWorld.worldDimension;
    	
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
  
    /***
     * This is the main loop for the display. All logic should go here.
     */
    private void loop() {
        GLContext.createFromCurrent();
  
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        while ( glfwWindowShouldClose(window) == GL_FALSE ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            
            float xStart = -XWindowPadding, yStart = YWindowPadding;
            float blockWidth, blockHeight;
            //blockWidth = (float) (1/(WorldSize*1.5) );
            blockWidth = (float) (1.5/WorldSize);
            //blockHeight = (float) (1/(WorldSize*1.5) );
            blockHeight = (float) (1.5/WorldSize);
            //float xOffset = (float) (blockWidth/0.99), yOffset = (float) (blockHeight/0.99);
            for( int i = 0; i < WorldSize; i++)
            {
            	for( int j = 0; j < WorldSize; j++)
                {
            		switch (GameWorld.world[i][j].landType) {
	        			case TypeValue.Land.SALTWATER:
	        				drawSquare(xStart, yStart, blockWidth, blockHeight, GraphicColor.SALTWATER);
	        				break;
	        			case TypeValue.NONE:
	        				drawSquare(xStart, yStart, blockWidth, blockHeight, GraphicColor.BLANK);
	        				break;
	        			case TypeValue.Land.WATER:
	        				drawSquare(xStart, yStart, blockWidth, blockHeight, GraphicColor.DIRT);
	        				break;
	        			case TypeValue.Land.DIRT:
	        				drawSquare(xStart, yStart, blockWidth, blockHeight, GraphicColor.DIRT);
	        				break;
	        			case TypeValue.Land.SAND:
	        				drawSquare(xStart, yStart, blockWidth, blockHeight, GraphicColor.SAND);
	        				break;
	        			case TypeValue.Land.GRASS:
	        				drawSquare(xStart, yStart, blockWidth, blockHeight, GraphicColor.GRASS);
	        				break;
	        			case TypeValue.Land.STONE:
	        				drawSquare(xStart, yStart, blockWidth, blockHeight, GraphicColor.STONE);
	        				break;
	        			default:
	        				drawSquare(xStart, yStart, blockWidth, blockHeight, GraphicColor.BLANK);
	        				break;
            		}
                	//xStart+=xOffset;
            		xStart+=blockWidth;
                }
            	xStart = -XWindowPadding;
            	//yStart-=yOffset;
            	yStart-=blockHeight;
            }
            
            //GameBoard Loop (Creatures, structures, items)
            xStart = -XWindowPadding;
            yStart = YWindowPadding;
            for( int i = 0; i < WorldSize; i++)
            {
            	for( int j = 0; j < WorldSize; j++)
                {
            		if (GameWorld.world[j][i].creature != null && GameWorld.world[j][i].creature.creatureType != 0) {
    					//Draw creature: Stringify.creature(GameWorld.world[j][i].creature)
            			//Font.drawString(5,5, Stringify.creature(GameWorld.world[j][i].creature), GraphicColor.BLANK);
            			//*** Draw white square as a test.
        				drawSquare(xStart, yStart, blockWidth, blockHeight, GraphicColor.BLANK);
    				} else if (GameWorld.world[j][i].structure != null && GameWorld.world[j][i].structure.structureType != 0) {
    					//Draw structure: Stringify.structure(GameWorld.world[j][i].structure)
    				} else if (GameWorld.world[j][i].item[0] != 0) {
    					//Draw item: Stringify.item(GameWorld.world[j][i].item)
    				}
            		xStart+=blockWidth;
                }
            	xStart = -XWindowPadding;
            	yStart-=blockHeight;
            }
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }
    
    /***
     * Draw a quad using float for X,Y, and Height,Width. Takes in color data in RGB float. Can be used with drawSquare function.
     * @param xStart - Starting float of the screen to draw the square on the x.
     * @param yStart - Starting float of the screen to draw the square on the x.
     * @param blockWidth - The size (float) of the block in the x direction.
     * @param blockHeight - The size (float) of the block in the y direction.
     * @param red - Red color data in the range 0.0-0.1.
     * @param green - Green color data in the range 0.0-0.1.
     * @param blue - Blue color data in the range 0.0-0.1.
     */
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

    /***
     * A helper function to correctly color the square. This changes a GraphicColor to a 3 part float.
     * @param xStart - Starting float of the screen to draw the square on the x.
     * @param yStart - Starting float of the screen to draw the square on the x.
     * @param blockWidth - The size (float) of the block in the x direction.
     * @param blockHeight - The size (float) of the block in the y direction.
     * @param color - What color the block should be (GraphicColor).
     */
    public void drawSquare(float xStart, float yStart, float blockWidth, float blockHeight, Color color){
    	float[] RGB;
    	RGB = new float[3];
    	color.getColorComponents(RGB);
    	drawSolidColoredBlock(xStart, yStart, blockWidth, blockHeight, RGB[0], RGB[1], RGB[2]);
    }
}