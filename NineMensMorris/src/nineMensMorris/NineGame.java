package nineMensMorris;

public class NineGame extends Game {	
	public NineGame() {
		setDisplay(new NineGameDisplay(this));
		setSlots(new Slot[3][8]);
	}
	
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

	//Assumes a piece has just been placed in <slot>
	private boolean checkMills(Slot slot) {
		int val = slot.getVal();
		int square = slot.getSquare();
		int location = slot.getLocation();
		switch (location) {
		case 0:
			return ((getSlot(square,1).getVal()==val && getSlot(square,2).getVal()==val)||(getSlot(square,6).getVal()==val && getSlot(square,7).getVal()==val));
		case 1:
			return ((getSlot(square,0).getVal()==val && getSlot(square,2).getVal()==val)||(getSlot(0,1).getVal()==val && getSlot(1,1).getVal()==val && getSlot(2,1).getVal()==val));
		case 2:
			return ((getSlot(square,0).getVal()==val && getSlot(square,1).getVal()==val)||(getSlot(square,3).getVal()==val && getSlot(square,4).getVal()==val));
		case 3:
			return ((getSlot(square,2).getVal()==val && getSlot(square,4).getVal()==val)||(getSlot(0,3).getVal()==val && getSlot(1,3).getVal()==val && getSlot(2,3).getVal()==val));
		case 4: 
			return ((getSlot(square,2).getVal()==val && getSlot(square,3).getVal()==val)||(getSlot(square,5).getVal()==val && getSlot(square,6).getVal()==val));
		case 5: 
			return ((getSlot(square,4).getVal()==val && getSlot(square,6).getVal()==val)||(getSlot(0,5).getVal()==val && getSlot(1,5).getVal()==val && getSlot(2,5).getVal()==val));
		case 6: 
			return ((getSlot(square,4).getVal()==val && getSlot(square,5).getVal()==val)||(getSlot(square,7).getVal()==val && getSlot(square,0).getVal()==val));
		case 7: 
			return ((getSlot(square,6).getVal()==val && getSlot(square,0).getVal()==val)||(getSlot(0,7).getVal()==val && getSlot(1,7).getVal()==val && getSlot(2,7).getVal()==val));
		default:
			return false;
		}
	}
	
	private void removePiece(Slot slot) {
		slot.setVal(0);
		getDisplay().fillSlot(slot.getSquare(), slot.getLocation(), 0);
	}
}
