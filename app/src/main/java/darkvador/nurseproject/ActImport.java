package darkvador.nurseproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;


public class ActImport extends ActionBarActivity {
    private Button mBouton = null;
    private AsyncTask<String, String, Boolean> mThreadCon = null;
    private Model model= new Model();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_import);
        Button testcon= (Button) findViewById(R.id.testcon);

        testcon.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String[] mesparams = { "http://10.0.3.2/patient/import.php" };
                mThreadCon = new Async (ActImport.this).execute(mesparams);
            }
        });

    }
    public void retourImport(StringBuilder sb)
    {
        JsonElement json = new JsonParser().parse(sb.toString());
        JsonArray varray = json.getAsJsonArray();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-mm-dd").create();
        ArrayList<Patient> listePatient = new ArrayList<Patient>();
        for (JsonElement obj : varray) {
            Patient patient = gson.fromJson(obj.getAsJsonObject(), Patient.class);
            patient.setCommentaireVisite("");
            listePatient.add(patient);
        }
        model.deletePatient();
        model.addPatient(listePatient);
        //alertmsg("Import terminé","Les "+listePatient.size()+" patients ont été ajouté à la base");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_act_import, menu);
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
    public void alertmsg(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage(msg)
                .setTitle(title);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });

        AlertDialog dialog = builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.
                TYPE_SYSTEM_ALERT);
        dialog.show();
    }


}
