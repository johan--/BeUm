package net.cosmiclion.opms.main.library.model;

import net.cosmiclion.opms.main.mylibrary.model.BookData;
import net.cosmiclion.opms.main.mylibrary.model.BooksResponse;

import java.util.ArrayList;
import java.util.List;

public class BooksResponseMapper {

    public static BookDomain transform(BookData itemData) {
        BookDomain itemDomain = null;
        if (itemData != null) {
            itemDomain = new BookDomain();
            itemDomain.setBookid(itemData.getBookid());
            itemDomain.setTitle(itemData.getTitle());
            itemDomain.setCover(itemData.getCover());
        }
        return itemDomain;
    }

    public static List<BookDomain> transform(BooksResponse itemsResponses) {
        List<BookDomain> quickMenuItems = new ArrayList<BookDomain>();
        List<BookData> quickMenuItemsResponse = itemsResponses.getItems();
        BookDomain itemDomain;

        for (BookData qMenuItem : quickMenuItemsResponse) {
            itemDomain = transform(qMenuItem);
            if (itemDomain != null) {
                quickMenuItems.add(itemDomain);
            }
        }
        return quickMenuItems;
    }
}
