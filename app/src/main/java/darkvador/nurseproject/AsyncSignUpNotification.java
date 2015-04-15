package darkvador.nurseproject;

import android.app.Fragment;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import static darkvador.nurseproject.CommonUtilities.TAG;

/**
 * Created by zumatec on 15/04/15.
 */
public class AsyncSignUpNotification extends AsyncTask<String, String, Boolean> {
    // Référence à l'activité qui appelle
    private WeakReference<Fragment> fragmentAppelante = null;
    private String classFragmentAppelante;
    private WeakReference<ActionBarActivity> activityAppelante = null;
    private String classActivityAppelante;
    private StringBuilder stringBuilder = new StringBuilder();
    private Fragment mContext;
    private ActionBarActivity mActivityContext;
    public AsyncSignUpNotification () {

    }
    @Override
    protected void onPreExecute () {// Au lancement, on envoie un message à l'appelant
        super.onPreExecute();

    }
    @Override
    protected void onPostExecute (Boolean result) {

    }

    @Override
    protected Boolean doInBackground (String... params) {// Exécution en arrière plan
        URL url;
        StringBuilder bodyBuilder = new StringBuilder();
        try {
            url = new URL(params[0]);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + params[0]);
        }

        // constructs the POST body using the parameters
        bodyBuilder.append("regId").append('=').append(params[1]).append('&').append("name").append('=').append(params[2]).append('&').append("email").append('=').append(params[3]);

        String body = bodyBuilder.toString();
        Log.v(TAG, "Posting '" + body + "' to " + url);
        byte[] bytes = body.getBytes();
        HttpURLConnection conn = null;
        try {
            Log.e("URL", "> " + url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            // post the request
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();
            // handle the response
            int HttpResult = conn.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        conn.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    stringBuilder.append(line);
                }
                br.close();
                // String[] vstring0 = { "Reçu du serveur",stringBuilder.toString() };

                //publishProgress(vstring0);
            }
            int status = conn.getResponseCode();
            if (status != 200) {
                throw new IOException("Post failed with error code " + status);
            }
        }
        catch (MalformedURLException e) {

            String[] vstring0 = { "Erreur", "Pbs url" };
        } catch (java.net.SocketTimeoutException e) {
            String[] vstring0 = { "Erreur", "temps trop long" };
        } catch (IOException e) {

            String[] vstring0 = { "Erreur", "Pbs IO" };

        } finally {
            if (conn != null) {
                conn.disconnect();
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