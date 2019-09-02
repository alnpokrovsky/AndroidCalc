package ru.pokrov.calc.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import ru.pokrov.calc.parser.Parser;
import ru.pokrov.calc.parser.ParseException;

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
        InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        this.input = input;
        notifyPropertyChanged(BR.input);

        try {
            Parser parser = new Parser(stream);
            setResult("" + parser.Parce());
        } catch (Exception e) {
            setResult("");
        }
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
