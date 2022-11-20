package logic;

import java.util.List;

import logic.pieces.Pawn;

/**
 * @author Skáre Erik
 * Egy tábla az aktuális állással.
 */
public class Board {
	
	/**
	 * Az állás lehetséges állapotai.
	 */
	enum State {
		ONGOING,
		DRAW,
		WHITE_VICTORY,
		BLACK_VICTORY
	}
	
	/**
	 * A tábla mezői.
	 */
	private Field[][] fields;
	
	/**
	 * Konstruktor
	 * @param whiteCapturedPool világos által leütöttek.
	 * @param blackCapturedPool sötét által leütöttek.
	 */
	public Board(List<Piece> whiteCapturedPool, List<Piece> blackCapturedPool) {
		this.fields = new Field[8][8];
		for(int i = 0; i < 8; ++i)
			for(int j = 0; j < 8; ++j)
				this.fields[i][j] = new Field(this, i, j, (i + j) % 2 == 0);
		for(int i = 0; i < 8; ++i) {
			this.fields[1][i].stepOn(new Pawn(this.fields[1][i], false, 1, whiteCapturedPool));
			this.fields[6][i].stepOn(new Pawn(this.fields[6][i], true, 1, blackCapturedPool));
		}
	}
	
	/**
	 * @param y vertikális pozíció.
	 * @param x horizontális pozíció.
	 * @return A pozíción lévő mező.
	 */
	public final Field getFieldAt(int y, int x) { return this.fields[y][x]; }
	
	/**
	 * @param isWhiteNext fehér következik-e.
	 * @return Az aktuális állapot.
	 */
	public final State getCurrentState(boolean isWhiteNext) { return State.ONGOING; }
	
}
