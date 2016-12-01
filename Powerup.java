//Powerup.java
import java.util.*;
import java.awt.*;
import java.util.Random;



public class Powerup{
	private int x, y, width, height, type;
	private Random rand;

	public int randInt(int min, int max) {
    	return rand.nextInt((max - min) + 1) + min;
	}

	public Powerup(int X, int Y, Paddle p){
		rand = new Random();
		this.y = Y;
		this.x = X;
		this.width = 10;
		this.height = 10;
		this.type = randInt(1, 4);  		// 1=Short ; 2=Long ; 3=Laser ; 4 bomb ;
		while(this.type == p.getType()){
			this.type = randInt(1, 4);
		}

	}

	

	public int getX(){return this.x;}
	public int getY(){return this.y;}
	public int getWidth(){return this.width;}
	public int getHeight(){return this.height;}
	public int getType(){return this.type;}


	public String getName(){
		if (this.type == 2){
			return "Long Paddle";
		}
		else if (this.type == 1){
			return "Short Paddle";
		}
		else if (this.type == 3){
			return "Laser Paddle";
		}
		else if (this.type == 4){
			return "Bomb Ball";
		}
		else return "Error 121";
	}

	public void move(){
		this.y += 2;		
	}
}
