package com.example.rene.myarrow.server.pfeil;

/**
 * Created by nily on 14.12.15.
 */
public class PfeilTbl  implements PfeilColumns {

    /**
     * Name der Datenbanktabelle.
     */
    public static final String TABLE_NAME = "pfeil";

    /**
     * SQL Anweisung zur Schemadefinition.
     */
    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "gid TEXT, " +
                    "name TEXT NOT NULL," +
                    "dateiname TEXT," +
                    "standard BOOLEAN," +
                    "zeitstempel LONG " +
                    ");";
/**
 * 
 CREATE TABLE `myarrow`.`pfeil` (
  `_id` INT NOT NULL AUTO_INCREMENT,
  `gid` VARCHAR(50) NOT NULL,
  `name` VARCHAR(50) NULL,
  `dateiname` VARCHAR(256) NULL,
  `standard` INT NULL,
  `zeitstempel` BIGINT NULL,
  PRIMARY KEY (`_id`, `gid`),
  UNIQUE INDEX `gid_UNIQUE` (`gid` ASC),
  UNIQUE INDEX `_id_UNIQUE` (`_id` ASC));
 * 
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
            " (gid, name, dateiname, standard, zeitstempel)" +
            " VALUES (?,?,?,?,?)";

    /**
     * SQL Anweisung fuer Aktualisierung eines
     * Eintrages.
     */
    public static final String STMT_UPDATE = "UPDATE "+ TABLE_NAME + " SET " +
            " name=?, dateiname=?, standard=?, zeitstempel=?" +
            " WHERE gid=?";


    
    /**
     * SQL-Anweisung zur Loeschung aller Pfeile
     */
    public static final String STMT_Pfeil_DELETE = "DELETE " + TABLE_NAME;

    /** Liste aller bekannten Attribute. */
    public static final String[] ALL_COLUMNS = new String[] {
            ID,
            GID,
            NAME,
            DATEINAME,
            STANDARD,
            ZEITSTEMPEL
    };

    /**
     * WHERE-Bedingung fuer ID-Anfrage.
     */
    public static final String WHERE_ID_EQUALS = ID + "=?";

    /**
     * WHERE-Bedingung fuer GID-Anfrage.
     */
    public static final String WHERE_GID_EQUALS = GID + "=?";

            /**
     * WHERE-Bedingung fuer DEVICEID-Anfrage.
     */
    public static final String STMT_WHERE_GID_EQUALS = 
            "select " + 
                ID               + ", " +
                GID              + ", " +
                NAME             + ", " +
                DATEINAME        + ", " +
                STANDARD         + ", " +
                ZEITSTEMPEL      + " " +
            "from " + TABLE_NAME + " " +
            "where " + GID + "=?";

    /**
     * WHERE-Bedingung fuer Bogen-Anfrage.
     */
    public static final String WHERE_BOGEN_EQUALS = NAME + "=?";

    /**
    * Statement to get all records (ID, GID, Name)
    */
    public static final String STMT_WHERE_GID_NAME_EQUALS  =
            "select " + ID   + ", " + GID + ", " + NAME + " " +
            "from " + TABLE_NAME;
    
    /**
     * Statement to get all records (ID, GID, Name) where the name is equal
     */
    public static final String STMT_WHERE_GID_NAME_NAME_EQUALS  =
            "select " + ID   + ", " + GID + ", " + NAME + " " +
            "from " + TABLE_NAME + " " +
            "where " + NAME + "=?";

    /**
     * Klasse enthaelt nur Konstanten.
     * Daher keine Objekterzeugung vorgesehen.
     */
    private PfeilTbl() {
    }

}
