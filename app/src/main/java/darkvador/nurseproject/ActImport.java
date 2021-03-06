package darkvador.nurseproject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;


public class ActImport extends Fragment {
    private Button mBouton = null;
    private AsyncTask<String, String, Boolean> mThreadCon = null;
    private Model model= new Model();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_act_import, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        Button testcon= (Button) getView().findViewById(R.id.testcon);

        testcon.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String[] mesparams = { "http://rdelaporte.alwaysdata.net/import.php" };
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
            //patient.setCommentaireVisite("");
            listePatient.add(patient);
        }
        model.deletePatient();
        model.addPatient(listePatient);
        //alertmsg("Import terminé","Les "+listePatient.size()+" patients ont été ajouté à la base");
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getView().getContext());
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
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_test, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

}
