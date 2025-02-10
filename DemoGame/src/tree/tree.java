package tree;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class tree extends Entity{
	
	GamePanel gp;
	public boolean destructible = false;
	
	public tree(GamePanel gp, int col, int row) {
		super(gp);
		this.gp = gp;
	}
	
	public boolean CorrectItem(Entity entity) {
		boolean CorrectItem = false;
		return CorrectItem;
	}
	public void playSE() {}
	
	public tree DestroyForm() {
		tree tile = null;
		return tile;
	}
	public void update() {
		if(invincible == true) {
			invinicibleCount++;
			if(invinicibleCount > 20) {
				invincible = false;
				invinicibleCount = 0;
			}
		}
	}
	
	// So dry tree always has 100% visibility
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		int screenx = worldX - gp.player.worldX + gp.player.screenX; 
		int screeny = worldY - gp.player.worldY + gp.player.screenY;
		
		// boundary so it draws only tiles which is visible on the screen
		if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
				worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
				worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
				worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			
			g2.drawImage(image, screenx, screeny, null);
		} 
	}
}
