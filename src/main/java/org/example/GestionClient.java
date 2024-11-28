package org.example;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Gère les opérations CRUD (Créer, Lire, Modifier, Supprimer) sur les objets Client.
 * Les données des clients sont stockées dans une base de données.
 */
public class GestionClient {

    private Connection connection;

    /**
     * Initialise une nouvelle instance de GestionClient avec une connexion à la base de données.
     */
    public GestionClient() {
        this.connection = DatabaseConfig.getConnection();
    }

    /**
     * Ajoute un nouveau client dans la base de données.
     *
     * @param client Le client à ajouter.
     * @return true si l'ajout est réussi, sinon false.
     */
    public boolean ajouterClient(Client client) {
        String sql = "INSERT INTO clients (cin, nom, prenom, telephone) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, client.getCin());
            pstmt.setString(2, client.getNom());
            pstmt.setString(3, client.getPrenom());
            pstmt.setString(4, client.getTelNumber());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du client: " + e.getMessage());
            return false;
        }
    }

    /**
     * Récupère un client en fonction de son CIN.
     *
     * @param cin Le CIN du client.
     * @return Le client correspondant ou null si non trouvé.
     */
    public Client getClient(String cin) {
        String sql = "SELECT * FROM clients WHERE cin = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, cin);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Client(
                        rs.getString("cin"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("telephone")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du client: " + e.getMessage());
        }

        return null;
    }

     public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                clients.add(new Client(
                        rs.getString("cin"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("telephone")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des clients: " + e.getMessage());
        }
        return clients;
    }



    /**
     * Modifie les informations d'un client existant dans la base de données.
     *
     * @param client Le client avec les nouvelles informations.
     * @return true si la modification est réussie, sinon false.
     */
    public boolean modifierClient(Client client) {
        String sql = "UPDATE clients SET nom = ?, prenom = ?, telephone = ? WHERE cin = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, client.getNom());
            pstmt.setString(2, client.getPrenom());
            pstmt.setString(3, client.getTelNumber());
            pstmt.setString(4, client.getCin());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification du client: " + e.getMessage());
            return false;
        }
    }

    /**
     * Supprime un client de la base de données.
     *
     * @param cin Le CIN du client à supprimer.
     * @return true si la suppression est réussie, sinon false.
     */
    public boolean supprimerClient(String cin) {
        String sql = "DELETE FROM clients WHERE cin = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, cin);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du client: " + e.getMessage());
            return false;
        }
    }
}

