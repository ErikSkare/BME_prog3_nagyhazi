package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import logic.pieces.Bishop;
import logic.pieces.King;
import logic.pieces.Knight;
import logic.pieces.Pawn;
import logic.pieces.Queen;
import logic.pieces.Rook;

/**
 * @author Skáre Erik
 */
public class Board implements Serializable {
	
	private static final long serialVersionUID = 1918514707848647485L;

	/**
	 * A tábla mezői.
	 */
	private Field[][] fields;
	
	/**
	 * Az eddig lépett lépések.
	 */
	private LinkedList<Move> pastMoves;
	
	/**
	 * A tábla aktuális állapota (múltbeli vagy sem)
	 */
	transient private ListIterator<Move> pastMovesIt;
	
	/**
	 * A világos királya
	 */
	private King whiteKing;
	
	/**
	 * A sötét királya
	 */
	private King blackKing;
	
	/**
	 * A gyalog promóciót megvalósító interfész.
	 */
	public interface Promotion {
		/**
		 * Megadja az adott gyaloghoz tartozó promóciós bábut.
		 * @param p a gyalog.
		 * @return A promóciós bábu.
		 */
		public Piece getPiece(Pawn p);
	}
	
	/**
	 * Az aktuális promóció.
	 */
	transient private Promotion promotion;
	
	/**
	 * Konstruktor, kezdeti állapotba állítja a táblát.
	 * @param whiteCapturedPool világos által leütöttek.
	 * @param blackCapturedPool sötét által leütöttek.
	 */
	public Board(List<Piece> whiteCapturedPool, List<Piece> blackCapturedPool) {
		this.fields = new Field[8][8];
		this.pastMoves = new LinkedList<Move>();
		this.pastMovesIt = this.pastMoves.listIterator();
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
	 * Egy adott koordinátájú mezőt ad vissza.
	 * @param y vertikális pozíció.
	 * @param x horizontális pozíció.
	 * @return A pozíción lévő mező.
	 */
	public final Field getFieldAt(int y, int x) { return this.fields[y][x]; }
	
	/**
	 * Visszaadja a megadott gyaloghoz tartozó promóciós bábut.
	 * @param p gyalog.
	 * @return A bábu, amivé a paraszt promótál.
	 */
	public final Piece getPromotionPiece(Pawn p) {
		return this.promotion.getPiece(p);
	}
	
	/**
	 * Visszaadja a promóciót megvalósító interfészt.
	 * @return A promóciót megvalósító interfész.
	 */
	public final Promotion getPromotion() { return this.promotion; }
	
	/**
	 * Beállítja a promócióhoz tartozó interfészt.
	 * @param p a promóció.
	 */
	public final void setPromotion(Promotion p) { this.promotion = p; }
	
	/**
	 * Megadja a tábla aktuális állapotát, ha egy adott szín következik.
	 * @param isWhiteNext fehér következik-e.
	 * @return Az aktuális állapot.
	 */
	public final Party.State getCurrentState(boolean isWhiteNext) { 
		King k = this.getKing(isWhiteNext);
		if(k.isInCheck() && !hasAvailableMoves(isWhiteNext)) {
			if(isWhiteNext)
				return Party.State.BLACK_VICTORY;
			else
				return Party.State.WHITE_VICTORY;
		}
		if(!k.isInCheck() && !hasAvailableMoves(isWhiteNext))
			return Party.State.DRAW;
		return Party.State.ONGOING;
	}
	
	/**
	 * Hozzáad egy lépést a már lépet lépésekhez.
	 * @param m az új lépés.
	 */
	public final void addPastMove(Move m) { 
		pastMoves.addFirst(m); 
		pastMovesIt = pastMoves.listIterator();
	}
	
	/**
	 * Visszaállítja a már lépett lépéseket iterátorát a jelenbe.
	 */
	public final void resetPastMovesIt() {
		this.pastMovesIt = this.pastMoves.listIterator();
	}
	
	/**
	 * Visszaadja a múltbeli lépéseket tároló listát.
	 * @return Az eddigi lépések.
	 */
	public final LinkedList<Move> getPastMoves() { return this.pastMoves; }
	
	/**
	 * Visszalép a táblán a korábbi lépések alapján.
	 * @return Sikeres volt-e a művelet.
	 */
	public final boolean stepBack() {
		if(pastMovesIt.hasNext()) {
			Move m = pastMovesIt.next();
			m.executeReverse();
			return true;
		}
		return false;
	}
	
	/**
	 * Előrelép a táblán ha régebbi állapotnál vagyunk.
	 * @return Sikeres volt-e a művelet.
	 */
	public final boolean stepForward() {
		if(pastMovesIt.hasPrevious()) {
			Move m = pastMovesIt.previous();
			m.execute();
			return true;
		}
		return false;
	}
	
	/**
	 * Visszapörgeti a lépéseket az aktuálisan következő lépéshez.
	 */
	public final void stepToNow() {
		while(this.stepForward());
	}
	
	/**
	 * Megadja, hogy a tábla az aktuális állásnál van, vagy múltbeli
	 * állásokat nézegetünk éppen.
	 * @return A legutóbbi állást kezeljük-e.
	 */
	public final boolean isInNow() {
		return !pastMovesIt.hasPrevious();
	}
	
	/**
	 * Megadja az adott szín által kontrollált összes mezőt.
	 * @param isWhite fehér-e.
	 * @return Az összes adott szín által kontrollált mező.
	 */
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
	
	/**
	 * Megadja az adott színhez tartozó királyt.
	 * @param isWhite fehér-e.
	 * @return Az adott szín királya.
	 */
	public final King getKing(boolean isWhite) {
		if(isWhite)
			return whiteKing;
		else
			return blackKing;
	}
	
	/**
	 * Megadja, hogy a megadott játékos tud-e lépni az aktuális állásból.
	 * @param isWhite fehér-e.
	 * @return Van-e legális lépése a megadott színnek.
	 */
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
