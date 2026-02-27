package com.saykarsd.androidcomposehellper.files

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.documentfile.provider.DocumentFile
import java.io.InputStream
import java.io.OutputStream

@Composable
fun FolderPicker(
onFolderPicked:(
uri:Uri,
read:(String,(InputStream)->Unit)->Unit,
write:(String,String,(OutputStream)->Unit)->Unit
)->Unit
):()->Unit{
val context=LocalContext.current
val launcher=rememberLauncherForActivityResult(
ActivityResultContracts.OpenDocumentTree()
){uri->
uri?.let{
context.contentResolver.takePersistableUriPermission(
it,
Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
)
val read:(String,(InputStream)->Unit)->Unit={fileName,block->
val file=DocumentFile.fromTreeUri(context,it)?.findFile(fileName)
file?.uri?.let{fileUri->
context.contentResolver.openInputStream(fileUri)?.use(block)
}
}
val write:(String,String,(OutputStream)->Unit)->Unit={fileName,mimeType,block->
val tree=DocumentFile.fromTreeUri(context,it)
val file=tree?.findFile(fileName)?:tree?.createFile(mimeType,fileName)
file?.uri?.let{fileUri->
context.contentResolver.openOutputStream(fileUri)?.use(block)
}
}
onFolderPicked(it,read,write)
}
}
return{launcher.launch(null)}
}

