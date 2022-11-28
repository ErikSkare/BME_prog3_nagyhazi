package logic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class MoveTest {
	
	private Board b;
	
	private Piece wp;
	
	private List<Piece> whiteCapturedPool = new ArrayList<Piece>();
	
	private List<Piece> blackCapturedPool = new ArrayList<Piece>();
	
	@Before
	public void init() {
		this.b = new Board(whiteCapturedPool, blackCapturedPool);
		this.wp = b.getFieldAt(6, 4).getPiece();
	}
	
	@Test
	public void testExecute() {
		Move m = Utils.findMoveByIndicator(wp, b.getFieldAt(4, 4));
		m.execute();
		assertEquals(wp.getField(), b.getFieldAt(4, 4));
		assertFalse(wp.getHasMoved());
		assertTrue(wp.canTakeEnPassant());
	}
	
	@Test
	public void testExecuteWithoutCallback() {
		Move m = Utils.findMoveByIndicator(wp, b.getFieldAt(4, 4));
		m.executeWithoutCallback();
		assertEquals(wp.getField(), b.getFieldAt(4, 4));
		assertFalse(wp.getHasMoved());
		assertFalse(wp.canTakeEnPassant());
	}
	
	@Test
	public void testExecuteReverse() {
		Move m = Utils.findMoveByIndicator(wp, b.getFieldAt(4, 4));
		m.execute();
		m.executeReverse();
		assertEquals(wp.getField(), b.getFieldAt(6, 4));
		assertFalse(wp.getHasMoved());
		assertTrue(wp.canTakeEnPassant());
	}
	
}
