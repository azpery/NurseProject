package darkvador.nurseproject;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;


public class ModificationPatient extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification_patient);
        Bundle b = getIntent().getExtras();
        String idPatient = b.getString("idPatient");
        //Toast.makeText(getApplicationContext(), "Choix : " + idPatient, Toast.LENGTH_LONG).show();
        remplir_champ(idPatient);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_modification_patient, menu);
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
    public void remplir_champ(String idPatient){
        final Patient patient = (new Model()).trouvePatient(idPatient);
        TextView textView = (TextView) findViewById(R.id.c_id);
        textView.setText(patient.getIdentifiant());
        textView = (TextView) findViewById(R.id.c_nom);
        textView.setText(patient.getNom());
        textView = (TextView) findViewById(R.id.c_prenom);
        textView.setText(patient.getPrenom());
        textView = (TextView) findViewById(R.id.c_ad);
        textView.setText(patient.getAdresse());
        textView = (TextView) findViewById(R.id.c_cp);
        textView.setText(patient.getCodePostal());
        textView = (TextView) findViewById(R.id.c_ville);
        textView.setText(patient.getVille());
        textView = (TextView) findViewById(R.id.c_datenaiss);
        textView.setText(new SimpleDateFormat("dd/MM/yyyy").format(patient
                .getDateNaiss()));
        textView = (TextView) findViewById(R.id.c_tl);
        String sTelephone = patient.getTelephone().toString();
        sTelephone = String.format("%s.%s.%s.%s.%s",
                sTelephone.substring(0, 2), sTelephone.substring(2, 4),
                sTelephone.substring(4, 6), sTelephone.substring(6, 8),
                sTelephone.substring(8, 10));
        textView.setText(sTelephone);
        EditText editText = (EditText) findViewById(R.id.ecommentaire);
        editText.setText(patient.getCommentaireVisite());
        ((EditText) findViewById(R.id.ecommentaire)).setSelected(false);
        Button buttonCancel = (Button) findViewById(R.id.bcanc);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        Button buttonOk = (Button) findViewById(R.id.bok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                patient.setCommentaireVisite(((EditText) findViewById(R.id.ecommentaire))
                        .getText().toString());
                (new Model()).savePatient(patient);
                finish();
            }
        });
    }
}
