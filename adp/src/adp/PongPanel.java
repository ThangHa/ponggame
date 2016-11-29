package adp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Component;

public class PongPanel extends JPanel implements ActionListener, KeyListener      {
	private static final long serialVersionUID = -1097341635155021546L;

	//
	//public static final String smangePath = "D:/adp/hinh.JPG";
	//public JLabel contenBackground;
	ImageIcon imgBackground, imgBackgroundplay, imgBall, imgGameOver,imgButton;
	//
	
	private boolean showTitleScreen = true;
	private boolean playing = false;
	private boolean gameOver = false;

	/** Background. */
	private Color backgroundColor = Color.BLACK;

	/** State on the control keys. */
	private boolean upPressed = false; // thêm các boolean = false
	private boolean downPressed = false;
	private boolean wPressed = false;
	private boolean sPressed = false;//ms
	//private boolean leftPressed;
	/** The ball: position, diameter */
	private int ballX = 200; 
	private int ballY = 200; 
	private int diameter = 20;
	private int ballDeltaX = -1; // sửa 1 thành -1 
	private int ballDeltaY = 3;

	/** Player 1's paddle: position and size */
	private int playerOneX = 25; // sửa toa do
	private int playerOneY = 250; // toa do
	private int playerOneWidth = 10;	//chieu ngang cua vach do
	private int playerOneHeight = 60; //chieu dai vach do  50 -> 60

	/** Player 2's paddle: position and size */
	private int playerTwoX = 466;
	private int playerTwoY = 250;
	private int playerTwoWidth = 10;
	private int playerTwoHeight = 60;

	/** Speed of the paddle - How fast the paddle move. */
	private int paddleSpeed = 5;  // gioi han vach do di chuyen
	
	/** Player score, show on upper left and right. */
	private int playerOneScore = 0; // thêm =0
	private int playerTwoScore = 0; // thêm =0
	//ve ngau nhien mot hinh
	private int ToDisplay;
	private boolean showRandom;
	private int xRanBall;   // toa do ngau nhien
	///
	

	/** Construct a PongPanel. */
	public PongPanel() {
		setBackground(backgroundColor);
		///////////////////////////////////////////////////////////////////////////
		//this.contenBackground = new JLabel(new ImageIcon(getClass().getResource(name)))
		imgBackground = new ImageIcon("hinh/hinh1.jpg");
		imgBackgroundplay = new ImageIcon("hinh/Background1.jpg");
		imgBall = new ImageIcon("hinh/ball.png");
		imgGameOver = new ImageIcon("hinh/hinh_nen_2.jpg");
		imgButton = new ImageIcon("hinh/button.png");
		////////////////////////////////////////////////////////////
		// listen to key presses
		setFocusable(true);
		//setVisible(true);
		addKeyListener(this);

		// call step() 60 fps
		Timer timer = new Timer(1000 / 60, this);   // 60 -> 100
		timer.start();
		
	}

	/** Implement actionPerformed */
	public void actionPerformed(ActionEvent e) {
		step();
	}

	/** Repeated task */
	public void step() {

		if (playing) {

			/* Playing mode */
									///////////////////// loi upPressed down.... của 2 player<-> 
			// move player 1
			// Move up if after moving, paddle is not outside the screen
			if (wPressed && playerOneY - paddleSpeed > 0 ) { 
				
				playerOneY -= paddleSpeed;
				
			}
			// Move down if after moving paddle is not outside the screen
			if (sPressed && playerOneY + playerOneHeight + paddleSpeed < getHeight()) {
				
				playerOneY += paddleSpeed;
				
			}

			// move player 2
			// Move up if after moving paddle is not outside the screen
			if (upPressed && playerTwoY - paddleSpeed > 0) {
				
				playerTwoY -= paddleSpeed;
			}
			// Move down if after moving paddle is not outside the screen
			if (downPressed && playerTwoY + playerTwoHeight + paddleSpeed < getHeight()) {
				playerTwoY += paddleSpeed;
			}
		
			/*
			 * where will the ball be after it moves? calculate 4 corners: Left,
			 * Right, Top, Bottom of the ball used to determine whether the ball
			 * was out yet
			 */
			int nextBallLeft = ballX + ballDeltaX;
			int nextBallRight = ballX + diameter + ballDeltaX;
			// FIXME Something not quite right here
			int nextBallTop = ballY + ballDeltaY;  // thêm deltaY (cong them khoang di chuyen)
			int nextBallBottom = ballY + diameter + ballDeltaY ; // thêm deltaY

			// Player 1's paddle position
			int playerOneRight = playerOneX + playerOneWidth;
			int playerOneTop = playerOneY;
			int playerOneBottom = playerOneY + playerOneHeight; 

			// Player 2's paddle position
			float playerTwoLeft = playerTwoX ;//- playerOneWidth;
			float playerTwoTop = playerTwoY;
			float playerTwoBottom = playerTwoY + playerTwoHeight;   

			// ball bounces off top and bottom of screen
			if (nextBallTop < 0 || nextBallBottom > getHeight()) {
				ballDeltaY *= -1; // qua bong bat lai   -3
				Sound.play("Sound/sound.wav");
			}

			// will the ball go off the left side?
			if (nextBallLeft < playerOneRight) {
				// is it going to miss the paddle?
				if (nextBallTop > playerOneBottom || nextBallBottom < playerOneTop) {

					playerTwoScore ++; // sửa playeronescore thanh playertwiscore

					// Player 2 Win, restart the game
					if (playerTwoScore >= 3) {   //////////// xet them playerTwoScore >=3
						playing = false;
						gameOver = true;
					}
					ballX = 200;
					ballY = 200;
				} else {
					// If the ball hitting the paddle, it will bounce back
					// FIXME Something wrong here
					ballDeltaX *= -1; // sửa 1 thành -1 nghich dao giá trị denta x
					//Sound 
					Sound.play("Sound/sound.wav");
				}
			}

			// will the ball go off the right side?
			if (nextBallRight > playerTwoLeft) {
				// is it going to miss the paddle?
				if (nextBallTop > playerTwoBottom || nextBallBottom < playerTwoTop) {

					playerOneScore++;

					// Player 1 Win, restart the game
					if (playerOneScore >= 3) {
						playing = false;
						gameOver = true;
					}
					ballX = 200;
					ballY = 200;
				} else {

					// If the ball hitting the paddle, it will bounce back
					// FIXME Something wrong here
					///////////////////////////////
					ballDeltaX *= -1; // sửa 1 bằng trừ 1
					//Sound 
					Sound.play("Sound/sound.wav");
				}
			}

			// move the ball
			ballX += ballDeltaX;
			ballY += ballDeltaY;
			
			//show random
			
		}

		// stuff has moved, tell this JPanel to repaint itself
		repaint();
	}

