package eggs;

public enum Color {
	GREEN(1), RED(2), BLUE(3), YELLOW(4), ORANGE(5);
	
	private int id;
	
	Color(int id){
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
}
