// TMF2954 Java Programming Group Project
// Group Name: LUCKY
// Group Members:
//     - LAU PIKK HEANG (75359)
//     - YUKI CHUNG PEI YING (77237)
//     - ANDREA ANG XIAO XUAN (73347)
// Health Diary App: Graph Panel.
// Contain Monthly Report Page (Default), Weekly Report Page, Yearly Report Page.
import java.awt.*;
import javax.swing.*;


public class GraphPanel extends ContentPanel {
    // Content Page.
    private ContentPage monthlyReport, weeklyReport, yearlyReport;

    // Constructor.
    public GraphPanel(ContentPanel lastContentPanel) {
        super(lastContentPanel);

        addMonthlyReport();
        return;
    }

    // Method.
    // Add Monthly Report Page.
    private void addMonthlyReport() {
        monthlyReport = new ContentPage(this);
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
        // Heading for Monthly Report.
        JLabel monthlyReportHeading = new JLabel("Monthly Report (Last 12 Months)");
        // Adjust font and color.
        monthlyReportHeading.setFont(HealthDiary.MAIN_FONT);
        monthlyReportHeading.setForeground(HealthDiary.TEXT_COLOR);

        monthlyReport.add(monthlyReportHeading, BorderLayout.CENTER);

        addPage(monthlyReport, "Monthly Report", 0);
        return;
    }
}
