package com.example.rene.myarrow.server.rundenziel;

/**
 * Created by nily on 16.06.16.
 */
public interface RundenZielColumns {

    /**
     * Primaerschluessel.
     */
    String ID = "_id";

    /**
     * globaler Primaerschluessel.
     */
    String GID = "gid";

    /**
     * Pflichtfeld: Runden-GID
     *
     * TEXT
     */
    String RUNDENGID = "rundengid";

    /**
     * Pflichtfeld: Ziel-GID
     *
     * TEXT
     */
    String ZIELGID = "zielgid";

    /**
     * Pflichtfeld: RundenSchuetzen-GID
     *
     * TEXT
     */
    String RUNDENSCHUETZENGID = "rundenschuetzengid";

    /**
     * Pflichtfeld: Nummer
     *
     * INTEGER
     */
    String NUMMER = "nummer";

    /**
     * Pflichtfeld: im ersten Schuss
     *
     * BOOLEAN
     */
    String EINS = "eins";

    /**
     * Pflichtfeld: im zweiten Schuss
     *
     * BOOLEAN
     */
    String ZWEI = "zwei";

    /**
     * Pflichtfeld: im dritten Schuss
     *
     * BOOLEAN
     */
    String DREI = "drei";

    /**
     * Pflichtfeld: im Kill
     *
     * BOOLEAN
     */
    String KILL = "kills";

    /**
     * Pflichtfeld: im Kill-Kill
     *
     * BOOLEAN
     */
    String KILLKILL = "killkill";

    /**
     * Pflichtfeld: Punkte
     *
     * INTEGER
     */
    String PUNKTE = "punkte";

    /**
     * Pflichtfeld: Anmerkung
     *
     * TEXT
     */
    String ANMERKUNG = "anmerkung";

    /**
     * Pflichtfeld: GPS Koordinaten
     *
     * DEZIMAL
     */
    String GPSKOORDINATEN = "gpskoordinaten";

    /**
     * Pflichtfeld: Dateiname
     *
     * TEXT
     */
    String DATEINAME = "dateiname";

    /**
     * Zeitpunkt der letzten Änderung.
     *
     * LONG
     */
    String ZEITSTEMPEL = "zeitstempel";

}
