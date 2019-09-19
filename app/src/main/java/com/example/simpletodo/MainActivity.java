package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;

public class MainActivity extends AppCompatActivity {

    List<String> items; //This is the list of items (mock data)

    Button btnAdd; //Handler to the Button
    EditText etItem; //Handler to the Text field
    RecyclerView rvItems; //Handler to the List
    itemsAdapter itemsAdapter; //Handler to our Adapter class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //sets the layout

        btnAdd = findViewById(R.id.btnAdd);
        etItem =  findViewById(R.id.etItem);
        rvItems =  findViewById(R.id.rvItems);

//        //Quick example to fill the text in the box
//        etItem.setText("I wrote this from Java!");

        //Mock Items for App Development
//        items = new ArrayList<>();
//        items.add("Kiss Annie");
//        items.add("Thank Annie");
//        items.add("Help Annie");

        //Load Items from storage
        loaditems();

        itemsAdapter.OnLongClickListener onLongClickListener = new itemsAdapter.OnLongClickListener(){
            //Override the method in this interface class
            @Override
            public void onItemLongClicked(int position) {
                //Delete the item from the model
                items.remove(position);
                //notify the adapter
                itemsAdapter.notifyItemRemoved(position);
                //Notify the user
                Toast.makeText(getApplicationContext(),"Item Removed", Toast.LENGTH_SHORT).show();
                //Save the change to storage
                saveItems();
            }
        };

        itemsAdapter = new itemsAdapter(items, onLongClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get the text from the text box and save it in a string
                String todoItem = etItem.getText().toString();
                //Add item to the model
                items.add(todoItem);
                //Notfiy the adapter that an item is inserted
                itemsAdapter.notifyItemInserted(items.size()-1);
                //Clear the edit text
                etItem.setText("");
                Toast.makeText(getApplicationContext(),"Item Added", Toast.LENGTH_SHORT).show();
                //Save the items to storage
                saveItems();
            }
        });

    }

    //Private Methods - only called here in Main

    //Returns the file in which we will store the to do list items
    private File getDataFile(){
        return new File(getFilesDir(), "ToDo_data.txt");
    }

    //This function will load items by reading every line of the data file
    private void loaditems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error Reading Items,e");
            items = new ArrayList<>();
        }
    }

    //This function saves by writing them into the data file
    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(),items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error Writing Items,e");
        }
    }

}
