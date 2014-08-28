package engine;

public class Slot {
	
	//Value of contained piece: 0 for empty, 1 for player one, 2 for player two
	private int val;
	//Square where the slot is located
	private int square;
	//Location in square where the slot is located
	private int location;

	/*
	 * Create Slot with default filled value of 0
	 * @param square Square where the slot is located
	 * @param location Location in that square
	 */
	public Slot(int square, int location) {
		setSquare(square);
		setLocation(location);
		val = 0;
	}
	
	/*
	 * Create Slot with starting filled value. Not sure why you'd want to do this.
	 * @param square Square where the slot is located
	 * @param location Location in that square
	 * @param val Filled value: 0 for none, 1 for player one, 2 for player two
	 */
	public Slot(int square, int location, int val) {
		setSquare(square);
		setLocation(location);
		setVal(val);
	}
	
	/*
	 * @return Filled value of the slot
	 */
	public int getVal() {
		return val;
	}
	
	/*
	 * @param val Value to fill slot with, required that 0 <= val <= 2
	 */
	public void setVal(int val) {
		if (!(0<=val && val<=2)) {
			try {
				throw new Exception("Slot value out of range");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.val = val;
	}

	/*
	 * @return Square where the slot is located
	 */
	public int getSquare() {
		return square;
	}
	
	/*
	 * Maybe should add some exceptions
	 * @param square Square where the slot is located
	 */
	public void setSquare(int square) {
		this.square = square;
	}

	/*
	 * @return Location in square where the slot is located
	 */
	public int getLocation() {
		return location;
	}

	/*
	 * Also might need exceptions
	 * @param location Location in square where the slot is located
	 */
	public void setLocation(int location) {
		this.location = location;
	}
	
	/*
	 * Check if this is beside in the same square
	 */
	public boolean isNextInSquare(Slot slot) {
		return ((this.getLocation() +- 1 == slot.getLocation()) || ((this.getLocation()==7 && slot.getLocation()==0) || (this.getLocation()==0 && slot.getLocation()==7)));
	}
}
