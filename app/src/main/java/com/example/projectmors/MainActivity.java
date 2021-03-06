package com.example.projectmors;

//Importy
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
    public int error = 0;
    //Tablice do tłumaczenia
    public String[] Litery = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
            "n", "o", "p", "r", "s", "t", "u", "w", "y", "z", "x", "q", "ą", "ć", "ę", "ł",
            "ń", "ó", "ś", "ż", "ź", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", " ",
            ".", ",", "'", "_", ":", ";", "?", "!", "-", "+", "/", "(", ")", "=", "@", "v",""};

    public String[] Mors = {"._", "_...", "_._.", "_..", ".", ".._.", "__.", "....", "..", ".___", "_._", "._..", "__",
            "_.", "___", ".__.", "._.", "...", "_", ".._", ".__", "_.__", "__..", "_.._", "__._", "._._", "_._..", ".._..", "._.._",
            "__.__", "___.", "..._...", "__.._.", "__.._", ".____", "..___", "...__", "...._", ".....", "_....", "__...", "___..", "____.", "_____", "/",
            "._._._", "__..__", ".____.", "..__._", "___...", "_._._.", "..__..", "_._.__", "_...._", "._._.", "_.._.", "_.__.", "_.__._", "_..._", ".__._.", "..._",""};



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Tylko orientacja pozioma
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        EditText tekst = findViewById(R.id.I_Tekst);
        EditText mors = findViewById(R.id.I_Mors);
        Switch zmiana = findViewById(R.id.switch1);

        //Nasłuchiwanie zmian przycisku
        zmiana.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TextView tekst1 = findViewById(R.id.I_tekst1);
                TextView tekst2 = findViewById(R.id.I_text2);
                if (isChecked) {

                    tekst1.setText("Kod Morsa");
                    tekst2.setText("Tekst");

                    tekst.setText("... ___ ... ");
                    mors.setText("sos ");
                } else {
                    tekst2.setText("Kod Morsa");
                    tekst1.setText("Tekst");
                    tekst.setText("Tutaj wpisz tekst");
                }
            }
        });

        //Nasłuchiwanie zmian tekstu
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

    //Część kodu odpowiedzialna za włączanie lampki
    public void OnClick(View adoz) {

        boolean FLASHYES = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);


            if (!FLASHYES) {
                Flasherror();
            }
            else
                {
                    //Kod odpowiedzialny za świecenie
                    Niechsieswieci();
                }



    }

    //Flash - error (czyli nie mamy błysku w telefonie)
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
    }

    //Tekst --> Kod
    public String tlumaczenie(String wpisane) {

        wpisane = wpisane.toLowerCase();
        String[] Tabela = wpisane.split("");

        wpisane = "";

        for (int i = 0; Tabela.length > i; i++) {

            int x = 0;

            for (int j = 0; j < Litery.length; j++) {

                String a = Tabela[i];
                String b = Litery[j];
                if (a.equals(b)) {
                    wpisane = wpisane + Mors[j];
                    x=1;
                }

            }
            //Obsługa błedu
            if (x==0 && error==0)
            {
                AlertDialog info = new AlertDialog.Builder(this).create();
                info.setTitle("NIEDOPUSZCZALNY ZNAK");
                info.setMessage("W polu znajduje się nieprawidłowy znak");

                info.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        info.dismiss();
                        error=0;
                    }
                });
                info.show();
                error=1;
            }


        }
        return wpisane;
    }

    //Kod --> Tekst
    public String tlumaczeniewdrugostrone(String wpisane) {

        String[] TabelaMors = wpisane.split(" ");
        wpisane = "";

        for (int i = 0; TabelaMors.length > i; i++) {

            int x = 0;

            for (int j = 0; j < Mors.length; j++) {

                String a = TabelaMors[i];
                String b = Mors[j];
                if (a.equals(b)) {
                    wpisane = wpisane + Litery[j];
                    x=1;
                }

            }
            //Obsługa błedu
            if (x==0 && error==0)
            {
                AlertDialog info = new AlertDialog.Builder(this).create();
                info.setTitle("NIEDOPUSZCZALNY ZNAK");
                info.setMessage("W polu znajduje się nieprawidłowy znak");

                info.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        info.dismiss();
                        error=0;
                    }
                });
                info.show();
                error=1;
            }


        }
        return wpisane;
    }



    //Funkcja obsługująca latarkę
    public void Niechsieswieci() {
        mFLASHManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            FLASHId = mFLASHManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        EditText lat = findViewById(R.id.I_Mors);
        String[] LIGHT = lat.getText().toString().split("");


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
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }else if( LIGHT[i].equals("/"))
            {
                try {

                    mFLASHManager.setTorchMode(FLASHId, false); //włączenie latarki
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
                try {
                    TimeUnit.SECONDS.sleep(4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }else
            {
                Toast chmurka;
                chmurka = Toast.makeText(this,"Nadawanie nie powiodlo sie",Toast.LENGTH_LONG);
                chmurka.show();
                break;

            }

            try {

                mFLASHManager.setTorchMode(FLASHId, false); //włączenie latarki
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }


        }
        try {

            mFLASHManager.setTorchMode(FLASHId, false); //włączenie latarki
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    //Podpowiedź dla użytkownika
    public void Podpowiedz(View adoz){
        Intent intent = new Intent(this, PodpowiedzActivity.class);
        startActivity(intent);

    }

}