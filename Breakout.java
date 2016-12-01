//Breakout.java
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.MouseInfo;
import java.util.*;
import javax.swing.Timer;


public class Breakout extends JFrame implements ActionListener{
	Timer myTimer;   
	GamePanel game;
		
    public Breakout() {
		super("Breakout!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,600);

		myTimer = new Timer(10, this);	 // trigger every 10 ms
		

		game = new GamePanel(this);
		add(game);

		setResizable(false);
		setVisible(true);

		game.buildBricks();	

    }

    public void start(){
		myTimer.start();
	}

	public void actionPerformed(ActionEvent evt){
		game.intersections();
		game.move();
		game.repaint();
	}


    public static void main(String[] arguments) {
		Breakout frame = new Breakout();	

    }
}


class GamePanel extends JPanel implements KeyListener{
	private int count = 0;
	private boolean []keys;
	private Image back;
	private Image[][] brickPics = new Image[5][5];
	private Image[] paddlePics = new Image[4];
	private Breakout mainFrame;
	private Paddle paddle = new Paddle();
	private Ball ball = new Ball();
	private ArrayList <Brick> bricks = new ArrayList();
	private ArrayList <Laser> lasers = new ArrayList();

	private Font font = new Font("Impact", Font.PLAIN, 40); 
	private Font font2 = new Font("Impact", Font.PLAIN, 12);
	private ArrayList <Powerup> powerups = new ArrayList(); 
	
	public GamePanel(Breakout m){
		keys = new boolean[KeyEvent.KEY_LAST+1];
		back = new ImageIcon("background.jpeg").getImage();

		mainFrame = m;
	    
		setSize(800,570);
        addKeyListener(this);

    }


    public void addNotify() {
        super.addNotify();
        requestFocus();
        mainFrame.start();
    }


    public void buildBricks(){
    	brickPics[0][0] = new ImageIcon("Brick10.png").getImage();
    	brickPics[1][0] = new ImageIcon("Brick20.png").getImage();
    	brickPics[1][1] = new ImageIcon("Brick21.png").getImage();
    	brickPics[2][0] = new ImageIcon("Brick30.png").getImage();
    	brickPics[2][1] = new ImageIcon("Brick31.png").getImage();
    	brickPics[2][2] = new ImageIcon("Brick32.png").getImage();
    	brickPics[3][0] = new ImageIcon("Brick40.png").getImage();
    	brickPics[3][1] = new ImageIcon("Brick41.png").getImage();
    	brickPics[3][2] = new ImageIcon("Brick42.png").getImage();
    	brickPics[3][3] = new ImageIcon("Brick43.png").getImage();
    	brickPics[4][0] = new ImageIcon("Brick50.png").getImage();
    	brickPics[4][1] = new ImageIcon("Brick51.png").getImage();
    	brickPics[4][2] = new ImageIcon("Brick52.png").getImage();
    	brickPics[4][3] = new ImageIcon("Brick53.png").getImage();
    	brickPics[4][4] = new ImageIcon("Brick54.png").getImage();

    	paddlePics[0] = new ImageIcon("paddleRegular.png").getImage();
    	paddlePics[1] = new ImageIcon("paddleShort.png").getImage();
    	paddlePics[2] = new ImageIcon("paddleLong.png").getImage();
    	paddlePics[3] = new ImageIcon("paddleLaser.png").getImage();

    	for (int i = 1; i <= 12; i++){
    		for (int j = 1; j <= 5; j++){
    			bricks.add(new Brick(i*45+100, j*25+100, 6-j));
    		}
    	}
    }


    public void intersections(){

    	//WHEN BALL HITS PADDLE

    	if (ball.intersect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight())){
			ball.bouncePaddle(paddle.getX(),paddle.getY(),paddle.getWidth(),paddle.getHeight());
		}

		//WHEN POWERUP HITS PADDLE

		for (int i = 0; i < powerups.size(); i++){
			if (paddle.intersect(powerups.get(i).getX(), powerups.get(i).getY(), powerups.get(i).getWidth(), powerups.get(i).getHeight())) {
				if (powerups.get(i).getType() != 4){
					paddle.setType(powerups.get(i).getType());
				}else{
					ball.setType(1);
				}
				powerups.remove(i);
			}
		}

		//WHEN LASER HITS BRICK

		for (int i = 0; i < lasers.size() ; i++){
			for (int j = 0; j < bricks.size(); j++){

				if (lasers.size() != 0 && lasers.get(i).intersect(bricks.get(j).getX(), bricks.get(j).getY(), bricks.get(j).getWidth(), bricks.get(j).getHeight())){
					bricks.get(j).hit(0);
					lasers.remove(i);
					if (i > 0){
						i--;
					}
				}
			}	
		}

