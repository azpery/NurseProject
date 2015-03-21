package darkvador.nurseproject;

import java.util.Date;

/**
 * Created by darkvador on 11/03/15.
 */
public class Patient {
    private String identifiant;
    private String nom;
    private String prenom;
    private String adresse;
    private String codePostal;
    private String ville;
    private String telephone;
    private Date dateNaiss;
    private String commentaireVisite;
    public Patient(){}
    public Patient(String videntifiant, String vnom, String vprenom,String vadresse, String vcp, String vville, String vtl,Date vdateNaiss, String vcommentaireVisite) {
        setIdentifiant(videntifiant);
        setNom(vnom);
        setPrenom(vprenom);
        setAdresse(vadresse);
        setCodePostal(vcp);
        setVille(vville);
        setTelephone(vtl);
        setDateNaiss(vdateNaiss);
        setCommentaireVisite(vcommentaireVisite);
    }
    public void recopiePatient(Patient patient)
    {
        setIdentifiant(patient.getIdentifiant());
        setNom(patient.getNom());
        setPrenom(patient.getPrenom());
        setAdresse(patient.getAdresse());
        setCodePostal(patient.getCodePostal());
        setVille(patient.getVille());
        setTelephone(patient.getTelephone());
        setDateNaiss(patient.getDateNaiss());
        setCommentaireVisite(patient.getCommentaireVisite());
    }
    public String getIdentifiant() {
        return identifiant;
    }
    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public String getCodePostal() {
        return codePostal;
    }
    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }
    public String getVille() {
        return ville;
    }
    public void setVille(String ville) {
        this.ville = ville;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public Date getDateNaiss() {
        return dateNaiss;
    }
    public void setDateNaiss(Date dateNaiss) {
        this.dateNaiss = dateNaiss;
    }
    public String getCommentaireVisite() {
        return commentaireVisite;
    }
    public void setCommentaireVisite(String commentaireVisite) {
        this.commentaireVisite = commentaireVisite;
    }
}
