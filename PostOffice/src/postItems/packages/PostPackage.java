package postItems.packages;

import people.citizen.Citizen;
import postItems.InvalidPostItemDataException;
import postItems.PostItem;

public class PostPackage extends PostItem {
	
	//Fields
	private static final double TAX = 2.0d;
	private static final double OVERBURDENED_INCREASE = TAX*0.5d;
	private static final double FRAGILE_INCREASE = TAX*0.5d;
	private static final int MAX_SIDE_SIZE = 60;
	private static final int MAILMAN_PROCESSING_TIME = 15;
	private int length;
	private int width;
	private int height;
	private boolean isOverburdened;
	private boolean isFragile;
	
	//Constructors
	public PostPackage(Citizen sender, Citizen receiver, int length, int width, int height, boolean isFragile)
			throws InvalidPostItemDataException {
		super(sender, receiver);
		setLength(length);
		setWidth(width);
		setHeight(height);
		setFragile(isFragile);
		setOverburdened();
		setMailmanProcessTime(MAILMAN_PROCESSING_TIME);
	}

	//Methods
	@Override
	public double calculateTax() {
		double tax = PostPackage.TAX;
		if(this.isFragile) {
			tax += PostPackage.FRAGILE_INCREASE;
		}
		if(this.isOverburdened) {
			tax += PostPackage.OVERBURDENED_INCREASE;
		}
		return tax;
	}
	
	@Override
	public String toString() {
		return String.format("Package	IsFragile: %s	IsOverburdened: %s	L/W/H: %d,%d,%d", this.isFragile, this.isOverburdened,
				this.length, this.width, this.height);
	}
	
	//Setters
	private void setLength(int length) throws InvalidPackageDataException {
		if(length > 0) {
			this.length = length;
		}
		else {
			throw new InvalidPackageDataException("Invalid package length");
		}
	}

	private void setWidth(int width) throws InvalidPackageDataException {
		if(width > 0) {
			this.width = width;
		}
		else {
			throw new InvalidPackageDataException("Invalid package width");
		}
	}

	private void setHeight(int height) throws InvalidPackageDataException {
		if(height > 0) {
			this.height = height;
		}
		else {
			throw new InvalidPackageDataException("Invalid package height");
		}
	}

	private void setOverburdened() {
		if(this.length > MAX_SIDE_SIZE || this.width > MAX_SIDE_SIZE || this.height > MAX_SIDE_SIZE) {
			this.isOverburdened = true;
		}
		else {
			this.isOverburdened = false;
		}
	}

	private void setFragile(boolean isFragile) {
		this.isFragile = isFragile;
	}
	
	//Getters
	public boolean getIsFragile() {
		return this.isFragile;
	}
	
}
