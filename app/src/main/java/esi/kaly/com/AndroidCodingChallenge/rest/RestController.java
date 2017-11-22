package esi.kaly.com.AndroidCodingChallenge.rest;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import esi.kaly.com.AndroidCodingChallenge.model.Book;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestController implements Callback<ArrayList<Book>> {

    private static final String BASE_URL = "http://de-coding-test.s3.amazonaws.com/";

    private static RestController singleton;

    private RestCallback mRestCallback;

    private RestController() {
    }

    public static void initializeRestController() {
        singleton = new RestController();
    }

    public static RestController getInstance() {
        if (singleton == null) {
            initializeRestController();
        }
        return singleton;
    }

    public void fetchBooks(final RestCallback restCallback) {

        this.mRestCallback = restCallback;

        //TODO - make retrofit singleton

        APICall apiCall = APIClient.getClient().create(APICall.class);

        Call<ArrayList<Book>> call = apiCall.getBooks();
        call.enqueue(this);
    }

    public void setRestCallback(RestCallback restCallback) {
        this.mRestCallback = restCallback;
    }

    @Override
    public void onResponse(@NonNull Call<ArrayList<Book>> call, @NonNull Response<ArrayList<Book>> response) {
        if (call.isExecuted()) {
            Log.i("RestController", response.toString());
            if (response.isSuccessful()) {
                try {

                    if (mRestCallback != null) {
                        mRestCallback.onBooksRetrieved(response.body());
                    }
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            } else {
                if (mRestCallback != null) {
                    mRestCallback.onError("Failed");
                }
            }
        }
    }

    @Override
    public void onFailure(@NonNull Call<ArrayList<Book>> call, @NonNull Throwable t) {
        if (mRestCallback != null) {
            mRestCallback.onError(t.getMessage());
        }
    }
}
