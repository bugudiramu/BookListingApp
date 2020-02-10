//package com.example.booklistingapp;
//
//import android.os.AsyncTask;
//import android.util.Log;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//
//public class FetchData extends AsyncTask<Void, Void, ArrayList<Book>> {
//    String data = "";
//    String dataParsed = "";
//    String singleParsed = "";
//    String QUERY = MainActivity.queryText.toString().trim();
//
//    @Override
//    protected ArrayList<Book> doInBackground(Void... voids) {
//        try {
//            URL url = new URL("https://www.googleapis.com/books/v1/volumes?q=flutter");
//            /*
//String server = uri.getAuthority();
//String path = uri.getPath();
//String protocol = uri.getScheme();
//Set<String> args = uri.getQueryParameterNames();
//            * */
//            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//            InputStream inputStream = httpURLConnection.getInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//            String line = "";
//            while (line != null) {
//                line = bufferedReader.readLine();
//                data = data + line;
//            }
//
//            JSONObject rootBookObj = new JSONObject(data);
//            JSONArray itemsArray = rootBookObj.getJSONArray("items");
//            for (int i = 0; i < itemsArray.length(); i++) {
//                JSONObject itemsObj = itemsArray.getJSONObject(i);
//                JSONObject volumeInfoObj = itemsObj.getJSONObject("volumeInfo");
//                JSONObject imageLinksObj = volumeInfoObj.getJSONObject("imageLinks");
//                String title = volumeInfoObj.getString("title");
//                String imageUrl = imageLinksObj.getString("smallThumbnail");
//                /*int totalItems = rootBookObj.getInt("totalItems");
//                singleParsed = "totalItems " + totalItems;
//                Log.v("totalItems", "" + totalItems);*/
//                Log.v("title", title);
//                singleParsed = "Title : " + title + " \n" + "ImageUrl : " + imageUrl;
//                Book book = new Book(title, imageUrl);
//                ArrayList<Book> books = new ArrayList<>();
//                books.add(book);
//                dataParsed = dataParsed + singleParsed + " \n";
//                return books;
//            }
//
//
//        } catch (IOException | JSONException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(ArrayList<Book> aVoid) {
//        super.onPostExecute(aVoid);
//    }
//}
