//Laser.java
import java.util.*;
import java.awt.*;



public class Laser{
	private int x, y, width, height;
	private Rectangle laserRect, secondRect;

	public Laser(int X, int Y){
		this.y = Y;
		this.x = X;
		this.width = 10;
		this.height = 10;
	}

	

	public int getX(){return this.x;}
	public int getY(){return this.y;}
	public int getWidth(){return this.width;}
	public int getHeight(){return this.height;}


	public void move(){
		this.y -= 5;		
	}

	public boolean intersect(int X, int Y, int W, int H){
		laserRect = new Rectangle(this.x, this.y, this.width, this.width);
		secondRect = new Rectangle(X,Y,W,H);
		return laserRect.intersects(secondRect);
	}
}