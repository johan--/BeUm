package net.cosmiclion.opms.main.library.dialog.model;

/**
 * Created by longpham on 10/27/2016.
 */
public class DialogParameter {
    private String title ;
    private String subTitle;
    private String hint;

    public DialogParameter(String title, String subTitle, String hint) {
        this.title = title;
        this.subTitle = subTitle;
        this.hint = hint;
    }

    public DialogParameter(String title, String subTitle) {
        this.title = title;
        this.subTitle = subTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
