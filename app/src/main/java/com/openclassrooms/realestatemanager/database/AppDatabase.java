package com.openclassrooms.realestatemanager.database;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Context;
import androidx.annotation.NonNull;

import com.openclassrooms.realestatemanager.database.Dao.HouseSellerDAO;
import com.openclassrooms.realestatemanager.database.Dao.InterestPointDAO;
import com.openclassrooms.realestatemanager.database.Dao.MediaDAO;
import com.openclassrooms.realestatemanager.database.Dao.PropertyDAO;
import com.openclassrooms.realestatemanager.models.HouseSeller;
import com.openclassrooms.realestatemanager.models.InterestPoint;
import com.openclassrooms.realestatemanager.models.Media;
import com.openclassrooms.realestatemanager.models.Property;

@Database(entities = {Property.class, Media.class, InterestPoint.class, HouseSeller.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile AppDatabase INSTANCE;

    // --- DAO ---
    public abstract PropertyDAO propertyDAO();
    public abstract MediaDAO mediaDAO();
    public abstract InterestPointDAO interestPointDAO();
    public abstract HouseSellerDAO houseSellerDAO();

    // --- INSTANCE ---
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "MyDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // ---

    private static Callback prepopulateDatabase(){
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
/*
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", 1);
                contentValues.put("username", "Philippe");
                contentValues.put("urlPicture", "https://oc-user.imgix.net/users/avatars/15175844164713_frame_523.jpg?auto=compress,format&q=80&h=100&dpr=2");

                db.insert("User", OnConflictStrategy.IGNORE, contentValues);

 */
            }
        };
    }
}
