// TMF2954 Java Programming Group Project
// Group Name: LUCKY
// Group Members:
//     - LAU PIKK HEANG (75359)
//     - YUKI CHUNG PEI YING (77237)
//     - ANDREA ANG XIAO XUAN (73347)
// Health Diary App: Health Record.
// To be used in Record Panel and Graph Panel.
import java.text.SimpleDateFormat;
import java.util.*;


public class HealthRecord {
    // Height in cm.
    // Weight in kg.
    // Temperature in Celcius.
    // BMI formula = weight in kg / (height in m ^ 2).
    private float height, weight, bodyTemp, bmi;
    // Refer: https://www.tutorialspoint.com/java/java_date_time.htm
    private Date dateTime;
    private SimpleDateFormat dateFormat;

    // Underweight (Below 18.5), Healthy (18.5 - 24.9), Overweight (25.0 - 29.9), Obese (30.0 and above).
    public static final String[] bmiStatus = {"Underweight", "Healthy", "Overweight", "Obese"};

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
    public boolean setHeight(float newHeight) {
        if (newHeight < 50 || newHeight > 250) {
            return false;
        }
        height = newHeight;
        return updateBMI();
    }

    public boolean setWeight(float newWeight) {
        if (newWeight < 5 || newWeight > 500) {
            return false;
        }
        weight = newWeight;
        return updateBMI();
    }

    public boolean setBodyTemp(float newTemp) {
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
    public float getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    public float getBodyTemp() {
        return bodyTemp;
    }

    public float getBMI() {
        return bmi;
    }

    public long getDateTimeLong() {
        return dateTime.getTime();
    }

    public String getDateTimeStr() {
        return dateFormat.format(dateTime);
    }

    // Method.
    // Update BMI value using set height and weight.
    private boolean updateBMI() {
        try {
            // BMI formula = weight in kg / (height in m ^ 2).
            bmi = weight / ((height / 100) * (height / 100));
            return true;
        }
        catch (Exception errorMsg) {
            System.out.println(errorMsg);
            return false;
        }
    }

    // Get status of current BMI.
    public String getStatus() {
        // Underweight (Below 18.5), Healthy (18.5 - 24.9), Overweight (25.0 - 29.9), Obese (30.0 and above).
        if (bmi < 18.5) {
            return bmiStatus[0];
        }
        else if (bmi < 25.0) {
            return bmiStatus[1];
        }
        else if (bmi < 30.0) {
            return bmiStatus[2];
        }
        return bmiStatus[3];
    }
}
