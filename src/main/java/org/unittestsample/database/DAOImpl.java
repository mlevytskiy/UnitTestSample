package org.unittestsample.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vadym on 12/1/14.
 */
public class DAOImpl implements DAO {
    private static final String TAG = DAOImpl.class.getSimpleName();
    
    private Context context = null;
    private DBHelper dbHelper;

    public static DAO getInstance(Context context) {
        LazySingletonHolder.transactionImpl.context = context;
        LazySingletonHolder.transactionImpl.dbHelper = new DBHelper(context);
        return LazySingletonHolder.transactionImpl;
    }

    private static class LazySingletonHolder {
        static final DAOImpl transactionImpl = new DAOImpl();
    }

    private DAOImpl() {}

    public void save(Transaction transaction) throws PersistException {
        Transaction oldTransaction = getTransactionById(transaction.getTransactionId());
        if (oldTransaction == null) {
            saveTransactionAndEchipTables(transaction);
        } else {
            updateTransactionAndEchipTables(transaction, oldTransaction.getTransactionId());
        }
    }

    private void saveTransactionAndEchipTables(Transaction transaction) throws PersistException {
        saveTransaction(transaction);
        EchipData echipData = transaction.getEchipData();
        if (echipData != null) {
            saveEchipData(transaction.getEchipData(), transaction.getTransactionId());
        }
    }

    private void updateTransactionAndEchipTables(Transaction transaction, String oldTransactionId) throws PersistException {
        updateTransaction(transaction, oldTransactionId);
        EchipData echipData = transaction.getEchipData();
        if (echipData != null) {
            updateEchipData(transaction.getEchipData(), transaction.getTransactionId());
        }
    }

    //////////// TRANSACTION METHODS ////////////
    private void saveTransaction(Transaction transaction) throws PersistException {
        Log.d(TAG, "saving basic transaction data");
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        if (sqLiteDatabase.insert(DBHelper.DB_TABLE_TRANSACTIONS, null, prepareTransactionValues(transaction)) == -1) {
            throw new PersistException("Transaction was not saved");
        }

        sqLiteDatabase.close();
        Log.d(TAG, "basic data saved");
    }

    private void updateTransaction(Transaction transaction, String oldTransactionId) throws PersistException {
        Log.d(TAG, "updating basic transaction data");
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        if (sqLiteDatabase.update(DBHelper.DB_TABLE_TRANSACTIONS, prepareTransactionValues(transaction),
                DBHelper.TRANSACTION_ID + "=?", new String[]{oldTransactionId}) == -1) {
            throw new PersistException("Transaction was not updated");
        }

        sqLiteDatabase.close();
        Log.d(TAG, "basic data updated");
    }

    private ContentValues prepareTransactionValues(Transaction transaction) {
        String transactionId = transaction.getTransactionId();
        Transaction.TransactionType type = transaction.getTransactionType();
        int twoSides = transaction.isBothSides() ? 1 : 0;
        int twoDocs = transaction.isSecondaryDocument() ? 1 : 0;
        String consumerRef = transaction.getConsumerReference();
        String transactionRef = transaction.getTransactionReference();
        String front = transaction.getFrontPicturePath();
        String back = transaction.getBackPicturePath();
        String second = transaction.getSecondaryPicturePath();
        String[] facesArray = transaction.getFacesPath().toArray(new String[transaction.getFacesPath().size()]);
        String faces =  TextUtils.join("|", facesArray);

        ContentValues values = new ContentValues();
        values.put(DBHelper.TRANSACTION_ID, transactionId);
        values.put(DBHelper.TRANSACTIONS_TYPE, type.toString());
        values.put(DBHelper.TRANSACTIONS_TWO_SIDES, twoSides);
        values.put(DBHelper.TRANSACTIONS_TWO_DOCS, twoDocs);
        values.put(DBHelper.TRANSACTIONS_CONSUMER_REF, consumerRef);
        values.put(DBHelper.TRANSACTIONS_TRANSACTION_REF, transactionRef);
        values.put(DBHelper.TRANSACTIONS_TRANSACTION_FRONT, front);
        values.put(DBHelper.TRANSACTIONS_TRANSACTION_BACK, back);
        values.put(DBHelper.TRANSACTIONS_TRANSACTION_SECOND, second);
        values.put(DBHelper.TRANSACTIONS_TRANSACTION_FACES, faces);

        return values;
    }


    //////////// ECHIP METHODS ////////////
    private void saveEchipData(EchipData echipData, String transactionId) throws PersistException {
        Log.d(TAG, "saving echip data");
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        if (sqLiteDatabase.insert(DBHelper.DB_TABLE_ECHIP, null, prepareEchipValues(echipData, transactionId)) == -1) {
            throw new PersistException("Echip data was not saved");
        }

        sqLiteDatabase.close();
        Log.d(TAG, "echip data saved");
    }

    private void updateEchipData(EchipData echipData, String transactionId) throws PersistException {
        Log.d(TAG, "updating echip data");
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        if (sqLiteDatabase.update(DBHelper.DB_TABLE_ECHIP, prepareEchipValues(echipData, transactionId),
                DBHelper.TRANSACTION_ID + "=?", new String[]{transactionId}) == -1) {
            throw new PersistException("Echip data was not saved");
        }

        sqLiteDatabase.close();
        Log.d(TAG, "echip data updated");
    }

