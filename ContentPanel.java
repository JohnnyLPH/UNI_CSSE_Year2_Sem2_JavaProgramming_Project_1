// TMF2954 Java Programming Group Project
// Group Name: LUCKY
// Group Members:
//     - LAU PIKK HEANG (75359)
//     - YUKI CHUNG PEI YING (77237)
//     - ANDREA ANG XIAO XUAN (73347)
// Health Diary App: Content Panel.
// Store content pages (i.e., home page and other subpages) using card layout.
import java.awt.*;
import javax.swing.*;


public class ContentPanel extends JPanel {
    public final String[] ALL_PAGES = {"HomePage", "ProfilePage", "RecordPage", "GraphPage"};
    private HomePage homePage;
    private ProfilePage profilePage;

    // Constructor.
    public ContentPanel() {
        setLayout(new CardLayout());
        setBackground(AppFrame.BG_COLOR);

        // Add Home Page.
        homePage = new HomePage(this);
        add(homePage, ALL_PAGES[0]);

        // Add Profile Page.
        profilePage = new ProfilePage(this);
        add(profilePage, ALL_PAGES[1]);
        
        return;
    }
}
