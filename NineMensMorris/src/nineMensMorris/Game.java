package nineMensMorris;

public abstract class Game {
	private GameDisplay display;
	//0 for none, 1 for player 1, 2 for player 2
	private Slot[][] slots;
	private Slot selectedSlot;
	protected enum Phase { PLACING, MOVING, REMOVING }
	private Phase phase = Phase.PLACING; 
	//Turn tracker
	private Player player1;
	private Player player2;
	private Player activePlayer;
	
	protected GameDisplay getDisplay() {
		return display;
	}
	
	protected Game setDisplay(GameDisplay newDisplay) {
		display = newDisplay;
		return this;
	}
	
	protected Phase getPhase() {
		return phase;
	}
	
	protected void setPhase(Phase phase) {
		this.phase = phase;	
	}
	
	protected Player getActivePlayer() {
		return activePlayer;
	}
	
	protected void placePiece(Slot slot) {
		slot.setVal(getActivePlayer().getVal());
		getDisplay().fillSlot(slot.getSquare(), slot.getLocation(), getActivePlayer().getVal());
	}
	
	protected void endTurn() {
		activePlayer = (activePlayer==player1)?player2:player1;
		setPhase(Phase.PLACING);
	}
	
	protected void clickPosition(int square, int location) {
		System.out.println("Square "+square+", location "+location);
	}

	protected void setSlots(Slot[][] slots) {
		this.slots = slots;
		for (int i = 0; i != slots.length; i++) {
			for (int j = 0; j != slots[i].length; j++) {
				this.slots[i][j] = new Slot(i, j);
			}
		}
	}
	
	protected Slot getSlot(int square, int location) {
		if (!(0<=square && square<=slots.length && 0<=location && location<=slots[square].length)) {
			try {
				throw new Exception("Slot value out of range");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return slots[square][location];
	}
	
	protected void initPlayers(int pieces) {
		if (!(pieces>0)) {
			try {
				throw new Exception("Init player with negative pieces");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		player1 = new Player(1, pieces);
		player2 = new Player(2, pieces);
		activePlayer = player1;
	}
}