    private ContentValues prepareEchipValues(EchipData echipData, String transactionId) {
        ContentValues values = new ContentValues();
        String filename = "test";
        values.put(DBHelper.ECHIP_DATE_OF_BIRTH, echipData.getDateOfBirth());
        values.put(DBHelper.ECHIP_DATE_OF_EXPIRATION, echipData.getDateOfExpiration());
        values.put(DBHelper.ECHIP_DOCUMENT_NUMBER, echipData.getDocumentNumber());
        values.put(DBHelper.ECHIP_GENDER, echipData.getGender());
        values.put(DBHelper.ECHIP_ISSUING_ORGANIZATION, echipData.getIssuingOrganization());
        values.put(DBHelper.ECHIP_NAME_OF_HOLDER, echipData.getNameOfHolder());
        values.put(DBHelper.ECHIP_NATIONALITY, echipData.getNationality());
        values.put(DBHelper.ECHIP_IMG_NAME, filename);
        values.put(DBHelper.ECHIP_OPTIONAL_DATA, echipData.getOptionalData());
        values.put(DBHelper.TRANSACTION_ID, transactionId);

        return values;

    }

    public List<Transaction> getAllTransactions() {
        Log.d(TAG, "About to retrieve all offline transactions");
        List<Transaction> transactions = new ArrayList<Transaction>();
        if (dbHelper != null) {
            SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

            String query = "SELECT " + DBHelper.getAllTransactionColumns() +
                    " FROM " + DBHelper.DB_TABLE_TRANSACTIONS +
                    " LEFT JOIN " + DBHelper.DB_TABLE_ECHIP +
                    " ON " + DBHelper.DB_TABLE_TRANSACTIONS + "." + DBHelper.TRANSACTION_ID +
                    " = " + DBHelper.DB_TABLE_ECHIP + "." + DBHelper.TRANSACTION_ID;


            Cursor cursor = sqLiteDatabase.rawQuery(query, null);

            int keyTransactionId = cursor.getColumnIndex(DBHelper.TRANSACTION_ID);
            int keyType = cursor.getColumnIndex(DBHelper.TRANSACTIONS_TYPE);
            int keyTwoSides = cursor.getColumnIndex(DBHelper.TRANSACTIONS_TWO_SIDES);
            int keyTwoDocs = cursor.getColumnIndex(DBHelper.TRANSACTIONS_TWO_DOCS);
            int keyConsumerRef = cursor.getColumnIndex(DBHelper.TRANSACTIONS_CONSUMER_REF);
            int keyTransactionRef = cursor.getColumnIndex(DBHelper.TRANSACTIONS_TRANSACTION_REF);
            int keyPictureFront = cursor.getColumnIndex(DBHelper.TRANSACTIONS_TRANSACTION_FRONT);
            int keyPictureBack = cursor.getColumnIndex(DBHelper.TRANSACTIONS_TRANSACTION_BACK);
            int keyPictureSecond = cursor.getColumnIndex(DBHelper.TRANSACTIONS_TRANSACTION_SECOND);
            int keyFaces = cursor.getColumnIndex(DBHelper.TRANSACTIONS_TRANSACTION_FACES);

            if (cursor != null && cursor.moveToFirst()) {

                do {
                    Transaction transaction = new Transaction();
                    transaction.setTransactionId(cursor.getString(keyTransactionId));
                    transaction.setTransactionType(Transaction.TransactionType.valueOf(cursor.getString(keyType)));
                    transaction.setBothSides(cursor.getInt(keyTwoSides) != 0);
                    transaction.setSecondaryDocument(cursor.getInt(keyTwoDocs) != 0);
                    transaction.setConsumerReference(cursor.getString(keyConsumerRef));
                    transaction.setTransactionReference(cursor.getString(keyTransactionRef));
                    transaction.setFrontPicturePath(cursor.getString(keyPictureFront));
                    transaction.setBackPicturePath(cursor.getString(keyPictureBack));
                    transaction.setSecondaryPicturePath(cursor.getString(keyPictureSecond));
                    String[] facesArray = cursor.getString(keyFaces).split("\\|");
                    List<String> faces = Arrays.asList(facesArray);
                    transaction.setFacesPath(faces);
                    transactions.add(transaction);

                    EchipData echipData = getEchipDataFromCursor(cursor);
                    transaction.setEchipData(echipData);
                } while (cursor.moveToNext());
            }
        }
        return transactions;
    }

