package logic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import logic.players.RealPlayer;

/**
 * A parti bizonyos metódusait teszteli.
 * @author Skáre Erik
 */
public class PartyTest {
	
	private Party p;
	
	private RealPlayer wp;
	
	private RealPlayer bp;
	
	/**
	 * Inicializálja egy partit két játékossal.
	 */
	@Before
	public void init() {
		this.wp = new RealPlayer(true);
		this.bp = new RealPlayer(false);
		this.p = new Party(wp, bp, true);
		this.wp.setPlaying(p);
		this.bp.setPlaying(p);
	}
	
	/**
	 * Teszteli a parti kezdeti állapotát.
	 */
	@Test
	public void initialState() {
		assertFalse(this.wp.hasStepPermission());
		assertFalse(this.bp.hasStepPermission());
		assertNull(this.p.getCurrentPlayer());
		assertTrue(this.p.getCanMakeDraw());
	}
	
	/**
	 * Elindítja a partit és teszteli a lépési jogokat.
	 */
	@Test
	public void startMatch() {
		this.p.startMatch();
		assertTrue(this.wp.hasStepPermission());
		assertFalse(this.bp.hasStepPermission());
		assertEquals(this.p.getCurrentPlayer(), this.wp);
		assertTrue(this.p.getIsWhiteCurrent());
	}
	
	/**
	 * Teszteli a lépési jog visszaadását.
	 */
	@Test
	public void returnStepPermission() {
		this.p.startMatch();
		this.wp.resignStepPermission();
		assertFalse(this.wp.hasStepPermission());
		assertTrue(this.bp.hasStepPermission());
		assertEquals(this.p.getCurrentPlayer(), this.bp);
		assertFalse(this.p.getIsWhiteCurrent());
		assertEquals(this.p.getPartyState(), Party.State.ONGOING);
	}
	
}
