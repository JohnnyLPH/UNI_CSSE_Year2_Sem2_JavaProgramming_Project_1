// TMF2954 Java Programming Group Project
// Group Name: LUCKY
// Group Members:
//     - LAU PIKK HEANG (75359)
//     - YUKI CHUNG PEI YING (77237)
//     - ANDREA ANG XIAO XUAN (73347)
// Health Diary App: Content Page (Super Class for other pages).
// Implement ActionListener for redirecting user to different pages in CardLayout.
import java.awt.event.*;
import javax.swing.*;


public class ContentPage extends JPanel implements ActionListener {
    private ContentPanel contentPanel;

    // Optional, use private class, comment out if directly implement ActionListener interface.
    // Redirect to different pages based on buttons clicked.
    // private class RedirectListener implements ActionListener {
    //     @Override
    //     public void actionPerformed(ActionEvent e) {
    //         // Change displayed page in content panel.
    //         ((CardLayout) contentPanel.getLayout()).show(contentPanel, e.getActionCommand());

    //         // For debugging, comment out if not used.
    //         // System.out.println("Button Click: " + e.getActionCommand());
    //         return;
    //     }
    // }

    // Constructor.
    public ContentPage(ContentPanel contentPanel) {
        this.contentPanel = contentPanel;
        setBackground(HealthDiary.BG_COLOR);
        return;
    }

    // Getter.
    public ContentPanel getContentPanel() {
        return this.contentPanel;
    }

    // Method.
    // Needed if use private class, otherwise comment out.
    // public RedirectListener getNewRedirectListener() {
    //     return new RedirectListener();
    // }

    // Redirect to different pages based on buttons clicked.
    @Override
    public void actionPerformed(ActionEvent e) {
        // Switch to previous panel of the Content Panel containing this page.
        if (e.getActionCommand().equals("Last Panel")) {
            this.contentPanel.switchLastPanel();
        }
        // Change displayed page in Content Panel.
        else {
            this.contentPanel.switchPage(e.getActionCommand());
        }

        // For debugging, comment out if not used.
        System.out.println("Button Click: " + e.getActionCommand());
        return;
    }
}
