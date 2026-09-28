package mx.familiasquesuman.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import mx.familiasquesuman.app.ui.nav.AppNavHost
import mx.familiasquesuman.app.ui.theme.FamiliasQueSumanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FamiliasQueSumanApp()
        }
    }
}

@Composable
fun FamiliasQueSumanApp() {
    FamiliasQueSumanTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AppNavHost()
        }
    }
}
