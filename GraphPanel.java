// TMF2954 Java Programming Group Project
// Group Name: LUCKY
// Group Members:
//     - LAU PIKK HEANG (75359)
//     - YUKI CHUNG PEI YING (77237)
//     - ANDREA ANG XIAO XUAN (73347)
// Health Diary App: Graph Panel.
// Contain Monthly Report Page (Default), Weekly Report Page, Yearly Report Page.
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import java.awt.*;

import javax.swing.*;
import javax.swing.plaf.InsetsUIResource;


public class GraphPanel extends ContentPanel {
    // Content Page.
    private ContentPage monthlyReport, weeklyReport, yearlyReport;
    // List of Health Record.
    private ArrayList<HealthRecord> allRecord;
    // Label for date range value.
    private JLabel[] dateRangeValues;

    // Constructor.
    public GraphPanel(ContentPanel lastContentPanel) {
        super(lastContentPanel);

        addMonthlyReport();
        refreshMonthlyReportPage();
        return;
    }

    // Method.
    // Read Health Record from file and sort by date. Called to obtain the latest record data before refreshing graph.
    private void readSortRecordFile() {
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

            // Use modified Comparator for Date.
            Comparator<HealthRecord> recordComparator = new Comparator<HealthRecord>() {
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
            Collections.sort(allRecord, recordComparator);
        }
        catch (Exception e) {
            System.out.println("Error with file in readSortRecordFile().");
        }
        return;
    }

    // Add Monthly Report Page.
    private void addMonthlyReport() {
        monthlyReport = new ContentPage(this, "Monthly Report", 0);
        monthlyReport.setLayout(new BorderLayout());

        // ----------------------------------------------------------------------------------------------------
        // Button for redirecting to Last Panel.
        JButton returnPanelBtn = new JButton(HealthDiary.UNI_RETURN_ARROW + " Back");
        // Adjust font and color.
        returnPanelBtn.setFont(HealthDiary.SMALL_BTN_FONT);
        returnPanelBtn.setForeground(HealthDiary.BTN_FG_COLOR);
        returnPanelBtn.setBackground(HealthDiary.BTN_BG_COLOR);
        // Adjust action.
        returnPanelBtn.setActionCommand("Last Panel");
        returnPanelBtn.addActionListener(monthlyReport);
        returnPanelBtn.setFocusable(false);
        // Add to Content Page.
        monthlyReport.add(returnPanelBtn, BorderLayout.NORTH);

        // ----------------------------------------------------------------------------------------------------
        // For main content of Monthly Report (i.e., heading, date range, graph).
        JPanel monthlyReportContent = new JPanel(new BorderLayout());
        monthlyReportContent.setBackground(HealthDiary.BLACK_BG_COLOR);

        // ----------------------------------------------------------------------------------------------------
        // Include the heading and date range.
        JPanel headingContent = new JPanel(new GridBagLayout());
        // No reuse, new object for each component.
        GridBagConstraints gridBagC;
        headingContent.setBackground(HealthDiary.THEME_BG_COLOR);
        monthlyReportContent.add(headingContent, BorderLayout.NORTH);

        // ----------------------------------------------------------------------------------------------------
        // Heading for View All Record.
        JLabel monthlyReportHeading = new JLabel("Graph Report (Monthly)");
        // Adjust font and color.
        monthlyReportHeading.setFont(HealthDiary.MAIN_FONT);
        monthlyReportHeading.setForeground(HealthDiary.TEXT_COLOR);
        monthlyReportHeading.setHorizontalAlignment(JLabel.CENTER);
        // Add icon to heading.
        monthlyReportHeading.setIcon(new ImageIcon(
            HealthDiary.GRAPH_ICON.getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH)
        ));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(10, 5, 5, 5);
        gridBagC.gridwidth = GridBagConstraints.REMAINDER;
        gridBagC.gridy = 0;
        gridBagC.gridx = 0;
        gridBagC.fill = GridBagConstraints.HORIZONTAL;
        headingContent.add(monthlyReportHeading, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Date range label.
        JLabel dateRangeLb = new JLabel("Date Range:");
        // Adjust font and color.
        dateRangeLb.setFont(HealthDiary.BTN_FONT);
        dateRangeLb.setForeground(HealthDiary.TEXT_COLOR);
        dateRangeLb.setHorizontalAlignment(JLabel.LEFT);
        dateRangeLb.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 5, 5, 5);
        gridBagC.gridy = 1;
        gridBagC.gridx = 0;
        headingContent.add(dateRangeLb, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Array for labels of Date range value.
        dateRangeValues = new JLabel[2];

        for (int i = 0; i < 2; i++) {
            // ----------------------------------------------------------------------------------------------------
            // Date range value.
            dateRangeValues[i] = new JLabel("June 2022");
            // Adjust font and color.
            dateRangeValues[i].setFont(HealthDiary.BTN_FONT);
            dateRangeValues[i].setForeground(HealthDiary.VALUE_FG_COLOR);
            dateRangeValues[i].setBackground(HealthDiary.VALUE_BG_COLOR);
            dateRangeValues[i].setOpaque(true);
            dateRangeValues[i].setHorizontalAlignment(JLabel.LEFT);
            dateRangeValues[i].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(HealthDiary.VALUE_FG_COLOR, 2),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
            ));
            // Add to content.
            gridBagC = new GridBagConstraints();
            gridBagC.insets = new InsetsUIResource(5, 5, 5, 5);
            gridBagC.gridy = 1;
            gridBagC.gridx = i + 1;
            headingContent.add(dateRangeValues[i], gridBagC);
        }

        // ----------------------------------------------------------------------------------------------------
        // Add content to Content Page.
        monthlyReport.add(monthlyReportContent, BorderLayout.CENTER);
        addPage(monthlyReport);
        return;
    }

    // Refresh graph in Monthly Report Page.
    public void refreshMonthlyReportPage() {
        // Read file and sort record again in case there is insertion, deletion or update of Health Record.
        readSortRecordFile();
        
        return;
    }

}
