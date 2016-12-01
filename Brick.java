//Brick.java
import java.util.*;
import java.awt.*;

public class Brick{
	private int x, y, width, height, durability, type, r;
	private Rectangle ballRect, brickRect;
	private Color[] colors = {Color.green, Color.yellow, Color.orange, Color.lightGray, Color.gray, Color.darkGray};
	private Color color;
	private Random rand;

	public int randInt(int min, int max) {
    	return rand.nextInt((max - min) + 1) + min;
	}

	public Brick(int X, int Y, int durability){
		rand = new Random();
		this.y = Y;
		this.x = X;
		this.durability = durability;
		this.width = 40;
		this.height = 20;
		this.type = durability;
	}

	public int getX(){return this.x;}
	public int getY(){return this.y;}
	public int getWidth(){return this.width;}
	public int getHeight(){return this.height;}
	public int getDurability(){return this.durability;}
	public int getType(){return this.type;}

	public void hit(int i){
		this.durability -= 1;
		if (i == 1){
			this.durability = 0;
		}
	}

	public boolean spawnPowerup(){
		r = randInt(1,3);
		if (r == 1){
			return true;
		}
		return false;
	}

	public boolean isDead(){
		if (this.durability == 0){
			return true;
		}
		return false;
	}

}
