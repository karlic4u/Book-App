package esi.kaly.com.AndroidCodingChallenge.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import esi.kaly.com.AndroidCodingChallenge.R;
import esi.kaly.com.AndroidCodingChallenge.model.Book;
import esi.kaly.com.AndroidCodingChallenge.view.presenter.BookPresenter;

public class MainActivity extends AppCompatActivity implements BookPresenter.BookView {

    public ListView mListView;

    private TextView mEmptyListTextView;
    private ProgressBar mProgressBar;
    private BooksAdapter mBooksAdapter;

    private BookPresenter mBookPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.list_view);
        mEmptyListTextView = (TextView) findViewById(R.id.emptyListTextView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (mBooksAdapter == null) {
            mBooksAdapter = new BooksAdapter(this);
        }

        mListView.setAdapter(mBooksAdapter);

        if (mBookPresenter == null) {
            mBookPresenter = new BookPresenter(this);
        }

        mProgressBar.setVisibility(View.VISIBLE);
        mBookPresenter.fetchBooks();
    }


    @Override
    public void onBooksRetrieved() {
        mProgressBar.setVisibility(View.GONE);
        if (mBookPresenter != null && mBooksAdapter != null) {
            final ArrayList<Book> books = mBookPresenter.getBookList();
            if (books != null && books.size() > 0) {

                mListView.setVisibility(View.VISIBLE);
                mEmptyListTextView.setVisibility(View.GONE);

                mBooksAdapter.updateBooks(books);
            } else {
                mListView.setVisibility(View.GONE);
                mEmptyListTextView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onError(String error) {
        mProgressBar.setVisibility(View.GONE);
        mListView.setVisibility(View.GONE);
        mEmptyListTextView.setVisibility(View.VISIBLE);
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    public class BooksAdapter extends BaseAdapter {

        private ArrayList<Book> mBooks;

        private Context mContext;

        public BooksAdapter(Context context) {
            this.mContext = context;
        }

        public void updateBooks(ArrayList<Book> books) {
            this.mBooks = books;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return (mBooks != null) ? mBooks.size() : 0;
        }

        @Override
        public Book getItem(int position) {
            return (mBooks != null && !mBooks.isEmpty()) ? mBooks.get(position) : null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            BooksViewHolder vh;
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_book, parent, false);
                vh = new BooksViewHolder();
                vh.titleTextView = (TextView) convertView.findViewById(R.id.title_text_view);
                vh.authorTextView = (TextView) convertView.findViewById(R.id.author_text_view);
                vh.mBookImageView = (ImageView) convertView.findViewById(R.id.book_image_view);
                convertView.setTag(vh);
            } else {
                vh = (BooksViewHolder) convertView.getTag();
            }

            if (mBooks != null && mBooks.size() > 0 && mBooks.get(position) != null) {
                final Book book = mBooks.get(position);
                vh.titleTextView.setText(book.getTitle());
                vh.authorTextView.setText(book.getAuthor());

                if (!TextUtils.isEmpty(book.getImageURL())) {
                    Picasso.with(convertView.getContext())
                            .load(book.getImageURL())
                            .placeholder(ContextCompat.getDrawable(mContext, R.drawable.ic_book))
                            .into(vh.mBookImageView);
                } else {
                    vh.mBookImageView.setImageResource(R.drawable.ic_book);
                }
            }

            return convertView;
        }
    }

    private class BooksViewHolder {
        private ImageView mBookImageView;
        private TextView titleTextView;
        private TextView authorTextView;
    }
}
