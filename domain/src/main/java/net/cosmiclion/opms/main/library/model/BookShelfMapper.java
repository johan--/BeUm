package net.cosmiclion.opms.main.library.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.cosmiclion.opms.utils.Debug;

import java.util.ArrayList;
import java.util.List;

public class BookShelfMapper {
    public static BookShelfDomain transform(String jsonBookData) {
        BookShelfDomain domain = new BookShelfDomain();
        if (jsonBookData != null) {
            BookShelfData bookData = new Gson().fromJson(jsonBookData, BookShelfData.class);
            domain.bs_id = bookData.bs_id;
            domain.bs_book_id = bookData.bs_book_id;
            domain.product_id = bookData.product_id;
            domain.product_title = bookData.product_title;

            domain.product_author = bookData.product_author;
            domain.product_translator = bookData.product_translator;
            domain.cover_image1 = bookData.cover_image1;
            domain.cover_image2 = bookData.cover_image2;

            domain.cover_image3 = bookData.cover_image3;
            domain.cover_image4 = bookData.cover_image4;
            domain.filename = bookData.filename;
            domain.filetype = bookData.filetype;
        }
        return domain;
    }

    public static BookShelfDomain transform(BookShelfData data) {
        BookShelfDomain domain = new BookShelfDomain();
        if (data != null) {
            domain.bs_id = data.bs_id;
            domain.bs_book_id = data.bs_book_id;
            domain.product_id = data.product_id;
            domain.product_title = data.product_title;

            domain.product_author = data.product_author;
            domain.product_translator = data.product_translator;
            domain.cover_image1 = data.cover_image1;
            domain.cover_image2 = data.cover_image2;

            domain.cover_image3 = data.cover_image3;
            domain.cover_image4 = data.cover_image4;
            domain.filename = data.filename;
            domain.filetype = data.filetype;
        }
        return domain;
    }

    public static List<BookShelfDomain> transformList(String jsonData) {
        List<BookShelfDomain> booksDomain = new ArrayList<BookShelfDomain>();
        List<BookShelfData> booksData = new GsonBuilder().setPrettyPrinting().create()
                .fromJson(jsonData, new TypeToken<List<BookShelfData>>() {
                }.getType());
        BookShelfDomain itemDomain;
        Debug.i("TAG_TAG", "==========hhhhhhhhh");
//        Debug.i("TAG_TAG", "bs_name=" + booksData.get(0).product_title
//                + " - bs_id=" + booksData.get(0).bs_id);
        if (booksData == null) {
            return new ArrayList<>(0);
        }
        for (BookShelfData item : booksData) {
            itemDomain = transform(item);
            if (itemDomain != null) {
                booksDomain.add(itemDomain);
            }
        }
        return booksDomain;
    }
}
