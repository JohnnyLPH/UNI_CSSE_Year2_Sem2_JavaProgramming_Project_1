// TMF2954 Java Programming Group Project
// Group Name: LUCKY
// Group Members:
//     - LAU PIKK HEANG (75359)
//     - YUKI CHUNG PEI YING (77237)
//     - ANDREA ANG XIAO XUAN (73347)
// Health Diary App: Main Function is in this file.
import javax.swing.JFrame;
import java.awt.Dimension;


public class HealthDiary {
    public static void main(String[] args) {
        // Frame for Health Diary app.
        JFrame appFrame = new JFrame("Health Diary by LUCKY");
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Based on iPhone SE display resolution (750 * 1334).
        appFrame.setPreferredSize(new Dimension(375, 667));
        // Not allow resizing app frame.
        appFrame.setResizable(false);

        // TODO: Add content to app frame, first create other classes.

        // Display app frame.
        appFrame.pack();
        appFrame.setVisible(true);
        return;
    }
}
