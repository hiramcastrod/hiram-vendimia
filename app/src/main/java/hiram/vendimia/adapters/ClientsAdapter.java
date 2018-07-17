package hiram.vendimia.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import hiram.vendimia.R;
import hiram.vendimia.models.Client;

public class ClientsAdapter extends RecyclerView.Adapter<ClientsAdapter.ClientViewHolder>{
    private ArrayList<Client> clients;
    private int resource;
    private Activity activity;

    public ClientsAdapter(ArrayList<Client> clients, int resource, Activity activity) {
    this.clients = clients;
    this.resource = resource;
    this.activity = activity;
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        Client client = clients.get(position);
        //GET DATA
        holder.nameCard.setText(client.getName());
        holder.lastnameCard.setText(client.getLastname());
        holder.surnameCard.setText(client.getSurname());
        holder.rfcCard.setText(client.getRfc());
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    public class ClientViewHolder extends RecyclerView.ViewHolder{
        private TextView nameCard;
        private TextView lastnameCard;
        private TextView surnameCard;
        private TextView rfcCard;

        public ClientViewHolder(View itemView) {
            super(itemView);
            nameCard = itemView.findViewById(R.id.textView_name_client);
            lastnameCard = itemView.findViewById(R.id.textView_lastname);
            surnameCard = itemView.findViewById(R.id.textView_surname);
            rfcCard = itemView.findViewById(R.id.textView_rfc_client);
        }
    }
}
