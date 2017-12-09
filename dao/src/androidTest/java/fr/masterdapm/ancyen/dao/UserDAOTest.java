package fr.masterdapm.ancyen.dao;

import android.content.Context;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.masterdapm.ancyen.model.User;

import static org.junit.Assert.*;

/**
 * Created by cyril on 02/12/17.
 */

@RunWith(AndroidJUnit4.class)
public class UserDAOTest {

    private UserDAO userDAO;
    private Context context;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getContext();
        userDAO = new UserDAO(context);
        userDAO.open();
    }

    @After
    public void tearDown() throws Exception {
        userDAO.close();
    }

    @Test
    public void add() throws Exception {
        long i = userDAO.add(new User(1, "cysou@", "123", "gaiton", "cyril"));
        assertTrue("add",  i != -1);
    }

    @Test
    public void mod() throws Exception {
        int i = userDAO.mod(new User(1, "lol@", "123", "gaiton", "cyril"));
        assertTrue("mod", i == 1);
    }

    @Test
    public void del() throws Exception {
        int i = userDAO.del(2);
        assertTrue("del", i == 0);
    }

    @Test
    public void get() throws Exception {
        User user = userDAO.get(1);
        assertTrue("get", user.getEmail().equals("lol@"));
    }

    @Test
    public void getAll() throws Exception {
        Cursor cursor = userDAO.getAll();
        assertTrue("getAll", cursor.getCount() != 1);
    }


}