//Paddle.java
import java.util.*;
import java.awt.*;

public class Paddle{
	private int x, y, speed, width, height, type, laserShots;
	private Rectangle paddleRect;
	private Rectangle secondRect;

	public Paddle(){
		this.y = 560;
		this.x = 350;
		this.width = 75;
		this.height = 10;
		this.speed = 8;
		this.laserShots = 10;
		this.type = 0; 			// 0=Regular ; 1=Short ; 2=Long ; 3 = Laser

	}

	public int getX(){return this.x;}
	public int getY(){return this.y;}
	public int getWidth(){return this.width;}
	public int getHeight(){return this.height;}
	public int getType(){return this.type;}
	public int getLaserShots(){return this.laserShots;}

	public void setType(int t){
		this.laserShots = 10;
		this.type = t;
		if (this.type == 2){
			this.width = 115;
		}
		else if (this.type == 1){
			this.width = 55;
		}
		else{
			this.width = 75;
		}
	}

	public void move(int dir){
		this.x += dir*this.speed;		
	}

	public boolean intersect(int X, int Y, int W, int H){
		paddleRect = new Rectangle(this.x, this.y, this.width, this.height);
		secondRect = new Rectangle(X,Y,W,H);
		return paddleRect.intersects(secondRect);
	}

	public void shotLaser(){
		this.laserShots--;
		if (this.laserShots <= 0){
			this.type = 0;
		}
	}

}