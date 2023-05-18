package com.example.bottomnavigation.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bottomnavigation.dao.PostDao
import com.example.bottomnavigation.entities.Posts

@Database(entities = [Posts::class], version = 2, exportSchema = false)
abstract class PostsDatabase : RoomDatabase() {

    companion object {
        private var postsDatabase: PostsDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): PostsDatabase {
            if (postsDatabase == null) {
                postsDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    PostsDatabase::class.java,
                    "new_posts.db" // change the database name here
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return postsDatabase!!
        }
    }

    abstract fun postDao(): PostDao
}
