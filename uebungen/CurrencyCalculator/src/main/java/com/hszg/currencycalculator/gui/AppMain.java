package com.hszg.currencycalculator.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentListener;

import com.hszg.currencycalculator.logic.CurrencyCalcImpl;
import com.hszg.currencycalculator.logic.CurrencyCalcEnumImpl;
import com.hszg.currencycalculator.logic.CurrencyCalculator;

public class AppMain {

	static CurrencyCalculator currencyCalc = new CurrencyCalcImpl();

	public static void main(String[] args) {
		SwingUtilities.invokeLater(AppMain::createAndShowUI);
	}

	private static void createAndShowUI() {
		JFrame frame = new JFrame("Currency Calculator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					currencyCalc.saveCurrencyData();
				} catch (Exception ex) {
					System.err.println("Failed to save currency data on app close: " + ex.getMessage());
				}
			}
		});

		JMenuBar menuBar = new JMenuBar();
		JMenu settingsMenu = new JMenu("Settings");
		JMenuItem standardImplItem = new JMenuItem("Use standard implementation");
		JMenuItem enumImplItem = new JMenuItem("Use enum implementation (adding currencies not supported)");
		settingsMenu.add(standardImplItem);
		settingsMenu.add(enumImplItem);
		menuBar.add(settingsMenu);
		frame.setJMenuBar(menuBar);

		JPanel content = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel converterHeading = new JLabel("Currency Converter");
		converterHeading.setFont(converterHeading.getFont().deriveFont(java.awt.Font.BOLD, 16f));
		JSeparator addSectionSeparator = new JSeparator();
		JLabel addCurrencyHeading = new JLabel("Add Currency");
		addCurrencyHeading.setFont(addCurrencyHeading.getFont().deriveFont(java.awt.Font.BOLD, 14f));

		JLabel inputLabel = new JLabel("Amount to convert:");
		JTextField inputField = new JTextField(15);

		JLabel outputLabel = new JLabel("Converted amount:");
		JTextField outputField = new JTextField(15);
		outputField.setEditable(false);

		java.util.List<String> initialCurrencies = currencyCalc.getCurrencyNames();
		String[] currencies = initialCurrencies.toArray(new String[0]);
		JComboBox<String> fromCurrency = new JComboBox<>(currencies);
		JComboBox<String> toCurrency = new JComboBox<>(currencies);
		String defaultCurrency = findCurrencyByCode(initialCurrencies, "EUR");

		fromCurrency.setSelectedItem(defaultCurrency);
		toCurrency.setSelectedItem(defaultCurrency);
		
		JButton swapDirectionButton = new JButton("Swap currencies");

		JLabel newCurrencyShortLabel = new JLabel("New currency code:");
		JTextField newCurrencyShortField = new JTextField(8);
		JLabel newCurrencyVerboseLabel = new JLabel("New currency name:");
		JTextField newCurrencyVerboseField = new JTextField(12);

		JLabel exchangeRateLabel = new JLabel("1 new currency equals:");
		JTextField exchangeRateField = new JTextField(10);
		JComboBox<String> referenceCurrency = new JComboBox<>(currencies);
		JLabel enumModeInfoLabel = new JLabel(" ");
		enumModeInfoLabel.setPreferredSize(new java.awt.Dimension(420,36));
		enumModeInfoLabel.setVerticalAlignment(JLabel.TOP);

		referenceCurrency.setSelectedItem(defaultCurrency);

		JButton addCurrencyButton = new JButton("Add currency");

		Runnable recalculate = () -> {
			try {
				double amount = normalizeValue(inputField.getText().trim());
				String from = (String) fromCurrency.getSelectedItem();
				String to = (String) toCurrency.getSelectedItem();
				double result = currencyCalc.convert(amount, from, to);
				outputField.setText(formatConvertedAmount(result));
			} catch (Exception e) {
				outputField.setText("");
			}
		};

		Runnable refreshCurrencySelectors = () -> {
			String currentFrom = (String) fromCurrency.getSelectedItem();
			String currentTo = (String) toCurrency.getSelectedItem();
			String currentReference = (String) referenceCurrency.getSelectedItem();

			fromCurrency.removeAllItems();
			toCurrency.removeAllItems();
			referenceCurrency.removeAllItems();

			java.util.List<String> availableCurrencies = currencyCalc.getCurrencyNames();
			if (availableCurrencies == null || availableCurrencies.isEmpty()) {
				throw new IllegalStateException("No currencies available in selected implementation.");
			}
			String fallbackCurrency = findCurrencyByCode(availableCurrencies, "EUR");

			for (String name : availableCurrencies) {
				fromCurrency.addItem(name);
				toCurrency.addItem(name);
				referenceCurrency.addItem(name);
			}

			if (currentFrom != null && availableCurrencies.contains(currentFrom)) {
				fromCurrency.setSelectedItem(currentFrom);
			} else {
				fromCurrency.setSelectedItem(fallbackCurrency);
			}

			if (currentTo != null && availableCurrencies.contains(currentTo)) {
				toCurrency.setSelectedItem(currentTo);
			} else {
				toCurrency.setSelectedItem(fallbackCurrency);
			}

			if (currentReference != null && availableCurrencies.contains(currentReference)) {
				referenceCurrency.setSelectedItem(currentReference);
			} else {
				referenceCurrency.setSelectedItem(fallbackCurrency);
			}
		};

		Runnable switchAddCurrencyAvailability = () -> {
			boolean canAdd = currencyCalc instanceof CurrencyCalcImpl;
			newCurrencyShortLabel.setEnabled(canAdd);
			newCurrencyShortField.setEnabled(canAdd);
			newCurrencyVerboseLabel.setEnabled(canAdd);
			newCurrencyVerboseField.setEnabled(canAdd);
			exchangeRateLabel.setEnabled(canAdd);
			exchangeRateField.setEnabled(canAdd);
			referenceCurrency.setEnabled(canAdd);
			addCurrencyButton.setEnabled(canAdd);
			if (canAdd) {
				enumModeInfoLabel.setText("");
			} else {
				enumModeInfoLabel.setText("<html><b>Enum implementation active.</b> Adding new currencies is not supported in this mode! Use the standard implementation to enable this feature.</html>");
			}
			content.revalidate();
			content.repaint();
			
		};

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 4;
		content.add(converterHeading, gbc);
		gbc.gridwidth = 1;

		gbc.gridx = 0;
		gbc.gridy = 1;
		content.add(inputLabel, gbc);

		gbc.gridx = 1;
		content.add(inputField, gbc);

		gbc.gridx = 2;
		content.add(fromCurrency, gbc);

		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.gridheight = 2;
		gbc.fill = GridBagConstraints.NONE;
		content.add(swapDirectionButton, gbc);
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 2;
		content.add(outputLabel, gbc);

		gbc.gridx = 1;
		content.add(outputField, gbc);

		gbc.gridx = 2;
		content.add(toCurrency, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 4;
		content.add(addSectionSeparator, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		content.add(addCurrencyHeading, gbc);
		gbc.gridwidth = 1;

		gbc.gridx = 0;
		gbc.gridy = 5;
		content.add(newCurrencyShortLabel, gbc);

		gbc.gridx = 1;
		content.add(newCurrencyShortField, gbc);

		gbc.gridx = 2;
		content.add(newCurrencyVerboseLabel, gbc);

		gbc.gridx = 3;
		content.add(newCurrencyVerboseField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 4;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.NONE;
		content.add(enumModeInfoLabel, gbc);
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 6;
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

		standardImplItem.addActionListener(event -> {
			try {
				currencyCalc.saveCurrencyData();
			} catch (Exception ex) {
				System.err.println("Failed to save currency data before switching implementation: " + ex.getMessage());
			}
			currencyCalc = new CurrencyCalcImpl();
			switchAddCurrencyAvailability.run();
			refreshCurrencySelectors.run();
			recalculate.run();
		});

		enumImplItem.addActionListener(event -> {
			CurrencyCalculator previousImplementation = currencyCalc;
			try {
				currencyCalc.saveCurrencyData();
				currencyCalc = CurrencyCalcEnumImpl.EUR;
				switchAddCurrencyAvailability.run();
				refreshCurrencySelectors.run();
				recalculate.run();
			} catch (Exception e) {
				currencyCalc = previousImplementation;
				refreshCurrencySelectors.run();
				recalculate.run();
				JOptionPane.showMessageDialog(frame,
						"CurrencyCalcEnumImpl is not ready yet. Standard implementation remains active.",
						"Implementation Not Ready",
						JOptionPane.WARNING_MESSAGE);
			}
		});


		addCurrencyButton.addActionListener(event -> {
			String newCurrencyCode = newCurrencyShortField.getText().trim();
			String newCurrencyName = newCurrencyVerboseField.getText().trim();
			String reference = (String) referenceCurrency.getSelectedItem();

			if (newCurrencyCode.isEmpty() || newCurrencyName.isEmpty() || reference == null) {
				System.err.println("Currency code, currency name and reference currency must be provided.");
				return;
			}

			try {
				double rate = normalizeValue(exchangeRateField.getText());
				String formattedCurrencyName = formatCurrencyName(newCurrencyCode, newCurrencyName);
				currencyCalc.addCurrency(formattedCurrencyName, rate, reference);
				refreshCurrencySelectors.run();
				newCurrencyShortField.setText("");
				newCurrencyVerboseField.setText("");
				exchangeRateField.setText("");
				newCurrencyShortField.requestFocusInWindow();
			} catch (Exception e) {
				System.err.println("Invalid input for new currency or exchange rate.");
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

	private static String findCurrencyByCode(java.util.List<String> currencies, String code) {
		if (currencies == null || currencies.isEmpty() || code == null || code.trim().isEmpty()) {
			return null;
		}

		String normalizedCode = code.trim().toUpperCase();
		for (String currency : currencies) {
			if (currency != null && currency.toUpperCase().startsWith(normalizedCode + " (")) {
				return currency;
			}
		}

		for (String currency : currencies) {
			if (currency != null && currency.equalsIgnoreCase(normalizedCode)) {
				return currency;
			}
		}

		return currencies.get(0);
	}

	private static String formatCurrencyName(String code, String verboseName) {
		return code.trim().toUpperCase() + " (" + verboseName.trim() + ")";
	}

	private static String formatConvertedAmount(double value) {
		double absoluteValue = Math.abs(value);
		if (absoluteValue > 0 && absoluteValue < 0.01) {
			DecimalFormat tinyValueFormatter = new DecimalFormat("0.00000000", DecimalFormatSymbols.getInstance(Locale.US));
			tinyValueFormatter.setGroupingUsed(false);
			return tinyValueFormatter.format(value);
		}

		DecimalFormat standardFormatter = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.US));
		standardFormatter.setGroupingUsed(false);
		return standardFormatter.format(value);
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
	}

	
}

