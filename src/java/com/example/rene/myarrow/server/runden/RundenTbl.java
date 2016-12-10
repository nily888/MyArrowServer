package com.example.rene.myarrow.server.runden;

/**
 * Created by nily on 15.12.15.
 */
public class RundenTbl implements RundenColumns {

    /**
     * Name der Datenbanktabelle.
     */
    public static final String TABLE_NAME = "runden";

    /**
     * SQL Anweisung zur Schemadefinition.
     */
    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "gid TEXT, " +
                    "parcourgid TEXT NOT NULL," +
                    "bogengid TEXT NOT NULL," +
                    "pfeilgid TEXT NOT NULL," +
                    "startzeit LONG," +
                    "s_startzeit TEXT," +
                    "endzeit LONG," +
                    "wetter TEXT, " +
                    "transfered INTEGER " +
                    ");";

/**
CREATE TABLE `myarrow`.`runden` (
  `_id` INT NOT NULL AUTO_INCREMENT,
  `gid` VARCHAR(50) NOT NULL,
  `parcourgid` VARCHAR(50) NOT NULL,
  `bogengid` VARCHAR(50) NOT NULL,
  `pfeilgid` VARCHAR(50) NOT NULL,
  `startzeit` BIGINT NULL,
  `s_startzeit` VARCHAR(45) NULL,
  `endzeit` BIGINT NULL,
  `wetter` VARCHAR(45) NULL,
  PRIMARY KEY (`_id`, `gid`),
  UNIQUE INDEX `_id_UNIQUE` (`_id` ASC),
  UNIQUE INDEX `gid_UNIQUE` (`gid` ASC));
  */
    /**
     * Standard-Sortierreihenfolge fuer die Tabelle.
     *
     * Sortiert wird nach Zeitstempel absteigend.
     */
    public static final String DEFAULT_SORT_ORDER = STARTZEIT + " DESC";

    /**
     * SQL Anweisung zur Loeschung der Tabelle.
     */
    public static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

    /**
     * SQL Anweisung fuer Erzeugung eines
     * neuen Eintrages.
     */
    public static final String STMT_INSERT = "INSERT INTO "+ TABLE_NAME +
            " (gid, parcourgid, bogengid, pfeilgid, startzeit, s_startzeit, endzeit, wetter)" +
            " VALUES (?,?,?,?,?,?,?,?)";

    /**
     * SQL Anweisung fuer Erzeugung eines
     * neuen Eintrages.
     */
    public static final String STMT_UPDATE = "UPDATE "+ TABLE_NAME + " SET " +
            " parcourgid=?, bogengid=?, pfeilgid=?, startzeit=?, s_startzeit=?, endzeit=?, wetter=?" +
            " WHERE gid=?";

    /**
     * SQL-Anweisung zur Loeschung aller Runden
     */
    public static final String STMT_Pfeil_DELETE = "DELETE " + TABLE_NAME;

    /** Liste aller bekannten Attribute. */
    public static final String[] ALL_COLUMNS = new String[] {
            ID,
            GID,
            PARCOURGID,
            BOGENGID,
            PFEILGID,
            STARTZEIT,
            S_STARTZEIT,
            ENDZEIT,
            WETTER
    };

    /**
     * WHERE-Bedingung fuer ID-Anfrage.
     */
    public static final String STMT_WHERE_GID_EQUALS = 
            "select " +
                ID + ", " +
                GID + ", " +
                PARCOURGID + ", " +
                BOGENGID + ", " +
                PFEILGID + ", " +
                STARTZEIT + ", " +
                S_STARTZEIT + ", " +
                ENDZEIT + ", " +
                WETTER + " " +
            "from " + TABLE_NAME + " " +
            "where " + GID + "=?";
    
    /**
     * STMT WHERE-Bedingung fuer GID-Anfrage.
     */
    public static final String WHERE_GID_EQUALS = GID + "=?";

    /**
     * WHERE-Bedingung fuer ParcourGID-Anfrage.
     */
    public static final String WHERE_PARCOURGID_EQUALS = PARCOURGID + "=?";

    /**
     * Klasse enthaelt nur Konstanten.
     * Daher keine Objekterzeugung vorgesehen.
     */
    private RundenTbl() {
    }

}
