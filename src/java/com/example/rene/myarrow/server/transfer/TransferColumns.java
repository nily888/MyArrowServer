package com.example.rene.myarrow.server.transfer;

/**
 * Created by René Düber on 11.06.16.
 */
public interface TransferColumns {

    /**
     * Globale Schlüssel zur Tabelle.
     * 
     * INTEGer
     */
    String ID = "_id";
    
    /**
     * Globale Schlüssel zur Tabelle.
     * 
     * TEXT
     */
    String GID = "gid";

    /**
     * Device ID
     * 
     * TEXT
     */
    String DEVICEID = "deviceid";
    
    /**
     * Tabellennamen
     * 
     * Text
     */
    String TARGET_TABLE = "target_table";
    
    /**
     * Transfered ja=1 oder nein=0
     * 
     * INTEGER
     */
    String TRANSFERED = "transfered";
    
    /**
     * Zeitpunkt der letzten Änderung.
     *
     * LONG
     */
    String ZEITSTEMPEL = "zeitstempel";

}
