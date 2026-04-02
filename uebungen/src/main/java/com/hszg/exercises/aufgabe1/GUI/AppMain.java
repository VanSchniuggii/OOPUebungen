package uebungen.src.main.java.com.hszg.exercises.aufgabe1.GUI;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentListener;

import uebungen.src.main.java.com.hszg.exercises.aufgabe1.Logic.CurrencyCalcEnumImpl;
import uebungen.src.main.java.com.hszg.exercises.aufgabe1.Logic.CurrencyCalcImpl;
import uebungen.src.main.java.com.hszg.exercises.aufgabe1.Logic.CurrencyCalculator;

public class AppMain {

	static CurrencyCalculator currencyCalc = new CurrencyCalcImpl();

	public static void main(String[] args) {
		SwingUtilities.invokeLater(AppMain::createAndShowUI);
	}

	private static void createAndShowUI() {
		JFrame frame = new JFrame("Currency Calculator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		JMenu settingsMenu = new JMenu("Settings");
		JMenuItem standardImplItem = new JMenuItem("Use standard implementation");
		JMenuItem enumImplItem = new JMenuItem("Use enum implementation (not ready)");
		settingsMenu.add(standardImplItem);
		settingsMenu.add(enumImplItem);
		menuBar.add(settingsMenu);
		frame.setJMenuBar(menuBar);

		JPanel content = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel inputLabel = new JLabel("Amount to convert:");
		JTextField inputField = new JTextField(15);

		JLabel outputLabel = new JLabel("Converted amount:");
		JTextField outputField = new JTextField(15);
		outputField.setEditable(false);

		String[] currencies = currencyCalc.getCurrencyNames().toArray(new String[0]);
		JComboBox<String> fromCurrency = new JComboBox<>(currencies);
		JComboBox<String> toCurrency = new JComboBox<>(currencies);

		fromCurrency.setSelectedItem("EUR");
		toCurrency.setSelectedItem("EUR");
		
		JButton swapDirectionButton = new JButton("Swap currencies");

		JLabel newCurrencyLabel = new JLabel("Currency to add:");
		JTextField newCurrencyField = new JTextField(10);

		JLabel exchangeRateLabel = new JLabel("1 new currency equals:");
		JTextField exchangeRateField = new JTextField(10);
		JComboBox<String> referenceCurrency = new JComboBox<>(currencies);
		JLabel enumModeInfoLabel = new JLabel(" ");
		enumModeInfoLabel.setPreferredSize(new java.awt.Dimension(420,36));
		enumModeInfoLabel.setVerticalAlignment(JLabel.TOP);

		referenceCurrency.setSelectedItem("EUR");

		JButton addCurrencyButton = new JButton("Add currency");

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

			for (String name : availableCurrencies) {
				fromCurrency.addItem(name);
				toCurrency.addItem(name);
				referenceCurrency.addItem(name);
			}

			if (currentFrom != null && availableCurrencies.contains(currentFrom)) {
				fromCurrency.setSelectedItem(currentFrom);
			} else {
				fromCurrency.setSelectedItem("EUR");
			}

			if (currentTo != null && availableCurrencies.contains(currentTo)) {
				toCurrency.setSelectedItem(currentTo);
			} else {
				toCurrency.setSelectedItem("EUR");;
			}

			if (currentReference != null && availableCurrencies.contains(currentReference)) {
				referenceCurrency.setSelectedItem(currentReference);
			} else {
				referenceCurrency.setSelectedItem("EUR");
			}
		};

		Runnable switchAddCurrencyAvailability = () -> {
			boolean canAdd = currencyCalc instanceof CurrencyCalcImpl;
			newCurrencyLabel.setEnabled(canAdd);
			newCurrencyField.setEnabled(canAdd);
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
		content.add(inputLabel, gbc);

		gbc.gridx = 1;
		content.add(inputField, gbc);

		gbc.gridx = 2;
		content.add(fromCurrency, gbc);

		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		gbc.fill = GridBagConstraints.NONE;
		content.add(swapDirectionButton, gbc);
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;

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
		gbc.gridy = 4;
		gbc.gridwidth = 4;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.NONE;
		content.add(enumModeInfoLabel, gbc);
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;

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

		standardImplItem.addActionListener(event -> {
			currencyCalc = new CurrencyCalcImpl();
			switchAddCurrencyAvailability.run();
			refreshCurrencySelectors.run();
			recalculate.run();
		});

		enumImplItem.addActionListener(event -> {
			CurrencyCalculator previousImplementation = currencyCalc;
			try {
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
			String newCurrency = newCurrencyField.getText().trim();
			String reference = (String) referenceCurrency.getSelectedItem();

			if (newCurrency.isEmpty() || reference == null) {
				System.err.println("New currency name and reference currency must be provided.");
				return;
			}

			try {
				double rate = normalizeValue(exchangeRateField.getText());
				currencyCalc.addCurrency(newCurrency, rate, reference);
				refreshCurrencySelectors.run();
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
