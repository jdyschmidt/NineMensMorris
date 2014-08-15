package engine;

public class Player {
	private boolean flying = false;
	private final int val;
	private int pieces;
	private int capturedPieces = 0;
	
	public Player(int val, int pieces) {
		this.val = val;
		this.pieces = pieces;
	}

	public boolean isFlying() {
		return flying;
	}

	public void setFlying() {
		this.flying = true;
	}

	public int getVal() {
		return val;
	}

	public int getPieces() {
		return pieces;
	}

	public void decPieces() {
		this.pieces--;
	}

	public int getCapturedPieces() {
		return capturedPieces;
	}

	public void incCapturedPieces() {
		capturedPieces++;
	}
}
