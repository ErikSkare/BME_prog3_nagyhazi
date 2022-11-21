package logic.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import logic.Board;
import logic.Field;
import logic.Move;
import logic.Piece;
import logic.effects.MoveEffect;

public class King extends Piece {

	public King(Field field, boolean isWhite, int value, List<Piece> capturedPool) {
		super(field, isWhite, value, capturedPool);
	}
	
	public final boolean isInCheck() {
		Set<Field> opponentControlled = this.getField().getBoard().getControlledFields(!this.getIsWhite());
		return opponentControlled.contains(this.getField());
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
		Field curr = this.getField();
		Board b = curr.getBoard();
		int x = curr.getXCoord();
		int y = curr.getYCoord();
		if(x + 1 < 8)
			result.add(b.getFieldAt(y, x + 1));
		if(x - 1 >= 0)
			result.add(b.getFieldAt(y, x - 1));
		if(y + 1 < 8)
			result.add(b.getFieldAt(y + 1, x));
		if(y - 1 >= 0)
			result.add(b.getFieldAt(y - 1, x));
		if(y + 1 < 8 && x + 1 < 8)
			result.add(b.getFieldAt(y + 1, x + 1));
		if(y + 1 < 8 && x - 1 >= 0)
			result.add(b.getFieldAt(y + 1, x - 1));
		if(y - 1 >= 0 && x + 1 < 8)
			result.add(b.getFieldAt(y - 1, x + 1));
		if(y - 1 >= 0 && x - 1 >= 0)
			result.add(b.getFieldAt(y - 1, x - 1));
		return result;
	}

	@Override
	public String getImageSrc() {
		if(this.getIsWhite())
			return "pictures/white_king.png";
		else
			return "pictures/black_king.png";
	}
	
	@Override
	public boolean canBeTaken(Piece p) {
		return false;
	}

}
