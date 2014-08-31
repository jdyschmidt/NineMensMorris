package engine;

public class Player {
	//Whether the player can move pieces anywhere
	private boolean flying = false;
	//Only two options: player one or two
	private final int val;
	//Amount of current pieces left to place
	private int unplaced;
	//Amount of pieces on the board
	private int piecesOut;
	//Amount of pieces captured from opponent
	private int captured = 0;
	
	/*
	 * @param val Which player this is (1 or 2)
	 * @param pieces Starting amount of pieces
	 */
	public Player(int val, int pieces) {
		this.val = val;
		this.unplaced = pieces;
	}

	/*
	 * @return Whether the player can move pieces anywhere
	 */
	public boolean isFlying() {
		return flying;
	}

	/*
	 * Allow player to move pieces anywhere. Can't switch back
	 */
	protected void setFlying() {
		this.flying = true;
	}

	/*
	 * @return Which player this is (1 or 2)
	 */
	public int getVal() {
		return val;
	}

	/*
	 * @return Value of the other player
	 */
	public int getOtherVal() {
		return (val==1?2:1);
	}
	
	/*
	 * @return Amount of pieces player has left to place
	 */
	public int getUnplaced() {
		return unplaced;
	}

	/*
	 * Remove one piece from player's stash
	 */
	protected void placePiece() {
		this.unplaced--;
		this.piecesOut++;
	}

	/*
	 * @return Amount of pieces player has captured from opponent
	 */
	public int getCaptured() {
		return captured;
	}

	/*
	 * Add one to captured amount
	 */
	protected void addCaptured() {
		captured++;
	}

	public int getPiecesOut() {
		return piecesOut;
	}
	
	protected void removePiece() {
		this.piecesOut--;
	}
}
