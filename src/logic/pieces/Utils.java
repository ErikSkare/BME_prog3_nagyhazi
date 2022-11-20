package logic.pieces;

import java.util.ArrayList;

import logic.Board;
import logic.Field;
import logic.Move;
import logic.Piece;
import logic.effects.MoveEffect;

public class Utils {
	public static interface Transition {
		public Field run(Field f);
	}
	
	public static ArrayList<Move> getMovesUntil(Piece p, Transition t) {
		ArrayList<Move> result = new ArrayList<Move>();
		
		Field f = t.run(p.getField());
		while(f != null && !f.hasPiece()) {
			Move m = new Move(f, () -> {});
			m.addEffect(new MoveEffect(p, f));
			result.add(m);
			f = t.run(f);
		}
		if(f != null && f.getPiece().canBeTaken(p)) {
			Move m = new Move(f, () -> {});
			m.addEffect(new MoveEffect(p, f));
			result.add(m);
		}
		
		return result;
	}
	
	public static ArrayList<Move> getStraightMoves(Piece p) {
		ArrayList<Move> result = new ArrayList<Move>();
		Board b = p.getField().getBoard();
		
		result.addAll(
			Utils.getMovesUntil(p, (Field f) -> {
				if(f.getXCoord() + 1 >= 8)
					return null;
				return b.getFieldAt(f.getYCoord(), f.getXCoord() + 1);
			})
		);
		result.addAll(
			Utils.getMovesUntil(p, (Field f) -> {
				if(f.getXCoord() - 1 < 0)
					return null;
				return b.getFieldAt(f.getYCoord(), f.getXCoord() - 1);
			})
		);
		result.addAll(
			Utils.getMovesUntil(p, (Field f) -> {
				if(f.getYCoord() - 1 < 0)
					return null;
				return b.getFieldAt(f.getYCoord() - 1, f.getXCoord());
			})
		);
		result.addAll(
			Utils.getMovesUntil(p, (Field f) -> {
				if(f.getYCoord() + 1 >= 8)
					return null;
				return b.getFieldAt(f.getYCoord() + 1, f.getXCoord());
			})
		);
		
		return result;
	}
	
	public static ArrayList<Move> getDiagonalMoves(Piece p) {
		ArrayList<Move> result = new ArrayList<Move>();
		Board b = p.getField().getBoard();
		
		result.addAll(
			Utils.getMovesUntil(p, (Field f) -> {
				if(f.getYCoord() + 1 >= 8 || f.getXCoord() + 1 >= 8)
					return null;
				return b.getFieldAt(f.getYCoord() + 1, f.getXCoord() + 1);
			})
		);
		result.addAll(
			Utils.getMovesUntil(p, (Field f) -> {
				if(f.getYCoord() + 1 >= 8 || f.getXCoord() - 1 < 0)
					return null;
				return b.getFieldAt(f.getYCoord() + 1, f.getXCoord() - 1);
			})
		);
		result.addAll(
			Utils.getMovesUntil(p, (Field f) -> {
				if(f.getYCoord() - 1 < 0 || f.getXCoord() + 1 >= 8)
					return null;
				return b.getFieldAt(f.getYCoord() - 1, f.getXCoord() + 1);
			})
		);
		result.addAll(
			Utils.getMovesUntil(p, (Field f) -> {
				if(f.getYCoord() - 1 < 0 || f.getXCoord() - 1 < 0)
					return null;
				return b.getFieldAt(f.getYCoord() - 1, f.getXCoord() - 1);
			})
		);
	
		return result;
	}
}
