// TMF2954 Java Programming Group Project
// Group Name: LUCKY
// Group Members:
//     - LAU PIKK HEANG (75359)
//     - YUKI CHUNG PEI YING (77237)
//     - ANDREA ANG XIAO XUAN (73347)
// Health Diary App: Profile Panel.
// Contain View Profile Page, Edit Profile Page.
import java.util.Scanner;
import java.io.FileWriter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.plaf.InsetsUIResource;


public class ProfilePanel extends ContentPanel {
    // Content Page.
    private ContentPage viewProfile, editProfile;
    // Text Field for data value and input.
    private JTextField nameValue, nameInput;
    // Label for data value.
    private JLabel genderValue, bloodValue;
    // Text Area for data value and input.
    private JTextArea historyValue, historyInput;
    // Radio Button for input.
    private JRadioButton maleRadioBtn, femaleRadioBtn;
    // Combo Box for input.
    private JComboBox<String> bloodComboBox;
    // User Profile.
    private UserProfile userProfile;

    // Constructor.
    public ProfilePanel(ContentPanel lastContentPanel) {
        super(lastContentPanel);

        readProfileFile();
        addViewProfile();
        addEditProfile();
        refreshProfilePage(2);
        return;
    }

    // Method.
    // Read User Profile from file.
    private void readProfileFile() {
        try {
            userProfile = new UserProfile();

            // File already exists and not created.
            if (!HealthDiary.PROFILE_FILE.createNewFile()) {
                // System.out.println("File exists.");
                Scanner sc = new Scanner(HealthDiary.PROFILE_FILE);
                
                // Read Name.
                userProfile.setName(sc.nextLine().strip());
                // Read Gender.
                try {
                    userProfile.setGender(Integer.parseInt(sc.nextLine()));
                }
                catch (Exception errorMsg) {
                    System.out.println(errorMsg);
                }
                // Read Blood Type.
                try {
                    userProfile.setBloodType(Integer.parseInt(sc.nextLine()));
                }
                catch (Exception errorMsg) {
                    System.out.println(errorMsg);
                }
                // Read Health History.
                String hHistory = "";
                while (sc.hasNextLine()) {
                    hHistory += sc.nextLine() + "\n";
                }
                userProfile.setHealthHistory(hHistory.strip());

                sc.close();
            }
            // File not exist, write default profile values into it.
            else {
                // System.out.println("File doesn't exist.");
                FileWriter newWriter = new FileWriter(HealthDiary.PROFILE_FILE);
                newWriter.write(userProfile.getName() + "\n");
                newWriter.write(userProfile.getGenderIndex() + "\n");
                newWriter.write(userProfile.getBloodTypeIndex() + "\n");
                newWriter.write(userProfile.getHealthHistory());

                newWriter.close();
                // System.out.println("Write default values into file.");
            }
        }
        catch (Exception e) {
            System.out.println("Error with file in readProfileFile().");
        }
        return;
    }

    // Write User Profile to file.
    private void writeProfileFile() {
        try {
            // Ensure the file is created if not exist.
            HealthDiary.PROFILE_FILE.createNewFile();

            FileWriter newWriter = new FileWriter(HealthDiary.PROFILE_FILE);
            newWriter.write(userProfile.getName() + "\n");
            newWriter.write(userProfile.getGenderIndex() + "\n");
            newWriter.write(userProfile.getBloodTypeIndex() + "\n");
            newWriter.write(userProfile.getHealthHistory());

            newWriter.close();
            // System.out.println("Write profile values into file.");
        }
        catch (Exception e) {
            System.out.println("Error with file in writeProfileFile().");
        }
        return;
    }

