package com.example.rene.myarrow.server.clients;

/**
 * Schnittstelle zur Tabelle ClientsTbl
 * Die Klasse liefert
 *
 * SQL-Code zur Erzeugung der Tabelle
 * SQL-Code für alle für MyArrow erforderlichen Statements
 *
 * @author Rene Dueber
 */
public final class ClientsTbl implements ClientsColumns {

    /**
     * Name der Datenbanktabelle.
     */
    public static final String TABLE_NAME = "clients";

    /**
     * SQL Anweisung zur Schemadefinition.
     */
    public static final String SQL_CREATE =
        "CREATE TABLE " + TABLE_NAME + " (" +
            "_id         INTEGER PRIMARY KEY AUTOINCREMENT," +
            "deviceid    TEXT NOT NULL," +
            "name        TEXT NOT NULL," +
            "zeitstempel LONG " +
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
    public static final String STMT_INSERT = "INSERT INTO "+ TABLE_NAME +
            " (deviceid, name, zeitstempel) " + "VALUES (?, ?, ?)";

    /**
     * SQL-Anweisung zur Loeschung aller Pfeile
     */
    public static final String STMT_CLIENTS_DELETE = "DELETE " + TABLE_NAME;

    /**
     * Liste aller bekannten Attribute.
     */
    public static final String[] ALL_COLUMNS = new String[] {
                ID,
                DEVICEID,
                NAME,
                ZEITSTEMPEL
    };

    /**
     * WHERE-Bedingung fuer ID-Anfrage.
     */
    public static final String WHERE_ID_EQUALS = ID + "=?";

    /**
     * WHERE-Bedingung fuer DEVICEID-Anfrage.
     */
    public static final String STMT_WHERE_DEVICEID_EQUALS = 
            "select " + ID + " from " + TABLE_NAME + " WHERE " + DEVICEID + "=?";

    /**
     * WHERE-Bedingung fuer DEVICEID-Anfrage.
     */
    public static final String STMT_WHERE_DEVICEID_NOT_EQUALS = 
            "select " + DEVICEID + " from " + TABLE_NAME + " WHERE " + DEVICEID + " != ?";    
    /**
     * Klasse enthaelt nur Konstanten.
     * Daher keine Objekterzeugung vorgesehen.
     */
    private ClientsTbl() {
    }
}
