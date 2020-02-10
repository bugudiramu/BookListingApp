package com.example.booklistingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private RequestQueue requestQueue;
    private String titles, subtitles, webReaderLink, description, textSnippet, previewLink, authors, thumbnail;
    private ProgressBar progressBar;
    BookAdapter bookAdapter;
    ArrayList<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the id's to button and edit text
        editText = findViewById(R.id.query_text);
        final Button searchBtn = findViewById(R.id.search_btn);
        progressBar = findViewById(R.id.progress_bar);
        books = new ArrayList<>();

        GridView listView = findViewById(R.id.list);
        bookAdapter = new BookAdapter(this, books);
        listView.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();


        requestQueue = Volley.newRequestQueue(this);


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBooks();


            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Send data to other Activity
               /* Intent intent = new Intent(getApplicationContext(), BookInfoActivity.class);
                intent.putExtra("title", books.get(position).getTitle());
                intent.putExtra("subtitle", books.get(position).getSubtitle());
                intent.putExtra("webReaderLink", books.get(position).getWebReaderLink());
                startActivity(intent);*/
                Intent bookInfoIntent = new Intent(getApplicationContext(), BookInfoActivity.class);
                bookInfoIntent.putExtra("title", books.get(position).getTitle());
//                bookInfoIntent.putExtra("subtitle", books.get(position).getSubtitle());
                bookInfoIntent.putExtra("webReaderLink", books.get(position).getWebReaderLink());
                bookInfoIntent.putExtra("desc", books.get(position).getDesc());
                bookInfoIntent.putExtra("textInfo", books.get(position).getTextInfo());
                bookInfoIntent.putExtra("previewLink", previewLink);
                bookInfoIntent.putExtra("authors", authors);
                bookInfoIntent.putExtra("thumbnail", thumbnail);

                System.out.println("previewLink" + previewLink);
                startActivity(bookInfoIntent);

            }
        });


    }

    private void getBooks() {
        // Base Url
        final String url = "https://www.googleapis.com/books/v1/volumes?q=";
        if (!TextUtils.isEmpty(editText.getText())) {
            // Show Progress Bar when search button clicked and end when books added to the list

            progressBar.setVisibility(View.VISIBLE);
            String queryText = editText.getText().toString().trim();
            String myUrl = url + queryText + "&key=" + Constants.API_KEY;

            Log.v("Query URl", myUrl);
// Volley Request
            // Request a string response from the provided URL.

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, myUrl, null, new Response.Listener<JSONObject>() {

                        public void onResponse(JSONObject response) {
//                                    Log.i("response", "response" + response);

                            try {
                                JSONArray jsonArray = response.getJSONArray("items");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject parObj = jsonArray.getJSONObject(i);
                                    JSONObject vol = parObj.getJSONObject("volumeInfo");
                                    previewLink = vol.getString("previewLink");
                                    JSONObject searchInfo = parObj.getJSONObject("searchInfo");

                                    if (!searchInfo.isNull("textSnippet")) {
                                        textSnippet = searchInfo.getString("textSnippet");
                                    } else {
                                        textSnippet = "textSnippet";
                                    }
                                    JSONArray authorsArr = vol.getJSONArray("authors");
                                    for (int auth = 0; auth < authorsArr.length(); auth++) {
                                        authors = authorsArr.getString(auth);
                                    }
                                    JSONObject accessInfo = parObj.getJSONObject("accessInfo");
                                    webReaderLink = accessInfo.getString("webReaderLink");
                                    JSONObject imgLink = vol.getJSONObject("imageLinks");

                                    titles = vol.getString("title");
                                    if (!vol.isNull("subtitle")) {
                                        subtitles = vol.getString("subtitle");
                                    } else {
                                        subtitles = "subtitle";
                                    }
                                    if (imgLink.has("thumbnail")) {
                                        thumbnail = imgLink.getString("thumbnail");
                                    } else {
                                        thumbnail = "http://books.google.com/books/content?id=hc6PDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api";
                                    }
                                    // Desc
                                    if (!vol.isNull("description")) {
                                        description = vol.getString("description");
                                    } else {
                                        description = "desc";
                                    }

                                    System.out.println(thumbnail);
                                    // Pass the data to the Book object
                                    Book book = new Book(titles, subtitles, thumbnail, webReaderLink, description, textSnippet);
                                    books.add(book);
                                    bookAdapter.notifyDataSetChanged();
                                    // Show Progress Bar when search button clicked and end when books added to the list
                                    progressBar.setVisibility(View.GONE);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });


// Add the request to the RequestQueue.
            requestQueue.add(jsonObjectRequest);
            myUrl = url;
            books.clear();
//                    bookAdapter.notifyDataSetChanged();

//                    FetchData fetchData = new FetchData();
//                    fetchData.execute();
        } else {
            Toast.makeText(getApplicationContext(), "Please Enter A Book Name", Toast.LENGTH_LONG).show();
        }
    }

    // TODO Get the SearchView from main.xml in menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.search_icon);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_here));
        // Listen to changes
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                bookAdapter.getFilter().filter(newText);
                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

//        if (item.getItemId() == R.id.search_icon) {
//            Toast.makeText(getApplicationContext(), "Search", Toast.LENGTH_LONG).show();
//        }
        return super.onOptionsItemSelected(item);
    }
}
