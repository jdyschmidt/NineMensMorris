package engine;

public class Player {
	//Whether the player can move pieces anywhere
	private boolean flying = false;
	//Only two options: player one or two
	private final int val;
	//Amount of current pieces left to place
	private int pieces;
	//Amount of pieces captured from opponent
	private int capturedPieces = 0;
	
	/*
	 * @param val Which player this is (1 or 2)
	 * @param pieces Starting amount of pieces
	 */
	public Player(int val, int pieces) {
		this.val = val;
		this.pieces = pieces;
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
	public void setFlying() {
		this.flying = true;
	}

	/*
	 * @return Which player this is (1 or 2)
	 */
	public int getVal() {
		return val;
	}

	/*
	 * @return Amount of pieces player has left to place
	 */
	public int getPieces() {
		return pieces;
	}

	/*
	 * Remove one piece from player's stash
	 */
	public void decPieces() {
		this.pieces--;
	}

	/*
	 * @return Amount of pieces player has captured from opponent
	 */
	public int getCapturedPieces() {
		return capturedPieces;
	}

	/*
	 * Add one to captured amount
	 */
	public void incCapturedPieces() {
		capturedPieces++;
	}
}
