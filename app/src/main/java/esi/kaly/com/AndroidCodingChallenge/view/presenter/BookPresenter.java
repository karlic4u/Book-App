package esi.kaly.com.AndroidCodingChallenge.view.presenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import esi.kaly.com.AndroidCodingChallenge.model.Book;
import esi.kaly.com.AndroidCodingChallenge.rest.RestCallback;
import esi.kaly.com.AndroidCodingChallenge.rest.RestController;

public class BookPresenter implements RestCallback {

    private ArrayList<Book> mBookList;

    private BookView mBookView;

    public BookPresenter(BookView bookView) {
        this.mBookView = bookView;
    }

    public void fetchBooks() {
        RestController.getInstance().fetchBooks(this);
    }

    @Override
    public void onBooksRetrieved(ArrayList<Book> bookList) {
        RestController.getInstance().setRestCallback(null);

        if (bookList == null || bookList.isEmpty()) return;
        this.mBookList = bookList;
        Log.i("books retrieved -> \n ", Arrays.toString(bookList.toArray()));
        if (mBookView != null) {
            mBookView.onBooksRetrieved();
        }
    }

    @Override
    public void onError(String error) {
        RestController.getInstance().setRestCallback(null);

    }

    public ArrayList<Book> getBookList() {
        return mBookList;
    }

    public interface BookView {
        void onBooksRetrieved();
        void onError(String error);
    }
}
