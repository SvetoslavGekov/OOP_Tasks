package people;

public class person {
	
	
	static final class Brain{
		
	}
	
	public Brain brain;
	
	private static int count;
	private static boolean isZaspal;
	
	public person() {
		this.brain = new Brain();
	}
	
	static person praiTam(){
		if(!isZaspal) {
			System.out.println("Дигай се животно");
			return new person();
		}
		return null;
	}

}
