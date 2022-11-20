package view;

import java.awt.Dimension;
import java.awt.FlowLayout;

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
	
	/**
	 * Konstruktor
	 * @param party a parti.
	 */
	public PartyView(Party party) {
		super(new FlowLayout());
		this.party = party;
		
		this.boardView = new BoardView(party.getBoard(), this);
		this.boardView.setPreferredSize(new Dimension(400, 400));
		
		this.add(this.boardView);
	}
	
	/**
	 * @return A parti.
	 */
	public final Party getParty() { return this.party; }
}
