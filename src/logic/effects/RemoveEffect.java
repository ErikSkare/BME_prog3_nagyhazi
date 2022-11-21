package logic.effects;

import java.util.ArrayList;
import java.util.List;

import logic.Effect;
import logic.Field;
import logic.Piece;

public class RemoveEffect extends Effect {
	
	private Field on;

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
