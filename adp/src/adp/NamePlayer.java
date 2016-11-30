/**
 * 
 */
package adp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * @author Hoa Tran
 *
 * T150112
 */

public class NamePlayer extends JFrame {
	JButton btnOk = new JButton("Ok");
	JTextField txt1 = new JTextField(),
			txt2 = new JTextField();
	JLabel lblplayer1 = new JLabel("Player 1:"),
			lblplayer2 = new JLabel("Player 2:");
	public NamePlayer(){
		setSize(300,200);
		setLayout(null);
		// add component
		add(lblplayer1);
		add(lblplayer2);
		add(txt1);
		add(txt2);
		add(btnOk);
		lblplayer1.setBounds(10, 30, 50, 25);
		lblplayer2.setBounds(10, 60, 50, 25);
		txt1.setBounds(70, 30, 150, 25);
		txt2.setBounds(70, 60, 150, 25);
		btnOk.setBounds(100, 100, 70, 30);
		
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				PongPanel d=new PongPanel();
				d.setName(txt1.getText(), txt2.getText());
				setVisible(false); //you can't see me!
				dispose(); //Destroy the JFrame object
			}
		});
//		ActionListener action = new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				if(e.getSource() == btnOk){
//					//closeApplication();
//				}
//			}
//		};
		//btnOk.addActionListener(action);
		// Set Event Exit Program
//		this.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e) {
//				closeApplication();
//			}
//
//			
//		});
//			}
//	public void closeApplication() {
//		int result = JOptionPane.showConfirmDialog(null, "Do you want to exit ?", "Confirm", JOptionPane.YES_NO_OPTION,
//				JOptionPane.QUESTION_MESSAGE);
//		if (result == JOptionPane.YES_OPTION) {
//			System.exit(0);
//		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NamePlayer mainW = new NamePlayer();
		mainW.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainW.setVisible(true);
	}

}
