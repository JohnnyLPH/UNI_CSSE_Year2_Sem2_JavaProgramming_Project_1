// TMF2954 Java Programming Group Project
// Group Name: LUCKY
// Group Members:
//     - LAU PIKK HEANG (75359)
//     - YUKI CHUNG PEI YING (77237)
//     - ANDREA ANG XIAO XUAN (73347)
// Health Diary App: Record Panel.
// Contain View All Record Page, View Each Record Page, Add Record Page, Edit Record Page, Delete Record Page.
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    // Scroll pane for panel displaying all records.
    private JScrollPane recordScrollPane;
    // Panel for displaying all records with different sorting.
    private JPanel recordSortContent;
    // Index of current displaying record.
    private int recordIndex;
    // Label for data value.
    private JLabel dateValue, bodyTempValue, heightValue, weightValue, bmiValue, statusValue;

    // Constructor.
    public RecordPanel(ContentPanel lastContentPanel) {
        super(lastContentPanel);
        recordIndex = 0;

        readRecordFile();

        addViewAllRecord();
        refreshAllRecordPage();

        addViewEachRecord();
        addAddRecord();
        addEditRecord();
        addDeleteRecord();
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
                // Read from single line (height, weight, body temperature, datetime).
                while (sc.hasNextLine()) {
                    tempRecord = new HealthRecord();
                    String[] tokens = sc.nextLine().split(",");

                    // Read height.
                    tempRecord.setHeight(Double.parseDouble(tokens[0].strip()));

                    // Read weight.
                    tempRecord.setWeight(Double.parseDouble(tokens[1].strip()));

                    // Read  body temperature.
                    tempRecord.setBodyTemp(Double.parseDouble(tokens[2].strip()));

                    // Read datetime.
                    tempRecord.setDateTime(Long.parseLong(tokens[3].strip()));

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
                // Refresh when new criteria or order is selected.
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
        recordScrollPane = new JScrollPane(recordSortContent);
        recordScrollPane.setBorder(BorderFactory.createEmptyBorder());
        // Adjust scrolling speed.
        recordScrollPane.getVerticalScrollBar().setUnitIncrement(24);
        // Adjust scroll bar appearance.
        recordScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        recordScrollPane.getVerticalScrollBar().setBackground(HealthDiary.THEME_BG_COLOR);
        recordScrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
        // Add to content.
        viewAllRecordContent.add(recordScrollPane, BorderLayout.CENTER);

        // ----------------------------------------------------------------------------------------------------
        // Modified ActionListener for redirecting to Add Record Page.
        ActionListener toAddPage = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Refresh first to clear previous inputs.
                refreshAddRecordPage();
                viewAllRecord.actionPerformed(e);
                return;
            }
        };

        // ----------------------------------------------------------------------------------------------------
        // Button for redirecting to Edit Profile Page.
        JButton toAddPageBtn = new JButton("Add New Record");
        // Adjust font and color.
        toAddPageBtn.setFont(HealthDiary.BTN_FONT);
        toAddPageBtn.setForeground(HealthDiary.BTN_FG_COLOR);
        toAddPageBtn.setBackground(HealthDiary.POSITIVE_COLOR);
        // Adjust action.
        toAddPageBtn.setActionCommand("Add Record");
        toAddPageBtn.addActionListener(toAddPage);
        toAddPageBtn.setFocusable(false);
        toAddPageBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.BTN_FG_COLOR, 3, true),
            BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        // toAddPageBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        toAddPageBtn.setMinimumSize(new Dimension(200, 50));
        viewAllRecordContent.add(toAddPageBtn, BorderLayout.SOUTH);

        // ----------------------------------------------------------------------------------------------------
        // Add content to Content Page.
        viewAllRecord.add(viewAllRecordContent, BorderLayout.CENTER);
        addPage(viewAllRecord, "View All Record", 0);
        return;
    }

    // Add View Each Record Page.
    private void addViewEachRecord() {
        viewEachRecord = new ContentPage(this);
        viewEachRecord.setLayout(new BorderLayout());

        // ----------------------------------------------------------------------------------------------------
        // Button for redirecting to Last Panel.
        JButton toViewAllPageBtn = new JButton(HealthDiary.UNI_RETURN_ARROW + " Back");
        // Adjust font and color.
        toViewAllPageBtn.setFont(HealthDiary.SMALL_BTN_FONT);
        toViewAllPageBtn.setForeground(HealthDiary.BTN_FG_COLOR);
        toViewAllPageBtn.setBackground(HealthDiary.BTN_BG_COLOR);
        // Adjust action.
        toViewAllPageBtn.setActionCommand("View All Record");
        toViewAllPageBtn.addActionListener(viewEachRecord);
        toViewAllPageBtn.setFocusable(false);
        // Add to Content Page.
        viewEachRecord.add(toViewAllPageBtn, BorderLayout.NORTH);

        // ----------------------------------------------------------------------------------------------------
        // For main content of View Each Record (i.e., all the record fields and values).
        JPanel viewEachRecordContent = new JPanel(new GridBagLayout());
        // No reuse, new object for each component.
        GridBagConstraints gridBagC;
        viewEachRecordContent.setBackground(HealthDiary.THEME_BG_COLOR);

        // ----------------------------------------------------------------------------------------------------
        // Heading for View Each Record content.
        JLabel viewEachRecordHeading = new JLabel("View Selected Record");
        // Adjust font and color.
        viewEachRecordHeading.setFont(HealthDiary.MAIN_FONT);
        viewEachRecordHeading.setForeground(HealthDiary.TEXT_COLOR);
        viewEachRecordHeading.setHorizontalAlignment(JLabel.CENTER);
        // Add icon to heading.
        viewEachRecordHeading.setIcon(new ImageIcon(
            HealthDiary.EACH_RECORD_ICON.getImage().getScaledInstance(
                64, 64, java.awt.Image.SCALE_SMOOTH
            )
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(10, 5, 15, 5);
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 0;
        gridBagC.gridx = 0;
        gridBagC.fill = GridBagConstraints.HORIZONTAL;
        viewEachRecordContent.add(viewEachRecordHeading, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Label for date.
        JLabel dateLb = new JLabel("Date:");
        // Adjust font and color.
        dateLb.setFont(HealthDiary.LB_FONT);
        dateLb.setForeground(HealthDiary.TEXT_COLOR);
        dateLb.setBackground(HealthDiary.BLACK_BG_COLOR);
        dateLb.setOpaque(true);
        dateLb.setHorizontalAlignment(JLabel.RIGHT);
        dateLb.setPreferredSize(new Dimension(100, 30));
        dateLb.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.TEXT_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 5, 5, 2);
        gridBagC.gridy = 1;
        gridBagC.gridx = 0;
        viewEachRecordContent.add(dateLb, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Date value.
        dateValue = new JLabel();
        // Adjust font and color.
        dateValue.setFont(HealthDiary.LB_FONT);
        dateValue.setForeground(HealthDiary.VALUE_FG_COLOR);
        dateValue.setBackground(HealthDiary.VALUE_BG_COLOR);
        dateValue.setOpaque(true);
        dateValue.setFocusable(false);
        dateValue.setHorizontalAlignment(JLabel.LEFT);
        dateValue.setPreferredSize(new Dimension(250, 30));
        dateValue.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 2, 5, 5);
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 1;
        gridBagC.gridx = 1;
        viewEachRecordContent.add(dateValue, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Label for body temperature.
        JLabel bodyTempLb = new JLabel("Temperature:");
        // Adjust font and color.
        bodyTempLb.setFont(HealthDiary.LB_FONT);
        bodyTempLb.setForeground(HealthDiary.TEXT_COLOR);
        bodyTempLb.setBackground(HealthDiary.BLACK_BG_COLOR);
        bodyTempLb.setOpaque(true);
        bodyTempLb.setHorizontalAlignment(JLabel.RIGHT);
        bodyTempLb.setPreferredSize(new Dimension(100, 30));
        bodyTempLb.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.TEXT_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 5, 5, 2);
        gridBagC.gridy = 2;
        gridBagC.gridx = 0;
        viewEachRecordContent.add(bodyTempLb, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Body temperature value.
        bodyTempValue = new JLabel();
        // Adjust font and color.
        bodyTempValue.setFont(HealthDiary.LB_FONT);
        bodyTempValue.setForeground(HealthDiary.VALUE_FG_COLOR);
        bodyTempValue.setBackground(HealthDiary.VALUE_BG_COLOR);
        bodyTempValue.setOpaque(true);
        bodyTempValue.setFocusable(false);
        bodyTempValue.setHorizontalAlignment(JLabel.LEFT);
        bodyTempValue.setPreferredSize(new Dimension(250, 30));
        bodyTempValue.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 2, 5, 5);
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 2;
        gridBagC.gridx = 1;
        viewEachRecordContent.add(bodyTempValue, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Label for height.
        JLabel heightLb = new JLabel("Height:");
        // Adjust font and color.
        heightLb.setFont(HealthDiary.LB_FONT);
        heightLb.setForeground(HealthDiary.TEXT_COLOR);
        heightLb.setBackground(HealthDiary.BLACK_BG_COLOR);
        heightLb.setOpaque(true);
        heightLb.setHorizontalAlignment(JLabel.RIGHT);
        heightLb.setPreferredSize(new Dimension(100, 30));
        heightLb.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.TEXT_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 5, 5, 2);
        gridBagC.gridy = 3;
        gridBagC.gridx = 0;
        viewEachRecordContent.add(heightLb, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Height value.
        heightValue = new JLabel();
        // Adjust font and color.
        heightValue.setFont(HealthDiary.LB_FONT);
        heightValue.setForeground(HealthDiary.VALUE_FG_COLOR);
        heightValue.setBackground(HealthDiary.VALUE_BG_COLOR);
        heightValue.setOpaque(true);
        heightValue.setFocusable(false);
        heightValue.setHorizontalAlignment(JLabel.LEFT);
        heightValue.setPreferredSize(new Dimension(250, 30));
        heightValue.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 2, 5, 5);
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 3;
        gridBagC.gridx = 1;
        viewEachRecordContent.add(heightValue, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Label for weight.
        JLabel weightLb = new JLabel("Weight:");
        // Adjust font and color.
        weightLb.setFont(HealthDiary.LB_FONT);
        weightLb.setForeground(HealthDiary.TEXT_COLOR);
        weightLb.setBackground(HealthDiary.BLACK_BG_COLOR);
        weightLb.setOpaque(true);
        weightLb.setHorizontalAlignment(JLabel.RIGHT);
        weightLb.setPreferredSize(new Dimension(100, 30));
        weightLb.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.TEXT_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 5, 5, 2);
        gridBagC.gridy = 4;
        gridBagC.gridx = 0;
        viewEachRecordContent.add(weightLb, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Weight value.
        weightValue = new JLabel();
        // Adjust font and color.
        weightValue.setFont(HealthDiary.LB_FONT);
        weightValue.setForeground(HealthDiary.VALUE_FG_COLOR);
        weightValue.setBackground(HealthDiary.VALUE_BG_COLOR);
        weightValue.setOpaque(true);
        weightValue.setFocusable(false);
        weightValue.setHorizontalAlignment(JLabel.LEFT);
        weightValue.setPreferredSize(new Dimension(250, 30));
        weightValue.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 2, 5, 5);
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 4;
        gridBagC.gridx = 1;
        viewEachRecordContent.add(weightValue, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Label for BMI.
        JLabel bmiLb = new JLabel("BMI:");
        // Adjust font and color.
        bmiLb.setFont(HealthDiary.LB_FONT);
        bmiLb.setForeground(HealthDiary.TEXT_COLOR);
        bmiLb.setBackground(HealthDiary.BLACK_BG_COLOR);
        bmiLb.setOpaque(true);
        bmiLb.setHorizontalAlignment(JLabel.RIGHT);
        bmiLb.setPreferredSize(new Dimension(100, 30));
        bmiLb.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.TEXT_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 5, 5, 2);
        gridBagC.gridy = 5;
        gridBagC.gridx = 0;
        viewEachRecordContent.add(bmiLb, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // BMI value.
        bmiValue = new JLabel();
        // Adjust font and color.
        bmiValue.setFont(HealthDiary.LB_FONT);
        bmiValue.setForeground(HealthDiary.VALUE_FG_COLOR);
        bmiValue.setBackground(HealthDiary.VALUE_BG_COLOR);
        bmiValue.setOpaque(true);
        bmiValue.setFocusable(false);
        bmiValue.setHorizontalAlignment(JLabel.LEFT);
        bmiValue.setPreferredSize(new Dimension(250, 30));
        bmiValue.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 2, 5, 5);
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 5;
        gridBagC.gridx = 1;
        viewEachRecordContent.add(bmiValue, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Label for status.
        JLabel statusLb = new JLabel("Status:");
        // Adjust font and color.
        statusLb.setFont(HealthDiary.LB_FONT);
        statusLb.setForeground(HealthDiary.TEXT_COLOR);
        statusLb.setBackground(HealthDiary.BLACK_BG_COLOR);
        statusLb.setOpaque(true);
        statusLb.setHorizontalAlignment(JLabel.RIGHT);
        statusLb.setPreferredSize(new Dimension(100, 30));
        statusLb.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.TEXT_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 5, 5, 2);
        gridBagC.gridy = 6;
        gridBagC.gridx = 0;
        viewEachRecordContent.add(statusLb, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Status value.
        statusValue = new JLabel();
        // Adjust font and color.
        statusValue.setFont(HealthDiary.LB_FONT);
        statusValue.setForeground(HealthDiary.VALUE_FG_COLOR);
        statusValue.setBackground(HealthDiary.VALUE_BG_COLOR);
        statusValue.setOpaque(true);
        statusValue.setFocusable(false);
        statusValue.setHorizontalAlignment(JLabel.LEFT);
        statusValue.setPreferredSize(new Dimension(250, 30));
        statusValue.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 2, 5, 5);
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 6;
        gridBagC.gridx = 1;
        viewEachRecordContent.add(statusValue, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Label for health advice.

        // ----------------------------------------------------------------------------------------------------
        // Add content to Content Page.
        viewEachRecord.add(viewEachRecordContent, BorderLayout.CENTER);
        addPage(viewEachRecord, "View Each Record", 1);
        return;
    }

    // Add Add Record Page.
    private void addAddRecord() {
        return;
    }

    // Add Edit Record Page.
    private void addEditRecord() {
        return;
    }

    // Add Delete Record Page.
    private void addDeleteRecord() {
        return;
    }

    // Add new Health Record to list.
    private void addListRecord() {
        return;
    }

    // Update Health Record in list.
    private void updateListRecord() {
        return;
    }

    // Delete Health Record from list.
    private void deleteListRecord() {
        return;
    }

    // Refresh records with sorting in View All Record Page.
    private void refreshAllRecordPage() {
        // Sort the record list using modified Comparator.
        Comparator<HealthRecord> recordComparator;

        // ----------------------------------------------------------------------------------------------------
        // Criteria (Date, Weight, BMI}).
        // Sort by Date.
        if (sortCriBox.getSelectedIndex() == 0) {
            // Modify Comparator for Date.
            recordComparator = new Comparator<HealthRecord>() {
                @Override
                public int compare(HealthRecord o1, HealthRecord o2) {
                    if (o1.getDateTimeLong() > o2.getDateTimeLong()) {
                        return 1;
                    }
                    else if (o1.getDateTimeLong() < o2.getDateTimeLong()) {
                        return -1;
                    }
                    return 0;
                }
            };
        }
        // Sort by Weight.
        else if (sortCriBox.getSelectedIndex() == 1) {
            // Modify Comparator for Weight.
            recordComparator = new Comparator<HealthRecord>() {
                @Override
                public int compare(HealthRecord o1, HealthRecord o2) {
                    if (o1.getWeight() > o2.getWeight()) {
                        return 1;
                    }
                    else if (o1.getWeight() < o2.getWeight()) {
                        return -1;
                    }
                    return 0;
                }
            };

        }
        // Sort by BMI.
        else {
            // Modify Comparator for BMI.
            recordComparator = new Comparator<HealthRecord>() {
                @Override
                public int compare(HealthRecord o1, HealthRecord o2) {
                    if (o1.getBMI() > o2.getBMI()) {
                        return 1;
                    }
                    else if (o1.getBMI() < o2.getBMI()) {
                        return -1;
                    }
                    return 0;
                }
            };
        }

        // ----------------------------------------------------------------------------------------------------
        // Order (Ascend, Descend).
        // Sort in ascending order first.
        Collections.sort(allRecord, recordComparator);
        // Sort in descending order if needed by reversing the sorted list.
        if (sortOrderBox.getSelectedIndex() == 0) {
            Collections.reverse(allRecord);
        }

        // ----------------------------------------------------------------------------------------------------
        // Scroll back to top.
        recordScrollPane.getVerticalScrollBar().setValue(0);
        // Remove all components in record sort content.
        recordSortContent.removeAll();
        recordSortContent.revalidate();
        recordSortContent.repaint();

        // ----------------------------------------------------------------------------------------------------
        // Display record.
        if (allRecord.size() > 0) {
            JButton tempRecordBtn;
            JLabel dateLb, weightLb, bmiLb;
            GridBagConstraints gridBagC;
            // Readding to record sort content.
            for (int i = 0; i < allRecord.size(); i++) {
                // Space in-between.
                if (i > 0) {
                    recordSortContent.add(Box.createRigidArea(new Dimension(0, 10)));
                }
    
                // ----------------------------------------------------------------------------------------------------
                // Button for redirecting to View Each Record Page.
                // Use GridBagLayout for formatting button.
                tempRecordBtn = new JButton();
                tempRecordBtn.setLayout(new GridBagLayout());

                // ----------------------------------------------------------------------------------------------------
                // Add labels for date, weight, and BMI.
                dateLb = new JLabel(
                    "Date: " + allRecord.get(i).getDateTimeStr()
                );
                weightLb = new JLabel(
                    "Weight: " + HealthRecord.VALUE_FORMAT.format(allRecord.get(i).getWeight()) + " kg"
                );
                bmiLb = new JLabel(
                    "BMI: " + HealthRecord.VALUE_FORMAT.format(allRecord.get(i).getBMI())
                );
                // Adjust labels font and color.
                dateLb.setFont(HealthDiary.SMALL_BTN_FONT);
                weightLb.setFont(HealthDiary.SMALL_BTN_FONT);
                bmiLb.setFont(HealthDiary.SMALL_BTN_FONT);
                dateLb.setForeground(HealthDiary.VALUE_FG_COLOR);
                weightLb.setForeground(HealthDiary.VALUE_FG_COLOR);
                bmiLb.setForeground(HealthDiary.VALUE_FG_COLOR);
                dateLb.setHorizontalAlignment(JLabel.CENTER);
                weightLb.setHorizontalAlignment(JLabel.LEFT);
                bmiLb.setHorizontalAlignment(JLabel.RIGHT);
                // Adjust border.
                dateLb.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                dateLb.setPreferredSize(new Dimension(250, 30));;
                weightLb.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                bmiLb.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                // Add labels to button.
                gridBagC = new GridBagConstraints();
                gridBagC.gridwidth = GridBagConstraints.REMAINDER;
                gridBagC.gridy = 0;
                gridBagC.gridx = 0;
                gridBagC.fill = GridBagConstraints.HORIZONTAL;
                tempRecordBtn.add(dateLb, gridBagC);
                gridBagC.gridwidth = 1;
                gridBagC.gridy = 1;
                gridBagC.gridx = 0;
                tempRecordBtn.add(weightLb, gridBagC);
                gridBagC.gridx = 1;
                tempRecordBtn.add(bmiLb, gridBagC);

                // ----------------------------------------------------------------------------------------------------
                // Adjust button color.
                tempRecordBtn.setBackground(HealthDiary.VALUE_BG_COLOR);
                // Adjust button border.
                tempRecordBtn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(HealthDiary.BTN_FG_COLOR, 4, true),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
                ));
                // Adjust button action.
                final int tempIndex = i;
                tempRecordBtn.setActionCommand("View Each Record");
                tempRecordBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Refresh View Each Record Page before redirecting.
                        recordIndex = tempIndex;
                        refreshEachRecordPage();
                        viewAllRecord.actionPerformed(e);
                    }
                }
                );
                tempRecordBtn.setFocusable(false);
                tempRecordBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
    
                // Add button to record sort content.
                recordSortContent.add(tempRecordBtn);
            }
        }
        // No record to display.
        else {
            JLabel emptyInfo = new JLabel("No Record is found :(");
            emptyInfo.setFont(HealthDiary.BTN_FONT);
            emptyInfo.setForeground(HealthDiary.VALUE_FG_COLOR);
            emptyInfo.setHorizontalAlignment(JLabel.CENTER);
            emptyInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
            recordSortContent.add(emptyInfo);
        }
        return;
    }

    // Refresh View Each Record Page based on the selected record.
    private void refreshEachRecordPage() {
        dateValue.setText(allRecord.get(recordIndex).getDateTimeStr());
        bodyTempValue.setText(
            HealthRecord.VALUE_FORMAT.format(allRecord.get(recordIndex).getBodyTemp()) +
            " " + HealthDiary.UNI_CELSIUS
        );
        heightValue.setText(
            HealthRecord.VALUE_FORMAT.format(allRecord.get(recordIndex).getHeight()) + " cm"
        );
        weightValue.setText(
            HealthRecord.VALUE_FORMAT.format(allRecord.get(recordIndex).getWeight()) + " kg"
        );
        bmiValue.setText(
            HealthRecord.VALUE_FORMAT.format(allRecord.get(recordIndex).getBMI())
        );
        statusValue.setText(allRecord.get(recordIndex).getStatusStr());
        return;
    }

    // Refresh Add Record Page for adding new Health Record.
    private void refreshAddRecordPage() {

        return;
    }

}
