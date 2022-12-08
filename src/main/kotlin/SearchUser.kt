
import java.io.File
import java.io.FileReader
import java.lang.IndexOutOfBoundsException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.function.Predicate
import java.util.stream.Collectors
import kotlin.Exception
import kotlin.io.path.name


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

    fun personLogOnOff(path: Path, userName:String): String {
        val f = File(path.toUri())
        var endfile: List<String> = emptyList()
        var endFileList: List<String> = emptyList()
        val filelist = f.listFiles()
        for (files in filelist) {
            if (files.name.startsWith(userName)) {
                val filePath = File(path.toString(), files.name)
                try {
                    val openfile = FileReader(filePath)
                    endfile = openfile
                        .readLines()
                        .takeLast(6)
                        .map { mutableListOf( it
                        .split(";")
                        .slice(listOf(0,2,3,4,5))+"\n")
                        .toString()
                            .replace("[","")
                            .replace("]","")
                            .replace(",",";")
                    }
                    println(endfile)
                    endFileList = endfile
                } catch (e: Exception) {
                    println(e.toString())
                }
            }
        }
        return endFileList.toString()
            .replace("[","")
            .replace("]","")
            .replace(",","")
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
                    val endfile = openfile.readLines().takeLast(1).toString()
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
        var endFileImage = ""
        try{
     val walk = Files.walk(Paths.get(path))
         .filter{it.name.startsWith(personName)}
         .collect(Collectors.toList()).get(0).toString()
        println(walk)
         endFileImage = walk

    }catch (e:IndexOutOfBoundsException){
        path = DEFAULT_IMAGE
            val file = File(path)
            println(file)
            endFileImage = file.toString()
    }
        return endFileImage
    }
companion object{
    val DEFAULT_IMAGE = Paths.get("C:\\person.png")
        .toAbsolutePath()
        .toString()
    val URL = Paths.get("R:\\IT\\Admin\\Internal\\LogOnOff\\LogOnOff_New")
        .toAbsolutePath()
        .toString()
    val URL_IMAGE = Paths.get("R:\\Common\\Фото_сотрудников_(база)\\")
        .toAbsolutePath()
        .toString()
}

}








