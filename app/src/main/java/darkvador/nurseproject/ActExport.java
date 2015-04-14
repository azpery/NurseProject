package darkvador.nurseproject;

import android.app.Fragment;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.nio.charset.Charset;
import java.util.ArrayList;


public class ActExport extends Fragment {
    private Button mBouton = null;
    private AsyncTask<String, String, Boolean> mThreadCon = null;
    String sPatient="";
    Model model= new Model();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_act_export, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        Model modele = new Model();
        ArrayList<Patient> listPatient = modele.listePatient();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss.S")
                .create();
        int i = 0;
        for (Patient vpatient : listPatient) {
            vpatient.setCommentaireVisite(new String(vpatient.getCommentaireVisite().getBytes(), Charset.forName("UTF-8")));
            sPatient = sPatient + gson.toJson(vpatient);
            i++;
            if (i < listPatient.size()) {
                sPatient = sPatient + "@@@";
            }
        }
        Button testcon= (Button) getView().findViewById(R.id.testcon);

        testcon.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String[] mesparams = { "http://rdelaporte.alwaysdata.net/export.php",sPatient};
                mThreadCon = new Async (ActExport.this).execute(mesparams);
                model.deletePatient();
            }
        });

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
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_test, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
