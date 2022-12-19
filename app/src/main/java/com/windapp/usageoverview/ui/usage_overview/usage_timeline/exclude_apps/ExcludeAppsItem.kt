package com.windapp.usageoverview.ui.usage_overview.usage_timeline.exclude_apps

import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.windapp.usageoverview.data.entities.AppNameInfo

@Composable
fun ExcludeAppsItem(
    appNameInfo: AppNameInfo,
    onEvent:(ExcludeAppsEvent) -> Unit,
    modifier: Modifier = Modifier

){

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(
            modifier= Modifier.weight(1f),
            verticalArrangement = Arrangement.Center

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text=appNameInfo.appName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))

            }


        }

        Checkbox(
            checked = appNameInfo.excludeApp,
            onCheckedChange ={
                    isBlocked->
                onEvent(ExcludeAppsEvent.OnExcludeChange(appNameInfo.copy(excludeApp = isBlocked)))
            } )

    }

}