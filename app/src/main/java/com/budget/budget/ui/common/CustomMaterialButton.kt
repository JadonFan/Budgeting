package com.budget.budget.ui.common

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import androidx.annotation.RequiresApi
import com.budget.budget.R
import com.google.android.material.button.MaterialButton

@RequiresApi(Build.VERSION_CODES.M)
class CustomMaterialButton(ctx: Context, attrs: AttributeSet): MaterialButton(ctx, attrs,
    R.style.Widget_MaterialComponents_Button) {
    init {
        // TODO move these hardcoded values into the styles XML file
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        setTextAppearance(R.style.Widget_MaterialComponents_Button)
        setTextColor(resources.getColor(android.R.color.white, ctx.theme))
        setPadding(0, 30, 0, 30)
        gravity = Gravity.CENTER
    }
}