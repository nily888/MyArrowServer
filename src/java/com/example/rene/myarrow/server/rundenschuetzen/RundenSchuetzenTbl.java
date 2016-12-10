package com.example.rene.myarrow.server.rundenschuetzen;

public class RundenSchuetzenTbl implements RundenSchuetzenColumns {

    /**
     * Name der Datenbanktabelle.
     */
    public static final String TABLE_NAME = "rundenschuetzen";

    /**
     * SQL Anweisung zur Schemadefinition.
     */
    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    "_id            INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "gid            TEXT, " +
                    "schuetzengid   TEXT NOT NULL," +
                    "rundengid      TEXT NOT NULL," +
                    "gesamtergebnis INTEGER," +
                    "zeitstempel    LONG, " +
                    "transfered INTEGER " +
            ");";
/**
  CREATE TABLE `myarrow`.`rundenschuetzen` (
  `_id` INT NOT NULL AUTO_INCREMENT,
  `gid` VARCHAR(50) NOT NULL,
  `rundengid` VARCHAR(50) NOT NULL,
  `schuetzenid` VARCHAR(50) NOT NULL,
  `gesamtergebnis` INT NULL,
  `zeitstempel` VARCHAR(50) NULL,
  PRIMARY KEY (`_id`, `gid`),
  UNIQUE INDEX `_id_UNIQUE` (`_id` ASC));
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
    public static final String STMT_INSERT = "INSERT INTO "+ TABLE_NAME +
            " (gid, schuetzengid, rundengid, gesamtergebnis, zeitstempel) " +
            " VALUES (?,?,?,?,?)";

    /**
     * SQL Anweisung fuer Erzeugung eines neuen Eintrages.
     */
    public static final String STMT_UPDATE = "UPDATE "+ TABLE_NAME + " SET " +
            " schuetzengid=?, rundengid=?, gesamtergebnis=?, zeitstempel=? WHERE GID=?";


    
    /**
     * SQL-Anweisung zur Loeschung aller Pfeile
     */
    public static final String STMT_RUNDENSCHUETZEN_DELETE = "DELETE " + TABLE_NAME;

    /** Liste aller bekannten Attribute. */
    public static final String[] ALL_COLUMNS = new String[] {
            ID,
            GID,
            SCHUETZENGID,
            RUNDENGID,
            GESAMTERGEBNIS,
            ZEITSTEMPEL
    };

    /**
     * STMT WHERE-Bedingung fuer ID-Anfrage.
     */
    public static final String STMT_WHERE_GID_EQUALS = 
            "select " +
                ID + ", " +
                GID + ", " +
                SCHUETZENGID + ", " +
                RUNDENGID + ", " +
                GESAMTERGEBNIS + ", " +
                ZEITSTEMPEL + " " +
            "from " + TABLE_NAME + " " +
            "where " + GID + "=?";

    /**
     * WHERE-Bedingung fuer GID-Anfrage.
     */
    public static final String WHERE_GID_EQUALS = GID + "=?";

    /**
     * WHERE-Bedingung fuer SCHUETZENGID-Anfrage.
     */
    public static final String WHERE_SCHUETZENGID_EQUALS = SCHUETZENGID + "=?";

    /**
     * WHERE-Bedingung fuer aller Ziele einer Runde.
     */
    public static final String WHERE_RUNDENGID_EQUALS = RUNDENGID + "=?";

    /**
     * WHERE-Bedingung fuer aller Ziele einer Runde.
     */
    public static final String WHERE_RUNDEN_SCHUETZEN_EQUALS =
            RUNDENGID + "=? AND " + SCHUETZENGID +"=?";

    /**
     * Klasse enthaelt nur Konstanten.
     * Daher keine Objekterzeugung vorgesehen.
     */
    private RundenSchuetzenTbl() {
    }
}
