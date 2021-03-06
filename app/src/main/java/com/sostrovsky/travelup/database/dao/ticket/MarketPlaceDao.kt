package com.sostrovsky.travelup.database.dao.ticket

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sostrovsky.travelup.database.entities.ticket.MarketPlace

/**
 * Author: Sergey Ostrovsky
 * Date: 15.09.20
 * Email: sergey.ostrovsky.it.dev@gmail.com
 */
@Dao
interface MarketPlaceDao {
    @Query("SELECT COUNT(*) FROM market_place")
    fun checkIfEmpty(): Int

    @Query("SELECT * FROM market_place")
    fun getAll(): List<MarketPlace>

    @Query("SELECT id FROM market_place WHERE code=:code AND name=:name")
    fun getId(code: String, name: String): Int

    @Query("SELECT * FROM market_place WHERE id=:id")
    fun getById(id: Int): MarketPlace

    @Query("SELECT id FROM market_place WHERE name=:name")
    fun getIdByName(name: String): Int

    @Insert
    fun insert(marketPlace: MarketPlace): Long
}