package com.example.concurrencyconverter;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedHashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity{
    Map<String, Float> exchange2VND;
    ArrayAdapter<String> adapter;
    EditText editText1, editText2;
    Spinner spinner1, spinner2;
    Button clearBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        clearBtn = findViewById(R.id.clearBtn);
        //
        exchange2VND = new LinkedHashMap<>();
        String[] cr = {"USD", "VND", "AUD", "GBD", "EUR", "JPY", "RUB", "THB", "CNY", "SGD"};
        Float[] ex = {23177f, 1f, 16455f, 30348f, 27425f, 221.21f, 303.5814f, 743f, 3480.18f, 17.077f};
        for(int i = 0; i < cr.length; i++){
            exchange2VND.put(cr[i], ex[i]);
        }
        //
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, exchange2VND.keySet().toArray());
        //
        clearBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                editText1.setText("");
                editText2.setText("");
            }
        });
        //
        spinner1.setAdapter(adapter);
        spinner1.setSelection(1);
        spinner2.setAdapter(adapter);
        //
        editText1.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){

            }

            @Override
            public void afterTextChanged(Editable s){
                if(!editText1.isFocused())
                    return;
                try{
                    editText2.setText(convert(Float.parseFloat(editText1.getText() + ""), spinner1.getSelectedItem() + "", spinner2.getSelectedItem() + "") + "");
                }catch(Exception e){
                    editText1.setText("0");
                }finally{
                    return;
                }
            }
        });
        
        editText2.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){

            }

            @Override
            public void afterTextChanged(Editable s){
                if(!editText2.isFocused())
                    return;
                try{
                    editText1.setText(convert(Float.parseFloat(editText2.getText() + ""), spinner2.getSelectedItem() + "", spinner1.getSelectedItem() + "") + "");
                }catch(Exception e){
                    editText2.setText("0");
                }finally{

                }
            }
        });

    }

    //
    public Float convert(Float input, String from, String to){
        float rate = exchange2VND.get(to) / exchange2VND.get(from);
        return input / rate;
    }
}