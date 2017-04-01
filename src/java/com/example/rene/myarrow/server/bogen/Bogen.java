package com.example.rene.myarrow.server.bogen;

/**
 * Created by nily on 15.06.16
 */
public class Bogen implements BogenColumns{
    public int id;
    public String gid;
    public String name;
    public String dateiname;
    public int standard;
    public long zeitstempel;
    public int transfered;

    public Bogen() { }
  
    public Bogen(
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
    public void setStandard(final int standard){
        this.standard = standard;
    }

    public long getZeitstempel(){
        return zeitstempel;
    }
    public void setZeitstempel(final long zeitstempel){
        this.zeitstempel = zeitstempel;
    }
 
    @Override
    public String toString() {
        String toString = "table=bogen";
        System.out.println("System: toString(): ID -          " + String.valueOf(getID()));
        System.out.println("System: toString(): GID -         " + getGID());
        System.out.println("System: toString(): NAME -        " + getName());
        System.out.println("System: toString(): Dateiname -   " + getDateiname());
        System.out.println("System: toString(): STANDARD -    " + String.valueOf(getStandard()));
        System.out.println("System: toString(): ZeitSetempl - " + String.valueOf(getZeitstempel()));
        toString += "&" + ID + "=" + String.valueOf(getID());
        if (!getGID().equals("")) toString += "&" + GID + "=" + getGID();
        if (!getName().equals("")) toString += "&" + NAME + "=" + getName();
        if (!getDateiname().equals("")) toString += "&" + DATEINAME   + "=" + getDateiname();
        toString += "&" + STANDARD    + "=" + String.valueOf(getStandard());
        toString += "&" + ZEITSTEMPEL + "=" + String.valueOf(getZeitstempel());
        return toString;
    }
}