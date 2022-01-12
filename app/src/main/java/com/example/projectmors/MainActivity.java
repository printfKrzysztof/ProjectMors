package com.example.projectmors;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Button stoppopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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
                String wpisane = mors.getText().toString();
                String przetlumaczenie = tlumaczeniewdrugostrone(wpisane);
                tekst.setText(przetlumaczenie);

            }
        });
    }


    public void OnClick(View adoz)
    {
        //Pobierasz tekst i na lampkę
        //String kodmorsa = mors.getText().toString();
        createNewDialog();

    }

    /* Funkacja odpowiedzialna za tlumaczenie*/
    public String tlumaczenie(String wpisane)
    {
        //Toast chmurka;
        //chmurka = Toast.makeText(this,"Wprowadzono złe dane",Toast.LENGTH_LONG);
        //chmurka.show();
        return wpisane;
    }

    public String tlumaczeniewdrugostrone(String wpisane)
    {
        //Toast chmurka;
        //chmurka = Toast.makeText(this,"Wprowadzono złe dane",Toast.LENGTH_LONG);
        //chmurka.show();
        return wpisane;
    }

    public void createNewDialog()
    {
        dialogBuilder = new AlertDialog.Builder(this);
        final View Popup = getLayoutInflater().inflate(R.layout.popup, null);

        stoppopup = (Button) Popup.findViewById(R.id.stoper);

        dialogBuilder.setView(Popup);
        dialog = dialogBuilder.create();
        dialog.show();

        stoppopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dodanie tego ze mozna przerwac wczesniej
                dialog.dismiss(); //znika okno
            }
        });

    }

}