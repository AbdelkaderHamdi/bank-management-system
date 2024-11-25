package org.example;

import java.util.Date;


/**
 * Classe abstraite représentant un compte bancaire.
 */
public abstract class Comptes {

    protected String numeroCompte;
    protected String cinClient;
    protected Date dateOuverture;
    protected double solde;

    /**
     * Constructeur pour initialiser un compte bancaire.
     *
     * @param numeroCompte numéro unique du compte
     * @param cinClient    CIN du client associé au compte
     * @param solde        solde initial du compte
     */
    public Comptes(String numeroCompte, String cinClient, double solde) {
        this.numeroCompte = numeroCompte;
        this.cinClient = cinClient;
        this.dateOuverture = new Date();
        this.solde = solde;
    }


    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public void setCinClient(String cinClient) {
        this.cinClient = cinClient;
    }

    public void setDateOuverture(Date dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }


    public String getNumeroCompte() {
        return numeroCompte;
    }

    public String getCinClient() {
        return cinClient;
    }

    public Date getDateOuverture() {
        return dateOuverture;
    }

    public double getSolde() {
        return solde;
    }


    /**
     * Effectue un retrait depuis le compte.
     *
     * @param montant montant à retirer
     * @return {@code true} si le retrait a été effectué avec succès, {@code false} sinon
     */
    public abstract boolean retrait(double montant);

    /**
     * Effectue un versement sur le compte.
     *
     * @param montant montant à verser
     * @return {@code true} si le versement a été effectué avec succès, {@code false} sinon
     */
    public abstract boolean versement(double montant);


}