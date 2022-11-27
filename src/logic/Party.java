package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Skáre Erik
 * Egy partit reprezentál.
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
	
	public interface ChangeListener {
		public void run();
	}
	
	/**
	 * A változásra feliratkozottak.
	 */
	transient private List<ChangeListener> changeListeners;
	
	/**
	 * @param p1 első játékos.
	 * @param p2 második játékos.
	 */
	public Party(Player p1, Player p2) {
		this.p1 = p1;
		this.p2 = p2;
		this.partyState = Party.State.ONGOING;
		this.whiteCapturedPool = new ArrayList<Piece>();
		this.blackCapturedPool = new ArrayList<Piece>();
		this.board = new Board(whiteCapturedPool, blackCapturedPool);
		this.changeListeners = new ArrayList<ChangeListener>();
	}
	
	/**
	 * @return A 'white' attribútum értéke.
	 */
	public final Player getWhite() { 
		if(p1.getIsWhite()) return p1;
		else return p2;
	}
	
	/**
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
	 * @return Világos-e a következő.
	 */
	public final boolean getIsWhiteCurrent() {
		return getWhite().hasStepPermission();
	}
	
	/**
	 * @return A 'board' attribútum értéke.
	 */
	public final Board getBoard() { return this.board; }
	
	/**
	 * @return A 'partyState' attribútum értéke.
	 */
	public final Party.State getPartyState() { return this.partyState; }
	
	/**
	 * Visszaadja a lépési jogát valaki.
	 * @param by a visszaadó játékos.
	 */
	public void returnStepPermission(Player by) {
		for(ChangeListener l : changeListeners)
			l.run();
		if(this.partyState != Party.State.ONGOING)
			return;
		boolean isWhiteNext = (by == this.getBlack());
		Party.State state = this.board.getCurrentState(isWhiteNext);
		this.partyState = state;
		if(state != Party.State.ONGOING)
			return;
		if(isWhiteNext)
			this.getWhite().grantStepPermission();
		else
			this.getBlack().grantStepPermission();
	}
	
	/**
	 * Döntetlenné teszi a partit.
	 */
	public void makeDraw() {
		this.partyState = Party.State.DRAW;
		this.getCurrentPlayer().resignStepPermission();
	}
	
}