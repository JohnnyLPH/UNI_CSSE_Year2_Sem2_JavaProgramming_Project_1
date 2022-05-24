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
    private JButton returnButton, recordButton, graphButton;

    // Constructor.
    public ProfilePanel(ContentPanel lastContentPanel) {
        super(lastContentPanel);

        addViewProfile();
        return;
    }

    // Method.
    public void addViewProfile() {
        this.viewProfile = new ContentPage(this);
        this.viewProfile.setLayout(new BorderLayout());

        this.returnButton = new JButton("Back");
        this.returnButton.setActionCommand("Last Panel");
        this.returnButton.addActionListener(this.viewProfile);

        this.viewProfile.add(this.returnButton, BorderLayout.NORTH);

        this.viewProfileHeading = new JLabel("View Profile Page");
        this.viewProfileHeading.setForeground(HealthDiary.TEXT_COLOR);
        
        this.viewProfile.add(this.viewProfileHeading, BorderLayout.CENTER);

        this.addPage(this.viewProfile, "View Profile");
        return;
    }
}
