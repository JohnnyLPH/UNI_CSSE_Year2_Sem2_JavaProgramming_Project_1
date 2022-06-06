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
    private String pageName;
    private int pageIndex;

    // Constructor.
    public ContentPage(ContentPanel cPanel) {
        this.contentPanel = cPanel;
        this.pageName = "Unknown";
        this.setName(this.pageName);
        this.pageIndex = cPanel.getTotalPage();
        setBackground(HealthDiary.BLACK_BG_COLOR);
        return;
    }

    public ContentPage(ContentPanel cPanel, String pName, int pIndex) {
        this.contentPanel = cPanel;
        this.pageName = (pName.compareTo("") == 0) ? "Unknown": pName;
        this.setName(this.pageName);
        this.pageIndex = (pIndex > cPanel.getTotalPage() || pIndex < 0) ? cPanel.getTotalPage(): pIndex;
        setBackground(HealthDiary.BLACK_BG_COLOR);
        return;
    }

    // Setter.
    public boolean setPageName(String pageName) {
        if (pageName.compareTo("") == 0) {
            return false;
        }
        this.pageName = pageName;
        this.setName(pageName);
        return true;
    }

    public boolean setPageIndex(int pageIndex) {
        if (pageIndex > this.contentPanel.getTotalPage() || pageIndex < 0) {
            return false;
        }
        this.pageIndex = pageIndex;
        return true;
    }

    // Getter.
    public ContentPanel getContentPanel() {
        return this.contentPanel;
    }

    public String getPageName() {
        return this.pageName;
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    // Method.
    // Display this Content Page in the containing Content Panel.
    public void display() {
        // Assuming name of this Content Page is set, which should be done when adding it to Content Panel.
        this.contentPanel.switchPage(this.getName());
        return;
    }

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
