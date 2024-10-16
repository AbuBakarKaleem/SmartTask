package com.smarttask.data.remote.model.ResponseModel

import java.io.Serializable

abstract class BaseModel : Serializable {
    var isEventNotConsumed = true
        get() {
            return if (field) {
                field = true
                true
            } else {
                field
            }
        }

}