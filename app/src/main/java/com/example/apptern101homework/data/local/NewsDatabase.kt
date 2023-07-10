package com.example.apptern101homework.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.apptern101homework.data.local.dao.ArticleDao
import com.example.apptern101homework.data.local.entity.ArticleEntity

@Database(
    entities = [ArticleEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}