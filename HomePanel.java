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

    // Constructor.
    public HomePanel() {
        addHomeMenu();
        addProfilePanel();
        addRecordPanel();
        addGraphPanel();
        return;
    }

    // Method.
    // Add Home Menu.
    private void addHomeMenu() {
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
        JButton profileBtn = new JButton("User Profile");
        // Adjust button font and color.
        profileBtn.setFont(HealthDiary.BTN_FONT);
        profileBtn.setForeground(HealthDiary.BTN_FG_COLOR);
        profileBtn.setBackground(HealthDiary.BTN_BG_COLOR);
        // Adjust button border.
        profileBtn.setBorder(BorderFactory.createLineBorder(HealthDiary.BTN_FG_COLOR, 4, true));
        // Adjust button action.
        profileBtn.setActionCommand("Profile Panel");
        profileBtn.addActionListener(homeMenu);
        profileBtn.setFocusable(false);
        profileBtn.setMaximumSize(new Dimension(200, 50));
        profileBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Add Profile Page button.
        homeMenu.add(profileBtn);
        homeMenu.add(Box.createRigidArea(new Dimension(0, 25)));

        // ----------------------------------------------------------------------------------------------------
        // Button for redirecting to Record Page.
        JButton recordBtn = new JButton("All Health Records");
        // Adjust button font and color.
        recordBtn.setFont(HealthDiary.BTN_FONT);
        recordBtn.setForeground(HealthDiary.BTN_FG_COLOR);
        recordBtn.setBackground(HealthDiary.BTN_BG_COLOR);
        // Adjust button border.
        recordBtn.setBorder(BorderFactory.createLineBorder(HealthDiary.BTN_FG_COLOR, 4, true));
        // Adjust button action.
        recordBtn.setActionCommand("Record Panel");
        recordBtn.addActionListener(homeMenu);
        recordBtn.setFocusable(false);
        recordBtn.setMaximumSize(new Dimension(200, 50));
        recordBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Add Record Page button.
        homeMenu.add(recordBtn);
        homeMenu.add(Box.createRigidArea(new Dimension(0, 25)));

        // ----------------------------------------------------------------------------------------------------
        // Button for redirecting to Graph Page.
        JButton graphBtn = new JButton("View Graph Report");
        // Adjust button font and color.
        graphBtn.setFont(HealthDiary.BTN_FONT);
        graphBtn.setForeground(HealthDiary.BTN_FG_COLOR);
        graphBtn.setBackground(HealthDiary.BTN_BG_COLOR);
        // Adjust button border.
        graphBtn.setBorder(BorderFactory.createLineBorder(HealthDiary.BTN_FG_COLOR, 4, true));
        // Adjust button action.
        graphBtn.setActionCommand("Graph Panel");
        graphBtn.addActionListener(homeMenu);
        graphBtn.setFocusable(false);
        graphBtn.setMaximumSize(new Dimension(200, 50));
        graphBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Add Graph Page button.
        homeMenu.add(graphBtn);

        // ----------------------------------------------------------------------------------------------------
        // Add to Home Panel.
        addPage(homeMenu, "Home Menu", 0);
        return;
    }

    // Add Profile Panel.
    private void addProfilePanel() {
        profilePanel = new ProfilePanel(this);

        // Add to Home Panel.
        addPage(profilePanel, "Profile Panel", 1);
        return;
    }

    // Add Record Panel.
    private void addRecordPanel() {
        recordPanel = new RecordPanel(this);

        // Add to Home Panel.
        addPage(recordPanel, "Record Panel", 2);
        return;
    }

    // Add Graph Panel.
    private void addGraphPanel() {
        graphPanel = new GraphPanel(this);

        // Add to Home Panel.
        addPage(graphPanel, "Graph Panel", 3);
        return;
    }
}
