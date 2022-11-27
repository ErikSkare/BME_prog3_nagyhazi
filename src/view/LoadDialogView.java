package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import logic.Party;

/**
 * @author Skáre Erik
 */
public class LoadDialogView extends JDialog {

	private static final long serialVersionUID = -4233439167393865912L;
	
	/**
	 * Egy callback, ami a játék betöltése után meghívódik.
	 */
	public interface Callback {
		public void run(Party p);
	}
	
	/**
	 * A callback egy példánya.
	 */
	private Callback cb;
	
	/**
	 * A ComboBox, ami a kiválasztható partikat jeleníti meg.
	 */
	private JComboBox<String> combo;
	
	/**
	 * A gomb, amivel betölthető a parti.
	 */
	private JButton submit;
	
	/**
	 * A gomb, amivel be lehet zárni a dialógust.
	 */
	private JButton cancel;
	
	/**
	 * Konstruktor
	 * @param fr a tartalmazó frame.
	 * @param cb callback.
	 */
	public LoadDialogView(MainFrame fr, Callback cb) {
		super(fr, "Load game");
		
		this.cb = cb;
		
		this.setLayout(new FlowLayout());
		this.setSize(new Dimension(300, 80));
		this.setResizable(false);
		this.setLocationRelativeTo(fr);
		
		File [] files = MainFrame.searchDir.listFiles(new FilenameFilter() {
		    @Override
		    public boolean accept(File dir, String name) {
		        return name.endsWith(".parti");
		    }
		});
		
		combo = new JComboBox<String>();
		for(File f : files)
			combo.addItem(f.getName());
		
		submit = new JButton("Load");
		if(combo.getItemCount() == 0)
			submit.setEnabled(false);
		cancel = new JButton("Cancel");
		
		submit.addActionListener(new OnLoadRequest());
		submit.addActionListener((e) -> { setVisible(false); });
		cancel.addActionListener((e) -> { setVisible(false); });
		
		this.add(combo);
		this.add(submit);
		this.add(cancel);
	}
	
	/**
	 * A parti fájlból beolvasásának logikáját valósítja meg.
	 */
	class OnLoadRequest implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String fileName = (String) combo.getSelectedItem();
			Party result = null;
			FileInputStream fis = null;
			ObjectInputStream ois = null;
			try {
				File f = new File(MainFrame.searchDir, fileName);
				fis = new FileInputStream(f);
				ois = new ObjectInputStream(fis);
				result = (Party) ois.readObject();
			} catch (IOException | ClassNotFoundException e1) {
				e1.printStackTrace();
			} finally {
				try {
					if(ois != null)
						ois.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			cb.run(result);
		}
		
	}
}
