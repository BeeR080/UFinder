import java.io.File
import java.io.FileReader
import java.lang.Exception
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.Path

class SearchUser() {

fun main(path: Path,userName:String){
    findInPath(path,userName)

}

companion object{
    val URL = Paths.get("R:\\IT\\Admin\\Internal\\LogOnOff\\LogOnOff_New")
        .toAbsolutePath()
        .toString()
}


}

fun findInPath(path: Path, userName:String){
    val f = File(path.toUri())
    val filelist = f.listFiles()
    for (files in filelist) {
        if (files.name.startsWith(userName)) {
            val filePath = File(path.toString(), files.name)
            try{
                val openfile = FileReader(filePath)
                val endfile = openfile.readLines().takeLast(6)
                println(endfile)
                println(files.name)
            }catch (e: Exception){
                println(e.toString())
            }
        }
    }

}






