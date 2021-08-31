package com.eja.biodataroom.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.eja.biodataroom.R;
import com.eja.biodataroom.activities.ListSiswaActivity;
import com.eja.biodataroom.activities.UpdateKelasActivity;
import com.eja.biodataroom.database.Constant;
import com.eja.biodataroom.database.SiswaDatabase;
import com.eja.biodataroom.model.KelasModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KelasAdapter extends RecyclerView.Adapter<KelasAdapter.ViewHolder> {

    private final Context context;
    private final List<KelasModel> kelasModelList;
    private SiswaDatabase siswaDatabase;
    private Bundle bundle;

    public KelasAdapter(Context context, List<KelasModel> kelasModelList){
        this.context = context;
        this.kelasModelList = kelasModelList;
    }

    @NonNull
    @Override
    public KelasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_kelas, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull KelasAdapter.ViewHolder holder, int position) {

        // Memindahkan data ke dalam list dengan index position ke dalam KelasModel
        final KelasModel kelasModel = kelasModelList.get(position);

        // Menampilkan data ke layar
        holder.tvNamaWali.setText(kelasModel.getNama_wali());
        holder.tvNamaKelas.setText(kelasModel.getNama_kelas());

        ColorGenerator generator = ColorGenerator.MATERIAL;

        // Membuat generate random color
        int color = generator.getRandomColor();
        holder.cvKelas.setCardBackgroundColor(color);

        // Fungsi OnClick yang dilakukan pada itemView
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle = new Bundle();

                bundle.putInt(Constant.KEY_ID_KELAS, kelasModel.getId_kelas());
                context.startActivity(new Intent(context, ListSiswaActivity.class).putExtras(bundle));
            }
        });

        // Membuat onClick pada icon overflow
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Membuat objek database
                siswaDatabase = siswaDatabase.createDatabase(context);

                // Membuat object pop menu
                PopupMenu popupMenu = new PopupMenu(context, view);

                // Inflate menu ke dalam popup menu
                popupMenu.inflate(R.menu.popup_menu);

                // Menampilkan popup menu
                popupMenu.show();

                // OnClick yang dilakukan pada salah satu icon popup menu
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.delete:

                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                                alertDialogBuilder.setMessage("Apakah anda yakin menghapus kelas " + kelasModel.getNama_kelas() + " ?");
                                alertDialogBuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Melakukan operasi delete data
                                        siswaDatabase.kelasDao().delete(kelasModel);

                                        // Membersihkan data yang telah dihapus pada list
                                        kelasModelList.remove(position);

                                        // Memberitahukan bahwa data sudah hilang
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(0, kelasModelList.size());

                                        Toast.makeText(context, "Berhasil dihapus", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();

                                break;

                            case R.id.edit:

                                // Membuat objek bundle
                                bundle = new Bundle();

                                // Mengisi bundle dengan data
                                bundle.putInt(Constant.KEY_ID_KELAS, kelasModel.getId_kelas());
                                bundle.putString(Constant.KEY_NAMA_KELAS, kelasModel.getNama_kelas());
                                bundle.putString(Constant.KEY_NAMA_WALI, kelasModel.getNama_wali());

                                // Berpindah halaman dengan membawa data yang telah diambil
                                context.startActivity(new Intent(context, UpdateKelasActivity.class).putExtras(bundle));

                                break;
                        }
                        return true;
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return kelasModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvNamaKelas)
        TextView tvNamaKelas;
        @BindView(R.id.tvNamaWali)
        TextView tvNamaWali;
        @BindView(R.id.cvKelas)
        CardView cvKelas;
        @BindView(R.id.overflow)
        ImageButton overflow;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
