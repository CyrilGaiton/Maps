package fr.masterdapm.ancyen.myapplication;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import fr.masterdapm.ancyen.dao.RideDAO;
import fr.masterdapm.ancyen.dao.UserDAO;
import fr.masterdapm.ancyen.model.Position;
import fr.masterdapm.ancyen.model.Ride;
import fr.masterdapm.ancyen.model.User;
import fr.masterdapm.ancyen.model.Waypoint;

public class MainActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);
    }

    public void go(View v) {
//        UserDAO userDAO = new UserDAO(MainActivity.this);
//        userDAO.open();
//        userDAO.add(new User("jd@gmail", "mdp1", "dupond", "jean"));
//        userDAO.add(new User("jp@gmail", "mdp1", "pond", "jean"));
//        User u = userDAO.get("jd@gmail");
//        Log.e("lolmdr", u.getEmail());
//        Cursor c = userDAO.getAll();
//        while(c.moveToNext()){
//            Log.e("lolmdr", c.getString(c.getColumnIndexOrThrow("email")));
//        }
//        userDAO.close();


        Position[] positions = {new Position(1, 2, 3),
                new Position(4, 5, 6)};
        Waypoint[] waypoints = {new Waypoint(1, 2, 3, "foret"),
                new Waypoint(4, 5, 6, "lac")};
        String[] autoEmails = {"lol@", "mdr@"};

        RideDAO rideDAO = new RideDAO(MainActivity.this);
        rideDAO.open();
        rideDAO.add(new Ride(1, "jd@gmail.com", "depart"
                , "depardate", "depar hour",
                "arric place", "28km", "3h38",
                positions, waypoints, autoEmails, "wait"));

        Ride r = rideDAO.get(1);
        Log.e("lolmdr", r.getAutorisedEmails()[1]);
        Cursor c = rideDAO.getAll();
        while (c.moveToNext()) {
            Log.e("lolmdr", c.getString(c.getColumnIndexOrThrow("arrivalPlace")));
        }
        rideDAO.close();
    }
}
