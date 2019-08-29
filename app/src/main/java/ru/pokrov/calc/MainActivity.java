package ru.pokrov.calc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etInput;
    private TextView tvPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etInput = findViewById(R.id.etInput);
        etInput.setShowSoftInputOnFocus(false);
        tvPreview = findViewById(R.id.tvPreview);
    }

    public void onDigitClick(View view) {
        CharSequence btn = ((Button)view).getText();
        tvPreview.append(btn);
    }
}
