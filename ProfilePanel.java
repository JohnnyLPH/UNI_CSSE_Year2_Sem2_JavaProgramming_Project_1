// TMF2954 Java Programming Group Project
// Group Name: LUCKY
// Group Members:
//     - LAU PIKK HEANG (75359)
//     - YUKI CHUNG PEI YING (77237)
//     - ANDREA ANG XIAO XUAN (73347)
// Health Diary App: Profile Panel.
// Contain View Profile Page, Edit Profile Page.
import java.awt.*;
import javax.swing.*;


public class ProfilePanel extends ContentPanel {
    private ContentPage viewProfile, editProfile;
    private JLabel viewProfileHeading;
    private JButton returnPanelBtn;

    // Constructor.
    public ProfilePanel(ContentPanel lastContentPanel) {
        super(lastContentPanel);

        addViewProfile();
        return;
    }

    // Method.
    public void addViewProfile() {
        viewProfile = new ContentPage(this);
        viewProfile.setLayout(new BorderLayout());

        // Button for redirecting to Last Panel.
        returnPanelBtn = new JButton(HealthDiary.UNI_RETURN_ARROW + " Back");
        // Adjust button font.
        returnPanelBtn.setFont(HealthDiary.SMALL_BTN_FONT);
        // Adjust button color.
        returnPanelBtn.setForeground(HealthDiary.BTN_FG_COLOR);
        returnPanelBtn.setBackground(HealthDiary.BTN_BG_COLOR);
        // Adjust button action.
        returnPanelBtn.setActionCommand("Last Panel");
        returnPanelBtn.addActionListener(viewProfile);
        returnPanelBtn.setFocusable(false);

        viewProfile.add(returnPanelBtn, BorderLayout.NORTH);

        // Heading for View Profile.
        viewProfileHeading = new JLabel("User Profile");
        // Adjust heading font.
        viewProfileHeading.setFont(HealthDiary.MAIN_FONT);
        // Adjust heading color.
        viewProfileHeading.setForeground(HealthDiary.TEXT_COLOR);

        viewProfile.add(viewProfileHeading, BorderLayout.CENTER);

        addPage(viewProfile, "View Profile", 0);
        return;
    }

    public boolean refreshViewProfile(boolean displayNow) {
        if (removePage("View Profile")) {
            addViewProfile();
    
            if (displayNow) {
                switchPage("View Profile");
            }
            return true;
        }
        return false;
    }
}
