package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JPanel;

import entity.Bullet;
import entity.Enemy;
import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

	// Screen Settings
	final int orignalTileSize = 24;  //16*16
	final int scale = 2;
	public final int tileSize = orignalTileSize * scale ; 
	public final int maxScreenCol = 23;
	public final int maxScreenRow = 13;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;

	//Stats
	public int prevGameKills = 0,kills=0;
	
	//Enemy
	ArrayList<Enemy>currEnemies = new ArrayList<>();
	private Random random = new Random();
	int defSpawnDelay = 120;
	int spawnDelay=defSpawnDelay;
	int enemiesSpawned = 0;
	
	//Bullets
	private ArrayList<Bullet>currBullets = new ArrayList<>();
	private static int damageDelay = 120;
	public boolean isReloading = false;
	int bulletCount=0;
	int maxBullets = 6 , bulletDamage = 2;
	private static int bulletDelay = 30;
	private static int reloadDelay = 120;	
	private static int delTime = bulletDelay;
	private static int relTime = reloadDelay;

	//Game State
	public int titleState = 0;
	public int playState = 1;
	public int pauseState = 2;
	public int controlsState = 3;
	public int endState = 4;
	public int gameState = titleState;
	
	
	//FPS
	int FPS = 60;
	
	Thread gameThread;
	
	KeyHandler keyH = new KeyHandler(this);
	MouseHandler mouH = new MouseHandler();
	CollisionChecker cChecker = new CollisionChecker(this);
	public Player player = new Player(this , keyH , mouH);
	TileManager tileM = new TileManager(this);
	UI ui = new UI(this);
	
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
	
	public void resetGame() {
		Player player = new Player(this , keyH , mouH);
		this.player = player;
		ArrayList<Enemy>Enemies = new ArrayList<>();
		this.currEnemies = Enemies;
		 ArrayList<Bullet>Bullets = new ArrayList<>();
		this.currBullets = Bullets;
		defSpawnDelay = 120;
		spawnDelay=defSpawnDelay;
		enemiesSpawned = 0;
		isReloading = false;
		bulletCount=0;
		bulletDamage = 2;
		bulletDelay = 30;
		reloadDelay = 120;	
		delTime = bulletDelay;
		relTime = reloadDelay;
		prevGameKills = kills;
		kills = 0;
	}
	
	public void setBulletValues() {
		bulletDelay = player.currWeapon.getDelTime();
		reloadDelay = player.currWeapon.getReloadTime();
		maxBullets  = player.currWeapon.getMaxBullets();
		bulletDamage = player.currWeapon.getDamage();
	}
	
	public void newBullet() {
		if(!isReloading && bulletCount<maxBullets) {
			Bullet newBullet = new Bullet(player.weaponX ,player.weaponY , mouH.mouseX, mouH.mouseY);
			currBullets.add(newBullet);
			bulletCount++;
		}
		if(bulletCount>=maxBullets) {
			isReloading = true;
		}
	}
	
	public void reload() {
		relTime--;
        if (relTime <= 0) {
        	bulletCount = 0;
    		relTime = reloadDelay; 
    		isReloading = false;
        }
	}
	
	public void updateAllBullets() {
	    Iterator<Bullet> bulletIterator = currBullets.iterator();
	    while (bulletIterator.hasNext()) {
	        Bullet bullet = bulletIterator.next();
	        bullet.move(screenWidth, screenHeight);
	        if (!bullet.isVisible) {
	            bulletIterator.remove();
	        }
	    }
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
		if(spawnX > player.worldX) {
			enemy.image = enemy.getScaledImage("/player/rightSpawn");
		}else if(spawnX < player.worldX){
			enemy.image = enemy.getScaledImage("/player/leftSpawn");
		}else {			
			enemy.image = enemy.getScaledImage("/player/rightSpawn");
		}
		currEnemies.add(enemy);
	}
	
	public void updateAllEnemies() { 
		for(int i = 0 ; i<currEnemies.size() ; i++) {
			Enemy enemy = currEnemies.get(i);
			enemy.move();
			 if (cChecker.circleCollision(enemy.worldX, enemy.worldY, enemy.radius,
                     player.worldX, player.worldY, player.radius) && player.isColliding==false) {
				 player.isColliding = true;
				 player.health-=enemy.damage;
			 }
		}
	}
	
	private void updateTimers() {
	    if (isReloading) {
	        reload();
	    }

	    if (delTime > 0) {
	        delTime--;
	    }

	    if (player.isColliding) {
	        damageDelay--;
	        if (damageDelay <= 0) {
	            player.isColliding = false;
	            damageDelay = 120; // Reset damage delay
	        }
	    }
	}

	private void handlePlayerCollisions() {
	    for (Enemy enemy : currEnemies) {
	        if (cChecker.circleCollision(enemy.worldX, enemy.worldY, enemy.radius,
	                                      player.worldX, player.worldY, player.radius)
	            && !player.isColliding) {
	            player.isColliding = true;
	            player.health -= enemy.damage; // Decrease health based on enemy's damage
	        }
	    }
	}

	private void handleEnemySpawning() {
	    spawnDelay--;
	    if (spawnDelay <= 0) {
	        spawnEnemy();
	        enemiesSpawned++;
	        if(enemiesSpawned>=10) {
	        	enemiesSpawned = 0;
	        	 defSpawnDelay-=10;
	        }
	        spawnDelay = defSpawnDelay; // Reset spawn delay
	    }
	}

	private void handleBulletEnemyCollisions() {
	    ArrayList<Bullet> bulletsToRemove = new ArrayList<>();
	    ArrayList<Enemy> enemiesToRemove = new ArrayList<>();

	    for (Bullet bullet : currBullets) {
	        for (Enemy enemy : currEnemies) {
	            if (cChecker.circleCollision(bullet.posX, bullet.posY, bullet.radius,
	                                          enemy.worldX, enemy.worldY, enemy.radius)) {
	                bulletsToRemove.add(bullet); // Mark bullet for removal
	                enemy.health -= bulletDamage; // Reduce enemy health
	                if (enemy.health <= 0) {
	                    enemiesToRemove.add(enemy); // Mark enemy for removal
	                    kills++; // Increment kill count
	                }
	                break; // A bullet can only hit one enemy at a time
	            }
	        }
	    }

	    // Remove marked bullets and enemies
	    currBullets.removeAll(bulletsToRemove);
	    currEnemies.removeAll(enemiesToRemove);
	}
	
	public void showStats(Graphics2D g2) {
		g2.setColor(Color.WHITE);
		g2.drawString("Kills: " + kills , 5, 10);
		g2.drawString("Health: " + player.health , 5, 30);
		g2.drawString(""+(maxBullets - bulletCount), mouH.mouseX , mouH.mouseY);
	}
	
	public void update() {
		if(gameState == playState) {
			updateTimers();               // Manage all time-related logic
			if (mouH.pressed && delTime <= 0 && !isReloading) {
				newBullet();              // Create a new bullet if possible
				delTime = bulletDelay;    // Reset bullet timer
			}
			player.updateWeapon();
			player.update();              // Update player 
			updateAllBullets();           // Move and clean up bullets
			updateAllEnemies();           // Move and handle enemy logic
			handlePlayerCollisions();     // Check and handle player collisions
			handleBulletEnemyCollisions();// Check and handle bullet-enemy collisions
			handleEnemySpawning();        // Spawn enemies if needed			
		}else {
			//nothing will update in pause and title State
		}
	}

	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		tileM.draw(g2);		
		if(gameState == playState){
			for(Enemy enemy : currEnemies) {
				enemy.draw(g2);
			}
			for(Bullet bullet : currBullets) {
				bullet.draw(g2);
			}
			player.draw(g2);
			showStats(g2);	
			ui.draw(g2);
		}else {
			ui.draw(g2);
		}
		g2.dispose();
		
	}
	
}
