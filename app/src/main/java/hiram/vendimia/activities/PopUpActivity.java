package hiram.vendimia.activities;

import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import hiram.vendimia.R;
import hiram.vendimia.models.Cart;

public class PopUpActivity extends AppCompatActivity {
    private Button btAccept, btCancel;
    private Spinner spinner;
    private TextView tvModel, tvPrice, tvTotal;
    private ImageView ivIcon;
    private int quantity;
    private int selectedItem;
    ArrayList<Cart> cartList;
    SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        tvModel = findViewById(R.id.tv_model_popup);
        tvPrice = findViewById(R.id.tv_total_popup);
        tvTotal = findViewById(R.id.tv_importe_popup);
        spinner = findViewById(R.id.quantity_spinner);
        btAccept = findViewById(R.id.button_accept_popup);
        ivIcon = findViewById(R.id.iv_icon_popup);
        sharedPreferences = getSharedPreferences("sales", MODE_PRIVATE);
        loadCartList();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.7), (int)(height*.7));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        Bundle extras = getIntent().getExtras();
        tvModel.setText(extras.getString("PRODUCT"));
        tvPrice.setText(Integer.toString(extras.getInt("TOTAL")));
        quantity = extras.getInt("STOCK");
        spinner.setSelection(0);
        ArrayList<Integer> stock = new ArrayList<Integer>();
        for(int i=0; i<quantity; i++) {
            stock.add(i);
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, stock);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem = (int) adapterView.getItemAtPosition(i);
                tvTotal.setText(Integer.toString(selectedItem * Integer.parseInt(tvPrice.getText().toString())));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                spinner.setSelection(0);
            }
        });

        btAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNewSale();
            }
        });
    }

    public void saveNewSale(){
        cartList.add(new Cart(tvModel.getText().toString(), selectedItem, Integer.parseInt(tvTotal.getText().toString()),ivIcon.getId()));
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cartList);
        editor.putString("cart list", json);
        editor.apply();
        for(int i = 0; i<cartList.size(); i++)
        System.out.println(cartList.get(i).getModelProduct());
    }

    public ArrayList<Cart> loadCartList(){
        SharedPreferences sharedPreferences = getSharedPreferences("sales", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("cart list", null);
        Type type = new TypeToken<ArrayList<Cart>>() {}.getType();
        cartList = gson.fromJson(json, type);

        if(cartList == null){
            cartList = new ArrayList<>();
        }

        return cartList;
    }


}
