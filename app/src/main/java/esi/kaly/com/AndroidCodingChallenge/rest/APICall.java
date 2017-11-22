package esi.kaly.com.AndroidCodingChallenge.rest;

import java.util.ArrayList;

import esi.kaly.com.AndroidCodingChallenge.model.Book;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APICall {

    @GET("books.json")
    Call<ArrayList<Book>> getBooks();
}
