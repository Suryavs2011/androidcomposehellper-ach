package com.saykarsd.androidcomposehellper.ui

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.isEditable
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(
textLabel:String,
customAccessibilityActions: List<CustomAccessibilityAction> = emptyList(),
query:String,
expanded:Boolean,
enabled:Boolean=true,
onSearch:(String)->Unit,
onQueryChange:(String)->Unit,
onExpandedChange:(Boolean)->Unit,
searchResultContent:@Composable ColumnScope.()->Unit={},
modifier:Modifier=Modifier
){
SearchBar(
expanded=expanded,
onExpandedChange=onExpandedChange,
inputField={
SearchBarDefaults.InputField(
query=query,
onQueryChange=onQueryChange,
onSearch=onSearch,
expanded=expanded,
onExpandedChange=onExpandedChange,
enabled=enabled,
placeholder={TextView(textLabel)}
)
},
content=searchResultContent,
modifier=Modifier
.wrapContentSize(Alignment.Center)
.padding(start=12.dp,top=15.dp,end=12.dp,bottom=0.dp)
.semantics{
contentDescription=textLabel
isEditable=true
customActions=customAccessibilityActions
}
.then(modifier),
)
}


