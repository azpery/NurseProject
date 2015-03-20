package darkvador.nurseproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    Model myModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myModel= new Model();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_list:
                Intent IntentListePatient = new Intent(getApplicationContext(), AfficheListePatient.class);
                startActivity(IntentListePatient);
                return true;
            case R.id.menu_import:
                Intent IntentImport = new Intent(getApplicationContext(), ActImport.class);
                startActivity(IntentImport);

                return true;
            case R.id.menu_export:
                Intent IntentExport = new Intent(getApplicationContext(), ActExport.class);
                startActivity(IntentExport);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
