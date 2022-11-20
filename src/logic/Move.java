package logic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Skáre Erik
 * Egy lehetséges lépés, az effektjeivel.
 */
public class Move {
	
	/**
	 * Az effektek.
	 */
	private List<Effect> effects;
	
	/**
	 * A lépést jelző mező. (megjelenítéshez)
	 */
	private Field indicator;
	
	/**
	 * Konstruktor
	 * @param indicator mező.
	 */
	public Move(Field indicator) {
		this.effects = new ArrayList<Effect>();
		this.indicator = indicator;
	}
	
	/**
	 * @return A lépést jelző mező.
	 */
	public final Field getIndicator() { return this.indicator; }
	
	/**
	 * @param e az új effekt
	 */
	public void addEffect(Effect e) {
		this.effects.add(e);
	}
	
	/**
	 * Végrehajtja a lépést.
	 */
	public void execute() {
		for(Effect e : this.effects)
			e.run();
	}
	
}
