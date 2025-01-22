package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
		GamePanel gp;
		BufferedImage tile1 , tile2;
		public TileManager(GamePanel gp) {
			this.gp = gp;
			tile1 = getTileImage("tile1");
			tile2 = getTileImage("tile2");
		}
		
		public BufferedImage getTileImage(String imageName) {
			BufferedImage image=null;
			try {
				image = ImageIO.read(getClass().getResourceAsStream("/player/"+ imageName +".png"));
				BufferedImage scaledImage = new BufferedImage(gp.tileSize , gp.tileSize , image.getType());
				Graphics2D g2 = scaledImage.createGraphics();
				g2.drawImage(image, 0, 0, gp.tileSize, gp.tileSize, null);
				image = scaledImage;
				
			}catch(IOException e){
				e.printStackTrace();
			}
			return image;
		}
		
		public void draw(Graphics2D g2) {
			
			int worldCol = 0;
			int worldRow =0;
			while(worldCol < gp.maxScreenCol && worldRow < gp.maxScreenRow) {	
				int worldX = worldCol * gp.tileSize;
				int worldY = worldRow * gp.tileSize;
				if(worldCol % 2 == 0) {
					g2.drawImage(tile1, worldX, worldY,null);										
				}else {
					g2.drawImage(tile2, worldX, worldY,null);															
				}
				worldCol++;
				if(worldCol == gp.maxScreenCol) {
					worldCol = 0;
					worldRow++;
				}
			}
			
		}
}
