package com.example.budgetapp.components

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import androidx.annotation.RequiresApi
import com.example.budgetapp.R
import com.google.android.material.button.MaterialButton

@RequiresApi(Build.VERSION_CODES.M)
class CustomMaterialButton(ctx: Context, attrs: AttributeSet): MaterialButton(ctx, attrs,
    R.style.Widget_MaterialComponents_Button) {
    init {
        // TODO move these hardcoded values into the dimen XML file
        this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        this.setTextAppearance(R.style.Widget_MaterialComponents_Button)
        this.setTextColor(resources.getColor(android.R.color.white, ctx.theme))
        this.setPadding(0, 30, 0, 30)
        this.gravity = Gravity.CENTER
    }
}