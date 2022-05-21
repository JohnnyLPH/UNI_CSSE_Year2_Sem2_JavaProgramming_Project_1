// TMF2954 Java Programming Group Project
// Group Name: LUCKY
// Group Members:
//     - LAU PIKK HEANG (75359)
//     - YUKI CHUNG PEI YING (77237)
//     - ANDREA ANG XIAO XUAN (73347)
// Health Diary App: Content Page (Super Class for other pages).
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class ContentPage extends JPanel {
    private ContentPanel contentPanel;

    // Redirect to different pages based on buttons clicked.
    private class RedirectListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Change displayed page in content panel.
            ((CardLayout) contentPanel.getLayout()).show(contentPanel, e.getActionCommand());

            // For debugging, comment out if not used.
            // System.out.println("Button Click: " + e.getActionCommand());
            return;
        }
    }

    // Constructor.
    public ContentPage(ContentPanel contentPanel) {
        this.contentPanel = contentPanel;
        return;
    }

    // Getter.
    public ContentPanel getContentPanel() {
        return contentPanel;
    }

    // Method.
    public RedirectListener getNewRedirectListener() {
        return new RedirectListener();
    }
}
