package net.cosmiclion.opms.main.library.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.cosmiclion.opms.utils.Debug;

import java.util.ArrayList;
import java.util.List;

public class BookLibraryMapper {
    public static BookLibraryDomain transform(String jsonBookData) {
        BookLibraryDomain domain = new BookLibraryDomain();
        if (jsonBookData != null) {
            BookLibraryData bookData = new Gson().fromJson(jsonBookData, BookLibraryData.class);
            domain.bs_id = bookData.bs_id;
            domain.member_id = bookData.member_id;
            domain.bs_name = bookData.bs_name;
            domain.bs_order = bookData.bs_order;
        }
        return domain;
    }

    public static BookLibraryDomain transform(BookLibraryData data) {
        BookLibraryDomain domain = new BookLibraryDomain();
        if (data != null) {
            domain.bs_id = data.bs_id;
            domain.member_id = data.member_id;
            domain.bs_name = data.bs_name;
            domain.bs_order = data.bs_order;
        }
        return domain;
    }

    public static List<BookLibraryDomain> transformList(String jsonData) {
        List<BookLibraryDomain> booksDomain = new ArrayList<BookLibraryDomain>();
        List<BookLibraryData> booksData = new GsonBuilder().setPrettyPrinting().create()
                .fromJson(jsonData, new TypeToken<List<BookLibraryData>>() {
                }.getType());
        BookLibraryDomain itemDomain;
        Debug.i("TAG_TAG", "==========hhhhhhhhh");
        Debug.i("TAG_TAG", "bs_name=" + booksData.get(0).bs_name + " - bs_id=" + booksData.get(0).bs_id);
        for (BookLibraryData item : booksData) {
            itemDomain = transform(item);
            if (itemDomain != null) {
                booksDomain.add(itemDomain);
            }
        }
        return booksDomain;
    }
}
