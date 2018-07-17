package hiram.vendimia.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import hiram.vendimia.adapters.ClientsAdapter;
import hiram.vendimia.models.Client;
import hiram.vendimia.services.indexApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hiram.vendimia.R;


public class ClientsFragment extends Fragment {
    TextView tvName, tvLastname, tvSurname, tvRfc;
    RecyclerView rvClients;
    private FloatingActionButton fabNewClient;

    public ClientsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_clients, container, false);
        tvName = view.findViewById(R.id.textView_name_client);
        tvLastname = view.findViewById(R.id.textView_lastname);
        tvSurname = view.findViewById(R.id.textView_surname);
        tvRfc = view.findViewById(R.id.textView_rfc_client);
        rvClients = view.findViewById(R.id.recyclerview_client);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvClients.setLayoutManager(linearLayoutManager);

        Map<String, String> header = new HashMap<>();
        indexApi.GetRequest("5b4e38a93200004c009c297a", null, header, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    ArrayList<Client> clients = ClientList(jsonArray);
                    System.out.println(clients);
                    ClientsAdapter clientsAdapter = new ClientsAdapter(clients, R.layout.clients_view_holder, getActivity());
                    rvClients.setAdapter(clientsAdapter);

                } catch (JSONException e){
                    System.out.print("ERROR JSON: " + e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.print("ERROR VOLLEY: " + error);
            }
        }, getContext());

        return view;
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
