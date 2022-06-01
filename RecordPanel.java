// TMF2954 Java Programming Group Project
// Group Name: LUCKY
// Group Members:
//     - LAU PIKK HEANG (75359)
//     - YUKI CHUNG PEI YING (77237)
//     - ANDREA ANG XIAO XUAN (73347)
// Health Diary App: Record Panel.
// Contain View All Record Page, View Each Record Page, Add Record Page, Edit Record Page, Delete Record Page.
import java.util.ArrayList;
import java.util.Scanner;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
import javax.swing.plaf.InsetsUIResource;


public class RecordPanel extends ContentPanel {
    // Content Page.
    private ContentPage viewAllRecord, viewEachRecord, addRecord, editRecord, deleteRecord;
    // List of Health Record.
    private ArrayList<HealthRecord> allRecord;
    // Combo Box for sorting criteria and order.
    private JComboBox<String> sortCriBox, sortOrderBox;
    // Panel for displaying all records with different sorting.
    private JPanel recordSortContent;

    // Constructor.
    public RecordPanel(ContentPanel lastContentPanel) {
        super(lastContentPanel);

        readRecordFile();
        addViewAllRecord();
        refreshAllRecordPage();
        return;
    }

    // Method.
    // Read Health Record from file.
    private void readRecordFile() {
        try {
            allRecord = new ArrayList<HealthRecord>();

            // File already exists and not created.
            if (!HealthDiary.RECORD_FILE.createNewFile()) {
                // System.out.println("File exists.");
                Scanner sc = new Scanner(HealthDiary.RECORD_FILE);
                
                HealthRecord tempRecord;
                // Read from single line (height, weight, bodyTemp, dateTime).
                while (sc.hasNextLine()) {
                    tempRecord = new HealthRecord();
                    String[] tokens = sc.nextLine().split(",");

                    // Read height.
                    tempRecord.setHeight(Double.parseDouble(tokens[0].strip()));

                    // Read weight.
                    tempRecord.setWeight(Double.parseDouble(tokens[1].strip()));

                    // Read bodyTemp.
                    tempRecord.setBodyTemp(Double.parseDouble(tokens[2].strip()));

                    // Read dateTime.
                    // tempRecord.setDateTime(Long.parseLong(tokens[3].strip()));

                    // Add to list.
                    allRecord.add(tempRecord);
                }
                sc.close();
            }
        }
        catch (Exception e) {
            System.out.println("Error with file in readRecordFile().");
        }
        return;
    }

    // Add View All Record Page.
    private void addViewAllRecord() {
        viewAllRecord = new ContentPage(this);
        viewAllRecord.setLayout(new BorderLayout());

        // ----------------------------------------------------------------------------------------------------
        // Button for redirecting to Last Panel.
        JButton returnPanelBtn = new JButton(HealthDiary.UNI_RETURN_ARROW + " Back");
        // Adjust font and color.
        returnPanelBtn.setFont(HealthDiary.SMALL_BTN_FONT);
        returnPanelBtn.setForeground(HealthDiary.BTN_FG_COLOR);
        returnPanelBtn.setBackground(HealthDiary.BTN_BG_COLOR);
        // Adjust action.
        returnPanelBtn.setActionCommand("Last Panel");
        returnPanelBtn.addActionListener(viewAllRecord);
        returnPanelBtn.setFocusable(false);
        // Add to Content Page.
        viewAllRecord.add(returnPanelBtn, BorderLayout.NORTH);

        // ----------------------------------------------------------------------------------------------------
        // For main content of View All Record (i.e., all the records, sorting options, add record button).
        JPanel viewAllRecordContent = new JPanel(new BorderLayout());
        viewAllRecordContent.setBackground(HealthDiary.THEME_BG_COLOR);

        // ----------------------------------------------------------------------------------------------------
        // Include the heading and sorting options.
        JPanel headingContent = new JPanel(new GridBagLayout());
        // No reuse, new object for each component.
        GridBagConstraints gridBagC;
        headingContent.setBackground(HealthDiary.THEME_BG_COLOR);
        viewAllRecordContent.add(headingContent, BorderLayout.NORTH);

        // ----------------------------------------------------------------------------------------------------
        // Heading for View All Record.
        JLabel viewAllRecordHeading = new JLabel("Health Records");
        // Adjust font and color.
        viewAllRecordHeading.setFont(HealthDiary.MAIN_FONT);
        viewAllRecordHeading.setForeground(HealthDiary.TEXT_COLOR);
        viewAllRecordHeading.setHorizontalAlignment(JLabel.CENTER);
        // Add icon to heading.
        viewAllRecordHeading.setIcon(new ImageIcon(
            HealthDiary.ALL_RECORD_ICON.getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH)
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(10, 5, 5, 5);
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 0;
        gridBagC.gridx = 0;
        gridBagC.fill = GridBagConstraints.HORIZONTAL;
        headingContent.add(viewAllRecordHeading, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Sort Label.
        JLabel sortLb = new JLabel("Sort By:");
        // Adjust font and color.
        sortLb.setFont(HealthDiary.BTN_FONT);
        sortLb.setForeground(HealthDiary.TEXT_COLOR);
        // sortLb.setBackground(HealthDiary.BLACK_BG_COLOR);
        // sortLb.setOpaque(true);
        sortLb.setHorizontalAlignment(JLabel.LEFT);
        sortLb.setMinimumSize(new Dimension(50, 30));
        sortLb.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 5, 5, 5);
        gridBagC.gridy = 1;
        gridBagC.gridx = 0;
        headingContent.add(sortLb, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Panel for sorting criteria and order inputs.
        GridLayout tempGridLayout = new GridLayout();
        tempGridLayout.setHgap(15);
        JPanel sortOptionPanel = new JPanel(tempGridLayout);
        sortOptionPanel.setBackground(HealthDiary.THEME_BG_COLOR);

        // ----------------------------------------------------------------------------------------------------
        // Modified ItemListener for refreshing View All Record Page with new sorting.
        ItemListener changeSort = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    refreshAllRecordPage();
                }
                return;
            }
        };

