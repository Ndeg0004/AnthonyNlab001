package com.example.andriodlabs;

import android.text.Editable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;



import android.os.Bundle;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private CheckBox checkbox;
    private Switch switch1;
    private Button button;
    private TextView textView;
    private ImageButton imageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextText2);
        textView = findViewById(R.id.love);
        checkbox = findViewById(R.id.checkBox);
        switch1 = findViewById(R.id.switch1);
        button = findViewById(R.id.button);
        imageButton = findViewById(R.id.imageButton);
        button.setOnClickListener(v -> {
            Editable text = editText.getText();
            textView.setText(text);
            // Show Toast with translated message
            String toastMessage = getResources().getString(R.string.toast_message);
            Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
        });
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    // nesting practice
//                    String text = editText.getText().toString();
//
//                    if (text.contains("Love")) {
//                        Toast.makeText(MainActivity.this, "The switch is On" + text, Toast.LENGTH_SHORT).show();
//                        // nested if statement
//                    } else {
//                        Toast.makeText(MainActivity.this, "Love", Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(MainActivity.this, "The Switch is ON", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "The Switch is Off", Toast.LENGTH_SHORT).show();
                }
            }
        });


        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String state = isChecked ? "on" : "off";
                Snackbar.make(buttonView, "The checkbox is now " + state, BaseTransientBottomBar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkbox.setChecked(!isChecked);
                            }
                        }).show();
            }
        });

    }
}