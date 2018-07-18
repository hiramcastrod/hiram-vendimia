package hiram.vendimia.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import hiram.vendimia.R;
import hiram.vendimia.models.Client;
import hiram.vendimia.models.Sale;
import hiram.vendimia.services.LocalDictionary;
import hiram.vendimia.services.LocalStorage;
import hiram.vendimia.services.indexApi;

public class SelectClientActivity extends AppCompatActivity {
    private Spinner spinner;
    private Button accept;
    ArrayList<Sale> saleList;
    private LocalStorage storage = new LocalStorage();
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_client);
        spinner = findViewById(R.id.clients_spinner);
        accept = findViewById(R.id.button_accept_select_cient);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        sharedPreferences = getSharedPreferences(LocalDictionary.SALES, MODE_PRIVATE);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.5), (int)(height*.5));
        getSaleList();
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

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Se ha añadido nueva venta", Toast.LENGTH_LONG).show();
                saveNewSale();
            }
        });

    }

    public void saveNewSale(){
        Random random = new Random();
        int r = random.nextInt( 1000) + 65;
        Date currentDate = Calendar.getInstance().getTime();
        saleList.add(new Sale(String.valueOf(r), String.valueOf(r),
                currentDate.toString(), spinner.getSelectedItem().toString(),
                storage.getLocalData(getApplicationContext(), LocalDictionary.TOTAL), "Activo"));
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(saleList);
        editor.putString("ventas", json);
        editor.apply();
        for(int i = 0; i<saleList.size(); i++)
            System.out.println(saleList);



    }

    public ArrayList<Sale> getSaleList() {
        SharedPreferences sharedPreferences = getSharedPreferences(LocalDictionary.SALES, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("ventas", null);
        Type type = new TypeToken<ArrayList<Sale>>() {}.getType();
        saleList = gson.fromJson(json, type);

        if(saleList == null){
            saleList = new ArrayList<>();
        }
        return saleList;
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
