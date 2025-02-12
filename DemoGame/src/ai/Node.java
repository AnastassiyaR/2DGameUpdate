package ai;

public class Node {

//	This class describes a cell on a map. It stores:
//		col and row — the cell's coordinates on the ma
//		gCost — the cost to reach this cell.
//		-hCost — the estimated cost to reach the goal from this cell.
//		fCost — the total cost (gCost + hCost).
//		solid — if the cell is a wall and cannot be passed through.
//		open — if the cell is available for searching.
//		сhecked — if the cell has already been checked.

	Node parent;
	public int col;
	public int row;
	int gCost;
	int hCost;
	int fCost;
	boolean solid;
	boolean open;
	boolean checked;
	
	public Node(int col, int row) {
		this.col = col;
		this.row = row;
	}
}
