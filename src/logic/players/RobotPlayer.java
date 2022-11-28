package logic.players;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import logic.Board;
import logic.Field;
import logic.Move;
import logic.Piece;
import logic.Player;
import logic.pieces.Bishop;
import logic.pieces.Knight;
import logic.pieces.Queen;
import logic.pieces.Rook;

/**
 * Egy robot játékost megvalósító osztály.
 * @author Skáre Erik
 */
public class RobotPlayer extends Player {

	private static final long serialVersionUID = -1716132081207932133L;

	/**
	 * Egy bábu és egy hozzá tartozó lépés párt definiál.
	 */
	class PieceMove {
		/**
		 * A bábu.
		 */
		private Piece p;
		
		/**
		 * A hozzá tartozó lépés.
		 */
		private Move m;
		
		/**
		 * Konstruktor
		 * @param p a bábu.
		 * @param m a lépés.
		 */
		public PieceMove(Piece p, Move m) {
			this.p = p;
			this.m = m;
		}
		
		/**
		 * Visszaadja a bábut.
		 * @return A bábu.
		 */
		public final Piece getPiece() { return this.p; }
		
		/**
		 * Visszaadja a lépést.
		 * @return A lépés.
		 */
		public final Move getMove() { return this.m; }
	}
	
	/**
	 * Konstruktor
	 * @param isWhite világossal játszik-e.
	 */
	public RobotPlayer(boolean isWhite) {
		super(isWhite);
	}

	@Override
	public void grantStepPermission() {
		Board b = this.getPlaying().getBoard();
		
		Board.Promotion save = b.getPromotion();
		
		Board.Promotion[] promotions = new Board.Promotion[4];
		promotions[0] = (p) -> new Bishop(null, p.getIsWhite(), 3, p.getCapturedPool());
		promotions[1] = (p) -> new Knight(null, p.getIsWhite(), 3, p.getCapturedPool());
		promotions[2] = (p) -> new Rook(null, p.getIsWhite(), 5, p.getCapturedPool());
		promotions[3] = (p) -> new Queen(null, p.getIsWhite(), 9, p.getCapturedPool());
		int rndProm = new Random().nextInt(4);
		b.setPromotion(promotions[rndProm]);
		
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
		
		b.setPromotion(save);
		
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
