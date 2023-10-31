package com.kelompok8.finalproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import com.kelompok8.finalproject1.SQL.SQLHandler;
import com.kelompok8.finalproject1.model.ToDoListModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ToDoListAdapter.deletetask, ToDoListAdapter.editTask {

    private RecyclerView rc;
    private SQLHandler sqLhandler;
    private List<ToDoListModel> listTask;
    private ToDoListAdapter toDoListAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rc = findViewById(R.id.rv);
        rc.setLayoutManager(new LinearLayoutManager(this));
        sqLhandler = new SQLHandler(this);

        loadContent();  // Memuat tugas dari database ke dalam RecyclerView
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);  // Inflasi menu aksi ke ActionBar
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add) {
            showPopUp(null, true);  // Menampilkan pop-up tambah tugas ketika tombol "Tambah" diklik
        }
        return true;
    }

    private void loadContent() {
        listTask = sqLhandler.getAllTask();  // Mendapatkan daftar tugas dari database
        toDoListAdapter = new ToDoListAdapter(this, this, listTask);  // Membuat adapter untuk RecyclerView
        rc.setAdapter(toDoListAdapter);  // Menghubungkan adapter dengan RecyclerView
    }

    private void showPopUp(ToDoListModel model, boolean isAddData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Get view popup dialog
        View popupView = getLayoutInflater().inflate(R.layout.popup_add, null);

        // Set Dialog
        builder.setView(popupView);  // Menambahkan tampilan pop-up ke dalam dialog

        AlertDialog dialog = builder.create();
        dialog.show();  // Menampilkan pop-up

        // Component
        EditText task = popupView.findViewById(R.id.etTask);  // Mendapatkan EditText dari pop-up
        Button btnAdd = popupView.findViewById(R.id.btnSubmitBuilder);  // Mendapatkan tombol "Tambah" dari pop-up

        if(!isAddData){
            task.setText(model.getTask());
        }


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(isAddData){ // Ketika kondisi is Add Data bernilai true
                   sqLhandler.addTask(new ToDoListModel(task.getText().toString()));  // Menambahkan tugas ke dalam database
               } else {
                   model.setTask(task.getText().toString());
                   sqLhandler.editTask(model);
               }

                loadContent();  // Memuat kembali tugas setelah menambahkan
                dialog.dismiss();  // Menutup pop-up setelah menambahkan
            }
        });

    }

    //Delete Task
    @Override
    public void onDeletetask(int position) {
        ToDoListModel selectedTaskDelete = listTask.get(position);  // Mendapatkan tugas yang akan dihapus

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Konfirmasi Hapus");  // Menampilkan judul dialog konfirmasi
        builder.setMessage("Apakah Anda yakin ingin menghapus tugas ini?");  // Menampilkan pesan konfirmasi

        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sqLhandler.completeTask(selectedTaskDelete);  // Menghapus tugas dari database
                loadContent();  // Memuat kembali tugas setelah menghapus
            }
        });

        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();  // Menutup dialog konfirmasi jika "Tidak" diklik
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();  // Menampilkan dialog konfirmasi
    }

    //Edit Task
    @Override
    public void onEditTask(int position) {
        ToDoListModel data = listTask.get(position);
        System.out.println(data.getTask());
        showPopUp(data, false);
    }

}
