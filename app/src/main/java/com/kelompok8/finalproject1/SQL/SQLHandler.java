package com.kelompok8.finalproject1.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kelompok8.finalproject1.model.ToDoListModel;

import java.util.ArrayList;
import java.util.List;

public class SQLHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ToDoList";
    private static final String TABLE_NAME = "TblTodo";

    private static final String KEY_ID = "id";
    private static final String TASKHEAD = "task";

    public SQLHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Membuat tabel database ketika database pertama kali dibuat
        String create = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TASKHEAD + " TEXT)";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Meng-upgrade atau menghapus dan membuat ulang tabel ketika versi database berubah
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addTask(ToDoListModel model) {
        // Menambahkan tugas ke database
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues data = new ContentValues();
        data.put(TASKHEAD, model.getTask());

        db.insert(TABLE_NAME, null, data);
        db.close();
    }

    public List<ToDoListModel> getAllTask() {
        // Mendapatkan daftar semua tugas dari database
        List<ToDoListModel> dataList = new ArrayList<ToDoListModel>();

        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                ToDoListModel model = new ToDoListModel(null);
                model.setId(Integer.parseInt(c.getString(0)));
                model.setTask(c.getString(1));
                dataList.add(model);
            } while (c.moveToNext());
        }

        return dataList;
    }

    public void completeTask(ToDoListModel model) {
        // Menghapus tugas dari database berdasarkan ID
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?", new String[]{String.valueOf(model.getId())});
        db.close();
    }

    public int editTask(ToDoListModel model){
        // Mengedit data dari database sesuai dengan ID
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASKHEAD,model.getTask());

        return db.update(TABLE_NAME, contentValues, KEY_ID + " = ?", new String[]{String.valueOf(model.getId())});
    }

}
