package ru.pokrov.calc.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import ru.pokrov.calc.parser.Parser;
import ru.pokrov.calc.parser.ParseException;
import ru.pokrov.calc.parser.Token;

public class Calc extends BaseObservable {

    static Calc sCalc = new Calc();

    private String input = "";
    private String result = "";

    public static Calc sGet() {
        return sCalc;
    }

    public static Token sTokenAt(int pos) {
        return Parser.getTokenAt(sCalc.input, pos);
    }

    private Calc() {
        super();
    }


    @Bindable
    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        try {
            this.input = input;
            notifyPropertyChanged(BR.input);
            setResult(Parser.Parce(input) + "");
        } catch (Exception e) {
            setResult("");
        }
    }

    @Bindable
    public String getResult() {
        return result;
    }

    private void setResult(String result) {
        this.result = result;
        notifyPropertyChanged(BR.result);
    }

    public void accept() {
        this.setInput(this.getResult());
        this.setResult("");
    }
}
