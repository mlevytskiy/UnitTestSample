package org.unittestsample.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by vadym on 12/1/14.
 */
public class DBHelper extends SQLiteOpenHelper implements BaseColumns {

    private static final String DB_NAME = "paycasso.db";
    public static final String DB_TABLE_TRANSACTIONS = "transactions";
    static final String DB_TABLE_ECHIP = "echip";

    private static final int    DB_VERSION = 2;

    static final String TRANSACTION_ID = "transaction_id";
    static final String TRANSACTIONS_TYPE = "transaction_type";
    static final String TRANSACTIONS_TWO_SIDES = "is_two_sides";
    static final String TRANSACTIONS_TWO_DOCS = "is_two_docs";
    static final String TRANSACTIONS_CONSUMER_REF = "consumer_ref";
    static final String TRANSACTIONS_TRANSACTION_REF = "transaction_ref";
    static final String TRANSACTIONS_TRANSACTION_FRONT = "front_picture";
    static final String TRANSACTIONS_TRANSACTION_BACK = "back_picture";
    static final String TRANSACTIONS_TRANSACTION_SECOND = "second_picture";
    static final String TRANSACTIONS_TRANSACTION_FACES = "faces";

    static final String ECHIP_NAME_OF_HOLDER = "name_of_holder";
    static final String ECHIP_DOCUMENT_NUMBER = "document_number";
    static final String ECHIP_ISSUING_ORGANIZATION = "issuing_organization";
    static final String ECHIP_NATIONALITY = "nationality";
    static final String ECHIP_DATE_OF_BIRTH = "date_of_birth";
    static final String ECHIP_DATE_OF_EXPIRATION = "date_of_expiration";
    static final String ECHIP_GENDER = "gender";
    static final String ECHIP_OPTIONAL_DATA = "optional_data";
    static final String ECHIP_IMG_NAME = "img_name";
    static final String INSERT_TS = "insert_ts";


    public static final String TRANSACTION_TABLE_CREATE_SCRIPT = "create table " +
            DB_TABLE_TRANSACTIONS + " (" + BaseColumns._ID +
            " integer primary key autoincrement, " +
            TRANSACTION_ID + " text, " +
            TRANSACTIONS_TYPE + " text not null, " +
            TRANSACTIONS_TWO_SIDES + " integer, " +
            TRANSACTIONS_TWO_DOCS + " integer, " +
            TRANSACTIONS_CONSUMER_REF + " text, " +
            TRANSACTIONS_TRANSACTION_REF + " text, " +
            TRANSACTIONS_TRANSACTION_FRONT + " text, " +
            TRANSACTIONS_TRANSACTION_BACK + " text, " +
            TRANSACTIONS_TRANSACTION_SECOND + " text, " +
            TRANSACTIONS_TRANSACTION_FACES + " text);";

    private static final String ECHIP_TABLE_CREATE_SCRIPT = "create table " +
            DB_TABLE_ECHIP + " (" +
            BaseColumns._ID + " integer primary key autoincrement, " +
            TRANSACTION_ID + " text, " +
            ECHIP_NAME_OF_HOLDER + " text, " +
            ECHIP_DOCUMENT_NUMBER + " text, " +
            ECHIP_ISSUING_ORGANIZATION + " text, " +
            ECHIP_NATIONALITY + " text, " +
            ECHIP_DATE_OF_BIRTH + " text, " +
            ECHIP_DATE_OF_EXPIRATION + " text, " +
            ECHIP_OPTIONAL_DATA + " text, " +
            INSERT_TS + " text, " +
            ECHIP_GENDER + " text, " +
            ECHIP_IMG_NAME + " text, "
            + "FOREIGN KEY" + "(" + TRANSACTION_ID + ")" + " REFERENCES "
            + DB_TABLE_TRANSACTIONS + "(" + TRANSACTION_ID + "));";


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TRANSACTION_TABLE_CREATE_SCRIPT);
        db.execSQL(ECHIP_TABLE_CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 2 && oldVersion < 2) {
            db.execSQL(ECHIP_TABLE_CREATE_SCRIPT);
        }
    }

    static String getAllTransactionColumns() {
        return DB_TABLE_TRANSACTIONS + "." + TRANSACTION_ID + ", "
                + DB_TABLE_TRANSACTIONS + "." + TRANSACTIONS_TWO_DOCS + ", "
                + DB_TABLE_TRANSACTIONS + "." + TRANSACTIONS_TYPE + ", "
                + DB_TABLE_TRANSACTIONS + "." + TRANSACTIONS_TWO_SIDES + ", "
                + DB_TABLE_TRANSACTIONS + "." + TRANSACTIONS_CONSUMER_REF + ", "
                + DB_TABLE_TRANSACTIONS + "." + TRANSACTIONS_TRANSACTION_REF + ", "
                + DB_TABLE_TRANSACTIONS + "." + TRANSACTIONS_TRANSACTION_FRONT + ", "
                + DB_TABLE_TRANSACTIONS + "." + TRANSACTIONS_TRANSACTION_BACK + ", "
                + DB_TABLE_TRANSACTIONS + "." + TRANSACTIONS_TRANSACTION_SECOND + ", "
                + DB_TABLE_TRANSACTIONS + "." + TRANSACTIONS_TRANSACTION_FACES + ", "
                + DB_TABLE_ECHIP + "." + ECHIP_DATE_OF_EXPIRATION + ", "
                + DB_TABLE_ECHIP + "." + ECHIP_NAME_OF_HOLDER + ", "
                + DB_TABLE_ECHIP + "." + ECHIP_DOCUMENT_NUMBER + ", "
                + DB_TABLE_ECHIP + "." + ECHIP_ISSUING_ORGANIZATION + ", "
                + DB_TABLE_ECHIP + "." + ECHIP_NATIONALITY + ", "
                + DB_TABLE_ECHIP + "." + ECHIP_DATE_OF_BIRTH + ", "
                + DB_TABLE_ECHIP + "." + ECHIP_GENDER + ", "
                + DB_TABLE_ECHIP + "." + ECHIP_IMG_NAME + ", "
                + DB_TABLE_ECHIP + "." + ECHIP_OPTIONAL_DATA;
    }
}
