package com.campus.echojournal.entries.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.campus.echojournal.ui.theme.EchoJournalTheme
import com.campus.echojournal.ui.theme.GradientColor2

@Composable
fun BottomSheetIconButton(
    onClick : () -> Unit = {},
    containerColor: Color = MaterialTheme.colorScheme.background,
    icon :  @Composable () -> Unit,
    size : Dp ,
    modifier: Modifier = Modifier,
) {

    IconButton(
        modifier = modifier
            .size(size)
            .clip(shape = RoundedCornerShape(100.dp))
            .background(containerColor),
        onClick = { onClick() },
    ) {
       icon()
    }


}

@Preview
@Composable
private fun BottomSheetIconButtonPreview() {
    EchoJournalTheme {
        BottomSheetIconButton(
            onClick = {},
            containerColor = GradientColor2,
            icon = {
                Image(
                    painter = painterResource(id = com.campus.echojournal.R.drawable.ic_pause),
                    contentDescription = "Pause Recording",
                    modifier = Modifier.size(24.dp)
                )
            },
            size = 48.dp
        )
    }
}