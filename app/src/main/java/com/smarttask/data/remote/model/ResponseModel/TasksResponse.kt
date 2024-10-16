package com.smarttask.data.remote.model.ResponseModel

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class TaskResponse(
    @SerializedName("results") var tasks: List<Tasks>,
) : BaseModel(), Serializable

data class Tasks(
    @SerializedName("id") var id: String = "",
    @SerializedName("TargetDate") var targetDate: String = "",
    @SerializedName("DueDate") var dueDate: String = "",
    @SerializedName("Title") var title: String = "",
    @SerializedName("Description") var description: String = "",
    @SerializedName("Priority") var priorty: String = "",
    var status: String = "unresolved",
    var formatedDueDate: String = "",
    var daysLeft: String = ""

) : Serializable



