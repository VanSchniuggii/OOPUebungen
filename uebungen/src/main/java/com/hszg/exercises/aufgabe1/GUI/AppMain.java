package uebungen.src.main.java.com.hszg.exercises.aufgabe1.GUI;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class AppMain {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(AppMain::createAndShowUI);
	}

	private static void createAndShowUI() {
		JFrame frame = new JFrame("Currency Calculator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel content = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel inputLabel = new JLabel("Input Amount:");
		JTextField inputField = new JTextField(15);

		JLabel outputLabel = new JLabel("Output Amount:");
		JTextField outputField = new JTextField(15);
		outputField.setEditable(false);

		String[] currencies = { "EUR", "USD", "GBP", "JPY" };
		JLabel fromLabel = new JLabel("From Currency:");
		JComboBox<String> fromCurrency = new JComboBox<>(currencies);

		JLabel toLabel = new JLabel("To Currency:");
		JComboBox<String> toCurrency = new JComboBox<>(currencies);

		gbc.gridx = 0;
		gbc.gridy = 0;
		content.add(inputLabel, gbc);

		gbc.gridx = 1;
		content.add(inputField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		content.add(outputLabel, gbc);

		gbc.gridx = 1;
		content.add(outputField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		content.add(fromLabel, gbc);

		gbc.gridx = 1;
		content.add(fromCurrency, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		content.add(toLabel, gbc);

		gbc.gridx = 1;
		content.add(toCurrency, gbc);

		frame.add(content, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
