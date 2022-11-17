
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
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
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.io.File
import kotlin.io.path.Path


@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun App() {
    val searchUsers = remember { SearchUser() }
    var textEditText by remember { mutableStateOf("") }
    var textName by remember { mutableStateOf("Имя фамилия") }
    var textLogin by remember { mutableStateOf("Логин") }
    var textLogOnOf by remember { mutableStateOf(" ") }
    var textImage by remember { mutableStateOf("C:\\ico_find.png") }

// Интерфейс
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        )
    {
        OutlinedTextField(
            value = textEditText.capitalize(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .onKeyEvent {
                    if (it.key == Key.Enter) {
                        textName = searchUsers.personName(Path(SearchUser.URL), textEditText)
                        textLogin = searchUsers.personLogin(Path(SearchUser.URL), textEditText)
                        textLogOnOf = searchUsers.personLogOnOff(Path(SearchUser.URL), textEditText)
                        textImage = searchUsers.getImage(textName)
                    }
                        true
                },

            shape = RoundedCornerShape(8.dp),
            trailingIcon = @Composable {
                IconButton(
                    onClick = {
                        textName = searchUsers.personName(Path(SearchUser.URL), textEditText)
                        textLogin = searchUsers.personLogin(Path(SearchUser.URL), textEditText).toString()
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
            placeholder = {
                Text(text="Введите фамилию сотрудника") },
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
                        imageFromFile(File(textImage)),
                        modifier = Modifier
                            .size(250.dp, 250.dp)
                            .border(3.dp,MyColor.Violet,RoundedCornerShape(2))
                        ,
                      // painter = rememberVectorPainter(Icons.Default.Person),
                        contentDescription = "userphoto",
                        alignment = Alignment.Center
                    )
                    Row(
                        modifier = Modifier
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.Center
                        ) {
                        Text(text = textName )
                        Text(text=" - $textLogin")
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxSize()
                        .padding(start = 310.dp),
                    horizontalArrangement = Arrangement.Center

                    ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        Text(
                            text = "Информация по сотруднику:")

                        Text(
                            text =textLogOnOf)
                        ListItem(textLogOnOf)
                    }

                }
            }

        }
    }
}
@Composable
private fun ListItem(data:String){
    println(data)
}

fun imageFromFile(file: File): ImageBitmap {

    return org.jetbrains.skia.Image.makeFromEncoded(file.readBytes()).toComposeImageBitmap()
}
fun main() = application {
    Window(onCloseRequest = ::exitApplication)
    {
        App()
    }

}
