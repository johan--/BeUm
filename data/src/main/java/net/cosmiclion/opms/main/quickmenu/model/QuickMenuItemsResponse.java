package net.cosmiclion.opms.main.quickmenu.model;

import java.util.List;

/**
 * Created by longpham on 10/26/2016.
 */
public class QuickMenuItemsResponse {
    private List<QMenuItem> items;

    public QuickMenuItemsResponse(List<QMenuItem> items){
        this.items = items;
    }

    public List<QMenuItem> getItems() {
        return items;
    }
}
