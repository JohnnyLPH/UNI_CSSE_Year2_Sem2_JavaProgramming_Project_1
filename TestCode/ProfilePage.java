package TestCode;
// TMF2954 Java Programming Group Project
// Group Name: LUCKY
// Group Members:
//     - LAU PIKK HEANG (75359)
//     - YUKI CHUNG PEI YING (77237)
//     - ANDREA ANG XIAO XUAN (73347)
// Health Diary App: Profile Page.
import java.awt.*;
import javax.swing.*;


public class ProfilePage extends ContentPage {
    private JLabel profileHeading;
    private JButton returnButton;

    // Constructor.
    public ProfilePage(ContentPanel contentPanel) {
        super(contentPanel);

        setLayout(new BorderLayout());
        setBackground(AppFrame.BG_COLOR);

        // Button for returning to previous page.
        returnButton = new JButton("<-");
        returnButton.setActionCommand(contentPanel.ALL_PAGES[0]);
        returnButton.addActionListener(this);
        returnButton.setFocusable(false);

        // Add return button.
        add(returnButton, BorderLayout.NORTH);

        // Heading of Profile Page.
        profileHeading = new JLabel("User Profile");
        // Add border to heading.
        profileHeading.setBorder(BorderFactory.createLineBorder(Color.CYAN, 3));
        
        // Adjust heading text.
        profileHeading.setFont(new Font("Monospaced", Font.BOLD, 20));
        profileHeading.setForeground(AppFrame.TEXT_COLOR);
        profileHeading.setHorizontalTextPosition(JLabel.CENTER);
        profileHeading.setVerticalTextPosition(JLabel.BOTTOM);
        profileHeading.setHorizontalAlignment(JLabel.CENTER);
        profileHeading.setVerticalAlignment(JLabel.CENTER);

        // Add heading to Profile Page.
        add(profileHeading, BorderLayout.CENTER);
        return;
    }
}
