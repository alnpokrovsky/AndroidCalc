package ru.pokrov.calc.models;

import android.widget.EditText;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class Calc extends BaseObservable {
    public enum BaseToken {
        TOK_0,
        TOK_1,
        TOK_2,
        TOK_3,
        TOK_4,
        TOK_5,
        TOK_6,
        TOK_7,
        TOK_8,
        TOK_9,
    }

    private String input = "";

    public Calc() {
        super();
        setInput("0");
    }


    @Bindable
    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
        notifyPropertyChanged(BR.input);
    }

    public void onAdd(BaseToken tok) {
        switch (tok) {
            case TOK_0:
            case TOK_1:
            case TOK_2:
            case TOK_3:
            case TOK_4:
            case TOK_5:
            case TOK_6:
            case TOK_7:
            case TOK_8:
            case TOK_9:
                setInput(tok.ordinal() + "");
                break;
        }
    }
}
