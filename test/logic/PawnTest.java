package logic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * A gyalog lépéseit teszteli.
 * @author Skáre Erik
 */
public class PawnTest {
	
	private Board b;
	
	private Piece wp;
	
	private Piece bp;
	
	private List<Piece> whiteCapturedPool = new ArrayList<Piece>();
	
	private List<Piece> blackCapturedPool = new ArrayList<Piece>();
	
	/**
	 * Incializálja a táblát és hozzá egy világos és sötét gyalogot.
	 */
	@Before
	public void init() {
		this.b = new Board(whiteCapturedPool, blackCapturedPool);
		this.wp = b.getFieldAt(6, 4).getPiece();
		this.bp = b.getFieldAt(1, 3).getPiece();
	}
	
	/**
	 * Teszteli a kettős lépést.
	 */
	@Test
	public void testDoublePawnMove() {
		Move m = Utils.findMoveByIndicator(wp, b.getFieldAt(4, 4));
		wp.makeMove(m);
		assertEquals(wp.getField(), b.getFieldAt(4, 4));
	}
	
	/**
	 * Teszteli az előrelépést.
	 */
	@Test
	public void testSinglePawnMove() {
		Move m = Utils.findMoveByIndicator(wp, b.getFieldAt(5, 4));
		wp.makeMove(m);
		assertEquals(wp.getField(), b.getFieldAt(5, 4));
	}
	
	/**
	 * Teszteli az ütést.
	 */
	@Test
	public void testTake() {
		wp.makeMove(Utils.findMoveByIndicator(wp, b.getFieldAt(4, 4)));
		bp.makeMove(Utils.findMoveByIndicator(bp, b.getFieldAt(3, 3)));
		assertTrue(Utils.hasMoveByIndicator(wp, b.getFieldAt(3, 3)));
	}
	
	/**
	 * Teszteli, hogy az En Passant lépés működik-e.
	 */
	@Test
	public void testEnPassant() {
		wp.makeMove(Utils.findMoveByIndicator(wp, b.getFieldAt(4, 4)));
		wp.makeMove(Utils.findMoveByIndicator(wp, b.getFieldAt(3, 4)));
		bp.makeMove(Utils.findMoveByIndicator(bp, b.getFieldAt(3, 3)));
		assertTrue(Utils.hasMoveByIndicator(wp, b.getFieldAt(2, 3)));
	}
	
}
