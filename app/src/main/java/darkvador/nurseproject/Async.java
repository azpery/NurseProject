package darkvador.nurseproject;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.db4o.internal.Null;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Async extends AsyncTask<String, String, Boolean> {
    // Référence à l'activité qui appelle
    private WeakReference<Fragment> fragmentAppelante = null;
    private String classFragmentAppelante;
    private WeakReference<ActionBarActivity> activityAppelante = null;
    private String classActivityAppelante;
    private StringBuilder stringBuilder = new StringBuilder();
    private Fragment mContext;
    private ActionBarActivity mActivityContext;
    //Constructeur pour les activités de type Fragment
    public Async (Fragment pFragment) {
        fragmentAppelante = new WeakReference<Fragment>(pFragment);
        classFragmentAppelante = pFragment.getClass().toString();
        this.mContext =pFragment;
    }
    //Constructeur pour des activité qui sont de type ActionBarActivity
    public Async (ActionBarActivity pActivity) {
        activityAppelante = new WeakReference<ActionBarActivity>(pActivity);
        classActivityAppelante = pActivity.getClass().toString();
        this.mActivityContext =pActivity;
    }
    @Override
    protected void onPreExecute () {// Au lancement, on envoie un message à l'appelant
        super.onPreExecute();
        if (fragmentAppelante != null) {
            if (fragmentAppelante.get() != null)
                //Toast.makeText(fragmentAppelante.get(), "Thread on démarre", Toast.LENGTH_SHORT).show();
                if (classFragmentAppelante.contains("ActImport") || classFragmentAppelante.contains("ActExport")) {
                    ((ProgressBar) mContext.getView().findViewById(R.id.progressBar)).setVisibility(View.VISIBLE);

                    ((ProgressBar) mContext.getView().findViewById(R.id.progressBar)).setProgress(20);
                }
        }
    }
    @Override
    protected void onPostExecute (Boolean result) {
        if (fragmentAppelante != null) {
            if (fragmentAppelante.get() != null) {
                if (result) {
                    TextView tv;
                    if (classFragmentAppelante.contains("ActImport") || classFragmentAppelante.contains("ActExport")) {
                        ((ProgressBar) mContext.getView().findViewById(R.id.progressBar)).setProgress(100);
                        tv = (TextView) mContext.getView().findViewById(R.id.tV);


                        tv.setVisibility(View.VISIBLE);
                    }
                    //pour exemple on appelle une méthode de l'appelant qui va gérer la fin ok du thread
                    if (classFragmentAppelante.contains("calendrier")) {
                        ((calendrier) fragmentAppelante.get()).retourImport(stringBuilder);
                    }
                    if (classFragmentAppelante.contains("ActImportAct")) {
                        tv = (TextView) mContext.getView().findViewById(R.id.tV);
                        tv.setText("Import Actes terminé avec succès");
                        ((ActImportActe) fragmentAppelante.get()).retourImport(stringBuilder);
                    } else if (classFragmentAppelante.contains("ActExport")) {
                        tv = (TextView) mContext.getView().findViewById(R.id.tV);
                        tv.setText("Export des données terminé avec succès");
                    } else if (classFragmentAppelante.contains("ActImport")) {
                        tv = (TextView) mContext.getView().findViewById(R.id.tV);
                        tv.setText("Import terminé avec succès");
                        ((ActImport) fragmentAppelante.get()).retourImport(stringBuilder);
                    }
                } else {
                }
                // Toast.makeText(fragmentAppelante.get(), "Fin ko", Toast.LENGTH_SHORT).show();
            }
        }else if (classActivityAppelante.contains("login")) {
            ((login) activityAppelante.get()).retourImport(stringBuilder);
        }
    }

    @Override
    protected Boolean doInBackground (String... params) {// Exécution en arrière plan
        String vUrl = "";

        String vlistpatient="";
        String vlistvisite="";
        if(fragmentAppelante!= null) {
            if ( classFragmentAppelante.contains("ActExport")||classFragmentAppelante.contains("ActImportActe")||classFragmentAppelante.contains("calendrier")) {
                vUrl = params[0];
                vlistpatient = params[1];
            } else {
                vUrl = params[0];
            }
        }else if(activityAppelante!=null){
            if (classActivityAppelante.contains("login")) {
                vUrl = params[0];
                vlistpatient = params[1];
            }
        }
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(vUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection
                    .setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setConnectTimeout(2000);
            OutputStreamWriter out = new OutputStreamWriter(
                    urlConnection.getOutputStream());
            // selon l'activity appelante on peut passer des paramètres en JSON exemple util pour export
            if(fragmentAppelante != null) {
                if (classFragmentAppelante.contains("ActExport")) {
                    // Création objet jsonn clé valeur
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("listpatient", vlistpatient);
                    out.write(jsonParam.toString());
                    out.flush();
                }else if(classFragmentAppelante.contains("ActImportActe")){
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("listpatient", Integer.parseInt(vlistpatient));
                    out.write(jsonParam.toString());
                    out.flush();
                }else if(classFragmentAppelante.contains("calendrier")) {
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("id", Integer.parseInt(vlistpatient));
                    out.write(jsonParam.toString());
                    out.flush();
                }
            }else if(classActivityAppelante.contains("login")){
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("pseudo", vlistpatient);
                out.write(jsonParam.toString());
                out.flush();
            }
            out.close();
            // récupération du serveur
            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    stringBuilder.append(line);
                }
                br.close();
               // String[] vstring0 = { "Reçu du serveur",stringBuilder.toString() };

                //publishProgress(vstring0);
            } else {
                String[] vstring0 = { "Erreur",
                        urlConnection.getResponseMessage() };
                publishProgress(vstring0);
            }
        } catch (MalformedURLException e) {

            String[] vstring0 = { "Erreur", "Pbs url" };
            publishProgress(vstring0);
            return false;
        } catch (java.net.SocketTimeoutException e) {
            String[] vstring0 = { "Erreur", "temps trop long" };
            publishProgress(vstring0);
            return false;
        } catch (IOException e) {

            String[] vstring0 = { "Erreur", "Pbs IO" };
            publishProgress(vstring0);
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        if(fragmentAppelante!=null) {
            if (!classFragmentAppelante.contains("calendrier")) {
                ((ProgressBar) mContext.getView().findViewById(R.id.progressBar)).setProgress(50);
            }
        }
        return true;
    }
    @Override
    protected void onProgressUpdate(String... param) {
        // utilisation de on progress pour afficher des message pendant le
        // doInBackground
        if (fragmentAppelante != null) {

            if (classFragmentAppelante.contains("ActImport")) {
                //((ActImport) fragmentAppelante.get()).alertmsg (param[0].toString(), param[1].toString());
            }
            if (!classFragmentAppelante.contains("calendrier")) {
                ((ProgressBar) mContext.getView().findViewById(R.id.progressBar)).setProgress(50);
            }
            //Toast.makeText(fragmentAppelante.get(), param[0].toString(), Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    protected void onCancelled () {
        if(fragmentAppelante.get() != null);
            //Toast.makeText(fragmentAppelante.get(), "Annulation", Toast.LENGTH_SHORT).show();
    }
}