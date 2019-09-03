package ru.pokrov.calc;


import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.InverseBindingAdapter;
import ru.pokrov.calc.databinding.ActivityMainBinding;
import ru.pokrov.calc.models.Calc;

import ru.pokrov.calc.parser.Token;
import ru.pokrov.calc.parser.ParserConstants;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // hide header in landscape orientation
        int display_mode = getResources().getConfiguration().orientation;
        if (display_mode == Configuration.ORIENTATION_LANDSCAPE) {
            requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
            getSupportActionBar().hide(); // hide the title bar
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
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

        if (start == 0) return;

        Token t = Calc.sTokenAt(start);
        if (t.kind != ParserConstants.NUMBER) {
            start = t.beginColumn-1;
            end = t.endColumn;
        } else if (start == end) {
            start -= 1;
        }

        binding.etInput.getText().delete(start, end);
    }

    public void onClickResult(View view) {
        binding.getCalc().accept();
    }

    private void addEtInput(String input) {
        binding.etInput.getText().replace(
                binding.etInput.getSelectionStart(), binding.etInput.getSelectionEnd(),
                input
        );
    }

    public void onClickInputDigit(View view) {
        Token t = Calc.sTokenAt(binding.etInput.getSelectionStart());
        if (t.kind == ParserConstants.RBR) {
            addEtInput("*"); // добввим умножение после закрывающей скобки
        }
        addEtInput(((Button)view).getText().toString());
    }

    // ввод бинарного оператора
    public void onClickInputBinOperator(View view) {
        Token t = Calc.sTokenAt(binding.etInput.getSelectionStart());
        if (t.kind != ParserConstants.NUMBER &&
                t.kind != ParserConstants.RBR &&
                    t.kind != ParserConstants.LBR) {
            onClickBackspace(view); // удаляем предыдущий оператор чтобы не возникало недопонимания
        }
        addEtInput(((Button)view).getText().toString());
    }

    // ввод выражения со скобками (в т.ч. унарный операторы)
    public void onClickInputBr(View view) {
        Token t = Calc.sTokenAt(binding.etInput.getSelectionStart());
        if (t.kind == ParserConstants.RBR || t.kind == ParserConstants.NUMBER) {
            addEtInput("*"); // добавим умножение если до этого было число или закрывающая скобка
        }
        addEtInput(((Button)view).getText().toString());
        binding.etInput.setSelection(binding.etInput.getSelectionStart() - 2); // перенесем курсор перед закрывающей кавычкой
    }
}
