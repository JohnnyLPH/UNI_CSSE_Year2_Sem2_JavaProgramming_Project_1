// TMF2954 Java Programming Group Project
// Group Name: LUCKY
// Group Members:
//     - LAU PIKK HEANG (75359)
//     - YUKI CHUNG PEI YING (77237)
//     - ANDREA ANG XIAO XUAN (73347)
// Health Diary App: Home Page.
import java.awt.*;
import javax.swing.*;


public class HomePage extends ContentPage {
    // private ContentPanel contentPanel;
    private JLabel homeHeading;
    private JButton profileButton, recordButton, graphReportButton;

    // Constructor.
    public HomePage(ContentPanel contentPanel) {
        super(contentPanel);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(HealthDiary.BG_COLOR);

        // Heading of Home Page.
        homeHeading = new JLabel("Health Diary by LUCKY");
        // Add border to heading.
        homeHeading.setBorder(BorderFactory.createLineBorder(Color.CYAN, 3));
        // Add icon to heading.
        homeHeading.setIcon(new ImageIcon(
            HealthDiary.APP_ICON.getImage().getScaledInstance(128, 128, java.awt.Image.SCALE_SMOOTH)
        ));
        // Adjust heading text.
        homeHeading.setFont(new Font("Monospaced", Font.BOLD, 20));
        homeHeading.setForeground(HealthDiary.TEXT_COLOR);
        homeHeading.setHorizontalTextPosition(JLabel.CENTER);
        homeHeading.setVerticalTextPosition(JLabel.BOTTOM);
        homeHeading.setHorizontalAlignment(JLabel.CENTER);
        homeHeading.setVerticalAlignment(JLabel.CENTER);

        add(Box.createRigidArea(new Dimension(0, 20)));
        // Add heading to Home Page.
        add(homeHeading);
        add(Box.createRigidArea(new Dimension(0, 20)));

        // Button for redirecting to Profile Page.
        profileButton = new JButton("User Profile");
        profileButton.setActionCommand(contentPanel.ALL_PAGES[1]);
        profileButton.addActionListener(getNewRedirectListener());

        // Add Profile Page button.
        add(profileButton);
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Button for redirecting to Record Page.
        recordButton = new JButton("All Health Records");
        recordButton.setActionCommand(contentPanel.ALL_PAGES[2]);
        recordButton.addActionListener(getNewRedirectListener());
        
        // Add Record Page button.
        add(recordButton);
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Button for redirecting to Graph Page.
        graphReportButton = new JButton("View Graph Report");
        graphReportButton.setActionCommand(contentPanel.ALL_PAGES[3]);
        graphReportButton.addActionListener(getNewRedirectListener());
        
        // Add Graph Page button.
        add(graphReportButton);
        return;
    }
}
