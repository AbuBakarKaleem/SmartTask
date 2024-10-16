package com.smarttask.data.remote.model

sealed class UIState {
    object InitialState : UIState()
    object LoadingState : UIState()
    object ContentState : UIState()
    class ErrorState(val message: String) : UIState()
}
