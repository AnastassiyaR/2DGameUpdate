package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	// 'implements' refers to making the class implement the interface.

	public boolean upPressed, downPressed, leftPressed, rightPressed;
	GamePanel gp;
	
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
		
	}
	@Override // it automatically appeared after typing KeyEvent
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			upPressed = true;
		}
		
		if(code == KeyEvent.VK_S) {
			downPressed = true;
		}
	 	if(code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		
		// PAUSE
	 	if(code == KeyEvent.VK_F) {
			if(gp.gameState == gp.playState) {
				gp.gameState = gp.pauseState;
			} else if(gp.gameState == gp.pauseState) {
				gp.gameState = gp.playState;
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			upPressed = false;
		}
		
		if(code == KeyEvent.VK_S) {
			downPressed = false;
		}
	 	if(code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
		}
	}
	
}
