package net.cosmiclion.opms.main.purchase.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import net.cosmiclion.opms.login.source.local.DbHelper;
import net.cosmiclion.opms.main.purchase.model.BookPurchaseData;
import net.cosmiclion.opms.main.purchase.source.PurchaseDataSource;
import net.cosmiclion.opms.utils.Debug;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static com.google.common.base.Preconditions.checkNotNull;
import static net.cosmiclion.opms.main.purchase.source.local.PurchaseLocalContract.Book.TABLE_NAME;

public class PurchaseLocalDataSource implements PurchaseDataSource {

    private String TAG = getClass().getSimpleName();
    private static PurchaseLocalDataSource INSTANCE;

    private DbHelper mDbHelper;
    private SQLiteDatabase mDb;
    private String[] projection = {
            PurchaseLocalContract.Book.COLUMN_PRODUCT_ID,
            PurchaseLocalContract.Book.COLUMN_FILE_NAME,
            PurchaseLocalContract.Book.COLUMN_FILE_TYPE,
            PurchaseLocalContract.Book.COLUMN_COVER_IMAGE,

            PurchaseLocalContract.Book.COLUMN_PURCHASE_TIME,
            PurchaseLocalContract.Book.COLUMN_IS_DOWNLOADED,
            PurchaseLocalContract.Book.COLUMN_LAST_PAGE,
            PurchaseLocalContract.Book.COLUMN_READ_DATE
    };

