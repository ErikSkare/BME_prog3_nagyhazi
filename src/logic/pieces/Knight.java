package logic.pieces;

import java.util.ArrayList;
import java.util.List;

import logic.Board;
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
		
		Field curr;
		curr = getFieldRelative(1, 2);
		if(curr != null) result.add(curr);
		curr = getFieldRelative(2, 1);
		if(curr != null) result.add(curr);
		curr = getFieldRelative(-1, 2);
		if(curr != null) result.add(curr);
		curr = getFieldRelative(2, -1);
		if(curr != null) result.add(curr);
		curr = getFieldRelative(-2, 1);
		if(curr != null) result.add(curr);
		curr = getFieldRelative(1, -2);
		if(curr != null) result.add(curr);
		curr = getFieldRelative(-2, -1);
		if(curr != null) result.add(curr);
		curr = getFieldRelative(-1, -2);
		if(curr != null) result.add(curr);
		
		return result;
	}

	@Override
	public String getImageSrc() {
		if(this.getIsWhite())
			return "pictures/white_knight.png";
		else
			return "pictures/black_knight.png";
	}
	
	private Field getFieldRelative(int dy, int dx) {
		Field where = this.getField();
		Board b = where.getBoard();
		int y = where.getYCoord();
		int x = where.getXCoord();
		if(y + dy >= 0 && y + dy < 8 && x + dx >= 0 && x + dx < 8) {
			Field f = b.getFieldAt(y + dy, x + dx);
			return f;
		}
		return null;
	}

}
