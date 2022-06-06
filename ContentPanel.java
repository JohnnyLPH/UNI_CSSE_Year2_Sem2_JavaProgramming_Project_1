// TMF2954 Java Programming Group Project
// Group Name: LUCKY
// Group Members:
//     - LAU PIKK HEANG (75359)
//     - YUKI CHUNG PEI YING (77237)
//     - ANDREA ANG XIAO XUAN (73347)
// Health Diary App: Content Panel.
// Store content pages or other content panels using card layout.
import java.util.ArrayList;

import java.awt.*;

import javax.swing.*;


public class ContentPanel extends JPanel {
    // Upper Content Panel containing current Content Panel.
    private ContentPanel lastContentPanel;
    private ArrayList<String> allPages;
    private String currentPage;  // Keep track displayed page in current Content Panel.

    // Constructor.
    public ContentPanel() {
        setLayout(new CardLayout());
        setBackground(HealthDiary.BLACK_BG_COLOR);
        // This is a top Content Panel.
        this.lastContentPanel = null;
        this.allPages = new ArrayList<String>();
        this.currentPage = "";
        return;
    }

    public ContentPanel(ContentPanel lastContentPanel) {
        setLayout(new CardLayout());
        setBackground(HealthDiary.BLACK_BG_COLOR);
        // This Content Panel is within another Content Panel.
        this.lastContentPanel = lastContentPanel;
        this.allPages = new ArrayList<String>();
        this.currentPage = "";
        return;
    }

    // Method.
    // Get total page.
    public int getTotalPage() {
        return allPages.size();
    }

    // Add Content Page.
    public void addPage(ContentPage page) {
        super.add(page, page.getPageName());
        this.allPages.add(page.getPageIndex(), page.getPageName());
        // First added page.
        if (this.getTotalPage() == 1) {
            this.currentPage = page.getPageName();
        }
        return;
    }

    // Add Content Page with specified name.
    public void addPage(ContentPage page, String pageName) {
        page.setPageName(pageName);
        super.add(page, page.getPageName());
        this.allPages.add(page.getPageIndex(), page.getPageName());
        // First added page.
        if (this.getTotalPage() == 1) {
            this.currentPage = page.getPageName();
        }
        return;
    }

    // Add Content Panel with specified name.
    public void addPage(ContentPanel page, String pageName) {
        super.add(page, pageName);
        this.allPages.add(pageName);
        // First added page.
        if (this.getTotalPage() == 1) {
            this.currentPage = pageName;
        }
        return;
    }

    // Add Content Page with specified name and index.
    public boolean addPage(ContentPage page, String pageName, int pageIndex) {
        // Invalid index.
        if (pageIndex < 0 || pageIndex > this.allPages.size()) {
            return false;
        }

        page.setPageName(pageName);
        page.setPageIndex(pageIndex);
        super.add(page, page.getPageName());
        this.allPages.add(page.getPageIndex(), page.getPageName());
        // First added page.
        if (this.getTotalPage() == 1) {
            this.currentPage = page.getPageName();
        }
        return true;
    }

    // Add Content Panel with specified name and index.
    public boolean addPage(ContentPanel page, String pageName, int pageIndex) {
        // Invalid index.
        if (pageIndex < 0 || pageIndex > this.allPages.size()) {
            return false;
        }

        super.add(page, pageName);
        this.allPages.add(pageIndex, pageName);
        // First added page.
        if (this.getTotalPage() == 1) {
            this.currentPage = pageName;
        }
        return true;
    }

    // Switch page using page index in allPages [0 <= x < size)].
    public boolean switchPage(int pageIndex) {
        // Invalid index.
        if (pageIndex < 0 || pageIndex > this.allPages.size() - 1) {
            return false;
        }

        // Not displaying the page.
        if (this.currentPage.compareTo(this.allPages.get(pageIndex)) != 0) {
            ((CardLayout) this.getLayout()).show(this, this.allPages.get(pageIndex));
            this.currentPage = this.allPages.get(pageIndex);
        }
        return true;
    }

    // Switch page using page name.
    public boolean switchPage(String pageName) {
        if (!this.allPages.contains(pageName)) {
            return false;
        }

        // Not displaying the page.
        if (this.currentPage.compareTo(pageName) != 0) {
            ((CardLayout) this.getLayout()).show(this, pageName);
            this.currentPage = pageName;
        }
        return true;
    }

    // Switch to previous Content Panel if exist.
    public boolean switchLastPanel() {
        if (this.lastContentPanel == null) {
            return false;
        }

        // Switch to first page of previous Content Panel.
        return this.lastContentPanel.switchPage(0);
    }
}
