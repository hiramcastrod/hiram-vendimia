package hiram.vendimia.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hiram.vendimia.R;
import hiram.vendimia.adapters.ClientsAdapter;
import hiram.vendimia.models.Client;
import hiram.vendimia.services.indexApi;

public class SelectClientActivity extends AppCompatActivity {
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_client);
        spinner = findViewById(R.id.clients_spinner);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.5), (int)(height*.5));

        Map<String, String> header = new HashMap<>();
        indexApi.GetRequest("5b4e38a93200004c009c297a", null, header, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    ArrayList<Client> clients = ClientList(jsonArray);
                    ArrayList<String> names = new ArrayList<>();
                    for(int i= 0; i < clients.size(); i++){
                        names.add(clients.get(i).getName() + " " + clients.get(i).getLastname());
                    }
                    System.out.println(clients);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, names);
                    spinner.setAdapter(adapter);
                } catch (JSONException e){
                    System.out.print("ERROR JSON: " + e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.print("ERROR VOLLEY: " + error);
            }
        }, getApplicationContext());

    }

    public ArrayList<Client> ClientList(JSONArray jsonArray){
        ArrayList<Client> clients = new ArrayList<>();
        for (int i = 0; i < jsonArray.length()-1; i++){
            JSONObject jsonObject;
            try{
                jsonObject = jsonArray.getJSONObject(i);
                clients.add(new Client(
                        jsonObject.getString("nombre"),
                        jsonObject.getString("apellido_paterno"),
                        jsonObject.getString("apellido_mateno"),
                        jsonObject.getString("rfc")));
            } catch (JSONException e){
                System.out.println("ERROR CLIENT JSON: " + e);
            }
        }

        return clients;
    }
}
