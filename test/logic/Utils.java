package logic;

import java.util.stream.Collectors;

/**
 * A logikai tesztek által használt közös metódusok.
 * @author Skáre Erik
 */
public class Utils {
	
	/**
	 * Megkeres egy lépést egy indikátor alapján. (adott bábu lépése)
	 * @param p			a bábu.
	 * @param indicator	indikátor.
	 * @return A lépés.
	 */
	public static Move findMoveByIndicator(Piece p, Field indicator) {
		return p.getAvailableMoves().stream()
			.filter(item -> item.getIndicator().equals(indicator))
			.collect(Collectors.toList()).get(0);
	}
	
	/**
	 * Van-e a bábunak egy adott indikátorral jelzett lépése.
	 * @param p			a bábu.
	 * @param indicator	indikátor.
	 * @return A lépés megléte.
	 */
	public static boolean hasMoveByIndicator(Piece p, Field indicator) {
		return p.getAvailableMoves().stream()
				.filter(item -> item.getIndicator().equals(indicator))
				.collect(Collectors.toList()).size() > 0;
	}
	
}
