
import java.io.File
import java.io.FileReader
import java.lang.Exception
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.function.Predicate
import java.util.stream.Collectors


class SearchUser() {

    fun personName(path: Path, userName:String):String {
        val f = File(path.toUri())
        var endFileName :String = ""
        val filelist = f.listFiles()
        for (files in filelist) {
            if (files.name.startsWith(userName)) {
               // val filePath = File(path.toString(), files.name)
                try {
                    endFileName = files.name.substring(0,files.name.indexOf("."))
                    println(endFileName)

                } catch (e: Exception) {
                    println(e.toString())
                }
            }
        }
        return endFileName
    }

    fun personLogOnOff(path: Path, userName:String):String{
        val f = File(path.toUri())
        var endfile = ""
        var endFileList :MutableList<String> = mutableListOf()
        val filelist = f.listFiles()
        for (files in filelist) {
            if (files.name.startsWith(userName)) {
                val filePath = File(path.toString(), files.name)
                try {
                    val openfile = FileReader(filePath)
                    endfile = openfile.readLines().takeLast(6).toString()
                    endFileList = endfile.split(";").map { it+"\n"}.toMutableList()
                } catch (e: Exception) {
                    println(e.toString())
                }
            }
        }
        return endFileList.toString()
    }


    fun personLogin(path:Path, userName:String):String {
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
                } catch (e: Exception) {
                    println(e.toString())
                }
            }

        }
        return endFileList.get(3)
    }


    fun getImage(personName:String):String {
        var path = URL_IMAGE
     Files.walk(Paths.get(path))
         .filter(Files::isRegularFile)
         .collect(Collectors.toList())
        var endFileImage = ""
        return endFileImage
    }
companion object{
    val URL = Paths.get("R:\\IT\\Admin\\Internal\\LogOnOff\\LogOnOff_New")
        .toAbsolutePath()
        .toString()
    val URL_IMAGE = Paths.get("R:\\Common\\Фото_сотрудников_(база)\\Москва\\")
        .toAbsolutePath()
        .toString()
}

}








