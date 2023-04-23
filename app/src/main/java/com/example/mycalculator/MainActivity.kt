package com.example.mycalculator

import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    private var lastNum: Boolean = false
    private var lastDec: Boolean = false
    private val buttonClick = AlphaAnimation(7f, 0.8f)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view:View) {
        view.startAnimation(buttonClick)
        tvInput?.append((view as Button).text)
        lastNum = true
        lastDec = false
    }

    fun onClear(view: View){
        view.startAnimation(buttonClick)
        tvInput?.text = ""
    }

    fun onDecimal(view: View){
        view.startAnimation(buttonClick)
        if(lastNum && !lastDec)
        {
            tvInput?.append(".")
            lastDec = true
            lastNum = false
        }
    }

    fun onOperator(view: View){
        view.startAnimation(buttonClick)
        tvInput?.text?.let {

            if(lastNum && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNum = false
                lastDec = false
            }
        }
    }

    fun onEqual(view: View){

        view.startAnimation(buttonClick)

        if(lastNum){
            var tvValue = tvInput?.text.toString()
            var prefix = ""

            try{
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if(tvValue.contains("-")){
                    var splitValue = tvValue.split("-")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvInput?.text = removeZero((one.toDouble()-two.toDouble()).toString())
                }
                else if(tvValue.contains("+")){
                    var splitValue = tvValue.split("+")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvInput?.text = removeZero((one.toDouble()+two.toDouble()).toString())
                }
                else if(tvValue.contains("*")){
                    var splitValue = tvValue.split("*")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvInput?.text = removeZero((one.toDouble()*two.toDouble()).toString())
                }
                else if(tvValue.contains("/")){
                    var splitValue = tvValue.split("/")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvInput?.text = removeZero((one.toDouble()/two.toDouble()).toString())
                }
            }
            catch (e: java.lang.ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    fun onDel(view: View){
        view.startAnimation(buttonClick)
        var delValue = tvInput?.text.toString()
        if (delValue != null) {
            delValue = delValue.dropLast(1)
            tvInput?.text = delValue
        }
    }

    private fun removeZero(result: String) : String{
        var value = result
        if(result.contains(".0"))
        {
            value = result.substring(0,result.length-2)
        }
        return value
    }

    private fun isOperatorAdded(value: String) : Boolean{
        return if(value.startsWith("-")) {
            false
        }else{
            value.contains("*") ||
                    value.contains("+") ||
                    value.contains("-") ||
                    value.contains("/")
        }
    }

 }