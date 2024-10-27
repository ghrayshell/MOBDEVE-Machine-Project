package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.R;
import java.util.List;
import android.widget.Button;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.AppointmentViewHolder> {

    private List<AppointmentsModel> appointmentList;

    public AppointmentsAdapter(List<AppointmentsModel> appointmentList) {
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_appointment, parent, false);
        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        AppointmentsModel appointment = appointmentList.get(position);
        holder.tvDate.setText(appointment.getDate());
        holder.tvLocation.setText(appointment.getLocation());
        holder.tvService.setText(appointment.getService());

        holder.btnCancel.setOnClickListener(v -> {
            removeAppointment(position);
        });
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public void removeAppointment(int position) {
        appointmentList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, appointmentList.size());
    }

    public static class AppointmentViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvLocation, tvService;
        Button btnCancel;

        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvService = itemView.findViewById(R.id.tvService);
            btnCancel = itemView.findViewById(R.id.btnCancel);
        }
    }
}