    // Add View Profile Page.
    private void addViewProfile() {
        viewProfile = new ContentPage(this);
        viewProfile.setLayout(new BorderLayout());

        // ----------------------------------------------------------------------------------------------------
        // Button for redirecting to Last Panel.
        JButton returnPanelBtn = new JButton(HealthDiary.UNI_RETURN_ARROW + " Back");
        // Adjust font and color.
        returnPanelBtn.setFont(HealthDiary.SMALL_BTN_FONT);
        returnPanelBtn.setForeground(HealthDiary.BTN_FG_COLOR);
        returnPanelBtn.setBackground(HealthDiary.BTN_BG_COLOR);
        // Adjust action.
        returnPanelBtn.setActionCommand("Last Panel");
        returnPanelBtn.addActionListener(viewProfile);
        returnPanelBtn.setFocusable(false);
        // Add to Content Page.
        viewProfile.add(returnPanelBtn, BorderLayout.NORTH);

        // ----------------------------------------------------------------------------------------------------
        // For main content of View Profile (i.e., all the profile fields and values).
        JPanel viewProfileContent = new JPanel(new GridBagLayout());
        // No reuse, new object for each component.
        GridBagConstraints gridBagC;
        viewProfileContent.setBackground(HealthDiary.THEME_BG_COLOR);

        // ----------------------------------------------------------------------------------------------------
        // Heading for View Profile content.
        JLabel viewProfileHeading = new JLabel("User Profile");
        // Adjust font and color.
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
        // Adjust font and color.
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
        // Name value.
        nameValue = new JTextField();
        // Adjust font and color.
        nameValue.setFont(HealthDiary.LB_FONT);
        nameValue.setForeground(HealthDiary.VALUE_FG_COLOR);
        nameValue.setBackground(HealthDiary.VALUE_BG_COLOR);
        nameValue.setOpaque(true);
        // Not allowing editing, there is an invisible vertical scrollbar on the right to scroll through long text.
        nameValue.setEditable(false);
        nameValue.setHorizontalAlignment(JLabel.LEFT);
        nameValue.setPreferredSize(new Dimension(250, 30));
        nameValue.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 2, 5, 5);
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 1;
        gridBagC.gridx = 1;
        viewProfileContent.add(nameValue, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Label for gender.
        JLabel genderLb = new JLabel("Gender:");
        // Adjust font and color.
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
        // Gender value.
        genderValue = new JLabel();
        // Adjust font and color.
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
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 2;
        gridBagC.gridx = 1;
        viewProfileContent.add(genderValue, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Label for blood type.
        JLabel bloodLb = new JLabel("Blood type:");
        // Adjust font and color.
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
        // Blood type value.
        bloodValue = new JLabel();
        // Adjust font and color.
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
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 3;
        gridBagC.gridx = 1;;
        viewProfileContent.add(bloodValue, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Label for health history.
        JLabel historyLb = new JLabel("Health History:");
        // Adjust font and color.
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
        // Health history value.
        historyValue = new JTextArea();
        // Adjust font and color.
        historyValue.setFont(HealthDiary.LB_FONT);
        historyValue.setForeground(HealthDiary.VALUE_FG_COLOR);
        historyValue.setBackground(HealthDiary.VALUE_BG_COLOR);
        historyValue.setOpaque(true);
        historyValue.setFocusable(false);
        historyValue.setLineWrap(true);
        historyValue.setWrapStyleWord(true);
        // historyValue.setHorizontalAlignment(JLabel.LEFT);
        historyValue.setPreferredSize(new Dimension(354, 256));
        historyValue.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(2, 5, 5, 5);
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 5;
        gridBagC.gridx = 0;
        gridBagC.fill = GridBagConstraints.HORIZONTAL;
        viewProfileContent.add(historyValue, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Button for redirecting to Edit Profile Page.
        JButton toEditPageBtn = new JButton("Edit Profile");
        // Adjust font and color.
        toEditPageBtn.setFont(HealthDiary.BTN_FONT);
        toEditPageBtn.setForeground(HealthDiary.BTN_FG_COLOR);
        toEditPageBtn.setBackground(HealthDiary.POSITIVE_COLOR);
        // Adjust action.
        toEditPageBtn.setActionCommand("Edit Profile");
        toEditPageBtn.addActionListener(viewProfile);
        toEditPageBtn.setFocusable(false);
        toEditPageBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.BTN_FG_COLOR, 3, true),
            BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        // toEditPageBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        toEditPageBtn.setMinimumSize(new Dimension(200, 50));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 5, 10, 5);
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 6;
        gridBagC.gridx = 0;
        // gridBagC.fill = GridBagConstraints.HORIZONTAL;
        viewProfileContent.add(toEditPageBtn, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Add content to Content Page.
        viewProfile.add(viewProfileContent, BorderLayout.CENTER);
        addPage(viewProfile, "View Profile", 0);
        return;
    }

    // Add Edit Profile Page.
    private void addEditProfile() {
        editProfile = new ContentPage(this);
        editProfile.setLayout(new BorderLayout());

        // ----------------------------------------------------------------------------------------------------
        // Modified ActionListener for edit cancellation.
        ActionListener cancelEdit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editProfile.actionPerformed(e);
                refreshProfilePage(1);
                return;
            }
        };

        // ----------------------------------------------------------------------------------------------------
        // Button for redirecting to View Profile Page.
        JButton toViewPageBtn = new JButton(HealthDiary.UNI_RETURN_ARROW + " Back");
        // Adjust font and color.
        toViewPageBtn.setFont(HealthDiary.SMALL_BTN_FONT);
        toViewPageBtn.setForeground(HealthDiary.BTN_FG_COLOR);
        toViewPageBtn.setBackground(HealthDiary.BTN_BG_COLOR);
        // Adjust action.
        toViewPageBtn.setActionCommand("View Profile");
        toViewPageBtn.addActionListener(cancelEdit);
        toViewPageBtn.setFocusable(false);
        // Add to Content Page.
        editProfile.add(toViewPageBtn, BorderLayout.NORTH);

        // ----------------------------------------------------------------------------------------------------
        // For main content of Edit Profile (i.e., all input fields and options).
        JPanel editProfileContent = new JPanel(new GridBagLayout());
        // No reuse, new object for each component.
        GridBagConstraints gridBagC;
        editProfileContent.setBackground(HealthDiary.THEME_BG_COLOR);

        // ----------------------------------------------------------------------------------------------------
        // Heading for Edit Profile content.
        JLabel editProfileHeading = new JLabel("Edit Profile");
        // Adjust font and color.
        editProfileHeading.setFont(HealthDiary.MAIN_FONT);
        editProfileHeading.setForeground(HealthDiary.TEXT_COLOR);
        editProfileHeading.setHorizontalAlignment(JLabel.CENTER);
        // Add icon to heading.
        editProfileHeading.setIcon(new ImageIcon(
            HealthDiary.EDIT_PROFILE_ICON.getImage().getScaledInstance(
                64, 64, java.awt.Image.SCALE_SMOOTH
            )
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(10, 5, 5, 5);
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 0;
        gridBagC.gridx = 0;
        gridBagC.fill = GridBagConstraints.HORIZONTAL;
        editProfileContent.add(editProfileHeading, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Label for name.
        JLabel nameLb = new JLabel("Name:");
        // Adjust font and color.
        nameLb.setFont(HealthDiary.BTN_FONT);
        nameLb.setForeground(HealthDiary.TEXT_COLOR);
        // nameLb.setBackground(HealthDiary.BLACK_BG_COLOR);
        // nameLb.setOpaque(true);
        nameLb.setHorizontalAlignment(JLabel.LEFT);
        nameLb.setPreferredSize(new Dimension(100, 30));
        nameLb.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 5, 0, 5);
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 1;
        gridBagC.gridx = 0;
        gridBagC.fill = GridBagConstraints.HORIZONTAL;
        editProfileContent.add(nameLb, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Name input.
        nameInput = new JTextField();
        // Adjust font and color.
        nameInput.setFont(HealthDiary.LB_FONT);
        nameInput.setForeground(HealthDiary.VALUE_FG_COLOR);
        // nameInput.setBackground(HealthDiary.VALUE_BG_COLOR);
        // nameInput.setOpaque(true);
        // nameInput.setFocusable(false);
        nameInput.setHorizontalAlignment(JLabel.LEFT);
        nameInput.setPreferredSize(new Dimension(250, 30));
        nameInput.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(0, 5, 5, 5);
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 2;
        gridBagC.gridx = 0;
        gridBagC.fill = GridBagConstraints.HORIZONTAL;
        editProfileContent.add(nameInput, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Label for gender.
        JLabel genderLb = new JLabel("Gender:");
        // Adjust font and color.
        genderLb.setFont(HealthDiary.BTN_FONT);
        genderLb.setForeground(HealthDiary.TEXT_COLOR);
        // genderLb.setBackground(HealthDiary.BLACK_BG_COLOR);
        // genderLb.setOpaque(true);
        genderLb.setHorizontalAlignment(JLabel.LEFT);
        genderLb.setPreferredSize(new Dimension(100, 30));
        genderLb.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 5, 5, 5);
        gridBagC.gridy = 3;
        gridBagC.gridx = 0;
        editProfileContent.add(genderLb, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Gender input (Radio Button).
        maleRadioBtn = new JRadioButton("Male " + HealthDiary.UNI_MALE);
        femaleRadioBtn = new JRadioButton("Female " + HealthDiary.UNI_FEMALE);
        // Adjust font and color.
        maleRadioBtn.setFont(HealthDiary.BTN_FONT);
        femaleRadioBtn.setFont(HealthDiary.BTN_FONT);
        maleRadioBtn.setForeground(HealthDiary.BTN_FG_COLOR);
        femaleRadioBtn.setForeground(HealthDiary.BTN_FG_COLOR);
        maleRadioBtn.setBackground(HealthDiary.THEME_BG_COLOR);
        femaleRadioBtn.setBackground(HealthDiary.THEME_BG_COLOR);
        // Add border.
        maleRadioBtn.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        femaleRadioBtn.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        maleRadioBtn.setMinimumSize(new Dimension(100, 30));
        femaleRadioBtn.setMinimumSize(new Dimension(100, 30));
        maleRadioBtn.setFocusable(false);
        femaleRadioBtn.setFocusable(false);
        // Add Radio Button to the same button group.
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioBtn);
        genderGroup.add(femaleRadioBtn);
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 5, 5, 5);
        gridBagC.gridy = 3;
        gridBagC.gridx = 1;
        // gridBagC.fill = GridBagConstraints.HORIZONTAL;
        editProfileContent.add(maleRadioBtn, gridBagC);
        gridBagC.gridx = 2;
        editProfileContent.add(femaleRadioBtn, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Label for blood type.
        JLabel bloodLb = new JLabel("Blood Type:");
        // Adjust font and color.
        bloodLb.setFont(HealthDiary.BTN_FONT);
        bloodLb.setForeground(HealthDiary.TEXT_COLOR);
        // bloodLb.setBackground(HealthDiary.BLACK_BG_COLOR);
        // bloodLb.setOpaque(true);
        bloodLb.setHorizontalAlignment(JLabel.LEFT);
        bloodLb.setPreferredSize(new Dimension(200, 30));
        bloodLb.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 5, 5, 5);
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 4;
        gridBagC.gridx = 0;
        gridBagC.fill = GridBagConstraints.HORIZONTAL;
        editProfileContent.add(bloodLb, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Blood type input (Combo Box).
        bloodComboBox = new JComboBox<String>(UserProfile.ALL_BLOOD_TYPE);
        // Adjust font and color.
        bloodComboBox.setFont(HealthDiary.BTN_FONT);
        bloodComboBox.setForeground(HealthDiary.VALUE_FG_COLOR);
        // bloodComboBox.setBackground(HealthDiary.VALUE_BG_COLOR);
        // Add border.
        bloodComboBox.setBorder(BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2));
        bloodComboBox.setMinimumSize(new Dimension(100, 30));
        bloodComboBox.setFocusable(false);
        bloodComboBox.setEditable(false);
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 5, 5, 5);
        gridBagC.gridy = 4;
        gridBagC.gridx = 1;
        gridBagC.fill = GridBagConstraints.HORIZONTAL;
        editProfileContent.add(bloodComboBox, gridBagC);


        // ----------------------------------------------------------------------------------------------------
        // Label for health history.
        JLabel historyLb = new JLabel("Health History:");
        // Adjust font and color.
        historyLb.setFont(HealthDiary.BTN_FONT);
        historyLb.setForeground(HealthDiary.TEXT_COLOR);
        // historyLb.setBackground(HealthDiary.BLACK_BG_COLOR);
        // historyLb.setOpaque(true);
        historyLb.setHorizontalAlignment(JLabel.LEFT);
        historyLb.setPreferredSize(new Dimension(100, 30));
        historyLb.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 5, 0, 5);
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 5;
        gridBagC.gridx = 0;
        gridBagC.fill = GridBagConstraints.HORIZONTAL;
        editProfileContent.add(historyLb, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Health history input.
        historyInput = new JTextArea();
        // Adjust font and color.
        historyInput.setFont(HealthDiary.LB_FONT);
        historyInput.setForeground(HealthDiary.VALUE_FG_COLOR);
        // historyInput.setBackground(HealthDiary.VALUE_BG_COLOR);
        // historyInput.setOpaque(true);
        // historyInput.setFocusable(false);
        historyInput.setEditable(true);
        historyInput.setLineWrap(true);
        historyInput.setWrapStyleWord(true);
        // historyInput.setHorizontalAlignment(JLabel.LEFT);
        historyInput.setPreferredSize(new Dimension(354, 204));
        historyInput.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(0, 5, 5, 5);
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 6;
        gridBagC.gridx = 0;
        gridBagC.fill = GridBagConstraints.HORIZONTAL;
        editProfileContent.add(historyInput, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Modified ActionListener for edit cancellation.
        ActionListener saveEdit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editProfile.actionPerformed(e);
                updateProfile();
                writeProfileFile();
                refreshProfilePage(2);
                return;
            }
        };

        // ----------------------------------------------------------------------------------------------------
        // Button for save profile editing.
        JButton saveEditBtn = new JButton("Save");
        // Adjust font and color.
        saveEditBtn.setFont(HealthDiary.BTN_FONT);
        saveEditBtn.setForeground(HealthDiary.BTN_FG_COLOR);
        saveEditBtn.setBackground(HealthDiary.POSITIVE_COLOR);
        // Adjust action.
        saveEditBtn.setActionCommand("View Profile");
        saveEditBtn.addActionListener(saveEdit);
        saveEditBtn.setFocusable(false);
        saveEditBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.BTN_FG_COLOR, 3, true),
            BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        // saveEditBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveEditBtn.setMinimumSize(new Dimension(100, 50));

        // ----------------------------------------------------------------------------------------------------
        // Button for cancel profile editing.
        JButton cancelEditBtn = new JButton("Cancel");
        // Adjust font and color.
        cancelEditBtn.setFont(HealthDiary.BTN_FONT);
        cancelEditBtn.setForeground(HealthDiary.BTN_FG_COLOR);
        cancelEditBtn.setBackground(HealthDiary.NEGATIVE_COLOR);
        // Adjust action.
        cancelEditBtn.setActionCommand("View Profile");
        cancelEditBtn.addActionListener(cancelEdit);
        cancelEditBtn.setFocusable(false);
        cancelEditBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.BTN_FG_COLOR, 3, true),
            BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        // cancelEditBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelEditBtn.setMinimumSize(new Dimension(100, 50));

