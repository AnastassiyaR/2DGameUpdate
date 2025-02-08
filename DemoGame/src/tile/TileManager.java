package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		tile = new Tile[50];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		loadMap("/maps/worldmap.txt");
	}
	public void getTileImage() {
		
		setup(0, "grass00", false);
		setup(1, "grass00", false);
		setup(2, "grass00", false);
		setup(3, "grass00", false);
		setup(4, "grass00", false);
		setup(5, "grass00", false);
		setup(6, "grass00", false);
		setup(7, "grass00", false);
		setup(8, "grass00", false);
		setup(9, "grass00", false);
		setup(10, "grass00", false);
		setup(11, "grass01", false);
	
		setup(12, "water00", true);
		setup(13, "water01", true);
		setup(14, "water02", true);
		setup(15, "water03", true);
		setup(16, "water04", true);
		setup(17, "water05", true);
		setup(18, "water06", true);
		setup(19, "water07", true);
		setup(20, "water08", true);
		setup(21, "water09", true);
		setup(22, "water10", true);
		setup(23, "water11", true);
		setup(24, "water12", true);
		setup(25, "water13", true);
		
		setup(26, "road00", false);
		setup(27, "road01", false);
		setup(28, "road02", false);
		setup(29, "road03", false);
		setup(30, "road04", false);
		setup(31, "road05", false);
		setup(32, "road06", false);
		setup(33, "road07", false);
		setup(34, "road08", false);
		setup(35, "road09", false);
		setup(36, "road10", false);
		setup(37, "road11", false);
		setup(38, "road12", false);
		
		setup(39, "earth", false);
		setup(40, "wall", true);
		setup(41, "tree", true);
		

//		try {
//
//			tile[0] = new Tile();
//			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
//
//			tile[1] = new Tile();
//			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
//			tile[1].collision = true;
//	
//			tile[2] = new Tile();
//			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
//			tile[2].collision = true;
//
//			tile[3] = new Tile();
//			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
//			
//			tile[4] = new Tile();
//			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
//			tile[4].collision = true;
//			
//			tile[5] = new Tile();
//			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
//			
//		} catch(IOException e) {
//			e.printStackTrace();
//		}
	
	}
	// handle all the half-duplicated lines 
	public void setup(int index, String imageName, boolean collision) {
		
		UtilityTool uTool = new UtilityTool();
		
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	public void loadMap(String filePath) {
		
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			// Read each char in the text and save in the buffer
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				
				String line = br.readLine();
				
				while(col < gp.maxWorldCol) {
					
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num;
					col++;
				}
				if(col == gp.maxWorldRow) {
					col = 0;
					row++;
				}
				
			}
			br.close();
		} catch (Exception e) {
		}
	}
	public void draw(Graphics2D g2) {
		
		int worldcol = 0;
		int worldrow = 0;
		
		// the thing is that world map and screen has different coordinates like coordinate for world and screen
		// The screen is a limited area!!!
		//so here are formulas
		
		while(worldcol < gp.maxWorldCol && worldrow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[worldcol][worldrow];
			
			int worldx = worldcol * gp.tileSize;
			int worldy = worldrow * gp.tileSize;
			
			// gp.player.worldx shows where is character on the map; relates to the map
			// gp.player.screenx is needed so player displace at the center; relates to the screen
			int screenx = worldx - gp.player.worldX + gp.player.screenX; 
			int screeny = worldy - gp.player.worldY + gp.player.screenY;
			
			// boundary so it draws only tiles which is visible on the screen
			if(worldx + gp.tileSize > gp.player.worldX - gp.player.screenX && 
					worldx - gp.tileSize < gp.player.worldX + gp.player.screenX &&
					worldy + gp.tileSize > gp.player.worldY - gp.player.screenY &&
					worldy - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				g2.drawImage(tile[tileNum].image, screenx, screeny, null);
			}
			
			worldcol++;
			
			if(worldcol == gp.maxWorldCol) {
				worldcol = 0;
				worldrow++;
			}
		}
	}
}
