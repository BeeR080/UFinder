
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.io.File
import javax.swing.border.Border
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
    var textImage by remember { mutableStateOf("R:\\Common\\Фото_сотрудников_(база)\\Москва\\Якушкин Константин Сергеевич_пропуск.jpg") }

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
                        //textImage = searchUsers.getImage(textName)
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
                        tint = MyColor.Gray
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MyColor.Violet,
                unfocusedBorderColor = MyColor.Gray,
                textColor = MyColor.White
            ),

            label = {
                Text(
                    color = MyColor.White,
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
                        .background(MyColor.DarkViolet),

                    ) {
                    Column(
                        modifier = Modifier
                            .padding(12.dp)
                            .padding(top = 64.dp)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            imageFromFile(File(textImage)),
                            modifier = Modifier
                                .size(200.dp, 200.dp)
                                .clip(CircleShape)
                                .border(3.dp, MyColor.White, CircleShape),
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
                                color = MyColor.White
                            )
                            Text(
                                text = " - $textLogin",
                                color = MyColor.White
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
                    .background(MyColor.DarkViolet)
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){

                Text(
                    color = MyColor.White,
                    text = "Информация по сотруднику:",
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
                            textColor = MyColor.LightGray
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

    return org.jetbrains.skia.Image.makeFromEncoded(file.readBytes()).toComposeImageBitmap()
}
fun main() = application {
    Window(
        title = "UFinder",
        icon = rememberVectorPainter(Icons.Default.Search),
        onCloseRequest = ::exitApplication,
        resizable = false)
    {
        MaterialTheme(colors = darkColors()) {
            Box(Modifier.fillMaxSize().background(MyColor.DarkViolet))
        }

        App()
    }

}
