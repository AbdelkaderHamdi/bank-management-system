package org.example;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.List;

public class Main {
    public static void displayLoginMenu() {
        System.out.println("Bienvenue dans le système bancaire.");
        System.out.println("1. Se connecter");
        System.out.println("2. S'inscrire");
        System.out.println("0. Quitter");
        System.out.print("Entrez votre choix : ");
    }

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

    public static void displayAdminMenu() {
        System.out.println("===== Admin workSpace ===== ");
        System.out.println("1. Gestion des Clients");
        System.out.println("2. Gestion des Comptes");
        System.out.println("3. Opérations Bancaires");
        System.out.println("0. Déconnexion");
        System.out.print("Choisissez une option : ");
    }


    public static void displayClientsMenu() {
        System.out.println("===== Gestion Client =====");
        System.out.println("1. Ajouter un client");
        System.out.println("2. Consulter un client");
        System.out.println("3. Modifier un client");
        System.out.println("4. Supprimer un client");
        System.out.println("0. Retour");
        System.out.print("Choisissez une option : ");
    }

    public static void displayComptesMenu() {
        System.out.println("===== GESTION DES COMPTES =====");
        System.out.println("1. Créer un compte courant");
        System.out.println("2. Créer un compte épargne");
        System.out.println("3. Consulter un compte");
        System.out.println("0. Retour");
        System.out.print("Choisissez une option : ");
    }

    public static void displayOperationsMenu() {
        System.out.println("===== OPÉRATIONS BANCAIRES =====");
        System.out.println("1. Retrait");
        System.out.println("2. Versement");
        System.out.println("3. Virement");
        System.out.println("4. voir historique des operations");
        System.out.println("0. Retour");
        System.out.print("Choisissez une option : ");
    }


    public static void adminServices(Scanner scanner, GestionClient gestionClient, GestionCompte gestionCompte, GestionOperation gestionOperation) throws SQLException {
        int choix;
        do {
            displayAdminMenu();
            choix = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choix) {
                case 1:
                    // Client Management Submenu
                    boolean retourMenuPrincipal = false;
                    do {
                        displayClientsMenu();
                        int sousChoixClients = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        switch(sousChoixClients) {
                            case 1: // Ajouter client
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
                                System.out.print("CIN du client : ");
                                String cinCourant = scanner.nextLine();
                                System.out.print("Solde initial : ");
                                double soldeCourant = scanner.nextDouble();

                                Comptes nouveauCompteCourant = new CompteCourant(cinCourant, soldeCourant, 10);
                                if (gestionCompte.ajouterCompte(nouveauCompteCourant)) {
                                    printColoredMessage("Compte courant créé avec succès!", true);
                                } else {
                                    printColoredMessage("Erreur lors de la création du compte courant.", false);
                                }
                                break;

                            case 2: // Créer compte épargne
                                System.out.print("CIN du client : ");
                                String cinEpargne = scanner.nextLine();
                                System.out.print("Solde initial : ");
                                double soldeEpargne = scanner.nextDouble();

                                Comptes nouveauCompteEpargne = new CompteEpargne(cinEpargne, soldeEpargne, 10);
                                if (gestionCompte.ajouterCompte(nouveauCompteEpargne)) {
                                    printColoredMessage("Compte épargne créé avec succès!", true);
                                } else {
                                    printColoredMessage("Erreur lors de la création du compte épargne.", false);
                                }
                                break;

                            case 3: // Consulter compte
                                System.out.print("Cin du client : ");
                                String cin = scanner.nextLine();
                                Comptes compte = gestionCompte.getCompte(cin);
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
                    System.out.println("Déconnexion !.");
                    displayLoginMenu();
                    break;
                default:
                    printColoredMessage("Option invalide!", false);
            }
        } while (choix != 0);
    }

    public static void clientServices(Scanner scanner, GestionClient gestionClient, GestionCompte gestionCompte, GestionOperation gestionOperation, String currentClientCIN) throws SQLException {
        int choix;
        do {
            System.out.println("===== Client workSpace =====");
            System.out.println("1. Consulter mes informations");
            System.out.println("2. Consulter mes comptes");
            System.out.println("3. Effectuer des opérations (Retrait, Versement, Virement.)");
            System.out.println("0. Déconnexion");
            System.out.print("Entrez votre choix : ");

            choix = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choix) {
                case 1:
                    // Consulter informations client
                    Client client = gestionClient.getClient(currentClientCIN);
                    if (client != null) {
                        System.out.println("Vos informations :");
                        System.out.println(client);
                    } else {
                        printColoredMessage("Erreur lors de la récupération de vos informations.", false);
                    }
                    break;

                case 2:
                    // Consulter comptes du client
                    List<Comptes> comptes = gestionCompte.getComptesByClient(currentClientCIN);
                    if (comptes != null && !comptes.isEmpty()) {
                        System.out.println("Vos comptes :");
                        comptes.forEach(compte -> System.out.println(compte.toString()));
                    } else {
                        printColoredMessage("Aucun compte trouvé.", false);
                    }
                    break;

                case 3:
                    System.out.println("1. Retrait");
                    System.out.println("2. Versement");
                    System.out.println("3. Virement");
                    System.out.println("4. voir historique des operations");

                    int sousChoixOperations = scanner.nextInt();
                    scanner.nextLine();
                    switch(sousChoixOperations) {
                        case 1: // Retrait
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

                        default:
                            printColoredMessage("Option invalide!", false);
                    }
                    break;

                case 0:
                    System.out.println("Déconnexion !");
                    displayLoginMenu();
                    break;

                default:
                    printColoredMessage("Option invalide!", false);
            }
        } while (choix != 0);
    }


