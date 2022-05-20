// TMF2954 Java Programming Group Project
// Group Name: LUCKY
// Group Members:
//     - LAU PIKK HEANG (75359)
//     - YUKI CHUNG PEI YING (77237)
//     - ANDREA ANG XIAO XUAN (73347)
// Health Diary App: Main Function is in this file.
import java.awt.*;
import javax.swing.*;


public class HealthDiary {
    public static final Color BG_COLOR = new Color(35, 35, 35);
    public static final Color TEXT_COLOR = Color.white;
    public static final ImageIcon APP_ICON = new ImageIcon("./img/HealthDiary_Icon.png");

    public static void main(String[] args) {
        // Frame for Health Diary app.
        JFrame appFrame = new JFrame("Health Diary by LUCKY");
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Adapt from iPhone SE display resolution (750 * 1334).
        appFrame.setPreferredSize(new Dimension(380, 660));

        // Not allow resizing app frame.
        appFrame.setResizable(false);

        // Set icon.
        appFrame.setIconImage(APP_ICON.getImage());

        // Set background color.
        appFrame.getContentPane().setBackground(BG_COLOR);

        // Add Home Page panel to app frame.
        appFrame.getContentPane().add(new HomePagePanel(), BorderLayout.CENTER);

        // Display app frame.
        appFrame.pack();
        appFrame.setVisible(true);
        return;
    }
}
