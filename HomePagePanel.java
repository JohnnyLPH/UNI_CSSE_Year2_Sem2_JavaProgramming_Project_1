// TMF2954 Java Programming Group Project
// Group Name: LUCKY
// Group Members:
//     - LAU PIKK HEANG (75359)
//     - YUKI CHUNG PEI YING (77237)
//     - ANDREA ANG XIAO XUAN (73347)
// Health Diary App: Home Page Panel.
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class HomePagePanel extends JPanel {
    private JLabel homeHeading;
    private JButton profileButton, recordButton, graphReportButton;

    // Redirect to different pages based on buttons clicked.
    private class RedirectListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String buttonClicked = e.getActionCommand();

            // For debugging, comment out if not used.
            // System.out.println("Button Click: " + buttonClicked);
            // homeHeading.setText("Button Click: " + buttonClicked);
        }
    }

    public HomePagePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(HealthDiary.BG_COLOR);

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
        homeHeading.setHorizontalAlignment(JLabel.CENTER);
        homeHeading.setVerticalAlignment(JLabel.CENTER);

        add(Box.createRigidArea(new Dimension(0, 20)));
        // Add heading to Home Page.
        add(homeHeading);
        add(Box.createRigidArea(new Dimension(0, 20)));

        // Button for User Profile.
        profileButton = new JButton("User Profile");
        profileButton.setActionCommand("User Profile");
        profileButton.addActionListener(new RedirectListener());

        // Add User Profile button.
        add(profileButton);
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Button for Health Records.
        recordButton = new JButton("All Health Records");
        recordButton.setActionCommand("Health Records");
        recordButton.addActionListener(new RedirectListener());
        
        // Add Health Records button.
        add(recordButton);
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Button for Graph Report.
        graphReportButton = new JButton("View Graph Report");
        graphReportButton.setActionCommand("Graph Report");
        graphReportButton.addActionListener(new RedirectListener());
        
        // Add Graph Report button.
        add(graphReportButton);
        return;
    }
}
