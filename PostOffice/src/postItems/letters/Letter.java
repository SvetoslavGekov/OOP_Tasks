package postItems.letters;

import people.citizen.Citizen;
import postItems.InvalidPostItemDataException;
import postItems.PostItem;

public class Letter extends PostItem {
	//Fields
	private static final double TAX = 0.5d;
	private static final int MAILMAN_PROCESSING_TIME = 10;
	
	//Constructors
	public Letter(Citizen sender, Citizen receiver) throws InvalidPostItemDataException {
		super(sender, receiver);
		setMailmanProcessTime(MAILMAN_PROCESSING_TIME);
	}
	
	//Methods
	@Override
	public String toString() {
		return String.format("%s", this.getClass().getSimpleName());
	}
	
	@Override
	public double calculateTax() {
		return Letter.TAX;
	}
}
