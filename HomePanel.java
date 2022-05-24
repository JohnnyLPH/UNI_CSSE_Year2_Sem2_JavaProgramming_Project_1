// TMF2954 Java Programming Group Project
// Group Name: LUCKY
// Group Members:
//     - LAU PIKK HEANG (75359)
//     - YUKI CHUNG PEI YING (77237)
//     - ANDREA ANG XIAO XUAN (73347)
// Health Diary App: Home Panel.
// Contain Main Menu, Profile Panel, Record Panel, Graph Panel.
import java.awt.*;
import javax.swing.*;


public class HomePanel extends ContentPanel {
    private ContentPage homeMenu;
    private JLabel homeHeading;
    private JButton profileButton, recordButton, graphButton;
    private ProfilePanel profilePanel;

    // Constructor.
    public HomePanel() {
        addHomeMenu();
        addProfilePanel();

        return;
    }

    // Method.
    public void addHomeMenu() {
        this.homeMenu = new ContentPage(this);

        this.homeMenu.setLayout(new BoxLayout(this.homeMenu, BoxLayout.Y_AXIS));

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
        homeHeading.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.homeMenu.add(Box.createRigidArea(new Dimension(0, 20)));
        // Add heading to Home Page.
        this.homeMenu.add(homeHeading);
        this.homeMenu.add(Box.createRigidArea(new Dimension(0, 25)));

        // Button for redirecting to Profile Page.
        profileButton = new JButton("User Profile");
        profileButton.setActionCommand("Profile Panel");
        profileButton.addActionListener(this.homeMenu);
        profileButton.setFocusable(false);
        profileButton.setMaximumSize(new Dimension(200, 50));
        profileButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add Profile Page button.
        this.homeMenu.add(profileButton);
        this.homeMenu.add(Box.createRigidArea(new Dimension(0, 15)));

        // Button for redirecting to Record Page.
        recordButton = new JButton("All Health Records");
        recordButton.setActionCommand("Record Panel");
        recordButton.addActionListener(this.homeMenu);
        recordButton.setFocusable(false);
        recordButton.setMaximumSize(new Dimension(200, 50));
        recordButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add Record Page button.
        this.homeMenu.add(recordButton);
        this.homeMenu.add(Box.createRigidArea(new Dimension(0, 15)));

        // Button for redirecting to Graph Page.
        graphButton = new JButton("View Graph Report");
        graphButton.setActionCommand("Graph Panel");
        graphButton.addActionListener(this.homeMenu);
        graphButton.setFocusable(false);
        graphButton.setMaximumSize(new Dimension(200, 50));
        graphButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add Graph Page button.
        this.homeMenu.add(graphButton);

        // Add to Home Panel.
        this.addPage(this.homeMenu, "Home Menu");
        return;
    }

    public void addProfilePanel() {
        profilePanel = new ProfilePanel(this);

        // Add to Home Panel.
        this.addPage(this.profilePanel, "Profile Panel");
        return;
    }
}
