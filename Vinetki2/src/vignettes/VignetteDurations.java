package vignettes;

public enum VignetteDurations {
	DAY(1), MONTH(30), YEAR(365);
	
	//Fields
	private int duration;

	//Constr
	private VignetteDurations(int duration) {
		this.duration = duration;
	}

	public int getDuration() {
		return this.duration;
	}
	
}
