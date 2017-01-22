package com.example.rene.myarrow.server.managegid;

import com.example.rene.myarrow.server.db.MyArrowDB;

import java.sql.*;

/**
 * Created by René Düber on 26.05.16.
 */
public class ManageGIDSpeicher {

    /**
     * Markierung für Logging.
     */
    private static final String TAG = "ManageGIDSpeicher";

    /**
     * Verweis auf die MyArrow-Datenbank.
     */
    private final Connection mDb;

    /**
     * Erzeugt einen neuen ManageGIDnSpeicher.<br>
     * Dabei wird sichergestellt, dass die zugrundeliegende
     * Datenbank unmittelbar nutzbar ist.
     */
    public ManageGIDSpeicher() {
        mDb = new MyArrowDB().getInstance();
    }

    /**
     * Check ob eine ServerGID in der Datenbank existiert.
     *
     * @param tablename
     *      auf welche Tabelle wird sich bezogen?
     * @param servergid
     *      welche ServerGID wird gesucht
     * @return
     *      true = wenn ein Eintrag existiert, false = Eintrag existiert nicht
     */
    public boolean checkServerGID(String tablename, String servergid, String deviceid) {
        if (getMobilGID(tablename, servergid, deviceid) == null) {
            return false;
        } else {
            return true;
        }    
    }
    
