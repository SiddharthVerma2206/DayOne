package entity;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.KeyHandler;
import main.MouseHandler;

public class Player extends Entity{
	
	KeyHandler keyH;
	MouseHandler mouH;
	int correction = gp.tileSize/2;
	float velocityX = 0;
	float velocityY = 0;
	float playerSpeed = 2.0f;
	
	//Idol image counter 
	int idolCounter = 0;
	int idolNum = 1;
	
	//weapon 
	public Weapon currWeapon;
	BufferedImage weapon1 = null;
	public BufferedImage reload1=null , reload2=null;
	boolean revolver = true;
	double weaponAngle;
	int weaponDistance = 30;
	public int weaponX;
	public int weaponY;
	int reloadCounter , reloadNum=1 ;
	
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
		currWeapon = new Weapon("Pistol" ,120 ,30 ,6 ,2 ,"/player/Pistol");
		getWeaponImage(currWeapon.getImagePath());
		health = 4;
		radius = 10;
		direction = "down";
	}
	
	public void getPlayerImage() {
		idol1 = getScaledImage("/player/idol1");
		idol2 = getScaledImage("/player/idol2");
		idol3 = getScaledImage("/player/idol3");
		left1 = getScaledImage("/player/left1");
		left2 = getScaledImage("/player/left2");
		right1 = getScaledImage("/player/right1");
		right2 = getScaledImage("/player/right2");
	}
	
	
	public void getWeaponImage(String imagePath) {
		weapon1 = getScaledImage(imagePath);
		reload1 = getScaledImage("/player/reload1");
		reload2 = getScaledImage("/player/reload2");
	}
	
	public void updateWeapon() {
		double deltaX = mouH.mouseX - worldX;
        double deltaY = mouH.mouseY- worldY;
        this.weaponAngle = Math.atan2(deltaY, deltaX);
        gp.setBulletValues();
	}
	
	public void changeWeapon() {
		Weapon newWeapon = new Weapon("SMG", 60 , 15 ,25 , 1, "/player/SMG");
		this.currWeapon = newWeapon;
		getWeaponImage(currWeapon.getImagePath());
	}
	
	public void update() {
		if(health <=0) {
			gp.gameState =gp.endState; 
			gp.resetGame();
		}
		velocityX = 0;
	    velocityY = 0;
		if(keyH.weaponChangeKey == true) {
			changeWeapon();
			keyH.weaponChangeKey = false;
		}
		if(keyH.upKey == true || keyH.downKey == true ||keyH.rightKey == true || keyH.leftKey == true) {
			if (keyH.upKey) {
		        velocityY = -playerSpeed;
		        direction = "up";
		    } 
		    if (keyH.downKey) {
		        velocityY = playerSpeed;
		        direction = "down";
		    } 
		    if (keyH.leftKey) {
		        velocityX = -playerSpeed;
		        direction = "left";
		    } 
		    if (keyH.rightKey) {
		        velocityX = playerSpeed;
		        direction = "right";
		    }
		    worldX += velocityX;
		    worldY += velocityY;
			
			
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
		if(gp.isReloading == true) {
			reloadCounter++;
			if(reloadCounter > 7) {
				if(reloadNum == 1) {
					reloadNum = 2;
				}
				else if(reloadNum == 2) {
					reloadNum = 1;
				}
				reloadCounter = 0;
			}	
	    }
		if(keyH.upKey == false && keyH.downKey == false && keyH.rightKey == false && keyH.leftKey == false) {
			direction = "idol";
			idolCounter++;
			if(idolCounter > 12) {
				if(idolNum == 1) {
					idolNum = 2;
				}
				else if(idolNum == 2) {
					idolNum = 3;
				}
				else if(idolNum == 3) {
					idolNum = 1;
				}
				idolCounter = 0;
			}
		}	
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage player = null;
		BufferedImage weapon = null;
		if(gp.isReloading == true) {
			if(reloadNum == 1) {
				weapon = reload1;
			}else if(reloadNum == 2) {
				weapon = reload2;
			}
		}
		else {
		weapon = weapon1;
	    }
		switch(direction) {
		case "idol":
			if(idolNum == 1) {
				player= idol1;				
			}
			if(idolNum == 2) {
				player= idol2;				
			}
			if(idolNum == 3) {
				player = idol3;
			}
			break;
		case "up":
			if(spriteNum == 1) {
				player= right1;				
			}
			if(spriteNum == 2) {
				player= right2;				
			}			
			break;
		case "down":
			if(spriteNum == 1) {
				player= left1;				
			}
			if(spriteNum == 2) {
				player= left2;
			}
			break;
		case "left":
			if(spriteNum == 1) {
				player = left1;
			}
			if(spriteNum == 2) {
				player= left2;
			}
			break;
		case "right":
			if(spriteNum == 1) {
				player= right1;			
			}
			if(spriteNum == 2) {
				player = right2;
			}
			break;		
		}
		
		g2.drawImage(player,worldX,worldY,null);
		
		 weaponX = (int) ((worldX+correction) + weaponDistance * Math.cos(weaponAngle));
	     weaponY = (int) ((worldY+correction) + weaponDistance * Math.sin(weaponAngle));
	     AffineTransform oldTransform = g2.getTransform();
	     g2.translate(weaponX, weaponY);
	     g2.rotate(weaponAngle);
	     g2.drawImage(weapon, -weapon.getWidth(null) / 2, -weapon.getHeight(null) / 2, null);
	     g2.setTransform(oldTransform);
	}

	
}






