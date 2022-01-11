package com.example.projectmors;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText tekst = findViewById(R.id.I_Tekst);
        EditText mors = findViewById(R.id.I_Mors);

        //Tekst --> Kod
        tekst.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                String wpisane = tekst.getText().toString();
                String przetlumaczenie = tlumaczenie(wpisane);
                mors.setText(przetlumaczenie);


            }
        });

        //Kod --> Jezyk
        mors.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
    }


    public void Glownyprogram(View adoz)
    {

        //String kodmorsa = mors.getText().toString();

    }

    /* Funkacja odpowiedzialna za tlumaczenie*/
    public String tlumaczenie(String wpisane)
    {
        //Toast chmurka;
        //chmurka = Toast.makeText(this,"Wprowadzono złe dane",Toast.LENGTH_LONG);
        //chmurka.show();
        return wpisane;
    }

}