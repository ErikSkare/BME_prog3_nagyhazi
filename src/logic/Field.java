package logic;

import java.io.Serializable;

/**
 * Egy mezőt megvalósító osztály.
 * @author Skáre Erik
 */
public class Field implements Serializable {
	
	private static final long serialVersionUID = 5232594265694801915L;

	/**
	 * A tábla, amin a mező van.
	 */
	private Board board;
	
	/**
	 * Vertikális pozíció.
	 */
	private int yCoord;
	
	/**
	 * Horizontális pozíció.
	 */
	private int xCoord;
	
	/**
	 * A mező színét jelzi.
	 */
	private boolean isWhite;
	
	/**
	 * A mezőn tartózkodó bábu.
	 */
	private Piece piece;
	
	/**
	 * Konstruktor
	 * @param board		tábla
	 * @param yCoord	vertikális pozíció
	 * @param xCoord	horizontális pozíció
	 * @param isWhite	fehér-e
	 */
	public Field(Board board, int yCoord, int xCoord, boolean isWhite) {
		this.board = board;
		this.yCoord = yCoord;
		this.xCoord = xCoord;
		this.isWhite = isWhite;
		this.piece = null;
	}
	
	/**
	 * Megadja a mezőhöz tartozó bábut.
	 * @return A tábla.
	 */
	public final Board getBoard() { return this.board; }
	
	/**
	 * Megadja a mező elhelyezkedésének Y koordinátáját.
	 * @return A vertikális pozíció.
	 */
	public final int getYCoord() { return this.yCoord; }
	
	/**
	 * Megadja a mező elhelyezkedésének X koordinátáját.
	 * @return A horizontális pozíció.
	 */
	public final int getXCoord() { return this.xCoord; }
	
	/**
	 * Megadja, hogy a mező fehér színű-e.
	 * @return Fehér-e.
	 */
	public final boolean getIsWhite() { return this.isWhite; }
	
	/**
	 * Megadja a mezőn állú bábut, vagy null-t ha nincs ilyen.
	 * @return A bábu.
	 */
	public final Piece getPiece() { return this.piece; }
	
	/**
	 * Megadja, hogy van-e bábu a mezőn.
	 * @return Van-e rajta bábu.
	 */
	public final boolean hasPiece() { return this.piece != null; }
	
	/**
	 * Ráléptet egy bábut a mezőre.
	 * @param p a bábu.
	 */
	public void stepOn(Piece p) {
		if(this.hasPiece())
			this.piece.capture();
		this.piece = p;
		p.setField(this);
	}
	
	/**
	 * Lelépteti az aktuális bábut a mezőről.
	 */
	public void stepOff() {
		this.piece.setField(null);
		this.piece = null;
	}
	
}
