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
import javax.swing.JTextPane;
import javax.swing.JScrollPane;

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
		rdbtnJava.setBounds(6, 7, 66, 23);
		contentPane.add(rdbtnJava);
		
		JRadioButton rdbtnPhp = new JRadioButton("php");
		rdbtnPhp.setBounds(74, 7, 72, 23);
		contentPane.add(rdbtnPhp);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 87, 418, 129);
		contentPane.add(scrollPane);
		
		JTextPane textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		
		JButton btnFind = new JButton("Find");
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Controller cont = new Controller();
				if(rdbtnJava.isSelected()){
					
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
				String tmp="";
				for(String x: cont.frameRule){
					tmp=tmp+x+"\n";
					
				}
				textPane.setText(tmp);
			}
		});
		btnFind.setBounds(335, 227, 89, 23);
		contentPane.add(btnFind);
		
		
	}
}
