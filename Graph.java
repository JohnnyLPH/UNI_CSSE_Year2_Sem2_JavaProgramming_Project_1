import javax.swing.JPanel;

// TMF2954 Java Programming Group Project
// Group Name: LUCKY
// Group Members:
//     - LAU PIKK HEANG (75359)
//     - YUKI CHUNG PEI YING (77237)
//     - ANDREA ANG XIAO XUAN (73347)
// Health Diary App: Graph.
// For drawing graph in Graph Panel.
import java.util.ArrayList;

import java.awt.*;


public class Graph extends JPanel {
    // Constant for generating graph.
    public static final String[] ALL_MONTHS = {
        "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };

    private static final Color POINT_COLOR = HealthDiary.VALUE_BG_COLOR;
    private static final Color INVALID_POINT_COLOR = HealthDiary.NEGATIVE_COLOR;
    private static final Color LINE_COLOR = HealthDiary.THEME_BG_COLOR;
    private static final Color HATCH_COLOR = HealthDiary.BTN_FG_COLOR;
    private static final Color GRID_COLOR = Color.GRAY;

    private static final int GRAPH_POINT_WIDTH = 8;
    // For drawing line between points.
    private static final Stroke GRAPH_STROKE = new BasicStroke(GRAPH_POINT_WIDTH / 3);
    // No. of hatch marks for Y axis.
    private static final int Y_TOTAL_HATCH = 10;

    // BMI data to plot point.
    private ArrayList<Double> bmiValues;
    private int startMonth, padding;

    // Constructor.
    public Graph(ArrayList<Double> bmiValues, int startMonth) {
        this.bmiValues = bmiValues;
        this.setBackground(HealthDiary.BLACK_BG_COLOR);
        this.setFont(HealthDiary.GRAPH_LB_FONT);

        // Starting month.
        this.startMonth = startMonth;

        // Padding.
        this.padding = HealthDiary.WINDOW_WIDTH / 50 * 50 / bmiValues.size();
        return;
    }

    // Method.
    // Get maximum value of BMI from given list.
    private double getMaxBMI() {
        double maxBMI = -1;
        for (double bValue: bmiValues) {
            // Valid value.
            if (bValue >= 0) {
                if (maxBMI < 0 || maxBMI < bValue) {
                    maxBMI = bValue;
                }
            }
        }
        // Minimum 5.
        return (maxBMI < 5) ? 5: maxBMI;
    }

    // Get maximum value of BMI from given list.
    private double getMinBMI() {
        double minBMI = -1;
        for (double bValue: bmiValues) {
            // Valid value.
            if (bValue >= 0) {
                if (minBMI < 0 || minBMI > bValue) {
                    minBMI = bValue;
                }
            }
        }
        // Minimum 0.
        return (minBMI < 0 || getMaxBMI() - minBMI < 5) ? getMaxBMI() - 5: minBMI;
    }

    // Draw automatically when Graph object is created.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // https://docs.oracle.com/javase/7/docs/api/java/awt/Graphics2D.html
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Calculate scale (distance between each point in each axis).
        // X axis: 2 padding, each for left and right, extra 1 padding for label.
        double xScale = ((double) getWidth() - 3 * padding) / (bmiValues.size() - 1);
        // Y axis: 2 padding, each for top and bottom, extra 1 padding for label.
        double yScale = ((double) getHeight() - 3 * padding) / (getMaxBMI() - getMinBMI());

        // Coordinate (x, y) of BMI value.
        ArrayList<Point> bmiPoint = new ArrayList<Point>();
        double tempBMI = (getMinBMI() >= 0) ? getMinBMI(): 0;
        for (int i = 0; i < bmiValues.size(); i++) {
            // Valid value.
            if (bmiValues.get(i) >= 0) {
                tempBMI = bmiValues.get(i);
            }
            // Try to get average between next valid value (estimation of BMI).
            else {
                for (int j = i + 1; j < bmiValues.size(); j++) {
                    // Valid value.
                    if (bmiValues.get(j) >= 0) {
                        tempBMI = (bmiValues.get(j) + tempBMI) / 2;
                        break;
                    }
                }
            }

            // X Coordinate. Count in padding for label (left).
            int xCoord = (int) (i * xScale + 2 * padding);
            // Y Coordinate.
            int yCoord = (int) ((getMaxBMI() - tempBMI) * yScale + padding);
            bmiPoint.add(new Point(xCoord, yCoord));
        }

        // Create hatch marks and grid lines for Y axis.
        for (int i = 0; i < Y_TOTAL_HATCH; i++) {
            // Length of hatch mark.
            int x0 = padding * 2;
            int x1 = padding * 2 + GRAPH_POINT_WIDTH;
            // Position of hatch mark on Y axis.
            // 10 hatch mark, divide available height by 9.
            int tempY = i * (getHeight() - padding * 3) / (Y_TOTAL_HATCH - 1) + padding * 2;
            int y0 = getHeight() - tempY;
            int y1 = y0;
            
            if (bmiValues.size() > 0) {
                g2.setColor(GRID_COLOR);
                // Draw grid line.
                g2.drawLine(padding * 2 + GRAPH_POINT_WIDTH + 1, y0, getWidth() - padding, y1);
                g2.setColor(HATCH_COLOR);
                // Label for each hatch mark.
                String yLabel = HealthRecord.VALUE_FORMAT.format(
                    (getMaxBMI() - getMinBMI()) / (Y_TOTAL_HATCH - 1) * i + getMinBMI()
                );
                FontMetrics metrics = g2.getFontMetrics();
                // Get width of label.
                int labelWidth = metrics.stringWidth(yLabel);
                // Draw label.
                g2.drawString(yLabel, x0 - labelWidth - 8, y0 + (metrics.getHeight() / 2) - 3);
            }
            // Draw hatch mark.
            g2.drawLine(x0, y0, x1, y1);
        }

        // Create hatch marks and grid lines for X axis.
        for (int i = 0; i < bmiValues.size(); i++) {
            // Position of hatch mark on X axis.
            // Divide available width by size - 1.
            int x0 = i * (getWidth() - padding * 3) / (bmiValues.size() - 1) + padding * 2;
            int x1 = x0;
            // Length of hatch mark.
            int y0 = getHeight() - padding * 2;
            int y1 = y0 - GRAPH_POINT_WIDTH;

            if (bmiValues.size() > 1 && (i % ((int) ((bmiValues.size() / 20.0)) + 1)) == 0) {
                g2.setColor(GRID_COLOR);
                // Draw grid line.
                g2.drawLine(x0, getHeight() - padding * 2 - GRAPH_POINT_WIDTH - 1, x1, padding);
                g2.setColor(HATCH_COLOR);
                // Label for each hatch mark.
                String xLabel = ALL_MONTHS[(startMonth - 1 + i) % ALL_MONTHS.length];
                FontMetrics metrics = g2.getFontMetrics();
                // Get width of label.
                int labelWidth = metrics.stringWidth(xLabel);
                // Draw label.
                g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        g2.setColor(HATCH_COLOR);
        // Create Y axis.
        g2.drawLine(padding * 2, getHeight() - padding * 2, padding * 2, padding);
        // Create X axis. 
        g2.drawLine(padding * 2, getHeight() - padding * 2, getWidth() - padding, getHeight() - padding * 2);

        Stroke oldStroke = g2.getStroke();
        // Draw line between points.
        g2.setColor(LINE_COLOR);
        g2.setStroke(GRAPH_STROKE);
        // Total lines = Total points - 1.
        for (int i = 0; i < bmiPoint.size() - 1; i++) {
            // Start point.
            int x1 = bmiPoint.get(i).x;
            int y1 = bmiPoint.get(i).y;
            // End point.
            int x2 = bmiPoint.get(i + 1).x;
            int y2 = bmiPoint.get(i + 1).y;
            // Draw line.
            g2.drawLine(x1, y1, x2, y2);
        }

        // Draw BMI points.
        g2.setStroke(oldStroke);
        for (int i = 0; i < bmiPoint.size(); i++) {
            // Check if this is a valid BMI or an estimation.
            if (bmiValues.get(i) >= 0) {
                g2.setColor(POINT_COLOR);
            }
            else {
                g2.setColor(INVALID_POINT_COLOR);
            }

            int x = bmiPoint.get(i).x - GRAPH_POINT_WIDTH / 2;
            int y = bmiPoint.get(i).y - GRAPH_POINT_WIDTH / 2;
            int ovalW = GRAPH_POINT_WIDTH;
            int ovalH = GRAPH_POINT_WIDTH;
            // Draw point.
            g2.fillOval(x, y, ovalW, ovalH);
        }

        // Valid and invalid point indicators.
        String validIndicator = "Monthly Average", invalidIndicator = "System Estimation";
        FontMetrics metrics = g2.getFontMetrics();
        // Get width of label.
        int invalidLbWidth = metrics.stringWidth(invalidIndicator);
        // Draw label.
        g2.setColor(HATCH_COLOR);
        g2.drawString(
            validIndicator, padding * 2, getHeight() - metrics.getHeight()
        );
        g2.drawString(
            invalidIndicator, getWidth() - padding - invalidLbWidth, getHeight() - metrics.getHeight()
        );
        // Draw line.
        int lineHeight = getHeight() - metrics.getHeight() - 4, lineWidth = padding - 3;
        g2.setStroke(GRAPH_STROKE);
        g2.setColor(LINE_COLOR);
        g2.drawLine(
            padding,
            lineHeight,
            padding + lineWidth, lineHeight
        );
        g2.drawLine(
            getWidth() - invalidLbWidth - padding * 2,
            lineHeight,
            getWidth() - invalidLbWidth - padding * 2 + lineWidth, lineHeight
        );

        // Draw valid and invalid point.
        g2.setStroke(oldStroke);
        g2.setColor(POINT_COLOR);
        g2.fillOval(
            padding + lineWidth / 2 - GRAPH_POINT_WIDTH / 2,
            lineHeight - GRAPH_POINT_WIDTH / 2,
            GRAPH_POINT_WIDTH,
            GRAPH_POINT_WIDTH
        );
        g2.setColor(INVALID_POINT_COLOR);
        g2.fillOval(
            getWidth() - padding * 2 - invalidLbWidth + lineWidth / 2 - GRAPH_POINT_WIDTH / 2,
            lineHeight - GRAPH_POINT_WIDTH / 2,
            GRAPH_POINT_WIDTH,
            GRAPH_POINT_WIDTH
        );
        return;
    }
}
