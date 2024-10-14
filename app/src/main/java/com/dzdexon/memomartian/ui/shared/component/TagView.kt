package com.dzdexon.memomartian.ui.shared.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dzdexon.memomartian.R
import com.dzdexon.memomartian.ui.theme.LocalCustomColors


@Composable
fun TagView(tagsList: List<String>) {
    val colors = LocalCustomColors.current
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .horizontalScroll(state = rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        tagsList.forEach { tag ->
            Text(
                text = "#$tag",
                fontSize = 18.sp,
                color = colors.onTertiary,
                fontFamily = FontFamily(Font(R.font.ibm_plex_mono_regular))
            )

        }
    }
}