// TMF2954 Java Programming Group Project
// Group Name: LUCKY
// Group Members:
//     - LAU PIKK HEANG (75359)
//     - YUKI CHUNG PEI YING (77237)
//     - ANDREA ANG XIAO XUAN (73347)
// Health Diary App: Health Record.
// To be used in Record Panel and Graph Panel.
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class HealthRecord {
    // Sorting criteria and order.
    public static final String[] SORT_CRITERIA = {"Date", "Weight", "BMI"};
    public static final String[] SORT_ORDER = {"Ascend", "Descend"};

    // Underweight (Below 18.5), Healthy (18.5 - 24.9), Overweight (25.0 - 29.9), Obese (30.0 and above).
    public static final String[] BMI_STATUS = {"Underweight", "Healthy", "Overweight", "Obese"};

    // Decimal Format (for displaying 1 decimal point).
    public static final DecimalFormat VALUE_FORMAT = new DecimalFormat("0.0");

    // Height in cm, Weight in kg, Temperature in Celsius.
    // BMI formula = weight in kg / (height in m ^ 2).
    private double height, weight, bodyTemp, bmi;
    // Refer: https://www.tutorialspoint.com/java/java_date_time.htm
    private Date dateTime;
    private SimpleDateFormat dateFormat;

    // Constructor.
    public HealthRecord() {
        height = 175;
        weight = 65;
        bodyTemp = 37;
        updateBMI();
        dateTime = new Date();
        // Date Format (e.g., 01/06/2022 12:00 PM).
        dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
        return;
    }

    // Setter.
    public boolean setHeight(double newHeight) {
        if (newHeight < 50 || newHeight > 250) {
            return false;
        }
        height = newHeight;
        return updateBMI();
    }

    public boolean setWeight(double newWeight) {
        if (newWeight < 5 || newWeight > 500) {
            return false;
        }
        weight = newWeight;
        return updateBMI();
    }

    public boolean setBodyTemp(double newTemp) {
        if (newTemp < 25 || newTemp > 50) {
            return false;
        }
        bodyTemp = newTemp;
        return true;
    }

    public void setDateTime(long newTime) {
        dateTime.setTime(newTime);
        return;
    }

    public boolean setDateTime(String newTime) {
        // Date Format (e.g., 01/06/2022 12:00 PM).
        try {
            dateTime = dateFormat.parse(newTime);
            return true;
        }
        catch (Exception errorMsg) {
            System.out.println(errorMsg);
            return false;
        }
    }

    // Getter.
    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public double getBodyTemp() {
        return bodyTemp;
    }

    public double getBMI() {
        return bmi;
    }

    public long getDateTimeLong() {
        return dateTime.getTime();
    }

    public String getDateTimeStr() {
        return dateFormat.format(dateTime);
    }

    // Method.
    // Update BMI value with new height or weight.
    private boolean updateBMI() {
        try {
            // BMI formula = weight in kg / (height in m ^ 2).
            bmi = weight / ((height / 100) * (height / 100));
            // Round value to 1 decimal point to change precision.
            bmi = Math.round(bmi * 10) / 10.0;
            return true;
        }
        catch (Exception errorMsg) {
            System.out.println(errorMsg);
            return false;
        }
    }

    // Get status index of current BMI.
    public int getStatusIndex() {
        // Underweight (Below 18.5), Healthy (18.5 - 24.9), Overweight (25.0 - 29.9), Obese (30.0 and above).
        if (bmi < 18.50) {
            return 0;
        }
        else if (bmi < 25.00) {
            return 1;
        }
        else if (bmi < 30.00) {
            return 2;
        }
        return 3;
    }

    // Get status string of current BMI.
    public String getStatusStr() {
        // Underweight (Below 18.5), Healthy (18.5 - 24.9), Overweight (25.0 - 29.9), Obese (30.0 and above).
        return BMI_STATUS[getStatusIndex()];
    }

    // Get health advice based on current BMI.
    public String getHealthAdvice() {
        int statusIndex = getStatusIndex();
        // Underweight, need to gain weight.
        if (statusIndex == 0) {

        }
        return "Testing Testing";
    }
}
