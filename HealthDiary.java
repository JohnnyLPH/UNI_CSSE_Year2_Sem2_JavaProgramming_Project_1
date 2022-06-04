// TMF2954 Java Programming Group Project
// Group Name: LUCKY
// Group Members:
//     - LAU PIKK HEANG (75359)
//     - YUKI CHUNG PEI YING (77237)
//     - ANDREA ANG XIAO XUAN (73347)
// Health Diary App: Main Function in this file.
import java.io.File;

import java.awt.*;
import javax.swing.*;


public class HealthDiary {
    // Constants defined for later use.
    // Color Picker: https://duckduckgo.com/?q=color+picker&ia=answer
    public static final Color BLACK_BG_COLOR = new Color(35, 35, 35);
    public static final Color THEME_BG_COLOR = new Color(0, 146, 173);
    public static final Color TEXT_COLOR = Color.white;
    public static final Color VALUE_FG_COLOR = Color.black;
    public static final Color VALUE_BG_COLOR = new Color(181, 234, 242);
    public static final Color BTN_BG_COLOR = new Color(26, 174, 214);
    public static final Color BTN_FG_COLOR = TEXT_COLOR;
    public static final Color POSITIVE_COLOR = new Color(97, 219, 37);
    public static final Color NEGATIVE_COLOR = new Color(237, 25, 14);
    // Image.
    public static final ImageIcon APP_ICON = new ImageIcon("./img/HealthDiary_Icon.png");
    public static final ImageIcon PROFILE_ICON = new ImageIcon("./img/Profile_Icon.png");
    public static final ImageIcon EDIT_PROFILE_ICON = new ImageIcon("./img/EditProfile_Icon.png");
    public static final ImageIcon ALL_RECORD_ICON = new ImageIcon("./img/RecordList_Icon.png");
    public static final ImageIcon EACH_RECORD_ICON = new ImageIcon("./img/ViewRecord_Icon.png");
    public static final ImageIcon ADD_RECORD_ICON = new ImageIcon("./img/AddRecord_Icon.png");
    public static final ImageIcon EDIT_RECORD_ICON = new ImageIcon("./img/EditRecord_Icon.png");
    // Data File.
    public static final File PROFILE_FILE = new File("./data/UserProfile_Data.txt");
    public static final File RECORD_FILE = new File("./data/HealthRecord_Data.txt");
    // Font.
    public static final Font MAIN_FONT = new Font("Comic Sans", Font.BOLD, 20);
    public static final Font BTN_FONT = new Font("Comic Sans", Font.BOLD, 15);
    public static final Font SMALL_BTN_FONT = new Font("Comic Sans", Font.BOLD, 13);
    public static final Font LB_FONT = SMALL_BTN_FONT;
    // Unicode special character.
    public static final char UNI_CROSS = '\u26E8';  // https://unicode-table.com/en/26E8/
    public static final char UNI_RETURN_ARROW = '\u21A9';  // https://unicode-table.com/en/21A9/
    public static final char UNI_MALE = '\u2642';  // https://unicode-table.com/en/2642/
    public static final char UNI_FEMALE = '\u2640';  // https://unicode-table.com/en/2640/
    public static final char UNI_CELSIUS = '\u2103';  // https://unicode-table.com/en/2103/

    public static void main(String[] args) {
        // New app frame.
        JFrame appFrame = new JFrame(UNI_CROSS + "Health Diary" + UNI_CROSS + " by LUCKY");
        // Set to exit on close.
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Adapt from iPhone SE display resolution (750 * 1334).
        appFrame.setPreferredSize(new Dimension(380, 660));
        // Not allow resizing app frame.
        appFrame.setResizable(false);
        // Set icon.
        appFrame.setIconImage(APP_ICON.getImage());
        // Set background color.
        appFrame.getContentPane().setBackground(BLACK_BG_COLOR);

        // Add content panel to app frame.
        HomePanel homePanel = new HomePanel();
        appFrame.getContentPane().add(homePanel, BorderLayout.CENTER);

        // Display app frame.
        appFrame.pack();
        appFrame.setVisible(true);
        return;
    }
}
