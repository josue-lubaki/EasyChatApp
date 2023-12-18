//package ca.josue_lubaki.chat.presentation
//
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
//import org.koin.androidx.compose.koinViewModel
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//
///**
// * created by Josue Lubaki
// * date : 2023-12-18
// * version : 1.0.0
// */
//
//@Composable
//fun ChatScreenScreen(
//    windowSize: WindowWidthSizeClass = WindowWidthSizeClass.Compact,
//    viewModel: ChatViewModel = koinViewModel(),
//    onNavigateToRoute: (String) -> Unit,
//) {
//    val state by viewModel.state.collectAsState()
//
//    LaunchedEffect(Unit) {
//        viewModel.onEvent(ChatEvent.OnLoadData)
//    }
//
//    Content(
//        state = state,
//        onNavigateToRoute = onNavigateToRoute
//    )
//}
//
//@Composable
//private fun Content(
//    state: ChatState,
//    onNavigateToRoute: (String) -> Unit
//) {
//
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun ChatScreenPreview() {
//    Content(
//        state = ChatState.Idle,
//        onNavigateToRoute = {}
//    )
//}