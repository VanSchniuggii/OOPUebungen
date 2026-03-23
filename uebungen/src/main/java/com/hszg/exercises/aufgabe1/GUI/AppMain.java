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
import javax.swing.event.DocumentListener;

import uebungen.src.main.java.com.hszg.exercises.aufgabe1.Logic.Currency;
import uebungen.src.main.java.com.hszg.exercises.aufgabe1.Logic.CurrencyCalcImpl;

public class AppMain {

	static CurrencyCalcImpl currencyCalc = new CurrencyCalcImpl();

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

		String[] currencies = new String[currencyCalc.getCurrencies().size()];
		for (int i = 0; i < currencyCalc.getCurrencies().size(); i++) {
			currencies[i] = currencyCalc.getCurrencies().get(i).getName();
		}
		JComboBox<String> fromCurrency = new JComboBox<>(currencies);
		JComboBox<String> toCurrency = new JComboBox<>(currencies);
		JButton swapDirectionButton = new JButton("Swap");

		JLabel newCurrencyLabel = new JLabel("New Currency:");
		JTextField newCurrencyField = new JTextField(10);

		JLabel exchangeRateLabel = new JLabel("Exchange Rate:");
		JTextField exchangeRateField = new JTextField(10);
		JComboBox<String> referenceCurrency = new JComboBox<>(currencies);

		JButton addCurrencyButton = new JButton("Add Currency");

		Runnable recalculate = () -> {
			try {
				double amount = normalizeValue(inputField.getText().trim());
				String from = (String) fromCurrency.getSelectedItem();
				String to = (String) toCurrency.getSelectedItem();
				double result = currencyCalc.convert(amount, from, to);
				outputField.setText(String.format("%.2f", result));
			} catch (Exception e) {
				outputField.setText("");
			}
		};

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
			recalculate.run();
		});

		addCurrencyButton.addActionListener(event -> {
			String newCurrency = newCurrencyField.getText().trim();

			for (Currency c : currencyCalc.getCurrencies()) {
				if (c.getName().equalsIgnoreCase(newCurrency.trim())) {
					System.err.println("Currency already exists.");
					return;
				}
			}			
			String reference = (String) referenceCurrency.getSelectedItem();
			if (newCurrency.isEmpty() || reference == null) {
				// Handle empty input (e.g., show a dialog)
				System.err.println("New currency name and reference currency must be provided.");
				return;
			}

			

			try {
				double rate = normalizeValue(exchangeRateField.getText());
				if (rate <= 0) {
					System.err.println("Exchange rate must be a positive number.");
					return;
				}
				currencyCalc.addCurrency(newCurrency, rate, reference);
				String currentFrom = (String) fromCurrency.getSelectedItem();
				String currentTo = (String) toCurrency.getSelectedItem();
				fromCurrency.removeAllItems();
				toCurrency.removeAllItems();
				referenceCurrency.removeAllItems();
				for (int i = 0; i < currencyCalc.getCurrencies().size(); i++) {
					String name = currencyCalc.getCurrencies().get(i).getName();
					fromCurrency.addItem(name);
					toCurrency.addItem(name);
					referenceCurrency.addItem(name);

					if (name.equals(currentFrom)) {
						fromCurrency.setSelectedItem(name);
					}
					if (name.equals(currentTo)) {
						toCurrency.setSelectedItem(name);
					}
				}
			} catch (Exception e) {
				// Handle invalid input (e.g., show a dialog)
				System.err.println("Invalid input for new currency or exchange rate.");
				return;
			}
		});	

		
		DocumentListener inputListener = new DocumentListener() {
			@Override
			public void insertUpdate(javax.swing.event.DocumentEvent e) {
				recalculate.run();
			}

			@Override
			public void removeUpdate(javax.swing.event.DocumentEvent e) {
				recalculate.run();
			}

			@Override
			public void changedUpdate(javax.swing.event.DocumentEvent e) {
				recalculate.run();
			}
		};

		inputField.getDocument().addDocumentListener(inputListener);

		fromCurrency.addActionListener(event -> recalculate.run());
		toCurrency.addActionListener(event -> recalculate.run());

		frame.add(content, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private static double normalizeValue(String input) {
			if (input == null) {
				throw new IllegalArgumentException("Input cannot be null.");
			}

			String s = input.trim();
			if (s.isEmpty()) {
				throw new IllegalArgumentException("Input cannot be empty.");
			}

			s = s.replace(" ", "").replace("'", "");

			int lastComma = s.lastIndexOf(',');
			int lastDot = s.lastIndexOf('.');

			if (lastComma >= 0 && lastDot >= 0) {
				if (lastComma > lastDot) {
					s = s.replace(".", "");
					s = s.replace(",", ".");
				} else {
					s = s.replace(",", "");
				}
			} else if (lastComma >= 0) {
				s = s.replace(",", ".");
			}

			if (!s.matches("-?\\d+(\\.\\d+)?")) {
				throw new IllegalArgumentException("Input must be a valid number.");
			}

			return Double.parseDouble(s);
	};

	
}
