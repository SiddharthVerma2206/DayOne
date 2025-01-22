package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class UI {
	GamePanel gp;
	Graphics2D g2;
	Font arial_40 , arial_80B;
	public int commandNum = 0;
	
	
	
	public UI(GamePanel gp) {
		this.gp = gp;
		arial_40 = new Font("Arial", Font.PLAIN, 40); 
		arial_80B = new Font("Arial", Font.BOLD, 80);
	}
	
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}else if(gp.gameState == gp.controlsState) {
			drawControlsScreen();
		}else if(gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}else if(gp.gameState == gp.endState) {
			drawEndScreen();
		}
	}
	
	public void drawTitleScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
		String text = "Survive Day One";
		int x = getXForText(text);
		int y = gp.tileSize * 3;
		//Shadow 
		g2.setColor(Color.gray);
		g2.drawString(text, x+5, y+5);		
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		displayMenu("START GAME" , "CONTROLS" , "QUIT");
		
	}
	
	public void drawPauseScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
		String text = "Paused";
		int x = getXForText(text);
		int y = gp.tileSize * 4;
		g2.setColor(Color.gray);
		g2.drawString(text, x+5, y+5);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		displayMenu("CONTINUE" , "BACK TO MENU" , "QUIT");	
	}

	public void drawEndScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
		String text = "You Died!";
		int x = getXForText(text);
		int y = gp.tileSize * 4;
		g2.setColor(Color.gray);
		g2.drawString(text, x+5, y+5);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		text = "Your Kills: ";
		g2.drawString(text + gp.prevGameKills , x, gp.tileSize*6);	//Display Kills
		displayMenu("RETRY" , "MAIN MENU" , "");	
	}
	
	
	public void drawControlsScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
		String text = "CONTROLS";
		int x = getXForText(text);
		int y = gp.tileSize * 4;
		g2.setColor(Color.gray);
		g2.drawString(text, x+5, y+5);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		displayMenu("" , "BACK" , "");
	}
	
	public void displayMenu(String first , String second , String third) {
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,40F));
		String text;
		int x , y;
		text = first;
		x = getXForText(text);
		y = gp.tileSize * 8;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-gp.tileSize, y);			
		}
		
		text = second;
		x = getXForText(text);
		y = gp.tileSize * 9;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-gp.tileSize, y);			
		}
		
		text = third;
		x = getXForText(text);
		y = gp.tileSize * 10;
		g2.drawString(text, x, y);
		if(commandNum == 2) {
			g2.drawString(">", x-gp.tileSize, y);			
		}
	}
	
	public int getXForText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
	
}
