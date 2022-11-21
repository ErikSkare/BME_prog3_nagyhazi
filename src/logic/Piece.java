package logic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Skáre Erik
 * Egy absztrakt bábu.
 */
abstract public class Piece {
	
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
	List<Piece> capturedPool;
	
	/**
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
	}
	
	/**
	 * @return A lehetséges lépések.
	 */
	abstract public List<Move> getAvailableMoves();
	
	/**
	 * @return Visszaadja a bábu által kontrollált mezőket.
	 */
	abstract public List<Field> getControlledFields();
	
	/**
	 * @return A kép elérési útvonala, amin a bábu van.
	 */
	abstract public String getImageSrc();
	
	/**
	 * @return A mező.
	 */
	public final Field getField() { return this.field; }
	
	/**
	 * Beállítja a mezőt, amin a bábu van.
	 * @param f mező.
	 */
	public final void setField(Field f) { this.field = f; }
	
	/**
	 * @return Fehér-e.
	 */
	public final boolean getIsWhite() { return this.isWhite; }
	
	/**
	 * @return A bábu értéke.
	 */
	public final int getValue() { return this.value; }
	
	/**
	 * @param p a leütést kezdeményező
	 * @return Leüthető-e.
	 */
	public boolean canBeTaken(Piece p) {
		return this.getIsWhite() != p.getIsWhite();
	}
	
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
		this.field.getBoard().addPastMove(m);
		m.execute();
	}
	
	/**
	 * @param moves a lépések.
	 * @return Azok a lépések, amelyek után a király nem lesz sakkban.
	 */
	protected final ArrayList<Move> filterMoves(ArrayList<Move> moves) {
		ArrayList<Move> filtered = new ArrayList<Move>();
		for(Move m : moves) {
			m.executeWithoutCallback();
			if(!this.getField().getBoard().getKing(this.getIsWhite()).isInCheck())
				filtered.add(m);
			m.executeReverse();
		}
		return filtered;
	}
	
}
