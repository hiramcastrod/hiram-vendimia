package hiram.vendimia.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import hiram.vendimia.R;
import hiram.vendimia.activities.PopUpActivity;
import hiram.vendimia.models.Product;

public class ProductsAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<Product> productList;
    ArrayList<Product> products;

    public ProductsAdapter(Context contxt, List<Product> productList){
        context = contxt;
        this.productList = productList;
        inflater = LayoutInflater.from(context);
        this.products = new ArrayList<Product>();
        this.products.addAll(productList);
    }

    public class ProductsViewHolder{
        TextView modelTv, descriptionTv, priceTv;
        ImageView image;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return productList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ProductsViewHolder holder;
        if(view==null){
            holder = new ProductsViewHolder();
            view = inflater.inflate(R.layout.products_viewholder, null);

            holder.modelTv = view.findViewById(R.id.textView_model_product);
            holder.descriptionTv = view.findViewById(R.id.textView_description_product);
            holder.priceTv = view.findViewById(R.id.textView_price_product);
            holder.image = view.findViewById(R.id.product_icon);

            view.setTag(holder);
        } else {
            holder = (ProductsViewHolder) view.getTag();
        }

        //Set results
        holder.modelTv.setText(productList.get(position).getModel());
        holder.descriptionTv.setText(productList.get(position).getDescription());
        holder.priceTv.setText(Integer.toString(productList.get(position).getPrice()));
        //probando con icons
        holder.image.setImageResource(productList.get(position).getIcon());

        //Item Click listener
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( context, PopUpActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ICON", productList.get(position).getIcon());
                bundle.putString("PRODUCT", productList.get(position).getModel());
                bundle.putInt("STOCK", productList.get(position).getStock());
                bundle.putInt("TOTAL", productList.get(position).getPrice());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return view;
    }


    //Filter
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        productList.clear();
        if(charText.length() == 0){
            productList.addAll(products);
        } else {
            for(Product product : products){
                if ( product.getModel().toLowerCase(Locale.getDefault()).contains(charText)){
                    productList.add(product);
                }
            }
        }
        notifyDataSetChanged();
    }
}
