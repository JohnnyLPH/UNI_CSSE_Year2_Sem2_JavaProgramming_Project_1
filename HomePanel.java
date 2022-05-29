// TMF2954 Java Programming Group Project
// Group Name: LUCKY
// Group Members:
//     - LAU PIKK HEANG (75359)
//     - YUKI CHUNG PEI YING (77237)
//     - ANDREA ANG XIAO XUAN (73347)
// Health Diary App: Home Panel.
// Contain Home Menu, Profile Panel, Record Panel, Graph Panel.
import java.awt.*;
import javax.swing.*;


public class HomePanel extends ContentPanel {
    // Content Page.
    private ContentPage homeMenu;
    // Content Panel.
    private ContentPanel profilePanel, recordPanel, graphPanel;
    // Button.
    private JButton profileButton, recordButton, graphButton;

    // Constructor.
    public HomePanel() {
        addHomeMenu();
        addProfilePanel();
        addRecordPanel();
        addGraphPanel();
        return;
    }

    // Method.
    public void addHomeMenu() {
        homeMenu = new ContentPage(this);

        homeMenu.setLayout(new BoxLayout(homeMenu, BoxLayout.Y_AXIS));

        // ----------------------------------------------------------------------------------------------------
        // Heading of Home Menu.
        JLabel homeHeading = new JLabel(HealthDiary.UNI_CROSS + "Health Diary" + HealthDiary.UNI_CROSS + " by LUCKY");
        // Add border to heading.
        homeHeading.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.CYAN, 3, true),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        // Add icon to heading.
        homeHeading.setIcon(new ImageIcon(
            HealthDiary.APP_ICON.getImage().getScaledInstance(128, 128, java.awt.Image.SCALE_SMOOTH)
        ));
        // Adjust heading font.
        homeHeading.setFont(HealthDiary.MAIN_FONT);
        // Adjust heading color.
        homeHeading.setForeground(HealthDiary.TEXT_COLOR);
        homeHeading.setBackground(HealthDiary.THEME_BG_COLOR);
        homeHeading.setOpaque(true);
        // Adjust heading text alignment.
        homeHeading.setHorizontalTextPosition(JLabel.CENTER);
        homeHeading.setVerticalTextPosition(JLabel.BOTTOM);
        homeHeading.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Add heading to Home Page.
        homeMenu.add(Box.createRigidArea(new Dimension(0, 50)));
        homeMenu.add(homeHeading);
        homeMenu.add(Box.createRigidArea(new Dimension(0, 40)));

        // ----------------------------------------------------------------------------------------------------
        // Button for redirecting to Profile Page.
        profileButton = new JButton("User Profile");
        // Adjust button font and color.
        profileButton.setFont(HealthDiary.BTN_FONT);
        profileButton.setForeground(HealthDiary.BTN_FG_COLOR);
        profileButton.setBackground(HealthDiary.BTN_BG_COLOR);
        // Adjust button border.
        profileButton.setBorder(BorderFactory.createLineBorder(HealthDiary.BTN_FG_COLOR, 4, true));
        // Adjust button action.
        profileButton.setActionCommand("Profile Panel");
        profileButton.addActionListener(homeMenu);
        profileButton.setFocusable(false);
        profileButton.setMaximumSize(new Dimension(200, 50));
        profileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Add Profile Page button.
        homeMenu.add(profileButton);
        homeMenu.add(Box.createRigidArea(new Dimension(0, 25)));

        // ----------------------------------------------------------------------------------------------------
        // Button for redirecting to Record Page.
        recordButton = new JButton("All Health Records");
        // Adjust button font and color.
        recordButton.setFont(HealthDiary.BTN_FONT);
        recordButton.setForeground(HealthDiary.BTN_FG_COLOR);
        recordButton.setBackground(HealthDiary.BTN_BG_COLOR);
        // Adjust button border.
        recordButton.setBorder(BorderFactory.createLineBorder(HealthDiary.BTN_FG_COLOR, 4, true));
        // Adjust button action.
        recordButton.setActionCommand("Record Panel");
        recordButton.addActionListener(homeMenu);
        recordButton.setFocusable(false);
        recordButton.setMaximumSize(new Dimension(200, 50));
        recordButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Add Record Page button.
        homeMenu.add(recordButton);
        homeMenu.add(Box.createRigidArea(new Dimension(0, 25)));

        // ----------------------------------------------------------------------------------------------------
        // Button for redirecting to Graph Page.
        graphButton = new JButton("View Graph Report");
        // Adjust button font and color.
        graphButton.setFont(HealthDiary.BTN_FONT);
        graphButton.setForeground(HealthDiary.BTN_FG_COLOR);
        graphButton.setBackground(HealthDiary.BTN_BG_COLOR);
        // Adjust button border.
        graphButton.setBorder(BorderFactory.createLineBorder(HealthDiary.BTN_FG_COLOR, 4, true));
        // Adjust button action.
        graphButton.setActionCommand("Graph Panel");
        graphButton.addActionListener(homeMenu);
        graphButton.setFocusable(false);
        graphButton.setMaximumSize(new Dimension(200, 50));
        graphButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Add Graph Page button.
        homeMenu.add(graphButton);

        // ----------------------------------------------------------------------------------------------------
        // Add to Home Panel.
        addPage(homeMenu, "Home Menu", 0);
        return;
    }

    public void addProfilePanel() {
        profilePanel = new ProfilePanel(this);

        // Add to Home Panel.
        addPage(profilePanel, "Profile Panel", 1);
        return;
    }

    public void addRecordPanel() {
        recordPanel = new RecordPanel(this);

        // Add to Home Panel.
        addPage(recordPanel, "Record Panel", 2);
        return;
    }

    public void addGraphPanel() {
        graphPanel = new GraphPanel(this);

        // Add to Home Panel.
        addPage(graphPanel, "Graph Panel", 3);
        return;
    }
}
