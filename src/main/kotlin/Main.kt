
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.sun.tools.javac.Main
import org.jetbrains.skia.ColorChannel
import java.io.File
import kotlin.io.path.Path


@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun App() {
    val searchUsers = remember { SearchUser() }
    var textEditText by remember { mutableStateOf("") }
    var textName by remember { mutableStateOf("Имя фамилия") }

// Интерфейс
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        )
    {
        OutlinedTextField(
            value = textEditText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .onKeyEvent {
                    if (it.key == Key.Enter)
                        textName = searchUsers.personName(Path(SearchUser.URL), textEditText).toString()
                    true
                },

            shape = RoundedCornerShape(8.dp),
            trailingIcon = @Composable {
                IconButton(
                    onClick = {
                        textName = searchUsers.personName(Path(SearchUser.URL), textEditText).toString()
                    },
                ) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "search",
                        tint = Color.Black
                    )

                }
            },
            label = {Text(text = "Поиск сотрудника")},
            placeholder = { Text(text="Введите фамилию сотрудника") },
            singleLine = true,
            onValueChange = {
                textEditText = it
            })
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            border = BorderStroke(2.dp,MyColor.Violet),
                elevation = 8.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                   ,

                ) {
                Column(
                    modifier = Modifier
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .size(300.dp, 300.dp)
                            .border(3.dp,MyColor.Violet,RoundedCornerShape(2))
                        ,
                        painter = rememberVectorPainter(Icons.Default.Person),
                        contentDescription = "userphoto",
                        alignment = Alignment.Center
                    )
                    Column(
                        modifier = Modifier
                            .padding(12.dp),
                        verticalArrangement = Arrangement.Center
                        ) {
                        Text(text = textName )
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxSize()
                        .padding(start = 300.dp),
                    horizontalArrangement = Arrangement.Center

                    ) {
                    Column(
                        modifier = Modifier
                            .padding(12.dp),

                    ) {
                        Text(text = "Информация по сотруднику:")


                    }


                }
            }

        }
    }
}


fun main() = application {
    Window(onCloseRequest = ::exitApplication)
    {
        App()

    }

}
