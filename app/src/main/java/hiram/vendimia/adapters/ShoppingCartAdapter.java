package hiram.vendimia.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import hiram.vendimia.R;
import hiram.vendimia.models.Cart;

public class ShoppingCartAdapter extends  RecyclerView.Adapter<ShoppingCartAdapter.CartViewHolder>{
    private ArrayList<Cart> carts;
    private int resource;
    private Activity activity;

    public ShoppingCartAdapter(ArrayList<Cart> carts, int resource, Activity activity){
        this.carts = carts;
        this.resource = resource;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart cart = carts.get(position);
        float amount;
        //GET
        //holder.iconCard.setImageResource(cart.getIcon());
        holder.modelCard.setText(cart.getModelProduct());
        holder.priceCard.setText(Float.toString(cart.getTotal()));
        holder.quantityCard.setText(Integer.toString(cart.getQuantity()));
        amount = Float.valueOf(holder.priceCard.getText().toString()) * Integer.parseInt(holder.quantityCard.getText().toString());
        holder.amountCard.setText(Float.toString(amount).toString());
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{
        private ImageView iconCard;
        private TextView quantityCard, priceCard, amountCard, modelCard;

        public CartViewHolder(View itemView) {
            super(itemView);
            iconCard = itemView.findViewById(R.id.cart_icon);
            quantityCard = itemView.findViewById(R.id.textview_quantitiy_cart);
            priceCard = itemView.findViewById(R.id.textView_price_cart);
            amountCard = itemView.findViewById(R.id.textview_import_cart);
            modelCard = itemView.findViewById(R.id.textView_model_cart);
        }
    }
}
