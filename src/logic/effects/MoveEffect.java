package logic.effects;

import java.util.ArrayList;
import java.util.List;

import logic.Effect;
import logic.Field;
import logic.Piece;

/**
 * @author Skáre Erik
 * Olyan effekt, ami egy bábut léptet.
 */
public class MoveEffect extends Effect {
	
	/**
	 * Ahonnan lépett a bábu
	 */
	private Field from;

	/**
	 * Ahova lép a bábu.
	 */
	private Field to;
	
	private Piece capturing;
	
	/**
	 * Konstruktor
	 * @param piece bábu.
	 * @param to	hova.
	 */
	public MoveEffect(Piece piece, Field to) {
		super(piece);
		this.from = piece.getField();
		this.to = to;
		this.capturing = this.to.getPiece();
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
	
	@Override
	public List<Effect> reverse() {
		List<Effect> result = new ArrayList<Effect>();
		result.add(new MoveEffect(this.getPiece(), from));
		if(this.capturing != null)
			result.add(new AddEffect(this.capturing, to));
		return result;
	}

}
