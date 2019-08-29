package ru.pokrov.calc;


import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.InverseBindingAdapter;

import ru.pokrov.calc.databinding.ActivityMainBinding;
import ru.pokrov.calc.models.Calc;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.setCalc(new Calc());

        EditText etInput = findViewById(R.id.etInput);
        etInput.setShowSoftInputOnFocus(false);
    }

    @BindingAdapter("android:text")
    public static void setEtInput(EditText editText, CharSequence value) {
        if (!editText.getText().toString().equals(value.toString())) {
            int pos = editText.getSelectionStart();
            editText.setText(value);
            editText.setSelection(pos);
        }
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static String getEtInput(EditText editText) {
        return editText.getText().toString();
    }

}
