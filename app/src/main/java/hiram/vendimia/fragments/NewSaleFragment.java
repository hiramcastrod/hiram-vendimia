package hiram.vendimia.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.support.v7.widget.SearchView;

import java.util.ArrayList;

import hiram.vendimia.R;
import hiram.vendimia.activities.ShoppingCartActivity;
import hiram.vendimia.adapters.ProductsAdapter;
import hiram.vendimia.models.Product;


public class NewSaleFragment extends Fragment {

    ListView productListView;
    ProductsAdapter adapter;
    String[] model;
    String[] description;
    int[] price;
    int[] icon;
    int[] stock;
    ArrayList<Product> productList = new ArrayList<Product>();

    public NewSaleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_sale, container, false);
        setHasOptionsMenu(true);
        android.support.v7.app.ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Lista de muebles");

        model = new String[]{ "Emperatriz", "Sioux", "Luxuriare", "Marital Napoleon", "Meseta", "Redmond", "CR-2" };
        description = new String[]{"Silla de comedor", "Silla de mimbre", "Sofá confidente",
                "Saca a la luz al pequeño emperador que todos llevamos dentro con la cama Marital Napoleón.",
                "Comedor de diseño de Londres", "La mesa definitiva para el diseñador o el usuario de ordenadores",
                "Compartimiento para los huevos bandeja para carne, ya no se fabrica color Aguacate ni Dorado"};
        price = new int[]{600, 80, 875, 3000, 450, 220, 2500};
        icon = new int[]{R.drawable.sillaemperatriz, R.drawable.sillamimbre, R.drawable.sillonluxiare,
                R.drawable.camanapoleon, R.drawable.comedormeseta, R.drawable.escritorio, R.drawable.refri};
        stock = new int[]{1,9,18,34,65,4,67};
        productListView = view.findViewById(R.id.listView_products);
        for(int i = 0; i<model.length; i++){
            Product product = new Product(description[i], model[i], price[i], stock[i], icon[i]);
            productList.add(product);
        }

        adapter = new ProductsAdapter(getContext(), productList);
        productListView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.actions, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(TextUtils.isEmpty(s)){
                    adapter.filter("");
                    productListView.clearTextFilter();
                } else {
                    adapter.filter(s);
                }
                return true;
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_cart){
            Intent intent = new Intent(getContext(), ShoppingCartActivity.class);
            startActivity(intent);
            return true;
        }
        return true;
    }

}
