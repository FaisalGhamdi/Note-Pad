package com.example.notepad;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity

        implements View.OnClickListener, View.OnLongClickListener {

    public static ArrayList<Note> noteList = new ArrayList<>();
    private RecyclerView recyclerView;
    public static NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        noteAdapter = new NoteAdapter(noteList, this);
        recyclerView.setAdapter(noteAdapter);
        recyclerView.setLayoutManager((new LinearLayoutManager(this)));

        loadFile();
        if (!noteList.isEmpty()) {
            setTitle(String.format("Multi Notes (%d)", noteList.size()));
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_page, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.editBtn:
                EditActivity();
                break;
            case R.id.aboutBtn:
                AboutActivity();
                break;
        }
        return true;
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, EditActivity.class);
        final int pos = recyclerView.getChildAdapterPosition(v);
        Note n = noteList.get(pos);
        String title = n.getTitle();
        String note = n.getText();
        intent.putExtra("Title", title);
        intent.putExtra("Note", note);
        intent.putExtra("Position", pos);
        startActivity(intent);
    }
    @Override
    public boolean onLongClick(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final int pos = recyclerView.getChildAdapterPosition(view);
        Note n = noteList.get(pos);
        final String title = n.getTitle();
        builder.setTitle("Delete Note '" + title + "'?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                noteList.remove(pos);
                noteAdapter.notifyDataSetChanged();
                try{
                    saveNotes();
                }
                catch (IOException e){
                    e.printStackTrace();
                }catch (JSONException e){
                    e.printStackTrace();
                }
                setTitle(String.format("Multi Note (%d)", noteList.size()));

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
  AlertDialog dialog = builder.create();
    dialog.show();

        return true;
    }
    public void EditActivity() {

        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);
    }
    public void AboutActivity() {

        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    protected void onPause () {
        try {
            saveNotes();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        super.onPause();
    }

    public void onResume () {

        if (!noteList.isEmpty()) {
            setTitle(String.format("Multi Note (%d)", noteList.size()));
        }
        super.onResume();
    }

    private void saveNotes () throws IOException, JSONException {

        FileOutputStream outputFile = getApplicationContext().
                openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);

        JSONArray jsonArray = new JSONArray();

        for (Note n : noteList) {
            JSONObject noteJSON = new JSONObject();
            noteJSON.put("titleText", n.getTitle());
            noteJSON.put("date", n.getDate());
            noteJSON.put("noteText", n.getText());
            jsonArray.put(noteJSON);
        }

        String jsonText = jsonArray.toString();

        outputFile.write(jsonText.getBytes());
        outputFile.close();
    }


    private void loadFile () {
        try {
            InputStream is = getApplicationContext().
                    openFileInput(getString(R.string.file_name));

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();

            JSONArray jsonArray = new JSONArray(sb.toString());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String title = jsonObject.getString("titleText");
                String date = jsonObject.getString("date");
                String note = jsonObject.getString("noteText");
                Note aNote = new Note(title, date, note);
               if(noteList.isEmpty())
                noteList.add(aNote);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


