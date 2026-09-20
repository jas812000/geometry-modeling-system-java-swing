import ui.MainWindow;

import javax.swing.SwingUtilities;

/**
 * Launches the Geometry Modeling application.
 *
 * <p>The application entry point is intentionally kept separate from the
 * Swing window, input workflows, domain model, and rendering components.</p>
 */
public final class ShapeApp {

    private ShapeApp() {
        // Prevent instantiation because application startup is static.
    }

    /**
     * Launches the main application window on the Swing Event Dispatch Thread.
     *
     * @param args command-line arguments; not used by this application
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
