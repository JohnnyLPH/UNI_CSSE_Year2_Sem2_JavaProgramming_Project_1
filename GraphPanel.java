// TMF2954 Java Programming Group Project
// Group Name: LUCKY
// Group Members:
//     - LAU PIKK HEANG (75359)
//     - YUKI CHUNG PEI YING (77237)
//     - ANDREA ANG XIAO XUAN (73347)
// Health Diary App: Graph Panel.
// Contain Monthly Report Page.
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import java.text.SimpleDateFormat;

import java.awt.*;

import javax.swing.*;
import javax.swing.plaf.InsetsUIResource;


public class GraphPanel extends ContentPanel {
    // Content Page.
    private ContentPage monthlyReport;
    // List of Health Record.
    private ArrayList<HealthRecord> allRecord;
    // Label for date range value.
    private JLabel[] dateRangeValues;
    // Panel for displaying line graph.
    private JPanel reportGraphContent;

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
            Collections.reverse(allRecord);
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
        dateRangeLb.setHorizontalAlignment(JLabel.RIGHT);
        dateRangeLb.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 5, 5, 5);
        gridBagC.gridy = 1;
        gridBagC.gridx = 0;
        headingContent.add(dateRangeLb, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Date range between label.
        JLabel rangeBetweenLb = new JLabel("to");
        // Adjust font and color.
        rangeBetweenLb.setFont(HealthDiary.BTN_FONT);
        rangeBetweenLb.setForeground(HealthDiary.TEXT_COLOR);
        rangeBetweenLb.setHorizontalAlignment(JLabel.CENTER);
        rangeBetweenLb.setBorder(BorderFactory.createEmptyBorder(0, 1, 0, 1));
        // Add to content.
        gridBagC = new GridBagConstraints();
        gridBagC.insets = new InsetsUIResource(5, 1, 5, 1);
        gridBagC.gridy = 1;
        gridBagC.gridx = 2;
        headingContent.add(rangeBetweenLb, gridBagC);

        // ----------------------------------------------------------------------------------------------------
        // Array for labels of Date range value.
        dateRangeValues = new JLabel[2];

        for (int i = 0; i < 2; i++) {
            // ----------------------------------------------------------------------------------------------------
            // Date range value.
            dateRangeValues[i] = new JLabel();
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
            gridBagC.gridx = i * 2 + 1;
            headingContent.add(dateRangeValues[i], gridBagC);
        }

        // ----------------------------------------------------------------------------------------------------
        // Panel for displaying the graph.
        reportGraphContent = new JPanel(new BorderLayout());
        reportGraphContent.setBackground(HealthDiary.BTN_BG_COLOR);
        reportGraphContent.setBorder(BorderFactory.createEmptyBorder());
        // Add to content.
        monthlyReportContent.add(reportGraphContent, BorderLayout.CENTER);

        // ----------------------------------------------------------------------------------------------------
        // Add content to Content Page.
        monthlyReport.add(monthlyReportContent, BorderLayout.CENTER);
        addPage(monthlyReport);
        return;
    }

    // Refresh graph in Monthly Report Page.
    protected void refreshMonthlyReportPage() {
        // Read file and sort record again in case there is insertion, deletion or update of Health Record.
        readSortRecordFile();
        
        // Remove all components in report graph content.
        reportGraphContent.removeAll();
        reportGraphContent.revalidate();
        reportGraphContent.repaint();

        HealthRecord tempRecord = new HealthRecord();
        SimpleDateFormat tempDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Count backwards from 11 months before until current month (e.g., July 2021 - June 2022).
        double[] bmiMonthTotal = new double[12];
        int[] bmiCount = new int[12];
        // Total no. of record within date range and their sum.
        double totalBMI = 0;
        int totalCount = 0;

        // Date Format in Health Record (e.g., 01/06/2022 12:00 pm).
        int endMonth = Integer.parseInt(tempRecord.getDateTimeStr().substring(3, 5));
        int endYear = Integer.parseInt(tempRecord.getDateTimeStr().substring(6, 10));
        int startMonth = endMonth - 11, startYear = endYear;
        // Start from last year.
        if (startMonth < 1) {
            startMonth = 12 + startMonth;
            startYear--;
        }

        // Set date range text.
        dateRangeValues[0].setText(Graph.ALL_MONTHS[startMonth - 1] + " " + startYear);
        dateRangeValues[1].setText(Graph.ALL_MONTHS[endMonth - 1] + " " + endYear);

        for (int i = 0; i < 12; i++) {
            // Set initial value to 0.
            bmiMonthTotal[i] = bmiCount[i] = 0;
        }

        int checkMonth, checkYear;
        String firstMonthDay;
        long compareDateA, compareDateB;

        // Store BMI value in 12 months.
        ArrayList<Double> bmiValueList = new ArrayList<Double>();
        // Check each record.
        for (int i = 0; i < allRecord.size(); i++) {
            // Check to see which month does the record fit in.
            for (int j = 0; j < 12; j++) {
                checkMonth = endMonth - j;
                checkYear = endYear;
                // Back to last year.
                if (checkMonth < 1) {
                    checkMonth = 12 + checkMonth;
                    checkYear--;
                }
                firstMonthDay = "01/";
                firstMonthDay += ((checkMonth < 10) ? "0" + checkMonth: checkMonth) + "/" + checkYear;

                try {
                    compareDateA = allRecord.get(i).getDateTimeLong();
                    compareDateB = tempDateFormat.parse(firstMonthDay).getTime();
                    // Within the month.
                    if (compareDateA >= compareDateB) {
                        // Add to each month count.
                        bmiMonthTotal[12 - 1 - j] += allRecord.get(i).getBMI();
                        bmiCount[12 - 1 - j]++;

                        // Add to list.
                        bmiValueList.add(0, allRecord.get(i).getBMI());

                        // Add to total count.
                        totalBMI += allRecord.get(i).getBMI();
                        totalCount++;
                        // System.out.println(allRecord.get(i).getDateTimeStr() + "->" + checkMonth + "/" + checkYear);
                        break;
                    }
                }
                catch (Exception errorMsg) {
                    System.out.println(errorMsg);
                }
            }
        }

        if (totalCount > 0) {
            // ----------------------------------------------------------------------------------------------------
            // Graph generation with BMI data.
            ArrayList<Double> tempArrayList = new ArrayList<Double>();
            for (int i = 0; i < 12; i++) {
                if (bmiCount[i] > 0) {
                    tempArrayList.add(bmiMonthTotal[i] / bmiCount[i]);
                }
                else {
                    tempArrayList.add(-1.0);
                }
                // System.out.println("Average BMI " + i + " = " + tempArrayList.get(i));
            }
            // Add to content.
            reportGraphContent.add(new Graph(tempArrayList, startMonth), BorderLayout.CENTER);

            // ----------------------------------------------------------------------------------------------------
            // Label for BMI Average.
            JLabel bmiAverageLb = new JLabel();
            String bmiAverageText = "Average BMI: " + HealthRecord.VALUE_FORMAT.format(totalBMI / totalCount);
            bmiAverageText += ", " + HealthRecord.BMI_STATUS[HealthRecord.getStatusIndex(totalBMI / totalCount)];
            bmiAverageLb.setText(bmiAverageText);
            // Adjust font and color.
            bmiAverageLb.setFont(HealthDiary.BTN_FONT);
            bmiAverageLb.setForeground(HealthDiary.TEXT_COLOR);
            bmiAverageLb.setHorizontalAlignment(JLabel.CENTER);
            // Adjust border.
            bmiAverageLb.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            // Add to content.
            reportGraphContent.add(bmiAverageLb, BorderLayout.SOUTH);
        }
        else {
            // ----------------------------------------------------------------------------------------------------
            // Label for message.
            JLabel noRecordMsg = new JLabel("No Record from " + Graph.ALL_MONTHS[startMonth - 1] + " " + startYear + " to " + Graph.ALL_MONTHS[endMonth - 1] + " " + endYear + " :(");
            // Adjust font and color.
            noRecordMsg.setFont(HealthDiary.BTN_FONT);
            noRecordMsg.setForeground(HealthDiary.TEXT_COLOR);
            noRecordMsg.setHorizontalAlignment(JLabel.CENTER);
            // Adjust border.
            noRecordMsg.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            // Add to content.
            reportGraphContent.add(noRecordMsg, BorderLayout.CENTER);
        }
        return;
    }
}
