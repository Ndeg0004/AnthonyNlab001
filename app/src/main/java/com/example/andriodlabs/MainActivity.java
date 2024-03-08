package com.example.andriodlabs;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private MyAdapter adapter;
    private ArrayList<StarWars> namesList = new ArrayList<>();


    public class StarWars {
        private String name;
        private String height;
        private String mass;

        public StarWars(String name, String height, String mass) {
            this.name = name;
            this.height= height;
            this.mass = mass;

            }


        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        public String getHeight(){
            return height;
        }
        public void setHeight(String height){
            this.height = height;
        }

        public String getMass(){
            return mass;
        }

        public void setMass(String mass){
            this.mass = mass;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        adapter = new MyAdapter(this, namesList);
        listView.setAdapter(adapter);

        new FetchNamesTask().execute();

    }
    private class FetchNamesTask extends AsyncTask<Void, Void, ArrayList<StarWars>> {
        @Override
        protected ArrayList<StarWars> doInBackground(Void... params) {
            ArrayList<StarWars> names = new ArrayList<>();

            try {
                String JsonResponse = "{\"results\":[{\"Name\":\"LukeSkywalker\",\"Mass\":\"77\",\"Height\":\"5'6\"},{\"Name\":\"Han Solo\",\"Mass\":\"179\",\"Height\":\"5'11\"},{\"Name\":\"Darth Vadar\",\"Mass\":\"77\",\"Height\":\"6'6\"}]}";
                JSONObject jsonObject = new JSONObject(JsonResponse);
                JSONArray resultsArray = jsonObject.getJSONArray("results");
                for(int i = 0; i < resultsArray.length(); i++) {
                    JSONObject personObject = resultsArray.getJSONObject(i);
                    String name = personObject.getString("Name");
                    String height = personObject.getString("Height");
                    String mass = personObject.getString("Mass");
                    StarWars starwars = new StarWars(name, height, mass);
                    names.add(starwars);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

           /*  try {

               URL url = new URL("https://swapi.dev/api/people/?format=json");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    names.add("Name");
                    names.add("Height");
                    names.add("Mass");
                } catch (Exception e) {
                    Log.e("Error","Exception occurred: "+e.getMessage(), e);

                }
                finally {
                    urlConnection.disconnect();
                }

            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
            } */
            return names;
        }
        protected void onPostExecute(ArrayList<StarWars> result) {
            if (result != null) {
                namesList.addAll(result);
                adapter.notifyDataSetChanged();
            } else {
                Log.e("ERROR", "Failed to fetch names");
            }
        }
    }
    private class MyAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<StarWars> names;
        public MyAdapter(Context context, ArrayList<StarWars> names) {
            this.context = context;
            this.names = names;
        }
        @Override
        public int getCount() {
            return names.size();
        }
        @Override
        public Object getItem(int position) {
            return names.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
            }
            TextView textView = convertView.findViewById(R.id.textViewTodo);
            StarWars sw = names.get(position);
            textView.setText(sw.name);
            return convertView;
        }
    }
}
