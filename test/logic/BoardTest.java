package logic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {
	
	private Board b;
	
	private List<Piece> whiteCapturedPool = new ArrayList<Piece>();
	
	private List<Piece> blackCapturedPool = new ArrayList<Piece>();
	
	@Before
	public void init() {
		this.b = new Board(whiteCapturedPool, blackCapturedPool);
	}

	@Test
	public void checkColors() {
		for(int i = 0; i <= 1; ++i) {
			for(int j = 0; j < 8; ++j) {
				Field f = b.getFieldAt(i, j);
				assertTrue(f.hasPiece());
				assertFalse(f.getPiece().getIsWhite());
			}
		}
		for(int i = 6; i <= 7; ++i) {
			for(int j = 0; j < 8; ++j) {
				Field f = b.getFieldAt(i, j);
				assertTrue(f.hasPiece());
				assertTrue(f.getPiece().getIsWhite());
			}
		}
	}
	
	@Test
	public void checkGameState() {
		assertEquals(b.getCurrentState(true), Party.State.ONGOING);
	}
	
	@Test
	public void checkPastMoves() {
		assertEquals(b.getPastMoves().size(), 0);
		assertTrue(b.isInNow());
	}
	
	@Test
	public void checkHasAvailableMoves() {
		assertTrue(b.hasAvailableMoves(true));
	}
	
	@Test
	public void checkStep() {
		assertFalse(b.stepForward());
		assertFalse(b.stepBack());
	}

}
