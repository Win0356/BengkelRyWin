package com.example.bengkelrywin.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bengkelrywin.AddEditActivity;
import com.example.bengkelrywin.MainActivity;
import com.example.bengkelrywin.R;
import com.example.bengkelrywin.models.Service;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder>
        implements Filterable {
    private List<Service> serviceList, filteredServiceList;
    private Context context;

    public ServiceAdapter(List<Service> serviceList, Context context) {
        this.serviceList = serviceList;
        filteredServiceList = new ArrayList<>(serviceList);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_service, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Service service = filteredServiceList.get(position);

        holder.tvNomor.setText("S-"+String.valueOf(service.getId()));
        holder.tvNamaJenis.setText(service.getNama() + "-" + service.getJenis());
        holder.tvAlamat.setText(service.getAlamat());
    }

    @Override
    public int getItemCount() {
        return filteredServiceList.size();
    }


    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
        filteredServiceList = new ArrayList<>(serviceList);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charSequenceString = charSequence.toString();
                List<Service> filtered = new ArrayList<>();

                if (charSequenceString.isEmpty()) {
                    filtered.addAll(serviceList);
                } else {
                    for (Service service : serviceList) {
                        if (service.getNama().toLowerCase()
                                .contains(charSequenceString.toLowerCase()))
                            filtered.add(service);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filtered;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults
                    filterResults) {
                filteredServiceList.clear();
                filteredServiceList.addAll((List<Service>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNomor, tvNamaJenis, tvAlamat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNomor = itemView.findViewById(R.id.tv_nomor);
            tvNamaJenis = itemView.findViewById(R.id.tv_nama_jenis);
            tvAlamat = itemView.findViewById(R.id.tv_alamat);
        }
    }
}
