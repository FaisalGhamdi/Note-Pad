package com.example.notepad;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import java.text.DateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity {

    private EditText titleValue;
    private EditText noteValue;
    private int index; // index of note within list


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setTitle("Multi Notes");
        titleValue = findViewById(R.id.titleTxt);
        noteValue = findViewById(R.id.noteTxt);


        Intent intent = getIntent();
        if(intent.hasExtra("Title")){
            String title = intent.getStringExtra("Title");
            String note = intent.getStringExtra("Note");


            titleValue.setText(title);
            noteValue.setText(note);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveBtn:
                updateNote();

                if (!titleValue.getText().toString().trim().equals("")) {
                    String title = titleValue.getText().toString();
                    String note = noteValue.getText().toString();
                    String date = DateFormat.getDateTimeInstance().format(new Date());
                    Note n = new Note(title, date, note);
                    MainActivity.noteList.add(n);
                    MainActivity.noteAdapter.notifyDataSetChanged();

                } else {

                    Context context = getApplicationContext();
                    CharSequence text = "Must Insert Title";
                    Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                    toast.show();
                }
                EditActivity.super.onBackPressed();
                break;
        }
    return true;
    }

    @Override
    public void onBackPressed() {
        checkTitle();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            updateNote();
            String title = titleValue.getText().toString();
            String note = noteValue.getText().toString();
            String date = DateFormat.getDateTimeInstance().format(new Date());
            Note n = new Note(title, date, note);

        MainActivity.noteList.add(n);
        MainActivity.noteAdapter.notifyDataSetChanged();
        EditActivity.super.onBackPressed();
        }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditActivity.super.onBackPressed();
            }
        });

        builder.setTitle("Your note is not saved! "+ "\n save this note:'"+ titleValue.getText().toString() + "'?");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void updateNote()
    {
        Intent intent = getIntent();
        if(intent.hasExtra("Position")){
            int pos = getIntent().getIntExtra("Position",1);
            MainActivity.noteList.remove(pos);
            MainActivity.noteAdapter.notifyDataSetChanged();
        }
    }

    public void checkTitle(){

        Intent intent = getIntent();
        if (intent.hasExtra("Title")){

            String title = intent.getStringExtra("Title");
            String note = intent.getStringExtra("Note");
            String titleStr = titleValue.getText().toString();
            String noteStr = noteValue.getText().toString();

            if (titleStr.equals(title) && noteStr.equals(note)){
                Context context = getApplicationContext();
                CharSequence text = "No changes have been made";
                Toast toast = Toast.makeText(context,text,Toast.LENGTH_SHORT);
                toast.show();
                super.onBackPressed();
            }
        }
        if (titleValue.getText().toString().trim().equals("")){

            Context context = getApplicationContext();
            CharSequence text = "MUST INSERT TITLE";
            Toast toast = Toast.makeText(context,text,Toast.LENGTH_SHORT);
            toast.show();
            super.onBackPressed();
        }
    }

}
