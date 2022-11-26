package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
	
	private JLabel stateText;
	
	private JButton drawButton;
	
	private JButton saveButton;
	
	enum Promotion {
		BISHOP,
		KNIGHT,
		ROOK,
		QUEEN
	}
	
	private JComboBox<Promotion> promotionCombo;
	
	/**
	 * Konstruktor
	 * @param party a parti.
	 */
	public PartyView(Party party) {
		super();
		this.setLayout(new BorderLayout());
		this.party = party;
		
		this.boardView = new BoardView(party.getBoard(), this);
		
		this.stateText = new JLabel(this.party.getPartyState().toString());
		this.stateText.setHorizontalAlignment(JLabel.CENTER);
		
		this.drawButton = new JButton("Make draw");
		this.drawButton.setFocusable(false);
		this.drawButton.addActionListener(new OnDrawButtonClick());
		if(this.party.getPartyState() != Party.State.ONGOING)
			this.drawButton.setEnabled(false);;
		
		this.saveButton = new JButton("Save game");
		this.saveButton.setFocusable(false);
		
		this.promotionCombo = new JComboBox<Promotion>(Promotion.values());
		this.promotionCombo.setFocusable(false);
		this.promotionCombo.addActionListener(new ComboAction());
		this.setPromotion();
		
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
	
	public final JLabel getStateText() { return this.stateText; }
	
	public final void disableDrawButton() { this.drawButton.setEnabled(false); }
	
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
}
