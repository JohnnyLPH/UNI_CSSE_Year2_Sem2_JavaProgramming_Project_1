package TestCode;
// TMF2954 Java Programming Group Project
// Group Name: LUCKY
// Group Members:
//     - LAU PIKK HEANG (75359)
//     - YUKI CHUNG PEI YING (77237)
//     - ANDREA ANG XIAO XUAN (73347)
// Health Diary App: Home Page.
// Contain main menu to navigate to other pages.
import java.awt.*;
import javax.swing.*;


public class HomePage extends ContentPage {
    private JLabel homeHeading;
    private JButton profileButton, recordButton, graphButton;

    // Constructor.
    public HomePage(ContentPanel contentPanel) {
        super(contentPanel);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(AppFrame.BG_COLOR);

        // Heading of Home Page.
        homeHeading = new JLabel("Health Diary by LUCKY");
        // Add border to heading.
        homeHeading.setBorder(BorderFactory.createLineBorder(Color.CYAN, 3));
        // Add icon to heading.
        homeHeading.setIcon(new ImageIcon(
            AppFrame.APP_ICON.getImage().getScaledInstance(128, 128, java.awt.Image.SCALE_SMOOTH)
        ));
        // Adjust heading text.
        homeHeading.setFont(new Font("Monospaced", Font.BOLD, 20));
        homeHeading.setForeground(AppFrame.TEXT_COLOR);
        homeHeading.setHorizontalTextPosition(JLabel.CENTER);
        homeHeading.setVerticalTextPosition(JLabel.BOTTOM);
        homeHeading.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createRigidArea(new Dimension(0, 20)));
        // Add heading to Home Page.
        add(homeHeading);
        add(Box.createRigidArea(new Dimension(0, 25)));

        // Button for redirecting to Profile Page.
        profileButton = new JButton("User Profile");
        profileButton.setActionCommand(contentPanel.ALL_PAGES[1]);
        profileButton.addActionListener(this);
        profileButton.setFocusable(false);
        profileButton.setMaximumSize(new Dimension(200, 50));
        profileButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add Profile Page button.
        add(profileButton);
        add(Box.createRigidArea(new Dimension(0, 15)));

        // Button for redirecting to Record Page.
        recordButton = new JButton("All Health Records");
        recordButton.setActionCommand(contentPanel.ALL_PAGES[2]);
        recordButton.addActionListener(this);
        recordButton.setFocusable(false);
        recordButton.setMaximumSize(new Dimension(200, 50));
        recordButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add Record Page button.
        add(recordButton);
        add(Box.createRigidArea(new Dimension(0, 15)));

        // Button for redirecting to Graph Page.
        graphButton = new JButton("View Graph Report");
        graphButton.setActionCommand(contentPanel.ALL_PAGES[3]);
        graphButton.addActionListener(this);
        graphButton.setFocusable(false);
        graphButton.setMaximumSize(new Dimension(200, 50));
        graphButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add Graph Page button.
        add(graphButton);
        return;
    }
}
