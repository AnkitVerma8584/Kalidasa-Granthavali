package com.kalidasagranthavali.ass.data.local.dao

import androidx.room.*
import com.kalidasagranthavali.ass.data.local.modals.Files
import kotlinx.coroutines.flow.Flow

@Dao
abstract class FilesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun insert(files: List<Files>)

    @Query("DELETE FROM files;")
    protected abstract suspend fun delete()

    @Query("SELECT * FROM files WHERE files.name LIKE :query AND cat_id=:cat_id AND sub_cat_id=:sub_cat_id;")
    abstract fun getFiles(query: String, cat_id: Int, sub_cat_id: Int): Flow<List<Files>>

    @Query("SELECT * FROM files WHERE files.id=:id;")
    abstract fun getFileById(id: Int): Files


    @Transaction
    open suspend fun insertFiles(files: List<Files>) {
        delete()
        insert(files)
    }
}