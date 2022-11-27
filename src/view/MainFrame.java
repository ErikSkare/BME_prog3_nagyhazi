package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import logic.Party;
import logic.players.RealPlayer;

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
	   
	    this.setJMenuBar(new MenuBar(this));
	    this.add(deck);
	    
	    setNoGameState();
	}
	
	public class OnNewPlayerGame implements ActionListener {

		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent e) {
			RealPlayer p1 = new RealPlayer();
			RealPlayer p2 = new RealPlayer();
			Party p = new Party(p1, p2);
			p1.setPlaying(p);
			p2.setPlaying(p);
			current = new PartyView(MainFrame.this, p);
			deck.add(current, "currentGame");
			layout.show(deck, "currentGame");
			current.requestDefaultFocus();
		}
		
	}
	
	private void setNoGameState() {
		JPanel panel = new JPanel(new BorderLayout());
		JLabel text = new JLabel("No game loaded!", JLabel.CENTER);
		panel.add(text, BorderLayout.CENTER);
		deck.add(panel, "noGame");
	}
}
