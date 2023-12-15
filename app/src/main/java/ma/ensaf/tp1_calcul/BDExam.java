package ma.ensaf.tp1_calcul;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import ma.ensaf.tp1_calcul.data.User;

public class BDExam extends SQLiteOpenHelper {

        private static final String DB_NAME = "calcul_db";
        private static final int DB_VERSION = 1;
        private static final String USERS_TABLE_NAME = "users";
        private static final String USER_ID = "id";
        private static final String NAME = "name";
        private static final String FIRST_NAME = "first_name";
        private static final String EMAIL = "email";
        private static final String PASSWORD = "password";
        private static final String OLD_PASSWORD = "old_password";
        private static final String ROLE = "role";

        public BDExam(Context mContext) {
                super(mContext, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
                String createTableUser = "CREATE TABLE " + USERS_TABLE_NAME + "(" +
                        USER_ID + " INTEGER PRIMARY KEY," +
                        NAME + " VARCHAR(50)," +
                        FIRST_NAME + " VARCHAR(50)," +
                        EMAIL + " VARCHAR(100)," +
                        PASSWORD + " VARCHAR(20)," +
                        OLD_PASSWORD + " VARCHAR(200)," +
                        ROLE + " VARCHAR(20)" +
                        ")";
                db.execSQL(createTableUser);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME);
                onCreate(db);
        }

        @SuppressLint("Range")
        public String findRole(String email, String password) {
                SQLiteDatabase db = this.getReadableDatabase();
                String whereClause = EMAIL + " = ? AND " + PASSWORD + " = ?";
                String[] whereArgs = {email, password};
                Cursor cursor = db.query(USERS_TABLE_NAME, new String[]{ROLE}, whereClause, whereArgs, null, null, null);
                String role = "null";
                if (cursor.moveToFirst()) {
                        role = cursor.getString(cursor.getColumnIndex(ROLE));
                }
                cursor.close();
                db.close();
                return role;
        }

        public boolean addAuth(User user) {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(NAME, user.getName());
                values.put(FIRST_NAME, user.getFirst_name());
                values.put(EMAIL, user.getEmail());
                values.put(PASSWORD, user.getPassword());
                values.put(OLD_PASSWORD, user.getOldPassword());
                values.put(ROLE, user.getRole());
                long result = db.insert(USERS_TABLE_NAME, null, values);
                db.close();
                return result != -1;
        }

        public boolean removeUser(String email, String password) {
                SQLiteDatabase db = this.getWritableDatabase();
                String whereClause = EMAIL + " = ? AND " + PASSWORD + " = ?";
                String[] whereArgs = {email, password};
                int result = db.delete(USERS_TABLE_NAME, whereClause, whereArgs);
                db.close();
                return result > 0;
        }

        public Cursor readAllData() {
                SQLiteDatabase db = this.getReadableDatabase();
                String query = "SELECT * FROM " + USERS_TABLE_NAME;
                return db.rawQuery(query, null);
        }
}
