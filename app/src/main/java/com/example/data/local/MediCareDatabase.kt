package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        MedicineEntity::class,
        CartItemEntity::class,
        WishlistItemEntity::class,
        PrescriptionEntity::class,
        OrderEntity::class,
        AddressEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MediCareDatabase : RoomDatabase() {

    abstract fun dao(): MediCareDao

    companion object {
        @Volatile
        private var INSTANCE: MediCareDatabase? = null

        fun getInstance(context: Context): MediCareDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MediCareDatabase::class.java,
                    "medicare_pharmacy.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
