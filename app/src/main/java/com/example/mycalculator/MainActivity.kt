package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var tvInput: TextView? = null
    private lateinit var outputtex: String
    var lastNumeric : Boolean = false
    var lastDot : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput=findViewById(R.id.editText)
    }

    fun onClear(view: View){

        if((lastNumeric || tvInput?.text.toString().startsWith("-") || lastDot) ){
            if(tvInput?.text.toString()!=""){
                tvInput?.text = tvInput?.text.toString().substring(0,tvInput?.text.toString().length-1)
        }
        }

    }


    fun onDigit(view: View) {
        tvInput?.append((view as Button).text)
        lastNumeric=true
        lastDot=false

    }
    fun onAllClear(view: View){
        tvInput?.text=""
//        lastNumeric = false
//        lastDot = false
    }
    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDot){
            tvInput?.append(".")
            lastNumeric= false
            lastDot= true
        }
    }
    fun onOperator(view: View){
        tvInput?.text?.let{
            if(lastNumeric and !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric=false
                lastDot=false
            }
        }
    }

    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue = tvInput?.text.toString()
            var prefix =""
            try{
                if(tvValue.startsWith("-")){
                    prefix="-"
                    tvValue=tvValue.substring(1)
                }
                if(tvValue.contains("-")){
                    var splitValue = tvValue.split("-")

                    var one=splitValue[0]
                    var two=splitValue[1]

                    if(prefix.isNotEmpty()){
                        one=prefix + one
                    }
                    tvInput?.text=removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                }else if(tvValue.contains("+")){
                    var splitValue = tvValue.split("+")

                    var one=splitValue[0]
                    var two=splitValue[1]

                    if(prefix.isNotEmpty()){
                        one=prefix + one
                    }
                    tvInput?.text=removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }
                else if(tvValue.contains("/")){
                    var splitValue = tvValue.split("/")

                    var one=splitValue[0]
                    var two=splitValue[1]

                    if(prefix.isNotEmpty()){
                        one=prefix + one
                    }
                    tvInput?.text=removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }else if(tvValue.contains("*")){
                    var splitValue = tvValue.split("*")

                    var one=splitValue[0]
                    var two=splitValue[1]

                    if(prefix.isNotEmpty()){
                        one=prefix + one
                    }
                    tvInput?.text=removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }else if(tvValue.contains("%")){
                    var splitValue = tvValue.split("%")

                    var one=splitValue[0]
                    var two=splitValue[1]

                    if(prefix.isNotEmpty()){
                        one=prefix + one
                    }
                    tvInput?.text=removeZeroAfterDot((one.toDouble() % two.toDouble()).toString())
                }

            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }
    private fun removeZeroAfterDot(result: String): String{
        var value = result
        if(result.contains(".0"))
            value = result.substring(0,result.length - 2)
        return value
    }

    private fun isOperatorAdded(value : String): Boolean{
        return if(value.startsWith("-")) { false }else { value.contains("/")|| value.contains("*") || value.contains("+") || value.contains("-") || value.contains("%") }
    }
}