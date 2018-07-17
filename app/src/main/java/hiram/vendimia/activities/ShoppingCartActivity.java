package hiram.vendimia.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import hiram.vendimia.activities.PopUpActivity;

import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import hiram.vendimia.R;
import hiram.vendimia.adapters.ShoppingCartAdapter;
import hiram.vendimia.models.Cart;

public class ShoppingCartActivity extends AppCompatActivity {
    TextView tvModel, tvPrice, tvQuantity, tvAmount, total;
    ImageView ivIcon;
    RecyclerView cartRecyclerView;
    ArrayList<Cart> cartList;
    float amount;
    double amountDouble;
    RadioGroup radioGroup;
    public int selectedMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        tvModel = findViewById(R.id.textView_model_cart);
        tvPrice = findViewById(R.id.textView_price_cart);
        tvQuantity = findViewById(R.id.textview_quantitiy_cart);
        tvAmount = findViewById(R.id.textview_import_cart);
        ivIcon = findViewById(R.id.cart_icon);
        cartRecyclerView = findViewById(R.id.recyclerview_cart);
        total = findViewById(R.id.textview_alltotal_cart);
        radioGroup = findViewById(R.id.radio_months);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartRecyclerView.setLayoutManager(linearLayoutManager);
        ShoppingCartAdapter cartAdapter = new ShoppingCartAdapter(loadCartList(), R.layout.cart_viewholder, this );
        cartRecyclerView.setAdapter(cartAdapter);
        for(int i = 0; i<loadCartList().size(); i++){
            amount = amount + cartList.get(i).getTotal();
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.three_radio:
                        amountDouble= amount * (1+(2.8*3)/100);
                        total.setText(String.valueOf(amountDouble));
                        break;
                    case R.id.six_radio:
                        amountDouble= amount * (1+(2.8*6)/100);
                        total.setText(String.valueOf(amountDouble));
                        break;
                    case R.id.nine_radio:
                        amountDouble= amount * (1+(2.8*9)/100);
                        total.setText(String.valueOf(amountDouble));
                        break;
                    case R.id.twelve_radio:
                        amountDouble= amount * (1+(2.8*12)/100);
                        total.setText(String.valueOf(amountDouble));
                        break;
                }
            }
        });
        total.setText(String.valueOf(amount));


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
