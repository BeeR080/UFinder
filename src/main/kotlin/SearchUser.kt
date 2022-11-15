import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.InputStreamReader
import java.lang.Exception
import java.nio.ByteBuffer
import java.nio.charset.CharsetDecoder
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*


class SearchUser() {

fun main(){

}
    fun personName(path: Path, userName:String):List<String> {
        val f = File(path.toUri())
        var endFileList :List<String> = emptyList()
        val filelist = f.listFiles()
        for (files in filelist) {
            if (files.name.startsWith(userName)) {
                val filePath = File(path.toString(), files.name)
                try {
                    val openfile = FileReader(filePath)
                    val endfile = openfile.readLines().takeLast(6).toString()
                    endFileList = endfile.split(";").map { it -> it.trim() }
                    println(endfile)

                    println(endFileList)


                } catch (e: Exception) {
                    println(e.toString())
                }


            }

        }
        return listOf(endFileList[1])
    }

companion object{
    val URL = Paths.get("R:\\IT\\Admin\\Internal\\LogOnOff\\LogOnOff_New")
        .toAbsolutePath()
        .toString()
}

}








