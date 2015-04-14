package darkvador.nurseproject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class AfficheListeActes extends Fragment {
    private ListView listView;
    private Model myModel=new Model();
    private ArrayList<Acte> listeActes;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_affiche_liste_actes, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        listeActes = myModel.listeActes();
        //setContentView(R.layout.activity_affiche_liste_acte);
        listView = (ListView)getView().findViewById(R.id.lvListe);
        ActeAdapter acteAdapter = new ActeAdapter(getView().getContext(), listeActes);
        listView.setAdapter(acteAdapter);
        /*listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Intent myIntent = new Intent(getView().getContext(), ModificationActe.class);
                myIntent.putExtra("idActe", listeActes.get(position).getId());
                startActivity(myIntent);
                Toast.makeText(getView().getContext(), "Choix : " + listeActes.get(position).getId(), Toast.LENGTH_LONG).show();
            }
        });*/ // MODIFICATION DE L'ACTE
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
