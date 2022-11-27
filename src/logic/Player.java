package logic;

import java.io.Serializable;

/**
 * @author Skáre Erik
 * Egy játékos.
 */
abstract public class Player implements Serializable {
	
	private static final long serialVersionUID = 6599240901998206520L;
	
	/**
	 * A parti, amit a játékos éppen játszik.
	 */
	private Party playing;
	
	/**
	 * A színt jelzi, amelyikkel a játékos játszik.
	 */
	private boolean isWhite;
	
	/**
	 * @param isWhite világossal játszik-e.
	 */
	public Player(boolean isWhite) {
		this.playing = null;
		this.isWhite = isWhite;
	}
	
	/**
	 * A lépési jog megadása a játékosnak.
	 * Lépés után kötelessége visszaadni a jogot.
	 */
	abstract public void grantStepPermission();
	
	/**
	 * Lemond a lépési jogról.
	 */
	abstract public void resignStepPermission();
	
	/**
	 * @return A lépési jog megléte.
	 */
	abstract public boolean hasStepPermission();
	
	/**
	 * @return A 'playing' attribútum értéke.
	 */
	public final Party getPlaying() { return this.playing; }
	
	/**
	 * @param p A 'playing' attribútum új értéke.
	 */
	public final void setPlaying(Party p) { this.playing = p; }
	
	/**
	 * @return Az 'isWhite' attribútum értéke.
	 */
	public final boolean getIsWhite() { return this.isWhite; }
	
}