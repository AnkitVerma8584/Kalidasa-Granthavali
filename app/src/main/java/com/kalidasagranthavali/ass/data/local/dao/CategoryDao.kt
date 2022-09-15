package com.kalidasagranthavali.ass.data.local.dao

import androidx.room.*
import com.kalidasagranthavali.ass.data.local.modals.Category
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun insert(category: List<Category>)


    @Query("SELECT COUNT(*) FROM category;")
    abstract fun getCategoryCount(): Flow<Int>

    @Query("DELETE FROM category;")
    protected abstract suspend fun delete()

    @Query("SELECT * FROM category WHERE category.name LIKE :query;")
    abstract fun getCategories(query: String): Flow<List<Category>>

    @Transaction
    open suspend fun insertCategory(category: List<Category>) {
        delete()
        insert(category)
    }
}