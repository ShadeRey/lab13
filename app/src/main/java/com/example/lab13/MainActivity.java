package com.example.lab13;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public static final String RON_SWANSON_QUOTES_API_URL = "https://ron-swanson-quotes.herokuapp.com/v2/quotes";
    public static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (instance == null)
        {
            instance = this;
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }
}
class FetchQuoteTask extends AsyncTask<String, Void, String> {

    public FetchQuoteTask(View view)
    {
        this.view = view;
    }
    public View view;
    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String quoteJsonStr = null;

        try {
            URL url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            quoteJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }

        return quoteJsonStr;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                if (jsonArray.length() > 0) {
                    String quote = jsonArray.getString(0);
                    // Теперь у вас есть цитата, которую вы можете использовать в вашем приложении
                    Log.d("Quote", quote);
                    TextView textView = view.findViewById(R.id.textview_ron_speech);
                    textView.setText(quote);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
