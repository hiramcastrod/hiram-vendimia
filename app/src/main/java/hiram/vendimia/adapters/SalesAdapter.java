package hiram.vendimia.adapters;

import android.app.Activity;
import android.graphics.Picture;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import hiram.vendimia.R;
import hiram.vendimia.models.Sale;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.SalesViewHolder>{
    private ArrayList<Sale> sales;
    private int resource;
    private Activity activity;


    public SalesAdapter(ArrayList<Sale> sales, int resource, Activity activity) {
        this.sales = sales;
        this.resource = resource;
        this.activity = activity;
    }

    @NonNull
    @Override
    public SalesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        return new SalesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SalesViewHolder holder, int position) {
        //Toda la lista
        Sale sale = sales.get(position);
        //get each item
        holder.folioCard.setText(sale.getFolio());
        holder.nclientCard.setText(sale.getClientNo());
        holder.dateCard.setText(sale.getDate());
        holder.nameCard.setText(sale.getName());
        holder.totalCard.setText(sale.getTotal());
        holder.statusCard.setText(sale.getStatus());

    }

    @Override
    public int getItemCount() {
        return sales.size();
    }

    public class SalesViewHolder extends RecyclerView.ViewHolder{
        private TextView folioCard;
        private TextView nclientCard;
        private TextView dateCard;
        private TextView nameCard;
        private TextView totalCard;
        private TextView statusCard;

        public SalesViewHolder(View itemView) {
            super(itemView);
            folioCard = itemView.findViewById(R.id.rv_folio);
            nclientCard = itemView.findViewById(R.id.rv_cclient);
            dateCard = itemView.findViewById(R.id.rv_date);
            nameCard = itemView.findViewById(R.id.rv_name);
            totalCard = itemView.findViewById(R.id.rv_total);
            statusCard = itemView.findViewById(R.id.rv_status);
        }
    }
}
