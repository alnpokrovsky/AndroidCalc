package ru.pokrov.calc;


import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.InverseBindingAdapter;
import ru.pokrov.calc.databinding.ActivityMainBinding;
import ru.pokrov.calc.models.Calc;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // hide header in landscape orientation
        int display_mode = getResources().getConfiguration().orientation;
        if (display_mode == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportActionBar().hide();
        }

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.setCalc(Calc.sGet());
        binding.etInput.setShowSoftInputOnFocus(false);
    }

    @BindingAdapter("android:text")
    public static void setEtInput(EditText editText, CharSequence value) {
        if (!editText.getText().toString().equals(value.toString())) {
            int pos = editText.getSelectionStart(); // save cursor position before changing
            editText.setText(value);
            try {
                editText.setSelection(pos); // than back it to its place
            } catch (RuntimeException e) {
                Log.d("myLog", "cursor exception");
            }
        }
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static String getEtInput(EditText editText) {
        return editText.getText().toString();
    }


    public void onClickBackspace(View view) {
        int start = binding.etInput.getSelectionStart();
        int end = binding.etInput.getSelectionEnd();
        binding.etInput.getText().delete((start == end) ? start-1 : start, end);
    }

    public void onClickResult(View view) {
        binding.getCalc().accept();
    }

    public void onClickInput(View view) {
        binding.etInput.getText().replace(
                binding.etInput.getSelectionStart(), binding.etInput.getSelectionEnd(),
                ((Button)view).getText().toString()
        );
    }
}
