package az.anvar.shopinglist.presentation

import androidx.databinding.BindingAdapter
import az.anvar.shopinglist.R
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorInputName")
fun bindErrorInputName(tilName: TextInputLayout, isError: Boolean) {
    val message = if (isError) {
        tilName.context.getString(R.string.error_input_name)
    } else {
        null
    }
    tilName.error = message
}

@BindingAdapter("errorInputCount")
fun bindErrorInputCount(tilName: TextInputLayout, isError: Boolean) {
    val message = if (isError) {
        tilName.context.getString(R.string.error_input_count)
    } else {
        null
    }
    tilName.error = message
}