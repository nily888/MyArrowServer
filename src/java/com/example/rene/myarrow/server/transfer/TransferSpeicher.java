package com.example.rene.myarrow.server.transfer;

import com.example.rene.myarrow.server.clients.ClientsSpeicher;
import com.example.rene.myarrow.server.MyArrowDB;

import java.sql.*;

/**
 * Created by René Düber on 26.05.16.
 */
public class TransferSpeicher {

    /**
     * Markierung für Logging.
     */
    private static final String TAG = "TransferSpeicher";

    /**
     * Verweis auf die MyArrow-Datenbank.
     */
    private final Connection mDb;

    /**
     * Erzeugt einen neuen SchuetzenSpeicher.<br>
     * Dabei wird sichergestellt, dass die zugrundeliegende
     * Datenbank unmittelbar nutzbar ist.
     */
    public TransferSpeicher() {
        mDb = new MyArrowDB().getInstance();
    }

    /**
     * Legt einen neuen Client in der Datenbank an.
     *
     * @param gid
     *          Globale ID des Datensatzes.
     * @param deviceid
     *          DeviceID
     * @param table
     *          Tabellennamen, des zu synchronisierenden Datensatz
     */
    public void insertTransfer(String gid, String deviceid, String table) {
        
        System.out.println("System: insertTransfer(): entered.....");
        
        PreparedStatement insertData = null;
        try {
            mDb.setAutoCommit(false);
            insertData = mDb.prepareStatement(TransferTbl.STMT_INSERT);
            insertData.setString(1, gid);
            insertData.setString(2, deviceid);
            insertData.setString(3, table);
            insertData.setInt(4, 0);
            insertData.setLong(5, System.currentTimeMillis());
            insertData.executeUpdate();
            mDb.commit();
        } catch (SQLException ex) {
            if (ex.getErrorCode()==1062){
                System.out.println("System: insertTransfer(): Es liegt schon ein Sync-Request vor");
            } else {
                System.out.println("System: insertTransfer(): insertData: " + insertData.toString());
                System.err.println("System: insertTransfer(): " + ex);
            }
            if (mDb != null) {
                try {
                    System.out.print("System: insertTransfer(): Transaction is being rolled back");
                    mDb.rollback();
                } catch(SQLException excep) {
                    System.err.print("System: insertTransfer(): " + excep);
                }
            }
        } finally {
            if (insertData != null) {
                try {
                    System.out.print("System: insertTransfer(): Transaction will be closed");
                    insertData.close();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }                
            }
            try {
                System.out.print("System: insertTransfer(): AutoCommit() switched on again");
                mDb.setAutoCommit(true);
            } catch(SQLException excep) {
                System.err.print("System: insertTransfer(): " + excep);
            }                
        }
    }

    /**
     * Speichert einen neuen Client. Ist dieser bereits in der
     * Datenbank bekannt, wird der vorhandene Datensatz
     * geändert.<br>
     * Ansonsten wird ein neuer Datensatz erzeugt.
     *
     * @param schuetzen zu speichernder Schuetze.
     * @return id der persistente Schuetze.
     */
    public void insertTransfer(Transfer transfer) {
        insertTransfer( transfer.getGID(), transfer.getDeviceID(), transfer.getTargetTable());
    }

    public void updateTransferListe(String gid, String deviceid, String table){
        /**
         * Update der zu synchrisierenden Datensätze und Clients
         */
        ClientsSpeicher cs= new ClientsSpeicher();
        TransferSpeicher ts = new TransferSpeicher();
        ResultSet rs = cs.getClientListe(deviceid);
        try {
            rs.last();
            System.out.print("System: updateTransferListe(): zu aktualisierende Clients = " + rs.getRow());
            rs.beforeFirst();
            while (rs.next()){
                ts.insertTransfer(gid, rs.getString(1), table);
            }
        } catch (SQLException sqlex) {
            System.err.println(sqlex);
        } finally {
            try {
                System.out.print("System: updateTransferListe(): Transaction will be closed");
                rs.close();
                ts.schliessen();
                cs.schliessen();
            } catch (SQLException sqlex) {
                System.err.println(sqlex);
            }
        }
    }
    
    public void updateTransferDone(int id) {
        PreparedStatement data = null;
        try {
            mDb.setAutoCommit(false);
            data = mDb.prepareStatement(TransferTbl.STMT_UPDATE_DONE);
            data.setInt(1, id);
            data.executeUpdate();
            mDb.commit();
        } catch (SQLException ex) {
            System.out.println("System: updateTransferDone(): insertData: " + data.toString());
            System.err.println("System: updateTransferDone(): " + ex);
            if (mDb != null) {
                try {
                    System.out.print("System: updateTransferDone(): Transaction is being rolled back");
                    mDb.rollback();
                } catch(SQLException excep) {
                    System.err.print("System: updateTransferDone(): " + excep);
                }
            }
        } finally {
            if (data != null) {
                try {
                    System.out.print("System: updateTransferDone(): Transaction will be closed");
                    data.close();
                } catch(SQLException excep) {
                    System.err.println("System: updateTransferDone(): " + excep);
                }                
            }
            try {
                System.out.println("System: updateTransferDone(): AutoCommit() switched on again");
                mDb.setAutoCommit(true);
            } catch(SQLException excep) {
                System.err.println("System: updateTransferDone(): " + excep);
            }                
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
    
    public ResultSet getTransferListe(String deviceid) {
        PreparedStatement queryData;
        queryData = null;
        try {
            queryData = mDb.prepareStatement(TransferTbl.STMT_TRANSFERLISTE);
            queryData.setString(1, deviceid);
            ResultSet rs = queryData.executeQuery();
            return rs;
        } catch (SQLException ex) {
            System.err.println("System: getTransferListe(): " + ex);
            if (mDb != null) {
                try {
                    System.out.print("System: getTransferListe(): Transaction is being rolled back");
                    mDb.rollback();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }
            }
            return null;
        }
    }
}
