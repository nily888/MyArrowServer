package com.example.rene.myarrow.server.bogen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.rene.myarrow.server.MyArrowDB;
import java.sql.ResultSet;

/**
 * Created by nily on 15.12.15.
 */
public class BogenSpeicher {

    /** Markierung für Logging. */
    private static final String TAG = "BogenSpeicher";

    /**
     * Verweis auf die MyArrow-Datenbank.
     */
    private final Connection mDb;

    /**
     * Erzeugt einen neuen ZielSpeicher.<br>
     * Dabei wird sichergestellt, dass die zugrundeliegende
     * Datenbank unmittelbar nutzbar ist.
     */
    public BogenSpeicher() {
        mDb = new MyArrowDB().getInstance();
    }

    /**
     * Legt ein neues Bogen in der Datenbank an.
     *
     */
    public void insertBogen(
            String gid,
            String name,
            String dateiname,
            int standard,
            long zeitstempel) {
        
        PreparedStatement insertData = null;
        
        try {
            mDb.setAutoCommit(false);
            /**
             * Datensatz einfügen
             */
            System.out.println("System: insertZiel(): Datensatz einfügen");
            insertData = mDb.prepareStatement(BogenTbl.STMT_INSERT);
            insertData.setString(1, gid);
            insertData.setString(2, name);
            insertData.setString(3, dateiname);
            insertData.setInt(4, standard);
            insertData.setLong(5, zeitstempel);
            insertData.executeUpdate();
            mDb.commit();
        } catch (SQLException ex) {
            /**
             * Wenn Fehlercode = 1062; Duplicate, einfach ein Update
             */
            if (ex.getErrorCode()==1062){
                try {
                    System.out.println("System: insertZiel(): Datensatz aktualisieren");
                    insertData = mDb.prepareStatement(BogenTbl.STMT_UPDATE);
                    insertData.setString(1, name);
                    insertData.setString(2, dateiname);
                    insertData.setInt(3, standard);
                    insertData.setLong(4, zeitstempel);
                    insertData.setString(5, gid);
                    insertData.executeUpdate();
                    mDb.commit();
                } catch(SQLException excep) {
                    System.out.println("System: insertZiel(): Error Code    = " + excep.getErrorCode());
                    System.out.println("System: insertZiel(): Error Message = " + excep.getMessage());
                    System.err.print(excep);
                }
            } else {
                System.out.println("System: insertZiel(): Error Code    = " + ex.getErrorCode());
                System.out.println("System: insertZiel(): Error Message = " + ex.getMessage());
                System.err.println(ex);
                if (mDb != null) {
                    try {
                        System.err.print("System: insertZiel(): Transaction is being rolled back");
                        mDb.rollback();
                    } catch(SQLException excep) {
                        System.out.println("System: insertZiel(): Error Code    = " + excep.getErrorCode());
                        System.out.println("System: insertZiel(): Error Message = " + excep.getMessage());
                        System.err.print(excep);
                    }
                }
            }
        } finally {
            if (insertData != null) {
                try {
                    System.out.print("System: insertZiel(): Transaction will be closed");
                    insertData.close();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }                
            }
            try {
                System.out.print("System: insertZiel(): AutoCommit() switched on again");
                mDb.setAutoCommit(true);
            } catch(SQLException excep) {
                System.err.print(excep);
            }                
        }
    }

    /**
     * Speichert ein neues Ziel. Ist dieser bereits in der
     * Datenbank bekannt, wird der vorhandene Datensatz
     * geändert.<br>
     * Ansonsten wird ein neuer Datensatz erzeugt.
     *
     * @param ziel zu speichernder Schuetze.
     */
    public void insertBogen(Bogen bogen) {
        insertBogen(
                bogen.getGID(),
                bogen.getName(),
                bogen.getDateiname(),
                bogen.getStandard(),
                bogen.getZeitstempel());
    }

