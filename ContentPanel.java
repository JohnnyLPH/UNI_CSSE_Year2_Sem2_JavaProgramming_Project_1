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

    // Constructor.
    public ContentPanel() {
        setLayout(new CardLayout());
        setBackground(HealthDiary.BLACK_BG_COLOR);
        // This is a top Content Panel.
        this.lastContentPanel = null;
        this.allPages = new ArrayList<String>();
        return;
    }

    public ContentPanel(ContentPanel lastContentPanel) {
        setLayout(new CardLayout());
        setBackground(HealthDiary.BLACK_BG_COLOR);
        // This Content Panel is within another Content Panel.
        this.lastContentPanel = lastContentPanel;
        this.allPages = new ArrayList<String>();
    }

    // Method.
    // Add Content Page or Content Panel.
    public void addPage(JPanel page, String pageName) {
        page.setName(pageName);
        super.add(page, pageName);
        this.allPages.add(pageName);
        return;
    }

    // Add Content Page or Content Panel at specific index in allPages [0 <= x <= size].
    public boolean addPage(JPanel page, String pageName, int pageIndex) {
        // Invalid index.
        if (pageIndex < 0 || pageIndex > this.allPages.size()) {
            return false;
        }

        page.setName(pageName);
        super.add(page, pageName);
        this.allPages.add(pageIndex, pageName);
        return true;
    }

    // Remove Content Page or Content Panel.
    // public boolean removePage(String pageName) {
    //     if (!this.allPages.contains(pageName)) {
    //         return false;
    //     }

    //     // Get objects of all pages in current Content Panel.
    //     Component[] components = this.getComponents();

    //     // Find the object of the page to remove.
    //     for(int i = 0; i < components.length; i++) {
    //         if (components[i].getName().equals(pageName)) {
    //             components[i].setVisible(false);
    //             ((CardLayout) this.getLayout()).removeLayoutComponent(components[i]);

    //             this.allPages.remove(pageName);
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    // Switch page using page index in allPages [0 <= x < size)].
    public boolean switchPage(int pageIndex) {
        // Invalid index.
        if (pageIndex < 0 || pageIndex > this.allPages.size() - 1) {
            return false;
        }

        ((CardLayout) this.getLayout()).show(this, this.allPages.get(pageIndex));
        return true;
    }

    // Switch page using page name.
    public boolean switchPage(String pageName) {
        if (!this.allPages.contains(pageName)) {
            return false;
        }

        ((CardLayout) this.getLayout()).show(this, pageName);
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
