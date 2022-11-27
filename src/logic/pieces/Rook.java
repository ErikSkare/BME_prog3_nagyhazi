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
public class Rook extends Piece {

	private static final long serialVersionUID = 3163567554782674065L;

	/**
	 * Konstruktor
	 * @param field			a mező, amin a bábu tartózkodik.
	 * @param isWhite		fehér-e.
	 * @param value			a bábu értéke.
	 * @param capturedPool	a lista, ahova kerül ha leütik.
	 */
	public Rook(Field field, boolean isWhite, int value, List<Piece> capturedPool) {
		super(field, isWhite, value, capturedPool);
	}

	@Override
	public List<Move> getAvailableMoves() {
		ArrayList<Move> result = new ArrayList<Move>();
		for(Field f : Utils.getStraightFields(this)) {
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
		return Utils.getStraightFields(this);
	}

	@Override
	public String getImageSrc() {
		if(this.getIsWhite())
			return "pictures/white_rook.png";
		else
			return "pictures/black_rook.png";
	}

}