    public Bogen loadBogenDetails(String gid) {
        PreparedStatement queryData;
        ResultSet rs = null;
        queryData = null;
        try {
            queryData = mDb.prepareStatement(BogenTbl.STMT_WHERE_GID_EQUALS);
            queryData.setString(1, gid);
            rs = queryData.executeQuery();
            rs.last();
            if (rs.getRow() == 1) {
                Bogen loadBogenDetails = new Bogen();
                loadBogenDetails.setID(rs.getInt(BogenTbl.ID));
                loadBogenDetails.setGID(rs.getString(BogenTbl.GID));
                loadBogenDetails.setName(rs.getString(BogenTbl.NAME));
                loadBogenDetails.setDateiname(rs.getString(BogenTbl.DATEINAME));
                loadBogenDetails.setStandard(rs.getInt(BogenTbl.STANDARD));
                loadBogenDetails.setZeitstempel(rs.getLong(BogenTbl.ZEITSTEMPEL));
                // loadBogenDetails.setTransfered(rs.getInt(BogenTbl.TRANSFERED));
                return loadBogenDetails;
            } else {
                System.err.println("System: loadBogenDetails(): Treffer nicht eindeutig = " + rs.getRow());
            }
            
        } catch (SQLException ex) {
            System.err.println("System: loadBogenDetails(): " + ex);
            if (mDb != null) {
                try {
                    System.out.print("System: loadBogenDetails(): Transaction is being rolled back");
                    mDb.rollback();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }
            }
            return null;
            
        } finally {
            try {
                System.out.print("System: loadBogenDetails(): Alles wird geschlossen");
                if (rs != null) rs.close();
                if (queryData != null) queryData.close();
            } catch(SQLException excep) {
                System.err.print("System: loadBogenDetails(): " + excep);
            }
        }
        return null;
    }
    
    /**
     * Check for duplicate entries based only on the name
     */
    public String[][] checkForDuplicates() {
        PreparedStatement queryData1;
        PreparedStatement queryData2;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        String[][] checkForDuplicates = null;
        int n=0;
        queryData1 = null;
        queryData2 = null;
        try {
            System.out.println(BogenTbl.STMT_WHERE_GID_NAME_EQUALS);
            queryData1 = mDb.prepareStatement(BogenTbl.STMT_WHERE_GID_NAME_EQUALS);
            rs1 = queryData1.executeQuery();
            
            /**
             * check if something was found, else return null
             */
            if (!rs1.first()) {
                System.err.println("System: checkForDuplicates(): No records found!!");
                return null;
            }
            
            while (!rs1.isAfterLast()) {
                
                /**
                 * Search for the same name
                 */
                queryData2 = mDb.prepareStatement(BogenTbl.STMT_WHERE_GID_NAME_NAME_EQUALS);
                queryData1.setString(1, rs1.getString(BogenTbl.NAME));
                rs2 = queryData2.executeQuery();

                /**
                * check if something was found and store it
                */
                if (rs1.first()) {
                    checkForDuplicates[n][1] = rs1.getString(BogenTbl.GID);
                    checkForDuplicates[n][2] = rs2.getString(BogenTbl.GID);
                    checkForDuplicates[n][3] = rs2.getString(BogenTbl.NAME);
                    n++;
                }
                
                /*
                * close recordset 2 and to the next record
                */
                rs2.close();
                queryData2.close();
                rs1.next();

            }

            /**
            * close recordset 2 and to the next record
            */
            rs1.close();
            queryData1.close();
            return checkForDuplicates;
            
        } catch (SQLException ex) {
            System.err.println("System: checkForDuplicates(): " + ex);
            if (mDb != null) {
                try {
                    System.out.print("System: checkForDuplicates(): Transaction is being rolled back");
                    mDb.rollback();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }
            }
            return null;   
        } finally {
            try {
                System.out.print("System: checkForDuplicates: Alles wird geschlossen");
                if (rs1 != null) rs1.close();
                if (rs2 != null) rs2.close();
                if (queryData1 != null) queryData1.close();
                if (queryData2 != null) queryData2.close();
            } catch(SQLException excep) {
                System.err.print("System: checkForDuplicates(): " + excep);
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
            System.err.println(ex);
        }
    }
}
