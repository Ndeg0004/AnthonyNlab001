package com.example.andriodlabs;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.example.andriodlabs.R;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {

    private ImageView catImageView;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        catImageView = findViewById(R.id.catImageView);
        progressBar = findViewById(R.id.progressBar);

        CatImages catImages = new CatImages();
        catImages.execute();
    }

    private class CatImages extends AsyncTask<String, Integer, String> {

        private Bitmap currentCatBitmap;

        @Override
        protected String doInBackground(String... strings) {
            while (true) {
                try {
                    URL url = new URL("https://cataas.com/cat?json=true");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    String jsonString = convertInputStreamToString(inputStream);
                    JSONObject jsonObject = new JSONObject(jsonString);
                    urlConnection.disconnect();
                    // checks if it already exists

                    String id = jsonObject.getString("_id");
                    String filename = id + ".jpg"; // Assuming JPEG format
                    boolean imageExists = checkIfImageExists(filename);

                    if (!imageExists) {
                  // this downlaods image from url
                        String imageUrl = "https://cataas.com/cat?_id=" + id;
                        Bitmap bitmap = downloadImage(imageUrl);

                        //Saves image to current divice
                        saveImageToDevice(bitmap, filename);
                        currentCatBitmap = bitmap;
                    } else {
                        // then this laods the image
                        currentCatBitmap = loadBitmapFromFile(filename);
                    }
                    // updates image(with saved image)
                    publishProgress(0);
                    for (int i = 0; i < 100; i++) {
                        Thread.sleep(30);
                        publishProgress(i);
                    }
                } catch (Exception e) {
                    Log.e("CatImages", "Error downloading cat image: " + e.getMessage());
                }
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
            catImageView.setImageBitmap(currentCatBitmap);
        }
        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            InputStreamReader in = new InputStreamReader(inputStream);
            BufferedReader buff = new BufferedReader(in);
            String line;
            StringBuilder builder = new StringBuilder();
            do {
                line = buff.readLine();
                builder.append(line).append("\n");
            } while (line != null);

            buff.close();
            return builder.toString();
        }
        private boolean checkIfImageExists(String filename) {
            // Check if image file exists on device
            File file = new File(getFilesDir(), filename);
            return file.exists();
        }
        private Bitmap downloadImage(String imageUrl) {
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                Log.e("DownloadImage", "Error downloading image: " + e.getMessage());
                return null;
            }
        }

        private void saveImageToDevice(Bitmap bitmap, String filename) {
            try {
                FileOutputStream outputStream = openFileOutput(filename, MODE_PRIVATE);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                Log.e("SaveImage", "Error saving image: " + e.getMessage());
            }
        }

        private Bitmap loadBitmapFromFile(String filename) {
            try {
                File file = new File(getFilesDir(), filename);
                if (file.exists()) {
                    return BitmapFactory.decodeFile(file.getAbsolutePath());
                }
            } catch (Exception e) {
                Log.e("LoadBitmap", "Error! no bitmap found: " + e.getMessage());
            }
            return null;
        }
    }
}
