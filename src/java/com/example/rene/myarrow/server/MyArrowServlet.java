package com.example.rene.myarrow.server;

import com.example.rene.myarrow.server.transfer.TransferSpeicher;
import com.example.rene.myarrow.server.schuetzen.Schuetzen;
import com.example.rene.myarrow.server.schuetzen.SchuetzenSpeicher;
import com.example.rene.myarrow.server.parcour.Parcour;
import com.example.rene.myarrow.server.parcour.ParcourSpeicher;
import com.example.rene.myarrow.server.clients.ClientsSpeicher;
import com.example.rene.myarrow.server.ziel.Ziel;
import com.example.rene.myarrow.server.ziel.ZielSpeicher;
import com.example.rene.myarrow.server.bogen.Bogen;
import com.example.rene.myarrow.server.bogen.BogenSpeicher;
import com.example.rene.myarrow.server.pfeil.Pfeil;
import com.example.rene.myarrow.server.pfeil.PfeilSpeicher;
import com.example.rene.myarrow.server.runden.Runden;
import com.example.rene.myarrow.server.runden.RundenSpeicher;
import com.example.rene.myarrow.server.rundenschuetzen.RundenSchuetzen;
import com.example.rene.myarrow.server.rundenschuetzen.RundenSchuetzenSpeicher;
import com.example.rene.myarrow.server.rundenziel.RundenZiel;
import com.example.rene.myarrow.server.rundenziel.RundenZielSpeicher;
import com.example.rene.myarrow.server.uptime.upTimeSpeicher;
import com.example.rene.myarrow.server.managegid.ManageGIDSpeicher;
import com.example.rene.myarrow.server.updatemobile.UpdateMobileSpeicher;
import com.example.rene.myarrow.server.updatemobile.UpdateMobile;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author René Düber, 2016
 *
 */
