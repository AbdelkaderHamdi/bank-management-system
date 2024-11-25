package org.example;
import java.sql.Date;

/**
 * Classe représentant un compte courant avec une autorisation de découvert.
 */
public class CompteCourant extends Comptes {
    private double decouvert;

    /**
     * Constructeur pour initialiser un compte courant.
     *
     * @param numeroCompte  numéro unique du compte
     * @param cinClient     CIN du client associé au compte
     * @param solde         solde initial du compte
     * @param decouvert     montant autorisé pour le découvert
     */
    public CompteCourant(String numeroCompte, String cinClient, double solde, double decouvert) {
        super(numeroCompte, cinClient, solde);
        this.decouvert = decouvert;
    }

    /**
     * Effectue un retrait depuis le compte si le montant reste dans la limite du découvert autorisé.
     *
     * @param montant montant à retirer
     * @return {@code true} si le retrait a été effectué avec succès, {@code false} sinon
     */
    public boolean retrait(double montant){
        if (solde - montant >= decouvert){
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
        if(montant > 0){
            solde += montant;
            return true;
        }
        return false;
    }


    public double getDecouvert() {
        return decouvert;
    }

    public void setDecouvert(double decouvert) {
        this.decouvert = decouvert;
    }
}
