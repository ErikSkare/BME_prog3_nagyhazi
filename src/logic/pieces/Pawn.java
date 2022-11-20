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

	/**
	 * @param field			a mező.
	 * @param isWhite		fehér-e
	 * @param value			a bábu értéke.
	 * @param capturedPool	ha leütik, ide kerül.
	 */
	public Pawn(Field field, boolean isWhite, int value, List<Piece> capturedPool) {
		super(field, isWhite, value, capturedPool);
	}

	@Override
	public List<Move> getAvailableMoves() {
		Board b = this.getField().getBoard();
		ArrayList<Move> result = new ArrayList<Move>();
		int val = this.getIsWhite() ? -1 : 1;
		Move m = new Move(b.getFieldAt(this.getField().getYCoord() + val, this.getField().getXCoord()));
		m.addEffect(new MoveEffect(this, b.getFieldAt(this.getField().getYCoord() + val, this.getField().getXCoord())));
		result.add(m);
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
