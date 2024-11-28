/*
package org.example;


import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;

public class Main {
    // Method to clear the console screen
    public static void clearScreen() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        }
        catch (final Exception e) {
            // Handle any exceptions
            System.out.println("Error clearing screen: " + e.getMessage());
        }
    }

    // Méthode statique pour afficher un message coloré
    public static void printColoredMessage(String message, boolean isSuccess) {
        // Codes ANSI pour les couleurs
        final String RESET = "\033[0m";    // Reset
        final String GREEN = "\033[0;32m"; // Vert pour succès
        final String RED = "\033[0;31m";   // Rouge pour échec

        // Sélectionner la couleur en fonction de la réussite ou de l'échec
        String color = isSuccess ? GREEN : RED;

        // Afficher le message coloré
        System.out.println(color + message + RESET);
    }

    // Method to display main menu
    public static void displayMainMenu() {
        clearScreen();
        System.out.println("===== GESTION BANCAIRE =====");
        System.out.println("1. Gestion des Clients");
        System.out.println("2. Gestion des Comptes");
        System.out.println("3. Opérations Bancaires");
        System.out.println("0. Quitter");
        System.out.print("Choisissez une option : ");
    }

    public static void displayClientsMenu() {
        clearScreen();
        System.out.println("===== GESTION DES CLIENTS =====");
        System.out.println("1. Ajouter un client");
        System.out.println("2. Consulter un client");
        System.out.println("3. Modifier un client");
        System.out.println("4. Supprimer un client");
        System.out.println("0. Retour");
        System.out.print("Choisissez une option : ");
    }

    public static void displayComptesMenu() {
        clearScreen();
        System.out.println("===== GESTION DES COMPTES =====");
        System.out.println("1. Créer un compte courant");
        System.out.println("2. Créer un compte épargne");
        System.out.println("3. Consulter un compte");
        System.out.println("0. Retour");
        System.out.print("Choisissez une option : ");
    }

    public static void displayOperationsMenu() {
        clearScreen();
        System.out.println("===== OPÉRATIONS BANCAIRES =====");
        System.out.println("1. Retrait");
        System.out.println("2. Versement");
        System.out.println("3. Virement");
        System.out.println("4. voir historique des operations");
        System.out.println("0. Retour");
        System.out.print("Choisissez une option : ");
    }


    public static void main(String[] args) throws SQLException {
        // Initialize database
        DatabaseConfig.getConnection();

        // Initialize necessary management classes
        GestionClient gestionClient = new GestionClient();
        GestionCompte gestionCompte = new GestionCompte();
        GestionOperation gestionOperation = new GestionOperation();
        Authentication auth = new Authentication();

        Scanner scanner = new Scanner(System.in);
        int choix;

        do {
            displayMainMenu();
            try {
                choix = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch(choix) {
                    case 1:
                        // Client Management Submenu
                        boolean retourMenuPrincipal = false;
                        do {
                            displayClientsMenu();
                            int sousChoixClients = scanner.nextInt();
                            scanner.nextLine(); // Consume newline

                            switch(sousChoixClients) {
                                case 1: // Ajouter client
                                    clearScreen();
                                    System.out.print("CIN : ");
                                    String cin = scanner.nextLine();
                                    System.out.print("Nom : ");
                                    String nom = scanner.nextLine();
                                    System.out.print("Prénom : ");
                                    String prenom = scanner.nextLine();
                                    System.out.print("Téléphone : ");
                                    String telephone = scanner.nextLine();

                                    Client nouveauClient = new Client(cin, nom, prenom, telephone);
                                    if (gestionClient.ajouterClient(nouveauClient)) {
                                        printColoredMessage("Client ajouté avec succès!", true);
                                    } else {
                                        printColoredMessage("Erreur lors de l'ajout du client.", false);
                                    }
                                    break;

                                case 2: // Consulter client
                                    clearScreen();
                                    System.out.print("Entrez le CIN du client : ");
                                    cin = scanner.nextLine();
                                    Client client = gestionClient.getClient(cin);
                                    if (client != null) {
                                        System.out.println("Informations du client :");
                                        System.out.println(client.toString());
                                    } else {
                                        printColoredMessage("Client non trouvé.", false);
                                    }
                                    break;

                                case 3: // Modifier client
                                    clearScreen();
                                    System.out.print("CIN du client à modifier : ");
                                    cin = scanner.nextLine();
                                    if (gestionClient.getClient(cin) != null) {
                                        System.out.print("Nouveau nom : ");
                                        nom = scanner.nextLine();
                                        System.out.print("Nouveau prénom : ");
                                        prenom = scanner.nextLine();
                                        System.out.print("Nouveau téléphone : ");
                                        telephone = scanner.nextLine();

                                        Client clientModifie = new Client(cin, nom, prenom, telephone);
                                        if (gestionClient.modifierClient(clientModifie)) {
                                            printColoredMessage("Client modifié avec succès!", true);
                                        } else {
                                            printColoredMessage("Erreur lors de la modification du client.", false);
                                        }
                                    } else {
                                        printColoredMessage("Client non trouvé.", false);
                                    }
                                    break;

                                case 4: // Supprimer client
                                    clearScreen();
                                    System.out.print("Entrez le CIN du client à supprimer : ");
                                    cin = scanner.nextLine();
                                    if (gestionClient.supprimerClient(cin)) {
                                        printColoredMessage("Client supprimé avec succès!", true);
                                    } else {
                                        printColoredMessage("Erreur lors de la suppression du client.", false);
                                    }
                                    break;

                                case 0: // Retour
                                    retourMenuPrincipal = true;
                                    break;

                                default:
                                    printColoredMessage("Option invalide!", false);
                            }
                        } while (!retourMenuPrincipal);
                        break;

                    case 2:
                        // Account Management Submenu
                        retourMenuPrincipal = false;
                        do {
                            displayComptesMenu();
                            int sousChoixComptes = scanner.nextInt();
                            scanner.nextLine(); // Consume newline

                            switch(sousChoixComptes) {
                                case 1: // Créer compte courant
                                    clearScreen();
                                    System.out.print("Numero compte : ");
                                    String numeroCourant = scanner.nextLine();
                                    System.out.print("CIN du client : ");
                                    String cinCourant = scanner.nextLine();
                                    System.out.print("Solde initial : ");
                                    double soldeCourant = scanner.nextDouble();

                                    Comptes nouveauCompteCourant = new CompteCourant(numeroCourant, cinCourant, soldeCourant, 10);
                                    if (gestionCompte.ajouterCompte(nouveauCompteCourant)) {
                                        printColoredMessage("Compte courant créé avec succès!", true);
                                    } else {
                                        printColoredMessage("Erreur lors de la création du compte courant.", false);
                                    }
                                    break;

                                case 2: // Créer compte épargne
                                    clearScreen();
                                    System.out.print("Numero compte : ");
                                    String numeroEpargne = scanner.nextLine();
                                    System.out.print("CIN du client : ");
                                    String cinEpargne = scanner.nextLine();
                                    System.out.print("Solde initial : ");
                                    double soldeEpargne = scanner.nextDouble();

                                    Comptes nouveauCompteEpargne = new CompteEpargne(numeroEpargne, cinEpargne, soldeEpargne, 10);
                                    if (gestionCompte.ajouterCompte(nouveauCompteEpargne)) {
                                        printColoredMessage("Compte épargne créé avec succès!", true);
                                    } else {
                                        printColoredMessage("Erreur lors de la création du compte épargne.", false);
                                    }
                                    break;

                                case 3: // Consulter compte
                                    clearScreen();
                                    System.out.print("Numéro de compte : ");
                                    String numero = scanner.nextLine();
                                    Comptes compte = gestionCompte.getCompte(numero);
                                    if (compte != null) {
                                        System.out.println("Informations du compte :");
                                        System.out.println(compte.toString());
                                    } else {
                                        printColoredMessage("Compte non trouvé.", false);
                                    }
                                    break;

                                case 0: // Retour
                                    retourMenuPrincipal = true;
                                    break;

                                default:
                                    printColoredMessage("Option invalide!", false);
                            }
                        } while (!retourMenuPrincipal);
                        break;

                    case 3:
                        // Banking Operations Submenu
                        retourMenuPrincipal = false;
                        do {
                            displayOperationsMenu();
                            int sousChoixOperations = scanner.nextInt();
                            scanner.nextLine(); // Consume newline

                            switch(sousChoixOperations) {
                                case 1: // Retrait
                                    clearScreen();
                                    System.out.print("Numéro de compte : ");
                                    String numRet = scanner.nextLine();
                                    System.out.print("Montant : ");
                                    double montantRet = scanner.nextDouble();

                                    try {
                                        gestionOperation.effectuerRetrait(numRet, montantRet);
                                        printColoredMessage("Retrait effectué avec succès!", true);
                                    } catch (SQLException e) {
                                        printColoredMessage("Erreur lors du retrait : " + e.getMessage(), false);
                                    }
                                    break;

                                case 2: // Versement
                                    clearScreen();
                                    System.out.print("Numéro de compte : ");
                                    String numVer = scanner.nextLine();
                                    System.out.print("Montant : ");
                                    double montantVer = scanner.nextDouble();
                                    if (gestionOperation.effectuerVersement(numVer, montantVer)) {
                                        printColoredMessage("Versement effectué avec succès!", true);
                                    } else {
                                        printColoredMessage("Erreur lors du versement.", false);
                                    }
                                    break;

                                case 3: // Virement
                                    clearScreen();
                                    System.out.print("Numéro du compte source : ");
                                    String numSource = scanner.nextLine();
                                    System.out.print("Numéro du compte destination : ");
                                    String numDest = scanner.nextLine();
                                    System.out.print("Montant : ");
                                    double montantVir = scanner.nextDouble();
                                    if (gestionOperation.effectuerVirement(numSource,numDest, montantVir)) {
                                        printColoredMessage("Virement effectué avec succès!", true);
                                    } else {
                                        printColoredMessage("Erreur lors du virement.", false);
                                    }
                                    break;

                                case 4: // Historique des opérations
                                    clearScreen();
                                    System.out.print("Numéro de compte : ");
                                    String numHis = scanner.nextLine();
                                    List<String[]> historique = gestionOperation.getHistoriqueOperations(numHis);
                                    if (historique != null && !historique.isEmpty()) {
                                        System.out.println("Historique des opérations :");
                                        for (String[] operation : historique) {
                                            System.out.println(String.join(" - ", operation));
                                        }
                                    } else {
                                        printColoredMessage("Aucune opération trouvée.", false);
                                    }
                                    break;

                                case 0: // Retour
                                    retourMenuPrincipal = true;
                                    break;

                                default:
                                    printColoredMessage("Option invalide!", false);
                            }
                        } while (!retourMenuPrincipal);
                        break;


                    case 0:
                        System.out.println("Au revoir! 👋");
                        break;

                    default:
                        printColoredMessage("Option invalide!", false);
                }
            } catch (InputMismatchException e) {
                printColoredMessage("Erreur de saisie. Veuillez entrer un nombre valide.", false);
                scanner.nextLine(); // Clear the invalid input
                choix = -1; // Continue the loop
            } catch (Exception e) {
                printColoredMessage("Une erreur est survenue : " + e.getMessage(), false);
                choix = -1; // Continue the loop
            }
        } while (choix != 0);

        scanner.close();
    }}

*/
