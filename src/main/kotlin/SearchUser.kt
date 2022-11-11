import java.io.File
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.Path

class SearchUser(var userName:String) {
    val path = Path(URL)

        var filePath = File(path.toString(),userName)
        val openfile = FileReader(filePath)
        val end = openfile.readLines().takeLast(6)
        val e = println(end)


companion object{
    val URL = Paths.get("R:\\IT\\Admin\\Internal\\LogOnOff\\LogOnOff_New")
        .toAbsolutePath()
        .toString()
}
}


