package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import algorithm.SDES_Algorithm;

public class SDES_GUI {

	private JFrame frame;
	private JTextField plainTextTranslationField;
	private JTextField plainBinaryField;
	private JTextField cipherTextTranslationField;
	private JTextField cipherBinaryField;
	private JTextField keyField;

	public SDES_GUI() {
		initialize();
		frame.setVisible(true);
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("S-DES GUI");
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());

		JPanel mainPanel = new JPanel(new GridLayout(1, 2));
		JPanel plainPanel = new JPanel(new GridLayout(2, 2));
		JPanel cipherPanel = new JPanel(new GridLayout(2, 2));

		plainTextTranslationField = new JTextField();
		plainBinaryField = new JTextField();
		cipherTextTranslationField = new JTextField();
		cipherBinaryField = new JTextField();

		plainPanel.add(new JLabel("明文翻译"));
		plainPanel.add(plainTextTranslationField);
		plainPanel.add(new JLabel("明文"));
		plainPanel.add(plainBinaryField);
		cipherPanel.add(new JLabel("密文翻译"));
		cipherPanel.add(cipherTextTranslationField);
		cipherPanel.add(new JLabel("密文"));
		cipherPanel.add(cipherBinaryField);

		mainPanel.add(plainPanel);
		mainPanel.add(cipherPanel);
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);

		JPanel keyPanel = new JPanel();
		JLabel lblKey = new JLabel("密钥:");
		keyField = new JTextField(10);
		keyPanel.add(lblKey);
		keyPanel.add(keyField);

		JButton btnEncrypt = new JButton("加密");
		btnEncrypt.addActionListener(this::encryptAction);

		JButton btnDecrypt = new JButton("解密");
		btnDecrypt.addActionListener(this::decryptAction);

		keyPanel.add(btnEncrypt);
		keyPanel.add(btnDecrypt);

		frame.getContentPane().add(keyPanel, BorderLayout.SOUTH);

		// 居中显示窗口
		frame.setLocationRelativeTo(null);
	}

	private void encryptAction(ActionEvent e) {
		String plaintextTranslation = plainTextTranslationField.getText();
		String plaintextBinary = plainBinaryField.getText();
		String key = keyField.getText();

		SDES_Algorithm sdes = new SDES_Algorithm();

		// 明文文本框显示
		String binaryText = "";
		for (char c : plaintextTranslation.toCharArray()) {
			String binaryString = Integer.toBinaryString(c);
			while (binaryString.length() < 8) {
				binaryString = "0" + binaryString;
			}
			binaryText += binaryString;
		}
		plainBinaryField.setText(binaryText);

		String encryptedBinary = sdes.encryptString(plaintextTranslation, key);
		cipherBinaryField.setText(encryptedBinary);

		StringBuilder encryptedTranslation = new StringBuilder();
		for (int i = 0; i < encryptedBinary.length(); i += 8) {
			String block = encryptedBinary.substring(i, i + 8);
			encryptedTranslation.append((char) Integer.parseInt(block, 2));
		}
		cipherTextTranslationField.setText(encryptedTranslation.toString());
	}

	private void decryptAction(ActionEvent e) {
		String ciphertextTranslation = cipherTextTranslationField.getText();
		String ciphertextBinary = cipherBinaryField.getText();
		String key = keyField.getText();

		SDES_Algorithm sdes = new SDES_Algorithm();

		// 密文文本框显示
		String binaryText = "";
		for (char c : ciphertextTranslation.toCharArray()) {
			String binaryString = Integer.toBinaryString(c);
			while (binaryString.length() < 8) {
				binaryString = "0" + binaryString;
			}
			binaryText += binaryString;
		}
		cipherBinaryField.setText(binaryText);

		String decryptedTranslation = sdes.decryptString(ciphertextBinary, key);
		plainTextTranslationField.setText(decryptedTranslation);

		StringBuilder decryptedBinary = new StringBuilder();
		for (char c : decryptedTranslation.toCharArray()) {
			String binaryString = Integer.toBinaryString(c);
			while (binaryString.length() < 8) {
				binaryString = "0" + binaryString;
			}
			decryptedBinary.append(binaryString);
		}
		plainBinaryField.setText(decryptedBinary.toString());
	}

}