package ca.josuelubaki.easychat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import ca.josuelubaki.easychat.navigation.SetupNavGraph
import com.alithya.common.data.datasource.datasourceimpl.DataStoreOperationsImpl
import com.alithya.common.ui.themes.EasyChatTheme
import com.alithya.common.utils.LocaleUtils.setLocale
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // setup language
                val language = DataStoreOperationsImpl(this@MainActivity).readPreferredLanguage()
                language
                    .distinctUntilChanged()
                    .collect {
                        setLocale(this@MainActivity, it)
                    }
            }
        }

        setContent {
            val navController = rememberAnimatedNavController()
            val windowSize = calculateWindowSizeClass(activity = this)

//            lifecycleScope.launchWhenStarted {
//                repeatOnLifecycle(Lifecycle.State.STARTED){
//                    // setup language
//                    val language = DataStoreOperationsImpl(this@MainActivity).readPreferredLanguage()
//                    language
//                        .distinctUntilChanged()
//                        .collect {
//                            setLocale(this@MainActivity, it)
//                        }
//                }
//            }

            EasyChatTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SetupNavGraph(
                        navController = navController,
                        windowSize = windowSize.widthSizeClass,
                    )
                }
            }
        }
    }
}