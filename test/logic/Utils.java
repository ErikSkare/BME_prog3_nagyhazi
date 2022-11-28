package logic;

import java.util.stream.Collectors;

public class Utils {
	
	public static Move findMoveByIndicator(Piece p, Field indicator) {
		return p.getAvailableMoves().stream()
			.filter(item -> item.getIndicator().equals(indicator))
			.collect(Collectors.toList()).get(0);
	}
	
	public static boolean hasMoveByIndicator(Piece p, Field indicator) {
		return p.getAvailableMoves().stream()
				.filter(item -> item.getIndicator().equals(indicator))
				.collect(Collectors.toList()).size() > 0;
	}
	
}
