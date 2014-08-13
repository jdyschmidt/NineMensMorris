package nineMensMorris;

public class Slot {
	
	//Value of contained piece: 0 for empty, 1 for player one, 2 for player two
	private int val;
	private int square;
	private int location;
	
	public Slot(int square, int location, int val) {
		setSquare(square);
		setLocation(location);
		setVal(val);
	}
	
	public Slot(int square, int location) {
		setSquare(square);
		setLocation(location);
		val = 0;
	}
	
	public int getVal() {
		return val;
	}
	
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

	public int getSquare() {
		return square;
	}
	
	//maybe should add some exceptions
	public void setSquare(int square) {
		this.square = square;
	}

	public int getLocation() {
		return location;
	}

	//also might need exceptions
	public void setLocation(int location) {
		this.location = location;
	}
}
