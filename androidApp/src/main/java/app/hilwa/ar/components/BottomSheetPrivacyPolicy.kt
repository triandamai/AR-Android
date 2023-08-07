package app.hilwa.ar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.jeziellago.compose.markdowntext.MarkdownText

/**
 * Bottom Sheet Privacy Policy
 * author Trian Damai
 * created_at 12/02/22 - 23.36
 * site https://trian.app
 */
@Composable
fun BottomSheetPrivacyPolicy(
    modifier: Modifier = Modifier,
    onAccept:()->Unit = {}
) {
    Column(
        modifier = modifier
            .verticalScroll(
                rememberScrollState()
            )
            .padding(
                horizontal = 16.dp
            )
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Spacer(modifier = modifier.height(20.dp))
        Text(
            text = "Privacy Policy",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = modifier.height(10.dp))
        MarkdownText(
            color = MaterialTheme.colorScheme.onSurface,
            markdown = """
              ### Privacy Policy of [NAMA APP]
            """.trimIndent())
        Spacer(modifier = modifier.height(16.dp))
        ButtonPrimary(text = "Accept"){
            onAccept()
        }
        Spacer(modifier = modifier.height(16.dp))
    }

}

@Preview
@Composable
fun PreviewBottomSheetPrivacyPolicy() {
    BaseMainApp {
        BottomSheetPrivacyPolicy()
    }
}