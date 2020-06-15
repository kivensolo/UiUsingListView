package com.kingz.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kingz.database.dao.AlbumDao
import com.kingz.database.dao.SongDao
import com.kingz.database.entity.BaseEntity
import com.kingz.database.entity.SongEntity

/**
 * @author zeke.wang
 * @date 2020/6/10
 * @maintainer zeke.wang
 * @desc: App抽象数据库层
 */
@Database(entities = [BaseEntity::class, SongEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getSongDao():SongDao

    abstract fun getAlbumDao(): AlbumDao

}