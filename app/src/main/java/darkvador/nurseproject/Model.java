package darkvador.nurseproject;

import android.os.Environment;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by darkvador on 11/03/15.
 */
public class Model {
    private String db4oFileName;
    private ObjectContainer dataBase;
    private File appDir;
    public Model() {
        createDirectory();
        open();
        // si partie import non développée
        //chargeDataBase();
        dataBase.close();
    }
    public void deletePatient() {
        open();
        ObjectSet<Patient> result = dataBase.queryByExample(Patient.class);
        while (result.hasNext()) {
            dataBase.delete(result.next());
        }
        dataBase.close();
    }

    public void addPatient(ArrayList<Patient> vPatient) {
        open();
        for (Patient v : vPatient) {
            dataBase.store(v);
        }
        dataBase.close();
    }
    public void deleteInfirmiere() {
        open();
        ObjectSet<Patient> result = dataBase.queryByExample(Infirmiere.class);
        while (result.hasNext()) {
            dataBase.delete(result.next());
        }
        dataBase.close();
    }

    public void addInfirmiere(Infirmiere i) {
        open();
        dataBase.store(i);
        dataBase.close();
    }
    public void deleteVisite() {
        open();
        ObjectSet<Visite> result = dataBase.queryByExample(Visite.class);
        while (result.hasNext()) {
            dataBase.delete(result.next());
        }
        dataBase.close();
    }
    public void deleteVisiteById(int IdVisite) {
        open();
        ObjectSet<Visite> result = dataBase.queryByExample(Visite.class);
        ArrayList<Visite> VisiteToDelete = new ArrayList<Visite>();
       for (Visite V : result) {
           if (V.getIdVisite() == IdVisite)
            dataBase.delete(V);
        }
        dataBase.close();
    }

    public void addVisite(ArrayList<Visite> vVisite) {
        open();
        for (Visite v : vVisite) {
            dataBase.store(v);
        }
        dataBase.close();
    }
    public void deleteActe(){
        open();
        ObjectSet<Acte> result = dataBase.queryByExample(Acte.class);
        while (result.hasNext()){
            dataBase.delete(result.next());
        }
        dataBase.close();
    }
    public void addActe(ArrayList<Acte> aActe)
    {
        open();
        for (Acte a : aActe){
            dataBase.store(a);
        }
        dataBase.close();
    }
//Ouvrir le flux fichier
//   Se servir des ligne misent en commentaire s'il y a un problème de droit avec le fichier db4o
    public boolean open() {
        DataOutputStream dataOutputStream=null;
        Process process = null;
//        try{
        db4oFileName = Environment.getExternalStorageDirectory() + "/basedb4o"+ "/BasePatient.db4o";
//        dataBase = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),db4oFileName);
        //db4oFileName = "/data/basedb4o" + "/BasePatient.db4o";
//        process = Runtime.getRuntime().exec("su");
//        dataOutputStream = new DataOutputStream(process.getOutputStream());
//        dataOutputStream.writeBytes("chmod 777 /data/basedb4o/BasePatient.db4o\n");
//        dataOutputStream.writeBytes("exit\n");
//        dataOutputStream.flush();
//        process.waitFor();
        dataBase = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),db4oFileName);
