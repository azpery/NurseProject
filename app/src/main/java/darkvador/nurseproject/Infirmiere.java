package darkvador.nurseproject;

/**
 * Created by darkvador on 08/04/15.
 */
public class Infirmiere {
    private int id =0;
    private String nom;
    private String prenom;
    private String pseudo;
    public Infirmiere(int id, String nom, String prenom,String pseudo) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.pseudo = pseudo;
    }
public Infirmiere(){

}
    public String getPrenomIfirmiere() {
        return prenom;
    }

    public void setPrenomIfirmiere(String prenomIfirmiere) {
        this.prenom = prenomIfirmiere;
    }

    public String getNomInfirmiere() {
        return nom;
    }

    public void setNomInfirmiere(String nomInfirmiere) {
        this.nom = nomInfirmiere;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
