package com.example.rennshukun_compose.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rennshukun_compose.data.room.dao.MessageListDao
import com.example.rennshukun_compose.data.room.entity.MessageEntity

@Database(
    entities =
        [
            MessageEntity::class,
        ], version = 1, exportSchema = false
)
@TypeConverters()
abstract class MainDatabase : RoomDatabase() {

    abstract fun messageListDao(): MessageListDao

    companion object {
        @Volatile
        private var INSTANCE: MainDatabase? = null

        fun getDatabase(context: Context): MainDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java,
                    "main_database"
                )
                    //.fallbackToDestructiveMigration() // TODO: マイグレーションの時に追加必要(旧データ保存必要ないの場合)
                    //.addMigrations(DemoDatabase.MIGRATION_1_to_2) // TODO: マイグレーションの時に追加必要
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // TODO : マイグレーションの時に追加必要
//        val MIGRATION_1_to_2 = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                // マイグレーション処理
//            }
//        }
    }
}