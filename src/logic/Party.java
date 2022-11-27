package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Skáre Erik
 */
public class Party implements Serializable {
	
	private static final long serialVersionUID = 3737441359349151775L;

	/**
	 * Az parti lehetséges állapotai.
	 */
	public enum State {
		ONGOING,
		DRAW,
		WHITE_VICTORY,
		BLACK_VICTORY
	}
	
	/**
	 * Az egyik játékos
	 */
	private Player p1;
	
	/**
	 * A másik játékos
	 */
	private Player p2;
	
	/**
	 * A tábla, amin a játék zajlik.
	 */
	private Board board;
	
	/**
	 * A parti aktuális állapota.
	 */
	private Party.State partyState;
	
	/**
	 * A lista, ahova a világos által leütött bábuk kerülnek.
	 */
	private List<Piece> whiteCapturedPool;
	
	/**
	 * A lista, ahova a sötét által leütött bábuk kerülnek.
	 */
	private List<Piece> blackCapturedPool;
	
	private boolean canMakeDraw;
	
	/**
	 * A változásra feliratkozást megvalósító interfész.
	 */
	public interface ChangeListener {
		/**
		 * A változás hatására történik valami.
		 */
		public void run();
	}
	
	/**
	 * A változásra feliratkozottak.
	 */
	transient private List<ChangeListener> changeListeners;
	
	/**
	 * Konstruktor
	 * @param p1 első játékos.
	 * @param p2 második játékos.
	 */
	public Party(Player p1, Player p2, boolean canMakeDraw) {
		this.p1 = p1;
		this.p2 = p2;
		this.canMakeDraw = canMakeDraw;
		this.partyState = Party.State.ONGOING;
		this.whiteCapturedPool = new ArrayList<Piece>();
		this.blackCapturedPool = new ArrayList<Piece>();
		this.board = new Board(whiteCapturedPool, blackCapturedPool);
		this.changeListeners = new ArrayList<ChangeListener>();
	}
	
	/**
	 * Megadja a világossal játszó játékost.
	 * @return A 'white' attribútum értéke.
	 */
	public final Player getWhite() { 
		if(p1.getIsWhite()) return p1;
		else return p2;
	}
	
	/**
	 * Megadja a sötéttel játszó játékost.
	 * @return A 'black' attribútum értéke.
	 */
	public final Player getBlack() { 
		if(!p1.getIsWhite()) return p1;
		else return p2;
	}
	
	/**
	 * Elindítja a partit.
	 * Azaz megadja a lépési jogot a világosnak.
	 */
	public final void startMatch() {
		getWhite().grantStepPermission();
	}
	
	/**
	 * Megadja az éppen lépő játékost, ha van ilyen.
	 * @return Az aktuálisan lépő játékos, vagy null ha nincs.
	 */
	public final Player getCurrentPlayer() {
		if(!getWhite().hasStepPermission() && !getBlack().hasStepPermission())
			return null;
		if(getIsWhiteCurrent())
			return getWhite();
		else
			return getBlack();
	}
	
	/**
	 * Feliratkoztatja a listenert a változásra.
	 * @param l listener
	 */
	public final void addChangeListener(ChangeListener l) {
		this.changeListeners.add(l);
	}
	
	/**
	 * Törli az aktuálisan feliratkozottakat.
	 */
	public final void resetChangeListeners() {
		this.changeListeners = new ArrayList<ChangeListener>();
	}
	
	/**
	 * Megadja, hogy az éppen lépő játékos a világos-e.
	 * @return Világos-e a következő.
	 */
	public final boolean getIsWhiteCurrent() {
		return getWhite().hasStepPermission();
	}
	
	/**
	 * Megadja a partihoz tartozó táblát.
	 * @return A 'board' attribútum értéke.
	 */
	public final Board getBoard() { return this.board; }
	
	/**
	 * Megadja a parti aktuális állapotát.
	 * @return A 'partyState' attribútum értéke.
	 */
	public final Party.State getPartyState() { return this.partyState; }
	
	/**
	 * Visszaadja a lépési jogát valaki.
	 * @param by a visszaadó játékos.
	 */
	public void returnStepPermission(Player by) {
		if(this.partyState == Party.State.ONGOING) {
			boolean isWhiteNext = (by == this.getBlack());
			Party.State state = this.board.getCurrentState(isWhiteNext);
			this.partyState = state;
			if(state == Party.State.ONGOING) {
				if(isWhiteNext)
					this.getWhite().grantStepPermission();
				else
					this.getBlack().grantStepPermission();
			}
		}
		for(ChangeListener l : changeListeners)
			l.run();
	}
	
	/**
	 * Döntetlenné teszi a partit.
	 */
	public void makeDraw() {
		this.partyState = Party.State.DRAW;
		this.getCurrentPlayer().resignStepPermission();
	}
	
	/**
	 * Megadja, hogy a döntetlenné tevés opciója elérhető-e.
	 * @return Elérhető-e az opció.
	 */
	public final boolean getCanMakeDraw() { return this.canMakeDraw; }
	
}