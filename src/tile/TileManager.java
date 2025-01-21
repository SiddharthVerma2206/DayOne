package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
		GamePanel gp;
		BufferedImage image;
		public TileManager(GamePanel gp) {
			this.gp = gp;
			getTileImage();
		}
		
		public void getTileImage() {
			try {
				image = ImageIO.read(getClass().getResourceAsStream("/player/Tile.png"));
				BufferedImage scaledImage = new BufferedImage(gp.tileSize , gp.tileSize , image.getType());
				Graphics2D g2 = scaledImage.createGraphics();
				g2.drawImage(image, 0, 0, gp.tileSize, gp.tileSize, null);
				image = scaledImage;
				
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		public void draw(Graphics2D g2) {
			
			int worldCol = 0;
			int worldRow =0;
			while(worldCol < gp.maxScreenCol && worldRow < gp.maxScreenRow) {	
				int worldX = worldCol * gp.tileSize;
				int worldY = worldRow * gp.tileSize;
				g2.drawImage(image, worldX, worldY,null);					
				worldCol++;
				if(worldCol == gp.maxScreenCol) {
					worldCol = 0;
					worldRow++;
				}
			}
			
		}
}