    private EchipData getEchipDataFromCursor(Cursor cursor) {
        String nameOfHolder = cursor.getString(cursor.getColumnIndex(DBHelper.ECHIP_NAME_OF_HOLDER));
        if (TextUtils.isEmpty(nameOfHolder)) {
            return null;
        }
        String documentNumber = cursor.getString(cursor.getColumnIndex(DBHelper.ECHIP_DOCUMENT_NUMBER));
        String issuingOrganization = cursor.getString(cursor.getColumnIndex(DBHelper.ECHIP_ISSUING_ORGANIZATION));
        String nationality = cursor.getString(cursor.getColumnIndex(DBHelper.ECHIP_NATIONALITY));
        String dateOfBirth = cursor.getString(cursor.getColumnIndex(DBHelper.ECHIP_DATE_OF_BIRTH));
        String gender = cursor.getString(cursor.getColumnIndex(DBHelper.ECHIP_GENDER));
        String dateOfExpiration = cursor.getString(cursor.getColumnIndex(DBHelper.ECHIP_DATE_OF_EXPIRATION));
        String filename = cursor.getString(cursor.getColumnIndex(DBHelper.ECHIP_IMG_NAME));
        String optionalData = cursor.getString(cursor.getColumnIndex(DBHelper.ECHIP_OPTIONAL_DATA));

        EchipData echipData = new EchipData(nameOfHolder,
                documentNumber,
                issuingOrganization,
                nationality,
                dateOfBirth,
                gender,
                dateOfExpiration,
                filename,
                optionalData);
        return echipData;
    }

    public Transaction getTransactionById(String transactionId) {
        if (dbHelper != null) {
            SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

            String query = "SELECT " + DBHelper.getAllTransactionColumns() +
                    " FROM " + DBHelper.DB_TABLE_TRANSACTIONS +
                    " LEFT JOIN " + DBHelper.DB_TABLE_ECHIP +
                    " ON " + DBHelper.DB_TABLE_TRANSACTIONS + "." + DBHelper.TRANSACTION_ID +
                    " = " + DBHelper.DB_TABLE_ECHIP + "." + DBHelper.TRANSACTION_ID
                    + " WHERE " + DBHelper.DB_TABLE_TRANSACTIONS + "." + DBHelper.TRANSACTION_ID + "='" + transactionId + "'";

            Cursor cursor = sqLiteDatabase.rawQuery(query, null);

            if (cursor == null) {
                return null;
            }

            int keyTransactionId = cursor.getColumnIndex(DBHelper.TRANSACTION_ID);
            int keyType = cursor.getColumnIndex(DBHelper.TRANSACTIONS_TYPE);
            int keyTwoSides = cursor.getColumnIndex(DBHelper.TRANSACTIONS_TWO_SIDES);
            int keyTwoDocs = cursor.getColumnIndex(DBHelper.TRANSACTIONS_TWO_DOCS);
            int keyConsumerRef = cursor.getColumnIndex(DBHelper.TRANSACTIONS_CONSUMER_REF);
            int keyTransactionRef = cursor.getColumnIndex(DBHelper.TRANSACTIONS_TRANSACTION_REF);
            int keyPictureFront = cursor.getColumnIndex(DBHelper.TRANSACTIONS_TRANSACTION_FRONT);
            int keyPictureBack = cursor.getColumnIndex(DBHelper.TRANSACTIONS_TRANSACTION_BACK);
            int keyPictureSecond = cursor.getColumnIndex(DBHelper.TRANSACTIONS_TRANSACTION_SECOND);
            int keyFaces = cursor.getColumnIndex(DBHelper.TRANSACTIONS_TRANSACTION_FACES);
            cursor.moveToFirst();

            if (cursor.getCount() != 0) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(cursor.getString(keyTransactionId));
                transaction.setTransactionType(Transaction.TransactionType.valueOf(cursor.getString(keyType)));
                transaction.setBothSides(cursor.getInt(keyTwoSides) != 0);
                transaction.setSecondaryDocument(cursor.getInt(keyTwoDocs) != 0);
                transaction.setConsumerReference(cursor.getString(keyConsumerRef));
                transaction.setTransactionReference(cursor.getString(keyTransactionRef));
                transaction.setFrontPicturePath(cursor.getString(keyPictureFront));
                transaction.setBackPicturePath(cursor.getString(keyPictureBack));
                transaction.setSecondaryPicturePath(cursor.getString(keyPictureSecond));
                String[] facesArray = cursor.getString(keyFaces).split("\\|");
                List<String> faces = Arrays.asList(facesArray);
                transaction.setFacesPath(faces);

                EchipData echipData = getEchipDataFromCursor(cursor);
                transaction.setEchipData(echipData);

                return transaction;
            }
        }
        return null;
    }

    public Transaction removeTransaction(String transactionId) {
        if (dbHelper != null) {
            Transaction result = getTransactionById(transactionId);
            if (result != null && removeTransactionData(transactionId)) {
                return  result;
            }
        }
        return null;
    }

    private boolean removeTransactionData(String transactionId) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        boolean isDeleted = sqLiteDatabase.delete(DBHelper.DB_TABLE_TRANSACTIONS, DBHelper.TRANSACTION_ID + "='" + transactionId + "'", null) > 0;
        boolean isDeleted2 = sqLiteDatabase.delete(DBHelper.DB_TABLE_ECHIP, DBHelper.TRANSACTION_ID + "='" + transactionId + "'", null) > 0;
        sqLiteDatabase.close();
        return isDeleted && isDeleted2;
    }
}
