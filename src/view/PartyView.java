package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import logic.Party;

/**
 * @author Skáre Erik
 * Egy partihoz tartozó nézet.
 */
public class PartyView extends JPanel {
	
	private static final long serialVersionUID = 1L;

	/**
	 * A parti, amihez a nézet tartozik.
	 */
	private Party party;
	
	/**
	 * A tábla nézete.
	 */
	private BoardView boardView;
	
	private JLabel stateText;
	
	private JButton drawButton;
	
	/**
	 * Konstruktor
	 * @param party a parti.
	 */
	public PartyView(Party party) {
		super();
		this.setLayout(new BorderLayout());
		this.party = party;
		
		this.boardView = new BoardView(party.getBoard(), this);
		this.boardView.setPreferredSize(new Dimension(400, 400));
		
		this.stateText = new JLabel(this.party.getPartyState().toString());
		this.stateText.setHorizontalAlignment(JLabel.CENTER);
		this.drawButton = new JButton("Make draw");
		this.drawButton.addActionListener(new OnDrawButtonClick());
		
		this.add(this.boardView, BorderLayout.CENTER);
		this.add(this.stateText, BorderLayout.NORTH);
		this.add(this.drawButton, BorderLayout.SOUTH);
	}
	
	/**
	 * @return A parti.
	 */
	public final Party getParty() { return this.party; }
	
	public final JLabel getStateText() { return this.stateText; }
	
	class OnDrawButtonClick implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			party.makeDraw();
			stateText.setText(party.getPartyState().toString());
			drawButton.setEnabled(false);
		}
		
	}
}
