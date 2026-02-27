package com.saykarsd.androidcomposehellper.files

import android.os.Environment
import java.io.File

fun SaveFile(folderPath: String, fileBody: String, fileTitle: String) {
val myFolderPath = File(Environment.getExternalStorageDirectory(), folderPath)
val myFile = File(myFolderPath, fileTitle)
if (!myFolderPath.exists()) {
myFolderPath.mkdirs()
}
myFile.writeText(fileBody)
}

