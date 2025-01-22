package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Enemy extends Entity{

	BufferedImage image = null;
	public Enemy(GamePanel gp , double spawnX , double spawnY) {
		super(gp);
		worldX =(int)spawnX;
		worldY =(int)spawnY;
		speed = 2;
		radius = 20;
		health = 2;
		damage = 1;
		image = getScaledImage("/player/guy_down_1");
	}
	
	public void move() {
		// Calculate the distance and direction to the player
	    double deltaX = gp.player.worldX - worldX;
	    double deltaY = gp.player.worldY - worldY;
	    double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

	    // Ensure the enemy moves only if the distance is greater than a small threshold
	    if (distance > 20) { // Avoid division by zero
	        double normalizedX = deltaX / distance;
	        double normalizedY = deltaY / distance;

	        // Move the enemy consistently at the given speed
	        worldX += normalizedX * speed;
	        worldY += normalizedY * speed;
	    }
	}
	
	public void draw(Graphics2D g2) {
		g2.drawImage(image ,worldX-(gp.tileSize/2),worldY-(gp.tileSize/2), null);
	}

}
