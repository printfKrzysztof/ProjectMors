package com.example.projectmors;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class PodpowiedzActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.podpowiedz);
    }

    //Cofanie z podpowiedzi
    public void Cofanie(View adoz){
        finish();
    }
}
