package com.example.rene.myarrow.server.rundenziel;

/**
 * Created by nily on 15.12.15.
 */
public class RundenZielTbl implements RundenZielColumns {

    /**
     * Name der Datenbanktabelle.
     */
    public static final String TABLE_NAME = "rundenziel";

    /**
     * SQL Anweisung zur Schemadefinition.
     */
    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    "_id                INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "gid                TEXT, " +
                    "rundengid          TEXT NOT NULL," +
                    "zielgid            TEXT NOT NULL," +
                    "rundenschuetzengid TEXT NOT NULL," +
                    "nummer             INTEGER NOT NULL," +
                    "eins               BOOLEAN," +
                    "zwei               BOOLEAN," +
                    "drei               BOOLEAN," +
                    "kill               BOOLEAN," +
                    "killkill           BOOLEAN," +
                    "punkte             INTEGER," +
                    "anmerkung          TEXT," +
                    "gpskoordinaten     DOUBLE," +
                    "dateiname          TEXT," +
                    "zeitstempel        LONG, " +
                    "transfered         INTEGER " +
                    ");";

    /**
    CREATE TABLE `myarrow`.`rundenziel` (
  `_id` INT NOT NULL AUTO_INCREMENT,
  `gid` VARCHAR(50) NOT NULL,
  `rundengid` VARCHAR(50) NOT NULL,
  `zielgid` VARCHAR(50) NOT NULL,
  `rundenschuetzengid` VARCHAR(50) NOT NULL,
  `nummer` INT NULL,
  `eins` INT NULL,
  `zwei` INT NULL,
  `drei` INT NULL,
  `kills` INT NULL,
  `killkill` INT NULL,
  `punkte` INT NULL,
  `anmerkung` VARCHAR(256) NULL,
  `dateiname` VARCHAR(256) NULL,
  `zeitstempel` BIGINT NULL,
  PRIMARY KEY (`_id`, `gid`),
  UNIQUE INDEX `_id_UNIQUE` (`_id` ASC),
  UNIQUE INDEX `gid_UNIQUE` (`gid` ASC));
     */
    
    /**
     * Standard-Sortierreihenfolge fuer die Tabelle.
     *
     * Sortiert wird nach Zeitstempel absteigend.
     */
    public static final String DEFAULT_SORT_ORDER = ZEITSTEMPEL + " DESC";

    /**
     * SQL Anweisung zur Loeschung der Tabelle.
     */
    public static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

    /**
     * SQL Anweisung fuer Erzeugung eines
     * neuen Eintrages.
     */
    public static final String STMT_INSERT = "INSERT INTO "+ TABLE_NAME + " (" 
            + GID + ", "
            + RUNDENGID  + ", "
            + ZIELGID  + ", "
            + RUNDENSCHUETZENGID + ", "
            + NUMMER + ", "
            + EINS + ", "
            + ZWEI + ", "
            + DREI + ", "
            + KILL + ", "
            + KILLKILL + ", "
            + PUNKTE + ", "
            + ANMERKUNG + ", "
            + DATEINAME + ", "
            + ZEITSTEMPEL + ") "
            + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    /**
     * SQL Anweisung fuer Erzeugung eines
     * neuen Eintrages.
     */
    public static final String STMT_UPDATE = "UPDATE "+ TABLE_NAME + " SET "
            + RUNDENGID + "=?, "
            + ZIELGID + "=?, "
            + RUNDENSCHUETZENGID + "=?, "
            + NUMMER + "=?, "
            + EINS + "=?, "
            + ZWEI + "=?, "
            + DREI + "=?, "
            + KILL + "=?, "
            + KILLKILL + "=?, "
            + PUNKTE + "=?, "
            + ANMERKUNG + "=?, "
            + DATEINAME + "=?, "
            + ZEITSTEMPEL + "=? "
            + "WHERE " + GID + "=?";

    /**
     * SQL-Anweisung zur Loeschung aller Pfeile
     */
    public static final String STMT_RUNDENZIEL_DELETE = "DELETE " + TABLE_NAME;

    /** Liste aller bekannten Attribute. */
    public static final String[] ALL_COLUMNS = new String[] {
            ID,
            GID,
            RUNDENGID,
            ZIELGID,
            RUNDENSCHUETZENGID,
            NUMMER,
            EINS,
            ZWEI,
            DREI,
            KILL,
            KILLKILL,
            PUNKTE,
            ANMERKUNG,
            DATEINAME,
            ZEITSTEMPEL
    };

    /**
     * STMT WHERE-Bedingung fuer ID-Anfrage.
     */
    public static final String STMT_WHERE_GID_EQUALS = 
            "select "
                + ID + ", "
                + GID + ", "
                + RUNDENGID  + ", "
                + ZIELGID  + ", "
                + RUNDENSCHUETZENGID + ", "
                + NUMMER + ", "
                + EINS + ", "
                + ZWEI + ", "
                + DREI + ", "
                + KILL + ", "
                + KILLKILL + ", "
                + PUNKTE + ", "
                + ANMERKUNG + ", "
                + DATEINAME + ", "
                + ZEITSTEMPEL + " "
            + "from " + TABLE_NAME + " "
            + "where " + GID + "=?";

    /**
     * WHERE-Bedingung fuer GID-Anfrage.
     */
    public static final String WHERE_GID_EQUALS = GID + "=?";

    /**
     * WHERE-Bedingung fuer alle Ziele einer Runde.
     */
    public static final String WHERE_RUNDENGID_EQUALS = RUNDENGID + "=?";

    /**
     * WHERE-Bedingung fuer aller Ziele einer Runde eines Schuetzen
     */
    public static final String WHERE_RUNDEN_NUMMER_SCHUETZEN_EQUALS =
            RUNDENGID + "=? AND " + NUMMER +"=? AND " + RUNDENSCHUETZENGID +"=?";

    /**
     * WHERE-Bedingung fuer aller Ziele einer Runde.
     */
    public static final String WHERE_RUNDENGID_NUMMER_EQUALS =
            RUNDENGID + "=? AND " + NUMMER +"=?";

    /**
     * Bildet die Summe über eine Runde
     */
    public static final String WHERE_SUMME_EINE_RUNDE_EIN_SCHUETZE =
            RUNDENGID + "=? AND " + RUNDENSCHUETZENGID + "=?";

    /**
     * Klasse enthaelt nur Konstanten.
     * Daher keine Objekterzeugung vorgesehen.
     */
    private RundenZielTbl() {
    }
}
