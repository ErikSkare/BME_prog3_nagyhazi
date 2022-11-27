package logic.effects;

import java.util.ArrayList;
import java.util.List;

import logic.Effect;
import logic.Field;
import logic.Piece;

/**
 * @author Skáre Erik
 */
public class AddEffect extends Effect {
	
	private static final long serialVersionUID = -8371555093380855618L;
	
	/**
	 * Ahova hozzáadódik a bábu.
	 */
	private Field where;

	/**
	 * Konstruktor
	 * @param piece a bábu.
	 * @param where hova.
	 */
	public AddEffect(Piece piece, Field where) {
		super(piece);
		this.where = where;
	}

	@Override
	public void run() {
		where.stepOn(this.getPiece());
	}

	@Override
	public List<Effect> reverse() {
		ArrayList<Effect> result = new ArrayList<Effect>();
		result.add(new RemoveEffect(this.getPiece()));
		return result;
	}

}
