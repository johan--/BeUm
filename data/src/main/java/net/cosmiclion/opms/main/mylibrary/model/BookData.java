package net.cosmiclion.opms.main.mylibrary.model;

/**
 * Created by longpham on 10/26/2016.
 */
public class BookData {
    private String bookid;
    private String title;
    private String cover;

    public BookData(String bookid, String title, String cover) {
        this.bookid = bookid;
        this.title = title;
        this.cover = cover;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
