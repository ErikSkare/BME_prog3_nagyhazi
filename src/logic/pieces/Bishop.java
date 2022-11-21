package logic.pieces;

import java.util.ArrayList;
import java.util.List;

import logic.Field;
import logic.Move;
import logic.Piece;
import logic.effects.MoveEffect;

public class Bishop extends Piece {

	public Bishop(Field field, boolean isWhite, int value, List<Piece> capturedPool) {
		super(field, isWhite, value, capturedPool);
	}

	@Override
	public List<Move> getAvailableMoves() {
		ArrayList<Move> result = new ArrayList<Move>();
		for(Field f : Utils.getDiagonalFields(this)) {
			if(!f.hasPiece() || f.getPiece().canBeTaken(this)) {
				Move m = new Move(f, () -> {});
				m.addEffect(new MoveEffect(this, f));
				result.add(m);
			}
		}
		return this.filterMoves(result);
	}
	
	@Override
	public List<Field> getControlledFields() {
		return Utils.getDiagonalFields(this);
	}

	@Override
	public String getImageSrc() {
		if(this.getIsWhite())
			return "pictures/white_bishop.png";
		else
			return "pictures/black_bishop.png";
	}

}
