package org.example;
import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe gérant les fonctionnalités d'authentification des utilisateurs.
 * Elle permet de vérifier les identifiants des utilisateurs et de créer de nouveaux comptes.
 */
public class Authentication {


    private final Connection connection;

    /**
     * Constructeur par défaut qui initialise une connexion à la base de données.
     */
    public Authentication() {
        this.connection = DatabaseConfig.getConnection();
    }

    /**
     * Authentifie un utilisateur en comparant son mot de passe saisi (après hachage)
     * avec celui stocké dans la base de données.
     *
     * @param username Le nom d'utilisateur.
     * @param password Le mot de passe saisi.
     * @return true si l'authentification réussit, sinon false.
     */
    public boolean authentifier(String username, String password) {
        String sql = "SELECT password FROM utilisateurs WHERE username = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);

            //Exécute la requête SQL et stocke les résultats dans un objet ResultSet.
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) { //Vérifie si un enregistrement a été trouvé dans la base de données.
                String hashedPassword = hashPassword(password); //Calcule le "hash" du mot de passe saisi pour le comparer avec celui stocké.
                return hashedPassword.equals(rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'authentification: " + e.getMessage());
        }
        return false;
    }

    /**
     * Crée un nouvel utilisateur en ajoutant son nom d'utilisateur et le mot de passe haché dans la base de données.
     *
     * @param username Le nom d'utilisateur.
     * @param password Le mot de passe à enregistrer.
     * @return true si l'utilisateur est créé avec succès, sinon false.
     */
    public boolean creerUtilisateur(String username,String cin, String password) {
        String sql = "INSERT INTO utilisateurs (username, cinClient, password) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2,cin);
            pstmt.setString(3, hashPassword(password));

            //Exécute la requête et retourne true si une ligne a été ajoutée.
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur lors de la création de l'utilisateur: " + e.getMessage());
            return false;
        }
    }




    /**
     * Hache un mot de passe en utilisant l'algorithme SHA-256 pour sécuriser son stockage.
     *
     * @param password Le mot de passe brut à hacher.
     * @return Le mot de passe haché sous forme de chaîne hexadécimale.
     */
    private String hashPassword(String password) {
        try {
            //Utiliser l'algorithme "SHA-256"
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            //Convertit chaque octet en une représentation hexadécimale.
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            //Retourne le mot de passe haché sous forme de chaîne.
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