		//BRICKS
		for (int i = 0; i < bricks.size(); i++){
			//WHEN BALL HITS BRICK
			if (ball.intersect(bricks.get(i).getX(), bricks.get(i).getY(), bricks.get(i).getWidth(), bricks.get(i).getHeight())){
				
				ball.bounceBrick(ball.hitOnSide(bricks.get(i).getX(), bricks.get(i).getY(), bricks.get(i).getWidth(), bricks.get(i).getHeight()));
				ball.move();


				bricks.get(i).hit(ball.getType());
				if (ball.getType() == 1){
					for (int j = 0; j < bricks.size(); j++){
						if (bricks.get(j).getX() > ball.getX()-50 && bricks.get(j).getX() < ball.getX()+50){
							if (bricks.get(j).getY() > ball.getY()-75 && bricks.get(j).getY() < ball.getY()+75){
								bricks.get(j).hit(1);
							}
						}
					}
					ball.setType(0);
				}

				
			}
		}
		//WHEN BRICK DIES
		for (int i = bricks.size()-1; i>=0; i--){
			if (bricks.get(i).isDead()){
				if (bricks.get(i).spawnPowerup()){
					powerups.add(new Powerup(bricks.get(i).getX(), bricks.get(i).getY(), paddle));
				}
				bricks.remove(i);
			}
		}

    }

	public void move(){
		if(keys[KeyEvent.VK_RIGHT] ){
			paddle.move(1);
		}
		if(keys[KeyEvent.VK_LEFT] ){
			paddle.move(-1);
		}

		ball.move();
		ball.newLife();
		ball.borderDetection();

		for (int i = 0; i < powerups.size(); i++){
			powerups.get(i).move();
		}

		for (int i = 0; i < lasers.size(); i++){
			lasers.get(i).move();
		}
		
		Point mouse = MouseInfo.getPointerInfo().getLocation();
		Point offset = getLocationOnScreen();
	}

	
    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
        if(e.getKeyCode() == e.VK_SPACE && paddle.getType() == 3){
        	lasers.add(new Laser(paddle.getX()+(paddle.getWidth()/2), paddle.getY()));
        	paddle.shotLaser();
        }
    }
    
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
    
    public void paintComponent(Graphics g){ 
    	count ++;	
    	g.drawImage(back,0,0,this);

    	g.setColor(Color.white);
    	g.setFont(font);
    	g.drawString("Lives --> "+ball.getLives(), 10, 50);
    	if (bricks.size() == 0){
    		g.setFont(font);
    		g.drawString("YOU WIN!!!", 300, 300);
    		return;
    	}
    	if (ball.getLives() != 0){
    		if (paddle.getType() == 3){
    			g.drawString("Laser Shots --> "+paddle.getLaserShots(), 500, 50);
    		}

	    	for (int i = 0; i < bricks.size(); i++){
	    		//g.setColor(bricks.get(i).getColor());
	    		//g.fillRect(bricks.get(i).getX(), bricks.get(i).getY(), bricks.get(i).getWidth(), bricks.get(i).getHeight());
	    		g.drawImage(brickPics[bricks.get(i).getType()-1][bricks.get(i).getDurability()-1], bricks.get(i).getX(), bricks.get(i).getY(), this);
	    	}  
			
			g.drawImage(paddlePics[paddle.getType()], paddle.getX(), paddle.getY(), this);
			g.setColor(Color.red);
			if (ball.getType() == 1 && count % 5 == 0){
				g.setColor(Color.white);
			}
	    	g.fillOval(ball.getX(),ball.getY(),ball.getRadius(),ball.getRadius());
	    	g.setColor(Color.white);
	    	g.setFont(font2);
	    	for (int i = 0; i < powerups.size(); i++){
	    		g.fillRect(powerups.get(i).getX(), powerups.get(i).getY(), powerups.get(i).getWidth(), powerups.get(i).getHeight());
	    		g.drawString(""+powerups.get(i).getName(), powerups.get(i).getX()-20, powerups.get(i).getY()-20);
	    		g.drawString("↓↓", powerups.get(i).getX(), powerups.get(i).getY()-5);
	    	}
	    	g.setColor(Color.red);
	    	for (int i = 0; i < lasers.size(); i++){
	    		g.fillRect(lasers.get(i).getX()-1, lasers.get(i).getY()-15, 2, 15);
	    	}
    	}
    	else{
    		g.setFont(font);
    		g.drawString("GAME OVER", 300, 300);
    	}
    	

    	
    }
}
















