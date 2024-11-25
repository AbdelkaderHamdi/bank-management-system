package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * La classe GestionCompte gère les opérations CRUD (Create, Read, Update, Delete) pour les comptes bancaires.
 * Elle utilise une base de données relationnelle pour stocker les informations des comptes.
 */

public class GestionCompte {
    private Connection connection;



        /**
         * Constructeur de la classe GestionCompte.
         * Initialise la connexion à la base de données.
         */
        public GestionCompte() {
            this.connection = DatabaseConfig.getConnection();
        }

        /**
         * Ajoute un compte à la base de données.
         *
         * @param compte L'objet compte à ajouter (peut être un CompteCourant ou un CompteEpargne).
         * @return true si le compte a été ajouté avec succès, sinon false.
         */
        public boolean ajouterCompte(Comptes compte) {
            String sql = "INSERT INTO comptes (numero, cin_client, solde, date_ouverture, type, decouvert, taux_interet) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, compte.getNumeroCompte());
                pstmt.setString(2, compte.getCinClient());
                pstmt.setDouble(3, compte.getSolde());
                pstmt.setDate(4, new java.sql.Date(compte.getDateOuverture().getTime()));

                if (compte instanceof CompteCourant) {
                    pstmt.setString(5, "COURANT");
                    pstmt.setDouble(6, ((CompteCourant) compte).getDecouvert());
                    pstmt.setNull(7, Types.DOUBLE);
                } else {
                    pstmt.setString(5, "EPARGNE");
                    pstmt.setNull(6, Types.DOUBLE);
                    pstmt.setDouble(7, ((CompteEpargne) compte).getTauxInteret());
                }

                return pstmt.executeUpdate() > 0;
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout du compte: " + e.getMessage());
                return false;
            }
        }

        /**
         * Récupère un compte en fonction de son numéro.
         *
         * @param cin Le numéro unique du client.
         * @return Un objet Comptes correspondant au numéro, ou null si aucun compte n'est trouvé.
         */
        public Comptes getCompte(String cin) {
            String sql = "SELECT * FROM comptes WHERE cin_client = ?";

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, cin);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return createCompteFromResultSet(rs);
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la récupération du compte: " + e.getMessage());
            }
            return null;
        }



    public List<Comptes> getComptesClient(String cinClient) {
        List<Comptes> comptes = new ArrayList<>();
        String sql = "SELECT * FROM comptes WHERE cin_client = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, cinClient);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                comptes.add(createCompteFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des comptes: " + e.getMessage());
        }
        return comptes;
    }



        /**
         * Modifie un compte existant dans la base de données.
         *
         * @param compte L'objet compte contenant les nouvelles informations.
         * @return true si la modification a été effectuée avec succès, sinon false.
         */
        public boolean modifierCompte(Comptes compte) {
            String sql = "UPDATE comptes SET solde = ?, decouvert = ?, taux_interet = ? WHERE numero = ?";

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setDouble(1, compte.getSolde());

                if (compte instanceof CompteCourant) {
                    pstmt.setDouble(2, ((CompteCourant) compte).getDecouvert());
                    pstmt.setNull(3, Types.DOUBLE);
                } else {
                    pstmt.setNull(2, Types.DOUBLE);
                    pstmt.setDouble(3, ((CompteEpargne) compte).getTauxInteret());
                }

                pstmt.setString(4, compte.getNumeroCompte());

                return pstmt.executeUpdate() > 0;
            } catch (SQLException e) {
                System.out.println("Erreur lors de la modification du compte: " + e.getMessage());
                return false;
            }
        }

        /**
         * Supprime un compte de la base de données en fonction de son numéro.
         *
         * @param cin Le numéro unique du compte à supprimer.
         * @return true si le compte a été supprimé avec succès, sinon false.
         */
        public boolean supprimerCompte(String cin) {
            String sql = "DELETE FROM comptes WHERE cin_client = ?";

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, cin);
                return pstmt.executeUpdate() > 0;
            } catch (SQLException e) {
                System.out.println("Erreur lors de la suppression du compte: " + e.getMessage());
                return false;
            }
        }

        /**
         * Crée un objet Comptes (CompteCourant ou CompteEpargne) à partir des données d'un ResultSet.
         *
         * @param rs Le ResultSet contenant les données d'un compte.
         * @return Un objet Comptes correspondant aux données.
         * @throws SQLException Si une erreur survient lors de l'accès aux données du ResultSet.
         */
        private Comptes createCompteFromResultSet(ResultSet rs) throws SQLException {
            String type = rs.getString("type");
            String numero = rs.getString("numero");
            String cinClient = rs.getString("cin_client");
            double solde = rs.getDouble("solde");

            if (type.equals("COURANT")) {
                double decouvert = rs.getDouble("decouvert");
                return new CompteCourant(numero, cinClient, solde, decouvert);
            } else {
                double tauxInteret = rs.getDouble("taux_interet");
                return new CompteEpargne(numero, cinClient, solde, tauxInteret);
            }
        }



    }