	/** Paint the game screen. */
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		if (showTitleScreen) {
			////////////////////////////////////////////////////////////
			//Background
			g.drawImage(imgBackground.getImage(), 0, 0, 500, 500, null);
			////////////////////////////////////////////////////////////
			/* Show welcome screen */

			// Draw game title and start message
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			g.setColor(Color.WHITE);
			g.drawString("Pong Game", 150, 200);

			// FIXME Welcome message below show smaller than game title
			g.setColor(Color.WHITE);
			g.drawString("Press 'P' to play.", 100, 60);    //// chỉnh lại cho ngay
		} else if (playing) {

			/* Game is playing */
			////////////////////////////////////////////////////////////////////////////////////
			//Background - play
			g.drawImage(imgBackgroundplay.getImage(), 0, 0, 500, 500, null);
			///////////////////////////////////////////////////////////////////////////////////////
			// set the coordinate limit
			int playerOneRight = playerOneX + playerOneWidth;
			
			int playerTwoLeft = playerTwoX;
			
			// draw dashed line down center
			for (int lineY = 0; lineY < getHeight(); lineY += 50) {  // net dut
				g.drawLine(250, lineY, 250, lineY + 25);
			}

			// draw "goal lines" on each side
			g.setColor(Color.GREEN);
			g.drawLine(playerOneRight, 0, playerOneRight, getHeight()); ////////// set thêm màu xanh
			
			g.drawLine(playerTwoLeft, 0, playerTwoLeft, getHeight());   ///////////    nt

			// draw the scores
			
			g.setColor(Color.BLUE);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			g.drawString(String.valueOf(playerOneScore), 100, 100); // Player 1
																	// score
			g.drawString(String.valueOf(playerTwoScore), 400, 100); // Player 2  // Sua one thanh two de diem cong vao play2
																	// score

			// draw the ball
			//g.setColor(Color.RED);
			//g.fillOval(ballX, ballY, diameter, diameter);
			g.drawImage(imgBall.getImage(),ballX, ballY, diameter+20, diameter+20,null);
			//g.drawImage(ballX, ballY, diameter, diameter);

			// draw the paddles
			g.drawImage(imgButton.getImage(), playerOneX, playerOneY, playerOneWidth, playerOneHeight, null);
			g.drawImage(imgButton.getImage(), playerTwoX, playerTwoY, playerTwoWidth, playerTwoHeight, null);
			/*
			g.fillRect(playerOneX, playerOneY, playerOneWidth, playerOneHeight);
			g.fillRect(playerTwoX, playerTwoY, playerTwoWidth, playerTwoHeight);
			*/
			
			//show random
		} else if (gameOver) {
			// Background
			
			g.setColor(Color.RED);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
			g.drawImage(imgGameOver.getImage(), 0, 0, 500, 500, null);
			g.drawString("Game Over", 290, 350);
			
			/* Show End game screen with winner name and score */

			// Draw scores
			// TODO Set Blue color
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			g.setColor(Color.red);
			g.drawString(String.valueOf(playerOneScore), 100, 100);
			g.drawString(String.valueOf(playerTwoScore), 400, 100);

			// Draw the winner name
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			if (playerOneScore > playerTwoScore) {
				g.setColor(Color.WHITE);
				g.drawString("Player 1 Wins!", 130, 200);
			} else {
				g.setColor(Color.WHITE);
				g.drawString("Player 2 Wins!", 130, 200);
			}

			// Draw Restart message
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
			g.setColor(Color.WHITE);
			g.drawString(" Press space to restart ", 150, 400); // code để game play again
			// TODO Draw a restart message
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		if (showTitleScreen) {
			if (e.getKeyChar() == 'p'|| e.getKeyChar() == 'P') {    //////// thêm || P
				showTitleScreen = false;
				playing = true;
			}
		} else if (playing) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				upPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				downPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_W) {
				wPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				sPressed = true;
			}
		} else if (gameOver && e.getKeyCode() == KeyEvent.VK_SPACE) {
			gameOver = false;
			showTitleScreen = true;
			playerOneY = 250;
			playerTwoY = 250;
			ballX = 250;
			ballY = 250;
			////////////////////// reset điểm  
			//playerOneScore = 0;
			//playerTwoScore = 0;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			downPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			wPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			sPressed = false; // sửa wPressed thành sPressed			
		}
	}

}
