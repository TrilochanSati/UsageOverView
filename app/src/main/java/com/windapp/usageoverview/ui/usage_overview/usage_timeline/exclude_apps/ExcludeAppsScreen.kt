package com.windapp.usageoverview.ui.usage_overview.usage_timeline.exclude_apps

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ExcludeAppsScreen(
    viewModel: ExcludeAppsViewModel= hiltViewModel()
) {

    var apps=viewModel.apps.collectAsState(initial = emptyList())

    val scaffoldState= rememberScaffoldState()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ){
        items(apps.value){
            app->
            ExcludeAppsItem(appNameInfo = app,
                onEvent =viewModel::onEvent ,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                )
        }
    }


}