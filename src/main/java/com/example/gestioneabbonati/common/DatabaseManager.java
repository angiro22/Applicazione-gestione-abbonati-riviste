package com.example.gestioneabbonati.common;

import com.example.gestioneabbonati.Subscriber;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

public class DatabaseManager {
        private static final String DB_PATH = "database/abbonati.db"; // dir database
        private static final String URL = "jdbc:sqlite:" + DB_PATH; // url connessione SQLite
        private static final String SCHEMA_PATH = "abbonati/schema.sql"; // dir istruzioni sql ddl

        // Inserire abbonati nel db
        public static boolean addSubscriber(Subscriber subscriber) {
                // I '?' verranno sostituiti con i dati dell'abbonato
                String sql = """
                        INSERT INTO abbonati (code, magazineName, secondName, name, address, gender, city)
                        VALUES (?, ?, ?, ?, ?, ?, ?)
                        """;
                try (Connection conn = getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                        // Sostituzione dei '?' con i dati dell'abbonato
                        preparedStatement.setString(1, subscriber.getCode());
                        preparedStatement.setString(2, subscriber.getMagazineName());
                        preparedStatement.setString(3, subscriber.getSecondName());
                        preparedStatement.setString(4, subscriber.getName());
                        preparedStatement.setString(5, subscriber.getAddress());
                        preparedStatement.setString(6, subscriber.getGender());
                        preparedStatement.setString(7, subscriber.getCity());

                        // Inserimento nel database
                        int rowsAffected = preparedStatement.executeUpdate();
                        return rowsAffected > 0;
                } catch (SQLException e) {
                        System.err.println("Errore durante l'inserimento dell'abbonato: " + e.getMessage());
                        e.printStackTrace();
                        return false;
                }
        }

        // Connessione al database
        public static Connection getConnection() throws SQLException {
                ensureDirectoryExsists();
                return DriverManager.getConnection(URL);
        }

        // Verifica che la directory del database esiste, in caso contrario viene creata
        private static void ensureDirectoryExsists() {
                File file = new File(DB_PATH);
                File parentDir = file.getParentFile();
                if (parentDir != null && !parentDir.exists()) {
                        parentDir.mkdirs();
                }
        }

        // Inizializzazione database
        public static void initializeDatabase() {
                Path schemaFile = Path.of(SCHEMA_PATH);

                if (!Files.exists(schemaFile)) {
                        System.err.println("File " + SCHEMA_PATH + " non trovato!");
                        return;
                }

                try {
                        // Legge il contenuto di schema.sql
                        String sqlScript = Files.readString(schemaFile);

                        // Apre la connessione al db ed esegue lo script
                        try (Connection conn = getConnection();
                        Statement stmt = conn.createStatement()) {
                                stmt.executeUpdate(sqlScript);
                        } catch (SQLException e) {
                                System.err.println("Errore SQL durante l'esecuzione di schema.sql " + e.getMessage());
                                e.printStackTrace();
                        }
                } catch (IOException e) {
                        System.err.println("Errore di lettura del file schema.sql: " + e.getMessage());
                        e.printStackTrace();
                }
        }
}