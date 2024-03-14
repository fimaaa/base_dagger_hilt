package com.baseapp.local.member

import androidx.room.TypeConverter
import com.general.model.common.user.Member
import com.google.gson.Gson

class MemberConverter {
    @TypeConverter
    fun listMemberToJson(value: List<Member>): String =
        Gson().toJson(value)
}