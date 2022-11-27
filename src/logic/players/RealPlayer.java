package logic.players;

import logic.Player;

/**
 * @author Skáre Erik
 * Egy ember játékos.
 */
public class RealPlayer extends Player {
	
	private static final long serialVersionUID = 6274118246228590898L;
	
	/**
	 * A lépési jog.
	 */
	private boolean canStep;

	/**
	 * @param isWhite világossal játszik-e.
	 */
	public RealPlayer(boolean isWhite) {
		super(isWhite);
		this.canStep = false;
	}

	@Override
	public void grantStepPermission() {
		this.canStep = true;
	}
	
	@Override
	public void resignStepPermission() {
		this.canStep = false;
		this.getPlaying().returnStepPermission(this);
	}

	@Override
	public boolean hasStepPermission() {
		return this.canStep;
	}
	
}