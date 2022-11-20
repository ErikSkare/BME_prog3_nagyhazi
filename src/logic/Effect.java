package logic;

/**
 * @author Skáre Erik
 * Csinál valamit a táblán egy bábuval.
 */
abstract public class Effect {
	
	/**
	 * A bábu, amin az effekt végrehajtódik.
	 */
	private Piece piece;
	
	/**
	 * Konstruktor
	 * @param piece a bábu
	 */
	public Effect(Piece piece) {
		this.piece = piece;
	}
	
	/**
	 * Végrehajtja az effektet.
	 */
	abstract public void run();
	
	/**
	 * @return Visszaadja az effekt megfordítását.
	 */
	abstract public Effect reverse();
	
	/**
	 * @return A bábu.
	 */
	public final Piece getPiece() { return this.piece; }
	
}
