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
        homeMenu = new ContentPage(this, "Home Menu", 0);

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
        JButton toProfileBtn = new JButton("User Profile");
        // Adjust button font and color.
        toProfileBtn.setFont(HealthDiary.BTN_FONT);
        toProfileBtn.setForeground(HealthDiary.BTN_FG_COLOR);
        toProfileBtn.setBackground(HealthDiary.BTN_BG_COLOR);
        // Adjust button border.
        toProfileBtn.setBorder(BorderFactory.createLineBorder(HealthDiary.BTN_FG_COLOR, 4, true));
        // Adjust button action.
        toProfileBtn.setActionCommand("Profile Panel");
        toProfileBtn.addActionListener(homeMenu);
        toProfileBtn.setFocusable(false);
        toProfileBtn.setMaximumSize(new Dimension(200, 50));
        toProfileBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Add Profile Page button.
        homeMenu.add(toProfileBtn);
        homeMenu.add(Box.createRigidArea(new Dimension(0, 25)));

        // ----------------------------------------------------------------------------------------------------
        // Button for redirecting to Record Page.
        JButton toRecordBtn = new JButton("All Health Records");
        // Adjust button font and color.
        toRecordBtn.setFont(HealthDiary.BTN_FONT);
        toRecordBtn.setForeground(HealthDiary.BTN_FG_COLOR);
        toRecordBtn.setBackground(HealthDiary.BTN_BG_COLOR);
        // Adjust button border.
        toRecordBtn.setBorder(BorderFactory.createLineBorder(HealthDiary.BTN_FG_COLOR, 4, true));
        // Adjust button action.
        toRecordBtn.setActionCommand("Record Panel");
        toRecordBtn.addActionListener(homeMenu);
        toRecordBtn.setFocusable(false);
        toRecordBtn.setMaximumSize(new Dimension(200, 50));
        toRecordBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Add Record Page button.
        homeMenu.add(toRecordBtn);
        homeMenu.add(Box.createRigidArea(new Dimension(0, 25)));

        // ----------------------------------------------------------------------------------------------------
        // Button for redirecting to Graph Page.
        JButton toGraphBtn = new JButton("View Graph Report");
        // Adjust button font and color.
        toGraphBtn.setFont(HealthDiary.BTN_FONT);
        toGraphBtn.setForeground(HealthDiary.BTN_FG_COLOR);
        toGraphBtn.setBackground(HealthDiary.BTN_BG_COLOR);
        // Adjust button border.
        toGraphBtn.setBorder(BorderFactory.createLineBorder(HealthDiary.BTN_FG_COLOR, 4, true));
        // Adjust button action.
        toGraphBtn.setActionCommand("Graph Panel");
        toGraphBtn.addActionListener(homeMenu);
        toGraphBtn.setFocusable(false);
        toGraphBtn.setMaximumSize(new Dimension(200, 50));
        toGraphBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Add Graph Page button.
        homeMenu.add(toGraphBtn);

        // ----------------------------------------------------------------------------------------------------
        // Add to Home Panel.
        addPage(homeMenu);
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
