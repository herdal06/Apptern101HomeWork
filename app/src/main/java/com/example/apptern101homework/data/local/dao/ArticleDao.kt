package com.example.apptern101homework.data.local.dao

import androidx.room.*
import com.example.apptern101homework.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: ArticleEntity)

    @Delete
    suspend fun delete(article: ArticleEntity)

    @Query("SELECT * FROM articles")
    fun getAll(): Flow<List<ArticleEntity>>
}