@WebServlet("/MyArrowServer")
public class MyArrowServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private int row_id;
    private final boolean action=false;
  
    public MyArrowServlet() {
        System.out.println("=====================================================================");
        System.out.println("System: Constructor(): Servlet wird initialisiert...");
        
        /**
         * Store startup time
         */
        final upTimeSpeicher uTS = new upTimeSpeicher();
        row_id = uTS.insertStartTime();
        uTS.schliessen();
        
        /**
         * GUI MyArrowEditor starten
         */
        // System.out.println("System: Constructor(): Start GUI");
        // new MyArrowEditor().main(new String[]{""});
        System.out.println("=====================================================================");
    }
  
    @Override
    public void destroy() {
        System.out.println("System: destroy(): entered...");
        final upTimeSpeicher uTS = new upTimeSpeicher();
        uTS.updateEndTime(row_id);
        uTS.schliessen();
        super.destroy();
        System.out.println("=====================================================================");
    }
  
    @Override
    protected void service(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        System.out.println("System: service(): entered...");
        super.service(request, response);
        System.out.println("=====================================================================");
    }
    
    @Override
    protected void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {
        System.out.println("System: doGet(): entered...");
	// wird nicht verwendet...
        System.out.println("=====================================================================");
    }
  
    @Override
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {
        System.out.println("System: doPost(): entered...");

        /**
         * Sessioninformationen anzeigen
         */
        System.out.println("System: doPost(): userAgent      = "
                + request.getHeader("user-agent"));
        System.out.println("System: doPost(): accept         = "
                + request.getHeader("accept"));
        System.out.println("System: doPost(): acceptEncoding = "
                + request.getHeader("accept-encoding"));
        System.out.println("System: doPost(): getContentTpye = "
                + request.getContentType());
        System.out.println("System: doPost(): getLocalAddr   = "
                + request.getLocalAddr());
        System.out.println("System: doPost(): getLocalPort   = "
                + request.getLocalPort());
        System.out.println("System: doPost(): getRemoteAddr  = "
                + request.getRemoteAddr());
        System.out.println("System: doPost(): getRemotePort  = "
                + request.getRemotePort());
        System.out.println("System: doPost(): Tabelle        = "
                + request.getParameter("table"));
        System.out.println("System: doPost(): Action         = "
                + request.getParameter("action"));
        System.out.println("=====================================================================");

        if (request.getParameter("action")!=null) {
            String deviceid = request.getParameter("deviceid");
            System.out.print("System: doPost(): Device = " + deviceid);
            TransferSpeicher ts = new TransferSpeicher();
            ResultSet rs = ts.getTransferListe(deviceid);

            switch (request.getParameter("action")) { 
                /**
                 * Start communication, send number of records to be transfered
                 */
                case "getdata":
                    try {
                        rs.last();
                        System.out.print("System: doPost(): zu transferierende Transactionen = " + rs.getRow());
                        response.setContentType("text/html");
                        PrintWriter out = response.getWriter();
                        out.println("anzahl=" + String.valueOf(rs.getRow()));
                        out.close();
                    } catch (SQLException ex) {
                        System.err.println("System: doPost(): Anwort (anzahl=999) konnte nicht gesendet werden!! " + ex);
                        System.err.println("System: doPost(): " + ex);
                        System.err.println("=====================================================================");
                    }
                    break;
                /**
                 * Send the transaction from other mobile to the current mobile
                 */
                case "o.k.":
                    try {
                        boolean send = false;
                        /**
                         * Nothing to send
                         */
                        if (!rs.first()) {
                            System.out.print("System: doPost(): Kein Transferrecord selektiert, Übertragung beenden!!");
                            send= sendEnd(response);
                            break;
                        }
                        System.out.print("System: doPost(): TransferListe = " + rs.getString(3));
                        System.out.print("System: doPost(): ID            = " + rs.getInt(1));
                        System.out.print("System: doPost(): GID           = " + rs.getString(2));
                        System.out.print("=====================================================================");
                        /**
                         * Get and send dataset
                         */
                        switch (rs.getString(3)) {
                            case "schuetzen":
                                send = sendSchuetzen(rs.getInt(1), rs.getString(2), deviceid, response);
                                break;

                            case "bogen":
                                send = sendBogen(rs.getInt(1), rs.getString(2), deviceid, response);
                                break;

                            case "pfeil":
                                send = sendPfeil(rs.getInt(1), rs.getString(2), deviceid, response);
                                break;

                            case "parcour":
                                send = sendParcour(rs.getInt(1), rs.getString(2), deviceid, response);
                                break;

                            case "ziel":
                                send = sendZiel(rs.getInt(1), rs.getString(2), deviceid, response);
                                break;

                            case "runden":
                                send = sendRunden(rs.getInt(1), rs.getString(2), deviceid, response);
                                break;

                            case "rundenschuetzen":
                                send = sendRundenSchuetzen(rs.getInt(1), rs.getString(2), deviceid, response);
                                break;

                            case "rundenziel":
                                send = sendRundenZiel(rs.getInt(1), rs.getString(2), deviceid, response);
                                break;

                            default:
                                System.err.println("System: doPost(): Tabellenname nicht gefunden!!"); 
                                break;
                        }
                        /**
                         * After successful upload, set flag transformed to done (1)
                         */
                        if (send) {
                            ts.updateTransferDone(rs.getInt(1));
                        }
                        
                    } catch (SQLException ex) {
                        System.err.println("System: doPost(): Anwort (anzahl=999) konnte nicht gesendet werden!! " + ex);
                    }
                    System.out.print("=====================================================================");
                    break;
                
                case "updatemobile":
                    /**
                     * Upload from change to the GIDs
                     */
                    Boolean send=null;
                    UpdateMobileSpeicher ums = new UpdateMobileSpeicher();
                    UpdateMobile um = ums.loadUpdateMobileNext(deviceid);
                    if (um!=null){
                        /**
                         * Send data to mobile
                         */
                        send = sendUpdateMobile(um, response);
                        /**
                         * After successful upload, set flag transformed to done (1)
                         */
                        if (send) {
                            ums.UpdateTransferDone(um.id);
                        }
                    }
                    ums.schliessen();
                    break;
                    
                case "done":
                    System.out.print("System: doPost(): Done - Alles synchronisiert.");
                    System.out.print("=====================================================================");
                    break;
            }
            /**
             * Datenbankverbindung schliessen
             */
            ts.schliessen();
            
        } else {
            /**
             * in Abhängigkeit der zu synchroniserenden Tabelle
             * die Prozedure auswählen, Daten erhalten und in der Datenbank
             * abspeichern
             */        
            switch (request.getParameter("table")) {
                case "schuetzen":
                    getSchuetzen(request);
                    break;

                case "bogen":
                    getBogen(request);
                    break;

                case "pfeil":
                    getPfeil(request);
                    break;

                case "parcour":
                    getParcour(request);
                    break;

                case "ziel":
                    getZiel(request);
                    break;

                case "runden":
                    getRunden(request);
                    break;

                case "rundenschuetzen":
                    getRundenSchuetzen(request);
                    break;

                case "rundenziel":
                    getRundenZiel(request);
                    break;

                default:
                    System.err.println("System: doPost(): Tabellenname nicht gefunden!!"); 
                    break;
            }

            /**
             * Erfolg zurückmelden
             */
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("Data saved");
            System.out.print("System: doPost(): Data saved!!");
            System.out.print("=====================================================================");

        }
    }
    
    private void getSchuetzen(HttpServletRequest request) {
        final String table = "schuetzen";
        final String deviceid = request.getParameter(Schuetzen.GID).split("_")[0];
        
        /**
         * Request-Body abholen
         */
        System.out.println("System: getSchuetzen(): " + Schuetzen.ID  +
                " = " + request.getParameter(Schuetzen.ID));
        System.out.println("System: getSchuetzen(): " + Schuetzen.GID +
                " = " + request.getParameter(Schuetzen.GID));
        System.out.println("System: getSchuetzen(): " + Schuetzen.NAME +
                " = " + request.getParameter(Schuetzen.NAME));
        System.out.println("System: getSchuetzen(): " + Schuetzen.DATEINAME +
                " = " + request.getParameter(Schuetzen.DATEINAME));
        System.out.println("System: getSchuetzen(): " + Schuetzen.ZEITSTEMPEL +
                " = " + request.getParameter(Schuetzen.ZEITSTEMPEL));
        
        /**
         * Falls der Client / das Handy noch nicht angelegt ist, einfügen
         */
        checkClient(deviceid);
    
        /**
         * Befüllung von Schuetzen
         */
        Schuetzen schuetzen = new Schuetzen();
        schuetzen.setID(
            Integer.valueOf(request.getParameter(Schuetzen.ID)));
        schuetzen.setGID(
            request.getParameter(Schuetzen.GID));
        schuetzen.setName(
            request.getParameter(Schuetzen.NAME));
        schuetzen.setDateiname(
            request.getParameter(Schuetzen.DATEINAME));
        schuetzen.setZeitstempel(
            Long.valueOf(request.getParameter(Schuetzen.ZEITSTEMPEL)));
        System.out.println("System: getSchuetzen(): Schuetzen = "
                + schuetzen.toString());

        /**
         * Mapping der GID von Mobile auf Server
         */
        ManageGIDSpeicher managegid = new ManageGIDSpeicher();
        String mappedGID = managegid.getServerGID(table, schuetzen.getGID(), deviceid);
        if (mappedGID != null) {
            schuetzen.setGID(mappedGID);
            System.out.println("System: getSchuetzen(): Schuetzen = " + schuetzen.toString());
        } else {
            System.err.println("System: getSchuetzen():  Error during mapping of GID!");
        }
        
        /**
         * Datensatz abspeichern
         */
        final SchuetzenSpeicher schuetzenSpeicher = new SchuetzenSpeicher();
        schuetzenSpeicher.insertSchuetzen(schuetzen);        
        schuetzenSpeicher.schliessen();

        /**
         * Update der zu synchrisierenden Datensätze und Clients
         */
        final TransferSpeicher ts = new TransferSpeicher();
        ts.updateTransferListe(schuetzen.getGID(), deviceid, table);
        ts.schliessen();
        
    }

    private void getParcour(HttpServletRequest request) {
        final String table = "parcour";
        final String deviceid = request.getParameter(Parcour.GID).split("_")[0];
        
        /**
         * Request-Body abholen
         */
        System.out.println("System: getParcour(): " + Parcour.ID  +
                " = " + request.getParameter(Parcour.ID));
        System.out.println("System: getParcour(): " + Parcour.GID +
                " = " + request.getParameter(Parcour.GID));
        System.out.println("System: getParcour(): " + Parcour.NAME +
                " = " + request.getParameter(Parcour.NAME));
        System.out.println("System: getParcour(): " + Parcour.ANZAHL_ZIELE +
                " = " + request.getParameter(Parcour.ANZAHL_ZIELE));
        System.out.println("System: getParcour(): " + Parcour.STRASSE +
                " = " + request.getParameter(Parcour.STRASSE));
        System.out.println("System: getParcour(): " + Parcour.PLZ +
                " = " + request.getParameter(Parcour.PLZ));
        System.out.println("System: getParcour(): " + Parcour.ORT +
                " = " + request.getParameter(Parcour.ORT));
        System.out.println("System: getParcour(): " + Parcour.GPS_LAT_KOORDINATEN +
                " = " + request.getParameter(Parcour.GPS_LAT_KOORDINATEN));
        System.out.println("System: getParcour(): " + Parcour.GPS_LON_KOORDINATEN +
                " = " + request.getParameter(Parcour.GPS_LON_KOORDINATEN));
        System.out.println("System: getParcour(): " + Parcour.ANMERKUNG +
                " = " + request.getParameter(Parcour.ANMERKUNG));
        System.out.println("System: getParcour(): " + Parcour.STANDARD +
                " = " + request.getParameter(Parcour.STANDARD));
        System.out.println("System: getParcour(): " + Parcour.ZEITSTEMPEL +
                " = " + request.getParameter(Parcour.ZEITSTEMPEL));
        
        /**
         * Falls der Client / das Handy noch nicht angelegt ist, einfügen
         */
        checkClient(deviceid);
    
        /**
         * Befüllung von Parcour
         */
        Parcour parcour = new Parcour();
        parcour.setID(
            Integer.valueOf(request.getParameter(Parcour.ID)));
        parcour.setGID(
            request.getParameter(Parcour.GID));
        parcour.setName(
            request.getParameter(Parcour.NAME));
        parcour.setAnzahlZiele(
            Integer.valueOf(request.getParameter(Parcour.ANZAHL_ZIELE)));
        parcour.setStrasse(
            request.getParameter(Parcour.STRASSE));
        parcour.setPLZ(
            request.getParameter(Parcour.PLZ));
        parcour.setOrt(
            request.getParameter(Parcour.ORT));
        parcour.setGPS_Lat_Koordinaten(
            request.getParameter(Parcour.GPS_LAT_KOORDINATEN));
        parcour.setGPS_Lon_Koordinaten(
            request.getParameter(Parcour.GPS_LON_KOORDINATEN));
        parcour.setAnmerkung(
            request.getParameter(Parcour.ANMERKUNG));
        parcour.setStandard(
            request.getParameter(Parcour.STANDARD).equals("true")?1:0);
        parcour.setZeitstempel(
            Long.valueOf(request.getParameter(Parcour.ZEITSTEMPEL)));
        System.out.println("System: getParcour(): Parcour = "
                + parcour.toString());
    
        /**
         * Mapping der GID von Mobile auf Server
         */
        ManageGIDSpeicher managegid = new ManageGIDSpeicher();
        String mappedGID = managegid.getServerGID(table, parcour.getGID(), deviceid);
        if (mappedGID != null) {
            parcour.setGID(mappedGID);
            System.out.println("System: getParcour(): Parcour = " + parcour.toString());
        } else {
            System.err.println("System: getParcour():  Error during mapping of GID!");
        }
        
        /**
         * Datensatz abspeichern
         */
        final ParcourSpeicher parcourSpeicher = new ParcourSpeicher();
        parcourSpeicher.insertParcour(parcour);
        parcourSpeicher.schliessen();

        /**
         * Update der zu synchrisierenden Datensätze und Clients
         */
        final TransferSpeicher ts = new TransferSpeicher();
        ts.updateTransferListe(parcour.getGID(), deviceid, table);
        ts.schliessen();
        
    }

    private void getZiel(HttpServletRequest request) {
        final String table = "ziel";
        final String deviceid = request.getParameter(Ziel.GID).split("_")[0];
        
        /**
         * Request-Body abholen
         */
        System.out.println("System: getZiel(): " + Ziel.ID  +
                " = " + request.getParameter(Ziel.ID));
        System.out.println("System: getZiel(): " + Ziel.GID +
                " = " + request.getParameter(Ziel.GID));
        System.out.println("System: getZiel(): " + Ziel.PARCOURGID +
                " = " + request.getParameter(Ziel.PARCOURGID));
        System.out.println("System: getZiel(): " + Ziel.NUMMER +
                " = " + request.getParameter(Ziel.NUMMER));
        System.out.println("System: getZiel(): " + Ziel.NAME +
                " = " + request.getParameter(Ziel.NAME));
        System.out.println("System: getZiel(): " + Ziel.GPS_LAT_KOORDINATEN +
                " = " + request.getParameter(Ziel.GPS_LAT_KOORDINATEN));
        System.out.println("System: getZiel(): " + Ziel.GPS_LON_KOORDINATEN +
                " = " + request.getParameter(Ziel.GPS_LON_KOORDINATEN));
        System.out.println("System: getZiel(): " + Ziel.DATEINAME +
                " = " + request.getParameter(Ziel.DATEINAME));
        System.out.println("System: getZiel(): " + Ziel.ZEITSTEMPEL +
                " = " + request.getParameter(Ziel.ZEITSTEMPEL));
        
        /**
         * Falls der Client / das Handy noch nicht angelegt ist, einfügen
         */
        checkClient(deviceid);
    
        /**
         * Befüllung vom Ziel
         */
        Ziel ziel = new Ziel();
        ziel.setID(
            Integer.valueOf(request.getParameter(Ziel.ID)));
        ziel.setGID(
            request.getParameter(Ziel.GID));
        ziel.setParcourGID(
            request.getParameter(Ziel.PARCOURGID));
        ziel.setNummer(
            Integer.valueOf(request.getParameter(Ziel.NUMMER)));
        ziel.setName(
            request.getParameter(Ziel.NAME));
        ziel.setGPS_Lat_Koordinaten(
            request.getParameter(Ziel.GPS_LAT_KOORDINATEN));
        ziel.setGPS_Lon_Koordinaten(
            request.getParameter(Ziel.GPS_LON_KOORDINATEN));
        ziel.setDateiname(
            request.getParameter(Ziel.DATEINAME));
        ziel.setZeitstempel(
            Long.valueOf(request.getParameter(Ziel.ZEITSTEMPEL)));
        System.out.println("System: getZiel(): Ziel = "
                + ziel.toString());
        
        /**
         * Mapping der GID von Mobile auf Server
         */
        ManageGIDSpeicher managegid = new ManageGIDSpeicher();
        String mappedGID = managegid.getServerGID(table, ziel.getGID(), deviceid);
        if (mappedGID != null) {
            ziel.setGID(mappedGID);
            System.out.println("System: getZiel(): Ziel = " + ziel.toString());
        } else {
            System.err.println("System: getZiel():  Error during mapping of GID!");
        }    
        /**
         * Datensatz abspeichern
         */
        final ZielSpeicher zielSpeicher = new ZielSpeicher();
        zielSpeicher.insertZiel(ziel);
        zielSpeicher.schliessen();

        /**
         * Update der zu synchrisierenden Datensätze und Clients
         */
        final TransferSpeicher ts = new TransferSpeicher();
        ts.updateTransferListe(ziel.getGID(), deviceid, table);
        ts.schliessen();
    }

    private void getBogen(HttpServletRequest request) {
        final String table = "bogen";
        final String deviceid = request.getParameter(Bogen.GID).split("_")[0];
        
        /**
         * Request-Body abholen
         */
        System.out.println("System: getBogen(): " + Bogen.ID  +
                " = " + request.getParameter(Bogen.ID));
        System.out.println("System: getBogen(): " + Ziel.GID +
                " = " + request.getParameter(Bogen.GID));
        System.out.println("System: getZiel(): " + Bogen.NAME +
                " = " + request.getParameter(Bogen.NAME));
        System.out.println("System: getZiel(): " + Bogen.DATEINAME +
                " = " + request.getParameter(Bogen.DATEINAME));
        System.out.println("System: getZiel(): " + Bogen.STANDARD +
                " = " + request.getParameter(Bogen.STANDARD));
        System.out.println("System: getZiel(): " + Bogen.ZEITSTEMPEL +
                " = " + request.getParameter(Bogen.ZEITSTEMPEL));
        
        /**
         * Falls der Client / das Handy noch nicht angelegt ist, einfügen
         */
        checkClient(deviceid);
    
        /**
         * Befüllung vom Ziel
         */
        Bogen bogen = new Bogen();
        bogen.setID(
            Integer.valueOf(request.getParameter(Bogen.ID)));
        bogen.setGID(
            request.getParameter(Bogen.GID));
        bogen.setName(
            request.getParameter(Bogen.NAME));
        bogen.setDateiname(
            request.getParameter(Bogen.DATEINAME));
        bogen.setStandard(
            request.getParameter(Bogen.STANDARD).equals("true")?1:0);
        bogen.setZeitstempel(
            Long.valueOf(request.getParameter(Bogen.ZEITSTEMPEL)));
        System.out.println("System: getBogen(): Bogen = "
                + bogen.toString());
        
        /**
         * Mapping der GID von Mobile auf Server
         */
        ManageGIDSpeicher managegid = new ManageGIDSpeicher();
        String mappedGID = managegid.getServerGID(table, bogen.getGID(), deviceid);
        if (mappedGID != null) {
            bogen.setGID(mappedGID);
            System.out.println("System: getBogen(): Bogen = " + bogen.toString());
        } else {
            System.err.println("System: getBogen():  Error during mapping of GID!");
        }
            
        /**
         * Datensatz abspeichern
         */
        final BogenSpeicher bogenSpeicher = new BogenSpeicher();
        bogenSpeicher.insertBogen(bogen);
        bogenSpeicher.schliessen();

        /**
         * Update der zu synchrisierenden Datensätze und Clients
         */
        final TransferSpeicher ts = new TransferSpeicher();
        ts.updateTransferListe(bogen.getGID(), deviceid, table);
        ts.schliessen();
        
    }

    private void getPfeil(HttpServletRequest request){
        final String table = "pfeil";
        final String deviceid = request.getParameter(Pfeil.GID).split("_")[0];
        
        /**
         * Request-Body abholen
         */
        System.out.println("System: getPfeil(): " + Pfeil.ID  +
                " = " + request.getParameter(Pfeil.ID));
        System.out.println("System: getPfeil(): " + Pfeil.GID +
                " = " + request.getParameter(Pfeil.GID));
        System.out.println("System: getPfeil(): " + Pfeil.NAME +
                " = " + request.getParameter(Pfeil.NAME));
        System.out.println("System: getPfeil(): " + Pfeil.DATEINAME +
                " = " + request.getParameter(Pfeil.DATEINAME));
        System.out.println("System: getPfeil(): " + Pfeil.STANDARD +
                " = " + request.getParameter(Pfeil.STANDARD));
        System.out.println("System: getPfeil(): " + Pfeil.ZEITSTEMPEL +
                " = " + request.getParameter(Pfeil.ZEITSTEMPEL));
        
        /**
         * Falls der Client / das Handy noch nicht angelegt ist, einfügen
         */
        checkClient(deviceid);
    
        /**
         * Befüllung vom Ziel
         */
        Pfeil pfeil = new Pfeil();
        pfeil.setID(
            Integer.valueOf(request.getParameter(Pfeil.ID)));
        pfeil.setGID(
            request.getParameter(Pfeil.GID));
        pfeil.setName(
            request.getParameter(Pfeil.NAME));
        pfeil.setDateiname(
            request.getParameter(Pfeil.DATEINAME));
        pfeil.setStandard(
            request.getParameter(Pfeil.STANDARD).equals("true")?1:0);
        pfeil.setZeitstempel(
            Long.valueOf(request.getParameter(Pfeil.ZEITSTEMPEL)));
        System.out.println("System: getPfeil(): Pfeil = "
                + pfeil.toString());
        
        /**
         * Mapping der GID von Mobile auf Server
         */
        ManageGIDSpeicher managegid = new ManageGIDSpeicher();
        String mappedGID = managegid.getServerGID(table, pfeil.getGID(), deviceid);
        if (mappedGID != null) {
            pfeil.setGID(mappedGID);
            System.out.println("System: getPfeil(): Pfeil = " + pfeil.toString());
        } else {
            System.err.println("System: getvPfeil():  Error during mapping of GID!");
        }
            
        /**
         * Datensatz abspeichern
         */
        final PfeilSpeicher pfeilSpeicher = new PfeilSpeicher();
        pfeilSpeicher.insertPfeil(pfeil);        
        pfeilSpeicher.schliessen();

        /**
         * Update der zu synchrisierenden Datensätze und Clients
         */
        final TransferSpeicher ts = new TransferSpeicher();
        ts.updateTransferListe(pfeil.getGID(), deviceid, table);
        ts.schliessen();
    }

    private void getRunden(HttpServletRequest request){
        final String table = "runden";
        final String deviceid = request.getParameter(Runden.GID).split("_")[0];
        
        /**
         * Request-Body abholen
         */
        System.out.println("System: getRunden(): " + Runden.ID  +
                " = " + request.getParameter(Runden.ID));
        System.out.println("System: getRunden(): " + Runden.GID +
                " = " + request.getParameter(Runden.GID));
        System.out.println("System: getRunden(): " + Runden.PARCOURGID +
                " = " + request.getParameter(Runden.PARCOURGID));
        System.out.println("System: getRunden(): " + Runden.BOGENGID +
                " = " + request.getParameter(Runden.BOGENGID));
        System.out.println("System: getRunden(): " + Runden.PFEILGID +
                " = " + request.getParameter(Runden.PFEILGID));
        System.out.println("System: getRunden(): " + Runden.STARTZEIT +
                " = " + request.getParameter(Runden.STARTZEIT));
        System.out.println("System: getRunden(): " + Runden.S_STARTZEIT +
                " = " + request.getParameter(Runden.S_STARTZEIT));
        System.out.println("System: getRunden(): " + Runden.ENDZEIT +
                " = " + request.getParameter(Runden.ENDZEIT));
        System.out.println("System: getRunden(): " + Runden.WETTER +
                " = " + request.getParameter(Runden.WETTER));
        System.out.println("System: getRunden(): " + Runden.PUNKTESTAND +
                " = " + request.getParameter(Runden.PUNKTESTAND));
        
        /**
         * Falls der Client / das Handy noch nicht angelegt ist, einfügen
         */
        checkClient(deviceid);
    
        /**
         * Befüllung vom Ziel
         */
        Runden runden = new Runden();
        runden.setID(
            Integer.valueOf(request.getParameter(Runden.ID)));
        runden.setGID(
            request.getParameter(Runden.GID));
        runden.setParcourGID(
            request.getParameter(Runden.PARCOURGID));
        runden.setBogenGID(
            request.getParameter(Runden.BOGENGID));
        runden.setPfeilGID(
            request.getParameter(Runden.PFEILGID));
        runden.setStartzeit(
            Long.valueOf(request.getParameter(Runden.STARTZEIT)));
        runden.setS_Startzeit(
            request.getParameter(Runden.S_STARTZEIT));
        runden.setEndzeit(
            Long.valueOf(request.getParameter(Runden.ENDZEIT)));
        runden.setWetter(
            request.getParameter(Runden.WETTER));
        runden.setPunktestand(
            Integer.valueOf(request.getParameter(Runden.PUNKTESTAND)));
        System.out.println("System: getRunden(): Runden = "
                + runden.toString());
        
        /**
         * Mapping der GID von Mobile auf Server
         */
        ManageGIDSpeicher managegid = new ManageGIDSpeicher();
        // GID
        String mappedGID = managegid.getServerGID(table, runden.getGID(), deviceid);
        if (mappedGID != null) {
            runden.setGID(mappedGID);
            System.out.println("System: getRunden(): Runden = " + runden.toString());
        } else {
            System.err.println("System: getRunden():  Error during mapping of GID!");
        }
        // Parcour
        mappedGID = managegid.getServerGID(table, runden.getParcourGID(), deviceid);
        if (mappedGID != null) {
            runden.setParcourGID(mappedGID);
            System.out.println("System: getRunden(): Parcour = " + runden.toString());
        } else {
            System.err.println("System: getRunden():  Error during mapping of GID!");
        }
        // Bogen
        mappedGID = managegid.getServerGID(table, runden.getBogenGID(), deviceid);
        if (mappedGID != null) {
            runden.setBogenGID(mappedGID);
            System.out.println("System: getRunden(): Bogen = " + runden.toString());
        } else {
            System.err.println("System: getRunden():  Error during mapping of GID!");
        }
        // Pfeil
        mappedGID = managegid.getServerGID(table, runden.getPfeilGID(), deviceid);
        if (mappedGID != null) {
            runden.setPfeilGID(mappedGID);
            System.out.println("System: getRunden(): Pfeil = " + runden.toString());
        } else {
            System.err.println("System: getRunden():  Error during mapping of GID!");
        }
            
        /**
         * Datensatz abspeichern
         */
        final RundenSpeicher rundenSpeicher = new RundenSpeicher();
        rundenSpeicher.insertRunden(runden);
        rundenSpeicher.schliessen();

        /**
         * Update der zu synchrisierenden Datensätze und Clients
         */
        final TransferSpeicher ts = new TransferSpeicher();
        ts.updateTransferListe(runden.getGID(), deviceid, table);
        ts.schliessen();
        
    }
    
    private void getRundenSchuetzen(HttpServletRequest request){
        final String table = "rundenschuetzen";
        final String deviceid = request.getParameter(RundenSchuetzen.GID).split("_")[0];
        
        /**
         * Request-Body abholen
         */
        System.out.println("System: RundenSchuetzen(): " + RundenSchuetzen.ID  +
                " = " + request.getParameter(RundenSchuetzen.ID));
        System.out.println("System: RundenSchuetzen(): " + RundenSchuetzen.GID +
                " = " + request.getParameter(RundenSchuetzen.GID));
        System.out.println("System: RundenSchuetzen(): " + RundenSchuetzen.SCHUETZENGID +
                " = " + request.getParameter(RundenSchuetzen.SCHUETZENGID));
        System.out.println("System: RundenSchuetzen(): " + RundenSchuetzen.RUNDENGID +
                " = " + request.getParameter(RundenSchuetzen.RUNDENGID));
        System.out.println("System: RundenSchuetzen(): " + RundenSchuetzen.GESAMTERGEBNIS +
                " = " + request.getParameter(RundenSchuetzen.GESAMTERGEBNIS));
        System.out.println("System: RundenSchuetzen(): " + RundenSchuetzen.ZEITSTEMPEL +
                " = " + request.getParameter(RundenSchuetzen.ZEITSTEMPEL));
        
        /**
         * Falls der Client / das Handy noch nicht angelegt ist, einfügen
         */
        checkClient(deviceid);
    
        /**
         * Befüllung vom Ziel
         */
        final RundenSchuetzen rundenschuetzen = new RundenSchuetzen();
        rundenschuetzen.setID(
            Integer.valueOf(request.getParameter(RundenSchuetzen.ID)));
        rundenschuetzen.setGID(
            request.getParameter(RundenSchuetzen.GID));
        rundenschuetzen.setSchuetzenGID(
            request.getParameter(RundenSchuetzen.SCHUETZENGID));
        rundenschuetzen.setRundenGID(
            request.getParameter(RundenSchuetzen.RUNDENGID));
        rundenschuetzen.setGesamtErgebnis(
            Integer.valueOf(request.getParameter(RundenSchuetzen.GESAMTERGEBNIS)));
        rundenschuetzen.setZeitstempel(
            Long.valueOf(request.getParameter(RundenSchuetzen.ZEITSTEMPEL)));
        System.out.println("System: RundenSchuetzen(): RundenSchuetzen = "
                + rundenschuetzen.toString());

        /**
         * Mapping der GID von Mobile auf Server
         */
        ManageGIDSpeicher managegid = new ManageGIDSpeicher();
        // GID
        String mappedGID = managegid.getServerGID(table, rundenschuetzen.getGID(), deviceid);
        if (mappedGID != null) {
            rundenschuetzen.setGID(mappedGID);
            System.out.println("System: getRundenSchuetzen(): RundenSchuetzen = " + rundenschuetzen.toString());
        } else {
            System.err.println("System: getRundenSchuetzen():  Error during mapping of GID!");
        }
        // Runden
        mappedGID = managegid.getServerGID(table, rundenschuetzen.getRundenGID(), deviceid);
        if (mappedGID != null) {
            rundenschuetzen.setRundenGID(mappedGID);
            System.out.println("System: getRundenSchuetzen(): RundenSchuetzen = " + rundenschuetzen.toString());
        } else {
            System.err.println("System: getRundenSchuetzen():  Error during mapping of GID!");
        }
        // Schuetzen
        mappedGID = managegid.getServerGID(table, rundenschuetzen.getSchuetzenGID(), deviceid);
        if (mappedGID != null) {
            rundenschuetzen.setSchuetzenGID(mappedGID);
            System.out.println("System: getRundenSchuetzen(): RundenSchuetzen = " + rundenschuetzen.toString());
        } else {
            System.err.println("System: getRundenSchuetzen():  Error during mapping of GID!");
        }


        /**
         * Datensatz abspeichern
         */
        final RundenSchuetzenSpeicher rundenschuetzenSpeicher = new RundenSchuetzenSpeicher();
        rundenschuetzenSpeicher.insertRundenSchuetzen(rundenschuetzen);
        rundenschuetzenSpeicher.schliessen();

        /**
         * Update der zu synchrisierenden Datensätze und Clients
         */
        final TransferSpeicher ts = new TransferSpeicher();
        ts.updateTransferListe(rundenschuetzen.getGID(), deviceid, table);
        ts.schliessen();
        
    }
    
    private void getRundenZiel(HttpServletRequest request){
        final String table = "rundenziel";
        final String deviceid = request.getParameter(RundenZiel.GID).split("_")[0];
        
        /**
         * Request-Body abholen
         */
        System.out.println("System: getRundenZiel(): " + RundenZiel.ID  +
                " = " + request.getParameter(RundenZiel.ID));
        System.out.println("System: getRundenZiel(): " + RundenZiel.GID +
                " = " + request.getParameter(RundenZiel.GID));
        System.out.println("System: getRundenZiel(): " + RundenZiel.RUNDENGID +
                " = " + request.getParameter(RundenZiel.RUNDENGID));
        System.out.println("System: getRundenZiel(): " + RundenZiel.ZIELGID +
                " = " + request.getParameter(RundenZiel.ZIELGID));
        System.out.println("System: getRundenZiel(): " + RundenZiel.RUNDENSCHUETZENGID +
                " = " + request.getParameter(RundenZiel.RUNDENSCHUETZENGID));
        System.out.println("System: getRundenZiel(): " + RundenZiel.EINS +
                " = " + request.getParameter(RundenZiel.EINS));
        System.out.println("System: getRundenZiel(): " + RundenZiel.ZWEI +
                " = " + request.getParameter(RundenZiel.ZWEI));
        System.out.println("System: getRundenZiel(): " + RundenZiel.DREI +
                " = " + request.getParameter(RundenZiel.DREI));
        System.out.println("System: getRundenZiel(): " + RundenZiel.KILL +
                " = " + request.getParameter("kill"));
        System.out.println("System: getRundenZiel(): " + RundenZiel.KILLKILL +
                " = " + request.getParameter(RundenZiel.KILLKILL));
        System.out.println("System: getRundenZiel(): " + RundenZiel.PUNKTE +
                " = " + request.getParameter(RundenZiel.PUNKTE));
        System.out.println("System: getRundenZiel(): " + RundenZiel.ANMERKUNG +
                " = " + request.getParameter(RundenZiel.ANMERKUNG));
        System.out.println("System: getRundenZiel(): " + RundenZiel.DATEINAME +
                " = " + request.getParameter(RundenZiel.DATEINAME));        
        System.out.println("System: getRundenZiel(): " + RundenZiel.ZEITSTEMPEL +
                " = " + request.getParameter(RundenZiel.ZEITSTEMPEL));
    
        /**
         * Falls der Client / das Handy noch nicht angelegt ist, einfügen
         */
        checkClient(deviceid);
    
        /**
         * Befüllung vom Ziel
         */
        RundenZiel rundenziel = new RundenZiel();
        rundenziel.setID(
            Integer.valueOf(request.getParameter(RundenZiel.ID)));
        rundenziel.setGID(
            request.getParameter(RundenZiel.GID));
        rundenziel.setRundenGID(
            request.getParameter(RundenZiel.RUNDENGID));
        rundenziel.setZielGID(
            request.getParameter(RundenZiel.ZIELGID));
        rundenziel.setRundenSchuetzenGID(
            request.getParameter(RundenZiel.RUNDENSCHUETZENGID));
        rundenziel.setNummer(
            Integer.valueOf(request.getParameter(RundenZiel.NUMMER)));
        rundenziel.setEins(
            request.getParameter(RundenZiel.EINS).equals("true")?1:0);
        rundenziel.setZwei(
            request.getParameter(RundenZiel.ZWEI).equals("true")?1:0);
        rundenziel.setDrei(
            request.getParameter(RundenZiel.DREI).equals("true")?1:0);
        rundenziel.setKill(
            request.getParameter("kill").equals("true")?1:0);
        rundenziel.setKillKill(
            request.getParameter(RundenZiel.KILLKILL).equals("true")?1:0);
        rundenziel.setPunkte(
            Integer.valueOf(request.getParameter(RundenZiel.PUNKTE)));
        rundenziel.setAnmerkung(
            request.getParameter(RundenZiel.ANMERKUNG));
        rundenziel.setDateiname(
            request.getParameter(RundenZiel.DATEINAME));
        rundenziel.setZeitstempel(
            Long.valueOf(request.getParameter(RundenZiel.ZEITSTEMPEL)));
        System.out.println("System: getRundenZiel(): RundenZiel = "
                + rundenziel.toString());

        /**
         * Mapping der GID von Mobile auf Server
         */
        ManageGIDSpeicher managegid = new ManageGIDSpeicher();
        // GID
        String mappedGID = managegid.getServerGID(table, rundenziel.getGID(), deviceid);
        if (mappedGID != null) {
            rundenziel.setGID(mappedGID);
            System.out.println("System: getRundenZiel(): RundenZiel = " + rundenziel.toString());
        } else {
            System.err.println("System: getRundenZiel():  Error during mapping of GID!");
        }
        // Runden
        mappedGID = managegid.getServerGID(table, rundenziel.getRundenGID(), deviceid);
        if (mappedGID != null) {
            rundenziel.setRundenGID(mappedGID);
            System.out.println("System: getRundenZiel(): RundenSchuetzen = " + rundenziel.toString());
        } else {
            System.err.println("System: getRundenZiel():  Error during mapping of GID!");
        }
        // Ziel
        mappedGID = managegid.getServerGID(table, rundenziel.getZielGID(), deviceid);
        if (mappedGID != null) {
            rundenziel.setZielGID(mappedGID);
            System.out.println("System: getRundenZiel(): RundenZiel = " + rundenziel.toString());
        } else {
            System.err.println("System: getRundenZiel():  Error during mapping of GID!");
        }
        // RundenSchuetzen
        mappedGID = managegid.getServerGID(table, rundenziel.getRundenSchuetzenGID(), deviceid);
        if (mappedGID != null) {
            rundenziel.setRundenSchuetzenGID(mappedGID);
            System.out.println("System: getRundenZiel(): RundenZiel = " + rundenziel.toString());
        } else {
            System.err.println("System: getRundenZiel():  Error during mapping of GID!");
        }

        /**
         * Datensatz abspeichern
         */
        final RundenZielSpeicher rundenzielSpeicher = new RundenZielSpeicher();
        rundenzielSpeicher.insertRundenZiel(rundenziel);        
        rundenzielSpeicher.schliessen();

        /**
         * Update der zu synchrisierenden Datensätze und Clients
         */
        final TransferSpeicher ts = new TransferSpeicher();
        ts.updateTransferListe(rundenziel.getGID(), deviceid, table);
        ts.schliessen();
        
    }
    
    /**
     * Falls der Client / das Handy noch nicht angelegt ist, einfügen
     * 
     * @param deviceid
     *      die zu überprüfende Device-ID (IMEI)
     */
    private void checkClient(String deviceid) {
        System.out.println("System: checkClient(): deviceid = " + deviceid);
        ClientsSpeicher clientsSpeicher = new ClientsSpeicher();
        if (!clientsSpeicher.newClients(deviceid)) {
            clientsSpeicher.insertClients(deviceid, "Default Device");
        }
        clientsSpeicher.schliessen();
    }

    private boolean sendBogen(int id, String gid, String deviceid, HttpServletResponse response) {
        
        try {
            System.out.println("System: sendBogen(): ID  -      " + id);
            System.out.println("System: sendBogen(): GID -      " + gid);
            System.out.println("System: sendBogen(): RESPONSE - " + response);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            Bogen bogen = new BogenSpeicher().loadBogenDetails(gid);
            if (bogen==null) return false;

            /**
             * Mapping der GID from Server to Mobile
             */
            // bogen.setGID(new ManageGIDSpeicher().getMobilGID("bogen", gid, deviceid));
            

            System.out.println("System: sendParcour(): BOGEN -  " + bogen.toString());
            out.println(bogen.toString());
            out.close();
            return true;
        } catch ( IOException e) {
            System.err.println("System: sendBogen(): Datensatz konnte nicht gesendt werden!!");
            System.err.println("System: sendBogen(): " + e);
            return false;
        }
    }
    
    private boolean sendParcour(int id, String gid, String deviceid, HttpServletResponse response) {
        try {
            System.out.println("System: sendParcour(): ID  -      " + id);
            System.out.println("System: sendParcour(): GID -      " + gid);
            System.out.println("System: sendParcour(): RESPONSE - " + response);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            Parcour parcour = new ParcourSpeicher().loadParcourDetails(gid);
            if (parcour==null) return false;

            /**
             * Mapping der GID from Server to Mobile
             */
            // parcour.setGID(new ManageGIDSpeicher().getMobilGID("parcour", gid, deviceid));
            
            System.out.println("System: sendParcour(): PARCOUR -  " + parcour.toString());
            out.println(parcour.toString());
            out.close();
            return true;
        } catch ( IOException e) {
            System.err.println("System: sendParcour(): Datensatz konnte nicht gesendt werden!!");
            System.err.println("System: sendParcour(): " + e);
            return false;
        }
    }
    
    private boolean sendPfeil(int id, String gid, String deviceid, HttpServletResponse response) {
        try {
            System.out.println("System: sendPfeil(): ID  -      " + id);
            System.out.println("System: sendPfeil(): GID -      " + gid);
            System.out.println("System: sendPfeil(): RESPONSE - " + response);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            Pfeil pfeil = new PfeilSpeicher().loadPfeilDetails(gid);
            if (pfeil==null) return false;

            /**
             * Mapping der GID from Server to Mobile
             */
            // pfeil.setGID(new ManageGIDSpeicher().getMobilGID("pfeil", gid, deviceid));
            
            out.println(pfeil.toString());
            out.close();
            return true;
        } catch ( IOException e) {
            System.err.println("System: sendPfeil(): Datensatz konnte nicht gesendt werden!!");
            System.err.println("System: sendPfeil(): " + e);
            return false;
        }
        
    }
    
    private boolean sendRunden(int id, String gid, String deviceid, HttpServletResponse response) {
        try {
            System.out.println("System: sendRunden(): ID  -      " + id);
            System.out.println("System: sendRunden(): GID -      " + gid);
            System.out.println("System: sendRunden(): RESPONSE - " + response);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            Runden runden = new RundenSpeicher().loadRundenDetails(gid);
            if (runden==null) return false;

            /**
             * Mapping der GID from Server to Mobile
             */
            // runden.setGID(new ManageGIDSpeicher().getMobilGID("runden", gid, deviceid));
            runden.setParcourGID(new ManageGIDSpeicher().getMobilGID("parcour", runden.getParcourGID(), deviceid));
            runden.setBogenGID(new ManageGIDSpeicher().getMobilGID("bogen", runden.getBogenGID(), deviceid));
            runden.setPfeilGID(new ManageGIDSpeicher().getMobilGID("pfeil", runden.getPfeilGID(), deviceid));
            
            out.println(runden.toString());
            out.close();
            return true;
        } catch ( IOException e) {
            System.err.println("System: sendRunden(): Datensatz konnte nicht gesendt werden!!");
            System.err.println("System: sendRunden(): " + e);
            return false;
        }
    }
    
    private boolean sendRundenSchuetzen(int id, String gid, String deviceid, HttpServletResponse response) {
        try {
            System.out.println("System: sendRundenSchuetzen(): ID  -      " + id);
            System.out.println("System: sendRundenSchuetzen(): GID -      " + gid);
            System.out.println("System: sendRundenSchuetzen(): RESPONSE - " + response);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            RundenSchuetzen rundenschuetzen = new RundenSchuetzenSpeicher().loadRundenSchuetzenDetails(gid);
            if (rundenschuetzen==null) return false;
            
            /**
             * Mapping der GID from Server to Mobile
             */
            // rundenschuetzen.setGID(new ManageGIDSpeicher().getMobilGID("rundenschuetzen", gid, deviceid));
            // rundenschuetzen.setRundenGID(new ManageGIDSpeicher().getMobilGID("runden", rundenschuetzen.getRundenGID(), deviceid));
            // rundenschuetzen.setSchuetzenGID(new ManageGIDSpeicher().getMobilGID("schuetzen", rundenschuetzen.getSchuetzenGID(), deviceid));
            
            out.println(rundenschuetzen.toString());
            out.close();
            return true;
        } catch ( IOException e) {
            System.err.println("System: sendRundenSchuetzen(): Datensatz konnte nicht gesendt werden!!");
            System.err.println("System: sendRundenSchuetzen(): " + e);
            return false;
        }        
    }
    
    private boolean sendRundenZiel(int id, String gid, String deviceid, HttpServletResponse response) {
        try {
            System.out.println("System: sendRundenZiel(): ID  -      " + id);
            System.out.println("System: sendRundenZiel(): GID -      " + gid);
            System.out.println("System: sendRundenZiel(): RESPONSE - " + response);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            RundenZiel rundenziel = new RundenZielSpeicher().loadRundenZielDetails(gid);
            if (rundenziel==null) return false;
            
            /**
             * Mapping der GID from Server to Mobile
             */
            // rundenziel.setGID(new ManageGIDSpeicher().getMobilGID("rundenziel", gid, deviceid));
            // rundenziel.setRundenGID(new ManageGIDSpeicher().getMobilGID("runden", rundenziel.getRundenGID(), deviceid));
            // rundenziel.setZielGID(new ManageGIDSpeicher().getMobilGID("ziel", rundenziel.getZielGID(), deviceid));
            // rundenziel.setRundenSchuetzenGID(new ManageGIDSpeicher().getMobilGID("rundenschuetzen", rundenziel.getRundenSchuetzenGID(), deviceid));
            
            out.println(rundenziel.toString());
            out.close();
            return true;
        } catch ( IOException e) {
            System.err.println("System: sendRundenZiel(): Datensatz konnte nicht gesendt werden!!");
            System.err.println("System: sendRundenZiel(): " + e);
            return false;
        }
    }
    
    private boolean sendSchuetzen(int id, String gid, String deviceid, HttpServletResponse response) {
        try {
            System.out.println("System: sendSchuetzen(): ID  -      " + id);
            System.out.println("System: sendSchuetzen(): GID -      " + gid);
            System.out.println("System: sendSchuetzen(): DEVICEID - " + deviceid);
            System.out.println("System: sendSchuetzen(): RESPONSE - " + response);
            
            /**
             * Verbindung aufbauen
             */
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            
            /**
             * Basierend auf der GID die Daten laden
             */
            Schuetzen schuetzen = new SchuetzenSpeicher().loadSchuetzenDetails(gid);
            if (schuetzen==null) return false;
            
            /**
             * Mapping der GID from Server to Mobile
             */
            // schuetzen.setGID(new ManageGIDSpeicher().getMobilGID("schuetzen", gid, deviceid));
            
            /**
             * Daten senden und Verbindung schliessen
             */
            out.println(schuetzen.toString());
            out.close();
            
            /**
             * Alles ist gut gegangen und ab nach Hause
             */
            return true;
            
        } catch ( IOException e) {
            System.err.println("System: sendSchuetzen(): Datensatz konnte nicht gesendt werden!!");
            System.err.println("System: sendSchuetzen(): " + e);
            return false;
        }        
    }
    
    private boolean sendZiel(int id, String gid, String deviceid, HttpServletResponse response) {
        try {
            System.out.println("System: sendZiel(): ID  -      " + id);
            System.out.println("System: sendZiel(): GID -      " + gid);
            System.out.println("System: sendZiel(): RESPONSE - " + response);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            Ziel ziel = new ZielSpeicher().loadZielDetails(gid);
            if (ziel==null) return false;
            /**
             * Mapping der GID from Server to Mobile
             */
            // ziel.setGID(new ManageGIDSpeicher().getMobilGID("ziel", gid, deviceid));
            
            out.println(ziel.toString());
            out.close();
            return true;
        } catch ( IOException e) {
            System.err.println("System: sendZiel(): Datensatz konnte nicht gesendt werden!!");
            System.err.println("System: sendZiel(): " + e);
            return false;
        }
        
    }

    private boolean sendEnd(HttpServletResponse response) {
        try {
            System.out.println("System: sendEnd(): done=done");
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("done=done");
            out.close();
            return true;
        } catch ( IOException e) {
            System.err.println("System: sendEnd(): Datensatz konnte nicht gesendt werden!!");
            System.err.println("System: sendEnd(): " + e);
            return false;
        } 
    }

    private boolean sendUpdateMobile(UpdateMobile um, HttpServletResponse response) {
        
        try {
            if (um==null) return false;
            System.out.println("System: sendUpdateMobile(): ID  -       " + um.id);
            System.out.println("System: sendUpdateMobile(): TABLENAME - " + um.tablename);
            System.out.println("System: sendUpdateMobile(): TABLENAME - " + um.fieldname);
            System.out.println("System: sendUpdateMobile(): RESPONSE -  " + response);
            
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            System.out.println("System: sendUpdateMobile(): UpdateMobile -  " + um.toString());
            out.println(um.toString());
            out.close();
            return true;
        } catch (IOException e) {
            System.err.println("System: sendUpdateMobile(): Datensatz konnte nicht gesendt werden!!");
            System.err.println("System: sendUpdateMobile(): " + e);
            return false;
        }
    }
       
}
