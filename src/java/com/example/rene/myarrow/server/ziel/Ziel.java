package com.example.rene.myarrow.server.ziel;

/**
 * Created by nily on 14.12.15.
 */
public class Ziel implements ZielColumns {
    private int id;
    private String gid;
    private String parcourgid;
    private int nummer;
    private String name;
    private String gps_lat_koordinaten;
    private String gps_lon_koordinaten;
    private String dateiname;
    private long zeitstempel;

    public Ziel() { }
  
    public Ziel(
            final int id,
            final String gid,
            final String parcourgid,
            final int nummer,
            final String name,
            final String gps_lat_koordinaten,
            final String gps_lon_koordinaten,
            final String dateiname,
            final long zeitstempel) {
        this.id = id;
        this.gid = gid;
        this.parcourgid = parcourgid;
        this.nummer = nummer;
        this.name = name;
        this.gps_lat_koordinaten = gps_lat_koordinaten;
        this.gps_lon_koordinaten = gps_lon_koordinaten;
        this.dateiname = dateiname;
        this.zeitstempel = zeitstempel;
    }

    public int getID(){
        return id;
    }
    public void setID(final int id){
        this.id = id;
    }

    public String getGID(){
        return gid;
    }
    public void setGID(final String gid){
        this.gid = gid;
    }
    
    public String getParcourGID(){
        return parcourgid;
    }
    public void setParcourGID(final String parcourgid){
            this.parcourgid = parcourgid;
    }
    
    public int getNummer(){
        return nummer;
    }
    public void setNummer(final int nummer){
        this.nummer = nummer;
    }
    
    public String getName(){
        return name;
    }
    public void setName(final String name){
            this.name = name;
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
    
    public String getDateiname(){
        return dateiname;
    }            
    public void setDateiname(final String dateiname){
        this.dateiname = dateiname;
    }
    
    public long getZeitstempel(){
        return zeitstempel;
    }
    public void setZeitstempel(final long zeitstempel){
        this.zeitstempel = zeitstempel;
    }

    @Override
    public String toString() {
        String toString = "table=ziel" +
            "&" + ID                  + "=" + getID() +
            "&" + GID                 + "=" + getGID() +
            "&" + PARCOURGID          + "=" + getParcourGID() +
            "&" + NUMMER              + "=" + String.valueOf(getNummer()) +
            "&" + NAME                + "=" + getName() +
            "&" + ZEITSTEMPEL         + "=" + String.valueOf(getZeitstempel());
        if (!getDateiname().equals(""))           toString += "&" + DATEINAME + "=" + getDateiname();
        if (!getGPS_Lat_Koordinaten().equals("")) toString += "&" + GPS_LAT_KOORDINATEN + "=" + getGPS_Lat_Koordinaten();
        if (!getGPS_Lon_Koordinaten().equals("")) toString += "&" + GPS_LON_KOORDINATEN + "=" + getGPS_Lon_Koordinaten();
        return toString;
    }
}
