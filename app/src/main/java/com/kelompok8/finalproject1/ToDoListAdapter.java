package com.kelompok8.finalproject1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.kelompok8.finalproject1.model.ToDoListModel;

import java.util.ArrayList;
import java.util.List;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ViewHolder> {
    private List<ToDoListModel> task = new  ArrayList<>();


    // Konstruktor adapter
    public ToDoListAdapter(editTask edittask, deletetask deletetask,  List<ToDoListModel> task) {
        this.edittask  = edittask;
        this.deletetask = deletetask;
        this.task = task;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_todolist, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ToDoListModel data = task.get(position);

        // Menetapkan teks tugas ke TextView
        holder.task.setText(data.getTask());
    }

    @Override
    public int getItemCount() {
        return task.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView task;
        private Button btnTaskCompleted;
        private Button btnEditTask;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.task = itemView.findViewById(R.id.heading);
            this.btnTaskCompleted = itemView.findViewById(R.id.taskCompletedButton);
            this.btnEditTask = itemView.findViewById(R.id.editButton);

            // Menambahkan OnClickListener ke tombol "Selesai"
            btnTaskCompleted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deletetask.onDeletetask(getAdapterPosition());
                }
            });

            // Menambahkna OnClickListener ke tompol "Edit"
            btnEditTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edittask.onEditTask(getAdapterPosition());
                }
            });
        }
    }

    // Antarmuka (interface) untuk menghandle aksi penghapusan tugas
    public interface deletetask {
        void onDeletetask(int position);
    }

    // Inteface untuk handling aksi edit
    public interface editTask {
        void onEditTask(int position);
    }

    private deletetask deletetask; // Variable pemanggil interface untuk delete
    private editTask edittask; // Variable pemanggil interface untuk edit

}
