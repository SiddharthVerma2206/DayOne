package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	GamePanel gp;
	public int worldX , worldY , speed;
	
	public BufferedImage up1 , up2 , down1 , down2 , left1 , left2 , right1 , right2 , pistol;
	public String direction;
	public int health , damage, radius;
	public boolean isColliding=false;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	public BufferedImage getScaledImage(String imagePath) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage Image = null;
		try {
			Image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			Image = uTool.scaleImage(Image, gp.tileSize , gp.tileSize);
		}catch(IOException e){
			e.printStackTrace();
		}
		return Image;
	}
	
}
