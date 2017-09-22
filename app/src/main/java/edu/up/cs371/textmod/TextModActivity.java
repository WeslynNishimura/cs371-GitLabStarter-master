package edu.up.cs371.textmod;

/**
 * class TextModActivity
 *
 * Allow text to be modified in simple ways with button-presses.
 */
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import java.util.ArrayList;
import android.widget.Button;
import android.text.InputFilter;
import android.widget.EditText;

import static edu.up.cs371.textmod.R.id.imageView;

public class TextModActivity extends ActionBarActivity {

    // array-list that contains our images to display
    private ArrayList<Bitmap> images;

    // instance variables containing widgets
    private ImageView imageView; // the view that shows the image

    // instance variables
    private Button copyName; // The "COPY NAME" button
    private Button reverseButton;
    private Button buttonClear;
    private Button buttonLowerCase;
    private EditText edit;
    Button upper;



    /**
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // perform superclass initialization; load the layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_mod);
        //set up the editText
        edit = (EditText)findViewById(R.id.editText);
        // set up the clear button
        buttonClear = (Button)findViewById(R.id.button);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit.setText("");
            }
        });

        // set up the lower case button
        buttonLowerCase = (Button)findViewById(R.id.button7);
        buttonLowerCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = "" + edit.getText();
                edit.setText(s.toLowerCase());
            }
        });

        upper = (Button)findViewById(R.id.button6);
        upper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = "" + edit.getText();
                edit.setText(s.toUpperCase());
            }
        });

        // set instance variables for our widgets
        imageView = (ImageView)findViewById(R.id.imageView);

        // Set up the spinner so that it shows the names in the spinner array resources
        //
        // get spinner object
        final Spinner spinner = (Spinner)findViewById(R.id.spinner);
        // get array of strings
        final String[] spinnerNames = getResources().getStringArray(R.array.spinner_names);
        // create adapter with the strings
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, spinnerNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // bind the spinner and adapter
        spinner.setAdapter(adapter);

        // load the images from the resources
        //
        // create the arraylist to hold the images
        images = new ArrayList<Bitmap>();
        // get array of image-resource IDs
        TypedArray imageIds2 = getResources().obtainTypedArray(R.array.imageIdArray);
        // loop through, adding one image per string
        for (int i = 0; i < spinnerNames.length; i++) {
            // determine the index; use 0 if out of bounds
            int id = imageIds2.getResourceId(i,0);
            if (id == 0) id = imageIds2.getResourceId(0,0);
            // load the image; add to arraylist
            Bitmap img = BitmapFactory.decodeResource(getResources(), id);
            images.add(img);
        }

        // define a listener for the spinner
        spinner.setOnItemSelectedListener(new MySpinnerListener());
        //set reverse button
        reverseButton = (Button) findViewById(R.id.button4);
        reverseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = "" + edit.getText();
                //edit.setText("pretend it reversed");
                edit.setText(reverse(text));
            }
        });

        // initialize the "COPY NAME" button
        copyName = (Button)findViewById(R.id.button2);
        edit = (EditText)findViewById(R.id.editText);
        copyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the selected spinner item
                String text = spinner.getSelectedItem().toString();
                int spinnerItem = spinner.getSelectedItemPosition();

                // get entered text
                String new1 = edit.getText() + text;

                // set the text to the editText
                edit.setText(new1);
            }
        });
    }


    public static String reverse(String text) {
        char[] input = text.toCharArray();
        int start = 0;
        int end = input.length - 1;
        char temp;
        while (end > start) {
            temp = input[start];
            input[start] = input[end];
            input[end] = temp;
            end--;
            start++;
        }
        return new String(input);
    }
    /**
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_text_mod, menu);
        return true;
    }

    /**
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * class that handles our spinner's selection events
     */
    private class MySpinnerListener implements OnItemSelectedListener {

        /**
         * @see android.widget.AdapterView.OnItemSelectedListener#onItemSelected(
         *                  android.widget.AdapterView, android.view.View, int, long)
         */
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
                                   int position, long id) {
            // set the image to the one corresponding to the index selected by the spinner
            imageView.setImageBitmap(images.get(position));
        }

        /**
         * @see android.widget.AdapterView.OnItemSelectedListener#onNothingSelected(
         *                  android.widget.AdapterView)
         */
        @Override
        public void onNothingSelected(AdapterView<?> parentView) {
            // your code here
        }
    }
}
