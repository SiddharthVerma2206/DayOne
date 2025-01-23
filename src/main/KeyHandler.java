package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	GamePanel gp;
	
	public boolean upKey , downKey , leftKey , rightKey;
	public boolean weaponChangeKey;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if(gp.gameState == gp.titleState) {
			menuControls(code);
		}
		else if(gp.gameState == gp.pauseState) {
			pauseControls(code);
		}else if(gp.gameState == gp.endState) {
			endControls(code);
		}else if(gp.gameState == gp.controlsState) {
			controlsControls(code);
		}
		
		if(code == KeyEvent.VK_W) {
			upKey = true;
		}
		if(code == KeyEvent.VK_S) {
			downKey = true;
		}
		if(code == KeyEvent.VK_A) {
			leftKey = true;
		}
		if(code == KeyEvent.VK_D) {
			rightKey = true;
		}
		if(code == KeyEvent.VK_E) {
			gp.player.changeWeapon();
		}
		if(code == KeyEvent.VK_R) {
			gp.isReloading = true;
		}
		if(code == KeyEvent.VK_P) {
			if(gp.gameState == gp.playState) {
				gp.gameState = gp.pauseState;
			}else if(gp.gameState == gp.pauseState) {
				gp.gameState = gp.playState;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			upKey = false;
		}
		if(code == KeyEvent.VK_S) {
			downKey = false;
		}
		if(code == KeyEvent.VK_A) {
			leftKey = false;
		}
		if(code == KeyEvent.VK_D) {
			rightKey = false;
		}
	}
	
	public void menuControls(int code) {
		if(code == KeyEvent.VK_UP) {
			if(gp.ui.commandNum == 0) {
				gp.ui.commandNum = 2;					
			}else {
				gp.ui.commandNum--;
			}
		}
		if(code == KeyEvent.VK_DOWN) {
			if(gp.ui.commandNum == 2) {
				gp.ui.commandNum = 0;					
			}else {
				gp.ui.commandNum++;
			}
		}
		if(code == KeyEvent.VK_ENTER) {
			if(gp.ui.commandNum == 0) {
				gp.gameState = gp.playState;
			}else if(gp.ui.commandNum == 1) {
				gp.gameState = gp.controlsState;
			}else if(gp.ui.commandNum == 2) {
				System.exit(0);
			}
		}
	}
	public void pauseControls(int code) {
		if(code == KeyEvent.VK_UP) {
			if(gp.ui.commandNum == 0) {
				gp.ui.commandNum = 2;					
			}else {
				gp.ui.commandNum--;
			}
		}
		if(code == KeyEvent.VK_DOWN) {
			if(gp.ui.commandNum == 2) {
				gp.ui.commandNum = 0;					
			}else {
				gp.ui.commandNum++;
			}
		}
		if(code == KeyEvent.VK_ENTER) {
			if(gp.ui.commandNum == 0) {
				gp.gameState = gp.playState;
			}else if(gp.ui.commandNum == 1) {
				gp.gameState = gp.titleState;
			}else if(gp.ui.commandNum == 2) {
				System.exit(0);
			}
		}
	}
	public void endControls(int code) {
		if(code == KeyEvent.VK_UP) {
			if(gp.ui.commandNum == 0) {
				gp.ui.commandNum = 1;					
			}else {
				gp.ui.commandNum--;
			}
		}
		if(code == KeyEvent.VK_DOWN) {
			if(gp.ui.commandNum == 1) {
				gp.ui.commandNum = 0;					
			}else {
				gp.ui.commandNum++;
			}
		}
		if(code == KeyEvent.VK_ENTER) {
			if(gp.ui.commandNum == 0) {	
				gp.gameState = gp.playState;
			}else if(gp.ui.commandNum == 1) {
				gp.gameState = gp.titleState;
			}
		}
	}
	public void controlsControls(int code) {
		if(code == KeyEvent.VK_ENTER) {
			gp.gameState = gp.titleState;
		}	
	}

}
