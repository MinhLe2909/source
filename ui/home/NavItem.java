package tamhoang.ldpro4.ui.home;

/* loaded from: classes.dex */
public class NavItem {
    private int resIcons;
    private String subtitle;
    private String title;

    public NavItem(String title2, String subtitle2, int resIcons2) {
        this.title = title2;
        this.subtitle = subtitle2;
        this.resIcons = resIcons2;
    }

    public int getResIcons() {
        return this.resIcons;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public String getTitle() {
        return this.title;
    }

    public void setResIcons(int resIcons2) {
        this.resIcons = resIcons2;
    }

    public void setSubtitle(String subtitle2) {
        this.subtitle = subtitle2;
    }

    public void setTitle(String title2) {
        this.title = title2;
    }
}
