package com.smarttask.data.remote.model.ResponseModel

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class TaskResponse(
    @SerializedName("tasks") var tasks: List<Tasks> = emptyList<Tasks>(),
) : BaseModel(), Serializable

data class Tasks(
    @SerializedName("id") var id: String = "",
    @SerializedName("TargetDate") var targetDate: String = "",
    @SerializedName("DueDate") var dueDate: String? = null,
    @SerializedName("Title") var title: String = "",
    @SerializedName("Description") var description: String = "",
    @SerializedName("Priority") var priority: Int = 0,
    var status: Int = 0,
    var formatedDueDate: String = "",
    var daysLeft: String = "",
    var unresolvedComment: String = ""

) : Serializable



