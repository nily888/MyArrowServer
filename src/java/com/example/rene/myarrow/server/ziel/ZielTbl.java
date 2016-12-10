package com.example.rene.myarrow.server.ziel;

/**
 * Created by nily on 15.12.15.
 */
public class ZielTbl implements ZielColumns {

    /**
     * Name der Datenbanktabelle.
     */
    public static final String TABLE_NAME = "ziel";

    /**
     * SQL Anweisung zur Schemadefinition.
     */
    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    "_id         INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "parcourid   LONG NOT NULL," +
                    "nummer      INTEGER NOT NULL," +
                    "name        TEXT NOT NULL," +
                    "gps_lat_koordinaten TEXT," +
                    "gps_lon_koordinaten TEXT," +
                    "dateiname   TEXT," +
                    "zeitstempel LONG " +
                    ");";

    /**
    CREATE TABLE `myarrow`.`ziel` (
        `_id` INT NOT NULL AUTO_INCREMENT,
        `gid` VARCHAR(50) NOT NULL,
        `parcourid` BIGINT NOT NULL,
        `nummer` INT NOT NULL,
        `name` VARCHAR(45) NOT NULL,
        `gps_lat_koordinaten` VARCHAR(50) NULL,
        `gps_lon_koordinaten` VARCHAR(50) NULL,
        `dateiname` VARCHAR(256) NULL,
        `zeitstempel` BIGINT NULL,
        PRIMARY KEY (`_id`, `gid`),
        UNIQUE INDEX `gid_UNIQUE` (`gid` ASC));
     */
    
    /**
     * Standard-Sortierreihenfolge fuer die Tabelle.
     *
     * Sortiert wird nach Zeitstempel absteigend.
     */
    public static final String DEFAULT_SORT_ORDER = NAME + " DESC";

    /**
     * SQL Anweisung zur Loeschung der Tabelle.
     */
    public static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

    /**
     * SQL Anweisung fuer Erzeugung eines
     * neuen Eintrages.
     */
    public static final String STMT_INSERT = "INSERT INTO "+ TABLE_NAME +
            " (gid, parcourgid, nummer, name, gps_lat_koordinaten," +
            " gps_lon_koordinaten, dateiname, zeitstempel)" +
            " VALUES (?,?,?,?,?,?,?,?)";

    /**
     * SQL Anweisung fuer Aktualisierung eines
     * Eintrages.
     */
    public static final String STMT_UPDATE = "UPDATE "+ TABLE_NAME + " SET " +
            " parcourgid=?, nummer=?, name=?, gps_lat_koordinaten=?," +
            " gps_lon_koordinaten=?, dateiname=?, zeitstempel=?" +
            " WHERE gid=?";

    /**
     * SQL-Anweisung zur Loeschung aller Ziele
     */
    public static final String STMT_ZIEL_DELETE = "DELETE " + TABLE_NAME;

    /** Liste aller bekannten Attribute. */
    public static final String[] ALL_COLUMNS = new String[] {
            ID,
            GID,
            PARCOURGID,
            NUMMER,
            NAME,
            GPS_LAT_KOORDINATEN,
            GPS_LON_KOORDINATEN,
            DATEINAME,
            ZEITSTEMPEL
    };

    /**
     * WHERE-Bedingung fuer ID-Anfrage.
     */
    public static final String STMT_WHERE_GID_EQUALS = 
            "select " +
                ID + ", " +
                GID + ", " +
                PARCOURGID + ", " +
                NUMMER + ", " +
                NAME + ", " +
                GPS_LAT_KOORDINATEN + ", " +
                GPS_LON_KOORDINATEN + ", " +
                DATEINAME + ", " +
                ZEITSTEMPEL + " " +
            "from " + TABLE_NAME + " " +
            "where " + GID + "=?";

    /**
     * WHERE-Bedingung fuer Parcour-Anfrage.
     */
    public static final String WHERE_PARCOUR_EQUALS = PARCOURGID + "=?";

    /**
     * Klasse enthaelt nur Konstanten.
     * Daher keine Objekterzeugung vorgesehen.
     */
    private ZielTbl() {
    }

}
