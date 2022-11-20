package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import logic.Field;
import logic.Move;

/**
 * @author Skáre Erik
 * Egy mezőhöz tartozó nézet.
 */
public class FieldView extends JPanel {
	
	private static final long serialVersionUID = 1L;

	/**
	 * A mező, amit megjelenítünk.
	 */
	private Field field;
	
	/**
	 * A partihoz tartozó nézet.
	 */
	private BoardView boardView;
	
	/**
	 * Az aktuális lépés, ami a mezőhöz tartozik.
	 */
	private Move move;
	
	/**
	 * @param field		a mező.
	 * @param partyView	a nézet.
	 */
	public FieldView(Field field, BoardView boardView) {
		super();
		this.field = field;
		this.boardView = boardView;
		
		this.addMouseListener(new OnClickAction());
	}
	
	/**
	 * @return A mező.
	 */
	public final Field getField() { return this.field; }
	
	/**
	 * @param m az aktuális lépés.
	 */
	public final void setMove(Move m) { 
		this.move = m; 
		this.repaint();
	}
	
	/**
	 * @return Tartozik-e a mezőhöz lépés.
	 */
	public final boolean isMoveable() { return this.move != null; }
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			Dimension d = this.getSize();
			this.setBackground(this.field.getIsWhite() ? Color.white : Color.gray);
			if(this.field.getPiece() != null) {
				BufferedImage img = ImageIO.read(new File(this.field.getPiece().getImageSrc()));
				g.drawImage(img, 0, 0, d.width, d.height, this);
			}
			if(this.isMoveable()) {
				g.setColor(Color.green);
				g.fillOval(d.width / 2 - d.width / 8, 
						   d.height / 2 - d.height / 8, 
						   d.width / 4, d.height / 4);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	class OnClickAction implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if(boardView.getMovingPlayer() == null)
				return;
			if(boardView.getState() == BoardView.State.THINKING) {
				if(field.getPiece() == null) 
					return;
				if(boardView.getMovingPlayer().getPlaying().getIsWhiteCurrent() != field.getPiece().getIsWhite())
					return;
				boardView.addAvailableMoves(field.getPiece(), field.getPiece().getAvailableMoves());
			} else {
				if(isMoveable()) {
					boardView.getActivePiece().makeMove(move);
					boardView.getMovingPlayer().resignStepPermission();
				}
				boardView.clearAvailableMoves();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) { }

		@Override
		public void mouseReleased(MouseEvent e) { }

		@Override
		public void mouseEntered(MouseEvent e) { }

		@Override
		public void mouseExited(MouseEvent e) { }
		
	}
	
}
