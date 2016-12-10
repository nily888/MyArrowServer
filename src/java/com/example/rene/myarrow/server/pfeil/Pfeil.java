package com.example.rene.myarrow.server.pfeil;

/**
 * Created by nily on 15.06.16
 */
public class Pfeil implements PfeilColumns{
    public int id;
    public String gid;
    public String name;
    public String dateiname;
    public int standard;
    public long zeitstempel;

    public Pfeil() { }
  
    public Pfeil(
            final int id,
            final String gid,
            final String name,
            final String dateiname,
            final int standard,
            final long zeitstempel) {
        this.id = id;
        this.gid = gid;
        this.name = name;
        this.dateiname = dateiname;
        this.standard = standard;
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
    
    public String getName(){
        return name;
    }
    public void setName(final String name){
            this.name = name;
    }
    
    public String getDateiname(){
        return dateiname;
    }            
    public void setDateiname(final String dateiname){
        this.dateiname = dateiname;
    }
    
    public int getStandard(){
        return standard;
    }
    public void setStandard(final int id){
        this.standard = standard;
    }

    public long getZeitstempel(){
        return zeitstempel;
    }
    public void setZeitstempel(final long zeitstempel){
        this.zeitstempel = zeitstempel;
    }
    
    public String toString() {
        String toString = "table=pfeil" +
            "&" + ID + "=" + String.valueOf(getID()) +
            "&" + GID + "=" + getGID() +
            "&" + NAME + "=" + getName() +
            "&" + STANDARD + "=" + getStandard() +
            "&" + ZEITSTEMPEL + "=" + getZeitstempel();
        if (!getDateiname().equals("")) toString = toString + "&" + DATEINAME + "=" + getDateiname();
        return toString;
    }
}