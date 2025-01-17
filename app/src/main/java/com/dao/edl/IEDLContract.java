package com.dao.edl;

/**
 * Created by Administrateur on 20/10/2017.
 */
public class IEDLContract {
    public static final String TABLE_EDLS = "etat_des_lieux";
    public static final String COLUMN_ID_EDLS = "id";
    public static final String COLUMN_DATE_EDLS= "date";
    public static final String COLUMN_ID_LOCATION_EDLS ="id_location";

    public static final int NUM_COL_ID_EDL = 0;
    public static final int NUM_COL_DATE_EDL = 1;
    public static final int NUM_ID_LOCATION_EDL = 2;

    public static final String CREATE_TABLE_EDLS= "create table "
            + TABLE_EDLS + "(" + COLUMN_ID_EDLS
            + " integer primary key autoincrement, " + COLUMN_DATE_EDLS
            + " text not null,"+ COLUMN_ID_LOCATION_EDLS
            + " integer not null"
            + ");";
}

