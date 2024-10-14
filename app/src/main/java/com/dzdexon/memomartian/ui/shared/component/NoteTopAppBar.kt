package com.dzdexon.memomartian.ui.shared.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dzdexon.memomartian.R
import com.dzdexon.memomartian.ui.theme.LocalCustomColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteTopAppBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    canNavigateBack: Boolean = false,
    actions: @Composable () -> Unit = {},
    navigateUp: () -> Unit = {},
) {
    val colors = LocalCustomColors.current
    TopAppBar(
        colors = TopAppBarColors(
            containerColor = colors.primary,
            scrolledContainerColor = colors.primary,
            navigationIconContentColor = colors.onPrimary,
            titleContentColor = colors.onPrimary,
            actionIconContentColor = colors.onPrimary,
        ),
        title = {
            Text(
                title ?: "", fontSize = 32.sp,
                fontFamily = FontFamily(Font(R.font.ntype82_headline)),
            )
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack)
                IconButton(onClick = navigateUp) {
                    Icon(
                        painter = painterResource(R.drawable.caret_left),
                        modifier = Modifier.size(32.dp),
                        contentDescription = "Back Button"
                    )
                }
        },
        actions = {
            actions()
        }
    )

}