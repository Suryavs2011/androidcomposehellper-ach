package com.saykarsd.androidcomposehellper.files

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.io.InputStream
import java.io.OutputStream

@Composable
fun MultipleFilesPicker(
mimeTypes: Array<String>,
onFilePicked: (
uri: Uri,
read: ((InputStream) -> Unit) -> Unit,
write: ((OutputStream) -> Unit) -> Unit
) -> Unit
): () -> Unit {
val context = LocalContext.current
val launcher = rememberLauncherForActivityResult(
ActivityResultContracts.OpenDocument()
) { uri ->
uri?.let {
context.contentResolver.takePersistableUriPermission(
it,
Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
)
val read: ((InputStream) -> Unit) -> Unit = { block ->
context.contentResolver.openInputStream(it)?.use(block)
}
val write: ((OutputStream) -> Unit) -> Unit = { block ->
context.contentResolver.openOutputStream(it)?.use(block)
}
onFilePicked(it,read,write)
}
}
return {
launcher.launch(mimeTypes)
}
}


@Composable
fun MultiFilePicker(
mimeTypes:Array<String>,
onFilesPicked:(
files:List<Triple<
Uri,
((InputStream)->Unit)->Unit,
((OutputStream)->Unit)->Unit
>>
)->Unit
):()->Unit{
val context=LocalContext.current
val launcher=rememberLauncherForActivityResult(
ActivityResultContracts.OpenMultipleDocuments()
){uris->
if(uris.isNotEmpty()){
val files=uris.map{uri->
context.contentResolver.takePersistableUriPermission(
uri,
Intent.FLAG_GRANT_READ_URI_PERMISSION or
Intent.FLAG_GRANT_WRITE_URI_PERMISSION
)
val read:((InputStream)->Unit)->Unit={block->
context.contentResolver.openInputStream(uri)?.use(block)
}
val write:((OutputStream)->Unit)->Unit={block->
context.contentResolver.openOutputStream(uri)?.use(block)
}
Triple(uri,read,write)
}
onFilesPicked(files)
}
}
return{launcher.launch(mimeTypes)}
}
