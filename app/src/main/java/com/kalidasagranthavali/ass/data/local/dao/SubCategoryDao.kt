package com.kalidasagranthavali.ass.data.local.dao

import androidx.room.*
import com.kalidasagranthavali.ass.data.local.modals.SubCategory
import kotlinx.coroutines.flow.Flow

@Dao
abstract class SubCategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun insert(sub_category: List<SubCategory>)

    @Query("DELETE FROM sub_category;")
    protected abstract suspend fun delete()

    @Query("SELECT * FROM sub_category WHERE sub_category.name LIKE :query AND sub_category.cat_id=:id;")
    abstract fun getSubCategories(query: String, id: Int): Flow<List<SubCategory>>

    @Transaction
    open suspend fun insertCategory(sub_category: List<SubCategory>) {
        delete()
        insert(sub_category)
    }
}