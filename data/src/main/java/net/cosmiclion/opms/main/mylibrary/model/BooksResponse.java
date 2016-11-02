package net.cosmiclion.opms.main.mylibrary.model;

import java.util.List;

/**
 * Created by longpham on 10/26/2016.
 */
public class BooksResponse {
    private List<BookData> items;

    public BooksResponse(List<BookData> items){
        this.items = items;
    }

    public List<BookData> getItems() {
        return items;
    }
}
