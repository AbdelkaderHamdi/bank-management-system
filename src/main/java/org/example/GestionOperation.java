package org.example;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsable de la gestion des opérations bancaires telles que retraits, versements et virements.
 */
public class GestionOperation {
    private Connection connection;
    private GestionCompte compteGes;

    /**
     * Constructeur de la classe GestionOperation.
     * Initialise la connexion à la base de données et le gestionnaire de comptes.
     */
    public GestionOperation() {
        this.connection = DatabaseConfig.getConnection();
        this.compteGes = new GestionCompte();
    }

    /**
     * Effectue un retrait depuis le compte spécifié.
     *
     * @param numeroCompte numéro du compte source
     * @param montant      montant à retirer
     */
    public void effectuerRetrait(String numeroCompte, double montant) throws SQLException {
        effectuerOperation("RETRAIT", montant, numeroCompte, null);
    }

    /**
     * Effectue un versement sur le compte spécifié.
     *
     * @param numeroCompte numéro du compte destinataire
     * @param montant      montant à verser
     * @return {@code true} si le versement a réussi, {@code false} sinon
     */
    public boolean effectuerVersement(String numeroCompte, double montant) throws SQLException {
        return effectuerOperation("VERSEMENT", montant, numeroCompte, null);
    }

    /**
     * Effectue un virement entre deux comptes.
     *
     * @param compteSource numéro du compte source
     * @param montant      montant à transférer
     * @return {@code true} si le virement a réussi, {@code false} sinon
     */
    public boolean effectuerVirement(String compteSource, String compteDestination, double montant) throws SQLException {
        return effectuerOperation("VIREMENT", montant, compteSource, compteDestination);
    }

    /**
     * Effectue une opération bancaire (retrait, versement ou virement) et met à jour les comptes en conséquence.
     *
     * @param type              type de l'opération ("RETRAIT", "VERSEMENT", "VIREMENT")
     * @param montant           montant de l'opération
     * @param compteSource      numéro du compte source
     * @param compteDestination numéro du compte destinataire (null pour les retraits et versements)
     * @return {@code true} si l'opération a réussi, {@code false} sinon
     */
    private boolean effectuerOperation(String type, double montant, String compteSource, String compteDestination) throws SQLException {
        String sql = "INSERT INTO operations (date, type, montant, compte_source, compte_destination) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Enregistrer l'opération
            pstmt.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            pstmt.setString(2, type);
            pstmt.setDouble(3, montant);
            pstmt.setString(4, compteSource);
            pstmt.setString(5, compteDestination);

            // Mettre à jour les soldes
            var sourceCompte = compteGes.getCompte(compteSource);
            if (sourceCompte == null) {
                throw new SQLException("Compte source non trouvé");
            }

            boolean success = false;
            switch (type) {
                case "RETRAIT":
                    success = sourceCompte.retrait(montant);
                    break;
                case "VERSEMENT":
                    success = sourceCompte.versement(montant);
                    break;
                case "VIREMENT":
                    var destCompte = compteGes.getCompte(compteDestination);
                    if (destCompte == null) {
                        throw new SQLException("Compte destination non trouvé");
                    }
                    if (sourceCompte.retrait(montant)) {
                        success = destCompte.versement(montant);
                        if (success) {
                            compteGes.modifierCompte(destCompte);
                        }
                    }
                    break;
            }

            if (success) {
                compteGes.modifierCompte(sourceCompte);
                pstmt.executeUpdate();
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Erreur lors du rollback: " + ex.getMessage());
            }
            System.out.println("Erreur lors de l'opération: " + e.getMessage());
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Erreur lors de la restauration de l'autocommit: " + e.getMessage());
            }
        }
    }

    /**
     * Récupère l'historique des opérations associées à un compte donné.
     *
     * @param numeroCompte numéro du compte dont l'historique est à récupérer
     * @return une liste des opérations sous forme de tableau de chaînes de caractères
     *         où chaque tableau contient : [date, type, montant, compte_source, compte_destination]
     */
    public List<String[]> getHistoriqueOperations(String numeroCompte) {
        List<String[]> operations = new ArrayList<>();
        String sql = "SELECT * FROM operations WHERE compte_source = ? OR compte_destination = ? " +
                "ORDER BY date DESC";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, numeroCompte);
            pstmt.setString(2, numeroCompte);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String[] operation = new String[5];
                operation[0] = rs.getDate("date").toString();
                operation[1] = rs.getString("type");
                operation[2] = String.valueOf(rs.getDouble("montant"));
                operation[3] = rs.getString("compte_source");
                operation[4] = rs.getString("compte_destination");
                operations.add(operation);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'historique: " + e.getMessage());
        }
        return operations;
    }
}
