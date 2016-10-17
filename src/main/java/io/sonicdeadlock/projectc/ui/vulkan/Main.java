package io.sonicdeadlock.projectc.ui.vulkan;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWVidMode;

/**
 * Created by Ben Tam on 10/11/2016.
 */
public class Main implements Runnable{

    private Thread thread;
    public boolean running = true;
    public long window;

    public static void main(String args[]){

        Main game = new Main();
        game.start();
    }

    public void start(){
        running = true;
        thread = new Thread(this, "Game");
        thread.start();
    }
    public void init(){

        if(glfwInit() != true){
            System.err.println("initialization failed");
        }

        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);

        window = glfwCreateWindow(800, 600, "Test", NULL, NULL);

        if(window == NULL){
            System.err.println("could not create window");
        }

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, 100, 100);

        glfwMakeContextCurrent(window);

        glfwShowWindow(window);
    }
    public void update(){
        glfwPollEvents();
    }
    public void render(){
        glfwSwapBuffers(window);
    }

    public void run(){
        init();
        while(running){
            update();
            render();

            if(glfwWindowShouldClose(window) == true){
                running = false;
            }
        }
    }
}
