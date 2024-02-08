package com.example.andriodlabs;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private static final int NAME_REQUEST_CODE = 1;
    private EditText editText;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        Button nextButton = findViewById(R.id.nextButton);

        sharedPreferences = getPreferences(MODE_PRIVATE);

        String savedName = sharedPreferences.getString("name", "");
        editText.setText(savedName);

     //ActivityResualtLauncher

        ActivityResultLauncher<Intent> nameActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String returnedName = data.getStringExtra("name");
                            editText.setText(returnedName);
                        }
                    }
                }
        );

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredName = editText.getText().toString();
                Intent intent = new Intent(MainActivity.this, NameActivity.class);
                intent.putExtra("name", enteredName);
                nameActivityResultLauncher.launch(intent);

            }

        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", editText.getText().toString());
        editor.apply();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NAME_REQUEST_CODE) {
            if (resultCode == 0) {
                // User wants to change their name
                // Handle this case as needed
            } else if (resultCode == 1) {
                // User is happy
                // Handle this case as needed
            }

        }}}