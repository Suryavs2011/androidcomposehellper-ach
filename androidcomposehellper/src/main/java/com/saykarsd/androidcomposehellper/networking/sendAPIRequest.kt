package com.saykarsd.androidcomposehellper.networking

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

private const val NETWORK_TIMEOUT=15000

fun SendAPIGetRequestAndGetStringResponse(url:String,scope:CoroutineScope,onStart:()->Unit,onres:(String,String)->Unit,onerror:(String)->Unit){
scope.launch(Dispatchers.IO){
withContext(Dispatchers.Main){onStart()}
try{
val connection=URL(url).openConnection() as HttpURLConnection
connection.connectTimeout=NETWORK_TIMEOUT
connection.readTimeout=NETWORK_TIMEOUT
connection.requestMethod="GET"
val responseCode=connection.responseCode
val contentType=connection.contentType?:""
if(responseCode in 200..299){
val isText=contentType.startsWith("text")||contentType.contains("json")||contentType.contains("xml")
if(isText){
val response=connection.inputStream.bufferedReader().readText()
withContext(Dispatchers.Main){onres(response,contentType)}
}else{
withContext(Dispatchers.Main){onerror("Response is not text. Content-Type: $contentType")}
}
}else{
val error=connection.errorStream?.bufferedReader()?.readText()?:"Unknown Error"
withContext(Dispatchers.Main){onerror(error)}
}
connection.disconnect()
}catch(e:CancellationException){
withContext(Dispatchers.Main){onerror("Request Cancelled")}
throw e
}catch(e:Exception){
withContext(Dispatchers.Main){onerror(e.message?:"Unknown Error")}
}
}
}

fun SendAPIGetRequestAndGetBytesResponse(url:String,scope:CoroutineScope,onStart:()->Unit,onres:(ByteArray,String)->Unit,onerror:(String)->Unit){
scope.launch(Dispatchers.IO){
withContext(Dispatchers.Main){onStart()}
try{
val connection=URL(url).openConnection() as HttpURLConnection
connection.connectTimeout=NETWORK_TIMEOUT
connection.readTimeout=NETWORK_TIMEOUT
connection.requestMethod="GET"
val responseCode=connection.responseCode
val contentType=connection.contentType?:""
if(responseCode in 200..299){
val response=connection.inputStream.readBytes()
withContext(Dispatchers.Main){onres(response,contentType)}
}else{
val error=connection.errorStream?.bufferedReader()?.readText()?:"Unknown Error"
withContext(Dispatchers.Main){onerror(error)}
}
connection.disconnect()
}catch(e:CancellationException){
withContext(Dispatchers.Main){onerror("Request Cancelled")}
throw e
}catch(e:Exception){
withContext(Dispatchers.Main){onerror(e.message?:"Unknown Error")}
}
}
}

fun SendAPIPostRequestAndGetBytesResponse(url:String,jsonBody:String,headers:Map<String,String>,scope:CoroutineScope,onStart:()->Unit,onres:(ByteArray,String)->Unit,onerror:(String)->Unit){
scope.launch(Dispatchers.IO){
withContext(Dispatchers.Main){onStart()}
try{
val connection=URL(url).openConnection() as HttpURLConnection
connection.connectTimeout=NETWORK_TIMEOUT
connection.readTimeout=NETWORK_TIMEOUT
connection.requestMethod="POST"
connection.doOutput=true
connection.doInput=true
connection.setRequestProperty("Content-Type","application/json")
headers.forEach{(k,v)->connection.setRequestProperty(k,v)}
connection.outputStream.use{it.write(jsonBody.toByteArray(Charsets.UTF_8))}
val responseCode=connection.responseCode
val contentType=connection.contentType?:""
if(responseCode in 200..299){
val responseBytes=connection.inputStream.readBytes()
withContext(Dispatchers.Main){onres(responseBytes,contentType)}
}else{
val error=connection.errorStream?.bufferedReader()?.readText()?:"Unknown Error"
withContext(Dispatchers.Main){onerror(error)}
}
connection.disconnect()
}catch(e:CancellationException){
withContext(Dispatchers.Main){onerror("Request Cancelled")}
throw e
}catch(e:Exception){
withContext(Dispatchers.Main){onerror(e.message?:"Unknown Error")}
}
}
}

fun SendAPIPostRequestAndGetStringResponse(url:String,jsonBody:String,headers:Map<String,String>,scope:CoroutineScope,onStart:()->Unit,onres:(String,String)->Unit,onerror:(String)->Unit){
scope.launch(Dispatchers.IO){
withContext(Dispatchers.Main){onStart()}
try{
val connection=URL(url).openConnection() as HttpURLConnection
connection.connectTimeout=NETWORK_TIMEOUT
connection.readTimeout=NETWORK_TIMEOUT
connection.requestMethod="POST"
connection.doOutput=true
connection.doInput=true
connection.setRequestProperty("Content-Type","application/json")
headers.forEach{(k,v)->connection.setRequestProperty(k,v)}
connection.outputStream.use{it.write(jsonBody.toByteArray(Charsets.UTF_8))}
val responseCode=connection.responseCode
val contentType=connection.contentType?:""
if(responseCode in 200..299){
val isText=contentType.startsWith("text")||contentType.contains("json")||contentType.contains("xml")
if(isText){
val response=connection.inputStream.bufferedReader().readText()
withContext(Dispatchers.Main){onres(response,contentType)}
}else{
withContext(Dispatchers.Main){onerror("Response is not text. Content-Type: $contentType")}
}
}else{
val error=connection.errorStream?.bufferedReader()?.readText()?:"Unknown Error"
withContext(Dispatchers.Main){onerror(error)}
}
connection.disconnect()
}catch(e:CancellationException){
withContext(Dispatchers.Main){onerror("Request Cancelled")}
throw e
}catch(e:Exception){
withContext(Dispatchers.Main){onerror(e.message?:"Unknown Error")}
}
}
}

fun EncodeURLParameter(parameter:String):String{
val result=URLEncoder.encode(parameter,"UTF-8")
return result
}

fun GetJSONObj(data:String):JSONObject{
val jsonObj=JSONObject(data)
return jsonObj
}
