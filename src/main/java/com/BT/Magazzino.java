package com.BT;


import java.sql.*;
import java.util.Scanner;

public class Magazzino {
    private static final String[] PRODOTTI = {"Vite", "Bullone", "Dado", "Chiodo", "Martello"};
    private static final String DB_URL = "jdbc:mysql://localhost:3306/fondi";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Mamma99*";
    private Scanner scanner = new Scanner(System.in);

    // Costruttore
    public Magazzino(int numScaffali, int posizioniPerScaffale) {
        System.out.println("Magazzino inizializzato.");
    }

    public static void main(String[] args) {
        Magazzino magazzino = new Magazzino(10, 12);
        magazzino.esegui();
    }

    // Metodo per inserire un prodotto nel database
    private void inserisciProdotto() {
        System.out.print("Inserisci nome del prodotto: ");
        String nome = scanner.nextLine();
        System.out.print("Inserisci numero dello scaffale: ");
        int scaffale = scanner.nextInt();
        System.out.print("Inserisci numero della posizione: ");
        int posizione = scanner.nextInt();
        scanner.nextLine();

        String query = "INSERT INTO prodotti (nome, scaffale, posizione) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fondi", "root", "Mamma99*");
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nome);
            pstmt.setInt(2, scaffale);
            pstmt.setInt(3, posizione);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Prodotto inserito nel database con successo.");
            } else {
                System.out.println("Errore durante l'inserimento del prodotto.");
            }
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    // Metodo per modificare un prodotto
    private void modificaProdotto() {
        System.out.print("Inserisci il nome del prodotto da modificare: ");
        String nome = scanner.nextLine();
        System.out.print("Inserisci il nuovo scaffale: ");
        int nuovoScaffale = scanner.nextInt();
        System.out.print("Inserisci la nuova posizione: ");
        int nuovaPosizione = scanner.nextInt();
        scanner.nextLine();

        String query = "UPDATE prodotti SET scaffale = ?, posizione = ? WHERE nome = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fondi", "root", "Mamma99*");
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, nuovoScaffale);
            pstmt.setInt(2, nuovaPosizione);
            pstmt.setString(3, nome);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Prodotto aggiornato con successo.");
            } else {
                System.out.println("Prodotto non trovato.");
            }
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    // Metodo per visualizzare tutti i prodotti
    private void visualizzaProdotti() {
        String query = "SELECT nome, scaffale, posizione FROM prodotti";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fondi", "root", "Mamma99*");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("Prodotti nel magazzino:");
            while (rs.next()) {
                String nome = rs.getString("nome");
                int scaffale = rs.getInt("scaffale");
                int posizione = rs.getInt("posizione");
                System.out.println("Prodotto: " + nome + ", Scaffale: " + scaffale + ", Posizione: " + posizione);
            }
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    // Metodo per cancellare un prodotto
    private void cancellaProdotto() {
        System.out.print("Inserisci il nome del prodotto da cancellare: ");
        String nome = scanner.nextLine();

        String query = "DELETE FROM prodotti WHERE nome = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fondi", "root", "Mamma99*");
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nome);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Prodotto cancellato con successo.");
            } else {
                System.out.println("Prodotto non trovato.");
            }
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    // Metodo per cercare un prodotto per nome
    private void cercaProdotto() {
        System.out.println("==============================================");
        System.out.print("Inserisci il nome del prodotto da cercare: ");
        String nome = scanner.nextLine();
        System.out.println("===============================================");

        String query = "SELECT scaffale, posizione FROM prodotti WHERE nome = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fondi", "root", "Mamma99*");
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nome);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                do {
                    int scaffale = rs.getInt("scaffale");
                    int posizione = rs.getInt("posizione");
                    System.out.println("Prodotto '" + nome + "' trovato in Scaffale " + scaffale + ", Posizione " + posizione);
                } while (rs.next());
            } else {
                System.out.println("Prodotto non trovato.");
            }
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }


    // Metodo per mostrare il menu principale
    private void mostraMenu() {
        System.out.println("===========================PRESENTAZIONE DEL MENU=====================================");
        System.out.println("\nMenu:");
        System.out.println("1. Inserisci Prodotto");
        System.out.println("2. Modifica Prodotto");
        System.out.println("3. Visualizza tutti i Prodotti");
        System.out.println("4. Cancella Prodotto");
        System.out.println("5. Cerca Prodotto");
        System.out.println("6. Esci");
        System.out.println("=====================================================================================");
    }


    // Metodo per eseguire le operazioni del magazzino
    public void esegui() {
        boolean continua = true;
        while (continua) {
            mostraMenu();
            System.out.print("Seleziona un'opzione: ");
            int scelta = scanner.nextInt();
            scanner.nextLine(); // Consuma la nuova linea

            switch (scelta) {
                case 1:
                    //System.out.println("INSERISCI IL PRODOTTO");
                    inserisciProdotto();
                    break;
                case 2:
                    modificaProdotto();
                    break;
                case 3:
                    visualizzaProdotti();
                    break;
                case 4:
                    cancellaProdotto();
                    break;
                case 5:
                    cercaProdotto();
                    break;
                case 6:
                    continua = false;
                    System.out.println("Uscita dal programma.");
                    break;
                default:
                    System.out.println("Opzione non valida. Riprova.");
                    break;
            }
        }
    }
}

