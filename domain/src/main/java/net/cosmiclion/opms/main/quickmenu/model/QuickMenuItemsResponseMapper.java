package net.cosmiclion.opms.main.quickmenu.model;

import java.util.ArrayList;
import java.util.List;

public class QuickMenuItemsResponseMapper {

    public static QuickMenuItem transform(QMenuItem qMenuItem) {
        QuickMenuItem quickMenuItem = null;
        if (qMenuItem != null) {
            quickMenuItem = new QuickMenuItem();
            quickMenuItem.setBookid(qMenuItem.getBookid());
            quickMenuItem.setTitle(qMenuItem.getTitle());
            quickMenuItem.setCover(qMenuItem.getCover());
        }
        return quickMenuItem;
    }

    public static List<QuickMenuItem> transform(QuickMenuItemsResponse itemsResponses) {
        List<QuickMenuItem> quickMenuItems = new ArrayList<QuickMenuItem>();
        List<QMenuItem> quickMenuItemsResponse = itemsResponses.getItems();
        QuickMenuItem quickMenuItem;

        for (QMenuItem qMenuItem : quickMenuItemsResponse) {
            quickMenuItem = transform(qMenuItem);
            if (quickMenuItem != null) {
                quickMenuItems.add(quickMenuItem);
            }
        }
        return quickMenuItems;
    }
}
