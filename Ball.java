//Ball.java
import java.util.*;
import java.awt.*;
import java.awt.Rectangle;

public class Ball{
	private int x, y, radius, life, type;
	private double vx, vy, speed;
	private Rectangle ballRect;
	private Rectangle secondRect;

	public Ball(){
		this.y = 350;
		this.x = 400;
		this.radius = 10;
		this.speed = 1.5;
		this.vx = 0;
		this.vy = 2;
		this.life = 3;
		this.type =  0;		//0 -> regular ; 1 - > bomb
	}

	public int getX(){return this.x;};
	public int getY(){return this.y;};
	public int getRadius(){return this.radius;}
	public int getLives(){return this.life;}
	public void setType(int t){this.type = t;}
	public int getType(){return this.type;}

	public void move(){
		this.x += this.vx*this.speed;
		this.y += this.vy*this.speed;
	}

	public boolean intersect(int X, int Y, int W, int H){
		ballRect = new Rectangle(this.x-this.radius, this.y-this.radius, (this.radius*2), (this.radius*2));
		secondRect = new Rectangle(X,Y,W,H);
		return ballRect.intersects(secondRect);
	}

	public boolean hitOnSide(int X, int Y, int W, int H){
		if (this.y >= Y-(this.radius/2) && this.y <= Y+H+(this.radius/2)){
			if (this.x+this.radius/2 < X || this.x-radius/2 > X+W){
				return true;
			}
		}
		return false;
	}

	public void bouncePaddle(int X, int Y, int W, int H){
		vy *= -1;
		if (this.x < X+(W/4)){
			vx = -2.5;
		}
		else if (this.x < X+(W/2)){
			vx = -1.5;
		}
		else if (this.x < X+(3*W/4)){
			vx = 1.5;
		}
		else if (this.x <= X+W){
			vx = 2.5;
		}
		else if (this.x == X+(W/2)){
			vx = 0;
		}
	}

	public void bounceBrick(boolean sideHit){
		if (sideHit){
			vx *= -1;
		}
		else{
			vy *= -1;
		}
	}

	public void borderDetection(){
		if (this.x <= 0 || this.x >= 790){
			vx *= -1;
		}
		if (this.y <= 0){
			vy *= -1;
		}
	}


	public void newLife(){

		if (this.y > 600){
			this.x = 400;
			this.y = 350;
			this.vx = 0;
			this.vy = 2;
			this.type = 0;

			if (this.life == 0){
				this.life = 0;
				return;
			}
			this.life -= 1;
		}
	}

}