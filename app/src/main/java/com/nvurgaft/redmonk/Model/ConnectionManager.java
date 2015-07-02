package com.nvurgaft.redmonk.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Koby on 02-Jul-15.
 */
public class ConnectionManager {

    private static SqlAccess sqlAccess;

    public static SQLiteDatabase getConnection(Context context) {
        if (sqlAccess==null) {
            sqlAccess = new SqlAccess(context);
        }
        return sqlAccess.getWritableDatabase();
    }

    public static void closeConnection() {
        sqlAccess.close();
        sqlAccess = null;
    }

    public static SqlAccess getSqlAccess() {
        return sqlAccess;
    }
}
