package com.eja.biodataroom.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.eja.biodataroom.model.KelasModel;
import com.eja.biodataroom.model.SiswaModel;

import java.util.List;

@Dao
public interface KelasDao {

    // Mengambil data kelas
    @Query("SELECT * FROM KELAS ORDER BY nama_kelas ASC")
    List<KelasModel> select();

    // Memasukkan data kelas
    @Insert
    void insert(KelasModel kelasModel);

    // Menghapus data kelas
    @Delete
    void delete(KelasModel kelasModel);

    // Mengupdate data kelas
    @Update
    void update(KelasModel kelasModel);

    // Mengambil data siswa
    @Query("SELECT * FROM TB_SISWA WHERE id_kelas = :id_kelas ORDER BY nama_siswa ASC")
    List<SiswaModel> selectSiswa(int id_kelas);

    // Memasukkan data siswa
    @Insert
    void insertSiswa(SiswaModel siswaModel);

    // Menghapus data siswa
    @Delete
    void deleteSiswa(SiswaModel siswaModel);

    // Mengupdate data
    @Update
    void updateSiswa(SiswaModel siswaModel);
}
