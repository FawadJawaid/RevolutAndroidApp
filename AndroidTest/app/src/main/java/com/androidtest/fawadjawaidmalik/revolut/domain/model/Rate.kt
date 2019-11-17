package com.androidtest.fawadjawaidmalik.revolut.domain.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * Author: Muhammad Fawad Jawaid Malik
 * This is also the data class for database which creates the Entity(Table) in the database.
 */
@Entity(tableName = "rates")
class Rate {
    @PrimaryKey
    var key: String
    var name: String
    var multiplier: Double
    var position: Int = 0
    @Ignore
    var value: Double? = null

    constructor() : this("")

    @Ignore
    constructor(key: String, name: String = "", multiplier: Double = 1.0, position: Int = 0) {
        this.key = key
        this.name = name
        this.multiplier = multiplier
        this.position = position
    }

    override fun toString(): String {
        return "Rate(key='$key', rateName=$name, multiplier=$multiplier), value=$value, position=$position"
    }


    // Used for DiffUtils. Indicates whether adapter should be changed.
    fun contentSame(another: Rate): Boolean {
        return key == another.key && name == another.name && multiplier == another.multiplier
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Rate

        if (key != other.key) return false

        return true
    }

    override fun hashCode(): Int {
        return key.hashCode()
    }
}