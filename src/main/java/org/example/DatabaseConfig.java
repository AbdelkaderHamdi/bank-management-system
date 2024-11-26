package org.example;

import java.sql.Connection; // Représente une connexion à une base de données pour interagir.
import java.sql.DriverManager; // Utilisé pour établir-creer une connexion à la base de données.
import java.sql.SQLException; // Gère les erreurs liées à la base de données.
import java.sql.Statement; // Permet d'exécuter des requêtes SQL.


/**
 * Classe responsable de la configuration de la base de données.
 * Elle gère la connexion et la création des tables nécessaires au fonctionnement de l'application.
 */
public class DatabaseConfig {
    private static final String DB_URL = "jdbc:sqlite:DataBase.db";
    private static Connection connection = null;

    /**
     * Méthode pour obtenir une instance de connexion à la base de données.
     * Si la connexion n'existe pas, elle sera créée.
     *
     * @return L'objet Connection pour interagir avec la base de données.
     */
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL);
                createTables();
            } catch (SQLException e) {
                System.out.println("Erreur de connexion à la base de données: " + e.getMessage());
            }
        }
        return connection;
    }

    /**
     * Méthode privée pour créer les tables nécessaires à l'application.
     * Elle est exécutée une fois lors de la première connexion à la base de données.
     */
    private static void createTables() {
        //Crée un objet Statement pour exécuter des requêtes SQL.
        try (Statement stmt = connection.createStatement()) {
            // Table Utilisateurs pour l'authentification
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS utilisateurs (
                    username TEXT PRIMARY KEY,
                    password TEXT NOT NULL
                )
            """);

            // Table Clients
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS clients (
                    cin TEXT PRIMARY KEY,
                    nom TEXT NOT NULL,
                    prenom TEXT NOT NULL,
                    telephone TEXT NOT NULL
                )
            """);

            // Table Comptes
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS comptes (
                    numero TEXT PRIMARY KEY,
                    cin_client TEXT NOT NULL,
                    solde REAL NOT NULL,
                    date_ouverture DATE NOT NULL,
                    type TEXT NOT NULL,
                    decouvert REAL,
                    taux_interet REAL,
                    FOREIGN KEY (cin_client) REFERENCES clients(cin)
                )
            """);

            // Table Opérations
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS operations (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    date DATE NOT NULL,
                    type TEXT NOT NULL,
                    montant REAL NOT NULL,
                    compte_source TEXT NOT NULL,
                    compte_destination TEXT,
                    FOREIGN KEY (compte_source) REFERENCES comptes(numero),
                    FOREIGN KEY (compte_destination) REFERENCES comptes(numero)
                )
            """);

        } catch (SQLException e) {
            System.out.println("Erreur lors de la création des tables: " + e.getMessage());
        }
    }
}