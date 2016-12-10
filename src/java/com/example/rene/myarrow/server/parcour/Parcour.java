package com.example.rene.myarrow.server.parcour;

import static java.util.Objects.isNull;

/**
 * Created by nily on 11.06.16.
 */
public class Parcour implements ParcourColumns {
    private long id;
    private String gid;
    private String name;
    private int anzahl_ziele;
    private String strasse;
    private String plz;
    private String ort;
    private String gps_lat_koordinaten;
    private String gps_lon_koordinaten;
    private String anmerkung;
    private int standard;
    private long zeitstempel;

    public Parcour() { }
  
    public Parcour(
            final int id,
            final String gid,
            final String name,
            final int anzahl_ziele,
            final String strasse,
            final String plz,
            final String ort,
            final String gps_lat_koordinaten,
            final String gps_lon_koordinaten,
            final String anmerkung,
            final int standard,
            final long zeitstempel) {
        this.id = id;
        this.gid = gid;
        this.name = name;
        this.anzahl_ziele = anzahl_ziele;
        this.strasse = strasse;
        this.plz = plz;
        this.ort = ort;
        this.gps_lat_koordinaten = gps_lat_koordinaten;
        this.gps_lon_koordinaten = gps_lon_koordinaten;
        this.anmerkung = anmerkung;
        this.standard = standard;
        this.zeitstempel = zeitstempel;
    }

    public long getID(){
        return id;
    }
        public void setID(final long id){
        this.id = id;
    }
    
    public String getGID(){
        return gid;
    }
    public void setGID(final String gid){
        this.gid = gid;
    }
    
    public String getName(){
        return name;
    }
    public void setName(final String name){
        this.name = name;
    }
    
    public int getAnzahlZiele(){
        return anzahl_ziele;
    }
    public void setAnzahlZiele(final int anzahl_ziele){
        this.anzahl_ziele = anzahl_ziele;
    }
    
    public String getStrasse(){
        return strasse;
    }
    public void setStrasse(final String strasse){
        this.strasse = strasse;
    }
    
    public String getPLZ(){
        return plz;
    }       
    public void setPLZ(final String plz){
        this.plz = plz;
    }

    public String getOrt(){
        return ort;
    }
    public void setOrt(final String ort){
        this.ort = ort;
    }
    
    public String getGPS_Lat_Koordinaten(){
        return gps_lat_koordinaten;
    }
    public void setGPS_Lat_Koordinaten(final String gps_lat_koordinaten){
        this.gps_lat_koordinaten = gps_lat_koordinaten;
    }        
    
    public String getGPS_Lon_Koordinaten(){
        return gps_lon_koordinaten;
    }
    public void setGPS_Lon_Koordinaten(final String gps_lon_koordinaten){
        this.gps_lon_koordinaten = gps_lon_koordinaten;
    }
    
    public String getAnmerkung(){
        return anmerkung;
    }
    public void setAnmerkung(final String anmerkung){
        this.anmerkung = anmerkung;
    }
    
    public int getStandard(){
        return standard;
    }
    public void setStandard(final int standard){
        this.standard = standard;
    }
    
    public long getZeitstempel(){
        return zeitstempel;
    }
    public void setZeitstempel(final long zeitstempel){
        this.zeitstempel = zeitstempel;
    }
 
    public String toString() {
        String toString = "table=parcour";
            toString += "&" + ID + "=" + String.valueOf(getID());
            toString += "&" + ANZAHL_ZIELE + "=" + String.valueOf(getAnzahlZiele());
            toString += "&" + STANDARD + "=" + String.valueOf(getStandard());
            toString += "&" + ZEITSTEMPEL + "=" + String.valueOf(getZeitstempel());
            if (!getGID().equals(""))                 toString += "&" + GID + "=" + getGID();
            if (!getName().equals(""))                toString += "&" + NAME + "=" + getName();
            if (!getStrasse().equals(""))             toString += "&" + STRASSE + "=" + getStrasse();
            if (!getPLZ().equals(""))                 toString += "&" + PLZ + "=" + getPLZ();
            if (!getOrt().equals(""))                 toString += "&" + ORT + "=" + getOrt();
            if (!getGPS_Lat_Koordinaten().equals("")) toString += "&" + GPS_LAT_KOORDINATEN + "=" + getGPS_Lat_Koordinaten();
            if (!getGPS_Lon_Koordinaten().equals("")) toString += "&" + GPS_LON_KOORDINATEN + "=" + getGPS_Lon_Koordinaten();
            if (!getAnmerkung().equals(""))           toString += "&" + ANMERKUNG + "=" + getAnmerkung();
        System.out.println("toString(): toString - " + toString);
        return toString;
    }
}
