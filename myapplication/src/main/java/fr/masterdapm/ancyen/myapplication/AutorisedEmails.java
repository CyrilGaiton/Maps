package fr.masterdapm.ancyen.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import fr.masterdapm.ancyen.model.Ride;

public class AutorisedEmails extends AppCompatActivity {

    EditText editText;
    LinearLayout lp;
    LinearLayout.LayoutParams llp;
    Ride ride;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autotised_emails);

        Bundle bd = getIntent().getExtras();
        if (bd != null){
            ride = (Ride) bd.get("ride");
        }

        lp = findViewById(R.id.lp);
    }

    public void add(View v){
        editText = new EditText(AutorisedEmails.this);
        llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        editText.setLayoutParams(llp);
        lp.addView(editText);
    }

    public void next(View v){
        List<String> strings = new ArrayList<>();
        for (int i=0; i<lp.getChildCount(); i++){
            editText = (EditText) lp.getChildAt(i);
            strings.add(editText.getText().toString());
        }

        Object[] objects = new Object[strings.size()+1];
        objects = strings.toArray();
        objects[strings.size()-1] = ride;
        new AsyncConnection().execute();
    }

    private class AsyncConnection extends AsyncTask<Object, Void, Void>{

        @Override
        protected Void doInBackground(Object... objects) {

            Connection connection = Conneciton.getInstance;

            Ride ride = (Ride) objects[objects.length-1];
            String[] strings = new String[objects.length];
            for(int i = 0; i<objects.length-1; i++){
                strings[i] = (String) objects[i];
            }

            Ride.set


            return null;
        }
    }
}
