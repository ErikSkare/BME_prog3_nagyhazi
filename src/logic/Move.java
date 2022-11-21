package logic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Skáre Erik
 * Egy lehetséges lépés, az effektjeivel.
 */
public class Move {
	
	public interface Callback {
		public void call();
	}
	
	/**
	 * A lépés után meghívódik.
	 */
	private Callback cb;
	
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
	 * @param cb callback.
	 */
	public Move(Field indicator, Callback cb) {
		this.effects = new ArrayList<Effect>();
		this.indicator = indicator;
		this.cb = cb;
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
		this.executeWithoutCallback();
		this.cb.call();
	}
	
	/**
	 * Végrehajtja a lépést callback nélkül.
	 */
	public void executeWithoutCallback() {
		for(Effect e : this.effects)
			e.run();
	}
	
	/**
	 * Végrehajtja a lépés fordítottját.
	 */
	public void executeReverse() {
		for(int i = this.effects.size() - 1; i >= 0; --i)
			for(Effect e : this.effects.get(i).reverse())
				e.run();
	}
}
