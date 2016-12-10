package com.example.rene.myarrow.server.clients;

import com.example.rene.myarrow.server.MyArrowDB;

import java.sql.*;

/**
 * Created by René Düber on 26.05.16.
 */
public class ClientsSpeicher {

    /**
     * Markierung für Logging.
     */
    private static final String TAG = "ClientsSpeicher";

    /**
     * Verweis auf die MyArrow-Datenbank.
     */
    private final Connection mDb;

    /**
     * Erzeugt einen neuen SchuetzenSpeicher.<br>
     * Dabei wird sichergestellt, dass die zugrundeliegende
     * Datenbank unmittelbar nutzbar ist.
     */
    public ClientsSpeicher() {
        mDb = new MyArrowDB().getInstance();
    }

    /**
     * Legt einen neuen Client in der Datenbank an.
     *
     * @param deviceID
     *      Device ID, welche kontrolliert werden muss
     * @return
     *      true = Client existiert schon, false = Client existiert nicht
     */
    public boolean newClients(String deviceID) {
        
        PreparedStatement queryData = null;
        
        try {
            System.out.println("System: newClient() = " + ClientsTbl.STMT_WHERE_DEVICEID_EQUALS + " => " + deviceID);
            queryData = mDb.prepareStatement(ClientsTbl.STMT_WHERE_DEVICEID_EQUALS);
            System.out.println("System: newClient() = " + queryData.toString());
            queryData.setString(1, deviceID);
            System.out.println("System: newClient() = " + queryData.toString());
            ResultSet rs = queryData.executeQuery();
            return rs.first();
        } catch (SQLException ex) {
            System.err.println(ex);
            if (mDb != null) {
                try {
                    System.out.print("System: newClients(): Transaction is being rolled back");
                    mDb.rollback();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }
            }
            return false;
        } finally {
            if (queryData != null) {
                try {
                    System.out.print("System: newClients(): Transaction will be closed");
                    queryData.close();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }                
            }
        }
    }
 
    /**
     * Legt einen neuen Client in der Datenbank an.
     *
     * @param deviceid
     *          DeviceID wie z.B. IMEI
     * @param name
     *          Name des Devices
     */
    public void insertClients(String deviceid, String name) {
        System.out.println("System: insertClients(): entered.....");
        
        PreparedStatement insertData = null;
        
        try {
            mDb.setAutoCommit(false);
            insertData = mDb.prepareStatement(ClientsTbl.STMT_INSERT);
            insertData.setString(1, deviceid);
            insertData.setString(2, name);
            insertData.setLong(3, System.currentTimeMillis());
            System.out.println("System: insertClients(): insertData: " +insertData.toString());
            insertData.executeUpdate();
            mDb.commit();
        } catch (SQLException ex) {
            System.out.println(ex);
            if (mDb != null) {
                try {
                    System.out.print("System: insertClients(): insertData: Transaction is being rolled back");
                    mDb.rollback();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }
            }
        } finally {
            if (insertData != null) {
                try {
                    System.out.print("System: insertClients(): insertData: Transaction will be closed");
                    insertData.close();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }                
            }
            try {
                System.out.print("System: insertClients(): AutoCommit() switched on again");
                mDb.setAutoCommit(true);
            } catch(SQLException excep) {
                System.err.print(excep);
            }                
        }
    }

    /**
     * Speichert einen neuen Client. Ist dieser bereits in der
     * Datenbank bekannt, wird der vorhandene Datensatz
     * geändert.<br>
     * Ansonsten wird ein neuer Datensatz erzeugt.
     * 
     * @param client
     * 
     */
    public void insertClients(Clients client) {
        insertClients( client.getDeviceID(), client.getName());
    }
    
    public ResultSet getClientListe(String deviceid){
        
        PreparedStatement queryData;
        queryData = null;
        try {
            queryData = mDb.prepareStatement(ClientsTbl.STMT_WHERE_DEVICEID_NOT_EQUALS);
            queryData.setString(1, deviceid);
            ResultSet rs = queryData.executeQuery();
            return rs;
        } catch (SQLException ex) {
            System.err.println(ex);
            if (mDb != null) {
                try {
                    System.out.print("System: insertClientListe(): Transaction is being rolled back");
                    mDb.rollback();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }
            }
            return null;
        }
    }

    /**
     * Schliesst die zugrundeliegende Datenbank.
     * <br>
     * Vor dem naechsten Zugriff muss oeffnen() aufgerufen
     * werden.
     */
    public void schliessen() {
        try {
            mDb.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}
