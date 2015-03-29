package darkvador.nurseproject;

/**
 * Created by darkvador on 28/03/15.
 */
public class Visite {
    private int idVisite;

    public Visite(int idVisite, String nomInfirmiere, String prenomInfirmiere, String nomPatient, String prenomPatient, String dateDebut, String dateFin, int annee, int mois, int jour, int heureDebut, int duree) {
        this.idVisite = idVisite;
        this.nomInfirmiere = nomInfirmiere;
        this.prenomInfirmiere = prenomInfirmiere;
        this.nomPatient = nomPatient;
        this.prenomPatient = prenomPatient;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.annee = annee;
        this.mois = mois;
        this.jour = jour;
        this.heureDebut = heureDebut;
        this.duree = duree;
    }

    private String nomInfirmiere;
    private String prenomInfirmiere;
    private String nomPatient;
    private String prenomPatient;
    private String dateDebut;
    private String dateFin;
    private int annee;


    private int mois;
    private int jour;
    private int heureDebut;
    private int duree;
    public Visite(int idVisite, String nomInfirmiere, String prenomInfirmiere, String nomPatient, String prenomPatient, String dateDebut, String dateFin) {
        this.idVisite = idVisite;
        this.nomInfirmiere = nomInfirmiere;
        this.prenomInfirmiere = prenomInfirmiere;
        this.nomPatient = nomPatient;
        this.prenomPatient = prenomPatient;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        setdate();
    }
    public void recopieVisite( Visite uneVisite){
        setIdVisite(uneVisite.getIdVisite());
        setNomInfirmiere(uneVisite.getNomInfirmiere());
        setPrenomInfirmiere(uneVisite.getPrenomInfirmiere());
        setNomPatient(uneVisite.getNomPatient());
        setPrenomPatient(uneVisite.getPrenomPatient());
        setDateDebut(uneVisite.getDateDebut());
        setDateFin(uneVisite.getDateFin());
    }
    public int getIdVisite() {
        return idVisite;
    }

    public void setIdVisite(int idVisite) {
        this.idVisite = idVisite;
    }

    public String getNomInfirmiere() {
        return nomInfirmiere;
    }

    public void setNomInfirmiere(String nomInfirmiere) {
        this.nomInfirmiere = nomInfirmiere;
    }

    public String getPrenomInfirmiere() {
        return prenomInfirmiere;
    }

    public void setPrenomInfirmiere(String prenomInfirmiere) {
        this.prenomInfirmiere = prenomInfirmiere;
    }

    public String getNomPatient() {
        return nomPatient;
    }

    public void setNomPatient(String nomPatient) {
        this.nomPatient = nomPatient;
    }

    public String getPrenomPatient() {
        return prenomPatient;
    }

    public void setPrenomPatient(String prenomPatient) {
        this.prenomPatient = prenomPatient;
    }
    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public int getMois() {
        return mois;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }

    public int getJour() {
        return jour;
    }

    public void setJour(int jour) {
        this.jour = jour;
    }

    public int getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(int heureDebut) {
        this.heureDebut = heureDebut;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }
    public void setdate(){
        String date= getDateDebut();
        String heure;
        String[] heureParse;
        String[] dateParse= date.split("-");
        setAnnee(Integer.parseInt(dateParse [0]));
        setMois(Integer.parseInt(dateParse [1]));
        setJour(Integer.parseInt(dateParse [2].split(" ")[0]));
        heureParse=dateParse [2].split(" ")[1].split(":");
        setHeureDebut(Integer.parseInt(heureParse [0]));
        setDuree( Integer.parseInt(getDateFin().split("-")[2].split(" ")[1].split(":")[0]) - Integer.parseInt(heureParse [0]));
    }
}
