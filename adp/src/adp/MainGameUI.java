/**
 * 
 */
package adp;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;



/**
 * @author tieu my
 *
 */
public class MainGameUI extends JFrame {
	private static final int _HEIGHT = 500;
	private static final int _WIDTH = 500;
	private PongPanel pongPanel;
	
	public MainGameUI(){
		setPreferredSize(new Dimension(_WIDTH, _HEIGHT));
		setLayout(new BorderLayout());
		setTitle("Pong Game - K21T Ltd.");
		setResizable(false);   // khong cho phep thay doi kich thuoc
		pongPanel = new PongPanel();
		getContentPane().add(pongPanel, BorderLayout.CENTER);
		pack();
	}
	 public PongPanel getPanel() {
	        return pongPanel;
	    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainGameUI mainWindow= new MainGameUI();
		// set status as closing
		mainWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
		// show the window
		mainWindow.setVisible(true);
	}

}
