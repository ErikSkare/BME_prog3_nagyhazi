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
	
	/**
	 * @return Sakkban van-e a király.
	 */
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
		this.addCastling(true, result);
		this.addCastling(false, result);
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
	
	private boolean canCastle(boolean toRight) {
		// Király nem mozgott és nincs sakkban
		if(this.getHasMoved() || this.isInCheck())
			return false;
		
		Field f = this.getField();
		Board b = f.getBoard();
		int y = f.getYCoord();
		int x = f.getXCoord();
		int rookX = toRight ? 7 : 0;
		int offset = toRight ? 1 : -1;
		Field rookField = b.getFieldAt(y, rookX);
		Set<Field> controlled = b.getControlledFields(!this.getIsWhite());
		
		// Bástya nem mozgott.
		if(!rookField.hasPiece() || rookField.getPiece().getHasMoved())
			return false;
		
		// Közöttük lévő mezők
		boolean flag = true;
		for(int i = Math.min(x + offset, rookX - offset); i <= Math.max(x + offset, rookX - offset); ++i) {
			Field curr = b.getFieldAt(y, i);
			flag &= !curr.hasPiece();
		}
		
		// Mezők, amin a király átmegy.
		for(int i = Math.min(x + offset, x + offset * 2); i <= Math.max(x + offset, x + offset * 2); ++i) {
			Field curr = b.getFieldAt(y, i);
			flag &= !controlled.contains(curr);
		}
		return flag;
	}
	
	private void addCastling(boolean toRight, ArrayList<Move> result) {
		if(!this.canCastle(toRight))
			return;
		int kingDestOffsetX = toRight ? 2 : -2;
		int rookCurrX = toRight ? 7 : 0;
		int rookDestX = toRight ? 5 : 3;
		Field curr = this.getField();
		Board b = curr.getBoard();
		int y = curr.getYCoord();
		int x = curr.getXCoord();
		Move m = new Move(b.getFieldAt(y, x + kingDestOffsetX), () -> {});
		m.addEffect(new MoveEffect(this, b.getFieldAt(y, x + kingDestOffsetX)));
		m.addEffect(new MoveEffect(b.getFieldAt(y, rookCurrX).getPiece(), b.getFieldAt(y, rookDestX)));
		result.add(m);
	}

}
