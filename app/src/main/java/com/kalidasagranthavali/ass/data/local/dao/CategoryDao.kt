package com.kalidasagranthavali.ass.data.local.dao

import androidx.room.*
import com.kalidasagranthavali.ass.data.local.modals.Category

@Dao
abstract class CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun insert(category: List<Category>)

    @Query("SELECT COUNT(*) FROM category;")
    abstract fun getCategoryCount(): Int

    @Query("DELETE FROM category;")
    protected abstract suspend fun delete()

    @Query("SELECT * FROM category;")
    abstract suspend fun getCategories(): List<Category>

    @Transaction
    open suspend fun insertCategory(category: List<Category>) {
        delete()
        insert(category)
    }
}