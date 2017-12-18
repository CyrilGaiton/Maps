package fr.masterdapm.ancyen.myapplication;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import fr.masterdapm.ancyen.dao.UserDAO;
import fr.masterdapm.ancyen.model.User;

public class MainActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);
    }

    public void go(View v){
        UserDAO userDAO = new UserDAO(MainActivity.this);
        userDAO.add(new User("jd@gmail", "mdp1", "dupond", "jean"));
        Cursor c = userDAO.getAll();
        while
    }
}
