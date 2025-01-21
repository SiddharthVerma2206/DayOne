package main;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter {
	public int mouseX , mouseY;
	public boolean pressed;
	public Point mousePosition = new Point(0,0);
	
    @Override
    public void mousePressed(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        pressed = true;
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosition = e.getPoint(); // Update mouse position on movement
    }
    
    public void mouseReleased(MouseEvent e) {
    	pressed = false;   	
    }
    
    public Point getMousePosition() {
        return mousePosition; // Return current mouse position
    }
    
}
