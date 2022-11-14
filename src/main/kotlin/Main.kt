
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
    var textEditText by remember { mutableStateOf("Поиск") }

// Интерфейс
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        )
    {
        TextField(
            value = textEditText,
            modifier = Modifier
                .fillMaxWidth()
                .size(320.dp,48.dp)
                .onKeyEvent { if(it.key == Key.Enter)
                    searchUsers.main(Path(SearchUser.URL) , textEditText)
                    true
                }
            ,
            shape = RoundedCornerShape(8.dp),
            trailingIcon = @Composable{
                IconButton(
                    onClick = {
                        searchUsers.main(Path(SearchUser.URL) , textEditText)
                    },
            ){
                    Icon(
                        Icons.Default.Search,
                    contentDescription = "search",
                    tint = Color.Black
                    )
                }
            },
            singleLine = true,
            onValueChange = {
                textEditText =it
            })
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
            ,
            elevation = 8.dp
        ){
            Box(modifier = Modifier
                .fillMaxSize(),

            ){
                Row (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                        ){
                    Image(
                        modifier = Modifier.size(300.dp,300.dp),
                        painter = rememberVectorPainter(Icons.Default.Person),
                        contentDescription = "userphoto"
                    )
                    Column(modifier = Modifier
                        .padding(12.dp)
                        .background(Color.Cyan)

                    ){
                        Text(text = "Имя фамилия")
                    }
                    Column(modifier = Modifier
                        .padding(12.dp)
                        .background(Color.Green)
                    ){
                        Text(text = "IP адреса:")
                    }
                    Column(modifier = Modifier
                        .padding(12.dp)
                        .background(Color.Red)
                    ){
                        Text(text = "Последние ПК")
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
