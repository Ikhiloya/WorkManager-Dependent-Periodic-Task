package com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class Beneficiary(
    @Expose(serialize = false)
    @PrimaryKey val localId: Int?, //local Beneficiary id

    @SerializedName("firstName")
    @Expose
    var firstName: String,

    @SerializedName("lastName")
    @Expose
    var lastName: String,

    @SerializedName("relationship")
    @Expose
    var relationship: String,

    @Expose(serialize = false)
    var personLocalId: Int?, //local Person id

    @Expose(serialize = false)
    var id: Long?,   // remote Beneficiary id

    @SerializedName("personId")
    @Expose
    var personId: Long?, // remote Person id

    @Expose(serialize = false)
    var state: String?
) {
    constructor(
        personLocalId: Int?,
        firstName: String,
        lastName: String,
        relationship: String,
        state: String?
    ) : this(null, firstName, lastName, relationship, personLocalId, null, null, state)
}