package nineMensMorris;

public class NineGame extends Game {
	//0 for no piece, 1 for player 1, 2 for player 2
	private int[] slots;
	
	public NineGame() {
		setDisplay(new NineGameDisplay(this));
		slots = new int[24];
	}
	
	protected int getSlot(int index) throws Exception {
		if (!(0<=index && index<=23))
			throw new Exception("Accessed outside of slot bounds");
		return slots[index];
	}
}
