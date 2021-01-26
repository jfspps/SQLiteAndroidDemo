package jamesapps.example.sqlitedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // use the default CursorFactory, hence null
        SQLiteDatabase sqLiteDatabase = getBaseContext().openOrCreateDatabase("sqlite-test.db", MODE_PRIVATE, null);
        String createTable = "CREATE TABLE IF NOT EXISTS contacts(name TEXT, phone INTEGER, email TEXT);";
        String insertFirst = "INSERT INTO contacts VALUES('James', 123456, 'noreply.com');";
        String insertSecond = "INSERT INTO contacts VALUES('Mary', 098765, 'noreply2.com');";

        sqLiteDatabase.execSQL(createTable);
        Log.d(TAG, "onCreate: SQL is " + createTable);
        sqLiteDatabase.execSQL(insertFirst);
        Log.d(TAG, "onCreate: SQL is " + insertFirst);
        sqLiteDatabase.execSQL(insertSecond);
        Log.d(TAG, "onCreate: SQL is " + insertSecond);

        // a cursor points to individual records in the DB prior to retrieving it
        Cursor query = sqLiteDatabase.rawQuery("SELECT * FROM contacts;", null);

        // check there is one record present when cursor moves to first (indices match the DB column no.s)
        if (query.moveToFirst()){
            do {
                String name = query.getString(0);
                int phone = query.getInt(1);
                String email = query.getString(2);
                // Toast messages queue up; note that the above INSERT statements are run each time the app is re-run so the DB gets
                // larger and the number of Toast messages displayed increases
                Toast.makeText(this, "Name = " + name + " phone = " + phone + " email = " + email, Toast.LENGTH_LONG).show();
            } while(query.moveToNext());
        }
        query.close();
        sqLiteDatabase.close();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}