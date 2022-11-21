package logic.pieces;

import java.util.ArrayList;
import java.util.List;

import logic.Board;
import logic.Field;
import logic.Move;
import logic.Piece;
import logic.effects.MoveEffect;

/**
 * @author Skáre Erik
 * Egy gyalog bábu.
 */
public class Pawn extends Piece {
	
	private boolean isFirstMove;

	/**
	 * @param field			a mező.
	 * @param isWhite		fehér-e
	 * @param value			a bábu értéke.
	 * @param capturedPool	ha leütik, ide kerül.
	 */
	public Pawn(Field field, boolean isWhite, int value, List<Piece> capturedPool) {
		super(field, isWhite, value, capturedPool);
		this.isFirstMove = true;
	}

	@Override
	public List<Move> getAvailableMoves() {
		ArrayList<Move> result = new ArrayList<Move>();
		Board b = this.getField().getBoard();
		Field current = this.getField();
		int mul = this.getIsWhite() ? -1 : 1;
		
		// First move
		if(this.isFirstMove) {
			Field to = b.getFieldAt(current.getYCoord() + mul * 2, current.getXCoord());
			Field before = b.getFieldAt(current.getYCoord() + mul, current.getXCoord());
			if(!to.hasPiece() && !before.hasPiece()) {
				Move m = new Move(to, () -> { this.isFirstMove = false; });
				m.addEffect(new MoveEffect(this, to));
				result.add(m);
			}
		}
		
		// Forward
		Field frontOf = b.getFieldAt(current.getYCoord() + mul, current.getXCoord());
		if(!frontOf.hasPiece()) {
			Move m = new Move(frontOf, () -> { this.isFirstMove = false; });
			m.addEffect(new MoveEffect(this, frontOf));
			result.add(m);
		}
		
		// Left capture
		if(current.getXCoord() - 1 >= 0) {
			Field leftCapture = b.getFieldAt(current.getYCoord() + mul, current.getXCoord() - 1);
			if(leftCapture.hasPiece() && leftCapture.getPiece().canBeTaken(this)) {
				Move m = new Move(leftCapture, () -> { this.isFirstMove = false; });
				m.addEffect(new MoveEffect(this, leftCapture));
				result.add(m);
			}
		}
		
		// Right capture
		if(current.getXCoord() + 1 < 8) {
			Field rightCapture = b.getFieldAt(current.getYCoord() + mul, current.getXCoord() + 1);
			if(rightCapture.hasPiece() && rightCapture.getPiece().canBeTaken(this)) {
				Move m = new Move(rightCapture, () -> { this.isFirstMove = false; });
				m.addEffect(new MoveEffect(this, rightCapture));
				result.add(m);
			}
		}
		
		return this.filterMoves(result);
	}
	
	@Override
	public List<Field> getControlledFields() {
		ArrayList<Field> result = new ArrayList<Field>();
		Board b = this.getField().getBoard();
		Field current = this.getField();
		int mul = this.getIsWhite() ? -1 : 1;
		if(current.getXCoord() - 1 >= 0) {
			Field leftCapture = b.getFieldAt(current.getYCoord() + mul, current.getXCoord() - 1);
			result.add(leftCapture);
		}
		if(current.getXCoord() + 1 < 8) {
			Field rightCapture = b.getFieldAt(current.getYCoord() + mul, current.getXCoord() + 1);
			result.add(rightCapture);
		}
		return result;
	}

	@Override
	public String getImageSrc() {
		if(this.getIsWhite())
			return "pictures/white_pawn.png";
		else
			return "pictures/black_pawn.png";
	}

}
