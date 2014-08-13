package nineMensMorris;

public abstract class Game {
	private GameDisplay display;
	//0 for none, 1 for player 1, 2 for player 2
	private Slot[][] slots;
	private Slot selectedSlot;
	protected enum Phase { PLACING, MOVING, REMOVING }
	private Phase phase = Phase.PLACING; 
	//Turn tracker
	private int player = 1;
	
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
	
	protected int getPlayer() {
		return player;
	}
	
	protected void placePiece(Slot slot) {
		slot.setVal(getPlayer());
		getDisplay().fillSlot(slot.getSquare(), slot.getLocation(), getPlayer());
	}
	
	protected void endTurn() {
		player = (player==1)?2:1;
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
	
	//Add exception
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
}
