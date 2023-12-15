package ma.ensaf.tp1_calcul;

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ma.ensaf.tp1_calcul.data.User

class CalculDatabase(mContext: Context) : SQLiteOpenHelper(
    mContext,
    DB_NAME,
    null,
    DB_VERSION
) {
    override fun onCreate(db: SQLiteDatabase?) {
        var createTableUser = """
            CREATE TABLE $USERS_TABLE_NAME(
            $USER_ID integer PRIMARY KEY,
            $NAME varchar(50),
            $FIRST_NAME varchar(50),
            $EMAIL varchar(100),
            $PASSWORD varchar(20),
            $OLD_PASSWORD varchar(200),
            $ROLE varchar(20)
            )
        """.trimIndent()

        db?.execSQL(createTableUser)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $USERS_TABLE_NAME")
        onCreate(db)
    }
    @SuppressLint("Range")
    fun findRole(email: String, password: String): String {
        val db = this.readableDatabase
        val whereClause = "$EMAIL = ? AND $PASSWORD = ?"
        val whereArgs = arrayOf(email, password)
        val cursor = db.query(USERS_TABLE_NAME, arrayOf(ROLE), whereClause, whereArgs, null, null, null)
        var role = "null"
        if (cursor.moveToFirst()) {
            role = cursor.getString(cursor.getColumnIndex(ROLE))
        }
        cursor.close()
        db.close()
        return role
    }
    fun addAuth(user : User) : Boolean{
        val db =this.writableDatabase
        val  values = ContentValues()
        values.put(NAME,user.name)
        values.put(FIRST_NAME,user.first_name)
        values.put(EMAIL,user.email)
        values.put(PASSWORD,user.password)
        values.put(OLD_PASSWORD,user.oldPassword)
        values.put(ROLE,user.role)
        val result = db.insert(USERS_TABLE_NAME,null,values).toInt()
        db.close()
        return result != -1
    }

    fun removeUser(email: String, password: String): Boolean {
        val db = this.writableDatabase
        val whereClause = "$EMAIL = ? AND $PASSWORD = ?"
        val whereArgs = arrayOf(email, password)
        val result = db.delete(USERS_TABLE_NAME, whereClause, whereArgs)
        db.close()
        return result > 0
    }
    fun readAllData(): Cursor? {
        val db = this.readableDatabase
        val query = """
        SELECT * FROM $USERS_TABLE_NAME
    """.trimIndent()
        var cursor: Cursor? = null
        if (db != null) {
            cursor = db.rawQuery(query, null)
        }
        return cursor
    }



    companion object {
        private val DB_NAME = "calcul_db"
        private val DB_VERSION = 1
        private val USERS_TABLE_NAME = "users"
        private val USER_ID = "id"
        private val NAME = "name"
        private val FIRST_NAME = "first_name"
        private val EMAIL = "email"
        private val PASSWORD = "password"
        private val OLD_PASSWORD = "old_password"
        private val ROLE = "role"
    }
}