    private PurchaseLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        mDbHelper = new DbHelper(context);
        mDb = mDbHelper.getWritableDatabase();
    }

    public static PurchaseLocalDataSource getInstance(@NonNull Context context) {
        Debug.i("TAG", "PurchaseLocalDataSource getInstance");
        if (INSTANCE == null) {
            INSTANCE = new PurchaseLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getBooksPurchaseResponse(@NonNull String token, @NonNull LoadPurchaseCallback callback) {

    }

    @Override
    public void doSaveBooks(List<BookPurchaseData> books) {
        Debug.i(TAG, "===doSaveBooks local");
        try {
            checkNotNull(books);
            mDb.beginTransaction();
            int is_downloaded = 0; // => default: 0, downloaded = 1
            int last_page = 1; // => default: 0
            String read_date = null; // => default: null = not read,
            ContentValues values = new ContentValues();
            for (BookPurchaseData book : books) {
                values.put(PurchaseLocalContract.Book.COLUMN_PRODUCT_ID, book.product_id);
                values.put(PurchaseLocalContract.Book.COLUMN_PRODUCT_TITLE, book.product_title);
                values.put(PurchaseLocalContract.Book.COLUMN_PRODUCT_AUTHOR, book.product_author);
                values.put(PurchaseLocalContract.Book.COLUMN_FILE_TYPE, book.filetype);

                values.put(PurchaseLocalContract.Book.COLUMN_FILE_NAME, book.filename);
                values.put(PurchaseLocalContract.Book.COLUMN_PRODUCT_TRANSLATOR, book.product_translator);
                values.put(PurchaseLocalContract.Book.COLUMN_COVER_IMAGE, book.cover_image2);
                values.put(PurchaseLocalContract.Book.COLUMN_PURCHASE_TIME, book.purchase_time);

                values.put(PurchaseLocalContract.Book.COLUMN_IS_DOWNLOADED, is_downloaded);
                values.put(PurchaseLocalContract.Book.COLUMN_LAST_PAGE, last_page);
                values.put(PurchaseLocalContract.Book.COLUMN_READ_DATE, read_date);

                //CONFLICT_IGNORE
                mDb.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
            }
            mDb.setTransactionSuccessful();
//            Debug.i(TAG, "===doSaveBooks insert List<BookPurchaseData> Success " + getBooksCount());
            Debug.i(TAG, "===doSaveBooks Getbooks " + getBooks(GET_RECENT_BOOK_PURCHASE).size());
            Debug.i(TAG, "===doSaveBooks Getbooks " + getBooks(GET_RECENT_BOOK_READ).size());

        } catch (IllegalStateException e) {
            // Send to analytics, log etc
            Debug.i(TAG, "===doSaveBooks local err " + e.getMessage());
        } finally {
            mDb.endTransaction();
        }
    }

    public static final int GET_RECENT_BOOK_PURCHASE = 1;
    public static final int GET_RECENT_BOOK_READ = 0;

    public List<BookPurchaseData> getBooks(int getBooksType) {
        List<BookPurchaseData> books = new ArrayList<>();
        try {
            String selection = null; // get all books
            String[] selectionArgs = null;
            String isDownloaded = "1";
            String orderBy = null;
            if (getBooksType == GET_RECENT_BOOK_READ) {
                selectionArgs = new String[]{isDownloaded};
                selection = PurchaseLocalContract.Book.QUERRY_GET_RECENT_READ_BOOKS;
                orderBy = PurchaseLocalContract.Book.COLUMN_ID + " DESC LIMIT 5";
            } else if (getBooksType == GET_RECENT_BOOK_PURCHASE) {
                selectionArgs = null;
                //                selectionArgs = new String[]{isDownloaded};
//                selection = PurchaseLocalContract.Book.QUERRY_GET_RECENT_PURCHASE_BOOKS;
                selection = null;
                orderBy = PurchaseLocalContract.Book.COLUMN_ID + " DESC LIMIT 2";
            }

            Cursor c = mDb.query(
                    PurchaseLocalContract.Book.TABLE_NAME, projection, selection, selectionArgs, null, null, orderBy);

            if (c != null && c.getCount() > 0) {
                while (c.moveToNext()) {
                    String product_id = c.getString(
                            c.getColumnIndexOrThrow(PurchaseLocalContract.Book.COLUMN_PRODUCT_ID));
                    String filename = c.getString(
                            c.getColumnIndexOrThrow(PurchaseLocalContract.Book.COLUMN_FILE_NAME));
                    int filetype = c.getInt(
                            c.getColumnIndexOrThrow(PurchaseLocalContract.Book.COLUMN_FILE_TYPE));
                    String cover_image = c.getString(
                            c.getColumnIndexOrThrow(PurchaseLocalContract.Book.COLUMN_COVER_IMAGE));

                    String purchase_time = c.getString(
                            c.getColumnIndexOrThrow(PurchaseLocalContract.Book.COLUMN_PURCHASE_TIME));
                    int is_downloaded = c.getInt(
                            c.getColumnIndexOrThrow(PurchaseLocalContract.Book.COLUMN_IS_DOWNLOADED));
                    int last_page = c.getInt(
                            c.getColumnIndexOrThrow(PurchaseLocalContract.Book.COLUMN_LAST_PAGE));
                    String read_date = c.getString(
                            c.getColumnIndexOrThrow(PurchaseLocalContract.Book.COLUMN_READ_DATE));

                    BookPurchaseData book = new BookPurchaseData();
                    book.product_id = product_id;
                    book.filename = filename;
                    book.filetype = filetype;
                    book.cover_image1 = cover_image;

                    book.purchase_time = purchase_time;
                    book.is_downloaded = is_downloaded;
                    book.last_page = last_page;
                    book.read_date = read_date;
                    books.add(book);
                }
            }
            if (c != null) {
                c.close();
            }
        } catch (IllegalStateException e) {
            // Send to analytics, log etc
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public BookPurchaseData getBook(@NonNull String bookId) {
        try {
            String selection = PurchaseLocalContract.Book.COLUMN_PRODUCT_ID + " LIKE ?";
            String[] selectionArgs = {bookId};

            Cursor c = mDb.query(
                    PurchaseLocalContract.Book.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

            BookPurchaseData book = null;

            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                String product_id = c.getString(
                        c.getColumnIndexOrThrow(PurchaseLocalContract.Book.COLUMN_PRODUCT_ID));
                String filename = c.getString(
                        c.getColumnIndexOrThrow(PurchaseLocalContract.Book.COLUMN_FILE_NAME));
                int filetype = c.getInt(
                        c.getColumnIndexOrThrow(PurchaseLocalContract.Book.COLUMN_FILE_TYPE));
                String cover_image = c.getString(
                        c.getColumnIndexOrThrow(PurchaseLocalContract.Book.COLUMN_COVER_IMAGE));

                String purchase_time = c.getString(
                        c.getColumnIndexOrThrow(PurchaseLocalContract.Book.COLUMN_PURCHASE_TIME));
                int is_downloaded = c.getInt(
                        c.getColumnIndexOrThrow(PurchaseLocalContract.Book.COLUMN_IS_DOWNLOADED));
                int last_page = c.getInt(
                        c.getColumnIndexOrThrow(PurchaseLocalContract.Book.COLUMN_LAST_PAGE));
                String read_date = c.getString(
                        c.getColumnIndexOrThrow(PurchaseLocalContract.Book.COLUMN_READ_DATE));

                book = new BookPurchaseData();
                book.product_id = product_id;
                book.filename = filename;
                book.filetype = filetype;
                book.cover_image1 = cover_image;

                book.purchase_time = purchase_time;
                book.is_downloaded = is_downloaded;
                book.last_page = last_page;
                book.read_date = read_date;
            }
            if (c != null) {
                c.close();
            }

            return book;
        } catch (IllegalStateException e) {
            // Send to analytics, log etc
        }
        return null;
    }

    public void doUpdateBookDownloaded(@NonNull String bookId) {
        try {
            ContentValues values = new ContentValues();
            values.put(PurchaseLocalContract.Book.COLUMN_IS_DOWNLOADED, 1);

            String selection = PurchaseLocalContract.Book.COLUMN_PRODUCT_ID + " LIKE ?";
            String[] selectionArgs = {bookId};

            mDb.update(PurchaseLocalContract.Book.TABLE_NAME, values, selection, selectionArgs);
        } catch (IllegalStateException e) {
            // Send to analytics, log etc
        }
    }

    public void doUpdateBookReaded(@NonNull String bookId) {
        Debug.i(TAG, "===doUpdateBookReaded local");
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Calendar cal = Calendar.getInstance(TimeZone.getDefault());
            String currentDay = dateFormat.format(cal.getTime());
            Debug.i(TAG, "currentDay =" + currentDay);
            ContentValues values = new ContentValues();
            values.put(PurchaseLocalContract.Book.COLUMN_READ_DATE, currentDay);

            String selection = PurchaseLocalContract.Book.COLUMN_PRODUCT_ID + " = ?";
            String[] selectionArgs = {bookId};

            mDb.update(PurchaseLocalContract.Book.TABLE_NAME, values, selection, selectionArgs);
        } catch (IllegalStateException e) {
            // Send to analytics, log etc
        }
    }

//    public int getBooksCount() {
//        Cursor c = null;
//        try {
//            String query = "select count(*) from BookPurchase  ";
//            c = mDb.rawQuery(query, null);
//            if (c.moveToFirst()) {
//                return c.getInt(0);
//            }
//            return 0;
//        } finally {
//            if (c != null) {
//                c.close();
//            }
//        }
//    }

}
