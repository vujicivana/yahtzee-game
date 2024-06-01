package application;

import java.util.Random;

public class Dice {
	Random random = new Random();
	private int number;
	private boolean aside;
	
	public Dice() {
		this.number = 1;
		this.aside = false;
	}
	
	public int getNumber() {
		return this.number;
	}
	
	public boolean isAside() {
		return this.aside;
	}
	
	public void setNumber() {
		this.number = random.nextInt(6) + 1;
	}
	
	public void setAside(boolean a) {
		this.aside = a;
	}
}
