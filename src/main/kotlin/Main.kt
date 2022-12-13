
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.skia.Pattern
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
    var isErrors by remember { mutableStateOf(false) }
    var textImage = File(SearchUser.DEFAULT_IMAGE).toString()

// Интерфейс
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        )
    {
        OutlinedTextField(
            value = textEditText
                .capitalize()
                .trimStart(' ')
                .replace("\\s+".toRegex()," "),
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .onKeyEvent {
                    if (it.key == Key.Enter ) {
                        if (textEditText.isNotBlank()){
                        textName = searchUsers.personName(Path(SearchUser.URL), textEditText)
                        textLogin = searchUsers.personLogin(Path(SearchUser.URL), textEditText)
                        textLogOnOf = searchUsers.personLogOnOff(Path(SearchUser.URL), textName)
                        textImage = searchUsers.getImage(textName)
                            isErrors = false

                    }
                        else{
                            isErrors = true
                        }
                    }

                    true
                },
            shape = RoundedCornerShape(8.dp),

            trailingIcon = @Composable {
                IconButton(
                    onClick = {
                        if (textEditText.isNotBlank()){
                        textName = searchUsers.personName(Path(SearchUser.URL), textEditText)
                        textLogin = searchUsers.personLogin(Path(SearchUser.URL), textEditText)
                        textLogOnOf = searchUsers.personLogOnOff(Path(SearchUser.URL), textName)
                        textImage = searchUsers.getImage(textName)
                            isErrors = false

                    }
                        else{
                            isErrors = true
                        }
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
            isError = isErrors,
            singleLine = true,

            onValueChange = {
                textEditText = it

            }
        )
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
                            imageFromFile(File(textImage)),
                            modifier = Modifier
                                .size(200.dp, 200.dp)
                                .clip(CircleShape)
                                .border(3.dp, MyColor.LightGray, CircleShape),
                            contentDescription = "userphoto",
                            alignment = Alignment.Center
                        )
                        Column (
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxSize()
                            ,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = textName,
                                color = MyColor.Black
                            )
                            Text(
                                fontWeight = FontWeight.W800,
                                text = textLogin,
                                color = MyColor.Black
                            )
                        }
                    }

                }

            }
            Card(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(800.dp)
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
                            textColor = MyColor.Black,

                        ),
                        textStyle = TextStyle(
                            fontSize = 19.sp
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
        resizable = true)
    {
        MaterialTheme(colors = darkColors()) {
            Box(Modifier.fillMaxSize().background(MyColor.Transparent))
        }

        App()
    }

}
