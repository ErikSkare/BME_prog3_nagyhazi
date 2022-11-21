package logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import logic.pieces.Bishop;
import logic.pieces.King;
import logic.pieces.Knight;
import logic.pieces.Pawn;
import logic.pieces.Queen;
import logic.pieces.Rook;

/**
 * @author Skáre Erik
 * Egy tábla az aktuális állással.
 */
public class Board {
	
	/**
	 * Az állás lehetséges állapotai.
	 */
	public enum State {
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
	 * Az eddig lépett lépések.
	 */
	private LinkedList<Move> pastMoves;
	
	private King whiteKing;
	
	private King blackKing;
	
	/**
	 * Konstruktor
	 * @param whiteCapturedPool világos által leütöttek.
	 * @param blackCapturedPool sötét által leütöttek.
	 */
	public Board(List<Piece> whiteCapturedPool, List<Piece> blackCapturedPool) {
		this.fields = new Field[8][8];
		this.pastMoves = new LinkedList<Move>();
		for(int i = 0; i < 8; ++i)
			for(int j = 0; j < 8; ++j)
				this.fields[i][j] = new Field(this, i, j, (i + j) % 2 == 0);
		//Kings
		this.blackKing = new King(this.fields[0][4], false, 0, null);
		this.whiteKing = new King(this.fields[7][4], true, 0, null);
		this.fields[0][4].stepOn(blackKing);
		this.fields[7][4].stepOn(whiteKing);
		
		// Pawns
		for(int i = 0; i < 8; ++i) {
			this.fields[1][i].stepOn(new Pawn(this.fields[1][i], false, 1, whiteCapturedPool));
			this.fields[6][i].stepOn(new Pawn(this.fields[6][i], true, 1, blackCapturedPool));
		}
		// Rooks
		this.fields[0][0].stepOn(new Rook(this.fields[0][0], false, 5, whiteCapturedPool));
		this.fields[0][7].stepOn(new Rook(this.fields[0][7], false, 5, whiteCapturedPool));
		this.fields[7][0].stepOn(new Rook(this.fields[7][0], true, 5, blackCapturedPool));
		this.fields[7][7].stepOn(new Rook(this.fields[7][7], true, 5, blackCapturedPool));
		//Bishops
		this.fields[0][2].stepOn(new Bishop(this.fields[0][2], false, 3, whiteCapturedPool));
		this.fields[0][5].stepOn(new Bishop(this.fields[0][5], false, 3, whiteCapturedPool));
		this.fields[7][2].stepOn(new Bishop(this.fields[7][2], true, 3, blackCapturedPool));
		this.fields[7][5].stepOn(new Bishop(this.fields[7][5], true, 3, blackCapturedPool));
		//Queens
		this.fields[0][3].stepOn(new Queen(this.fields[7][3], false, 9, whiteCapturedPool));
		this.fields[7][3].stepOn(new Queen(this.fields[0][3], true, 9, blackCapturedPool));
		//Knights
		this.fields[0][1].stepOn(new Knight(this.fields[0][1], false, 3, whiteCapturedPool));
		this.fields[0][6].stepOn(new Knight(this.fields[0][6], false, 3, whiteCapturedPool));
		this.fields[7][1].stepOn(new Knight(this.fields[7][1], true, 3, blackCapturedPool));
		this.fields[7][6].stepOn(new Knight(this.fields[7][6], true, 3, blackCapturedPool));
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
	public final State getCurrentState(boolean isWhiteNext) { 
		King k = this.getKing(isWhiteNext);
		if(k.isInCheck() && !hasAvailableMoves(isWhiteNext)) {
			if(isWhiteNext)
				return State.BLACK_VICTORY;
			else
				return State.WHITE_VICTORY;
		}
		if(!k.isInCheck() && !hasAvailableMoves(isWhiteNext))
			return State.DRAW;
		return State.ONGOING;
	}
	
	/**
	 * Hozzáad egy lépést a már lépet lépésekhez.
	 * @param m az új lépés.
	 */
	public final void addPastMove(Move m) { pastMoves.addFirst(m); }
	
	/**
	 * @return Az eddigi lépések.
	 */
	public final LinkedList<Move> getPastMoves() { return this.pastMoves; }
	
	public final Set<Field> getControlledFields(boolean isWhite) {
		Set<Field> result = new HashSet<Field>();
		for(int i = 0; i < 8; ++i) {
			for(int j = 0; j < 8; ++j) {
				if(!this.fields[i][j].hasPiece() || 
					this.fields[i][j].getPiece().getIsWhite() != isWhite) continue;
				result.addAll(this.fields[i][j].getPiece().getControlledFields());
			}
		}
		return result;
	}
	
	public final King getKing(boolean isWhite) {
		if(isWhite)
			return whiteKing;
		else
			return blackKing;
	}
	
	public final boolean hasAvailableMoves(boolean isWhite) {
		ArrayList<Move> result = new ArrayList<Move>();
		for(int i = 0; i < 8; ++i) {
			for(int j = 0; j < 8; ++j) {
				if(!this.fields[i][j].hasPiece() ||
					this.fields[i][j].getPiece().getIsWhite() != isWhite) continue;
				result.addAll(this.fields[i][j].getPiece().getAvailableMoves());
			}
		}
		return result.size() != 0;
	}
	
}
