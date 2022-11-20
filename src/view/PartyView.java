package view;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;

import logic.Field;
import logic.Move;
import logic.Party;
import logic.Piece;

/**
 * @author Skáre Erik
 * A partihoz tartozó nézet.
 */
public class PartyView extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * A megjelenítés állapota.
	 */
	enum State {
		THINKING, // nincsenek megjelenítve lehetséges lépések.
		MOVING	  // a lehetséges lépések meg vannak jelenítve.
	}
	
	/**
	 * Az aktuális állapot.
	 */
	private State state;
	
	/**
	 * A parti, amit megjelenít.
	 */
	private Party party;
	
	/**
	 * A mezőkhöz tartozó nézetek.
	 */
	private FieldView[][] fieldViews;
	
	/**
	 * Az éppen kiválasztott bábu.
	 */
	private Piece activePiece;
	
	/**
	 * @param party a parti.
	 */
	public PartyView(Party party) {
		super(new GridLayout(0, 8));
		this.state = State.THINKING;
		this.party = party;
		this.activePiece = null;
		
		this.fieldViews = new FieldView[8][8];
		for(int i = 0; i < 8; ++i) {
			for(int j = 0; j < 8; ++j) {
				this.fieldViews[i][j] = new FieldView(this.party.getBoard().getFieldAt(i, j), this);
				this.add(this.fieldViews[i][j]);
			}
		}
	}
	
	/**
	 * @return A parti, amit megjelenít.
	 */
	public final Party getParty() { return this.party; }
	
	/**
	 * @return Az aktuális állapot.
	 */
	public final State getState() { return this.state; }
	
	/**
	 * @return Az éppen aktív bábu.
	 */
	public final Piece getActivePiece() { return this.activePiece; }
	
	/**
	 * Megjeleníti az aktuális lépéseket.
	 * @param p		a bábu, aki lép.
	 * @param moves	a lehetséges lépései.
	 */
	public final void addAvailableMoves(Piece p, List<Move> moves) {
		state = State.MOVING;
		this.activePiece = p;
		for(Move m : moves) {
			Field indicator = m.getIndicator();
			this.fieldViews[indicator.getYCoord()][indicator.getXCoord()].setMove(m);
		}
	}
	
	/**
	 * Leszedi az éppen léphető lépések megjelenítését.
	 */
	public final void clearAvailableMoves() {
		state = State.THINKING;
		for(int i = 0; i < 8; ++i)
			for(int j = 0; j < 8; ++j)
				this.fieldViews[i][j].setMove(null);
	}
	
}
