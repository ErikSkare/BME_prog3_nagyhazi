package logic.players;

import logic.Player;

/**
 * @author Skáre Erik
 * Egy ember játékos.
 */
public class RealPlayer extends Player {
	
	/**
	 * A lépési jog.
	 */
	private boolean canStep;

	/**
	 * @param playing a parti, amit játszik.
	 */
	public RealPlayer() {
		super();
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