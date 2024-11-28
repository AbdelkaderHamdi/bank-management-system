package org.example;

/**
 * Représente un client avec des détails personnels tels que CIN, nom, prénom et numéro de téléphone.
 * Cette classe fournit des méthodes getter et setter pour les attributs,
 * ainsi qu'une méthode `toString` pour une représentation sous forme de chaîne formatée.
 */

public class Client {
    private String cin ;
    private String nom;
    private String prenom ;
    private String telNumber;

    /**
     * Construit un nouveau client avec les détails spécifiés.
     *
     * @param cin       L'identifiant unique (CIN) du client.
     * @param nom       Le nom du client.
     * @param prenom    Le prénom du client.
     * @param telNumber Le numéro de téléphone du client.
     */

    public Client(String cin, String nom, String prenom, String telNumber) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.telNumber = telNumber;
    }


    public String getCin() {
        return cin;
    }


    public String getNom() {
        return nom;
    }


    public String getPrenom() {
        return prenom;
    }


    public String getTelNumber() {
        return telNumber;
    }



    public void setCin(String cin) {
        this.cin = cin;
    }


    public void setNom(String nom) {
        this.nom = nom;
    }


    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }


    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }


    /**
     * Retourne une représentation sous forme de chaîne des détails du client.
     *
     * @return Une chaîne décrivant les détails du client.
     */

    public String toString() {
        return "\tcin= " + cin + "\n\tnom= " + nom + "\n\tprenom= " + prenom + "\n\ttelephone= " + telNumber + "\n";
    }

}
