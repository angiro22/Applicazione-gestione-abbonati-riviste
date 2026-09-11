package com.example.gestioneabbonati.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
        private static final String DB_PATH = "database/abbonati.db"; // dir database
        private static final String URL = "jdbc:sqlite:" + DB_PATH; // url connessione SQLite
        private static final String SCHEMA_PATH = "abbonati/schema.sql"; // dir istruzioni sql ddl

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