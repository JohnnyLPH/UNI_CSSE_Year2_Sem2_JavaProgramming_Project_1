// TMF2954 Java Programming Group Project
// Group Name: LUCKY
// Group Members:
//     - LAU PIKK HEANG (75359)
//     - YUKI CHUNG PEI YING (77237)
//     - ANDREA ANG XIAO XUAN (73347)
// Health Diary App: Record Panel.
// Contain View All Record Page, View Each Record Page, Add Record Page, Edit Record Page, Delete Record Page.
import java.awt.*;
import javax.swing.*;


public class RecordPanel extends ContentPanel {
    private ContentPage viewAllRecord, viewEachRecord, addRecord, editRecord, deleteRecord;
    private JLabel viewAllRecordHeading;
    private JButton returnPanelBtn;

    // Constructor.
    public RecordPanel(ContentPanel lastContentPanel) {
        super(lastContentPanel);

        addViewAllRecord();
        return;
    }

    // Method.
    public void addViewAllRecord() {
        viewAllRecord = new ContentPage(this);
        viewAllRecord.setLayout(new BorderLayout());

        // Button for redirecting to Last Panel.
        returnPanelBtn = new JButton(HealthDiary.UNI_RETURN_ARROW + " Back");
        // Adjust button font.
        returnPanelBtn.setFont(HealthDiary.SMALL_BTN_FONT);
        // Adjust button color.
        returnPanelBtn.setForeground(HealthDiary.BTN_FG_COLOR);
        returnPanelBtn.setBackground(HealthDiary.BTN_BG_COLOR);
        // Adjust button action.
        returnPanelBtn.setActionCommand("Last Panel");
        returnPanelBtn.addActionListener(viewAllRecord);
        returnPanelBtn.setFocusable(false);

        viewAllRecord.add(returnPanelBtn, BorderLayout.NORTH);

        // Heading for View All Record.
        viewAllRecordHeading = new JLabel("All Records");
        // Adjust heading font.
        viewAllRecordHeading.setFont(HealthDiary.MAIN_FONT);
        // Adjust heading color.
        viewAllRecordHeading.setForeground(HealthDiary.TEXT_COLOR);

        viewAllRecord.add(viewAllRecordHeading, BorderLayout.CENTER);

        addPage(viewAllRecord, "View All Record", 0);
        return;
    }
}