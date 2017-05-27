import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MFrame frame = new MFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JRadioButton rdbtnJava = new JRadioButton("java");
		rdbtnJava.setBounds(6, 7, 109, 23);
		contentPane.add(rdbtnJava);
		
		JRadioButton rdbtnPhp = new JRadioButton("php");
		rdbtnPhp.setBounds(6, 42, 109, 23);
		contentPane.add(rdbtnPhp);
		
		JButton btnFind = new JButton("Find");
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnJava.isSelected()){
					Controller cont = new Controller();
					String[] args = new String[3];
					args[0] = "/data/crawl/root";
					args[1] = "1";
					args[2]= rdbtnJava.getText();
					try {
						cont.main(args);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if(rdbtnPhp.isSelected()){
					Controller cont = new Controller();
					String[] args = new String[3];
					args[0] = "/data/crawl/root";
					args[1] = "1";
					args[2]= rdbtnPhp.getText();
					try {
						cont.main(args);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		btnFind.setBounds(316, 193, 89, 23);
		contentPane.add(btnFind);
	}
}
