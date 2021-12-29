package Game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.MenuKeyEvent;

import java.util.Random;
import java.util.Arrays;
import java.util.List;


public class GamePanel extends JPanel implements ActionListener  {
	private static final int GAME_WIDTH = 750; 
	private static final int GAME_HEIGHT = 550;
	private static int UNIT_SIZE = 25;
	private static final int GAME_UNITS = (GAME_WIDTH*GAME_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);

    private boolean running = true;
    
    final int snakeX[] = new int[GAME_UNITS];
	final int snakeY[] = new int[GAME_UNITS];
	
	int snakeBody = 6;
	
    List<Integer> speedList = Arrays.asList(60, 30, 90, 120, 20, 10);
	
	int speed = 60;
	
	int applesEaten;
	int appleX;
	int appleY;
	int speedAppleX;
	int speedAppleY;
	boolean speedAppleVisible = false;
	
	char direction = 'R';
	Timer timer;
	Random random;
    
	
	public GamePanel() {
		setFocusable(true);
		addKeyListener(new MyKeyAdapter());
		
		setBounds(50, 50, GAME_WIDTH, GAME_HEIGHT);
		setBackground(new Color(249, 255, 230));
//		setBackground(Color.black);
		
		random = new Random(); 
		startGame();
		
		
	}
	
	public void startGame() {
		running =true;
		timer = new Timer(speed,this);
		timer.start();
		newApple();
		speedApple();
	}
	
	public void stopGame() {
		running = false;
	}
	
	public void newApple(){
		appleX = random.nextInt((int)(GAME_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(GAME_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	
	public void speedApple() {
		speedAppleVisible = true;
		speedAppleX = random.nextInt((int)(GAME_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		speedAppleY = random.nextInt((int)(GAME_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	
	public void checkApple() {
		if((snakeX[0] == appleX) && (snakeY[0] == appleY)) {
			snakeBody++;
			if (speed==10) {
				applesEaten+=2;
			}
			else {
				applesEaten +=1;
			}
			newApple();
		}

		if((snakeX[0] == speedAppleX) && (snakeY[0] == speedAppleY)) {
			snakeBody++;
			applesEaten++;
			speed = speedList.get(random.nextInt(speedList.size()));
			timer.stop();
			timer.setDelay(speed);
			timer.start();
			System.out.println(speed);
			speedApple();
		}
	}
	
	public void move(){
		for(int i = snakeBody;i>0;i--) {
			snakeX[i] =snakeX[i-1];
			snakeY[i] = snakeY[i-1];
		}
		
		switch(direction) {
		case 'U':
			snakeY[0] -= UNIT_SIZE;
			break;
		case 'D':
			snakeY[0] += UNIT_SIZE;
			break;
		case 'L':
			snakeX[0] -= UNIT_SIZE;
			break;
		case 'R':
			snakeX[0] += UNIT_SIZE;
			break;
		}
		
	}
	
	public void checkCollisions() {
		for(int i = snakeBody; i>0; i--) {
			if ( (snakeX[0] == snakeX[i]) && (snakeY[0] == snakeY[i]) ) {
				running = false;
			}
		}
		if ((snakeY[0] < 0 ) || (snakeX[0] < 0 ) || (snakeX[0] > GAME_WIDTH) || (snakeY[0] > GAME_HEIGHT)) {
			running = false;
			}
		
		if (!running) {
			timer.stop();
		}
	}
	
	public void gameOver(Graphics g) {
		//Score board
		g.setColor(Color.red);
		g.setFont( new Font("Hi Bold",Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+applesEaten, (GAME_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		
		//Game Over text
		g.setColor(Color.red);
		g.setFont( new Font("Hi Bold",Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (GAME_WIDTH - metrics2.stringWidth("Game Over"))/2, GAME_HEIGHT/2);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		
		if (running) {
			g.setColor(Color.red);
			for(int i=0; i < GAME_HEIGHT/25; i++) {
				g.drawLine(0, i*25, GAME_WIDTH, i*25);
			}
		
			for(int i=0; i < GAME_WIDTH/25; i++) {
				g.drawLine(i*25, 0, i*25, GAME_HEIGHT);
			}
		
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			
			if(speedAppleVisible) {
				g.setColor(Color.pink);
				g.fillOval(speedAppleX, speedAppleY, UNIT_SIZE, UNIT_SIZE);
			}
		
			for(int i = 0; i < snakeBody; i++) {
				if(i==0) {
					g.setColor(Color.red);
					g.fillRect(snakeX[i], snakeY[i], UNIT_SIZE, UNIT_SIZE);
				}
				else {
					g.setColor(Color.green);
					g.fillRect(snakeX[i], snakeY[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			g.setColor(Color.red);
			g.setFont( new Font("Ink Free",Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+applesEaten, (GAME_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());

		}
		else  gameOver(g) ;
	}

		
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
}
