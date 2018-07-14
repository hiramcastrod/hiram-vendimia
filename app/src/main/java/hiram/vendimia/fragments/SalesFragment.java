package hiram.vendimia.fragments;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import hiram.vendimia.services.indexApi;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hiram.vendimia.R;
import hiram.vendimia.adapters.SalesAdapter;
import hiram.vendimia.models.Sale;


public class SalesFragment extends Fragment {
    TextView isgone;
    RecyclerView salesRV;
    private FloatingActionButton fabNewSale;
    public SalesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sales, container, false);
        salesRV =  view.findViewById(R.id.recyclerview_sales);
        fabNewSale = view.findViewById(R.id.fab_add_sale);
        isgone = view.findViewById(R.id.isgone_tv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        salesRV.setLayoutManager(linearLayoutManager);

        Map<String, String> header = new HashMap<>();
        indexApi.GetRequest("5b4a54ee2f000079001e0e4c", null, header, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonSales = new JSONObject(response);
                    JSONArray jsonArray = jsonSales.getJSONArray("data");
                    ArrayList<Sale> sales = SaleList(jsonArray);
                    System.out.println(sales);
                    if(jsonArray.length() != 0) {
                        SalesAdapter salesAdapter = new SalesAdapter(sales, R.layout.sales_view_holder, getActivity());
                        salesRV.setAdapter(salesAdapter);
                    } else {
                        isgone.setVisibility(View.VISIBLE);
                        isgone.setText("No hay ventas actualmente");
                    }
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


        fabNewSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                NewSaleFragment newSaleFragment = new NewSaleFragment();
                fragmentManager.beginTransaction().replace(R.id.mainContainer, newSaleFragment).setTransition(
                        FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
            }
        });


        return view;
    }

    public ArrayList<Sale> buildSales(){
        ArrayList<Sale> sales = new ArrayList<>();
        sales.add(new Sale("0001", "01", "13/07/2018", "Jorge", "$500.00", "Activo"));
        sales.add(new Sale("0002", "02", "13/07/2018", "Hiram", "$500.00", "Inactivo"));
        sales.add(new Sale("0003", "03", "13/07/2018", "Jorge Hiram", "$500.00", "Activo"));
        sales.add(new Sale("0004", "04", "13/07/2018", "Jorge Hiram", "$500.00", "Activo"));
        sales.add(new Sale("0005", "05", "13/07/2018", "Jorge Hiram", "$500.00", "Activo"));
        sales.add(new Sale("0006", "06", "13/07/2018", "Jorge Hiram", "$500.00", "Activo"));
        sales.add(new Sale("0007", "07", "13/07/2018", "Jorge Hiram", "$500.00", "Activo"));
        sales.add(new Sale("0008", "08", "13/07/2018", "Jorge Hiram", "$500.00", "Activo"));
        sales.add(new Sale("0009", "09", "13/07/2018", "Jorge Hiram", "$500.00", "Activo"));
        sales.add(new Sale("0010", "11", "13/07/2018", "Jorge Hiram", "$500.00", "Activo"));
        sales.add(new Sale("0011", "12", "13/07/2018", "Jorge Hiram", "$500.00", "Activo"));
        sales.add(new Sale("0012", "13", "13/07/2018", "Jorge Hiram", "$500.00", "Activo"));
        return sales;
    }

    public ArrayList<Sale> SaleList(JSONArray jsonArray){
        ArrayList<Sale> sales = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonObject;
            try {
                jsonObject = jsonArray.getJSONObject(i);
                sales.add(new Sale(jsonObject.getString("folio"),
                        jsonObject.getString("client_number"),
                        jsonObject.getString("date"),
                        jsonObject.getString("name"),
                        jsonObject.getString("total"),
                        jsonObject.getString("status")));
            } catch (JSONException e){
                System.out.print("JSON ERROR :" + e);
            }
        }
        return sales;
    }

    }


