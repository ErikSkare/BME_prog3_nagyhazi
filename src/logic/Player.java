package logic;

/**
 * @author Skáre Erik
 * Egy játékos.
 */
abstract public class Player {
	
	/**
	 * A parti, amit a játékos éppen játszik.
	 */
	private Party playing;
	
	/**
	 * @param playing a parti
	 */
	public Player() {
		this.playing = null;
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
	
}