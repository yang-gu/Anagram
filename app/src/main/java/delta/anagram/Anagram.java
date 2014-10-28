package delta.anagram;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Anagram extends Activity implements View.OnClickListener{
    private TextView fileNameView;
    private Button button;
    private ListView outputView;
    private AnagramOutputAdapter anagramOutputAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anagram);

        fileNameView = (TextView)findViewById(R.id.editText);
        button = (Button)findViewById(R.id.button);
        outputView = (ListView) findViewById(R.id.output);
        anagramOutputAdapter = new AnagramOutputAdapter(this, new ArrayList<List<String>>());
        outputView.setAdapter(anagramOutputAdapter);
        button.setOnClickListener(this);
        writeFileIfDoNotExist();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.anagram, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void writeFileIfDoNotExist(){
        String filename = "data";
        String string = "node done\nstop pots spot\npool loop polo";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String readFile(String fileName) throws Exception{
        BufferedReader bufferedReader;
        StringBuilder content = new StringBuilder();

        InputStream inputStream = openFileInput(fileName);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        bufferedReader = new BufferedReader(inputStreamReader);

        String line;

        while ((line = bufferedReader.readLine()) != null) {
            content.append(line);
            content.append("\n");
        }
        return content.toString();
    }

    private String[] getWordsFromFile(String fileName) throws Exception{
        String content = readFile(fileName);
        return content.split("[^\\w]+");
    }

    @Override
    public void onClick(View view) {
        String fileName = fileNameView.getText().toString();
        try {
            if(fileName.isEmpty())throw new IOException("File name is not valid");
            String[] content = getWordsFromFile(fileName);
            for(String word : content) Log.d("Word", word);
            AnagramLister anagramLister = new AnagramLister(new ArrayList<String>(Arrays.asList(content)));
            anagramOutputAdapter.setData(anagramLister.getOutput());
        } catch (Exception e) {
            Toast.makeText(this,"Error open file " + fileName, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
