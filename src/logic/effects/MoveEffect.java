package logic.effects;

import logic.Effect;
import logic.Field;
import logic.Piece;

/**
 * @author Skáre Erik
 * Olyan effekt, ami egy bábut léptet.
 */
public class MoveEffect extends Effect {

	/**
	 * Ahova lép a bábu.
	 */
	private Field to;
	
	/**
	 * Konstruktor
	 * @param piece bábu.
	 * @param to	hova.
	 */
	public MoveEffect(Piece piece, Field to) {
		super(piece);
		this.to = to;
	}
	
	/**
	 * @return A mező, ahova lép.
	 */
	public final Field getTo() { return this.to; }

	@Override
	public void run() {
		this.getPiece().getField().stepOff();
		this.getTo().stepOn(this.getPiece());
	}

}
