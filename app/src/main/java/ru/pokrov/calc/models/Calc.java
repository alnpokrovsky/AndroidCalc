package ru.pokrov.calc.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class Calc extends BaseObservable {

    static Calc sCalc = new Calc();

    private String input = "";
    private String result = "";

    public static Calc sGet() {
        return sCalc;
    }

    private Calc() {
        super();
    }


    @Bindable
    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
        notifyPropertyChanged(BR.input);
    }

    @Bindable
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
        notifyPropertyChanged(BR.result);
    }

    public void accept() {
        this.setInput(this.getResult());
        this.setResult("");
    }
}