        // ----------------------------------------------------------------------------------------------------
        // Sorting criteria and order inputs (Combo Box).
        sortCriBox = new JComboBox<String>(HealthRecord.SORT_CRITERIA);
        sortCriBox.setSelectedIndex(0);
        sortOrderBox = new JComboBox<String>(HealthRecord.SORT_ORDER);
        sortOrderBox.setSelectedIndex(0);
        // Set ItemListener.
        sortCriBox.addItemListener(changeSort);
        sortOrderBox.addItemListener(changeSort);
        // Adjust font and color.
        sortCriBox.setFont(HealthDiary.SMALL_BTN_FONT);
        sortCriBox.setForeground(HealthDiary.VALUE_FG_COLOR);
        sortCriBox.setBackground(HealthDiary.VALUE_BG_COLOR);
        sortOrderBox.setFont(HealthDiary.SMALL_BTN_FONT);
        sortOrderBox.setBackground(HealthDiary.VALUE_BG_COLOR);
        sortOrderBox.setForeground(HealthDiary.VALUE_FG_COLOR);
        // Add border.
        sortCriBox.setBorder(BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2));
        sortCriBox.setMinimumSize(new Dimension(100, 20));
        sortCriBox.setFocusable(false);
        sortCriBox.setEditable(false);
        sortOrderBox.setBorder(BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2));
        sortOrderBox.setMinimumSize(new Dimension(100, 20));
        sortOrderBox.setFocusable(false);
        sortOrderBox.setEditable(false);
        // Add to content.
        sortOptionPanel.add(sortCriBox);
        sortOptionPanel.add(sortOrderBox);
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 5, 5, 5);
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 1;
        gridBagC.gridx = 1;
        gridBagC.fill = GridBagConstraints.HORIZONTAL;
        headingContent.add(sortOptionPanel, gridBagC);


        // ----------------------------------------------------------------------------------------------------
        // Panel for displaying all records with different sorting.
        recordSortContent = new JPanel();
        recordSortContent.setLayout(new BoxLayout(recordSortContent, BoxLayout.Y_AXIS));
        recordSortContent.setBackground(HealthDiary.BTN_BG_COLOR);
        recordSortContent.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        // ----------------------------------------------------------------------------------------------------
        // Scroll pane for panel displaying all records.
        JScrollPane recordScrollPane = new JScrollPane(recordSortContent);
        recordScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        // Adjust scrolling speed.
        recordScrollPane.getVerticalScrollBar().setUnitIncrement(24);
        viewAllRecordContent.add(recordScrollPane, BorderLayout.CENTER);

        // ----------------------------------------------------------------------------------------------------
        // Add content to Content Page.
        viewAllRecord.add(viewAllRecordContent, BorderLayout.CENTER);
        addPage(viewAllRecord, "View All Record", 0);
        return;
    }

    // Refresh records with sorting in View All Record Page.
    private void refreshAllRecordPage() {
        // Sort the record list.
        // Criteria (Date, Weight, BMI}), Order (Ascend, Descend).


        // System.out.println(
        //     "Sort Change: " + allSortCri[sortCriBox.getSelectedIndex()] +
        //     " " + allSortOrder[sortOrderBox.getSelectedIndex()]
        // );

        // Remove all components in record sort content.
        recordSortContent.removeAll();
        recordSortContent.revalidate();
        recordSortContent.repaint();
        
        JButton tempRecordBtn;
        // Readding to record sort content.
        for (int i = 0; i < allRecord.size(); i++) {
            // Space in-between.
            if (i > 0) {
                recordSortContent.add(Box.createRigidArea(new Dimension(0, 15)));
            }

            // ----------------------------------------------------------------------------------------------------
            // Button for redirecting to View Each Record Page.
            // Use HTML to allow display of multiline text.
            tempRecordBtn = new JButton(
                "<html>" +
                allRecord.get(i).toString().replaceAll("\\n", "<br>") +
                "</html>"
            );
            // Adjust button font and color.
            tempRecordBtn.setFont(HealthDiary.BTN_FONT);
            tempRecordBtn.setForeground(HealthDiary.VALUE_FG_COLOR);
            tempRecordBtn.setBackground(HealthDiary.VALUE_BG_COLOR);
            // Adjust button border.
            tempRecordBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(HealthDiary.BTN_FG_COLOR, 4, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));

            // Adjust button action.
            final int recordIndex = i;
            tempRecordBtn.setActionCommand("View Each Record");
            tempRecordBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Refresh View Each Record Page before redirecting.
                    refreshEachRecordPage(recordIndex);
                    viewAllRecord.actionPerformed(e);
                }
            }
            );
            tempRecordBtn.setFocusable(false);
            tempRecordBtn.setMaximumSize(new Dimension(300, 50));
            tempRecordBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Add button to record sort content.
            recordSortContent.add(tempRecordBtn);
        }
        return;
    }

    // Refresh View Each Record Page based on the selected record from View All Record Page.
    private void refreshEachRecordPage(int recordIndex) {

        return;
    }
}
