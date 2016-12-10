package com.example.rene.myarrow.server.uptime;

/**
 * 
 * @author René Düber, 2016
 * 
 */
public class upTime implements upTimeColumns {
    private int id;
    private long starttime;
    private long endtime;

    public upTime() { }
  
    public upTime(final int id, final long starttime, final long endtime) {
        this.id = id;
        this.starttime = starttime;
        this.endtime = endtime;
    }

    public int getID() {
        return id;
    }

    public void setID(final int id) {
        this.id = id;
    }

    public long getStartTime() {
        return starttime;
    }

    public void setStartTime(final long starttime) {
        this.starttime = starttime;
    }

    public long getEndTime() {
        return endtime;
    }

    public void setEndTime(final long endtime) {
        this.endtime = endtime;
    }

    @Override
    public String toString() {
        return "upTime [" 
                + ID + "=" + String.valueOf(id) + ", "
                + STARTTIME + "=" + String.valueOf(starttime) + ", "
                + ENDTIME + "=" + String.valueOf(endtime)
            + "]";
    }
}