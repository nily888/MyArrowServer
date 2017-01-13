package com.example.rene.myarrow.server.managegid;

import com.example.rene.myarrow.server.clients.*;

/**
 * Schnittstelle zur Tabelle ClientsTbl
 * Die Klasse liefert
 *
 * SQL-Code zur Erzeugung der Tabelle
 * SQL-Code für alle für MyArrow erforderlichen Statements
 *
 * @author Rene Dueber
 */
public final class ManageGIDTbl implements ManageGIDColumns {

    /**
     * Name der Datenbanktabelle.
     */
    public static final String TABLE_NAME = "managegid";

    /**
     * SQL Anweisung zur Schemadefinition.
     */
    public static final String SQL_CREATE =
        "CREATE TABLE " + TABLE_NAME + " (" +
            "_id          INTEGER PRIMARY KEY AUTOINCREMENT," +
            "tablename    TEXT NOT NULL," +
            "servergid    TEXT NOT NULL," +
            "mobilgid     TEXT NOT NULL," +
            "deviceid     TEXT NOT NULL " +
        ");";

    /**
     * Standard-Sortierreihenfolge fuer die Tabelle.
     *
     * Sortiert wird nach Zeitstempel absteigend.
     */
    public static final String DEFAULT_SORT_ORDER = SERVERGID + " DESC";

    /**
     * SQL Anweisung zur Loeschung der Tabelle.
     */
    public static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

    /**
     * SQL Anweisung fuer Erzeugung eines neuen Eintrages.
     */
    public static final String STMT_INSERT = "INSERT INTO "+ TABLE_NAME +
            " (tablename, servergid, mobilgid, deviceid) " + "VALUES (?, ?, ?, ?)";

    /**
     * QL-Anweisung zum Update genau eines Datensatzes
     */
    public static final String STMT_UPDATE_MOBILGID = "UPDATE " + TABLENAME +
            " SET " + MOBILGID + "= ? WHERE " + TABLENAME + " =? AND " +
            SERVERGID + " =? AND " + MOBILGID + " =? ";
    
    /**
     * SQL-Anweisung zur Loeschung aller Eintraege
     */
    public static final String STMT_DELETE_MANAGEGID = "DELETE FROM " + TABLE_NAME;

    /**
     * SQL-Anweisung zur Loeschung aller Eintraege
     */
    public static final String STMT_DELETE_MOBILGID = "DELETE FROM " + TABLE_NAME +
            " WHERE " + TABLENAME + " = ? AND " + MOBILGID + " =? ";

    /**
     * SQL-Anweisung zur Loeschung aller Eintraege
     */
    public static final String STMT_DELETE_SERVERGID = "DELETE FROM " + TABLE_NAME +
            " WHERE " + TABLENAME + " = ? AND " + SERVERGID + " =? ";

    /**
     * Liste aller bekannten Attribute.
     */
    public static final String[] ALL_COLUMNS = new String[] {
                ID,
                TABLENAME,
                SERVERGID,
                MOBILGID,
                DEVICEID
    };

    /**
     * Select fuer ID-Anfrage.
     */
    public static final String WHERE_ID_EQUALS = ID + "=?";

    /**
     * Select fuer TABLENAME/SERVERGID/DEVICEID-Anfrage.
     */
    public static final String STMT_WHERE_TABLENAME_SERVERGID_DEVICEID_EQUALS = 
            "select " + ID + ", " + MOBILGID + " from " + TABLE_NAME +
            " where " + TABLENAME + "=? and " + SERVERGID + "=? and " +
            DEVICEID + " =?";

    /**
     * Select fuer TABLENAME/MOBILGID/DEVICEID-Anfrage.
     */
    public static final String STMT_WHERE_TABLENAME_MOBILGID_DEVICEID_EQUALS = 
            "select " + ID + ", " + SERVERGID + " from " + TABLE_NAME + 
            " where " + TABLENAME + "=? and " + MOBILGID + "=? and " +
            DEVICEID + " =?";    

    /**
     * Klasse enthaelt nur Konstanten.
     * Daher keine Objekterzeugung vorgesehen.
     */
    private ManageGIDTbl() {
    }
}
