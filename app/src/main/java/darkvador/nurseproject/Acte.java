package darkvador.nurseproject;

/**
 * Created by Mathieu on 25/03/2015.
 */
public class Acte {
    private int id;
    private String libelle;

    public boolean isEstFait() {
        return estFait;
    }

    public void setEstFait(boolean estFait) {
        this.estFait = estFait;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getTypeSoin() {
        return typeSoin;
    }

    public void setTypeSoin(String typeSoin) {
        this.typeSoin = typeSoin;
    }

    private String typeSoin;
    private boolean estFait;
    public Acte(int unId, String unLibelle, String unTypeSoin)
    {
        id = unId; libelle = unLibelle; typeSoin = unTypeSoin; estFait = false;
    }
}