
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.io.File
import java.nio.file.Path
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
    var textImage = File(SearchUser.DEFAULT_IMAGE).toString()

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
                        textLogin = searchUsers.personLogin(Path(SearchUser.URL), textEditText)
                    },
                ) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "search",
                        tint = MyColor.Gray
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MyColor.Violet,
                unfocusedBorderColor = MyColor.Gray,
                textColor = MyColor.Black
            ),

            label = {
                Text(
                    color = MyColor.Violet,
                    text = "Поиск сотрудника"
                )
            },
            placeholder = {
                Text(
                    color = MyColor.Gray,
                    text = "Введите фамилию сотрудника"
                )
            },
            singleLine = true,
            onValueChange = {
                textEditText = it
            })
        Row(modifier = Modifier
            .fillMaxSize()

        ) {
            Card(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(300.dp)
                    .padding(12.dp),
                elevation = 8.dp,
                shape = RoundedCornerShape(24.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MyColor.White),

                    ) {
                    Column(
                        modifier = Modifier
                            .padding(12.dp)
                            .padding(top = 64.dp)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            imageFromFile(File(textImage.toString())),
                            modifier = Modifier
                                .size(200.dp, 200.dp)
                                .clip(CircleShape)
                                .border(3.dp, MyColor.LightGray, CircleShape),
                            contentDescription = "userphoto",
                            alignment = Alignment.Center
                        )
                        Row(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = textName,
                                color = MyColor.Black
                            )
                            Text(
                                text = " - $textLogin",
                                color = MyColor.Black
                            )
                        }
                    }

                }

            }
            Card(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(500.dp)
                    .padding(12.dp),
                elevation = 8.dp,
                shape = RoundedCornerShape(24.dp),

            ) {
                Column (modifier = Modifier
                    .fillMaxSize()
                    .background(MyColor.White)
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){

                Text(
                    color = MyColor.Black,
                    fontSize = 18.sp ,
                    fontWeight = FontWeight.W800,
                    text = "LogOnOff пользователя:",
                    modifier = Modifier
                        .padding(
                            top = 12.dp)

                )

                    OutlinedTextField(
                    value = textLogOnOf,
                        modifier = Modifier
                            .fillMaxSize()
                            ,
                        shape = RoundedCornerShape(24.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MyColor.Violet,
                            unfocusedBorderColor = MyColor.Gray,
                            textColor = MyColor.Black
                        ),
                        readOnly = true,
                        singleLine = false,
                        onValueChange = {
                            textLogOnOf = it
                        })

            }
            }
        }

    }
}


fun imageFromFile(file: File): ImageBitmap {
    return org.jetbrains.skia.Image.makeFromEncoded(file
        .readBytes())
        .toComposeImageBitmap()
}
fun main() = application {
    Window(
        title = "UFinder",
        icon = rememberVectorPainter(Icons.Default.Search),
        onCloseRequest = ::exitApplication,
        resizable = false)
    {
        MaterialTheme(colors = darkColors()) {
            Box(Modifier.fillMaxSize().background(MyColor.Transparent))
        }

        App()
    }

}
