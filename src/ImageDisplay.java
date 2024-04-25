/**
 * James Stevens
 * 13 June 2023
 * CMSC 335 - Object-Oriented and Concurrent Programming
 *
 * This program creates a graphical user interface (GUI) window for
 * displaying  an image.
 * The window includes a label to show the image and a "close"  button.
 *
 */

//package CMSC_335_Project2;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

// declares a public class
public class ImageDisplay {
    private JFrame frame;
    private final JLabel label;
    private final JButton closeButton;

    // declares a public constructor that takes in one parameter to generate
    // and display an image based on the provided image path
    public ImageDisplay(String imagePath) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPanel = new JPanel(new BorderLayout());

        label = new JLabel();
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(600, 400,
                Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        label.setIcon(scaledIcon);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        closeButton = new JButton("Close");
        closeButton.addActionListener((ActionEvent e) -> {
            frame.dispose();
        });

        // Disable all buttons except closeButton
        Component[] buttons = buttonPanel.getComponents();
        for (Component button : buttons) {
            if (button != closeButton) {
                button.setEnabled(false);
            }
        }

        buttonPanel.add(closeButton);

        contentPanel.add(label, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.setContentPane(contentPanel);

        frame.pack(); // Adjusts the frame size based on the label size

        // Get the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Calculate the frame position to center it on the screen
        int frameX = (screenWidth - frame.getWidth()) / 2;
        int frameY = (screenHeight - frame.getHeight()) / 2;
        frame.setLocation(frameX, frameY);

        frame.setVisible(true);

    } // end imageDisplay method

}// end imageDisplay class

