package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.UtilityTool;

public class SuperObject {
	
	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int worldXX, worldYY;
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // for each object
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	UtilityTool uTool = new UtilityTool();
	
	public void draw(Graphics2D g2, GamePanel gp) {
	// gp.player.screenx is needed so player displace at the center; relates to the screen
	int screenx = worldXX - gp.player.worldX + gp.player.screenX; 
	int screeny = worldYY - gp.player.worldY + gp.player.screenY;
	
	// boundary so it draws only tiles which is visible on the screen
	if(worldXX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
			worldXX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			worldYY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			worldYY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
		g2.drawImage(image, screenx, screeny, gp.tileSize, gp.tileSize, null);
	
		}
	}

}
