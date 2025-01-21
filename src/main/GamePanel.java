package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import entity.Enemy;
import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

	// Screen Settings
	final int orignalTileSize = 16;  //16*16
	final int scale = 2;
	public final int tileSize = orignalTileSize * scale ; 
	public final int maxScreenCol = 25;
	public final int maxScreenRow = 20;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;

	//Enemy
	ArrayList<Enemy>currEnemies = new ArrayList<>();
	private Random random = new Random();
	int spawnDelay = 1000;
	
	//FPS
	int FPS = 60;
	
	KeyHandler keyH = new KeyHandler();
	MouseHandler mouH = new MouseHandler();
	Thread gameThread;
	public Player player = new Player(this , keyH , mouH);
	TileManager tileM = new TileManager(this);
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth , screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.addMouseListener(mouH);
		this.addMouseListener(mouH);
        this.addMouseMotionListener(mouH);
		this.setFocusable(true);
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void spawnEnemy() {
		double spawnX , spawnY;
		if (random.nextBoolean()) { // Randomly choose top/bottom or left/right
            spawnX = random.nextDouble() * screenWidth; // Random x position
            spawnY = random.nextBoolean() ? -20 : screenHeight + 20; // Off-screen y position
        } else {
            spawnX = random.nextBoolean() ? -20 : screenWidth + 20; // Off-screen x position
            spawnY = random.nextDouble() * screenHeight; // Random y position
        }
		Enemy enemy = new Enemy(this , spawnX , spawnY);
		currEnemies.add(enemy);
	}
	
	public void updateAllEnemies() { 
		for(int i = 0 ; i<currEnemies.size() ; i++) {
			Enemy enemy = currEnemies.get(i);
			enemy.move();
		}
	}
	
	@Override

	public void run() {
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread != null) {
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			if(delta >= 1) {
				update();
				repaint();
				delta--;
			}
		}
	}
	
	public void update() {
		spawnDelay-=5;
		player.update();
		player.updateWeapon();
		player.updateAllBullets();
		updateAllEnemies();
		if(spawnDelay <= 0) {
			spawnEnemy();
			spawnDelay = 1000;
		}
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		tileM.draw(g2);
		for(Enemy enemy : currEnemies) {
			enemy.draw(g2);
		}
		player.draw(g2);
		g2.dispose();
		
	}
	
}
