package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Egy általános bábu.
 * @author Skáre Erik
 */
abstract public class Piece implements Serializable {
	
	private static final long serialVersionUID = -6678099664988104827L;

	/**
	 * A mező, amin a bábu tartózkodik.
	 */
	private Field field;
	
	/**
	 * A bábu színét jelzi.
	 */
	private boolean isWhite;
	
	/**
	 * A bábu értéke.
	 */
	private int value;
	
	/**
	 * Lista, amibe kerül a bábu ha leütik.
	 */
	private List<Piece> capturedPool;
	
	/**
	 * Lépett-e már a bábu.
	 */
	private boolean hasMoved;
	
	/**
	 * Konstruktor
	 * @param field			mező
	 * @param isWhite		fehér-e
	 * @param value			érték
	 * @param capturedPool	lista
	 */
	public Piece(Field field, boolean isWhite, int value, List<Piece> capturedPool) {
		this.field = field;
		this.isWhite = isWhite;
		this.value = value;
		this.capturedPool = capturedPool;
		this.hasMoved = false;
	}
	
	/**
	 * Visszaadja a bábu lehetséges lépéseit az adott pozícióból.
	 * @return A lehetséges lépések.
	 */
	abstract public List<Move> getAvailableMoves();
	
	/**
	 * Visszaadja a bábu által ellenőrzés alatt tartott mezőket.
	 * @return A bábu által kontrollált mezőket.
	 */
	abstract public List<Field> getControlledFields();
	
	/**
	 * Lekéri a bábut reprezentáló kép elérési útvonalát.
	 * @return A kép elérési útvonala, amin a bábu van.
	 */
	abstract public String getImageSrc();
	
	/**
	 * Megadja, hogy a bábu leüthető-e a speciális En Passant lépéssel.
	 * Csak gyalognál van jelentősége.
	 * @return Leüthető-e En passant lépéssel.
	 */
	public boolean canTakeEnPassant() { return false; }
	
	/**
	 * Elmulasztja az en passant lépést.
	 */
	public void forfeitEnPassant() { }
	
	/**
	 * Megadja a mezőt, amin a bábu áll éppen.
	 * @return A mező.
	 */
	public final Field getField() { return this.field; }
	
	/**
	 * Beállítja a mezőt, amin a bábu tartózkodik éppen.
	 * @param f mező.
	 */
	public final void setField(Field f) { this.field = f; }
	
	/**
	 * Megadja, hogy a bábu világos színű-e.
	 * @return Fehér-e.
	 */
	public final boolean getIsWhite() { return this.isWhite; }
	
	/**
	 * Megadja a bábu értékét pontokban.
	 * @return A bábu értéke.
	 */
	public final int getValue() { return this.value; }
	
	/**
	 * Megadja a listát, ahova a bábu kerül ha leütik.
	 * @return A leütöttek listája.
	 */
	public final List<Piece> getCapturedPool() { return this.capturedPool; }
	
	/**
	 * Megadja, hogy egy adott bábu leütheti-e ezt a bábut.
	 * @param p a leütést kezdeményező
	 * @return Leüthető-e.
	 */
	public boolean canBeTaken(Piece p) {
		return this.getIsWhite() != p.getIsWhite();
	}
	
	/**
	 * Megadja, hogy a bábu lépett-e már.
	 * @return Lépett-e már a bábu.
	 */
	public boolean getHasMoved() { return this.hasMoved; }
	
	/**
	 * Leüti a bábut.
	 */
	public void capture() { 
		this.capturedPool.add(this);
		this.field.stepOff();
	}
	
	/**
	 * Végrehajtja a megadott lépést.
	 * @param m a lépés.
	 */
	public void makeMove(Move m) { 
		Board b = this.field.getBoard();
		b.addPastMove(m);
		this.hasMoved = true;
		m.execute();
		for(int i = 0; i < 8; ++i)
			for(int j = 0; j < 8; ++j)
				if(b.getFieldAt(i, j).hasPiece() && b.getFieldAt(i, j).getPiece().getIsWhite() != this.getIsWhite())
					b.getFieldAt(i, j).getPiece().forfeitEnPassant();
	}
	
	/**
	 * Kiszűri azokat a lépéseket, amelyek eredményeként a király sakkban lenne.
	 * @param moves a lépések.
	 * @return Azok a lépések, amelyek után a király nem lesz sakkban.
	 */
	protected final ArrayList<Move> filterMoves(ArrayList<Move> moves) {
		ArrayList<Move> filtered = new ArrayList<Move>();
		Board b = this.getField().getBoard();
		for(Move m : moves) {
			m.executeWithoutCallback();
			if(!b.getKing(this.getIsWhite()).isInCheck())
				filtered.add(m);
			m.executeReverse();
		}
		return filtered;
	}
	
}
