// TMF2954 Java Programming Group Project
// Group Name: LUCKY
// Group Members:
//     - LAU PIKK HEANG (75359)
//     - YUKI CHUNG PEI YING (77237)
//     - ANDREA ANG XIAO XUAN (73347)
// Health Diary App: App Frame.
// Contain the content panel.
import java.awt.*;
import javax.swing.*;


public class AppFrame extends JFrame {
    // Constant defined for later use.
    public static final Color BG_COLOR = new Color(35, 35, 35);
    public static final Color TEXT_COLOR = Color.white;
    public static final ImageIcon APP_ICON = new ImageIcon("./img/HealthDiary_Icon.png");

    // Main content panel.
    private ContentPanel contentPanel;

    public AppFrame(String title) {
        super(title);

        // Set to exit on close.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Adapt from iPhone SE display resolution (750 * 1334).
        setPreferredSize(new Dimension(380, 660));

        // Not allow resizing app frame.
        setResizable(false);

        // Set icon.
        setIconImage(APP_ICON.getImage());

        // Set background color.
        getContentPane().setBackground(BG_COLOR);

        // Add content panel to app frame.
        contentPanel = new ContentPanel();
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Display app frame.
        pack();
        setVisible(true);
        return;
    }
}
