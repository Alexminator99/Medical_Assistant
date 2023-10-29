package com.xeladevmobile.core.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [],
    version = 1,
    exportSchema = true,
)
abstract class MedicalDatabase : RoomDatabase() {
}