    /**
     * Hole eine Liste von MobilGID aus der Datenbank mit dem tablename und servergid.
     *
     * @param tablename
     *      auf welche Tabelle wird sich bezogen?
     * @param servergid
     *      mit welcher MobilGID wird gesucht
     * @return
     *      Liste von MobilGID = wenn ein Eintrag existiert, NULL = Eintrag existiert nicht
     */
    public String getMobilGID(String tablename, String servergid, String deviceid) {
        
        PreparedStatement queryData = null;
        
        try {
            System.out.println("System: getMobilGID() = " + ManageGIDTbl.STMT_WHERE_TABLENAME_SERVERGID_DEVICEID_EQUALS + " => " + tablename + "/" + servergid);
            queryData = mDb.prepareStatement(ManageGIDTbl.STMT_WHERE_TABLENAME_SERVERGID_DEVICEID_EQUALS);
            System.out.println("System: getMobilGID() = " + queryData.toString());
            queryData.setString(1, tablename);
            queryData.setString(2, servergid);
            queryData.setString(3, deviceid);
            System.out.println("System: getMobilGID() = " + queryData.toString());
            ResultSet rs = queryData.executeQuery();
            if (rs.first()) { 
                return rs.getString(2);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.err.println(ex);
            if (mDb != null) {
                try {
                    System.out.print("System: getMobilrGID(): Transaction is being rolled back");
                    mDb.rollback();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }
            }
            return null;
        } finally {
            if (queryData != null) {
                try {
                    System.out.print("System: getMobilGID(): Transaction will be closed");
                    queryData.close();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }                
            }
        }
    }
 
    /**
     * Check ob eine MobilGID in der Datenbank existiert.
     *
     * @param tablename
     *      auf welche Tabelle wird sich bezogen?
     * @param mobilgid
     *      welche MobilGID wird gesucht
     * @return
     *      true = wenn ein Eintrag existiert, false = Eintrag existiert nicht
     */
    public boolean checkMobilGID(String tablename, String mobilgid, String deviceid) {
        if (getServerGID(tablename, mobilgid, deviceid) == null) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * Hole eine ServerGID aus der Datenbank mit dem tablename und mobilgid.
     *
     * @param tablename
     *      auf welche Tabelle wird sich bezogen?
     * @param mobilgid
     *      mit welcher MobilGID wird gesucht
     * @param deviceid
     *      fuer welches Handy
     * 
     * @return
     *      ServerGID = wenn ein Eintrag existiert, NULL = Eintrag existiert nicht
     */
    public String getServerGID(String tablename, String mobilgid, String deviceid) {
        PreparedStatement queryData = null;
        
        try {
            System.out.println("System: getMobilGID() = " + ManageGIDTbl.STMT_WHERE_TABLENAME_MOBILGID_DEVICEID_EQUALS + " => " + tablename + "/" + mobilgid);
            queryData = mDb.prepareStatement(ManageGIDTbl.STMT_WHERE_TABLENAME_MOBILGID_DEVICEID_EQUALS);
            System.out.println("System: getMobilGID() = " + queryData.toString());
            queryData.setString(1, tablename);
            queryData.setString(2, mobilgid);
            queryData.setString(3, deviceid);
            System.out.println("System: getMobilGID() = " + queryData.toString());
            ResultSet rs = queryData.executeQuery();
            if (rs.first()) {
                return rs.getString(2);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            /**
             * Wenn Datensatz nicht gefunden wurde einfuegen
             * Werden Daten eingefuegt, sind Server- und Mobile GID gleicht
             */
            // TODO Code fuer No Found heraussuchen
            if (ex.getErrorCode()==1062){
                insertNewGIDset(tablename, mobilgid, mobilgid, deviceid);
            } else {
                System.err.println(ex);
                if (mDb != null) {
                    try {
                        System.out.print("System: getMobilGID(): Transaction is being rolled back");
                        mDb.rollback();
                    } catch(SQLException excep) {
                        System.err.print(excep);
                    }
                }
            }
            return null;
        } finally {
            if (queryData != null) {
                try {
                    System.out.print("System: getMobilGID(): Transaction will be closed");
                    queryData.close();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }                
            }
        }
    }
 
    /**
     * Legt einen neuen Mapping Datensatz (tablename, servergid, mobilgid) in der Datenbank an.
     *
     * @param tablename
     *          Name der Tabelle, fuer die der Mapping-Satz angelegt werden soll
     * @param servergid
     *          GID auf dem Server
     * @param mobilgid
     *          GID auf dem entsprechenden Handy
     */
    public boolean insertNewGIDset(String tablename, String servergid, String mobilgid, String deviceid) {
        System.out.println("System: insertNewGIDset(): entered.....");
        
        PreparedStatement insertData = null;
        
        try {
            mDb.setAutoCommit(false);
            insertData = mDb.prepareStatement(ManageGIDTbl.STMT_INSERT);
            insertData.setString(1, tablename);
            insertData.setString(2, servergid);
            insertData.setString(3, mobilgid);
            insertData.setString(4, deviceid);
            System.out.println("System: insertNewGIDset(): insertData: " +insertData.toString());
            insertData.executeUpdate();
            mDb.commit();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
            if (mDb != null) {
                try {
                    System.out.print("System: insertNewGIDset(): insertData: Transaction is being rolled back");
                    mDb.rollback();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }
            }
            return false;
        } finally {
            if (insertData != null) {
                try {
                    System.out.print("System: insertNewGIDset(): insertData: Transaction will be closed");
                    insertData.close();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }                
            }
            try {
                System.out.print("System: insertNewGIDset(): AutoCommit() switched on again");
                mDb.setAutoCommit(true);
            } catch(SQLException excep) {
                System.err.print(excep);
            }                
        }
    }

    /**
     * Legt einen neuen Mapping Datensatz (tablename, servergid, mobilgid) in der Datenbank an.
     *
     * @param managegid
     *      Dataset of a managegid
     * 
     */
    public void insertNewGIDset(ManageGID managegid) {
        insertNewGIDset( managegid.getTablename(), managegid.getServerGID(), managegid.getMobilGID(), managegid.getDeviceID());
    }
    
    /**
     * Deletion of entries according the tablename and the mobilgid
     * 
     * @param tablename
     *      name of the table, from where you want to delete the mobilgid
     * 
     * @param mobilgid
     *      the mobilgid
     * 
     * @return 
     *      number of deleted records
     * 
     */
    public int deleteMobilGID(String tablename, String mobilgid) {
        System.out.println("System: deleteMobilGID(): entered.....");
        
        PreparedStatement deleteData = null;
        
        try {
            mDb.setAutoCommit(false);
            deleteData = mDb.prepareStatement(ManageGIDTbl.STMT_DELETE_MOBILGID);
            deleteData.setString(1, tablename);
            deleteData.setString(2, mobilgid);
            System.out.println("System: deleteMobilGID(): deleteData: " + deleteData.toString());
            deleteData.executeUpdate();
            mDb.commit();
            return 0;
        } catch (SQLException ex) {
            System.out.println(ex);
            if (mDb != null) {
                try {
                    System.out.print("System: deleteMobilGID(): deleteData: Transaction is being rolled back");
                    mDb.rollback();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }
            }
            return 1;
        } finally {
            if (deleteData != null) {
                try {
                    System.out.print("System: deleteMobilGID(): deleteData: Transaction will be closed");
                    deleteData.close();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }                
            }
            try {
                System.out.print("System: deleteMobilGID(): AutoCommit() switched on again");
                mDb.setAutoCommit(true);
            } catch(SQLException excep) {
                System.err.print(excep);
            }                
        }
    }
    
    /**
     * Deletion of entries according the tablename and the servergid
     * 
     * @param tablename
     *      name of the table, from where you want to delete the mobilgid
     * 
     * @param servergid
     *      the mobilgid
     * 
     * @return 
     *      number of deleted records
     * 
     */
    public int deleteServerGID(String tablename, String servergid) {
        System.out.println("System: deleteServerlGID(): entered.....");
        
        PreparedStatement deleteData = null;
        
        try {
            mDb.setAutoCommit(false);
            deleteData = mDb.prepareStatement(ManageGIDTbl.STMT_DELETE_MOBILGID);
            deleteData.setString(1, tablename);
            deleteData.setString(2, servergid);
            System.out.println("System: deleteServerlGID(): deleteData: " + deleteData.toString());
            deleteData.executeUpdate();
            mDb.commit();
            return 0;
        } catch (SQLException ex) {
            System.out.println(ex);
            if (mDb != null) {
                try {
                    System.out.print("System: deleteServerlGID(): deleteData: Transaction is being rolled back");
                    mDb.rollback();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }
            }
            return 1;
        } finally {
            if (deleteData != null) {
                try {
                    System.out.print("System: deleteServerlGID(): deleteData: Transaction will be closed");
                    deleteData.close();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }                
            }
            try {
                System.out.print("System: deleteServerlGID(): AutoCommit() switched on again");
                mDb.setAutoCommit(true);
            } catch(SQLException excep) {
                System.err.print(excep);
            }                
        }
    }

    /**
     * Update of the mobilgid (new) for exactly one entry according the tablename, servergid and mobilgid (old)
     * 
     * @param tablename
     *      name of the table, from where you want to update the mobilgid
     * 
     * @param servergid
     *      the mobilgid
     * 
     * @param oldmobilgid
     *      the old number for the mobilgid
     * 
     * @param newmobilgid
     *      the new number for the mobilgid
     * 
     * @return 
     *      number of updated records (should be always 1)
     * 
     */    
    public int updateMobilGID(String tablename, String servergid, String oldmobilgid, String newmobilgid) {
        System.out.println("System: updateMobilGID(): entered.....");
        
        PreparedStatement updateData = null;
        
        try {
            mDb.setAutoCommit(false);
            updateData = mDb.prepareStatement(ManageGIDTbl.STMT_DELETE_MOBILGID);
            updateData.setString(1, newmobilgid);
            updateData.setString(2, tablename);
            updateData.setString(3, servergid);
            updateData.setString(4, oldmobilgid);
            System.out.println("System: updateMobilGID(): deleteData: " + updateData.toString());
            updateData.executeUpdate();
            mDb.commit();
            return 0;
        } catch (SQLException ex) {
            System.out.println(ex);
            if (mDb != null) {
                try {
                    System.out.print("System: deleteServerlGID(): updateMobilGID: Transaction is being rolled back");
                    mDb.rollback();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }
            }
            return 1;
        } finally {
            if (updateData != null) {
                try {
                    System.out.print("System: updateMobilGID(): updateData: Transaction will be closed");
                    updateData.close();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }                
            }
            try {
                System.out.print("System: updateMobilGID(): AutoCommit() switched on again");
                mDb.setAutoCommit(true);
            } catch(SQLException excep) {
                System.err.print(excep);
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
}
