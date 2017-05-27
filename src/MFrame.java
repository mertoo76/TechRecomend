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
import javax.swing.JTextField;

public class MFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 87, 418, 129);
		contentPane.add(scrollPane);
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		
		textField = new JTextField();
		textField.setBounds(73, 11, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnFind = new JButton("Find");
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Controller cont = new Controller();
		
				textPane.setText("");
				
				
				/*	if(rdbtnJava.isSelected()){
					
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
				}*/
				
				
				String[] args = new String[3];
				args[0] = "/data/crawl/root";
				args[1] = "1";
				args[2]= textField.getText();
				try {
					cont.main(args);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				////////////
				String tmp="";
				textPane.setText(tmp);
				for(String x: cont.frameRule){
					tmp=tmp+x+"\n";
					
				}
				textPane.setText(tmp);
				tmp="";
			}
		});
		btnFind.setBounds(335, 227, 89, 23);
		contentPane.add(btnFind);
		
		
		
		JLabel lblTeknoloji = new JLabel("Teknoloji:");
		lblTeknoloji.setBounds(7, 14, 66, 14);
		contentPane.add(lblTeknoloji);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textPane.setText("");
				textPane.updateUI();
			}
		});
		btnClear.setBounds(236, 227, 89, 23);
		contentPane.add(btnClear);
		
		
	}
}
