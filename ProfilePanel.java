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
import javax.swing.plaf.InsetsUIResource;


public class ProfilePanel extends ContentPanel {
    // Content Page.
    private ContentPage viewProfile, editProfile;
    // Content (Layout: GridBagLayout).
    private JPanel viewProfileContent;
    // Grid Bag Constraints (No Reuse, new object for each component).
    private GridBagConstraints gridBagC;
    // Label.
    private JLabel nameValue, genderValue, bloodValue, historyValue;
    // Button.
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

        // ----------------------------------------------------------------------------------------------------
        // Button for redirecting to Last Panel.
        returnPanelBtn = new JButton(HealthDiary.UNI_RETURN_ARROW + " Back");
        // Adjust button font and color.
        returnPanelBtn.setFont(HealthDiary.SMALL_BTN_FONT);
        returnPanelBtn.setForeground(HealthDiary.BTN_FG_COLOR);
        returnPanelBtn.setBackground(HealthDiary.BTN_BG_COLOR);
        // Adjust button action.
        returnPanelBtn.setActionCommand("Last Panel");
        returnPanelBtn.addActionListener(viewProfile);
        returnPanelBtn.setFocusable(false);
        // Add to Content Page.
        viewProfile.add(returnPanelBtn, BorderLayout.NORTH);

        // For main content of View Profile (i.e., all the profile fields and values).
        viewProfileContent = new JPanel(new GridBagLayout());
        viewProfileContent.setBackground(HealthDiary.THEME_BG_COLOR);

