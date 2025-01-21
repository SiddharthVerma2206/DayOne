package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Enemy extends Entity{

	BufferedImage image = null;
	
	public Enemy(GamePanel gp , double spawnX , double spawnY) {
		super(gp);
		this.worldX = (int)spawnX;
		this.worldY = (int)spawnY;
		speed = 2;
		image = getScaledImage("/player/guy_down_1");
	}
	
	public void move() {
		double deltaX = gp.player.worldX - worldX;
        double deltaY = gp.player.worldY - worldY;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if (distance > 0) {
            worldX += (deltaX / distance) * speed;
            worldY += (deltaY / distance) * speed;
        }
	}
	
	public void draw(Graphics2D g2) {
		g2.drawImage(image ,worldX, worldY, null);
	}

}
