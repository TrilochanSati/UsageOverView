package com.windapp.usageoverview.ui.usage_overview.browsing_time

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.windapp.usageoverview.ui.usage_overview.usage_timeline.UsageTimelineEvent


@Composable
fun BrowsingTimeItem(
    icon: Drawable,
    timeSpent:String,
    inTime:String,
    website:String,
    onEvent: (BrowsingTimeEvent)->Unit,
    modifier: Modifier = Modifier
){

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(painter = rememberDrawablePainter(drawable = icon), contentDescription ="" )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = website)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text =timeSpent )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text =inTime )
    }
    Spacer(modifier = Modifier.height(16.dp))



}