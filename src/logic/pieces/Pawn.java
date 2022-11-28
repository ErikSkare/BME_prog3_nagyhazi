package logic.pieces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import logic.Board;
import logic.Field;
import logic.Move;
import logic.Move.Callback;
import logic.Piece;
import logic.effects.AddEffect;
import logic.effects.MoveEffect;
import logic.effects.RemoveEffect;

/**
 * Gyalogot megvalósító osztály.
 * @author Skáre Erik
 */
public class Pawn extends Piece {
	
	private static final long serialVersionUID = -1467480266319352702L;

	/**
	 * Első lépés-e a gyaloggal.
	 */
	private boolean isFirstMove;
	
	/**
	 * Leüthető-e en passant lépéssel.
	 */
	private boolean enPassant;

	/**
	 * Konstruktor
	 * @param field			a mező, amin a bábu tartózkodik.
	 * @param isWhite		fehér-e.
	 * @param value			a bábu értéke.
	 * @param capturedPool	a lista, ahova kerül ha leütik.
	 */
	public Pawn(Field field, boolean isWhite, int value, List<Piece> capturedPool) {
		super(field, isWhite, value, capturedPool);
		this.isFirstMove = true;
		this.enPassant = false;
	}
	
	/**
	 * Megadja, hogy a bábu leüthető-e En Passant lépéssel.
	 * @return leüthető-e En Passant-al.
	 */
	public boolean canTakeEnPassant() {
		return this.enPassant;
	}
	
	/**
	 * Ha az ellenfél nem él az En Passant lépés lehetőségével, akkor
	 * hívódik meg.
	 */
	public void forfeitEnPassant() {
		this.enPassant = false;
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
				Move m = new Move(to, (Callback & Serializable) () -> { this.isFirstMove = false; this.enPassant = true; });
				m.addEffect(new MoveEffect(this, to));
				result.add(m);
			}
		}
		
		Field canPromoteFrom = this.getIsWhite() 
				? b.getFieldAt(1, current.getXCoord())
				: b.getFieldAt(6, current.getXCoord());
		
		// Forward
		Field frontOf = b.getFieldAt(current.getYCoord() + mul, current.getXCoord());
		if(!frontOf.hasPiece()) {
			// Promoting
			Move m = new Move(frontOf, (Callback & Serializable) () -> { this.isFirstMove = false; this.enPassant = false; });
			if(current == canPromoteFrom) {
				Piece promote = b.getPromotionPiece(this);
				m.addEffect(new RemoveEffect(this));
				m.addEffect(new AddEffect(promote, frontOf));
			} else
				m.addEffect(new MoveEffect(this, frontOf));
			result.add(m);
		}
		
		// Left capture
		if(current.getXCoord() - 1 >= 0) {
			Field leftCapture = b.getFieldAt(current.getYCoord() + mul, current.getXCoord() - 1);
			if(leftCapture.hasPiece() && leftCapture.getPiece().canBeTaken(this)) {
				Move m = new Move(leftCapture, (Callback & Serializable) () -> { this.isFirstMove = false; this.enPassant = false; });
				m.addEffect(new MoveEffect(this, leftCapture));
				// Promotion
				if(current == canPromoteFrom) {
					Piece promote = b.getPromotionPiece(this);
					m.addEffect(new RemoveEffect(this));
					m.addEffect(new AddEffect(promote, leftCapture));
				}
				result.add(m);
			}
			// En passant
			Field enPassantLeft = b.getFieldAt(current.getYCoord(), current.getXCoord() - 1);
			if(!leftCapture.hasPiece() && enPassantLeft.hasPiece() && enPassantLeft.getPiece().canTakeEnPassant()) {
				Move m = new Move(leftCapture, (Callback & Serializable) () -> { this.isFirstMove = false; this.enPassant = false; });
				m.addEffect(new MoveEffect(this, enPassantLeft));
				m.addEffect(new MoveEffect(this, leftCapture));
				result.add(m);
			}
		}
		
		// Right capture
		if(current.getXCoord() + 1 < 8) {
			Field rightCapture = b.getFieldAt(current.getYCoord() + mul, current.getXCoord() + 1);
			if(rightCapture.hasPiece() && rightCapture.getPiece().canBeTaken(this)) {
				Move m = new Move(rightCapture, (Callback & Serializable) () -> { this.isFirstMove = false; this.enPassant = false; });
				m.addEffect(new MoveEffect(this, rightCapture));
				// Promote
				if(current == canPromoteFrom) {
					Piece promote = b.getPromotionPiece(this);
					m.addEffect(new RemoveEffect(this));
					m.addEffect(new AddEffect(promote, rightCapture));
				}
				result.add(m);
			}
			// En passant
			Field enPassantRight = b.getFieldAt(current.getYCoord(), current.getXCoord() + 1);
			if(!rightCapture.hasPiece() && enPassantRight.hasPiece() && enPassantRight.getPiece().canTakeEnPassant()) {
				Move m = new Move(rightCapture, (Callback & Serializable) () -> { this.isFirstMove = false; this.enPassant = false; });
				m.addEffect(new MoveEffect(this, enPassantRight));
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
