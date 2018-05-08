package eggs;

import java.time.LocalDateTime;

import people.Kid;

public class Egg {
	// Fields
	private static int currentId = 1;
	private int id;
	private EggType eggType;
	private Color color;
	private boolean isColored;
	private boolean isPartyColored;
	private Kid kid;
	private LocalDateTime coloredTime;
	private int jar_id; 

	// Constructor
	public Egg(EggType eggType) {
		this.id = currentId++;
		setEggType(eggType);
		System.out.println(this);
	}

	// Methods
	@Override
	public String toString() {
		return String.format("Egg: %d	Type: %s", this.id, this.eggType);
	}

	// Setters
	public void setEggType(EggType eggType) {
		if (eggType != null) {
			this.eggType = eggType;
		}
	}

	public void setColor(Color color) {
		if (color != null) {
			this.color = color;
		}
	}

	public void setPartyColored(boolean isPartyColored) {
		this.isPartyColored = isPartyColored;
	}

	// Getters
	public int getId() {
		return id;
	}

	public EggType getEggType() {
		return eggType;
	}

	public Color getColor() {
		return color;
	}

	public void setKid(Kid kid) {
		if(kid != null) {
			this.kid = kid;
		}
	}

	public boolean isPartyColored() {
		return isPartyColored;
	}
	
	public void setColored(boolean isColored) {
		this.isColored = isColored;
	}

	public void setJarId(int jar_id) {
		if(jar_id > 0) {
			this.jar_id = jar_id;
		}
	}
	
	public synchronized void getColored(Color color, int jarId) {
		// Sleep the egg while it's being colored
		Thread eggColoringThread = new Thread(()->{
			try {
				Thread.sleep(eggType.getProcessingTime() * 1000);
				setColor(color);
				setColored(true);
				setColoredTime(LocalDateTime.now());
				setJarId(jarId);
				synchronized (this) {
					notifyAll();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		eggColoringThread.start();
	}

	public void setColoredTime(LocalDateTime coloredTime) {
		this.coloredTime = coloredTime;
	}
	
	public LocalDateTime getColoredTime() {
		return coloredTime;
	}
	
	public boolean isReady() {
		if (isColored) {
			return true;
		}
		return false;
	}

	public String getInfo() {
		return String.format("Egg: %d	Color:%s ColoredBy:%s	IsPartyColored:%s	ColoringTime:%s%n", this.id, this.color,
				this.getKid().getName(), this.isPartyColored, this.coloredTime);
	}

	public int getJar_id() {
		return jar_id;
	}
	
	public Kid getKid() {
		return kid;
	}
}
