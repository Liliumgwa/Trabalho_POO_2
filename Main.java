import view.SistemaAcademicoGUI;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            SistemaAcademicoGUI interfaceGrafica = new SistemaAcademicoGUI();
            interfaceGrafica.setVisible(true);
        });
    }
}