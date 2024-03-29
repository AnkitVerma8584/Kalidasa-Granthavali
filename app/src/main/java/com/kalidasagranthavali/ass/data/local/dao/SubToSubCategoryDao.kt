package com.kalidasagranthavali.ass.data.local.dao

import androidx.room.*
import com.kalidasagranthavali.ass.data.local.modals.SubToSubCategory

@Dao
abstract class SubToSubCategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun insert(sub_to_sub_category: List<SubToSubCategory>)

    @Query("DELETE FROM sub_to_sub_category;")
    protected abstract suspend fun delete()

    @Query("SELECT * FROM sub_to_sub_category WHERE sub_to_sub_category.cat_id=:catId AND sub_to_sub_category.sub_cat_id=:subCatId;")
    abstract suspend fun getSubToSubCategories(catId: Int, subCatId: Int): List<SubToSubCategory>

    @Query("SELECT COUNT(*) FROM sub_to_sub_category WHERE sub_to_sub_category.cat_id=:catId AND sub_to_sub_category.sub_cat_id=:subCatId;")
    abstract suspend fun getSubToSubCategoryCount(catId: Int, subCatId: Int): Int

    @Transaction
    open suspend fun insertSubToSubCategory(sub_to_sub_category: List<SubToSubCategory>) {
        delete()
        insert(sub_to_sub_category)
    }
}