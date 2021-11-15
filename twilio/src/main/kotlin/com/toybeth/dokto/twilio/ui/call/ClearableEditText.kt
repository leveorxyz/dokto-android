package com.toybeth.dokto.twilio.ui.call

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.graphics.drawable.DrawableCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.toybeth.dokto.twilio.R

// TODO Replace custom view with TextInputLayout Material Component as part of
// https://issues.corp.twilio.com/browse/AHOYAPPS-109
/**
 * ClearableEditText is an extension for standard EditText with an extra option to setup clear icon
 * with as right compound drawable, which handles clear icon touch event as erase for the contents
 * of user input.
 */
class ClearableEditText : AppCompatEditText {
    /** Clear action icon to display to the right of EditText input.  */
    private var clearDrawable: Drawable? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val stylables = context.theme
                .obtainStyledAttributes(attrs, R.styleable.ClearableEditText, 0, 0)

            // obtain clear icon resource id
            /** Clear icon resource id.  */
            val clearIconResId =
                stylables.getResourceId(R.styleable.ClearableEditText_clearIcon, -1)
            if (clearIconResId != -1) {
                clearDrawable = VectorDrawableCompat.create(resources, clearIconResId, null)
            }
        }

        // setup initial clear icon state
        setCompoundDrawablesWithIntrinsicBounds(null, null, clearDrawable, null)
        val text = text
        if (text != null) {
            showClearIcon(text.toString().length > 0)
        }

        // update clear icon state after every text change
        addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence, i: Int, i1: Int, i2: Int
                ) {
                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun afterTextChanged(editable: Editable) {
                    showClearIcon(editable.toString().length > 0)
                }
            })

        // simulate on clear icon click - delete edit text contents
        setOnTouchListener { view: View, motionEvent: MotionEvent ->
            if (isClearVisible && motionEvent.action == MotionEvent.ACTION_UP) {
                val editText = view as ClearableEditText
                val bounds = clearDrawable!!.bounds
                if (motionEvent.rawX >= view.getRight() - bounds.width()) {
                    editText.setText("")
                }
            }
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                view.performClick()
            }
            false
        }
    }

    /**
     * Displays clear icon in ClearableEditText.
     *
     * @param show pass true to display icon, otherwise false to hide.
     */
    private fun showClearIcon(show: Boolean) {
        // TODO: should probably use setVisibility method, but seems to not working.
        if (clearDrawable != null) {
            clearDrawable!!.alpha = if (show) 255 else 0
        }
    }

    /**
     * Reflects current state of clear icon.
     *
     * @return true if active, otherwise - false.
     */
    private val isClearVisible: Boolean
        get() = clearDrawable != null && DrawableCompat.getAlpha(clearDrawable!!) == 255
}