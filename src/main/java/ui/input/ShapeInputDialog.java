package ui.input;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides reusable dialogs for collecting numeric shape dimensions.
 *
 * <p>This class centralizes Swing input construction, numeric parsing, and
 * validation so individual shape workflows do not duplicate dialog-handling
 * logic.</p>
 */
public class ShapeInputDialog {

    private static final Dimension FIELD_SIZE =
            new Dimension(200, 25);

    private final Component parent;

    /**
     * Creates an input-dialog helper.
     *
     * @param parent parent component used to position dialogs
     */
    public ShapeInputDialog(Component parent) {
        this.parent = parent;
    }

    /**
     * Requests one numeric value.
     *
     * @param title dialog title
     * @param label field label
     * @return entered value, or {@code null} when cancelled or invalid
     */
    public Double requestSingleValue(
            String title,
            String label) {

        JTextField field = createTextField();

        JPanel panel = createInputPanel(
                new String[]{label},
                new JTextField[]{field});

        SwingUtilities.invokeLater(
                field::requestFocusInWindow);

        if (isInputCancelled(panel, title)) {
            return null;
        }

        double[] values = parseValues(
                new String[]{label},
                new JTextField[]{field});

        return values == null ? null : values[0];
    }

    /**
     * Requests two numeric values.
     *
     * @param title dialog title
     * @param firstLabel first field label
     * @param secondLabel second field label
     * @return entered values, or {@code null} when cancelled or invalid
     */
    public double[] requestTwoValues(
            String title,
            String firstLabel,
            String secondLabel) {

        JTextField firstField = createTextField();
        JTextField secondField = createTextField();

        JPanel panel = createInputPanel(
                new String[]{firstLabel, secondLabel},
                new JTextField[]{firstField, secondField});

        SwingUtilities.invokeLater(
                firstField::requestFocusInWindow);

        if (isInputCancelled(panel, title)) {
            return null;
        }

        return parseValues(
                new String[]{firstLabel, secondLabel},
                new JTextField[]{firstField, secondField});
    }

    /**
     * Requests three numeric values.
     *
     * @param title dialog title
     * @param firstLabel first field label
     * @param secondLabel second field label
     * @param thirdLabel third field label
     * @return entered values, or {@code null} when cancelled or invalid
     */
    public double[] requestThreeValues(
            String title,
            String firstLabel,
            String secondLabel,
            String thirdLabel) {

        JTextField firstField = createTextField();
        JTextField secondField = createTextField();
        JTextField thirdField = createTextField();

        JPanel panel = createInputPanel(
                new String[]{
                        firstLabel,
                        secondLabel,
                        thirdLabel
                },
                new JTextField[]{
                        firstField,
                        secondField,
                        thirdField
                });

        SwingUtilities.invokeLater(
                firstField::requestFocusInWindow);

        if (isInputCancelled(panel, title)) {
            return null;
        }

        return parseValues(
                new String[]{
                        firstLabel,
                        secondLabel,
                        thirdLabel
                },
                new JTextField[]{
                        firstField,
                        secondField,
                        thirdField
                });
    }

    /**
     * Displays a validation warning.
     *
     * @param message validation message
     */
    public void showInputError(String message) {
        JOptionPane.showMessageDialog(
                parent,
                message,
                "Invalid Input",
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Creates a text field used for numeric dimensions.
     *
     * @return configured numeric input field
     */
    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setPreferredSize(FIELD_SIZE);
        return field;
    }

    /**
     * Creates a reusable labeled input panel.
     *
     * @param labels field labels
     * @param fields text fields associated with the labels
     * @return configured input panel
     */
    private JPanel createInputPanel(
            String[] labels,
            JTextField[] fields) {

        JPanel panel =
                new JPanel(new GridBagLayout());

        GridBagConstraints constraints =
                new GridBagConstraints();

        constraints.anchor =
                GridBagConstraints.WEST;

        constraints.insets =
                new Insets(5, 5, 5, 5);

        for (int i = 0; i < labels.length; i++) {
            constraints.gridx = 0;
            constraints.gridy = i;

            panel.add(
                    new JLabel(labels[i]),
                    constraints);

            constraints.gridx = 1;

            panel.add(
                    fields[i],
                    constraints);
        }

        return panel;
    }

    /**
     * Displays an input panel and determines whether it was canceled.
     *
     * @param panel input panel to display
     * @param title dialog title
     * @return {@code true} when the user cancels or closes the dialog
     */
    private boolean isInputCancelled(
            JPanel panel,
            String title) {

        int result =
                JOptionPane.showConfirmDialog(
                        parent,
                        panel,
                        title,
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

        return result != JOptionPane.OK_OPTION;
    }

    /**
     * Parses and validates all fields as one input operation.
     *
     * <p>Every field is checked before a warning is displayed so the user can
     * correct all invalid entries at once instead of receiving one warning at
     * a time. Geometric measurements must be finite values greater than zero.</p>
     *
     * @param labels field labels used in validation messages
     * @param fields fields containing entered values
     * @return parsed values, or {@code null} when any field is invalid
     */
    private double[] parseValues(
            String[] labels,
            JTextField[] fields) {

        double[] values = new double[fields.length];
        List<String> errors = new ArrayList<>();

        for (int i = 0; i < fields.length; i++) {
            String text = fields[i].getText();
            String subject = validationSubject(labels[i]);

            if (text == null || text.trim().isEmpty()) {
                errors.add(
                        "Please enter " + subject + ".");
                continue;
            }

            try {
                double value =
                        Double.parseDouble(text.trim());

                if (!Double.isFinite(value)) {
                    errors.add(
                            "Please enter a finite number for "
                                    + subject
                                    + ".");
                    continue;
                }

                if (value <= 0) {
                    errors.add(
                            "Please enter a value greater than zero for "
                                    + subject
                                    + ".");
                    continue;
                }

                values[i] = value;

            } catch (NumberFormatException e) {
                errors.add(
                        "Please enter a valid number for "
                                + subject
                                + ".");
            }
        }

        if (!errors.isEmpty()) {
            showInputError(
                    String.join("\n", errors));
            return null;
        }

        return values;
    }

    /**
     * Converts a display label into wording suitable for validation messages.
     *
     * <p>Input labels contain punctuation for the Swing form. Validation
     * messages remove that punctuation and use natural sentence wording.
     * Numbered or lettered sides retain their identifying capital letter.</p>
     *
     * @param label display label to convert
     * @return field description suitable for a validation message
     */
    private String validationSubject(String label) {
        String name = label.trim();

        if (name.endsWith(":")) {
            name = name.substring(0, name.length() - 1);
        }

        if (name.matches("Side [A-Z]")) {
            return "side " + name.substring(name.length() - 1);
        }

        return "the " + name.toLowerCase();
    }
}
