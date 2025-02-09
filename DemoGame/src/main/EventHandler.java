package main;

import java.awt.Rectangle;

public class EventHandler {

	GamePanel gp;
	Rectangle eventRect;
	int eventRectX, eventRectY;
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		
		eventRect = new Rectangle();
		eventRect.x = 23;
		eventRect.y = 23;
		eventRect.width = 20;
		eventRect.height = 20;
		eventRectX = eventRect.x;
		eventRectY = eventRect.y;
		
	}
	
	public void checkEvent() {
//		if(hit(27,16,"right") == true) { damagePit(gp.dialogueState);} // Use as damage
		if(hit(27,16,"right") == true) { teleport(gp.dialogueState);} // Use as teleport
		if(hit(23,12, "up") == true) {healingPool(gp.dialogueState);}
	}
	
	public boolean hit(int eventCol, int eventRow, String reqDirection) {
		
		boolean hit = false;
		
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		eventRect.x = eventCol * gp.tileSize + eventRect.x;
		eventRect.y = eventRow * gp.tileSize + eventRect.y;
		
		
		// reqDirection is the desired direction for the event to fire
		// .contentEquals(reqDirection) checks if the player's direction matches the required direction
		if(gp.player.solidArea.intersects(eventRect)) {
			if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
				hit = true;
			}
		}
		
//		difference between equals and contentEquals
//		String str1 = "hello";
//		String str2 = "hello";
//		StringBuilder str3 = new StringBuilder("hello");
//
//		System.out.println("equals:");
//		System.out.println(str1.equals(str2));         // true
//		System.out.println(str1.equals(str3));         // false
//
//		System.out.println("contentEquals:");
//		System.out.println(str1.contentEquals(str2));  // true
//		System.out.println(str1.contentEquals(str3));  // true

		
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect.x = eventRectX;
		eventRect.y = eventRectY;
		
		return hit;
	}
	
	public void teleport(int gameState) {
		
		gp.gameState = gameState;
		gp.ui.currentDialogue = "Teleport";
		gp.player.worldX = gp.tileSize*37;
		gp.player.worldY = gp.tileSize*10;
	}
	public void damagePit(int gameState) {
		
		gp.gameState = gameState;
		gp.ui.currentDialogue = "Eh, you fall";
		gp.player.life -= 1;
	}
	
	public void healingPool(int gameState) {
		
		if(gp.keyH.enterPressed == true) {
			gp.gameState = gameState;
			gp.ui.currentDialogue = "Drink drink";
			gp.player.life = gp.player.maxLife;
		}
		
	}
}
