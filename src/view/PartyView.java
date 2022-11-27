package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import logic.Board;
import logic.Party;
import logic.pieces.Bishop;
import logic.pieces.Knight;
import logic.pieces.Queen;
import logic.pieces.Rook;

/**
 * @author Skáre Erik
 * Egy partihoz tartozó nézet.
 */
public class PartyView extends JPanel {
	
	private static final long serialVersionUID = 1L;

	/**
	 * A parti, amihez a nézet tartozik.
	 */
	private Party party;
	
	/**
	 * A tábla nézete.
	 */
	private BoardView boardView;
	
	/**
	 * Az aktuális állapotot kiíró label.
	 */
	private JLabel stateText;
	
	/**
	 * A gomb, amivel döntetlenné lehet tenni a partit.
	 */
	private JButton drawButton;
	
	/**
	 * A gomb, ami elmenti a partit.
	 */
	private JButton saveButton;
	
	enum Promotion {
		BISHOP,
		KNIGHT,
		ROOK,
		QUEEN
	}
	
	/**
	 * A promóciós bábut lehet kiválasztani vele.
	 */
	private JComboBox<Promotion> promotionCombo;
	
	/**
	 * A mentés dialógushoz tartozó nézet.
	 */
	private SaveDialogView saveDialog;
	
	/**
	 * Konstruktor
	 * @param fr a tartalmazó frame.
	 * @param party a parti.
	 */
	public PartyView(JFrame fr, Party party) {
		super();
		this.setLayout(new BorderLayout());
		this.party = party;
		this.party.getBoard().resetPastMovesIt();
		
		this.boardView = new BoardView(party.getBoard(), this);
		this.party.resetChangeListeners();
		this.party.addChangeListener(() -> {
			this.boardView.repaint();
		});
		
		this.stateText = new JLabel(this.party.getPartyState().toString());
		this.stateText.setHorizontalAlignment(JLabel.CENTER);
		
		this.drawButton = new JButton("Make draw");
		this.drawButton.setFocusable(false);
		this.drawButton.addActionListener(new OnDrawButtonClick());
		if(this.party.getPartyState() != Party.State.ONGOING)
			this.drawButton.setEnabled(false);;
		
		this.saveButton = new JButton("Save game");
		this.saveButton.setFocusable(false);
		this.saveButton.addActionListener(new SaveAction());
		
		this.promotionCombo = new JComboBox<Promotion>(Promotion.values());
		this.promotionCombo.setFocusable(false);
		this.promotionCombo.addActionListener(new ComboAction());
		this.setPromotion();
		
		this.saveDialog = new SaveDialogView(fr, this.party);
		
		this.add(this.boardView, BorderLayout.CENTER);
		this.add(this.stateText, BorderLayout.NORTH);
		this.add(this.drawButton, BorderLayout.EAST);
		this.add(this.saveButton, BorderLayout.WEST);
		this.add(this.promotionCombo, BorderLayout.SOUTH);
	}
	
	/**
	 * @return A parti.
	 */
	public final Party getParty() { return this.party; }
	
	/**
	 * @return Az állapotot megjelenítő label.
	 */
	public final JLabel getStateText() { return this.stateText; }
	
	/**
	 * Kikapcsolja a döntetlen lehetőségét.
	 */
	public final void disableDrawButton() { this.drawButton.setEnabled(false); }
	
	/**
	 * Beállítja a promóciós bábut a kiválasztottra.
	 */
	private void setPromotion() {
		Board b = this.party.getBoard();
		switch((Promotion) this.promotionCombo.getModel().getSelectedItem()) {
		case BISHOP:
			b.setPromotion((p) -> new Bishop(null, p.getIsWhite(), 3, p.getCapturedPool()));
			break;
		case KNIGHT:
			b.setPromotion((p) -> new Knight(null, p.getIsWhite(), 3, p.getCapturedPool()));
			break;
		case ROOK:
			b.setPromotion((p) -> new Rook(null, p.getIsWhite(), 3, p.getCapturedPool()));
			break;
		case QUEEN:
			b.setPromotion((p) -> new Queen(null, p.getIsWhite(), 3, p.getCapturedPool()));
			break;
		default:
			break;
		}
	}
	
	class OnDrawButtonClick implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			party.makeDraw();
			stateText.setText(party.getPartyState().toString());
			disableDrawButton();
		}
		
	}
	
	class ComboAction implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			setPromotion();
		}
		
	}
	
	class SaveAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			party.getBoard().stepToNow();
			repaint();
			saveDialog.setVisible(true);
		}
		
	}
}
