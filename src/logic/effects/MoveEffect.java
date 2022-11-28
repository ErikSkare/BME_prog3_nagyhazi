package logic.effects;

import java.util.ArrayList;
import java.util.List;

import logic.Effect;
import logic.Field;
import logic.Piece;

/**
 * Effekt, amely mozgat egy bábut a táblán.
 * @author Skáre Erik
 */
public class MoveEffect extends Effect {
	
	private static final long serialVersionUID = 7826060350341492898L;

	/**
	 * Ahonnan lépett a bábu
	 */
	private Field from;

	/**
	 * Ahova lép a bábu.
	 */
	private Field to;
	
	/**
	 * A bábu, ami leütődik, vagy null ha nincs ilyen.
	 */
	private Piece capturing;
	
	/**
	 * Konstruktor
	 * @param piece bábu.
	 * @param to	hova.
	 */
	public MoveEffect(Piece piece, Field to) {
		super(piece);
		this.from = piece.getField();
		this.to = to;
		this.capturing = this.to.getPiece();
	}
	
	/**
	 * Megadja, hogy melyik mezőre lép a bábu.
	 * @return A mező, ahova lép.
	 */
	public final Field getTo() { return this.to; }

	@Override
	public void run() {
		this.getPiece().getField().stepOff();
		this.getTo().stepOn(this.getPiece());
	}
	
	@Override
	public List<Effect> reverse() {
		List<Effect> result = new ArrayList<Effect>();
		result.add(new MoveEffect(this.getPiece(), from));
		if(this.capturing != null)
			result.add(new AddEffect(this.capturing, to));
		return result;
	}

}
