package logic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import logic.pieces.King;

public class KingTest {
	
	private Board b;
	
	private King wk;
	
	private List<Piece> whiteCapturedPool = new ArrayList<Piece>();
	
	private List<Piece> blackCapturedPool = new ArrayList<Piece>();
	
	@Before
	public void init() {
		this.b = new Board(whiteCapturedPool, blackCapturedPool);
		this.wk = b.getKing(true);
	}
	
	@Test
	public void testInitialCheck() {
		assertFalse(wk.isInCheck());
	}
	
	@Test
	public void testCheckmate() {
		Piece bq = b.getFieldAt(0, 3).getPiece();
		Piece bp = b.getFieldAt(1, 4).getPiece();
		Piece wp1 = b.getFieldAt(6, 5).getPiece();
		Piece wp2 = b.getFieldAt(6, 6).getPiece();
		wp1.makeMove(Utils.findMoveByIndicator(wp1, b.getFieldAt(4, 5)));
		wp2.makeMove(Utils.findMoveByIndicator(wp2, b.getFieldAt(4, 6)));
		bp.makeMove(Utils.findMoveByIndicator(bp, b.getFieldAt(3, 4)));
		bq.makeMove(Utils.findMoveByIndicator(bq, b.getFieldAt(4, 7)));
		assertTrue(wk.isInCheck());
		assertEquals(wk.getAvailableMoves().size(), 0);
		assertEquals(b.getCurrentState(true), Party.State.BLACK_VICTORY);
	}
	
}
