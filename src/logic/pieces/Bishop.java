package logic.pieces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import logic.Field;
import logic.Move;
import logic.Piece;
import logic.Move.Callback;
import logic.effects.MoveEffect;

/**
 * @author Skáre Erik
 */
public class Bishop extends Piece {

	private static final long serialVersionUID = -7746783782119001111L;

	/**
	 * Konstruktor
	 * @param field			a mező, amin a bábu tartózkodik.
	 * @param isWhite		fehér-e.
	 * @param value			a bábu értéke.
	 * @param capturedPool	a lista, ahova kerül ha leütik.
	 */
	public Bishop(Field field, boolean isWhite, int value, List<Piece> capturedPool) {
		super(field, isWhite, value, capturedPool);
	}

	@Override
	public List<Move> getAvailableMoves() {
		ArrayList<Move> result = new ArrayList<Move>();
		for(Field f : Utils.getDiagonalFields(this)) {
			if(!f.hasPiece() || f.getPiece().canBeTaken(this)) {
				Move m = new Move(f, (Callback & Serializable) () -> {});
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
