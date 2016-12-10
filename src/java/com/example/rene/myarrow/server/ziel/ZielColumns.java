package com.example.rene.myarrow.server.ziel;

/**
 * Created by René Düber on 11.06.16.
 */
public interface ZielColumns {

    /**
     * Primaerschluessel.
     */
    String ID = "_id";

    /**
     * Globaler Primaerschluessel.
     * 
     * STRING
     */
    String GID = "gid";
    
    /**
     * Pflichtfeld: ParcourID
     *
     * LONG
     */
    String PARCOURGID = "parcourgid";

    /**
     * Pflichtfeld: Nummer
     *
     * INTEGER
     */
    String NUMMER = "nummer";

    /**
     * Pflichtfeld: Name
     *
     * TEXT
     */
    String NAME = "name";

    /**
     * Pflichtfeld: GPS_LAT_KOORDINATEN
     *
     * TEXT
     */
    String GPS_LAT_KOORDINATEN = "gps_lat_koordinaten";

    /**
     * Pflichtfeld: GPS_LON_KOORDINATEN
     *
     * TEXT
     */
    String GPS_LON_KOORDINATEN = "gps_lon_koordinaten";

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
