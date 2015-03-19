package darkvador.nurseproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class AfficheListePatient extends ActionBarActivity {
    private ListView listView;
    private Model myModel=new Model();
    private ArrayList<Patient> listePatient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listePatient=myModel.listePatient();
        setContentView(R.layout.activity_affiche_liste_patient);
        listView = (ListView)findViewById(R.id.lvListe);
        PatientAdapter patientAdapter = new PatientAdapter(this, listePatient);
        listView.setAdapter(patientAdapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Intent myIntent = new Intent(getApplicationContext(), ModificationPatient.class);
                myIntent.putExtra("idPatient", listePatient.get(position).getIdentifiant());
                startActivity(myIntent);
                Toast.makeText(getApplicationContext(), "Choix : " + listePatient.get(position).getIdentifiant(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_affiche_liste_patient, menu);
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
