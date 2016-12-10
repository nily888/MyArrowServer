package com.example.rene.myarrow.server.transfer;

/**
 * Schnittstelle zur Tabelle ClientsTbl
 * Die Klasse liefert
 *
 * SQL-Code zur Erzeugung der Tabelle
 * SQL-Code für alle für MyArrow erforderlichen Statements
 *
 * @author Rene Dueber
 */
public final class TransferTbl implements TransferColumns {

    /**
     * Name der Datenbanktabelle.
     */
    public static final String TABLE_NAME = "transfer";

    /**
     * SQL Anweisung zur Schemadefinition.
     */
    public static final String SQL_CREATE =
        "CREATE TABLE " + TABLE_NAME + " (" +
            "gid          TEXT PRIMARY NOT NULL," +
            "deviceid     TEXT NOT NULL," +
            "target_table TEXT NOT NULL," +
            "transfered   INTEGER DEFAULT 0, " +
            "zeitstempel  LONG " +
        ");";

    /**
     * Standard-Sortierreihenfolge fuer die Tabelle.
     *
     * Sortiert wird nach Zeitstempel absteigend.
     */
    public static final String DEFAULT_SORT_ORDER = DEVICEID + " DESC";

    /**
     * SQL Anweisung zur Loeschung der Tabelle.
     */
    public static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

    /**
     * SQL Anweisung fuer Erzeugung eines neuen Eintrages.
     */
    public static final String STMT_INSERT =
            "INSERT INTO " + TABLE_NAME + " " +
            "(gid, deviceid, target_table, transfered, zeitstempel) " +
            "VALUES (?, ?, ?, ?, ?)";

    /**
     * SQL-Anweisung zur Loeschung aller Pfeile
     */
    public static final String STMT_CLIENTS_DELETE = "DELETE " + TABLE_NAME;

    /**
     * Liste aller bekannten Attribute.
     */
    public static final String[] ALL_COLUMNS = new String[] {
                GID,
                DEVICEID,
                TARGET_TABLE,
                TRANSFERED,
                ZEITSTEMPEL
    };

    /**
     * WHERE-Bedingung fuer ID-Anfrage.
     */
    public static final String WHERE_GID_EQUALS = GID + "=?";

    /**
     * WHERE-Bedingung fuer DEVICEID-Anfrage.
     */
    public static final String STMT_WHERE_DEVICEID_EQUALS = 
            "select " + GID + " from " + TABLE_NAME + " WHERE " + DEVICEID + "=?";

    /**
     * WHERE-Bedingung fuer DEVICEID-Anfrage.
     */
    public static final String STMT_TRANSFERLISTE = 
            "select " + ID +
            "," + GID +
            "," + TARGET_TABLE +
            " from " + TABLE_NAME +
            " WHERE " + DEVICEID + "=?" +
            " AND " + TRANSFERED + "=0";
    
    public static final String STMT_UPDATE_DONE = "update " + TABLE_NAME +
            " set " + TRANSFERED + "=1 where " + ID + "=?";
    
    /**
     * Klasse enthaelt nur Konstanten.
     * Daher keine Objekterzeugung vorgesehen.
     */
    private TransferTbl() {
    }
}
