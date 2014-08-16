package engine;

import ui.GameDisplay;

public abstract class Game {
	//Display with which to interact
	private GameDisplay display;
	//Set of slots
	private Slot[][] slots;
	//Slot currently selected (unused so far)
	private Slot selectedSlot;
	//Game phases definition
	protected enum Phase { PLACING, MOVING, REMOVING }
	//Current phase
	private Phase phase = Phase.PLACING; 
	//Players
	private Player player1;
	private Player player2;
	//Current player (turn tracker)
	private Player activePlayer;
	
	/*
	 * @return Display interacted with by the engine
	 */
	public GameDisplay getDisplay() {
		return display;
	}
	
	/*
	 * @param display Display to interact with. Set once from constructor in subclass
	 */
	protected void setDisplay(GameDisplay display) {
		this.display = display;
	}
	
	/*
	 * @return Current phase of the game
	 */
	protected Phase getPhase() {
		return phase;
	}
	
	/*
	 * @param phase Phase of the game to which to set
	 */
	protected void setPhase(Phase phase) {
		this.phase = phase;	
	}
	
	/*
	 * @return Current player taking turn
	 */
	protected Player getActivePlayer() {
		return activePlayer;
	}
	
	/*
	 * @param slot Slot in which {@link activePlayer} is placing his piece
	 */
	protected void placePiece(Slot slot) {
		slot.setVal(getActivePlayer().getVal());
		getDisplay().fillSlot(slot.getSquare(), slot.getLocation(), getActivePlayer().getVal());
	}
	
	/*
	 * Ends current turn, alternates {@link activePlayer}
	 */
	protected void endTurn() {
		activePlayer = (activePlayer==player1)?player2:player1;
		setPhase(Phase.PLACING);
	}
	
	/*
	 * Called by the display when a slot is clicked. 
	 * May need to be overridden depending on game logic necessities, but hopefully can outsource functions that will be overridden
	 * @param square Square in which slot clicked is located
	 * @param location Location in square in which slot clicked is located
	 */
	public void clickPosition(int square, int location) {
		Slot slot = getSlot(square, location);
		switch (getPhase()) {
		case PLACING:
			placePiece(slot);
			if (checkMills(slot)) {
				setPhase(Phase.REMOVING);
				break;
			}
			endTurn();
			break;
		case MOVING:
			break;
		case REMOVING:
			removePiece(slot);
			endTurn();
			break;
		}
	}

	/*
	 * Create new set of slots
	 * @param slots Empty matrix of given dimensions
	 */
	protected void setSlots(Slot[][] slots) {
		this.slots = slots;
		for (int i = 0; i != slots.length; i++) {
			for (int j = 0; j != slots[i].length; j++) {
				this.slots[i][j] = new Slot(i, j);
			}
		}
	}
	
	/*
	 * @param square Square in which slot is located
	 * @param location Location in square in which slot is located
	 * @return Slot in given location
	 */
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
	
	/*
	 * Check for mills around given slot. Piece is assumed to just have been placed there.
	 * @param slot Slot around which to check
	 */
	protected abstract boolean checkMills(Slot slot);
	
	/*
	 * Remove a player's piece from the board
	 * @param slot Slot from which to remove
	 */
	private void removePiece(Slot slot) {
		slot.setVal(0);
		getDisplay().fillSlot(slot.getSquare(), slot.getLocation(), 0);
	}
	
	/*
	 * Init both players with starting number of pieces at the beginning of the game
	 * @param pieces Amount of pieces for each to start with
	 */
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