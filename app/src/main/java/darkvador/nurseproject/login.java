package darkvador.nurseproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;


public class login extends ActionBarActivity {
    private AsyncTask<String, String, Boolean> mThreadCon = null;
    private Model model = new Model();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button connexion = (Button) findViewById(R.id.btn_connexion);

        if(Identity.id!=0){
            Infirmiere i = model.trouveInfirmiere();
            ajoutSession(i);
            Intent mi = new Intent(this, MainActivity.class);
            startActivity(mi);
            finish();
        }
        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String[] mesparams = { "http://rdelaporte.alwaysdata.net/auth.php", ((EditText) findViewById(R.id.field_pseudo)).getText().toString() };
                    mThreadCon = new Async (login.this).execute(mesparams);
            }
        });
    }
    public void retourImport(StringBuilder sb)
    {
        if (!sb.toString().equalsIgnoreCase("[]")||!sb.toString().equalsIgnoreCase("")) {
        JsonElement json = new JsonParser().parse(sb.toString());
            JsonArray varray = json.getAsJsonArray();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-mm-dd").create();
            Infirmiere i = new Infirmiere();
            if (varray.size() != 0) {
                i = gson.fromJson(new JsonParser().parse(sb.toString()).getAsJsonArray().get(0), Infirmiere.class);
                model.deleteInfirmiere();
                model.addInfirmiere(i);
                ajoutSession(i);
                Intent mi = new Intent(this, MainActivity.class);
                startActivity(mi);
                finish();
            }

        }else if(sb.toString().equalsIgnoreCase("")){
            Toast.makeText(this, "pas de connexion internet", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "mauvais pseudo sorry", Toast.LENGTH_SHORT).show();
        }
    }

    private void ajoutSession(Infirmiere i) {
        Identity.id= i.getId();
        Identity.nom=i.getNomInfirmiere();
        Identity.prenom=i.getPrenomIfirmiere();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
