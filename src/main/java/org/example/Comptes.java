package org.example;

import java.util.Date;


/**
 * Classe abstraite représentant un compte bancaire.
 */
public abstract class Comptes {

    protected String cinClient;
    protected Date dateOuverture;
    protected double solde;

    /**
     * Constructeur pour initialiser un compte bancaire.
     *
     * @param cinClient    CIN du client associé au compte
     * @param solde        solde initial du compte
     */
    public Comptes(String cinClient, double solde) {
        this.cinClient = cinClient;
        this.dateOuverture = new Date();
        this.solde = solde;
    }


    public void setCinClient(String cinClient) {
        this.cinClient = cinClient;
    }

    public void setSolde(double solde) {
        this.solde = solde;
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

    @Override
    public String toString() {
        return "\tcinClient= " + cinClient + "\n\tdateOuverture= " + dateOuverture +"\n\tsolde= " + solde + "\n";
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