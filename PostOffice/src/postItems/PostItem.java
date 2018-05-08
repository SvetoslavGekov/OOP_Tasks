package postItems;

import people.citizen.Citizen;

public abstract class PostItem {
	// Fields
	private Citizen sender;
	private Citizen receiver;
	private int mailmanProcessTime;
	
	//Constructor
	public PostItem(Citizen sender, Citizen receiver) throws InvalidPostItemDataException {
		setSender(sender);
		setReceiver(receiver);
	}
	
	//Methods
	
	public abstract double calculateTax();
	
	//Setters
	private void setSender(Citizen sender) throws InvalidPostItemDataException {
		if(sender != null) {
			this.sender = sender;
		}
		else {
			throw new InvalidPostItemDataException("Invalid post item sender.");
		}
	}

	private void setReceiver(Citizen receiver) throws InvalidPostItemDataException {
		if(receiver != null) {
			this.receiver = receiver;
		}
		else {
			throw new InvalidPostItemDataException("Invalid post item receiever.");
		}
	}
	
	protected void setMailmanProcessTime(int mailmanProcessTime) throws InvalidPostItemDataException {
		if(mailmanProcessTime >0) {
			this.mailmanProcessTime = mailmanProcessTime;
		}
		else {
			throw new InvalidPostItemDataException("Invalid post item mailman processing time.");
		}
	}
	
	//Getters
	public Citizen getReceiver() {
		return this.receiver;
	}
	
	public Citizen getSender() {
		return this.sender;
	}
	
	public int getMailmanProcessTime() {
		return this.mailmanProcessTime;
	}
}
