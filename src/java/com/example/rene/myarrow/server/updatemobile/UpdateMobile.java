package com.example.rene.myarrow.server.updatemobile;

import com.example.rene.myarrow.server.updatemobile.*;

/**
 * Created by René Düber on 15.06.16
 */
public class UpdateMobile implements UpdateMobileColumns{
    public int id;
    public int transfered;
    public String deviceid;
    public String tablename;
    public String fieldname;
    public String old_gid;
    public String new_gid;

    public UpdateMobile() { }
  
    public UpdateMobile(
            final int id,
            final String deviceid,
            final String tablename,
            final String fieldname,
            final String old_gid,
            final String new_gid) {
        this.id = id;
        this.deviceid = deviceid;
        this.tablename = tablename;
        this.fieldname = fieldname;
        this.old_gid = old_gid;
        this.new_gid = new_gid;
    }

    public int getID(){
        return id;
    }
    
    public void setID(final int id){
        this.id = id;
    }

    public String getDeviceID(){
        return deviceid;
    }
    public void setDeviceID(final String deviceid){
        this.deviceid = deviceid;
    }
    
    public String getTableName(){
        return tablename;
    }
    public void setTableName(final String tablename){
        this.tablename = tablename;
    }
    
    public String getFieldName(){
        return fieldname;
    }
    public void setFieldName(final String fieldname){
            this.fieldname = fieldname;
    }
    
    public String getoldGID(){
        return old_gid;
    }            
    public void setoldGID(final String old_gid){
        this.old_gid = old_gid;
    }
     
    public String getnewGID(){
        return new_gid;
    }
    public void setnewGID(final String new_gid){
        this.new_gid = new_gid;
    }
     
    public int getTransfered(){
        return transfered;
    }
    public void setTransfered(final int transfered){
        this.transfered = transfered;
    }
     
    @Override
    public String toString() {
        String toString = "table=updatemobile";
        toString += "&" + ID + "=" + String.valueOf(getID());
        if (!getDeviceID().equals("")) toString += "&" + DEVICEID + "=" + getDeviceID();
        if (!getTableName().equals("")) toString += "&" + TABLENAME + "=" + getTableName();
        if (!getFieldName().equals("")) toString += "&" + FIELDNAME + "=" + getFieldName();
        if (!getoldGID().equals("")) toString += "&" + OLD_GID   + "=" + getoldGID();
        if (!getnewGID().equals("")) toString += "&" + NEW_GID   + "=" + getnewGID();
        toString += "&" + TRANSFERED   + "=" + getTransfered();
        return toString;
    }
}