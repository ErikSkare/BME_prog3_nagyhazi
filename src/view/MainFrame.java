package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import logic.Party;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = 8456560429229699542L;
	
	public static File searchDir = new File(".");
	
	private PartyView current = null;
	
	private CardLayout layout;
	
	private JPanel deck;

	public MainFrame() {
		this.setSize(new Dimension(700, 600));
		this.setResizable(false);
		this.setLocationByPlatform(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		layout = new CardLayout();
		deck = new JPanel(layout);
	   
	    this.setJMenuBar(new MenuBar(this, 
	    	(p) -> { setCurrentGame(p); }, 
	    	(p) -> { setCurrentGame(p); },
	    	(p) -> { setCurrentGame(p); }
	    ));
	    this.add(deck);
	    
	    setNoGameState();
	}
	
	private void setNoGameState() {
		JPanel panel = new JPanel(new BorderLayout());
		JLabel text = new JLabel("No game loaded!", JLabel.CENTER);
		panel.add(text, BorderLayout.CENTER);
		deck.add(panel, "noGame");
	}
	
	@SuppressWarnings("deprecation")
	private void setCurrentGame(Party p) {
		current = new PartyView(MainFrame.this, p);
		deck.add(current, "currentGame");
		layout.show(deck, "currentGame");
		current.requestDefaultFocus();
	}
}
