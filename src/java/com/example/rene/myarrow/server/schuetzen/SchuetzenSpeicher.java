package com.example.rene.myarrow.server.schuetzen;

import com.example.rene.myarrow.server.MyArrowDB;

import java.sql.*;

/**
 * Created by René Düber on 26.05.16.
 */
public class SchuetzenSpeicher {

    /**
     * Markierung für Logging.
     */
    private static final String TAG = "SchuetzenSpeicher";

    /**
     * Verweis auf die Mobilfunknummern-Datenbank.
     */
    private final MyArrowDB mDb;

    /**
     * Erzeugt einen neuen SchuetzenSpeicher.<br>
     * Dabei wird sichergestellt, dass die zugrundeliegende
     * Datenbank unmittelbar nutzbar ist.
     */
    public SchuetzenSpeicher() {
        mDb = new MyArrowDB();
    }

    /**
     * Legt einen neuen Schuetzen in der Datenbank an.
     *
     * @param name
     *          Name des Schuetzen.
     * @param gid
     *          Globale ID
     * @param dateiname
     *          Dateiname für das Bild
     * @param zeitstempel
     *      Zeitpunkt des Kontakts.
     */
    public Boolean insertSchuetzen(
            String name,
            String gid,
            String dateiname,
            long zeitstempel) {
        
        Boolean insertSchuetzen = false;
        PreparedStatement insertData = null;
        PreparedStatement updateData = null;
        try {
            
            insertData = mDb.getInstance().prepareStatement(SchuetzenTbl.STMT_INSERT);
            insertData.setString(1, gid);
            insertData.setString(2, name);
            insertData.setString(3, dateiname);
            insertData.setLong(4, zeitstempel);

            updateData = mDb.getInstance().prepareStatement(SchuetzenTbl.STMT_UPDATE);
            updateData.setString(1, name);
            updateData.setString(2, dateiname);
            updateData.setLong(3, zeitstempel);
            updateData.setString(4, gid);
        
            insertSchuetzen = mDb.insertDataset(insertData, updateData);
            
        } catch(SQLException excep) {
            System.out.println("System: insertSchuetzen(): " + insertData.toString());
            System.out.println("System: insertSchuetzen(): " + updateData.toString());
            System.err.println("System: insertSchuetzen(): Error Code    = " + excep.getErrorCode());
            System.err.println("System: insertSchuetzen(): Error Message = " + excep.getMessage());
            System.err.println("System: insertSchuetzen(): " + excep);
        
        } finally {

            if (insertData != null) {
                try {
                    System.out.print("System: insertSchuetzen(): Transaction will be closed");
                    insertData.close();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }                
            }
            if (updateData != null) {
                try {
                    System.out.print("System: insertSchuetzen(): Transaction will be closed");
                    updateData.close();
                } catch(SQLException excep) {
                    System.err.println("System: insertSchuetzen(): " + excep);
                }                
            }

        }
        
        return insertSchuetzen;
    }

    /**
     * Speichert einen neuen Schuetzen. Ist dieser bereits in der
     * Datenbank bekannt, wird der vorhandene Datensatz
     * geändert.<br>
     * Ansonsten wird ein neuer Datensatz erzeugt.
     *
     * @param schuetzen zu speichernder Schuetze.
     */
    public Boolean insertSchuetzen(Schuetzen schuetzen) {
        return insertSchuetzen(
                schuetzen.getName(),
                schuetzen.getGID(),
                schuetzen.getDateiname(),
                schuetzen.getZeitstempel());
    }

    public Schuetzen loadSchuetzenDetails(String gid) {
        PreparedStatement queryData;
        ResultSet rs = null;
        queryData = null;
        try {
            queryData = mDb.getInstance().prepareStatement(SchuetzenTbl.STMT_WHERE_GID_EQUALS);
            queryData.setString(1, gid);
            rs = queryData.executeQuery();
            rs.last();
            if (rs.getRow() == 1) {
                Schuetzen loadSchuetzenDetails = new Schuetzen();
                loadSchuetzenDetails.setID(rs.getInt(SchuetzenTbl.ID));
                loadSchuetzenDetails.setGID(rs.getString(SchuetzenTbl.GID));
                loadSchuetzenDetails.setName(rs.getString(SchuetzenTbl.NAME));
                loadSchuetzenDetails.setDateiname(rs.getString(SchuetzenTbl.DATEINAME));
                loadSchuetzenDetails.setZeitstempel(rs.getLong(SchuetzenTbl.ZEITSTEMPEL));
                // loadSchuetzenDetails.setTransfered(rs.getInt(SchuetzenTbl.TRANSFERED));
                return loadSchuetzenDetails;
            } else {
                System.err.println("System: loadSchuetzenDetails(): Treffer nicht eindeutig = " + rs.getRow());
            }
            
        } catch (SQLException ex) {
            System.err.println("System: loadSchuetzenDetails(): " + ex);
            if (mDb != null) {
                try {
                    System.out.print("System: loadSchuetzenDetails(): Transaction is being rolled back");
                    mDb.getInstance().rollback();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }
            }
            return null;
            
        } finally {
            try {
                System.out.print("System: loadSchuetzenDetails(): Alles wird geschlossen");
                if (rs != null) rs.close();
                if (queryData != null) queryData.close();
            } catch(SQLException excep) {
                System.err.print("System: loadSchuetzenDetails(): " + excep);
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
        mDb.close();
    }

}
