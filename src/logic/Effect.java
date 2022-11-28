package logic;

import java.io.Serializable;
import java.util.List;

/**
 * Valamit csinál a táblán egy bábuval.
 * @author Skáre Erik
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
	 * Megfordítja az effektet, végrehajtva az ellenkezője fog történni.
	 * @return Az effekt megfordítása.
	 */
	abstract public List<Effect> reverse();
	
	/**
	 * Megadja a bábut, amin az effekt hajtódik végre.
	 * @return A bábu.
	 */
	public final Piece getPiece() { return this.piece; }
	
}
