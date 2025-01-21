package entity;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Bullet{

	int posX , posY , speed;
	double dx,dy;
	boolean isVisible;	
	double angle;
	BufferedImage bullet;
	
	public Bullet(int startX , int startY , int mouseX , int mouseY) {
		this.posX = startX + 24;
		this.posY = startY + 24;
		this.angle = Math.atan2(mouseY-startY, mouseX-startX);
		isVisible = true;
		speed = 10;
		this.dx = speed * Math.cos(angle);
		this.dy = speed * Math.sin(angle);
		getImage();
	}

	void getImage() {
		try {
			bullet=ImageIO.read(getClass().getResourceAsStream("/player/bullet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void move(int screenWidth , int screenHeight) {
		posX += dx;
		posY += dy;
		if (posX < 0 || posX > screenWidth || posY < 0 || posY > screenHeight) {
			isVisible = false;
		}
	}
	
	public void draw(Graphics2D g2) {
		AffineTransform oldTransform = g2.getTransform();
		g2.translate(posX, posY);
        g2.rotate(angle);
        g2.drawImage(bullet, -bullet.getWidth(null) / 2, -bullet.getHeight(null) / 2, null);
        g2.setTransform(oldTransform);
	}
	
}
