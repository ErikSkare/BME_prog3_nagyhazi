package logic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * A tábla bizonyos metódusait teszteli.
 * @author Skáre Erik
 */
public class BoardTest {
	
	private Board b;
	
	private List<Piece> whiteCapturedPool = new ArrayList<Piece>();
	
	private List<Piece> blackCapturedPool = new ArrayList<Piece>();
	
	/**
	 * Inicializálja a táblát.
	 */
	@Before
	public void init() {
		this.b = new Board(whiteCapturedPool, blackCapturedPool);
	}

	/**
	 * Leteszteli, hogy a kezdeti állásban az adott színű bábuk a megfelelő
	 * helyen vannak-e.
	 */
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
	
	/**
	 * Megnézni, hogy a kezdeti állásban megfelelő-e a játék állapota.
	 */
	@Test
	public void checkGameState() {
		assertEquals(b.getCurrentState(true), Party.State.ONGOING);
	}
	
	/**
	 * Leteszteli, hogy a kezdeti állásban nincsenek múltbeli lépések.
	 */
	@Test
	public void checkPastMoves() {
		assertEquals(b.getPastMoves().size(), 0);
		assertTrue(b.isInNow());
	}
	
	/**
	 * Leteszteli, hogy a kezdeti állásban tud-e lépni a világos.
	 */
	@Test
	public void checkHasAvailableMoves() {
		assertTrue(b.hasAvailableMoves(true));
	}
	
	/**
	 * Leteszteli, hogy lehet-e lépkedni a régebbi állások között.
	 * (nem lehet)
	 */
	@Test
	public void checkStep() {
		assertFalse(b.stepForward());
		assertFalse(b.stepBack());
	}

}
