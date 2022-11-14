
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlin.io.path.Path


@Preview
@Composable
fun App() {
    val searchUsers = remember { SearchUser() }
    var text by remember { mutableStateOf("Поиск") }

// Интерфейс
    Row (
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()

    )
    {
        Button(modifier = Modifier
            ,
            onClick = {
                searchUsers.main(Path(SearchUser.URL) , "Шишлаков")
            })
        {
            Text(text)

        }
    }

}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()

    }
}
