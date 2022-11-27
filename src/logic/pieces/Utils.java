package logic.pieces;

import java.util.ArrayList;

import logic.Board;
import logic.Field;
import logic.Piece;

/**
 * @author Skáre Erik
 */
public class Utils {
	
	/**
	 * Megadja, hogy egy adott mezőből hogyan kapható meg a következő.
	 * Ezzel írja le pl. a diagonális lépéseket.
	 */
	public static interface Transition {
		public Field run(Field f);
	}
	
	/**
	 * Visszaadja egy adott bábutól kiindulva az összes átmenettel definiált mezőt,
	 * egészen addig, amíg egy foglalt mezőhöz ér.
	 * @param p az adott bábu.
	 * @param t az átmenet.
	 * @return Az összes feltételnek megfelelő mező.
	 */
	public static ArrayList<Field> getFieldsUntil(Piece p, Transition t) {
		ArrayList<Field> result = new ArrayList<Field>();
		
		Field f = t.run(p.getField());
		while(f != null && !f.hasPiece()) {
			result.add(f);
			f = t.run(f);
		}
		if(f != null)
			result.add(f);
		
		return result;
	}
	
	/**
	 * Megadja egy adott bábuhoz a sorában és oszlopában elhelyezkedő mezőket.
	 * (nem feltétlen végig, csak addig amíg nem ér egy foglalt mezőhöz)
	 * @param p a bábu.
	 * @return Az összes feltételnek megfelelő mező.
	 */
	public static ArrayList<Field> getStraightFields(Piece p) {
		ArrayList<Field> result = new ArrayList<Field>();
		Board b = p.getField().getBoard();
		
		result.addAll(
			Utils.getFieldsUntil(p, (Field f) -> {
				if(f.getXCoord() + 1 >= 8)
					return null;
				return b.getFieldAt(f.getYCoord(), f.getXCoord() + 1);
			})
		);
		result.addAll(
			Utils.getFieldsUntil(p, (Field f) -> {
				if(f.getXCoord() - 1 < 0)
					return null;
				return b.getFieldAt(f.getYCoord(), f.getXCoord() - 1);
			})
		);
		result.addAll(
			Utils.getFieldsUntil(p, (Field f) -> {
				if(f.getYCoord() - 1 < 0)
					return null;
				return b.getFieldAt(f.getYCoord() - 1, f.getXCoord());
			})
		);
		result.addAll(
			Utils.getFieldsUntil(p, (Field f) -> {
				if(f.getYCoord() + 1 >= 8)
					return null;
				return b.getFieldAt(f.getYCoord() + 1, f.getXCoord());
			})
		);
		
		return result;
	}
	
	/**
	 * Megadja egy adott bábuhoz az átlósan elhelyezkedő mezőket.
	 * (nem feltétlen végig, csak addig amíg nem ér egy foglalt mezőhöz)
	 * @param p a bábu.
	 * @return Az összes feltételnek megfelelő mező.
	 */
	public static ArrayList<Field> getDiagonalFields(Piece p) {
		ArrayList<Field> result = new ArrayList<Field>();
		Board b = p.getField().getBoard();
		
		result.addAll(
			Utils.getFieldsUntil(p, (Field f) -> {
				if(f.getYCoord() + 1 >= 8 || f.getXCoord() + 1 >= 8)
					return null;
				return b.getFieldAt(f.getYCoord() + 1, f.getXCoord() + 1);
			})
		);
		result.addAll(
			Utils.getFieldsUntil(p, (Field f) -> {
				if(f.getYCoord() + 1 >= 8 || f.getXCoord() - 1 < 0)
					return null;
				return b.getFieldAt(f.getYCoord() + 1, f.getXCoord() - 1);
			})
		);
		result.addAll(
			Utils.getFieldsUntil(p, (Field f) -> {
				if(f.getYCoord() - 1 < 0 || f.getXCoord() + 1 >= 8)
					return null;
				return b.getFieldAt(f.getYCoord() - 1, f.getXCoord() + 1);
			})
		);
		result.addAll(
			Utils.getFieldsUntil(p, (Field f) -> {
				if(f.getYCoord() - 1 < 0 || f.getXCoord() - 1 < 0)
					return null;
				return b.getFieldAt(f.getYCoord() - 1, f.getXCoord() - 1);
			})
		);
	
		return result;
	}
	
	/**
	 * Egy, az adott bábuhoz relatív elhelyezkedő mezőt ad vissza.
	 * (ló lépéseinél hasznos)
	 * @param p a bábu.
	 * @param dy Y koordinátabeli eltérés.
	 * @param dx X koordinátabeli eltérés.
	 * @return
	 */
	public static Field getFieldRelative(Piece p, int dy, int dx) {
		Field where = p.getField();
		Board b = where.getBoard();
		int y = where.getYCoord();
		int x = where.getXCoord();
		if(y + dy >= 0 && y + dy < 8 && x + dx >= 0 && x + dx < 8) {
			Field f = b.getFieldAt(y + dy, x + dx);
			return f;
		}
		return null;
	}
	
}
