package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	// 'implements' refers to making the class implement the interface.

	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;
	GamePanel gp;
	
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
		
	}
	@Override // it automatically appeared after typing KeyEvent
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		// TITLE STATE
		if(gp.gameState == gp.titleState) {titleState(code);}
		
		// PLAY STATE
		else if(gp.gameState == gp.playState) {playState(code);}
		
		// PAUSE STATE
		else if(gp.gameState == gp.pauseState) {pauseState(code);}
		
		// DIALOGUE STATE
		else if(gp.gameState == gp.dialogueState) {dialogueState(code);}
		
		// CHARACTER STATE
		else if(gp.gameState == gp.characterState) {characterState(code);}
		
		// OPTIONS STATE
		else if(gp.gameState == gp.optionsState) {optionsState(code);}
		
		// GAME OVER
		else if(gp.gameState == gp.gameover) {gameover(code);}
	
		// TRADE STATE
		else if(gp.gameState == gp.tradeState) {tradeState(code);}
		}

	public void tradeState(int code) {
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		
		if(code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			if(gp.ui.commandNum < 0) {
				gp.ui.commandNum = 2;
			}
		}
		if(code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if(gp.ui.commandNum > 2) {
				gp.ui.commandNum = 0;
			}
		}
		if(gp.ui.subState == 1) {
			npcInventory(code);
			if(code == KeyEvent.VK_ESCAPE) {
				gp.ui.subState = 0;
			}
		}
		if(gp.ui.subState == 2) {
			playerInventory(code);
			if(code == KeyEvent.VK_ESCAPE) {
				gp.ui.subState = 0;
			}
		}
		
	}
	public void gameover(int code) {
		if(code == KeyEvent.VK_A) {
			gp.ui.commandNum--;
			if(gp.ui.commandNum < 0) {
				gp.ui.commandNum = 1;
			}
			gp.playSE(7);
		}
		
		if(code == KeyEvent.VK_D) {
			gp.ui.commandNum++;
			if(gp.ui.commandNum > 1) {
				gp.ui.commandNum = 0;
			}
			gp.playSE(7);
		}
		
		if(code == KeyEvent.VK_ENTER) {
			if(gp.ui.commandNum == 0) {
				gp.gameState = gp.playState;
				gp.retry();
				gp.playMusic(0);
			}
			else if(gp.ui.commandNum == 1) {
				gp.gameState = gp.titleState;
				gp.restart();
				
			}
		}
	}	
		
		
		

	public void titleState(int code) {
		if(gp.ui.titleScreenState == 0) {
			if(code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if(gp.ui.commandNum < 0) {
					gp.ui.commandNum = 2;
				}
			}
			
			if(code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if(gp.ui.commandNum > 2) {
					gp.ui.commandNum =0;
				}
			}
			if(code == KeyEvent.VK_ENTER) {
				if(gp.ui.commandNum == 0) {
					gp.ui.titleScreenState = 1;
				}
				if(gp.ui.commandNum == 1) {
					// later
				}
				if(gp.ui.commandNum == 2) {
					System.exit(0);
				}
			}
		}

		else if(gp.ui.titleScreenState == 1) {
			if(code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if(gp.ui.commandNum < 0) {
					gp.ui.commandNum = 3;
				}
			}
			
			if(code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if(gp.ui.commandNum > 3) {
					gp.ui.commandNum =0;
				}
			}
			
			if(code == KeyEvent.VK_ENTER) {
				if(gp.ui.commandNum == 0) {
					gp.gameState = gp.playState;
					gp.playMusic(0);
				}
				if(gp.ui.commandNum == 1) {
					gp.gameState = gp.playState;
					gp.playMusic(0);
				}
				if(gp.ui.commandNum == 2) {
					gp.gameState = gp.playState;
				}
				if(gp.ui.commandNum == 3) {
					gp.ui.titleScreenState = 0;
				}
			}
		}
	}
	
	public void playState(int code) {
		
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
		
	 	if(code == KeyEvent.VK_P) {
	 		gp.gameState = gp.pauseState;
		}
	 	if(code == KeyEvent.VK_C) {
	 		gp.gameState = gp.characterState;
	 	}
	 	if(code == KeyEvent.VK_ENTER) {
	 		enterPressed = true;
		}
	 	if(code == KeyEvent.VK_F) {
	 		shotKeyPressed = true;
		}
	 	if(code == KeyEvent.VK_1) {
	 		gp.gameState = gp.optionsState;
		}
	 	if(code == KeyEvent.VK_R) {
	 		switch(gp.currentMap) {
	 		case 0: gp.tileM.loadMap("/maps/worldmap.txt", 0); break;
	 		case 1: gp.tileM.loadMap("/maps/interior.txt", 1); break;
	 		}
	 	}

	}
	
	public void pauseState(int code) {
		if(code == KeyEvent.VK_P) {
	 		gp.gameState = gp.playState;
		}
	}
	
	public void dialogueState(int code) {
		if(code == KeyEvent.VK_ENTER) {
			gp.gameState = gp.playState;
		}
	}
	
	public void characterState(int code) {
		if(code == KeyEvent.VK_C) {
			gp.gameState = gp.playState;
		}
		
		if(code == KeyEvent.VK_ENTER) {
			gp.player.selectItem();
			}
		
		playerInventory(code);
		}
	
	public void optionsState(int code) {
		
		if(code == KeyEvent.VK_1) {
			gp.gameState = gp.playState;
		}
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		
		int maxCommandNum = 0;
		switch(gp.ui.subState) {
		case 0: maxCommandNum = 4; break;
		case 2: maxCommandNum = 1; break;
		}
		
		if(code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			gp.playSE(7);
			if(gp.ui.commandNum < 0) {
				gp.ui.commandNum = maxCommandNum;
			}
		}
		if(code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			gp.playSE(7);
			if(gp.ui.commandNum > maxCommandNum) {
				gp.ui.commandNum = 0;
			}
		}
		
		if(code == KeyEvent.VK_A) {
			if(gp.ui.subState == 0) {
				if(gp.ui.commandNum == 0 && gp.music.volumeScale > 0) {
					gp.music.volumeScale--;
					gp.music.checkVolume();
					gp.playSE(7);
				}
				if(gp.ui.commandNum == 1 && gp.se.volumeScale > 0) {
					gp.se.volumeScale--;
					gp.playSE(7);
				}
			}
		}
		
		if(code == KeyEvent.VK_D) {
			if(gp.ui.subState == 0) {
				if(gp.ui.commandNum == 0 && gp.music.volumeScale < 5) {
					gp.music.volumeScale++;
					gp.music.checkVolume();
					gp.playSE(7);
				}
			}
			
			if(gp.ui.commandNum == 1 && gp.se.volumeScale < 5) {
				gp.se.volumeScale++;
				gp.playSE(7);
				}
		}
		
		
	}
	public void playerInventory(int code) {
		
		if(code == KeyEvent.VK_W) {
			if(gp.ui.PslotRow != 0) {
				gp.ui.PslotRow--;
			}
		}
		if(code == KeyEvent.VK_A) {
			if(gp.ui.PslotCol != 0) {
				gp.ui.PslotCol--;
			}
		}
		if(code == KeyEvent.VK_D) {
			if(gp.ui.PslotCol != 4) {
				gp.ui.PslotCol++;
			}
		}
		if(code == KeyEvent.VK_S) {
			if(gp.ui.PslotRow != 3) {
				gp.ui.PslotRow++;
			}
		}
		
	}
	public void npcInventory(int code) {
		
		if(code == KeyEvent.VK_W) {
			if(gp.ui.npcSlotRow != 0) {
				gp.ui.npcSlotRow--;
			}
		}
		if(code == KeyEvent.VK_A) {
			if(gp.ui.npcSlotCol != 0) {
				gp.ui.npcSlotCol--;
			}
		}
		if(code == KeyEvent.VK_D) {
			if(gp.ui.npcSlotCol != 4) {
				gp.ui.npcSlotCol++;
			}
		}
		if(code == KeyEvent.VK_S) {
			if(gp.ui.npcSlotRow != 3) {
				gp.ui.npcSlotRow++;
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
	 	if(code == KeyEvent.VK_F) {
	 		shotKeyPressed = false;
		}
	}
	
}
