package view;

import java.awt.Dimension;
import javax.swing.BoxLayout;
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
	
	/**
	 * Konstruktor
	 * @param party a parti.
	 */
	public PartyView(Party party) {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.party = party;
		
		this.boardView = new BoardView(party.getBoard(), this);
		this.boardView.setPreferredSize(new Dimension(400, 400));
		
		this.stateText = new JLabel(this.party.getPartyState().toString());
		
		this.add(this.boardView);
		this.add(this.stateText);
	}
	
	/**
	 * @return A parti.
	 */
	public final Party getParty() { return this.party; }
	
	public final JLabel getStateText() { return this.stateText; }
}
