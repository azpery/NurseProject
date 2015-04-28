package darkvador.nurseproject;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import android.support.v4.app.FragmentActivity;

public class afficheVisite extends FragmentActivity {

    private FragmentPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager);

        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, AfficheListeActes.class.getName()));
        fragments.add(Fragment.instantiate(this, AfficheListePatient.class.getName()));

        this.mPagerAdapter = new MyPagerAdapter(super.getFragmentManager(), fragments);
        ViewPager pager = (ViewPager) super.findViewById(R.id.viewpager);

        pager.setAdapter(this.mPagerAdapter);



        Bundle b = getIntent().getExtras();
        String idPatient = b.getString("id");

        //Toast.makeText(getApplicationContext(), "Choix : " + idPatient, Toast.LENGTH_LONG).show();
        //remplir_champ(idPatient);
        /*((Button) findViewById(R.id.btnMap)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i=new Intent(v.getContext(), GoogleView.class);
                startActivity(i);
            }
        });
        ((Button) findViewById(R.id.btnListeActe)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Fragment fragment = new AfficheListeActes();
                Bundle args = new Bundle();
                fragment.setArguments(args);
                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.listeSoins, fragment)
                        .commit();

            }
        });*/
    }

    private void remplir_champ(String idPatient) {
        final Visite visite = (new Model()).trouveVisite(Integer.parseInt(idPatient));
        TextView textView = (TextView) findViewById(R.id.tvInfoClient);
        textView.setText(visite.getPrenomPatient()+" "+visite.getNomPatient());
        textView = (TextView) findViewById(R.id.tvDomicile);
        textView.setText(visite.getAdressePatient());
        ArrayList<Visite> v= new ArrayList<Visite>();
        v=(new Model()).listeVisite();
        textView = (TextView) findViewById(R.id.tvHorraire);
        textView.setText(visite.getDateDebut());
        EditText editText = (EditText) findViewById(R.id.etCommentaire);
        editText.setText(visite.getCommentaireVisite());
        ((EditText) findViewById(R.id.etCommentaire)).setSelected(false);
        Button buttonCancel = (Button) findViewById(R.id.bcanc);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        Button buttonOk = (Button) findViewById(R.id.bok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                visite.setCommentaireVisite(((EditText) findViewById(R.id.etCommentaire)).getText().toString());
                (new Model()).saveVisite(visite);
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_affiche_visite, menu);
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
