
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.io.File
import kotlin.io.path.Path


@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun App() {
    var expanded by remember { mutableStateOf(false) }
    var textfieldSize by remember { mutableStateOf(Size.Zero) }
    val searchUsers = remember { SearchUser() }
    var textEditText by remember { mutableStateOf("") }
    var textName by remember { mutableStateOf("Имя фамилия") }
    var textLogin by remember { mutableStateOf("Логин") }
    var textLogOnOf by remember { mutableStateOf(" ") }
    var isErrors by remember { mutableStateOf(false) }
    var textImage by remember { mutableStateOf( File(SearchUser.DEFAULT_IMAGE).toString())}
    val errorMessage ="* поле ввода не должно быть пустым " +
            "\n* введенные данные не совпадают с данными в базе сотрдников"
    var tabIndex by remember { mutableStateOf(0) }
    val tabList = listOf("Недавние записи","За все время")
   val usersList = (searchUsers.usersList(Path(SearchUser.URL),textEditText))
    val options = usersList
    //val options = listOf("Аббрар","Якушк","Уткин Андр", "Уткина Н")

    fun getUserData(){
        if (textEditText.isNotBlank()){
            isErrors = false
            textName = searchUsers.personName(Path(SearchUser.URL), textEditText)
            textLogin = searchUsers.personLogin(Path(SearchUser.URL), textName)
            textLogOnOf = searchUsers.personLogOnOff(Path(SearchUser.URL), textName)
            textImage = searchUsers.getImage(textLogin)
            textEditText = textName

            if (textName=="Not found" && textLogin=="")
                isErrors= true

        }
        else{
            isErrors = true

        }


    }
    fun getUserDataNoKeyEnter(key:KeyEvent){
        if (key.key == Key.Enter || key.key == Key.NumPadEnter ) {
            if (textEditText.isNotBlank()){

                isErrors=false

                textName = searchUsers.personName(Path(SearchUser.URL), textEditText)
                textLogin = searchUsers.personLogin(Path(SearchUser.URL), textName)
                textLogOnOf = searchUsers.personLogOnOff(Path(SearchUser.URL), textName)
                searchUsers.usersList(Path(SearchUser.URL),textEditText)
                textImage = searchUsers.getImage(textLogin)
                textEditText = textName
                if (textName=="Not found" && textLogin=="")
                    isErrors= true

            }
            else{
                isErrors = true


            }
        }
    }

// Интерфейс
    // Основной экран
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        )
    {

// Поле ввода ФИО

        OutlinedTextField(
            value = textEditText
                .capitalize()
                .trimStart(' ')
                .replace("\\s+".toRegex()," ")
                ,
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates->
                    textfieldSize = coordinates.size.toSize()
                }
                .onKeyEvent {
                   getUserDataNoKeyEnter(it)

                    true
                },


            shape = RoundedCornerShape(8.dp),

            trailingIcon = @Composable {
                IconButton(
                    onClick = {
                        getUserData()

                              },
                    )

                {

                    if(isErrors==false){
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "search",
                        tint = MyColor.Gray
                    )
                }
                    else{
                        Icon(
                            Icons.Filled.Warning,
                            "error",
                            tint = MaterialTheme.colors.error)

                    }
                }

                //Меню выпадания списка

                val filteringOptions = options.filter{
                    it.contains(
                        textEditText,
                        ignoreCase = true)
                }
                if (filteringOptions.isNotEmpty()){
                    if(textEditText.length > 3){
                        DropdownMenu(
                            expanded = !expanded,
                            focusable = false,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .width(with(
                                    LocalDensity.current) {

                                    textfieldSize.width.toDp()
                                })

                        ) {
                            filteringOptions.forEach { users ->
                                DropdownMenuItem(
                                    onClick = {
                                        textEditText = users
                                        getUserData()
                                    }){

                                    Text(text = users)
                                }
                            }
                        }
                    }
                }
            },

            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MyColor.Violet,
                unfocusedLabelColor = MyColor.Gray,
                unfocusedBorderColor = MyColor.Gray,
                textColor = MyColor.Black
            ),

            label = {
                if(isErrors==false){
                Text(
                    color = MyColor.Violet,
                    text = "Поиск сотрудника"
                )
            }
                else{
                    Text(
                        color = MaterialTheme.colors.error,
                        text = "Поиск сотрудника",
                    )
                }

                    },

            placeholder = {
                Text(
                    color = MyColor.Gray,
                    text = "Введите фамилию сотрудника"
                )


            },

            onValueChange = {
                textEditText = it
            },
            isError = isErrors,
            singleLine = true,

        )
        if(isErrors==true)
            Text(
                text=errorMessage,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(start = 24.dp)
            )



        Row(modifier = Modifier
            .fillMaxSize()

        ) {

            // Карточка пользователя
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


            // Поле вывода логов
            Card(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
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
                    text = "Логи пользователя:",
                    modifier = Modifier
                        .padding(
                            top = 12.dp)

                )
                    // Tab элементы Последние записи и За все время

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp)
                    ){
                        TabRow(
                            selectedTabIndex = tabIndex,
                            backgroundColor = MyColor.White,
                            contentColor = MyColor.Black,
                            indicator = {
                                TabRowDefaults.Indicator(modifier =
                                Modifier.tabIndicatorOffset(it[tabIndex]),
                                    color = MyColor.Violet,
                                )
                            }


                        ){
                            tabList.forEachIndexed{index,title ->
                                Tab(
                                    selected = tabIndex == index,
                                    onClick = {
                                        tabIndex = index

                                    },
                                    selectedContentColor = MyColor.Violet,
                                    text = {
                                        Text(
                                            text = title,
                                            color = if(tabIndex==index) MyColor.Violet else MyColor.DarkGray
                                        )
                                    }
                                )
                            }
                        }

                    }
                    when(tabIndex){
                        0-> textLogOnOf = searchUsers.personLogOnOff(Path(SearchUser.URL), textName)
                        1-> textLogOnOf = searchUsers.personLogOnOffAllLines(Path(SearchUser.URL), textName)
                    }

                    // Поле вовода лога о пользователе

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

/*@Composable
fun dropDonwMenu(textEditText: String,textfieldSize: Size, expanded: Boolean){
    val options = listOf("Якушк", "Якушкин Константин", "Option 3", "Option 4", "Option 5")
    var text = textEditText
    var expanded = expanded

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier
            .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
    ) {
        options.forEach { label ->
            DropdownMenuItem(onClick = {
                text = label
                expanded = false

            }){
                Text(text = label)
            }
    }
    }
}*/

fun imageFromFile(file: File): ImageBitmap {
    return org.jetbrains.skia.Image.makeFromEncoded(file
        .readBytes())
        .toComposeImageBitmap()
}



fun main() = application {
    Window(

        title = "UFinder",
        icon = BitmapPainter(useResource("icons/UFinder.ico", ::loadImageBitmap)),
        onCloseRequest = ::exitApplication,
        resizable = true)
    {
        MaterialTheme(colors = darkColors()) {
            Box(Modifier.fillMaxSize().background(MyColor.Transparent))
        }

        App()
    }

}
