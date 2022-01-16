package com.example.projectmors;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private CameraManager mFLASHManager;
    private String FLASHId;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Button stoppopup;

    public String[] Litery = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
            "n", "o", "p", "r", "s", "t", "u", "w", "y", "z", "x", "q", "ą", "ć", "ę", "ł",
            "ń", "ó", "ś", "ż", "ź", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", " ",
            ".", ",", "'", "_", ":", ";", "?", "!", "-", "+", "/", "(", ")", "=", "@", "v"};

    public String[] Mors = {".", "...", "..", "..", ".", "...", ".", "....", "..", "._", ".", "...", "__",
            ".", "", "..", "..", "...", "", "..", ".", "_.", "..", "..", ".", "..", "...", "....", "...",
            ".", ".", "......", "...", "__..", "._", "..", "...", "....", ".....", "....", "...", "..", "__.", "__", "/",
            "...", "..", ".__.", "...", "...", "...", "....", "..", "....", "...", "...", "_..", ".__.", "...", "._..", "..._"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        EditText tekst = findViewById(R.id.I_Tekst);
        EditText mors = findViewById(R.id.I_Mors);
        Switch zmiana = findViewById(R.id.switch1);

        zmiana.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TextView tekst1 = findViewById(R.id.I_tekst1);
                TextView tekst2 = findViewById(R.id.I_text2);
                if (isChecked) {

                    tekst1.setText("Kod Morsa");
                    tekst2.setText("Tekst");
                    Editable tymczasowa = tekst.getText();
                    tekst.setText(mors.getText());
                    mors.setText(tymczasowa);
                } else {
                    tekst2.setText("Kod Morsa");
                    tekst1.setText("Tekst");
                    Editable tymczasowa = tekst.getText();
                    tekst.setText(mors.getText());
                    mors.setText(tymczasowa);
                }
            }
        });
        //Tekst --> Kod
        tekst.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                String wpisane = tekst.getText().toString();
                String przetlumaczenie;
                if (zmiana.isChecked()) {
                    przetlumaczenie = tlumaczeniewdrugostrone(wpisane);
                } else {
                    przetlumaczenie = tlumaczenie(wpisane);
                }
                mors.setText(przetlumaczenie);

            }
        });

    }


    public void OnClick(View adoz) {

        boolean FLASHYES = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!FLASHYES) {
            Flasherror();
        } else {
            //Pobierasz tekst i na lampkę
            //String kodmorsa = mors.getText().toString();
            Niechsieswieci();
            createNewDialog();
        }


    }

    private void Flasherror() {
        AlertDialog info = new AlertDialog.Builder(this).create();
        info.setTitle(">>>ERROR<<<");
        info.setMessage("Latarka nie jest dostępna na tym urządzeniu");

        info.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                info.dismiss();
            }
        });
        info.show();
        //Toast chmurka;
        //chmurka = Toast.makeText(this,"Wprowadzono złe dane",Toast.LENGTH_LONG);
        //chmurka.show();
    }

    /* Funkacja odpowiedzialna za tlumaczenie*/
    public String tlumaczenie(String wpisane) {
        wpisane = wpisane.toLowerCase();
        String Tabela[] = wpisane.split("");

        wpisane = "";

        for (int i = 0; Tabela.length > i; i++) {

            int a = 0;

            for (int j = 0; j < Litery.length; j++) {

                String ku = Tabela[i];
                String ba = Litery[j];
                if (ku.equals(ba)) {
                    wpisane = wpisane + Mors[j];
                }

            }


        }
        //Toast chmurka;
        //chmurka = Toast.makeText(this,"Wprowadzono złe dane",Toast.LENGTH_LONG);
        //chmurka.show();
        return wpisane;
    }

    public String tlumaczeniewdrugostrone(String wpisane) {
        wpisane = "Z morsa na litery";
        return wpisane;
    }

    public void createNewDialog() {
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


    public void Niechsieswieci() {
        mFLASHManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            FLASHId = mFLASHManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        EditText lat = findViewById(R.id.I_Mors);
        String LIGHT[] = lat.getText().toString().split("");


        for (int i = 0; LIGHT.length > i; i++) {
            try {

                mFLASHManager.setTorchMode(FLASHId, true); //włączenie latarki
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }

            //odpowiednia długość sygnału
            if ( LIGHT[i].equals("."))
            {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }else if ( LIGHT[i].equals("_"))
            {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }else if( LIGHT[i].equals("/"))
            {
                try {
                    TimeUnit.SECONDS.sleep(7);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }else
                {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {

                mFLASHManager.setTorchMode(FLASHId, false); //włączenie latarki
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



        }
    }
}