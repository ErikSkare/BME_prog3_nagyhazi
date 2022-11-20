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
		
		Move curr;
		curr = getMoveRelative(1, 2);
		if(curr != null) result.add(curr);
		curr = getMoveRelative(2, 1);
		if(curr != null) result.add(curr);
		curr = getMoveRelative(-1, 2);
		if(curr != null) result.add(curr);
		curr = getMoveRelative(2, -1);
		if(curr != null) result.add(curr);
		curr = getMoveRelative(-2, 1);
		if(curr != null) result.add(curr);
		curr = getMoveRelative(1, -2);
		if(curr != null) result.add(curr);
		curr = getMoveRelative(-2, -1);
		if(curr != null) result.add(curr);
		curr = getMoveRelative(-1, -2);
		if(curr != null) result.add(curr);
		
		return result;
	}
	
	private Move getMoveRelative(int dy, int dx) {
		Field where = this.getField();
		Board b = where.getBoard();
		int y = where.getYCoord();
		int x = where.getXCoord();
		if(y + dy >= 0 && y + dy < 8 && x + dx >= 0 && x + dx < 8) {
			Field f = b.getFieldAt(y + dy, x + dx);
			if(!f.hasPiece() || f.getPiece().canBeTaken(this)) {
				Move m = new Move(f, () -> {});
				m.addEffect(new MoveEffect(this, f));
				return m;
			}
		}
		return null;
	}

	@Override
	public String getImageSrc() {
		if(this.getIsWhite())
			return "pictures/white_knight.png";
		else
			return "pictures/black_knight.png";
	}

}