        // ----------------------------------------------------------------------------------------------------
        // Heading for View Profile content.
        JLabel viewProfileHeading = new JLabel("User Profile");
        // Adjust heading font and color.
        viewProfileHeading.setFont(HealthDiary.MAIN_FONT);
        viewProfileHeading.setForeground(HealthDiary.TEXT_COLOR);
        viewProfileHeading.setHorizontalAlignment(JLabel.CENTER);
        // Add icon to heading.
        viewProfileHeading.setIcon(new ImageIcon(
            HealthDiary.PROFILE_ICON.getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH)
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(10, 5, 15, 5);
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 0;
        gridBagC.gridx = 0;
        gridBagC.fill = GridBagConstraints.HORIZONTAL;
        viewProfileContent.add(viewProfileHeading, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Label for name.
        JLabel nameLb = new JLabel("Name:");
        // Adjust label font and color.
        nameLb.setFont(HealthDiary.LB_FONT);
        nameLb.setForeground(HealthDiary.TEXT_COLOR);
        nameLb.setBackground(HealthDiary.BLACK_BG_COLOR);
        nameLb.setOpaque(true);
        nameLb.setHorizontalAlignment(JLabel.RIGHT);
        nameLb.setPreferredSize(new Dimension(100, 30));
        nameLb.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.TEXT_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 5, 5, 2);
        gridBagC.gridy = 1;
        gridBagC.gridx = 0;
        viewProfileContent.add(nameLb, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Label for name value.
        nameValue = new JLabel("Michael Jackson");
        // Adjust label font and color.
        nameValue.setFont(HealthDiary.LB_FONT);
        nameValue.setForeground(HealthDiary.VALUE_FG_COLOR);
        nameValue.setBackground(HealthDiary.VALUE_BG_COLOR);
        nameValue.setOpaque(true);
        nameValue.setFocusable(false);
        nameValue.setHorizontalAlignment(JLabel.LEFT);
        nameValue.setPreferredSize(new Dimension(250, 30));
        nameValue.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 2, 5, 5);
        gridBagC.gridy = 1;
        gridBagC.gridx = 1;
        viewProfileContent.add(nameValue, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Label for gender.
        JLabel genderLb = new JLabel("Gender:");
        // Adjust label font and color.
        genderLb.setFont(HealthDiary.LB_FONT);
        genderLb.setForeground(HealthDiary.TEXT_COLOR);
        genderLb.setBackground(HealthDiary.BLACK_BG_COLOR);
        genderLb.setOpaque(true);
        genderLb.setHorizontalAlignment(JLabel.RIGHT);
        genderLb.setPreferredSize(new Dimension(100, 30));
        genderLb.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.TEXT_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 5, 5, 2);
        gridBagC.gridy = 2;
        gridBagC.gridx = 0;
        viewProfileContent.add(genderLb, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Label for gender value.
        genderValue = new JLabel("Male");
        // Adjust label font and color.
        genderValue.setFont(HealthDiary.LB_FONT);
        genderValue.setForeground(HealthDiary.VALUE_FG_COLOR);
        genderValue.setBackground(HealthDiary.VALUE_BG_COLOR);
        genderValue.setOpaque(true);
        genderValue.setFocusable(false);
        genderValue.setHorizontalAlignment(JLabel.LEFT);
        genderValue.setPreferredSize(new Dimension(250, 30));
        genderValue.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 2, 5, 5);
        gridBagC.gridy = 2;
        gridBagC.gridx = 1;
        viewProfileContent.add(genderValue, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Label for blood type.
        JLabel bloodLb = new JLabel("Blood type:");
        // Adjust label font and color.
        bloodLb.setFont(HealthDiary.LB_FONT);
        bloodLb.setForeground(HealthDiary.TEXT_COLOR);
        bloodLb.setBackground(HealthDiary.BLACK_BG_COLOR);
        bloodLb.setOpaque(true);
        bloodLb.setHorizontalAlignment(JLabel.RIGHT);
        bloodLb.setPreferredSize(new Dimension(100, 30));
        bloodLb.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.TEXT_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 5, 5, 2);
        gridBagC.gridy = 3;
        gridBagC.gridx = 0;
        viewProfileContent.add(bloodLb, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Label for blood type value.
        bloodValue = new JLabel("AB+");
        // Adjust label font and color.
        bloodValue.setFont(HealthDiary.LB_FONT);
        bloodValue.setForeground(HealthDiary.VALUE_FG_COLOR);
        bloodValue.setBackground(HealthDiary.VALUE_BG_COLOR);
        bloodValue.setOpaque(true);
        bloodValue.setFocusable(false);
        bloodValue.setHorizontalAlignment(JLabel.LEFT);
        bloodValue.setPreferredSize(new Dimension(250, 30));
        bloodValue.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 2, 5, 5);
        gridBagC.gridy = 3;
        gridBagC.gridx = 1;;
        viewProfileContent.add(bloodValue, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Label for health history.
        JLabel historyLb = new JLabel("Health History:");
        // Adjust label font and color.
        historyLb.setFont(HealthDiary.LB_FONT);
        historyLb.setForeground(HealthDiary.TEXT_COLOR);
        historyLb.setBackground(HealthDiary.BLACK_BG_COLOR);
        historyLb.setOpaque(true);
        historyLb.setHorizontalAlignment(JLabel.CENTER);
        // historyLb.setPreferredSize(new Dimension(354, 30));
        historyLb.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.TEXT_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 5, 2, 5);
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 4;
        gridBagC.gridx = 0;
        gridBagC.fill = GridBagConstraints.HORIZONTAL;
        viewProfileContent.add(historyLb, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Label for health history value.
        historyValue = new JLabel("Diabetes");
        // Adjust label font and color.
        historyValue.setFont(HealthDiary.LB_FONT);
        historyValue.setForeground(HealthDiary.VALUE_FG_COLOR);
        historyValue.setBackground(HealthDiary.VALUE_BG_COLOR);
        historyValue.setOpaque(true);
        historyValue.setFocusable(false);
        historyValue.setHorizontalAlignment(JLabel.LEFT);
        historyValue.setPreferredSize(new Dimension(354, 250));
        historyValue.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(2, 5, 10, 5);
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 5;
        gridBagC.gridx = 0;
        gridBagC.fill = GridBagConstraints.HORIZONTAL;
        viewProfileContent.add(historyValue, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Add content to Content Page.
        viewProfile.add(viewProfileContent, BorderLayout.CENTER);
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
