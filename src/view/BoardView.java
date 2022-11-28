package view;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import javax.swing.JPanel;

import logic.Board;
import logic.Field;
import logic.Move;
import logic.Piece;
import logic.Player;

/**
 * A táblához tartozó nézet.
 * @author Skáre Erik
 */
public class BoardView extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * A megjelenítés állapota.
	 */
	enum State {
		HISTORY,  // múltbeli állást néz a játékos.
		THINKING, // nincsenek megjelenítve lehetséges lépések.
		MOVING	  // a lehetséges lépések meg vannak jelenítve.
	}
	
	/**
	 * Az aktuális állapot.
	 */
	private State state;
	
	/**
	 * A tábla, amit megjelenít.
	 */
	private Board board;
	
	/**
	 * A mezőkhöz tartozó nézetek.
	 */
	private FieldView[][] fieldViews;
	
	/**
	 * Az éppen kiválasztott bábu.
	 */
	private Piece activePiece;
	
	/**
	 * A parti nézete.
	 */
	private PartyView partyView;
	
	/**
	 * Konstruktor
	 * @param board			a tábla.
	 * @param partyView		a parti nézete.
	 */
	public BoardView(Board board, PartyView partyView) {
		super(new GridLayout(0, 8));
		this.state = State.THINKING;
		this.board = board;
		this.partyView = partyView;
		this.activePiece = null;
		
		this.setFocusable(true);
		this.addKeyListener(new OnKeyPress());
		
		this.fieldViews = new FieldView[8][8];
		for(int i = 0; i < 8; ++i) {
			for(int j = 0; j < 8; ++j) {
				this.fieldViews[i][j] = new FieldView(this.board.getFieldAt(i, j), this);
				this.add(this.fieldViews[i][j]);
			}
		}
	}
	
	/**
	 * Visszaadja a táblát, amit megjelenít ez a nézet.
	 * @return A tábla, amit megjelenít.
	 */
	public final Board getBoard() { return this.board; }
	
	/**
	 * Visszaadja az éppen gondolkodó/lépő játékost.
	 * @return Az éppen lépő játékos.
	 */
	public final Player getMovingPlayer() { return this.partyView.getParty().getCurrentPlayer(); }
	
	/**
	 * Visszaadja a tábla nézet aktuális állapotát.
	 * (azaz éppen gondolkodik a játékos, vagy már kiválasztotta a bábut, amivel lép)
	 * @return Az aktuális állapot.
	 */
	public final State getState() { return this.state; }
	
	/**
	 * Visszaadja a bábut, amivel a játékos lépni tervez.
	 * @return Az éppen aktív bábu.
	 */
	public final Piece getActivePiece() { return this.activePiece; }
	
	/**
	 * Visszaadja a táblához tartozó parti nézetét.
	 * @return A parti nézete.
	 */
	public final PartyView getPartyView() { return this.partyView; }
	
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
		this.repaint();
	}
	
	/**
	 * Leszedi az éppen léphető lépések megjelenítését.
	 */
	public final void clearAvailableMoves() {
		state = State.THINKING;
		for(int i = 0; i < 8; ++i)
			for(int j = 0; j < 8; ++j)
				this.fieldViews[i][j].setMove(null);
		this.repaint();
	}
	
	/**
	 * Listener, az időbeli mozgást valósítja meg.
	 */
	class OnKeyPress implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) { }

		@Override
		public void keyPressed(KeyEvent e) {
			if(state == State.MOVING)
				return;
			if(e.getKeyCode() == 37 && board.stepBack()) {
				repaint();
				state = State.HISTORY;
			} else if(e.getKeyCode() == 39 && board.stepForward())
				repaint();
			if(board.isInNow())
				state = State.THINKING;
		}

		@Override
		public void keyReleased(KeyEvent e) { }
		
	}
	
}
