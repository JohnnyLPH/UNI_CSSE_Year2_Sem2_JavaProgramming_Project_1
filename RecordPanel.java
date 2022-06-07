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
import java.io.FileWriter;

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
    // Label for data value and input messages.
    private JLabel[] recordValues, editMsgs, addMsgs;
    // Textfield for input.
    private JTextField[] editInputs, addInputs;
    // Text Area for advice value.
    private JTextArea adviceValue;

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

    // Write Health Record to file.
    private void writeRecordFile(boolean append) {
        try {
            // Ensure the file is created if not exist.
            HealthDiary.RECORD_FILE.createNewFile();

            FileWriter newWriter = new FileWriter(HealthDiary.RECORD_FILE, append);
            // Write each record in single line (height, weight, body temperature, datetime).
            for (int i = (append) ? allRecord.size() - 1: 0; i < allRecord.size(); i++) {
                if (i > 0) {
                    newWriter.write("\n");
                }
                newWriter.write(HealthRecord.VALUE_FORMAT.format(allRecord.get(i).getHeight()));
                newWriter.write(", " + HealthRecord.VALUE_FORMAT.format(allRecord.get(i).getWeight()));
                newWriter.write(", " + HealthRecord.VALUE_FORMAT.format(allRecord.get(i).getBodyTemp()));
                newWriter.write(", " + allRecord.get(i).getDateTimeLong());
            }

            newWriter.close();
        }
        catch (Exception e) {
            System.out.println("Error with file in writeRecordFile().");
        }
        return;
    }

    // Validate datetime.
    private boolean validateDateTime(String inputStr) {
        try {
            // Format: dd/MM/yyyy hh:mm aa
            HealthRecord.DATE_FORMAT.parse(inputStr);

            if (inputStr.strip().length() != 19) {
                return false;
            }
        }
        catch (Exception errorMsg) {
            return false;
        }
        return true;
    }

    // Validate body temperature.
    private boolean validateBodyTemp(String inputStr) {
        try {
            Double tValue = Double.parseDouble(inputStr);
            
            if (tValue < 25 || tValue > 50) {
                return false;
            }
        }
        catch (Exception errorMsg) {
            return false;
        }
        return true;
    }

    // Validate height.
    private boolean validateHeight(String inputStr) {
        try {
            Double tValue = Double.parseDouble(inputStr);
            
            if (tValue < 50 || tValue > 250) {
                return false;
            }
        }
        catch (Exception errorMsg) {
            return false;
        }
        return true;
    }

    // Validate weight.
    private boolean validateWeight(String inputStr) {
        try {
            Double tValue = Double.parseDouble(inputStr);
            
            if (tValue < 5 || tValue > 500) {
                return false;
            }
        }
        catch (Exception errorMsg) {
            return false;
        }
        return true;
    }

    // Add View All Record Page.
    private void addViewAllRecord() {
        viewAllRecord = new ContentPage(this, "View All Record", 0);
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
        addPage(viewAllRecord);
        return;
    }

    // Add View Each Record Page.
    private void addViewEachRecord() {
        viewEachRecord = new ContentPage(this, "View Each Record", 1);
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
        // Array of data value.
        recordValues = new JLabel[6];
        JLabel[] valueLb = new JLabel[6];

        for (int i = 0; i < 6; i++) {
            // ----------------------------------------------------------------------------------------------------
            // Label for value.
            valueLb[i] = new JLabel("Date:");
            // Adjust font and color.
            valueLb[i].setFont(HealthDiary.LB_FONT);
            valueLb[i].setForeground(HealthDiary.TEXT_COLOR);
            valueLb[i].setBackground(HealthDiary.BLACK_BG_COLOR);
            valueLb[i].setOpaque(true);
            valueLb[i].setHorizontalAlignment(JLabel.RIGHT);
            valueLb[i].setPreferredSize(new Dimension(100, 30));
            valueLb[i].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(HealthDiary.TEXT_COLOR, 2),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
            ));
            // Add to content.
            gridBagC = new GridBagConstraints();
            gridBagC.insets = new InsetsUIResource(5, 5, 5, 2);
            gridBagC.gridy = i + 1;
            gridBagC.gridx = 0;
            viewEachRecordContent.add(valueLb[i], gridBagC);
    
            // ----------------------------------------------------------------------------------------------------
            // Value.
            recordValues[i] = new JLabel();
            // Adjust font and color.
            recordValues[i].setFont(HealthDiary.LB_FONT);
            recordValues[i].setForeground(HealthDiary.VALUE_FG_COLOR);
            recordValues[i].setBackground(HealthDiary.VALUE_BG_COLOR);
            recordValues[i].setOpaque(true);
            recordValues[i].setFocusable(false);
            recordValues[i].setHorizontalAlignment(JLabel.LEFT);
            recordValues[i].setPreferredSize(new Dimension(250, 30));
            recordValues[i].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
            ));
            // Add to content.
            gridBagC = new GridBagConstraints();
            gridBagC.insets = new InsetsUIResource(5, 2, 5, 5);
            gridBagC.gridwidth = GridBagConstraints.REMAINDER;
            gridBagC.gridy = i + 1;
            gridBagC.gridx = 1;
            viewEachRecordContent.add(recordValues[i], gridBagC);
        }
        // Set label text.
        valueLb[0].setText("Date:");
        valueLb[1].setText("Temperature:");
        valueLb[2].setText("Height:");
        valueLb[3].setText("Weight:");
        valueLb[4].setText("BMI:");
        valueLb[5].setText("BMI Status:");

        // ----------------------------------------------------------------------------------------------------
        // Label for health advice.
        JLabel adviceLb = new JLabel("LUCKY Health Advice:");
        // Adjust font and color.
        adviceLb.setFont(HealthDiary.LB_FONT);
        adviceLb.setForeground(HealthDiary.TEXT_COLOR);
        adviceLb.setBackground(HealthDiary.BLACK_BG_COLOR);
        adviceLb.setOpaque(true);
        adviceLb.setHorizontalAlignment(JLabel.CENTER);
        adviceLb.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.TEXT_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 5, 2, 5);
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 7;
        gridBagC.gridx = 0;
        gridBagC.fill = GridBagConstraints.HORIZONTAL;
        viewEachRecordContent.add(adviceLb, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Health advice value.
        adviceValue = new JTextArea();
        // Adjust font and color.
        adviceValue.setFont(HealthDiary.LB_FONT);
        adviceValue.setForeground(HealthDiary.VALUE_FG_COLOR);
        adviceValue.setBackground(HealthDiary.VALUE_BG_COLOR);
        adviceValue.setOpaque(true);
        adviceValue.setFocusable(false);
        adviceValue.setLineWrap(true);
        adviceValue.setWrapStyleWord(true);
        adviceValue.setPreferredSize(new Dimension(354, 100));
        adviceValue.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(2, 5, 5, 5);
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 8;
        gridBagC.gridx = 0;
        gridBagC.fill = GridBagConstraints.HORIZONTAL;
        viewEachRecordContent.add(adviceValue, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Modified ActionListener for redirecting to Edit Record Page.
        ActionListener toEditPage = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshEditRecordPage();
                viewEachRecord.actionPerformed(e);
                return;
            }
        };

        // ----------------------------------------------------------------------------------------------------
        // Button for redirecting to Edit Record Page.
        JButton toEditPageBtn = new JButton("Edit Record");
        // Adjust font and color.
        toEditPageBtn.setFont(HealthDiary.BTN_FONT);
        toEditPageBtn.setForeground(HealthDiary.BTN_FG_COLOR);
        toEditPageBtn.setBackground(HealthDiary.POSITIVE_COLOR);
        // Adjust action.
        toEditPageBtn.setActionCommand("Edit Record");
        toEditPageBtn.addActionListener(toEditPage);
        toEditPageBtn.setFocusable(false);
        toEditPageBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.BTN_FG_COLOR, 3, true),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        toEditPageBtn.setPreferredSize(new Dimension(130, 50));

        // ----------------------------------------------------------------------------------------------------
        // Button for redirecting to Delete Record Page.
        JButton toDeletePageBtn = new JButton("Delete Record");
        // Adjust font and color.
        toDeletePageBtn.setFont(HealthDiary.BTN_FONT);
        toDeletePageBtn.setForeground(HealthDiary.BTN_FG_COLOR);
        toDeletePageBtn.setBackground(HealthDiary.NEGATIVE_COLOR);
        // Adjust action.
        toDeletePageBtn.setActionCommand("Delete Record");
        toDeletePageBtn.addActionListener(viewEachRecord);
        toDeletePageBtn.setFocusable(false);
        toDeletePageBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.BTN_FG_COLOR, 3, true),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        toDeletePageBtn.setPreferredSize(new Dimension(130, 50));

        // ----------------------------------------------------------------------------------------------------
        // Panel for edit and delete buttons.
        JPanel editDeletePanel = new JPanel();
        editDeletePanel.setBackground(HealthDiary.THEME_BG_COLOR);
        editDeletePanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        // Set layout.
        GridLayout saveCancelLayout = new GridLayout();
        saveCancelLayout.setHgap(15);
        editDeletePanel.setLayout(saveCancelLayout);
        // Add button.
        editDeletePanel.add(toEditPageBtn);
        editDeletePanel.add(toDeletePageBtn);
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(10, 5, 10, 5);
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 9;
        gridBagC.gridx = 0;
        gridBagC.fill = GridBagConstraints.HORIZONTAL;
        viewEachRecordContent.add(editDeletePanel, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Add content to Content Page.
        viewEachRecord.add(viewEachRecordContent, BorderLayout.CENTER);
        addPage(viewEachRecord);
        return;
    }

    // Add Add Record Page.
    private void addAddRecord() {
        addRecord = new ContentPage(this, "Add Record", 2);
        addRecord.setLayout(new BorderLayout());

        // ----------------------------------------------------------------------------------------------------
        // Button for redirecting to View Each Record Page.
        JButton toViewAllPageBtn = new JButton(HealthDiary.UNI_RETURN_ARROW + " Back");
        // Adjust font and color.
        toViewAllPageBtn.setFont(HealthDiary.SMALL_BTN_FONT);
        toViewAllPageBtn.setForeground(HealthDiary.BTN_FG_COLOR);
        toViewAllPageBtn.setBackground(HealthDiary.BTN_BG_COLOR);
        // Adjust action.
        toViewAllPageBtn.setActionCommand("View All Record");
        toViewAllPageBtn.addActionListener(addRecord);
        toViewAllPageBtn.setFocusable(false);
        // Add to Content Page.
        addRecord.add(toViewAllPageBtn, BorderLayout.NORTH);

        // ----------------------------------------------------------------------------------------------------
        // For main content of Add Record (i.e., all input fields and options).
        JPanel addRecordContent = new JPanel(new GridBagLayout());
        // No reuse, new object for each component.
        GridBagConstraints gridBagC;
        addRecordContent.setBackground(HealthDiary.THEME_BG_COLOR);

        // ----------------------------------------------------------------------------------------------------
        // Heading for Add Record content.
        JLabel addRecordHeading = new JLabel("Add Record");
        // Adjust font and color.
        addRecordHeading.setFont(HealthDiary.MAIN_FONT);
        addRecordHeading.setForeground(HealthDiary.TEXT_COLOR);
        addRecordHeading.setHorizontalAlignment(JLabel.CENTER);
        // Add icon to heading.
        addRecordHeading.setIcon(new ImageIcon(
            HealthDiary.ADD_RECORD_ICON.getImage().getScaledInstance(
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
        addRecordContent.add(addRecordHeading, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Array of data input and message.
        addInputs = new JTextField[4];
        addMsgs = new JLabel[4];
        JLabel[] inputLb = new JLabel[4];

        for (int i = 0; i < 4; i++) {
            // ----------------------------------------------------------------------------------------------------
            // Label for input.
            inputLb[i] = new JLabel();
            // Adjust font and color.
            inputLb[i].setFont(HealthDiary.BTN_FONT);
            inputLb[i].setForeground(HealthDiary.TEXT_COLOR);
            inputLb[i].setHorizontalAlignment(JLabel.LEFT);
            inputLb[i].setMinimumSize(new Dimension(200, 30));
            inputLb[i].setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
            // Add to content.
            gridBagC = new GridBagConstraints();
            gridBagC.insets = new InsetsUIResource(5, 5, 2, 5);
            gridBagC.gridwidth = GridBagConstraints.REMAINDER;
            gridBagC.gridy = i * 3 + 1;
            gridBagC.gridx = 0;
            gridBagC.fill = GridBagConstraints.HORIZONTAL;
            addRecordContent.add(inputLb[i], gridBagC);

            // ----------------------------------------------------------------------------------------------------
            // Input field.
            addInputs[i] = new JTextField();
            // Adjust font and color.
            addInputs[i].setFont(HealthDiary.LB_FONT);
            addInputs[i].setForeground(HealthDiary.VALUE_FG_COLOR);
            addInputs[i].setHorizontalAlignment(JLabel.LEFT);
            addInputs[i].setPreferredSize(new Dimension(300, 30));
            addInputs[i].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)
            ));
            // Add to content.
            gridBagC = new GridBagConstraints();
            gridBagC.insets = new InsetsUIResource(0, 5, 1, 5);
            gridBagC.gridwidth = GridBagConstraints.REMAINDER;
            gridBagC.gridy = i * 3 + 2;
            gridBagC.gridx = 0;
            gridBagC.fill = GridBagConstraints.HORIZONTAL;
            addRecordContent.add(addInputs[i], gridBagC);

            // ----------------------------------------------------------------------------------------------------
            // Label for input error.
            addMsgs[i] = new JLabel();
            // Adjust font and color.
            addMsgs[i].setFont(HealthDiary.LB_FONT);
            addMsgs[i].setForeground(HealthDiary.NEGATIVE_COLOR);
            addMsgs[i].setBackground(HealthDiary.VALUE_BG_COLOR);
            addMsgs[i].setOpaque(true);
            addMsgs[i].setHorizontalAlignment(JLabel.CENTER);
            addMsgs[i].setMinimumSize(new Dimension(250, 30));
            addMsgs[i].setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
            // Add to content.
            gridBagC = new GridBagConstraints();
            gridBagC.insets = new InsetsUIResource(1, 5, 5, 5);
            gridBagC.gridwidth = GridBagConstraints.REMAINDER;
            gridBagC.gridy = i * 3 + 3;
            gridBagC.gridx = 0;
            gridBagC.fill = GridBagConstraints.HORIZONTAL;
            addRecordContent.add(addMsgs[i], gridBagC);
        }
        // Set label text.
        inputLb[0].setText("Date (e.g., 01/06/2022 12:00 pm):");
        inputLb[1].setText("Body Temperature (in " + HealthDiary.UNI_CELSIUS + "):");
        inputLb[2].setText("Height (in cm):");
        inputLb[3].setText("Weight (in kg):");

        // ----------------------------------------------------------------------------------------------------
        // Modified ActionListener for saving new record.
        ActionListener saveAdd = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!addRecordInList()) {
                    return;
                }
                
                writeRecordFile(true);
                // Set current index to the last index.
                recordIndex = allRecord.size() - 1;
                refreshEachRecordPage();

                // Check index after refresh View All Record Page.
                HealthRecord tempRecord = allRecord.get(recordIndex);
                refreshAllRecordPage();
                recordIndex = allRecord.indexOf(tempRecord);

                addRecord.actionPerformed(e);
                return;
            }
        };

        // ----------------------------------------------------------------------------------------------------
        // Button for save and cancel record adding.
        JButton[] saveCancelAddBtn = new JButton[2];
        for (int i = 0; i < 2; i++) {
            saveCancelAddBtn[i] = new JButton();
            // Adjust font and color.
            saveCancelAddBtn[i].setFont(HealthDiary.BTN_FONT);
            saveCancelAddBtn[i].setForeground(HealthDiary.BTN_FG_COLOR);
            saveCancelAddBtn[i].setBackground(HealthDiary.POSITIVE_COLOR);
            saveCancelAddBtn[i].setFocusable(false);
            saveCancelAddBtn[i].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(HealthDiary.BTN_FG_COLOR, 3, true),
                BorderFactory.createEmptyBorder(10, 25, 10, 25)
            ));
            saveCancelAddBtn[i].setMinimumSize(new Dimension(100, 50));
        }
        // Adjust action and text.
        saveCancelAddBtn[0].setActionCommand("View Each Record");
        saveCancelAddBtn[0].addActionListener(saveAdd);
        saveCancelAddBtn[0].setText("Add Now");
        saveCancelAddBtn[0].setBackground(HealthDiary.POSITIVE_COLOR);
        saveCancelAddBtn[1].setActionCommand("View All Record");
        saveCancelAddBtn[1].addActionListener(addRecord);
        saveCancelAddBtn[1].setText("Cancel Add");
        saveCancelAddBtn[1].setBackground(HealthDiary.NEGATIVE_COLOR);

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
        saveCancelPanel.add(saveCancelAddBtn[0]);
        saveCancelPanel.add(saveCancelAddBtn[1]);
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 5, 10, 5);
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 13;
        gridBagC.gridx = 0;
        gridBagC.fill = GridBagConstraints.HORIZONTAL;
        addRecordContent.add(saveCancelPanel, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Add content to Content Page.
        addRecord.add(addRecordContent, BorderLayout.CENTER);
        addPage(addRecord);
        return;
    }

    // Add Edit Record Page.
    private void addEditRecord() {
        editRecord = new ContentPage(this, "Edit Record", 3);
        editRecord.setLayout(new BorderLayout());

        // ----------------------------------------------------------------------------------------------------
        // Button for redirecting to View Each Record Page.
        JButton toViewPageBtn = new JButton(HealthDiary.UNI_RETURN_ARROW + " Back");
        // Adjust font and color.
        toViewPageBtn.setFont(HealthDiary.SMALL_BTN_FONT);
        toViewPageBtn.setForeground(HealthDiary.BTN_FG_COLOR);
        toViewPageBtn.setBackground(HealthDiary.BTN_BG_COLOR);
        // Adjust action.
        toViewPageBtn.setActionCommand("View Each Record");
        toViewPageBtn.addActionListener(editRecord);
        toViewPageBtn.setFocusable(false);
        // Add to Content Page.
        editRecord.add(toViewPageBtn, BorderLayout.NORTH);

        // ----------------------------------------------------------------------------------------------------
        // For main content of Edit Record (i.e., all input fields and options).
        JPanel editRecordContent = new JPanel(new GridBagLayout());
        // No reuse, new object for each component.
        GridBagConstraints gridBagC;
        editRecordContent.setBackground(HealthDiary.THEME_BG_COLOR);

        // ----------------------------------------------------------------------------------------------------
        // Heading for Edit Record content.
        JLabel editRecordHeading = new JLabel("Edit Record");
        // Adjust font and color.
        editRecordHeading.setFont(HealthDiary.MAIN_FONT);
        editRecordHeading.setForeground(HealthDiary.TEXT_COLOR);
        editRecordHeading.setHorizontalAlignment(JLabel.CENTER);
        // Add icon to heading.
        editRecordHeading.setIcon(new ImageIcon(
            HealthDiary.EDIT_RECORD_ICON.getImage().getScaledInstance(
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
        editRecordContent.add(editRecordHeading, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Array of data input and message.
        editInputs = new JTextField[4];
        editMsgs = new JLabel[4];
        JLabel[] inputLb = new JLabel[4];

        for (int i = 0; i < 4; i++) {
            // ----------------------------------------------------------------------------------------------------
            // Label for input.
            inputLb[i] = new JLabel();
            // Adjust font and color.
            inputLb[i].setFont(HealthDiary.BTN_FONT);
            inputLb[i].setForeground(HealthDiary.TEXT_COLOR);
            inputLb[i].setHorizontalAlignment(JLabel.LEFT);
            inputLb[i].setMinimumSize(new Dimension(200, 30));
            inputLb[i].setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
            // Add to content.
            gridBagC = new GridBagConstraints();
            gridBagC.insets = new InsetsUIResource(5, 5, 2, 5);
            gridBagC.gridwidth = GridBagConstraints.REMAINDER;
            gridBagC.gridy = i * 3 + 1;
            gridBagC.gridx = 0;
            gridBagC.fill = GridBagConstraints.HORIZONTAL;
            editRecordContent.add(inputLb[i], gridBagC);

            // ----------------------------------------------------------------------------------------------------
            // Input field.
            editInputs[i] = new JTextField();
            // Adjust font and color.
            editInputs[i].setFont(HealthDiary.LB_FONT);
            editInputs[i].setForeground(HealthDiary.VALUE_FG_COLOR);
            editInputs[i].setHorizontalAlignment(JLabel.LEFT);
            editInputs[i].setPreferredSize(new Dimension(300, 30));
            editInputs[i].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)
            ));
            // Add to content.
            gridBagC = new GridBagConstraints();
            gridBagC.insets = new InsetsUIResource(0, 5, 1, 5);
            gridBagC.gridwidth = GridBagConstraints.REMAINDER;
            gridBagC.gridy = i * 3 + 2;
            gridBagC.gridx = 0;
            gridBagC.fill = GridBagConstraints.HORIZONTAL;
            editRecordContent.add(editInputs[i], gridBagC);

            // ----------------------------------------------------------------------------------------------------
            // Label for input error.
            editMsgs[i] = new JLabel();
            // Adjust font and color.
            editMsgs[i].setFont(HealthDiary.LB_FONT);
            editMsgs[i].setForeground(HealthDiary.NEGATIVE_COLOR);
            editMsgs[i].setBackground(HealthDiary.VALUE_BG_COLOR);
            editMsgs[i].setOpaque(true);
            editMsgs[i].setHorizontalAlignment(JLabel.CENTER);
            editMsgs[i].setMinimumSize(new Dimension(250, 30));
            editMsgs[i].setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
            // Add to content.
            gridBagC = new GridBagConstraints();
            gridBagC.insets = new InsetsUIResource(1, 5, 5, 5);
            gridBagC.gridwidth = GridBagConstraints.REMAINDER;
            gridBagC.gridy = i * 3 + 3;
            gridBagC.gridx = 0;
            gridBagC.fill = GridBagConstraints.HORIZONTAL;
            editRecordContent.add(editMsgs[i], gridBagC);
        }
        // Set label text.
        inputLb[0].setText("Date (e.g., 01/06/2022 12:00 pm):");
        inputLb[1].setText("Body Temperature (in " + HealthDiary.UNI_CELSIUS + "):");
        inputLb[2].setText("Height (in cm):");
        inputLb[3].setText("Weight (in kg):");

        // ----------------------------------------------------------------------------------------------------
        // Modified ActionListener for edit saving.
        ActionListener saveEdit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!updateRecordInList()) {
                    return;
                }
                
                writeRecordFile(false);
                refreshEachRecordPage();

                // Check index after refresh View All Record Page.
                HealthRecord tempRecord = allRecord.get(recordIndex);
                refreshAllRecordPage();
                recordIndex = allRecord.indexOf(tempRecord);

                editRecord.actionPerformed(e);
                return;
            }
        };

        // ----------------------------------------------------------------------------------------------------
        // Button for save and cancel record editing.
        JButton[] saveCancelEditBtn = new JButton[2];
        for (int i = 0; i < 2; i++) {
            saveCancelEditBtn[i] = new JButton();
            // Adjust font and color.
            saveCancelEditBtn[i].setFont(HealthDiary.BTN_FONT);
            saveCancelEditBtn[i].setForeground(HealthDiary.BTN_FG_COLOR);
            saveCancelEditBtn[i].setBackground(HealthDiary.POSITIVE_COLOR);
            // Adjust action.
            saveCancelEditBtn[i].setActionCommand("View Each Record");
            saveCancelEditBtn[i].setFocusable(false);
            saveCancelEditBtn[i].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(HealthDiary.BTN_FG_COLOR, 3, true),
                BorderFactory.createEmptyBorder(10, 25, 10, 25)
            ));
            saveCancelEditBtn[i].setMinimumSize(new Dimension(100, 50));
        }
        // Adjust action and text.
        saveCancelEditBtn[0].addActionListener(saveEdit);
        saveCancelEditBtn[0].setText("Save Edit");
        saveCancelEditBtn[0].setBackground(HealthDiary.POSITIVE_COLOR);
        saveCancelEditBtn[1].addActionListener(editRecord);
        saveCancelEditBtn[1].setText("Cancel Edit");
        saveCancelEditBtn[1].setBackground(HealthDiary.NEGATIVE_COLOR);

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
        saveCancelPanel.add(saveCancelEditBtn[0]);
        saveCancelPanel.add(saveCancelEditBtn[1]);
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 5, 10, 5);
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 13;
        gridBagC.gridx = 0;
        gridBagC.fill = GridBagConstraints.HORIZONTAL;
        editRecordContent.add(saveCancelPanel, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Add content to Content Page.
        editRecord.add(editRecordContent, BorderLayout.CENTER);
        addPage(editRecord);
        return;
    }

    // Add Delete Record Page.
    private void addDeleteRecord() {
        deleteRecord = new ContentPage(this, "Delete Record", 4);
        deleteRecord.setLayout(new BorderLayout());

        // ----------------------------------------------------------------------------------------------------
        // Button for redirecting to View Each Record Page.
        JButton toViewPageBtn = new JButton(HealthDiary.UNI_RETURN_ARROW + " Back");
        // Adjust font and color.
        toViewPageBtn.setFont(HealthDiary.SMALL_BTN_FONT);
        toViewPageBtn.setForeground(HealthDiary.BTN_FG_COLOR);
        toViewPageBtn.setBackground(HealthDiary.BTN_BG_COLOR);
        // Adjust action.
        toViewPageBtn.setActionCommand("View Each Record");
        toViewPageBtn.addActionListener(deleteRecord);
        toViewPageBtn.setFocusable(false);
        // Add to Content Page.
        deleteRecord.add(toViewPageBtn, BorderLayout.NORTH);

        // ----------------------------------------------------------------------------------------------------
        // For main content of Delete Record (i.e., all input fields and options).
        JPanel delRecordContent = new JPanel(new GridBagLayout());
        // No reuse, new object for each component.
        GridBagConstraints gridBagC;
        delRecordContent.setBackground(HealthDiary.THEME_BG_COLOR);

        // ----------------------------------------------------------------------------------------------------
        // Heading for Delete Record content.
        JLabel delRecordHeading = new JLabel("Delete Record?!");
        // Adjust font and color.
        delRecordHeading.setFont(HealthDiary.MAIN_FONT);
        delRecordHeading.setForeground(HealthDiary.TEXT_COLOR);
        delRecordHeading.setHorizontalAlignment(JLabel.CENTER);
        // Add icon to heading.
        delRecordHeading.setIcon(new ImageIcon(
            HealthDiary.DELETE_RECORD_ICON.getImage().getScaledInstance(
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
        delRecordContent.add(delRecordHeading, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Label for delete warning.
        JLabel delWarning = new JLabel("* This Action Cannot be Reversed! *");
        // Adjust font and color.
        delWarning.setFont(HealthDiary.LB_FONT);
        delWarning.setForeground(HealthDiary.NEGATIVE_COLOR);
        delWarning.setBackground(HealthDiary.VALUE_BG_COLOR);
        delWarning.setOpaque(true);
        delWarning.setHorizontalAlignment(JLabel.CENTER);
        delWarning.setPreferredSize(new Dimension(120, 30));
        delWarning.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(2, 5, 5, 5);
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 1;
        gridBagC.gridx = 0;
        gridBagC.fill = GridBagConstraints.HORIZONTAL;
        delRecordContent.add(delWarning, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Modified ActionListener for edit saving.
        ActionListener confirmDel = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteRecordInList();
                
                writeRecordFile(false);

                refreshAllRecordPage();
                recordIndex = allRecord.size() - 1;

                deleteRecord.actionPerformed(e);
                return;
            }
        };

        // ----------------------------------------------------------------------------------------------------
        // Button for save and cancel record editing.
        JButton[] confirmCancelDelBtn = new JButton[2];
        for (int i = 0; i < 2; i++) {
            confirmCancelDelBtn[i] = new JButton();
            // Adjust font and color.
            confirmCancelDelBtn[i].setFont(HealthDiary.BTN_FONT);
            confirmCancelDelBtn[i].setForeground(HealthDiary.BTN_FG_COLOR);
            confirmCancelDelBtn[i].setBackground(HealthDiary.POSITIVE_COLOR);
            confirmCancelDelBtn[i].setFocusable(false);
            confirmCancelDelBtn[i].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(HealthDiary.BTN_FG_COLOR, 3, true),
                BorderFactory.createEmptyBorder(10, 25, 10, 25)
            ));
            confirmCancelDelBtn[i].setMinimumSize(new Dimension(100, 50));
        }
        // Adjust action and text.
        confirmCancelDelBtn[0].setActionCommand("View All Record");
        confirmCancelDelBtn[0].addActionListener(confirmDel);
        confirmCancelDelBtn[0].setText("Confirm");
        confirmCancelDelBtn[0].setBackground(HealthDiary.POSITIVE_COLOR);
        confirmCancelDelBtn[1].setActionCommand("View Each Record");
        confirmCancelDelBtn[1].addActionListener(deleteRecord);
        confirmCancelDelBtn[1].setText("Cancel");
        confirmCancelDelBtn[1].setBackground(HealthDiary.NEGATIVE_COLOR);

        // ----------------------------------------------------------------------------------------------------
        // Panel for save and cancel buttons.
        JPanel confirmCancelPanel = new JPanel();
        confirmCancelPanel.setBackground(HealthDiary.THEME_BG_COLOR);
        confirmCancelPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        // Set layout.
        GridLayout saveCancelLayout = new GridLayout();
        saveCancelLayout.setHgap(15);
        confirmCancelPanel.setLayout(saveCancelLayout);
        // Add button.
        confirmCancelPanel.add(confirmCancelDelBtn[0]);
        confirmCancelPanel.add(confirmCancelDelBtn[1]);
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 5, 10, 5);
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 2;
        gridBagC.gridx = 0;
        gridBagC.fill = GridBagConstraints.HORIZONTAL;
        delRecordContent.add(confirmCancelPanel, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Add content to Content Page.
        deleteRecord.add(delRecordContent, BorderLayout.CENTER);
        addPage(deleteRecord);
        return;
    }

    // Add new Health Record to list.
    private boolean addRecordInList() {
        boolean acceptInput = true;

        addInputs[0].setText(addInputs[0].getText().strip());
        // Valid datetime.
        if (validateDateTime(addInputs[0].getText())) {
            addMsgs[0].setText("");
            addInputs[0].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
            ));
        }
        // Invalid datetime.
        else {
            addMsgs[0].setText("* Follow format: dd/MM/yyyy hh:mm aa *");
            addInputs[0].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(HealthDiary.NEGATIVE_COLOR, 2),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
            ));
            acceptInput = false;
        }

        addInputs[1].setText(addInputs[1].getText().strip());
        // Valid body temperature.
        if (validateBodyTemp(addInputs[1].getText())) {
            addMsgs[1].setText("");
            addInputs[1].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
            ));
        }
        // Invalid body temperature.
        else {
            addMsgs[1].setText("* Valid temperature: 25.0 < x < 50.0 *");
            addInputs[1].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(HealthDiary.NEGATIVE_COLOR, 2),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
            ));
            acceptInput = false;
        }

        addInputs[2].setText(addInputs[2].getText().strip());
        // Valid height.
        if (validateHeight(addInputs[2].getText())) {
            addMsgs[2].setText("");
            addInputs[2].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
            ));
        }
        // Invalid height.
        else {
            addMsgs[2].setText("* Valid height: 50.0 < x < 250.0 *");
            addInputs[2].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(HealthDiary.NEGATIVE_COLOR, 2),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
            ));
            acceptInput = false;
        }

        addInputs[3].setText(addInputs[3].getText().strip());
        // Valid weight.
        if (validateWeight(addInputs[3].getText())) {
            addMsgs[3].setText("");
            addInputs[3].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
            ));
        }
        // Invalid weight.
        else {
            addMsgs[3].setText("* Valid weight: 5.0 < x < 500.0 *");
            addInputs[3].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(HealthDiary.NEGATIVE_COLOR, 2),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
            ));
            acceptInput = false;
        }

        // Store data.
        if (acceptInput) {
            HealthRecord tempRecord = new HealthRecord();

            tempRecord.setDateTime(addInputs[0].getText());
            tempRecord.setBodyTemp(Double.parseDouble(addInputs[1].getText()));
            tempRecord.setHeight(Double.parseDouble(addInputs[2].getText()));
            tempRecord.setWeight(Double.parseDouble(addInputs[3].getText()));

            allRecord.add(tempRecord);
        }
        return acceptInput;
    }

    // Update Health Record in list.
    private boolean updateRecordInList() {
        boolean acceptInput = true;

        editInputs[0].setText(editInputs[0].getText().strip());
        // Valid datetime.
        if (validateDateTime(editInputs[0].getText())) {
            editMsgs[0].setText("");
            editInputs[0].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
            ));
        }
        // Invalid datetime.
        else {
            editMsgs[0].setText("* Follow format: dd/MM/yyyy hh:mm aa *");
            editInputs[0].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(HealthDiary.NEGATIVE_COLOR, 2),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
            ));
            acceptInput = false;
        }

        editInputs[1].setText(editInputs[1].getText().strip());
        // Valid body temperature.
        if (validateBodyTemp(editInputs[1].getText())) {
            editMsgs[1].setText("");
            editInputs[1].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
            ));
        }
        // Invalid body temperature.
        else {
            editMsgs[1].setText("* Valid temperature: 25.0 < x < 50.0 *");
            editInputs[1].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(HealthDiary.NEGATIVE_COLOR, 2),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
            ));
            acceptInput = false;
        }

        editInputs[2].setText(editInputs[2].getText().strip());
        // Valid height.
        if (validateHeight(editInputs[2].getText())) {
            editMsgs[2].setText("");
            editInputs[2].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
            ));
        }
        // Invalid height.
        else {
            editMsgs[2].setText("* Valid height: 50.0 < x < 250.0 *");
            editInputs[2].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(HealthDiary.NEGATIVE_COLOR, 2),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
            ));
            acceptInput = false;
        }

        editInputs[3].setText(editInputs[3].getText().strip());
        // Valid weight.
        if (validateWeight(editInputs[3].getText())) {
            editMsgs[3].setText("");
            editInputs[3].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
            ));
        }
        // Invalid weight.
        else {
            editMsgs[3].setText("* Valid weight: 5.0 < x < 500.0 *");
            editInputs[3].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(HealthDiary.NEGATIVE_COLOR, 2),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
            ));
            acceptInput = false;
        }

        // Store data.
        if (acceptInput) {
            allRecord.get(recordIndex).setDateTime(editInputs[0].getText());
            allRecord.get(recordIndex).setBodyTemp(Double.parseDouble(editInputs[1].getText()));
            allRecord.get(recordIndex).setHeight(Double.parseDouble(editInputs[2].getText()));
            allRecord.get(recordIndex).setWeight(Double.parseDouble(editInputs[3].getText()));
        }
        return acceptInput;
    }

    // Delete Health Record from list.
    private void deleteRecordInList() {
        allRecord.remove(recordIndex);
        return;
    }

    // Refresh records with sorting in View All Record Page.
    protected void refreshAllRecordPage() {
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
        // Set date.
        recordValues[0].setText(allRecord.get(recordIndex).getDateTimeStr());
        // Set body temperature.
        recordValues[1].setText(
            HealthRecord.VALUE_FORMAT.format(allRecord.get(recordIndex).getBodyTemp()) +
            " " + HealthDiary.UNI_CELSIUS
        );
        // Set height.
        recordValues[2].setText(
            HealthRecord.VALUE_FORMAT.format(allRecord.get(recordIndex).getHeight()) + " cm"
        );
        // Set weight.
        recordValues[3].setText(
            HealthRecord.VALUE_FORMAT.format(allRecord.get(recordIndex).getWeight()) + " kg"
        );
        // Set BMI.
        recordValues[4].setText(
            HealthRecord.VALUE_FORMAT.format(allRecord.get(recordIndex).getBMI())
        );
        // Set BMI status.
        recordValues[5].setText(allRecord.get(recordIndex).getStatusStr());
        // Set health advice.
        adviceValue.setText(allRecord.get(recordIndex).getHealthAdvice());
        return;
    }

    // Refresh Add Record Page for adding new Health Record.
    private void refreshAddRecordPage() {
        HealthRecord tempRecord = new HealthRecord();
        addInputs[0].setText(tempRecord.getDateTimeStr());
        addInputs[0].setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Set body temperature.
        addInputs[1].setText(
            HealthRecord.VALUE_FORMAT.format(tempRecord.getBodyTemp())
        );
        addInputs[1].setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Set height.
        addInputs[2].setText(
            HealthRecord.VALUE_FORMAT.format(tempRecord.getHeight())
        );
        addInputs[2].setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Set weight.
        addInputs[3].setText(
            HealthRecord.VALUE_FORMAT.format(tempRecord.getWeight())
        );
        addInputs[3].setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        addMsgs[0].setText("");
        addMsgs[1].setText("");
        addMsgs[2].setText("");
        addMsgs[3].setText("");
        return;
    }

    // Refresh Edit Record Page for editing selected record.
    private void refreshEditRecordPage() {
        editInputs[0].setText(allRecord.get(recordIndex).getDateTimeStr());
        editInputs[0].setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Set body temperature.
        editInputs[1].setText(
            HealthRecord.VALUE_FORMAT.format(allRecord.get(recordIndex).getBodyTemp())
        );
        editInputs[1].setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Set height.
        editInputs[2].setText(
            HealthRecord.VALUE_FORMAT.format(allRecord.get(recordIndex).getHeight())
        );
        editInputs[2].setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        // Set weight.
        editInputs[3].setText(
            HealthRecord.VALUE_FORMAT.format(allRecord.get(recordIndex).getWeight())
        );
        editInputs[3].setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
            BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        editMsgs[0].setText("");
        editMsgs[1].setText("");
        editMsgs[2].setText("");
        editMsgs[3].setText("");
        return;
    }
}
