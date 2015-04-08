package darkvador.nurseproject;

/**
 * Created by darkvador on 08/04/15.
 */
public class Infirmiere {
    private int id =0;
    private String nomInfirmiere;
    private String prenomIfirmiere;
    private String pseudo;
    public Infirmiere(int id, String nomInfirmiere, String prenomIfirmiere,String pseudo) {
        this.id = id;
        this.nomInfirmiere = nomInfirmiere;
        this.prenomIfirmiere = prenomIfirmiere;
        this.pseudo = pseudo;
    }
public Infirmiere(){

}
    public String getPrenomIfirmiere() {
        return prenomIfirmiere;
    }

    public void setPrenomIfirmiere(String prenomIfirmiere) {
        this.prenomIfirmiere = prenomIfirmiere;
    }

    public String getNomInfirmiere() {
        return nomInfirmiere;
    }

    public void setNomInfirmiere(String nomInfirmiere) {
        this.nomInfirmiere = nomInfirmiere;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
