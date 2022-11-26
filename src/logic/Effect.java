package logic;

import java.io.Serializable;
import java.util.List;

/**
 * @author Skáre Erik
 * Csinál valamit a táblán egy bábuval.
 */
abstract public class Effect implements Serializable {
	
	private static final long serialVersionUID = -8149151912488462034L;
	
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
	abstract public List<Effect> reverse();
	
	/**
	 * @return A bábu.
	 */
	public final Piece getPiece() { return this.piece; }
	
}
