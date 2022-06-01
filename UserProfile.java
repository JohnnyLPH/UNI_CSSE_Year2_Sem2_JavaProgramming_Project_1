// TMF2954 Java Programming Group Project
// Group Name: LUCKY
// Group Members:
//     - LAU PIKK HEANG (75359)
//     - YUKI CHUNG PEI YING (77237)
//     - ANDREA ANG XIAO XUAN (73347)
// Health Diary App: User Profile.
// To be used in Profile Panel.


public class UserProfile {
    public static final String[] ALL_GENDER = {"Male", "Female"};
    public static final String[] ALL_BLOOD_TYPE = {"O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"};
    private String name, healthHistory;
    private int gender, bloodType;

    // Constructor.
    public UserProfile() {
        name = "Unknown";
        gender = bloodType = 0;
        healthHistory = "None";
        return;
    }

    // Setter.
    public void setName(String newName) {
        name = (newName.length() > 0) ? newName: "Unknown";
        return;
    }

    public boolean setGender(int genderIndex) {
        if (genderIndex < 0 || genderIndex > ALL_GENDER.length - 1) {
            return false;
        }
        gender = genderIndex;
        return true;
    }

    public boolean setBloodType(int bloodTypeIndex) {
        if (bloodTypeIndex < 0 || bloodTypeIndex > ALL_BLOOD_TYPE.length - 1) {
            return false;
        }
        bloodType = bloodTypeIndex;
        return true;
    }

    public void setHealthHistory(String newHealthHistory) {
        healthHistory = (newHealthHistory.length() > 0) ? newHealthHistory: "None";
        return;
    }

    // Getter.
    public String getName() {
        return name;
    }

    public String getGenderStr() {
        return ALL_GENDER[gender];
    }

    public int getGenderIndex() {
        return gender;
    }

    public String getBloodTypeStr() {
        return ALL_BLOOD_TYPE[bloodType];
    }

    public int getBloodTypeIndex() {
        return bloodType;
    }

    public String getHealthHistory() {
        return healthHistory;
    }
}