        // ----------------------------------------------------------------------------------------------------
        // Panel for save and cancel buttons.
        JPanel saveCancelPanel = new JPanel();
        saveCancelPanel.setBackground(HealthDiary.THEME_BG_COLOR);
        saveCancelPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        // Set layout.
        GridLayout saveCancelLayout = new GridLayout();
        saveCancelLayout.setHgap(15);
        saveCancelPanel.setLayout(saveCancelLayout);
        // Add button.
        saveCancelPanel.add(saveEditBtn);
        saveCancelPanel.add(cancelEditBtn);
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 5, 10, 5);
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 7;
        gridBagC.gridx = 0;
        gridBagC.fill = GridBagConstraints.HORIZONTAL;
        editProfileContent.add(saveCancelPanel, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Add content to Content Page.
        editProfile.add(editProfileContent, BorderLayout.CENTER);
        addPage(editProfile, "Edit Profile", 1);
        return;
    }

    // Update values in User Profile from inputs.
    private void updateProfile() {
        userProfile.setName(nameInput.getText().strip());

        if (maleRadioBtn.isSelected()) {
            userProfile.setGender(0);
        }
        else {
            userProfile.setGender(1);
        }

        userProfile.setBloodType(bloodComboBox.getSelectedIndex());

        userProfile.setHealthHistory(historyInput.getText().strip());
        return;
    }

    // Refresh profile values in View Profile Page and Edit Profile Page.
    // toRefresh: 0 to refresh View Profile Page, 1 to refresh Edit Profile Page, 2 to refresh both.
    private void refreshProfilePage(int toRefresh) {
        // Refresh View Profile Page after profile is edit.
        if (toRefresh != 1) {
            nameValue.setText(userProfile.getName());
            genderValue.setText(userProfile.getGenderStr());
            bloodValue.setText(userProfile.getBloodTypeStr());
            historyValue.setText(userProfile.getHealthHistory());
        }

        // Refresh Edit Profile Page if the edit is cancelled.
        if (toRefresh != 0) {
            nameInput.setText(userProfile.getName());

            if (userProfile.getGenderIndex() == 0) {
                maleRadioBtn.setSelected(true);
                femaleRadioBtn.setSelected(false);
            }
            else {
                maleRadioBtn.setSelected(false);
                femaleRadioBtn.setSelected(true);
            }

            bloodComboBox.setSelectedIndex(userProfile.getBloodTypeIndex());

            historyInput.setText(userProfile.getHealthHistory());
        }
        return;
    }
}
