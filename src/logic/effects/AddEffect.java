package logic.effects;

import java.util.List;

import logic.Effect;
import logic.Field;
import logic.Piece;

public class AddEffect extends Effect {
	
	private Field where;

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
		// TODO Auto-generated method stub
		return null;
	}

}
