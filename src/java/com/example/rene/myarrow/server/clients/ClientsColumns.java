package com.example.rene.myarrow.server.clients;

/**
 * Created by René Düber on 27.05.16.
 */
public interface ClientsColumns {

    /**
     * Primaerschluessel.
     * 
     * TEXT
     */
    String ID = "_id";

    /**
     * Device ID
     * 
     * TEXT
     */
    String DEVICEID = "deviceid";
    
    /**
     * Handy Nutzer
     * 
     * Text
     */
    String NAME = "name";
    
    /**
     * Zeitpunkt der letzten Änderung.
     *
     * LONG
     */
    String ZEITSTEMPEL = "zeitstempel";

}
