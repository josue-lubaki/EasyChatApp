//package ca.josue_lubaki.chat.presentation
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.CoroutineDispatcher
//
///**
// * created by Josue Lubaki
// * date : 2023-12-18
// * version : 1.0.0
// */
//
//class ChatViewModel(
//    private val useCase: ChatUseCase,
//    private val dispatcher: CoroutineDispatcher,
//) : ViewModel() {
//
//    private val _state = MutableStateFlow<ChatState>(ChatState.Idle)
//    val state: StateFlow<ChatState> = _state.asStateFlow()
//
//    fun onEvent(event: ChatEvent) {
//        when (event) {
//            //==== if you need execute actions ====//
//            is ChatEvent.OnLoadData -> getAllData()
//        }
//    }
//
//    private fun getAllData() {
//        _state.value = ChatState.Loading
//        try {
//            viewModelScope.launch(dispatcher) {
//                //==== if your useCase is a flow ====/
//                /*
//                    useCase().collect { response ->
//                        when (response) {
//                            is ChatStatus.Success -> {
//                                _state.value = ChatState.Success(response.data)
//                            }
//
//                            is ChatStatus.Error -> {
//                                _state.value = ChatState.Error(response.error)
//                            }
//                        }
//                    }
//                */
//
//                //==== if it's not a flow ====//
//                /*
//                    when (val response = useCase()) {
//                        is ChatStatus.Success -> {
//                            _state.value = ChatState.Success(response.data)
//                        }
//                        is ChatStatus.Error -> {
//                            _state.value = ChatState.Error(response.error)
//                        }
//                    }
//                */
//            }
//        } catch (e: Exception) {
//            // handle errors
//            _state.value = ChatState.Error(e)
//        }
//    }
//}