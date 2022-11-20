package logic.pieces;

import java.util.ArrayList;
import java.util.List;

import logic.Field;
import logic.Move;
import logic.Piece;

public class Bishop extends Piece {

	public Bishop(Field field, boolean isWhite, int value, List<Piece> capturedPool) {
		super(field, isWhite, value, capturedPool);
	}

	@Override
	public List<Move> getAvailableMoves() {
		ArrayList<Move> result = new ArrayList<Move>();
		result.addAll(Utils.getDiagonalMoves(this));
		return result;
	}

	@Override
	public String getImageSrc() {
		if(this.getIsWhite())
			return "pictures/white_bishop.png";
		else
			return "pictures/black_bishop.png";
	}

}
