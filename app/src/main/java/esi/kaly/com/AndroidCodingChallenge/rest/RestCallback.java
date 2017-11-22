package esi.kaly.com.AndroidCodingChallenge.rest;

import java.util.ArrayList;

import esi.kaly.com.AndroidCodingChallenge.model.Book;

public interface RestCallback {

    void onBooksRetrieved(ArrayList<Book> bookList);
    void onError(String error);
}
