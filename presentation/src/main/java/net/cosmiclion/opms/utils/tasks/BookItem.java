package net.cosmiclion.opms.utils.tasks;

/**
 * Created by longpham on 8/23/2016.
 */
public class BookItem {
    public int bookCode;
    public String title;
    public int imgRes;
    public String filePath;
    public int isPDF;

    public BookItem(int code, String title, int imgRes, String filePath, int isPDF) {
        bookCode = code;
        this.title = title;
        this.imgRes = imgRes;
        this.filePath = filePath;
        this.isPDF = isPDF;
    }

}