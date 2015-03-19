package darkvador.nurseproject;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    private WeakReference<Activity> activityAppelante = null;
    private String classActivityAppelante;
    private StringBuilder stringBuilder = new StringBuilder();
    private Activity mContext;

    public Async (Activity pActivity) {
        activityAppelante = new WeakReference<Activity>(pActivity);
        classActivityAppelante = pActivity.getClass().toString();
        this.mContext =pActivity;
    }

    @Override
    protected void onPreExecute () {// Au lancement, on envoie un message à l'appelant
        super.onPreExecute();
        if (activityAppelante.get() != null)
            //Toast.makeText(activityAppelante.get(), "Thread on démarre", Toast.LENGTH_SHORT).show();
         ((ProgressBar) mContext.findViewById(R.id.progressBar)).setVisibility(View.VISIBLE);

        ((ProgressBar) mContext.findViewById(R.id.progressBar)).setProgress(20);

    }

    @Override
    protected void onPostExecute (Boolean result) {
        if (activityAppelante.get() != null) {
            if (result) {
                ((ProgressBar) mContext.findViewById(R.id.progressBar)).setProgress(100);
                TextView tv =(TextView) mContext.findViewById(R.id.tV);
                tv.setText("Import terminé avec succès");
                tv.setVisibility(View.VISIBLE);
//pour exemple on appelle une méthode de l'appelant qui va gérer la fin ok du thread
                if (classActivityAppelante.contains("ActImport"))
                {
                    ((ActImport) activityAppelante.get()).retourImport (stringBuilder);
                }
            }
            else
                Toast.makeText(activityAppelante.get(), "Fin ko",
                        Toast.LENGTH_SHORT).show();		}
    }

    @Override
    protected Boolean doInBackground (String... params) {// Exécution en arrière plan
        String vUrl = "";
        if (classActivityAppelante.contains("ActImport")) {
            vUrl = params[0];

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
            if (classActivityAppelante.contains("xxxxxxxxxxx"))
            {
                // Création objet jsonn clé valeur
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("xxxx", "jug");
                jsonParam.put("xxxx", "jug");
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
        ((ProgressBar) mContext.findViewById(R.id.progressBar)).setProgress(50);
        return true;
    }
    @Override
    protected void onProgressUpdate(String... param) {
        // utilisation de on progress pour afficher des message pendant le
        // doInBackground
        if (classActivityAppelante.contains("ActImport")) {
            ((ActImport) activityAppelante.get()).alertmsg (
                    param[0].toString(), param[1].toString());
        }
        ((ProgressBar) mContext.findViewById(R.id.progressBar)).setProgress(50);

    }


    @Override
    protected void onCancelled () {
        if(activityAppelante.get() != null)
            Toast.makeText(activityAppelante.get(), "Annulation", Toast.LENGTH_SHORT).show();
    }
}