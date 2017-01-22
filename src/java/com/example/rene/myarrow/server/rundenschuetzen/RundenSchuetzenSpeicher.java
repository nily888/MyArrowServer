package com.example.rene.myarrow.server.rundenschuetzen;

import com.example.rene.myarrow.server.db.MyArrowDB;
import java.sql.*;

/**
 * Created by nily on 15.12.15.
 */
public class RundenSchuetzenSpeicher {

    /** Markierung für Logging. */
    private static final String TAG = "RundenSchuetzenSpeicher";

    
    /**
     * Verweis auf die Mobilfunknummern-Datenbank.
     */
    private final Connection mDb;

    /**
     * Erzeugt einen neuen SchuetzenSpeicher.<br>
     * Dabei wird sichergestellt, dass die zugrundeliegende
     * Datenbank unmittelbar nutzbar ist.
     */
    public RundenSchuetzenSpeicher() {
        mDb = new MyArrowDB().getInstance();
    }

    /**
     * Legt einen neuen Schuetzen in der Datenbank an.
     *
     */
    public void insertRundenSchuetzen(
            String gid,
            String rundengid,
            String schuetzengid,
            int gesamtergebnis,
            long zeitstempel) {
        
        PreparedStatement insertData = null;
        
        try {
            mDb.setAutoCommit(false);
            /**
             * Datensatz einfügen
             */
            System.out.println("System: insertRundenSchuetzen(): Datensatz einfügen");
            insertData = mDb.prepareStatement(RundenSchuetzenTbl.STMT_INSERT);
            insertData.setString(1, gid);
            insertData.setString(2, schuetzengid);
            insertData.setString(3, rundengid);
            insertData.setInt(4, gesamtergebnis);
            insertData.setLong(5, zeitstempel);
            insertData.executeUpdate();
            mDb.commit();
        } catch (SQLException ex) {
            /**
             * Wenn Fehlercode = 1062; Duplicate, einfach ein Update
             */
            if (ex.getErrorCode()==1062){
                try {
                    System.out.println("System: insertSchuetzen(): Datensatz aktualisieren");
                    insertData = mDb.prepareStatement(RundenSchuetzenTbl.STMT_UPDATE);
                    insertData.setString(1, schuetzengid);
                    insertData.setString(2, rundengid);
                    insertData.setInt(3, gesamtergebnis);
                    insertData.setLong(4, zeitstempel);
                    insertData.setString(5, gid);
                    insertData.executeUpdate();
                    mDb.commit();
                } catch(SQLException excep) {
                    System.out.println("System: insertRundenSchuetzen(): Error Code    = " + excep.getErrorCode());
                    System.out.println("System: insertRundenSchuetzen(): Error Message = " + excep.getMessage());
                    System.err.print(excep);
                }
            } else {
                System.out.println("System: insertRundenSchuetzen(): Error Code    = " + ex.getErrorCode());
                System.out.println("System: insertRundenSchuetzen(): Error Message = " + ex.getMessage());
                System.out.println(ex);
                if (mDb != null) {
                    try {
                        System.err.print("System: insertRundenSchuetzen(): Transaction is being rolled back");
                        mDb.rollback();
                    } catch(SQLException excep) {
                        System.out.println("System: insertRundenSchuetzen(): Error Code    = " + excep.getErrorCode());
                        System.out.println("System: insertRundenSchuetzen(): Error Message = " + excep.getMessage());
                        System.err.print(excep);
                    }
                }
            }
        } finally {
            if (insertData != null) {
                try {
                    System.out.print("System: insertRundenSchuetzen(): Transaction will be closed");
                    insertData.close();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }                
            }
            try {
                System.out.print("System: insertRundenSchuetzen(): AutoCommit() switched on again");
                mDb.setAutoCommit(true);
            } catch(SQLException excep) {
                System.err.print(excep);
            }                
        }
    }

    /**
     * Speichert einen neuen Schuetzen. Ist dieser bereits in der
     * Datenbank bekannt, wird der vorhandene Datensatz
     * geändert.<br>
     * Ansonsten wird ein neuer Datensatz erzeugt.
     *
     * @param schuetzen zu speichernder Schuetze.
     */
    public void insertRundenSchuetzen(RundenSchuetzen rundenschuetzen) {
        insertRundenSchuetzen(
                rundenschuetzen.getGID(),
                rundenschuetzen.getRundenGID(),
                rundenschuetzen.getSchuetzenGID(),
                rundenschuetzen.getGesamtErgebnis(),
                rundenschuetzen.getZeitstempel());
    }

    public RundenSchuetzen loadRundenSchuetzenDetails(String gid) {
        PreparedStatement queryData;
        ResultSet rs = null;
        queryData = null;
        try {
            queryData = mDb.prepareStatement(RundenSchuetzenTbl.STMT_WHERE_GID_EQUALS);
            queryData.setString(1, gid);
            rs = queryData.executeQuery();
            rs.last();
            if (rs.getRow() == 1) {
                RundenSchuetzen loadRundenSchuetzenDetails = new RundenSchuetzen();
                loadRundenSchuetzenDetails.setID(rs.getInt(RundenSchuetzenTbl.ID));
                loadRundenSchuetzenDetails.setGID(rs.getString(RundenSchuetzenTbl.GID));
                loadRundenSchuetzenDetails.setSchuetzenGID(rs.getString(RundenSchuetzenTbl.SCHUETZENGID));
                loadRundenSchuetzenDetails.setRundenGID(rs.getString(RundenSchuetzenTbl.RUNDENGID));
                loadRundenSchuetzenDetails.setGesamtErgebnis(rs.getInt(RundenSchuetzenTbl.GESAMTERGEBNIS));
                loadRundenSchuetzenDetails.setZeitstempel(rs.getLong(RundenSchuetzenTbl.ZEITSTEMPEL));
                // loadRundenSchuetzenDetails.setTransfered(rs.getInt(RundenSchuetzenTbl.TRANSFERED));
                return loadRundenSchuetzenDetails;
            } else {
                System.err.println("System: loadRundenSchuetzenDetails(): Treffer nicht eindeutig = " + rs.getRow());
            }
            
        } catch (SQLException ex) {
            System.err.println("System: loadRundenSchuetzenDetails(): " + ex);
            if (mDb != null) {
                try {
                    System.out.print("System: loadRundenSchuetzenDetails(): Transaction is being rolled back");
                    mDb.rollback();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }
            }
            return null;
            
        } finally {
            try {
                System.out.print("System: loadRundenSchuetzenDetails(): Alles wird geschlossen");
                if (rs != null) rs.close();
                if (queryData != null) queryData.close();
            } catch(SQLException excep) {
                System.err.print("System: loadRundenSchuetzenDetails(): " + excep);
            }
        }
        return null;
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
