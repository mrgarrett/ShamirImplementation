package main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import main.Shamir.SecretShare;
import main.Shamir.*;

public class SSSFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JButton btnRecalculate;
	private JButton btnCalculate;
	private JTextArea textArea;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private BigInteger prime;
	private Shamir shamir;
	private SecretShare[] s;
    //private final Random random;
    //private static final int CERTAINTY = 50;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					SSSFrame frame = new SSSFrame();
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
	public SSSFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Shamir's Secret Sharing");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 6, 438, 23);
		contentPane.add(lblNewLabel);
		
		comboBox = new JComboBox();
		comboBox.setEditable(true);
		comboBox.setBounds(207, 58, 52, 27);
		comboBox.addItem('1');
		comboBox.addItem('2');
		comboBox.addItem('3');
		comboBox.addItem('4');
		comboBox.addItem('5');
		contentPane.add(comboBox);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setEditable(true);
		comboBox_1.setBounds(323, 58, 52, 27);
		comboBox_1.addItem('1');
		comboBox_1.addItem('2');
		comboBox_1.addItem('3');
		comboBox_1.addItem('4');
		comboBox_1.addItem('5');
		contentPane.add(comboBox_1);
		
		JLabel lblNumberOfShares = new JLabel("Number of Shares");
		lblNumberOfShares.setBounds(175, 35, 140, 16);
		contentPane.add(lblNumberOfShares);
		
		JLabel lblNewLabel_1 = new JLabel("Threshold");
		lblNewLabel_1.setBounds(313, 35, 77, 16);
		contentPane.add(lblNewLabel_1);
		
		btnCalculate = new JButton("Calculate");
		btnCalculate.setBounds(175, 87, 117, 29);
		contentPane.add(btnCalculate);
		btnCalculate.addActionListener(this);
		
		JLabel lblEnterASecret = new JLabel("Enter a secret number");
		lblEnterASecret.setBounds(23, 35, 149, 16);
		contentPane.add(lblEnterASecret);
		
		textField = new JTextField();
		textField.setBounds(23, 58, 134, 28);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textArea = new JTextArea();
		textArea.setBounds(23, 117, 404, 69);
		//contentPane.add(textArea);
		
		JScrollPane scroll = new JScrollPane (textArea, 
				   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		scroll.setSize(117, 23);
		scroll.setBounds(23, 117, 404, 69);
		contentPane.add(scroll);
		btnRecalculate = new JButton("Recalculate");
		btnRecalculate.setBounds(127, 214, 117, 29);
		contentPane.add(btnRecalculate);
		btnRecalculate.addActionListener(this);
		
		textField_1 = new JTextField();
		textField_1.setBounds(256, 213, 134, 28);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setEditable(true);
		comboBox_2.setBounds(58, 215, 52, 27);
		comboBox_2.addItem('1');
		comboBox_2.addItem('2');
		comboBox_2.addItem('3');
		comboBox_2.addItem('4');
		comboBox_2.addItem('5');
		contentPane.add(comboBox_2);
		
		JLabel lblRecoveredSecret = new JLabel("Recovered Secret");
		lblRecoveredSecret.setBounds(264, 198, 142, 16);
		contentPane.add(lblRecoveredSecret);
		
		JLabel lblThreshold = new JLabel("Threshold");
		lblThreshold.setBounds(48, 198, 77, 16);
		contentPane.add(lblThreshold);
				
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnRecalculate) {
			//textField_1.setText("Working!");
			prime = shamir.getPrime();
			BigInteger result = shamir.combine(s, prime);
			String something = new String(result.toString());
			textField_1.setText(something);
			System.out.println("Recalculating got: "+ something);
		}
		if (e.getSource() == btnCalculate) {
			int numShares = Integer.parseInt(comboBox.getSelectedItem().toString());
			int threshold = Integer.parseInt(comboBox_1.getSelectedItem().toString());
			Integer secret = Integer.parseInt(textField.getText());
			BigInteger secretInt =  BigInteger.valueOf(secret.intValue());

			shamir = new Shamir(threshold, numShares);
			s = shamir.split(secretInt);
			StringBuilder builder = new StringBuilder();
			for (SecretShare share : s){
				builder.append(share.toString()+"\n");
			}
			if (numShares < threshold) {
				textArea.setText("Shares cannot be less than threshold!");
			}else{
				textArea.setText(builder.toString());
			}
        }
	}
		
}

	