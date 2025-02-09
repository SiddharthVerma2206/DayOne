package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter {
	public int mouseX , mouseY;
	public boolean pressed;
	
    public void mousePressed(MouseEvent e) {
        pressed = true;
    }
    
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
    
    public void mouseReleased(MouseEvent e) {
    	pressed = false;   	
    }
    
    public void mouseDragged(MouseEvent e) {
      mouseX = e.getX();
      mouseY = e.getY();
    }
    
}
