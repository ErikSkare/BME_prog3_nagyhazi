package logic.players;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import logic.Board;
import logic.Field;
import logic.Move;
import logic.Piece;
import logic.Player;

/**
 * @author Skáre Erik
 * Egy robot játékos, aki véletlenszerűen lép.
 */
public class RobotPlayer extends Player {

	private static final long serialVersionUID = -1716132081207932133L;

	/**
	 * Egy bábuhoz tartozó lépés.
	 */
	class PieceMove {
		private Piece p;
		
		private Move m;
		
		public PieceMove(Piece p, Move m) {
			this.p = p;
			this.m = m;
		}
		
		public final Piece getPiece() { return this.p; }
		
		public final Move getMove() { return this.m; }
	}
	
	/**
	 * @param isWhite világossal játszik-e.
	 */
	public RobotPlayer(boolean isWhite) {
		super(isWhite);
	}

	@Override
	public void grantStepPermission() {
		Board b = this.getPlaying().getBoard();
		
		ArrayList<PieceMove> moves = new ArrayList<PieceMove>();
		for(int i = 0; i < 8; ++i) {
			for(int j = 0; j < 8; ++j) {
				Field f = b.getFieldAt(i, j);
				if(f.hasPiece() && f.getPiece().getIsWhite() == getIsWhite()) {
					List<Move> ret = f.getPiece().getAvailableMoves();
					for(Move m : ret)
						moves.add(new PieceMove(f.getPiece(), m));
				}
			}
		}
		
		int rnd = new Random().nextInt(moves.size());
		moves.get(rnd).getPiece().makeMove(moves.get(rnd).getMove());
		
		this.resignStepPermission();
	}

	@Override
	public void resignStepPermission() {
		this.getPlaying().returnStepPermission(this);
	}

	@Override
	public boolean hasStepPermission() {
		return false;
	}

}
