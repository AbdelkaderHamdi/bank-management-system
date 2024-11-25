package org.example;
import java.sql.Date;

/**
 * Classe représentant un compte épargne avec un taux d'intérêt appliqué sur le solde.
 */
public class CompteEpargne extends Comptes {
    private double tauxInteret;

    /**
     * Constructeur pour initialiser un compte épargne.
     *
     * @param numeroCompte  numéro unique du compte
     * @param cinClient     CIN du client associé au compte
     * @param solde         solde initial du compte
     * @param tauxInteret   taux d'intérêt du compte (en pourcentage)
     */
    public CompteEpargne(String numeroCompte, String cinClient, double solde, double tauxInteret) {
        super(numeroCompte, cinClient, solde);
        this.tauxInteret = tauxInteret;
    }


    /**
     * Effectue un retrait depuis le compte si le solde est suffisant.
     *
     * @param montant montant à retirer
     * @return {@code true} si le retrait a été effectué avec succès, {@code false} sinon
     */
    public boolean retrait(double montant){
        if (solde - montant >= 0){
            solde -= montant ;
            return true;
        }
        return false;
    }

    /**
     * Effectue un versement sur le compte si le montant est positif.
     *
     * @param montant montant à verser
     * @return {@code true} si le versement a été effectué avec succès, {@code false} sinon
     */
    public boolean versement(double montant){
        if(montant >0){
            solde += montant ;
            return true;
        }
        return false;
    }

    /**
     * Calcule et applique les intérêts sur le solde du compte en fonction du taux d'intérêt.
     */
    public void calculerInteret() {
        solde = solde*( 1+ tauxInteret / 100);
    }



    public double getTauxInteret() {
        return tauxInteret;
    }

    public void setTauxInteret(double tauxInteret) {
        this.tauxInteret = tauxInteret;
    }


}
