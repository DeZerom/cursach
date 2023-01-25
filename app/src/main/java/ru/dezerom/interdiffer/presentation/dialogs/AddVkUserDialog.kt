package ru.dezerom.interdiffer.presentation.dialogs

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import ru.dezerom.interdiffer.R
import ru.dezerom.interdiffer.presentation.utils.Dimens
import ru.dezerom.interdiffer.presentation.widgets.BaseFullWidthTextInput
import ru.dezerom.interdiffer.presentation.widgets.BoldExtraBigText
import ru.dezerom.interdiffer.presentation.widgets.FullWidthButton
import ru.dezerom.interdiffer.presentation.widgets.FullWidthCard

@Composable
fun AddVkUserDialog(
    showState: MutableState<Boolean>,
    onUserAdd: (String) -> Unit
) {
    BaseDialogScreen(showState = showState) {
        FullWidthCard {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(Dimens.Paddings.basePadding)
            ) {
                BoldExtraBigText(
                    text = stringResource(id = R.string.user_adding),
                    modifier = Modifier.padding(bottom = Dimens.Paddings.largePadding)
                )

                val userId = remember { mutableStateOf("") }
                val isError = remember { mutableStateOf(false) }
                BaseFullWidthTextInput(
                    value = userId.value,
                    label = stringResource(id = R.string.user_id),
                    placeholder = stringResource(id = R.string.i_am_vk_id),
                    modifier = Modifier.padding(bottom = Dimens.Paddings.basePadding),
                    isError = isError.value,
                    onValueChanged = {
                        userId.value = it
                        if (userId.value.isNotBlank())
                            isError.value = false
                    }
                )

                val context = LocalContext.current
                val errorText = stringResource(id = R.string.field_should_not_be_blank)
                FullWidthButton(
                    text = stringResource(id = R.string.add)
                ) {
                    if (userId.value.isNotBlank()) {
                        onUserAdd(userId.value)
                        userId.value = ""

                        showState.value = false
                    } else {
                        isError.value = true
                        Toast.makeText(context, errorText, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
