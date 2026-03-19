package uebungen.src.main.java.com.hszg.exercises.aufgabe1.GUI;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
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
		JComboBox<String> fromCurrency = new JComboBox<>(currencies);
		JComboBox<String> toCurrency = new JComboBox<>(currencies);
		JButton swapDirectionButton = new JButton("Swap");

		JLabel newCurrencyLabel = new JLabel("New Currency:");
		JTextField newCurrencyField = new JTextField(10);

		JLabel exchangeRateLabel = new JLabel("Exchange Rate:");
		JTextField exchangeRateField = new JTextField(10);
		JComboBox<String> referenceCurrency = new JComboBox<>(currencies);

		JButton addCurrencyButton = new JButton("Add Currency");

		gbc.gridx = 0;
		gbc.gridy = 0;
		content.add(inputLabel, gbc);

		gbc.gridx = 1;
		content.add(inputField, gbc);

		gbc.gridx = 2;
		content.add(fromCurrency, gbc);

		gbc.gridx = 3;
		content.add(swapDirectionButton, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		content.add(outputLabel, gbc);

		gbc.gridx = 1;
		content.add(outputField, gbc);

		gbc.gridx = 2;
		content.add(toCurrency, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		content.add(newCurrencyLabel, gbc);

		gbc.gridx = 1;
		content.add(newCurrencyField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		content.add(exchangeRateLabel, gbc);

		gbc.gridx = 1;
		content.add(exchangeRateField, gbc);

		gbc.gridx = 2;
		content.add(referenceCurrency, gbc);

		gbc.gridx = 3;
		content.add(addCurrencyButton, gbc);

		swapDirectionButton.addActionListener(event -> {
			Object currentFrom = fromCurrency.getSelectedItem();
			Object currentTo = toCurrency.getSelectedItem();
			fromCurrency.setSelectedItem(currentTo);
			toCurrency.setSelectedItem(currentFrom);
		});

		frame.add(content, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
