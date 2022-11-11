
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Preview
@Composable
fun App() {
    var text by remember { mutableStateOf("Ufinder on Kotlin") }
Button( {
}){
    Text(text)
}
    var searchUsers = remember { SearchUser("Якушкин Константин.txt") }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()

    }
}