//    } catch (Exception e) {
//
//    } finally {
//        try {
//            if (dataOutputStream != null) {
//                dataOutputStream.close();
//            }
//            process.destroy();
//        } catch (Exception e) {
//        }
//    }
//        return false;
        return false;
    }
    public void createDirectory() {
        appDir = new File(Environment.getExternalStorageDirectory()+ "/basedb4o");

       // appDir = new File("/data" + "/basedb4o");
        if (!appDir.exists() && !appDir.isDirectory()) {
            appDir.mkdirs();
        }
    }
    public ArrayList<Visite> listeVisite() {
        open();
        ArrayList<Visite> listeVisite = new ArrayList<Visite>();
        ObjectSet<Visite> result = dataBase.queryByExample(Visite.class);
        while (result.hasNext()) {
            listeVisite.add(result.next());
        }
        dataBase.close();
        return listeVisite;
    }
    public ArrayList<Patient> listePatient() {
        open();
        ArrayList<Patient> listePatient = new ArrayList<Patient>();
        ObjectSet<Patient> result = dataBase.queryByExample(Patient.class);
        while (result.hasNext()) {
            listePatient.add(result.next());
        }
        dataBase.close();
        return listePatient;
    }
    public ArrayList<Acte> listeActes() {
        open();
        ArrayList<Acte> listeActe = new ArrayList<Acte>();
        ObjectSet<Acte> result = dataBase.queryByExample(Acte.class);
        while (result.hasNext()){
            listeActe.add(result.next());
        }
        dataBase.close();
        return listeActe;
    }
    public Patient trouvePatient (String id) {
        open();
        Patient vretour = new Patient();
        vretour.setIdentifiant(id);
        ObjectSet<Patient> result = dataBase.queryByExample(vretour);
        vretour = (Patient) result.next();
        dataBase.close();
        return vretour;
    }
    public Infirmiere trouveInfirmiere () {
        open();
        Infirmiere vretour=new Infirmiere();
        ObjectSet<Infirmiere> i = dataBase.queryByExample(Infirmiere.class);
        vretour = (Infirmiere)i.next();
        dataBase.close();
        return vretour;
    }
    public boolean isSetInfirmiere(){
        boolean vretour=true;
        open();
        ObjectSet<Infirmiere> i = dataBase.queryByExample(Infirmiere.class);
        if(i.size()==0){
            vretour=false;
        }
        dataBase.close();
        return vretour;
    }
    public Visite trouveVisite (int id) {
        open();
        Visite vretour = new Visite();
        vretour.setIdVisite(id);
        ObjectSet<Visite> result = dataBase.queryByExample(vretour);
        vretour = (Visite) result.next();
        dataBase.close();
        return vretour;
    }
    public void savePatient(Patient patient) {
        open();
        Patient vretour = new Patient();
        vretour.setIdentifiant(patient.getIdentifiant());
        ObjectSet<Patient> result = dataBase.queryByExample(vretour);
        if (result.size() == 0) {
            dataBase.store(patient);
        } else {
            vretour = (Patient) result.next();
            vretour.recopiePatient(patient);
            dataBase.store(vretour);
        }
        dataBase.close();
    }
    public void saveVisite(Visite visite) {
        open();
        Visite vretour = new Visite();
        vretour.setIdVisite(visite.getIdVisite());
        ObjectSet<Visite> result = dataBase.queryByExample(vretour);
        if (result.size() == 0) {
            dataBase.store(visite);
        } else {
            vretour = (Visite) result.next();
            vretour.recopieVisite(visite);
            dataBase.store(vretour);
        }
        dataBase.close();
        ArrayList<Visite> v =listeVisite();
        Patient p=new Patient();

    }
    /*public void chargeDataBase() {
        try {
            ObjectSet<Patient> result = dataBase.queryByExample(Patient.class);
            if (result.size() == 0) {
                try {
                    dataBase.store(new Patient("1001", "Dupont", "paul",
                            "10 rue Anne Frank", "49000", "angers",
                            "0624553212",
                            new SimpleDateFormat("dd/MM/yyyy")
                                    .parse("15/03/1945")));
                    dataBase.commit();
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    dataBase.store(new Patient("1002", "Lulu", "Isabelle",
                            "10 Avenue des arts et métiers", "49000", "angers",
                            "0624553212",
                            new SimpleDateFormat("dd/MM/yyyy")
                                    .parse("15/03/1954")));
                    dataBase.commit();
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    dataBase.store(new Patient("1003", "Caolin", "Etienne",
                            "10 rue Boisnet", "49000", "angers", "0624553212",
                            new SimpleDateFormat(
                                    "dd/MM/yyyy").parse("15/03/2012")));
                    dataBase.commit();
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    dataBase.store(new Patient("1004", "Breche", "Alfred",
                            "25 rue du quinconce", "49000", "angers",
                            "0623553211",
                            new SimpleDateFormat("dd/MM/yyyy")
                                    .parse("15/08/1964")));
                    dataBase.commit();
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    dataBase.store(new Patient("1005", "Centaure", "Marie",
                            "20 rue des lutins", "49000", "angers",
                            "0744553212",
                            new SimpleDateFormat("dd/MM/yyyy")
                                    .parse("15/05/1951")));
                    dataBase.commit();
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } finally {
            dataBase.close();
        }
    }*/
}
