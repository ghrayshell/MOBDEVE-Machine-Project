// AppointmentsActivity.java
package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.R;
import java.util.ArrayList;
import java.util.List;

public class AppointmentsActivity extends AppCompatActivity {

    private RecyclerView rvAppointmentsList;
    private AppointmentsAdapter appointmentsAdapter;
    private List<AppointmentsModel> appointmentList;
    private ImageView btnBackAppointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        btnBackAppointments = findViewById(R.id.ivBackAppointments);
        rvAppointmentsList = findViewById(R.id.rvAppointmentsList);
        appointmentList = new ArrayList<>();

        // Sample data PUT RETRIEVAL LOGIC HERE
        appointmentList.add(new AppointmentsModel("November 31, 2024", "De La Salle University", "Blinds Installation"));
        appointmentList.add(new AppointmentsModel("December 31, 2024", "De La Salle University", "Blinds Installation"));
        appointmentList.add(new AppointmentsModel("January 31, 2025", "De La Salle University", "Blinds Installation"));
        appointmentList.add(new AppointmentsModel("February 31, 2025", "De La Salle University", "Blinds Installation"));
        // Add more appointments as needed

        appointmentsAdapter = new AppointmentsAdapter(appointmentList);
        rvAppointmentsList.setLayoutManager(new LinearLayoutManager(this));
        rvAppointmentsList.setAdapter(appointmentsAdapter);
    }
}
