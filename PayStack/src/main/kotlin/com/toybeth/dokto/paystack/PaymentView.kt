package com.toybeth.dokto.paystack

import android.annotation.TargetApi
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.content.res.TypedArray
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.material.card.MaterialCardView
import com.toybeth.dokto.paystack.databinding.PaymentviewBinding

import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.Locale
import io.ghyeok.stickyswitch.widget.StickySwitch


class PaymentView @JvmOverloads constructor(
    private val mContext: Context,
    private val attributeSet: AttributeSet? = null,
    private val styleAttr: Int = 0
    ) : MaterialCardView(mContext, attributeSet, styleAttr) {

    private var binding: PaymentviewBinding = PaymentviewBinding.inflate(LayoutInflater.from(context))
    private var headerSrc: Drawable? = null
    private var theme: String? = "0"
    private var headerTitleText: String? = ""
    private var headerBackgroundBoolean: String? = "0"
    private var borderRadius = 0f
    var billHeader: String? = null
    var billContent: String? = null
    private var chargeListener: ChargeListener? = null
    var CARD = 0
    var BANK = 1
    var PAYMENT_FROM = CARD
    var formerLength = 0
    var imageArray = arrayOf<Int>(
        R.drawable.visa,
        R.drawable.mastercard,
        R.drawable.discover,
        R.drawable.american_express
    )

    fun setAccountNumber(accountNumber: String?) {
        binding.accountNumber.setText(accountNumber)
    }

    fun setAccountHolderBirthday(mAccountHolderBirthday: String?) {
        binding.accountHolderBirthday.setText(mAccountHolderBirthday)
    }

    fun getAccountNumber(): String {
        return binding.accountNumber.text.toString()
    }

    fun getAccountHolderBirthday(): String {
        return binding.accountHolderBirthday.text.toString()
    }

    val bankName: String
        get() = binding.bankName.selectedItem.toString()
    var cardNumber: String?
        get() = binding.creditCardNumber.text.toString()
        set(mCreditNumber) {
            binding.creditCardNumber.setText(mCreditNumber)
        }
    var cardExpDate: String?
        get() = binding.creditCardExpiry.text.toString()
        set(mCreditMonth) {
            binding.creditCardExpiry.setText(mCreditMonth)
        }
    var cardCCV: String?
        get() = binding.creditCardCcv.text.toString()
        set(mCreditCCV) {
            binding.creditCardCcv.setText(mCreditCCV)
        }

    fun setPentecostTheme(theme: String?) {
        this.theme = theme
    }

    fun setPentecostBackground(background: Drawable?) {
        this.background = background
        binding.secondParent.setBackgroundResource(0)
        binding.parentView.background = background
    }

    fun setPentecostBackgroundColor(backgroundColor: Int) {
        this.setBackgroundColor(backgroundColor)

        //Need to tidy this up
        //String temp = String.valueOf(backgroundColor);
        //int bgColor = Color.parseColor(temp);
        binding.secondParent.background = null
        val gradientDrawable = GradientDrawable()
        gradientDrawable.setColor(backgroundColor)
        gradientDrawable.shape = GradientDrawable.RECTANGLE
        gradientDrawable.cornerRadius = resources.getDimension(R.dimen.size_5)
        //gradientDrawable.setStroke((int)getResources().getDimension(R.dimen.size_2), getResources().getColor(R.color.black));
        binding.parentView.background = gradientDrawable
        //parentView.setBackgroundColor(backgroundColor);
    }

    fun showLoader() {
        binding.progressBar.visibility = VISIBLE
        binding.payButton.visibility = INVISIBLE
        binding.payButton.isEnabled = false
        binding.creditCardNumber.isEnabled = false
        binding.creditCardExpiry.isEnabled = false
        binding.creditCardCcv.isEnabled = false
        //payButton.blockTouch();
        //payButton.morphToProgress(R.color.white, R.dimen.size_2, width, R.dimen.size_14, 10, R.color.colorAccent);
    }

    fun hideLoader() {
        //payButton.unblockTouch();
        binding.progressBar.visibility = GONE
        binding.payButton.visibility = VISIBLE
        binding.payButton.isEnabled = true
        binding.creditCardNumber.isEnabled = true
        binding.creditCardExpiry.isEnabled = true
        binding.creditCardCcv.isEnabled = true
    }

    private fun tintProgressBar(mProgressBar: ProgressBar?) {
        mProgressBar?.indeterminateDrawable?.setColorFilter(
            ContextCompat.getColor(
                context,
                R.color.colorPayAccent
            ), PorterDuff.Mode.SRC_IN
        )
    }

    init {

        tintProgressBar(binding.progressBar)
        setTextWatchers()

        //we are setting the background resource here again because MorphButton overrides what is set in the xml
        binding.payButton.setBackgroundResource(R.drawable.payment_button)
        val arr =
            mContext.obtainStyledAttributes(attributeSet, R.styleable.PaymentView)
        val gradientDrawable = GradientDrawable()
        val parentGradientDrawable = GradientDrawable()
        borderRadius = arr.getDimension(
            R.styleable.PaymentView_pentecostHeaderBorderRadius,
            resources.getDimension(R.dimen.size_5)
        )
        Log.e("Radius", borderRadius.toString())
        gradientDrawable.shape = GradientDrawable.RECTANGLE
        gradientDrawable.setColor(resources.getColor(R.color.transparent))
        val image_radii = floatArrayOf(borderRadius, borderRadius, 0f, 0f)
        gradientDrawable.cornerRadii = image_radii
        Log.e("Radius", borderRadius.toString())
        parentGradientDrawable.shape = GradientDrawable.RECTANGLE
        parentGradientDrawable.setColor(resources.getColor(R.color.transparent))
        val radiii = floatArrayOf(borderRadius, borderRadius, borderRadius, borderRadius)
        parentGradientDrawable.setColor(resources.getColor(R.color.transparent))
        val stroke = resources.getDimension(R.dimen.size_2).toInt()
        parentGradientDrawable.setStroke(stroke, resources.getColor(R.color.black))
        parentGradientDrawable.cornerRadius = borderRadius

        //gradientDrawable.mutate();
        //parentGradientDrawable.mutate();

        //Comeback to this later
        //binding.headerView.setBackground(gradientDrawable);
        //parentView.setBackground(parentGradientDrawable);

        theme = arr.getString(R.styleable.PaymentView_pentecostTheme)
        headerBackgroundBoolean = arr.getString(R.styleable.PaymentView_pentecostHeaderBackground)
        background = arr.getDrawable(R.styleable.PaymentView_pentecostBackgroundDrawable)
        setBackgroundColor(arr.getInt(R.styleable.PaymentView_pentecostBackgroundColor, R.color.default_bg))
        headerSrc = arr.getDrawable(R.styleable.PaymentView_pentecostHeaderBackgroundDrawable)
        headerTitleText = arr.getString(R.styleable.PaymentView_pentecostHeaderTitle)
        if (theme == null) {
            theme = "0"
        }
        if (headerBackgroundBoolean == null) {
            headerBackgroundBoolean = "0"
        }
        arr.recycle()
        if (theme == "0") {
            binding.parentView.setBackgroundResource(R.drawable.round_dark_bg)
            binding.creditCardNumber.setBackgroundResource(R.drawable.edit_text_bg)
            binding.creditCardCcv.setBackgroundResource(R.drawable.edit_text_bg)
            binding.creditCardExpiry.setBackgroundResource(R.drawable.edit_text_bg)
            binding.secureLogo.setImageResource(R.drawable.white_paystack_logo)
            binding.billHeader.setTextColor(resources.getColor(R.color.white))
            binding.billContent.setTextColor(resources.getColor(R.color.white))
            binding.leftIndicator.setTextColor(resources.getColor(R.color.white))
            binding.rightIndicator.setTextColor(resources.getColor(R.color.white))
        } else if (theme == "1") {
            binding.parentView.setBackgroundResource(R.drawable.round_white_bg)
            binding.creditCardNumber.setBackgroundResource(R.drawable.edit_text_white_bg)
            binding.creditCardCcv.setBackgroundResource(R.drawable.edit_text_white_bg)
            binding.creditCardExpiry.setBackgroundResource(R.drawable.edit_text_white_bg)
            binding.secureLogo.setImageResource(R.drawable.blue_paystack_logo)
            binding.billHeader.setTextColor(resources.getColor(R.color.black))
            binding.billContent.setTextColor(resources.getColor(R.color.black))
            binding.leftIndicator.setTextColor(resources.getColor(R.color.black))
            binding.rightIndicator.setTextColor(resources.getColor(R.color.black))
        }
        if (headerBackgroundBoolean == "1") {
            binding.headerView.setImageResource(0)
        }
        Log.e("TAG", theme!!)
        if (background != null) {
            binding.secondParent.background = null
            binding.parentView.background = background
        }
        if (backgroundTintList?.defaultColor != null &&
            backgroundTintList?.defaultColor != R.color.default_bg) {
            binding.secondParent.background = null
            val thisDrawable = GradientDrawable()
            thisDrawable.setColor(backgroundTintList?.defaultColor!!)
            thisDrawable.shape = GradientDrawable.RECTANGLE
            thisDrawable.cornerRadius = resources.getDimension(R.dimen.size_5)
            binding.parentView.background = thisDrawable
        }
        if (headerSrc != null) {
            binding.headerView.setImageDrawable(headerSrc)
        }
        if (headerTitleText != null) {
            binding.billHeader.text = headerTitleText
        }

        binding.stickySwitch.onSelectedChangeListener = object : StickySwitch.OnSelectedChangeListener {
            override fun onSelectedChange(direction: StickySwitch.Direction, text: String) {
                when (direction) {
                    StickySwitch.Direction.LEFT -> {
                        setVisibility(binding.bankDetailsSection, GONE)
                        setVisibility(binding.cardDetailsSection, VISIBLE)
                        PAYMENT_FROM = CARD
                    }
                    StickySwitch.Direction.RIGHT -> {
                        setVisibility(binding.bankDetailsSection, VISIBLE)
                        setVisibility(binding.cardDetailsSection, GONE)
                        PAYMENT_FROM = BANK
                    }
                }
            }
        }
        initSpinner()
        binding.payButton.setOnClickListener {
            when (PAYMENT_FROM) {
                0 -> {
                    if (cardNumber != "" && cardCCV != "" && cardExpDate != "") {
                        chargeListener!!.onChargeCard()
                    }
                    if (bankName != "" && getAccountNumber() != "" && getAccountHolderBirthday() != "") {
                        chargeListener!!.onChargeBank()
                    }
                }
                1 -> if (bankName != "" && getAccountNumber() != "" && getAccountHolderBirthday() != "") {
                    chargeListener!!.onChargeBank()
                }
            }
        }
        binding.accountHolderBirthday.setOnClickListener {
            DatePickerDialog(
                context, datePickerDialog,
                myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        }
    }

    var myCalendar = Calendar.getInstance()
    var datePickerDialog =
        OnDateSetListener { datePicker, year, monthOfYear, dayOfMonth ->
            myCalendar[Calendar.YEAR] = year
            myCalendar[Calendar.MONTH] = monthOfYear
            myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
            updateDateText()
        }

    private fun updateDateText() {
        val format = "yyyy-MM-dd" //1995-12-23 from PayStack
        val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
        binding.accountHolderBirthday.setText(simpleDateFormat.format(myCalendar.time))
    }

    interface ChargeListener {
        fun onChargeCard()
        fun onChargeBank()
        fun onSuccess()
    }

    fun setPAYMENT_FROM(value: PAYMENT_FORM_TYPE?) {
        when (value) {
            PAYMENT_FORM_TYPE.CARD -> {
                setVisibility(binding.bankDetailsSection, GONE)
                setVisibility(binding.cardDetailsSection, VISIBLE)
                PAYMENT_FROM = CARD
            }
            PAYMENT_FORM_TYPE.BANK -> {
                setVisibility(binding.bankDetailsSection, VISIBLE)
                setVisibility(binding.cardDetailsSection, GONE)
                PAYMENT_FROM = BANK
            }
        }
        setVisibility(binding.selectorControlBg, GONE)
    }

    private fun setVisibility(view: View?, visibility: Int) {
        view!!.visibility = visibility
    }

    fun setBanksSpinner(arraySpinner: Array<String?>) {
        val length = arraySpinner.size + 1
        val tempSpinner = arrayOfNulls<String>(length)
        tempSpinner[0] = "Select Bank"
        System.arraycopy(arraySpinner, 0, tempSpinner, 1, arraySpinner.size)
        bankSpinner = tempSpinner
        val s = findViewById<Spinner>(R.id.bank_name)
        val adapter = ArrayAdapter(context, R.layout.layout_spinner_item, bankSpinner)
        adapter.setDropDownViewResource(R.layout.layout_spinner_item_drop)
        s.adapter = adapter
        s.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                val tv = view as TextView
                if (position == 0) {
                    tv.setTextColor(resources.getColor(R.color.spinner_hint))
                } else {
                    tv.setTextColor(resources.getColor(R.color.black))
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }

    private lateinit var bankSpinner: Array<String?>

    private fun initSpinner() {
        bankSpinner = arrayOf()
        val adapter = ArrayAdapter(
            context, R.layout.layout_spinner_item,
            bankSpinner
        )
        adapter.setDropDownViewResource(R.layout.layout_spinner_item_drop)
        binding.bankName.adapter = adapter
        binding.bankName.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                val tv = view as TextView
                if (position == 0) {
                    tv.setTextColor(resources.getColor(R.color.spinner_hint))
                } else {
                    tv.setTextColor(resources.getColor(R.color.black))
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }

    private fun setTextWatchers() {
        binding.creditCardNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, j: Int, j1: Int, j2: Int) {
                val cardNumber = charSequence.toString()
                if (cardNumber.length > 2) {
                    for (i in listOfPattern().indices) {
                        if (cardNumber.substring(0, 2).matches(listOfPattern()[i].toRegex())) {
                            binding.creditCardNumber.setCompoundDrawablesWithIntrinsicBounds(
                                0, 0,
                                imageArray[i], 0
                            )
                            //break;
                        } else {
                            //creditNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0,  R.drawable.visa, 0);
                            //break;
                        }
                    }
                }
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                val cardNumber = binding.creditCardNumber.text.toString()
                if (!cardNumber.equals("", ignoreCase = true)) {
                    for (i in listOfPattern().indices) {
                        if (cardNumber.matches(listOfPattern()[i].toRegex())) {
                            binding.creditCardNumber.setCompoundDrawablesWithIntrinsicBounds(
                                0, 0,
                                imageArray[i], 0
                            )
                        }
                    }
                } else {
                    binding.creditCardNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                }
            }
        })
        binding.creditCardExpiry.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                formerLength = charSequence.length
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable.length > formerLength) { //User is adding text
                    if (editable.length == 2) {
                        editable.append("/")
                    }
                } else {
                    if (editable.length == 2) {
                        editable.delete(editable.length - 1, editable.length)
                    }
                }
            }
        })
    }

    companion object {
        fun listOfPattern(): ArrayList<String> {
            val listOfPattern = ArrayList<String>()
            val ptVisa = "^4[0-9]$"
            listOfPattern.add(ptVisa)
            val ptMasterCard = "^5[1-5]$"
            listOfPattern.add(ptMasterCard)
            val ptDiscover = "^6(?:011|5[0-9]{2})$"
            listOfPattern.add(ptDiscover)
            val ptAmeExp = "^3[47]$"
            listOfPattern.add(ptAmeExp)
            return listOfPattern
        }
    }
}
