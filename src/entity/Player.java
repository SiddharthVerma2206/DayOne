package entity;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.MouseHandler;
import main.UtilityTool;

public class Player extends Entity{
	
	KeyHandler keyH;
	MouseHandler mouH;
	int correction = gp.tileSize/2;
	
	//weapon 
	boolean revolver = true;
	double weaponAngle;
	int weaponDistance = 40;
	int weaponX ,weaponY;
	
	
	//Bullets 
	private ArrayList<Bullet>currBullets = new ArrayList<>();
	int bulletCount;
	int maxBullets = 6;
	boolean isReloading = false;
	private static int bulletDelay = 500;
	private static int reloadDelay = 2000;	
	private static int relTime = reloadDelay;
	private static int delTime = bulletDelay;
	
	
	public Player(GamePanel gp , KeyHandler keyH , MouseHandler mouH) {
		super(gp);
		this.keyH = keyH;
		this.mouH = mouH;
		setDefaultValues();
		getPlayerImage();
	}

	public void setDefaultValues() {
		worldX = (gp.screenWidth/2) - correction;
		worldY = (gp.screenHeight/2) - correction;
		speed = 2;
		direction = "down";
	}
	
	public void getPlayerImage() {
		up1 = getScaledImage("/player/guy_up_1");
		up2 = getScaledImage("/player/guy_up_2");
		down1 = getScaledImage("/player/guy_down_1");
		down2 = getScaledImage("/player/guy_down_2");
		left1 = getScaledImage("/player/guy_left_1");
		left2 = getScaledImage("/player/guy_left_2");
		right1 = getScaledImage("/player/guy_right_1");
		right2 = getScaledImage("/player/guy_right_2");
		pistol = getScaledImage("/player/pistol");
	}
	
	public void newBullet() {
		if(!isReloading && bulletCount<maxBullets) {
			Bullet newBullet = new Bullet(weaponX ,weaponY , mouH.mouseX, mouH.mouseY);
			currBullets.add(newBullet);
			bulletCount++;
		}
		if(bulletCount>=maxBullets) {
			isReloading = true;
		}
	}
	
	public void reload() {
		bulletCount = 0;
		relTime = reloadDelay; 
		isReloading = false;
	}
	
	public void updateAllBullets() {
		for(int i =0 ; i<currBullets.size();i++) {
			Bullet bullet = currBullets.get(i);
			bullet.move(gp.screenWidth , gp.screenHeight);
			if(bullet.isVisible == false) {
				currBullets.remove(i--);
			}
		}
	}

	public void updateWeapon() {
		double deltaX = mouH.mousePosition.x - worldX;
        double deltaY = mouH.mousePosition.y - worldY;
        this.weaponAngle = Math.atan2(deltaY, deltaX);
	}
	
	public void update() {
		delTime -= 15;
		if(isReloading == true) {
			relTime -= 15;
		}
		if(relTime <= 0 && isReloading) {
			reload();
		}
		if(mouH.pressed == true) {
			if(delTime<= 0 && !isReloading) {
				newBullet();
				delTime = bulletDelay;
			}
		}
		
		if(keyH.upKey == true || keyH.downKey == true ||keyH.rightKey == true || keyH.leftKey == true) {
			if(keyH.upKey == true) {
				direction = "up";
				worldY -= speed;
			}
			else if(keyH.downKey == true) {
				direction = "down";
				worldY += speed;
			}
			else if(keyH.leftKey == true) {
				direction = "left";
				worldX -= speed;
			}
			else if(keyH.rightKey == true) {
				direction = "right";
				worldX += speed;
			}
			
			collisionOn = false;
			
			spriteCounter++;
			if(spriteCounter > 12) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}
				else if(spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}	
		}	
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		BufferedImage weapon = null;
		if(revolver == true) {
			weapon = pistol;
		}
		
		switch(direction) {
		case "up":
			if(spriteNum == 1) {
				image = up1;				
			}
			if(spriteNum == 2) {
				image = up2;				
			}			
			break;
		case "down":
			if(spriteNum == 1) {
				image = down1;				
			}
			if(spriteNum == 2) {
				image = down2;
			}
			break;
		case "left":
			if(spriteNum == 1) {
				image = left1;
			}
			if(spriteNum == 2) {
				image = left2;
			}
			break;
		case "right":
			if(spriteNum == 1) {
				image = right1;			
			}
			if(spriteNum == 2) {
				image = right2;
			}
			break;		
		}
		
		g2.drawImage(image,worldX,worldY,null);
		
		for(Bullet bullet : currBullets) {
			bullet.draw(g2);
		}
		
		 weaponX = (int) ((worldX+correction) + weaponDistance * Math.cos(weaponAngle));
	     weaponY = (int) ((worldY+correction) + weaponDistance * Math.sin(weaponAngle));
	     AffineTransform oldTransform = g2.getTransform();
	     g2.translate(weaponX, weaponY);
	     g2.rotate(weaponAngle);
	     g2.drawImage(weapon, -weapon.getWidth(null) / 2, -weapon.getHeight(null) / 2, null);
	     g2.setTransform(oldTransform);
	}

	
}






