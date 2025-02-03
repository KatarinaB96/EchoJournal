package com.campus.echojournal.entries.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.campus.echojournal.R

@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }
    var totalLineCount by remember { mutableStateOf(0) }
    val maxLines = 3

    Text(
        text = text,
        maxLines = Int.MAX_VALUE,
        onTextLayout = { textLayoutResult ->
            totalLineCount = textLayoutResult.lineCount
        },
        modifier = Modifier.height(0.dp)
    )

    Column(modifier = modifier) {
        Text(
            text = text,
            maxLines = if (isExpanded) Int.MAX_VALUE else maxLines
        )

        if (totalLineCount > maxLines) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomEnd) {
                Text(
                    text = if (isExpanded) stringResource(R.string.show_less) else stringResource(R.string.show_more),
                    color = Color.Blue,
                    modifier =modifier
                        .clickable { isExpanded = !isExpanded }

                )
            }

        }
    }
}