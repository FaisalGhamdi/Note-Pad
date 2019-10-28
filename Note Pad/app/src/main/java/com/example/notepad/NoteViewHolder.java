package com.example.notepad;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    TextView title;
    TextView note;
    TextView date;

    NoteViewHolder(View view) {
        super(view);
        title = view.findViewById(R.id.titleRow);
        note = view.findViewById(R.id.noteRwo);
        date = view.findViewById(R.id.dateRow);
    }






}
