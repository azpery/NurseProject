package darkvador.nurseproject;

/**
 * Created by darkvador on 08/04/15.
 */
public final class Identity {
    public static String nom="";
    public static int id=0;
    public static String prenom="";
    public static void deconnexion(){
        nom="";
        prenom="";
        id=0;
        new Model().deleteInfirmiere();
    }
}
