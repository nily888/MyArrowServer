package com.example.rene.myarrow.server.parcour;

/**
 * Created by nily on 11.06.16.
 */
public class ParcourTbl implements ParcourColumns {

    /**
     * Name der Datenbanktabelle.
     */
    public static final String TABLE_NAME = "parcour";

    /**
     * SQL Anweisung zur Schemadefinition.
     */
    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    "_id                 INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "gid                 TEXT NOT NULL," +
                    "name                TEXT NOT NULL," +
                    "anzahl_ziele        INTEGER NOT NULL, " +
                    "strasse             TEXT," +
                    "plz                 TEXT," +
                    "ort                 TEXT," +
                    "gps_lat_koordinaten TEXT," +
                    "gps_lon_koordinaten TEXT," +
                    "anmerkung           TEXT," +
                    "standard            INTEGER," +
                    "zeitstempel         LONG " +
                    ");";

    /**
        CREATE TABLE `myarrow`.`parcour` (
        `_id` INT NOT NULL AUTO_INCREMENT,
        `gid` VARCHAR(50) NOT NULL,
        `name` VARCHAR(256) NOT NULL,
        `anzahl_ziele` INT NOT NULL,
        `strasse` VARCHAR(50) NULL,
        `plz` VARCHAR(50) NULL,
        `ort` VARCHAR(50) NULL,
        `gps_lat_koordinaten` VARCHAR(50) NULL,
        `gps_lon_koordinaten` VARCHAR(50) NULL,
        `anmerkung` VARCHAR(256) NULL,
        `standard` INT NULL,
        `zeitstempel` BIGINT NULL,
        PRIMARY KEY (`_id`, `gid`),
        UNIQUE INDEX `gid_UNIQUE` (`gid` ASC),
        UNIQUE INDEX `_id_UNIQUE` (`_id` ASC));
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
            " (gid, name, anzahl_ziele, strasse, plz, ort, gps_lat_koordinaten," +
            " gps_lon_koordinaten, anmerkung, standard, zeitstempel)" +
            " VALUES (?,?,?,?,?,?,?,?,?,?,?)";

    /**
     * SQL Anweisung fuer den Update eines Eintrages.
     */
    public static final String STMT_UPDATE = "UPDATE "+ TABLE_NAME + " SET " +
            " name=?, anzahl_ziele=?, strasse=?, plz=?, ort=?," +
            " gps_lat_koordinaten=?, gps_lon_koordinaten=?, anmerkung=?," +
            " standard=?, zeitstempel=? WHERE GID=?";
    
    /**
     * SQL-Anweisung zur Loeschung aller Pfeile
     */
    public static final String STMT_Pfeil_DELETE = "DELETE " + TABLE_NAME;

    /** Liste aller bekannten Attribute. */
    public static final String[] ALL_COLUMNS = new String[] {
            ID,
            GID,
            NAME,
            ANZAHL_ZIELE,
            STRASSE,
            PLZ,
            ORT,
            GPS_LAT_KOORDINATEN,
            GPS_LON_KOORDINATEN,
            ANMERKUNG,
            STANDARD,
            ZEITSTEMPEL
    };

    /**
     * WHERE-Bedingung fuer ID-Anfrage.
     */
    public static final String WHERE_ID_EQUALS = ID + "=?";

    /**
     * STMT WHERE-Bedingung fuer ID-Anfrage.
     */
    public static final String STMT_WHERE_GID_EQUALS =
            "select " +
            ID + ", " +
            GID + ", " +
            NAME + ", " +
            ANZAHL_ZIELE + ", " +
            STRASSE + ", " +
            PLZ + ", " +
            ORT + ", " +
            GPS_LAT_KOORDINATEN + ", " +
            GPS_LON_KOORDINATEN + ", " +
            ANMERKUNG + ", " +
            STANDARD + ", " +
            ZEITSTEMPEL + " " +
            "from " + TABLE_NAME + " " +
            "where " + GID + "=?";
    
    /**
     * WHERE-Bedingung fuer Pfeil-Anfrage.
     */
    public static final String WHERE_PARCOUR_LIKE = NAME + "=?";

    /**
     * Klasse enthaelt nur Konstanten.
     * Daher keine Objekterzeugung vorgesehen.
     */
    private ParcourTbl() {}

}
