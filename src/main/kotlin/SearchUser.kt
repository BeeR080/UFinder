
import java.io.File
import java.io.FileReader
import java.lang.IndexOutOfBoundsException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors
import kotlin.Exception
import kotlin.io.path.name


class SearchUser() {


    private val resourcesDir = File(System.getProperty("compose.application.resources.dir"))
    private val resourcesUrlPath = resourcesDir.resolve("config.txt").readLines().get(3)
    val resourcesDefaultImagePath = resourcesDir.resolve("person.png").toURI()
    private val resourcesUsersImagesPath = resourcesDir.resolve("config.txt").readLines().get(5)
    fun personName(userName:String):String {
        val f = File(Paths.get(resourcesUrlPath).toUri())
        var endFileName=""
        var name = userName
            .trim()
            .split("\\s+".toRegex())
            .map { it
                .capitalize()
            }
            .joinToString(" ")

        val filelist = f.listFiles()
        for (files in filelist) {
            if (files.name.startsWith(name)) {
                    endFileName = files.name
                        .substring(
                            0,
                            files.name
                            .indexOf("."))
            }
            if(!endFileName.startsWith(name)){
                endFileName="Not found"
            }
        }
        return endFileName
    }

    fun personLogOnOff(userName:String): String {
        val f = File(Paths.get(resourcesUrlPath).toUri())
        var endfile: List<String> = emptyList()
        var endFileList: List<String> = emptyList()
        val filelist = f.listFiles()
        for (files in filelist) {
            if (files.name.startsWith(userName)) {
                val filePath = File(resourcesUrlPath, files.name)
                if(userName!="error"){
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
                    endFileList = endfile
                } catch (e: Exception) {
                }

            } else{
                    endFileList = listOf("[,]error")
            }
            }
        }
        return endFileList.toString()
            .replace("[","")
            .replace("]","")
            .replace(",","")
    }
    fun personLogOnOffAllLines(userName:String): String {
        val f = File(Paths.get(resourcesUrlPath).toUri())
        var endfile: List<String> = emptyList()
        var endFileList: List<String> = emptyList()
        val filelist = f.listFiles()
        for (files in filelist) {
            if (files.name.startsWith(userName)) {
                val filePath = File(resourcesUrlPath, files.name)
                if(userName!="error"){
                    try {
                        val openfile = FileReader(filePath)
                        endfile = openfile
                            .readLines()
                            .map { mutableListOf( it
                                .split(";")
                                .slice(listOf(0,2,3,4,5))+"\n")
                                .toString()
                                .replace("[","")
                                .replace("]","")
                                .replace(",",";")
                            }
                        endFileList = endfile
                    } catch (e: Exception) {
                    }

                } else{
                    endFileList = listOf("[,]error")
                }
            }
        }
        return endFileList.toString()
            .replace("[","")
            .replace("]","")
            .replace(",","")
    }


    fun personLogin(userName:String):String {
        val f = File(Paths.get(resourcesUrlPath).toUri())
        var login = ""
        var endFileList :List<String> = emptyList()
        val filelist = f.listFiles()
        for (files in filelist) {
            if (files.name.startsWith(userName)) {
                val filePath = File(resourcesUrlPath, files.name)
                try {
                    val openfile = FileReader(filePath)
                    val endfile = openfile.readLines().takeLast(1).toString()
                    endFileList = endfile.split(";").map { it -> it.trim() }
                    login = endFileList.get(3)
                } catch (e: IndexOutOfBoundsException) {
                    login =""
                }
            }

        }
        return login
    }


    fun getImage(personName:String):String {
        var path = Paths.get(resourcesUsersImagesPath).toAbsolutePath().toString()
        var endFileImage = ""
        if(personName!=""){
        try{
     val walk = Files.walk(Paths.get(path))
         .filter{it.name.startsWith(personName)}
         .collect(Collectors.toList()).get(0).toString()
         endFileImage = walk

    }catch (e:Exception){
        path = Paths.get(resourcesDefaultImagePath).toAbsolutePath().toString()
            val file = File(path)
            endFileImage = file.toString()
    }
        }else{
            path = Paths.get(resourcesDefaultImagePath).toAbsolutePath().toString()
            val file = File(path)
            endFileImage = file.toString()
        }
        return endFileImage

    }


}








