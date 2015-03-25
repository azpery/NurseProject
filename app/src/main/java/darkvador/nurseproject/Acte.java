package darkvador.nurseproject;

/**
 * Created by Mathieu on 25/03/2015.
 */
public class Acte {
    public int getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }

    public int getDuree() {
        return duree;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private int id;
    private String libelle;
    private int duree;
    private String description;
    public Acte(int unId, String unLibelle, int uneDuree, String uneDescription)
    {
        id = unId; libelle = unLibelle; duree = uneDuree; description = uneDescription;
    }
}
