package com.campus.echojournal.entries.presentation.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.campus.echojournal.R
import com.campus.echojournal.ui.theme.EchoJournalTheme
import com.campus.echojournal.ui.theme.Gray6
import com.campus.echojournal.ui.theme.OnSurfaceVariant

@Composable
fun EntriesTopicChip(
    title: String,
    modifier: Modifier = Modifier
) {

    InputChip(
        modifier = modifier.height(30.dp).padding(vertical = 2.dp),
        shape = RoundedCornerShape(16.dp),
        onClick = { },
        label = {
            Text(
                style = MaterialTheme.typography.labelSmall,
                text = title
            )
        },
        avatar = {
            Icon(
                painter = painterResource(R.drawable.ic_hashtag),
                contentDescription = stringResource(R.string.icon_hashtag),
                tint = OnSurfaceVariant.copy(alpha = 0.5f)
            )
        },
        border = BorderStroke(
            width = 0.dp,
            color = Gray6
        ),
        selected = false,
        colors = InputChipDefaults.inputChipColors(
            containerColor = Gray6,
            labelColor = OnSurfaceVariant,
            selectedContainerColor = Gray6
        ),
    )

}

@Preview
@Composable
private fun EntriesTopicChipPreview() {
    EchoJournalTheme {
        Surface {
            EntriesTopicChip(title = "Topic")
        }
    }

}