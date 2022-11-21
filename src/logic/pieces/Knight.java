package logic.pieces;

import java.util.ArrayList;
import java.util.List;

import logic.Field;
import logic.Move;
import logic.Piece;
import logic.effects.MoveEffect;

public class Knight extends Piece {

	public Knight(Field field, boolean isWhite, int value, List<Piece> capturedPool) {
		super(field, isWhite, value, capturedPool);
	}

	@Override
	public List<Move> getAvailableMoves() {
		ArrayList<Move> result = new ArrayList<Move>();
		for(Field f : this.getControlledFields()) {
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
		ArrayList<Field> result = new ArrayList<Field>();
		
		for(int i = -1; i <= 1; i+=2) {
			for(int j = -2; j <= 2; j+=4) {
				Field f1 = Utils.getFieldRelative(this, i, j);
				if(f1 != null) result.add(f1);
				Field f2 = Utils.getFieldRelative(this, j, i);
				if(f2 != null) result.add(f2);
			}
		}
		
		return result;
	}

	@Override
	public String getImageSrc() {
		if(this.getIsWhite())
			return "pictures/white_knight.png";
		else
			return "pictures/black_knight.png";
	}

}
