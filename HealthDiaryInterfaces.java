// TMF2954 Java Programming Group Project
// Group Name: LUCKY
// Group Members:
//     - LAU PIKK HEANG (75359)
//     - YUKI CHUNG PEI YING (77237)
//     - ANDREA ANG XIAO XUAN (73347)
// Health Diary App: Interfaces.

interface HealthRecordFeature {
    public boolean setHeight(double newHeight);
    public boolean setWeight(double newWeight);
    public boolean setBodyTemp(double newTemp);
    public void setDateTime(long newTime);
    public boolean setDateTime(String newTime);
    public double getHeight();
    public double getWeight();
    public double getBodyTemp();
    public double getBMI();
    public long getDateTimeLong();
    public String getDateTimeStr();
    public int getStatusIndex();
    public String getStatusStr();
    public String getHealthAdvice();
}

interface UserProfileFeature {
    public void setName(String newName);
    public boolean setGender(int genderIndex);
    public boolean setBloodType(int bloodTypeIndex);
    public void setHealthHistory(String newHealthHistory);
    public String getName();
    public String getGenderStr();
    public int getGenderIndex();
    public String getBloodTypeStr();
    public int getBloodTypeIndex();
    public String getHealthHistory();
}

interface ContentPanelFeature {
    public int getTotalPage();
    public void addPage(ContentPage page);
    public void addPage(ContentPage page, String pageName);
    public void addPage(ContentPanel page, String pageName);
    public boolean addPage(ContentPage page, String pageName, int pageIndex);
    public boolean addPage(ContentPanel page, String pageName, int pageIndex);
    public boolean switchPage(int pageIndex);
    public boolean switchPage(String pageName);
    public boolean switchLastPanel();
}

interface ContentPageFeature {
    public boolean setPageName(String pageName);
    public boolean setPageIndex(int pageIndex);
    public ContentPanel getContentPanel();
    public String getPageName();
    public int getPageIndex();
    public void display();
}
