package logic;

import java.util.ArrayList;
import java.util.List;

import logic.Board.State;

/**
 * @author Skáre Erik
 * Egy partit reprezentál.
 */
public class Party {
	
	/**
	 * A világossal játszó játékos.
	 */
	private Player white;
	
	/**
	 * A sötéttel játszó játékos.
	 */
	private Player black;
	
	/**
	 * A tábla, amin a játék zajlik.
	 */
	private Board board;
	
	private Board.State partyState;
	
	/**
	 * A lista, ahova a világos által leütött bábuk kerülnek.
	 */
	private List<Piece> whiteCapturedPool;
	
	/**
	 * A lista, ahova a sötét által leütött bábuk kerülnek.
	 */
	private List<Piece> blackCapturedPool;
	
	/**
	 * @param white világos.
	 * @param black sötét.
	 */
	public Party(Player white, Player black) {
		this.white = white;
		this.black = black;
		this.partyState = State.ONGOING;
		this.whiteCapturedPool = new ArrayList<Piece>();
		this.blackCapturedPool = new ArrayList<Piece>();
		this.board = new Board(whiteCapturedPool, blackCapturedPool);
		white.grantStepPermission();
	}
	
	/**
	 * @return A 'white' attribútum értéke.
	 */
	public final Player getWhite() { return this.white; }
	
	/**
	 * @return A 'black' attribútum értéke.
	 */
	public final Player getBlack() { return this.black; }
	
	/**
	 * @return Az aktuálisan lépő játékos, vagy null ha nincs.
	 */
	public final Player getCurrentPlayer() {
		if(!white.hasStepPermission() && !black.hasStepPermission())
			return null;
		if(getIsWhiteCurrent())
			return white;
		else
			return black;
	}
	
	/**
	 * @return Világos-e a következő.
	 */
	public final boolean getIsWhiteCurrent() {
		return white.hasStepPermission();
	}
	
	/**
	 * @return A 'board' attribútum értéke.
	 */
	public final Board getBoard() { return this.board; }
	
	/**
	 * @return A 'partyState' attribútum értéke.
	 */
	public final Board.State getPartyState() { return this.partyState; }
	
	/**
	 * Visszaadja a lépési jogát valaki.
	 * @param by a visszaadó játékos.
	 */
	public void returnStepPermission(Player by) {
		boolean isWhiteNext = (by == this.black);
		Board.State state = this.board.getCurrentState(isWhiteNext);
		this.partyState = state;
		if(state != Board.State.ONGOING)
			return;
		if(isWhiteNext)
			this.white.grantStepPermission();
		else
			this.black.grantStepPermission();
	}
	
}