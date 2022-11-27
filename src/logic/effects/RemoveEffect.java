package logic.effects;

import java.util.ArrayList;
import java.util.List;

import logic.Effect;
import logic.Field;
import logic.Piece;

/**
 * @author Skáre Erik
 */
public class RemoveEffect extends Effect {
	
	private static final long serialVersionUID = 4610838995513204908L;
	
	/**
	 * A mező, ahol a bábu állt.
	 */
	private Field on;

	/**
	 * Konstruktor
	 * @param piece a törlendő bábu.
	 */
	public RemoveEffect(Piece piece) {
		super(piece);
		this.on = piece.getField();
	}

	@Override
	public void run() {
		this.getPiece().getField().stepOff();
	}

	@Override
	public List<Effect> reverse() {
		ArrayList<Effect> result = new ArrayList<Effect>();
		result.add(new AddEffect(this.getPiece(), this.on));
		return result;
	}

}
