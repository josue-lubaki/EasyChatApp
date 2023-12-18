//package ca.josue_lubaki.loginatp.presentation
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.MutableSharedFlow
//import kotlinx.coroutines.flow.asSharedFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.CoroutineDispatcher
//
///**
// * created by Josue Lubaki
// * date : 2023-12-18
// * version : 1.0.0
// */
//
//class LoginATPViewModel(
//    private val useCase: LoginATPUseCase,
//    private val dispatcher: CoroutineDispatcher,
//) : ViewModel() {
//
//    private val _state = MutableStateFlow<LoginATPState>(LoginATPState.Idle)
//    val state: StateFlow<LoginATPState> = _state.asStateFlow()
//
//    fun onEvent(event: LoginATPEvent) {
//        when (event) {
//            //==== if you need execute actions ====//
//            is LoginATPEvent.OnLoadData -> getAllData()
//        }
//    }
//
//    private fun getAllData() {
//        _state.value = LoginATPState.Loading
//        try {
//            viewModelScope.launch(dispatcher) {
//                //==== if your useCase is a flow ====/
//                /*
//                    useCase().collect { response ->
//                        when (response) {
//                            is LoginATPStatus.Success -> {
//                                _state.value = LoginATPState.Success(response.data)
//                            }
//
//                            is LoginATPStatus.Error -> {
//                                _state.value = LoginATPState.Error(response.error)
//                            }
//                        }
//                    }
//                */
//
//                //==== if it's not a flow ====//
//                /*
//                    when (val response = useCase()) {
//                        is LoginATPStatus.Success -> {
//                            _state.value = LoginATPState.Success(response.data)
//                        }
//                        is LoginATPStatus.Error -> {
//                            _state.value = LoginATPState.Error(response.error)
//                        }
//                    }
//                */
//            }
//        } catch (e: Exception) {
//            // handle errors
//            _state.value = LoginATPState.Error(e)
//        }
//    }
//}