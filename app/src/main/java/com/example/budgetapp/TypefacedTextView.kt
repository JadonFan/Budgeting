package com.example.budgetapp

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView

class TypefacedTextView(context: Context?, attrs: AttributeSet?) : TextView(context, attrs) {
    init {
        if (!isInEditMode) {
            val styledAttrs: TypedArray? = context?.obtainStyledAttributes(attrs, R.styleable.TypefacedTextView)
            val fontName: String? = styledAttrs?.getString(R.styleable.TypefacedTextView_typeface)
            styledAttrs?.recycle()

            if (fontName != null) {
                val typeface: Typeface = Typeface.createFromAsset(context.assets, fontName)
                setTypeface(typeface)
            }

        }
    }
}