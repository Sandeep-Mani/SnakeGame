package main.java;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String LEFT_MOUSE_IMAGE_PATH = "leftmouth.png";
	private static final String UP_MOUTH_IMAGE_PATH = "upmouth.png";
	private static final String DOWN_MOUTH_IMAGE_PATH = "downmouth.png";
	private static final String RIGHT_MOUTH_IMAGE_PATH = "rightmouth.png";
	private static final String SNAKE_IMAGE_PATH = "snakeimage.png";
	private static final String SNAKE_TITLE_IMAGE_OATH = "snaketitle.jpg";
	private static final String FOOD_IMAGE_PATH = "food.png";

	private ImageIcon rightMouth;
	private ImageIcon leftMouth;
	private ImageIcon upMouth;
	private ImageIcon downMouth;
	private ImageIcon snakeImage;
	private ImageIcon foodImage;
	private ImageIcon titleImage;

	private int[] snakeXlength = new int[750];
	private int[] snakeYlength = new int[750];

	private boolean up = false;
	private boolean left = false;
	private boolean down = false;
	private boolean right = false;

	private Timer timer;

	private int delay = 100;

	private int blockSize = 25;

	private int lengthSnake = 3;

	private int moves = 0;

	private int score = 0;
	
	private int level = 1;

	private boolean isGameOver = false;

	private int[] foodXpos = initiateFoodPositions(25, 850);
	private int[] foodYpos = initiateFoodPositions(75, 625);

	private Random random = new Random();
	private int xPos = random.nextInt(foodXpos.length);
	private int yPos = random.nextInt(foodYpos.length);

	public Gameplay() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
		
		rightMouth = new ImageIcon(this.getImageUrl(RIGHT_MOUTH_IMAGE_PATH));
		leftMouth = new ImageIcon(this.getImageUrl(LEFT_MOUSE_IMAGE_PATH));
		upMouth = new ImageIcon(this.getImageUrl(UP_MOUTH_IMAGE_PATH));
		downMouth = new ImageIcon(this.getImageUrl(DOWN_MOUTH_IMAGE_PATH));
		snakeImage = new ImageIcon(this.getImageUrl(SNAKE_IMAGE_PATH));
		foodImage = new ImageIcon(this.getImageUrl(FOOD_IMAGE_PATH));
		titleImage = new ImageIcon(this.getImageUrl(SNAKE_TITLE_IMAGE_OATH));
		
	}
	
	private URL getImageUrl(String path) {
		URL imageUrl =this.getClass().getClassLoader().getResource(path);
		return imageUrl;
	}

	@Override
	public void paint(Graphics graphics) {
		String fontFamily = "arial";
		if (moves == 0) {
			snakeXlength[2] = 50;
			snakeXlength[1] = 75;
			snakeXlength[0] = 100;

			snakeYlength[2] = 100;
			snakeYlength[1] = 100;
			snakeYlength[0] = 100;

		}

		// Draw title image border.
		graphics.setColor(Color.white);
		graphics.drawRect(24, 10, 851, 55);

		// Draw the title image.
		titleImage.paintIcon(this, graphics, 25, 11);

		// Border for Game Play.
		graphics.setColor(Color.WHITE);
		graphics.drawRect(24, 74, 851, 577);

		// Background For Game Play
		graphics.setColor(Color.black);
		graphics.fillRect(25, 75, 850, 575);

		rightMouth.paintIcon(this, graphics, snakeXlength[0], snakeYlength[0]);

		// Score
		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font(fontFamily, Font.PLAIN, 14));
		graphics.drawString("Score: " + score, 780, 30);

		// length
		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font(fontFamily, Font.PLAIN, 14));
		graphics.drawString("Length: " + lengthSnake, 780, 50);
		
		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font(fontFamily, Font.PLAIN, 14));
		graphics.drawString("Level: " + level, 700, 50);

		if (right)
			rightMouth.paintIcon(this, graphics, snakeXlength[0], snakeYlength[0]);

		if (left)
			leftMouth.paintIcon(this, graphics, snakeXlength[0], snakeYlength[0]);

		if (up)
			upMouth.paintIcon(this, graphics, snakeXlength[0], snakeYlength[0]);

		if (down)
			downMouth.paintIcon(this, graphics, snakeXlength[0], snakeYlength[0]);

		for (int i = 1; i < lengthSnake; i++) {
			snakeImage.paintIcon(this, graphics, snakeXlength[i], snakeYlength[i]);
		}

		// food image

		boolean isCollosion = (foodXpos[xPos] == snakeXlength[0]) && (foodYpos[yPos] == snakeYlength[0]);

		if (isCollosion) {
			lengthSnake++;
			score++;
			if(lengthSnake % 10 == 0) {
				delay -= 25;
				level++;
				timer.setDelay(delay);
			}
			xPos = random.nextInt(foodXpos.length);
			yPos = random.nextInt(foodYpos.length);
		}

		foodImage.paintIcon(this, graphics, foodXpos[xPos], foodYpos[yPos]);
		
		// Self collosion
		for (int ii = 1; ii < lengthSnake; ii++) {
			boolean selfCollosion = (snakeXlength[ii] == snakeXlength[0]) && (snakeYlength[ii] == snakeYlength[0]);
			if (selfCollosion) {

				up = false;
				down = false;
				left = false;
				right = false;

				graphics.setColor(Color.WHITE);
				graphics.setFont(new Font(fontFamily, Font.BOLD, 50));
				graphics.drawString("Game Over", 300, 300);

				graphics.setFont(new Font(fontFamily, Font.BOLD, 20));
				graphics.drawString("Space to RESTART.", 350, 340);

				isGameOver = true;
			}
		}

		graphics.dispose();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {

		if (isGameOver)
			return;

		moves++;
		
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_RIGHT) {
			if (!left)
				right = true;
			up = false;
			down = false;

		}

		if (keyCode == KeyEvent.VK_LEFT) {
			if (!right)
				left = true;
			up = false;
			down = false;

		}

		if (keyCode == KeyEvent.VK_UP) {
			if (!down)
				up = true;

			left = false;
			right = false;

		}

		if (keyCode == KeyEvent.VK_DOWN) {
			if (!up)
				down = true;
			left = false;
			right = false;

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (isGameOver && e.getKeyCode() == KeyEvent.VK_SPACE) {
			isGameOver = false;
			moves = 0;
			score = 0;
			level = 1;
			delay = 100;
			lengthSnake = 3;
			repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int xAxisStart = 25;
		int yAxisStart = 75;
		int xAxisEnd = 850;
		int yAxisEnd = 625;

		timer.start();

		if (up || down) {
			
			for (int r = lengthSnake - 1; r >= 0; r--) {
				snakeXlength[r + 1] = snakeXlength[r];
			}
			
			if (up)
				trunUp(yAxisStart, yAxisEnd);
			else
				trunDown(yAxisStart, yAxisEnd);
		}

		if (right || left) {
			
			for (int r = lengthSnake - 1; r >= 0; r--) {
				snakeYlength[r + 1] = snakeYlength[r];
			}
			
			if (right)
				trunRight(xAxisStart, xAxisEnd);
			else
				trunLeft(xAxisStart, xAxisEnd);
		}
		
		repaint();
	}

	private void trunLeft(int xAxisStart, int xAxisEnd) {

		for (int r = lengthSnake; r >= 0; r--) {
			if (r == 0) {
				snakeXlength[r] = snakeXlength[r] - blockSize;
			} else {
				snakeXlength[r] = snakeXlength[r - 1];
			}
			if (snakeXlength[r] < xAxisStart) {
				snakeXlength[r] = xAxisEnd;
			}
		}
	}

	private void trunRight(int xAxisStart, int xAxisEnd) {
		for (int r = lengthSnake; r >= 0; r--) {
			if (r == 0) {
				snakeXlength[r] = snakeXlength[r] + blockSize;
			} else {
				snakeXlength[r] = snakeXlength[r - 1];
			}
			if (snakeXlength[r] > xAxisEnd) {
				snakeXlength[r] = xAxisStart;
			}
		}
	}

	private void trunUp(int yAxisStart, int yAxisEnd) {
		for (int r = lengthSnake; r >= 0; r--) {
			if (r == 0) {
				snakeYlength[r] = snakeYlength[r] - blockSize;
			} else {
				snakeYlength[r] = snakeYlength[r - 1];
			}
			if (snakeYlength[r] < yAxisStart) {
				snakeYlength[r] = yAxisEnd;
			}
		}
	}

	private void trunDown(int yAxisStart, int yAxisEnd) {
		for (int r = lengthSnake; r >= 0; r--) {
			if (r == 0) {
				snakeYlength[r] = snakeYlength[r] + blockSize;
			} else {
				snakeYlength[r] = snakeYlength[r - 1];
			}
			if (snakeYlength[r] > yAxisEnd) {
				snakeYlength[r] = yAxisStart;
			}
		}
	}

	private int[] initiateFoodPositions(int start, int end) {
		int noOfPos = (end - start) / blockSize;
		int[] foodCorPos = new int[noOfPos];
		int j = 0;
		for (int i = start; i < end; i += blockSize) {
			foodCorPos[j++] = i;
		}
		return foodCorPos;
	}

}
