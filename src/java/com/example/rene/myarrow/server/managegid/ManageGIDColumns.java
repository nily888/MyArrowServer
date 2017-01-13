package com.example.rene.myarrow.server.managegid;

import com.example.rene.myarrow.server.clients.*;

/**
 * Created by René Düber on 27.05.16.
 */
public interface ManageGIDColumns {

    /**
     * Primaerschluessel.
     * 
     * TEXT
     */
    String ID = "_id";

    /**
     * Tablename for the mapping
     * 
     * TEXT
     */
    String TABLENAME = "tablename";
    
    /**
     * Server GID
     * 
     * Text
     */
    String SERVERGID = "servergid";
    
    /**
     * MOBIL GID
     *
     * Text
     */
    String MOBILGID = "mobilgid";

    /**
     * Device ID
     * 
     * Text
     */
    String DEVICEID = "deviceid";
    
}
