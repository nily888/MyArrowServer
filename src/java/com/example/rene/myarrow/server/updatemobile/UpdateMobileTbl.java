package com.example.rene.myarrow.server.updatemobile;

import com.example.rene.myarrow.server.updatemobile.*;

/**
 * Created by nily on 14.12.15.
 */
public class UpdateMobileTbl  implements UpdateMobileColumns {

    /**
     * Name der Datenbanktabelle.
     */
    public static final String TABLE_NAME = "updatemobile";

    /**
     * SQL Anweisung zur Schemadefinition.
     */
    public static final String SQL_CREATE = "";

    /**
     * Standard-Sortierreihenfolge fuer die Tabelle.
     *
     * Sortiert wird nach Zeitstempel absteigend.
     */
    public static final String DEFAULT_SORT_ORDER = TRANSFERED + " DESC";

    /**
     * SQL Anweisung zur Loeschung der Tabelle.
     */
    public static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

    /**
     * SQL Anweisung fuer Erzeugung eines
     * neuen Eintrages.
     */
    public static final String STMT_INSERT = "INSERT INTO "+ TABLE_NAME +
            " (deviceid, tablename, fieldname, old_gid, new_gid)" +
            " VALUES (?,?,?,?,?)";

    /**
     * SQL Anweisung fuer Aktualisierung eines
     * Eintrages.
     */
    public static final String STMT_UPDATE = "UPDATE "+ TABLE_NAME + " SET " +
            " deviceid=?, tablename=?, fieldname=?, old_gid=?, new_gid=?" +
            " WHERE id=?";
    
    /**
     * SQL-Anweisung zur Loeschung aller Pfeile
     */
    public static final String STMT_UpdateMobile_DELETE = "DELETE " + TABLE_NAME;

    /** Liste aller bekannten Attribute. */
    public static final String[] ALL_COLUMNS = new String[] {
            ID,
            DEVICEID,
            TABLENAME,
            FIELDNAME,
            OLD_GID,
            NEW_GID,
            TRANSFERED
    };

    /**
     * WHERE-Bedingung fuer ID-Anfrage.
     */
    public static final String STMT_WHERE_ID_EQUALS = 
            "select " 
                + ID          + ", "
                + DEVICEID    + ", "
                + TABLENAME   + ", "
                + FIELDNAME   + ", "
                + OLD_GID     + ", "
                + NEW_GID     + ", "
                + TRANSFERED  + " " +
            "from " + TABLE_NAME + " " +
            "where " + ID + "=?";

    /**
     * WHERE-Bedingung Get All.
     */
    public static final String STMT_GET_ALL = 
            "select " 
                + ID          + ", "
                + DEVICEID    + ", "
                + TABLENAME   + ", "
                + FIELDNAME   + ", "
                + OLD_GID     + ", "
                + NEW_GID     + ", "
                + TRANSFERED  + " " +
            "from " + TABLE_NAME;

    /**
     * WHERE-Bedingung Get All.
     */
    public static final String STMT_NOT_TRANSFERED = 
            "select " 
                + ID          + ", "
                + DEVICEID    + ", "
                + TABLENAME   + ", "
                + FIELDNAME   + ", "
                + OLD_GID     + ", "
                + NEW_GID     + ", "
                + TRANSFERED  + " " +
            "from " + TABLE_NAME + " " +
            "where " + TRANSFERED + "=0";

    /**
     * WHERE-Bedingung fuer GID-Anfrage.
     */
    public static final String WHERE_ID_EQUALS = ID + "=?";

    /**
     * Klasse enthaelt nur Konstanten.
     * Daher keine Objekterzeugung vorgesehen.
     */
    private UpdateMobileTbl() {
    }

}
