package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = 8456560429229699542L;
	
	private PartyView current = null;
	
	private CardLayout layout;
	
	private JPanel deck;

	public MainFrame() {
		this.setSize(new Dimension(700, 600));
		this.setResizable(false);
		this.setLocationByPlatform(true);
		
		layout = new CardLayout();
		deck = new JPanel(layout);
	   
	    this.setJMenuBar(new MenuBar());
	    this.add(deck);
	    
	    setNoGameState();
	}
	
	private void setNoGameState() {
		JPanel panel = new JPanel(new BorderLayout());
		JLabel text = new JLabel("No game loaded!", JLabel.CENTER);
		panel.add(text, BorderLayout.CENTER);
		deck.add(panel, "noGame");
	}
}