    public static void main(String[] args) throws SQLException {
        DatabaseConfig.getConnection(); // Initialize database

        GestionClient gestionClient = new GestionClient();
        GestionCompte gestionCompte = new GestionCompte();
        GestionOperation gestionOperation = new GestionOperation();
        Authentication auth = new Authentication();

        Scanner scanner = new Scanner(System.in);
        boolean isAdmin = false;

        int choix;
        do {
            displayLoginMenu();
            choix = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choix) {
                case 1: // Admin or Client login
                    System.out.print("Nom d'utilisateur : ");
                    String Username = scanner.nextLine();
                    System.out.print("Mot de passe : ");
                    String Password = scanner.nextLine();

                    isAdmin = Username.equals("admin") && Password.equals("admin123");
                    if (isAdmin) {
                        adminServices(scanner, gestionClient, gestionCompte, gestionOperation);
                    } else {
                        if (auth.authentifier(Username, Password)) {
                            printColoredMessage("Connexion réussie!", true);
                            String Cin = gestionClient.getCinByUsername(Username);
                            clientServices(scanner, gestionClient, gestionCompte, gestionOperation, Cin);
                        } else printColoredMessage("Nom d'utilisateur ou mot de passe incorrect.", false);
                    }
                    break;

                case 2: // Client sign-up
                            System.out.print("CIN : ");
                            String newCin = scanner.nextLine();
                            System.out.print("Nom : ");
                            String nom = scanner.nextLine();
                            System.out.print("Prénom : ");
                            String prenom = scanner.nextLine();
                            System.out.print("Téléphone : ");
                            String telephone = scanner.nextLine();
                            System.out.print("Mot de passe : ");
                            String newPassword = scanner.nextLine();

                            Client nouveauClient = new Client(newCin, nom, prenom, telephone);
                            auth.creerUtilisateur(nom, newCin, newPassword);


                            if (gestionClient.ajouterClient(nouveauClient)) {
                                printColoredMessage("Inscription réussie!", true);
                                clientServices(scanner, gestionClient, gestionCompte, gestionOperation, newCin);
                            } else {
                                printColoredMessage("Erreur lors de l'inscription. Réessayez.", false);
                            }
                            break;

                case 0:
                            System.out.println("Au revoir! 👋");
                            break;

                default:
                            printColoredMessage("Option invalide!", false);
                    }
            }
            while (choix != 0) ;

            scanner.close();
        }

    }


