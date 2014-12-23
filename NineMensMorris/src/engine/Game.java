package engine;

import ui.GameDisplay;

public abstract class Game {
	//Display with which to interact
	private GameDisplay display;
	//Set of slots
	private Slot[][] slots;
	//Slot currently selected
	private Slot selectedSlot;
	//Game phases definition
	protected enum Phase { PLACING, MOVING, REMOVING }
	//Current phase
	private Phase phase = Phase.PLACING; 
	//Players. 1 is player one, 2 is player two, 0 points to active player
	private Player[] players;
	
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
	public Player getActivePlayer() {
		return players[0];
	}
	
	/*
	 * @param player Player whose turn it is to be
	 */
	private void setActivePlayer(Player player) {
		players[0] = player;
	}
	
	/*
	 * @return Player with given index (1 or 2)
	 */
	protected Player getPlayer(int val) {
		if (!(val==1||val==2)) {
			try {
				throw new Exception("Player value out of range");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return players[val];
	}
	
	/*
	 * @param slot Slot in which {@link activePlayer} is placing his piece
	 */
	protected void placePiece(Slot slot) {
		slot.setVal(getActivePlayer().getVal());
		getDisplay().fillSlot(slot.getSquare(), slot.getLocation(), getActivePlayer().getVal());
		getActivePlayer().placePiece();
	}
	
	/*
	 * Ends current turn, alternates active player
	 */
	protected void endTurn() {
		setActivePlayer(getPlayer(getActivePlayer().getOtherVal()));
		if (getActivePlayer().getUnplaced()>0) {
			setPhase(Phase.PLACING);
			getDisplay().setDisabled(getActivePlayer().getVal()+getActivePlayer().getOtherVal());
		}
		else {
			setPhase(Phase.MOVING);
			getDisplay().setDisabled(getActivePlayer().getOtherVal()+4);
		}
		clearSelectedSlot();
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
				getDisplay().setDisabled(getActivePlayer().getVal()+4);
				break;
			}
			endTurn();
			break;
		case MOVING:
			if (selectedSlot==null) {
				setSelectedSlot(slot);
				getDisplay().setDisabled(getActivePlayer().getOtherVal()+getActivePlayer().getVal());
				break;
			}
			if (slot.getVal()==getActivePlayer().getVal()) {
				clearSelectedSlot();
				getDisplay().setDisabled(getActivePlayer().getOtherVal()+4);
				break;
			}
			if (attemptMove(getSelectedSlot(),slot)) {
				if (checkMills(slot)) {
					setPhase(Phase.REMOVING);
					getDisplay().setDisabled(getActivePlayer().getVal()+4);
				}
				else
					endTurn();
			}
			else {
				getDisplay().clearSelectedSlots();
				getDisplay().selectSlot(selectedSlot.getSquare(), selectedSlot.getLocation());
				getDisplay().enableSelectedSlot();
			}
			break;
		case REMOVING:
			if (removePiece(slot))
				endTurn();
			else
				clearSelectedSlot();
			break;
		}
		getDisplay().repaint();
	}

	private Slot getSelectedSlot() {
		return selectedSlot;
	}

	private void setSelectedSlot(Slot slot) {
		selectedSlot = slot;
		if (slot == null)
			getDisplay().clearSelectedSlots();
		else
			getDisplay().selectSlot(slot.getSquare(), slot.getLocation());
	}
	
	protected void clearSelectedSlot() {
		setSelectedSlot(null);
	}

	/*
	 * Create new set of slots
	 * @param slots Empty matrix of given dimensions (squares, locations)
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
	 * Attempt to move a piece to an empty adjacent slot
	 * @param primary Slot with player's piece
	 * @param secondary Empty slot to move to
	 * @return Success. Fails on move to non-adjacent slot
	 */
	protected abstract boolean attemptMove(Slot primary, Slot secondary);
	
	/*
	 * Updates whether player is flying (when they have 3 pieces left on the board)
	 */
	protected void updateFlying(Player player) {
		System.out.println(player.getPiecesOut());
		if (player.getPiecesOut()<=3 && player.getUnplaced()==0)
			player.setFlying();
	}
	
	/*
	 * Remove a player's piece from the board
	 * @param slot Slot from which to remove
	 */
	private boolean removePiece(Slot slot) {
		if (checkMills(slot)) {
			for (int i = 0; i != slots.length; i++) {
				for (int j = 0; j != slots[i].length; j++) {
					if (slots[i][j].getVal() == getActivePlayer().getOtherVal() && !checkMills(slots[i][j]))
						return false;
				}
			}
		}
		slot.setVal(0);
		getDisplay().fillSlot(slot.getSquare(), slot.getLocation(), 0);
		getActivePlayer().addCaptured();
		getPlayer(getActivePlayer().getOtherVal()).removePiece();
		updateFlying(players[getActivePlayer().getOtherVal()]);
		return true;
	}
	
	/*
	 * @param player 1 for player one, 2 for player two, 0 for current player
	 * @return Amount of pieces remaining for player to place
	 */
	public int getPieces(int player) {
		return players[player].getUnplaced();
	}
	
	/*
	 * @param player 1 for player one, 2 for player two, 0 for current player
	 * @return Amount of pieces captured by player
	 */
	public int getCaptured(int player) {
		return players[player].getCaptured();
	}
	
	/*
	 * Init both players with starting number of pieces at the beginning of the game
	 * @param pieces Amount of pieces for each to start with
	 */
	protected void initPlayers(int pieces) {
		if (!(pieces>0)) {
			try {
				throw new Exception("Tried to init players with negative pieces");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		players = new Player[3];
		players[1] = new Player(1, pieces);
		players[2] = new Player(2, pieces);
		setActivePlayer(getPlayer(1));
	}
}
