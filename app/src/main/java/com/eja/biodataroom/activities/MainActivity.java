package com.eja.biodataroom.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eja.biodataroom.R;
import com.eja.biodataroom.adapter.KelasAdapter;
import com.eja.biodataroom.database.SiswaDatabase;
import com.eja.biodataroom.model.KelasModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rvKelas)
    RecyclerView rvKelas;

    private SiswaDatabase siswaDatabase;
    private List<KelasModel> kelasModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Membuat objek database
        siswaDatabase = SiswaDatabase.createDatabase(this);

        // Membuat objek list
        kelasModelList = new ArrayList<>();

        // Berpindah ke halaman tambah kelas
        ExtendedFloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TambahKelasActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Mengosongkan list agar memastikan list dapat di isi dengan data yang baru
        kelasModelList.clear();

        // Mengambil data dari Room DB
        getData();

        // Setting layout dan menampilkan data ke recycler view
        rvKelas.setLayoutManager(new GridLayoutManager(this, 2));
        rvKelas.setAdapter(new KelasAdapter(this, kelasModelList));
    }

    private void getData() {

        // Operasi mengambil data dari Room DB
        kelasModelList = siswaDatabase.kelasDao().select();
    }
}