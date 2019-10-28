package com.example.notepad;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private ArrayList<Note> notes;
    private MainActivity mainActivity;

    NoteAdapter(ArrayList<Note> list,MainActivity main){
        this.notes = list;
        this.mainActivity = main;
    }
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.list_row_item, parent, false);

        itemView.setOnClickListener(mainActivity);
        itemView.setOnLongClickListener(mainActivity);

        return new NoteViewHolder(itemView);
    }
    @Override
    public int getItemCount() {
        return notes.size();
    }
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note n = notes.get(position);
        holder.title.setText(n.getTitle());
        holder.date.setText(n.getDate());

        String str = n.getText();
        if( str.length() > 80 && !str.equals(""))
        {
            str = str.substring(0, 80) + "...";
        }
        holder.note.setText(str);
    }
}
