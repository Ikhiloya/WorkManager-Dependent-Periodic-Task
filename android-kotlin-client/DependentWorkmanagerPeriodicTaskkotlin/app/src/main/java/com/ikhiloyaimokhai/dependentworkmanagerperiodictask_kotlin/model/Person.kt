package com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class Person(
    @Expose(serialize = false)
    @PrimaryKey val localId: Int?, // local Person id
    @Expose(serialize = false)
    var id: Long?,  // remote Person id
    @SerializedName("firstName")
    @Expose
    var firstName: String,
    @SerializedName("lastName")
    @Expose
    var lastName: String,
    @Expose(serialize = false)
    var state: String?
) {
    constructor(firstName: String, lastName: String, state: String?) : this(
        null,
        null,
        firstName,
        lastName,
        state
    )
}