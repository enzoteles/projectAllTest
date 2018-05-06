package br.com.izio.util

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.text.Spannable
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.widget.TextView
import android.text.style.UnderlineSpan
import android.text.SpannableString
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.text.DecimalFormat
import java.text.Normalizer
import java.text.NumberFormat
import java.util.*
import java.util.regex.Pattern


/**
 * Created by ricardonuma on 6/30/17.
 */


object TextUtils {

    /*fun convertStreamToString(`is`: InputStream): String? {
        val reader = BufferedReader(InputStreamReader(`is`))
        val sb = StringBuilder()
        var line: String? = null
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line!! + "\n")
            }
            `is`.close()
        } catch (e: IOException) {
            return null
        }

        return sb.toString()
    }*/

    fun normalize(text: String): String {
        //Log.d("String Utils", "Normalize text: " + text);
        var string = text
        string = Normalizer.normalize(string, Normalizer.Form.NFD)
        string = string.replace("[^\\p{ASCII}]".toRegex(), "")
        return string
    }

    fun isEmailValid(email: String): Boolean {
        var isValid = false

        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"

        val inputStr = email

        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(inputStr)

        if (matcher.matches()) {
            isValid = true
        }

        return isValid
    }

    fun isValidPhone(phone: String): Boolean {
        var isValid = false

        //String expression = "^(\\(11\\) [9][0-9]{4}-[0-9]{4})|(\\(1[2-9]\\) [5-9][0-9]{3}-[0-9]{4})|(\\([2-9][1-9]\\) [5-9][0-9]{3}-[0-9]{4})$";
        val expression = "^(\\([0-9]{2}\\))\\s([9]{1})?([0-9]{4})-([0-9]{4})$"

        val inputStr = phone

        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(inputStr)

        if (matcher.matches()) {
            isValid = true
        }

        return isValid
    }

    fun checkCNPJ(CNPJ: String): Boolean {
        var CNPJ = CNPJ

        CNPJ = CNPJ.replace("\\D".toRegex(), "")

        if (CNPJ == "00000000000000"
                || CNPJ == "11111111111111"
                || CNPJ == "22222222222222"
                || CNPJ == "33333333333333"
                || CNPJ == "44444444444444"
                || CNPJ == "55555555555555"
                || CNPJ == "66666666666666"
                || CNPJ == "77777777777777"
                || CNPJ == "88888888888888"
                || CNPJ == "99999999999999"
                || CNPJ.length != 14)
            return false

        val dig13: Char
        val dig14: Char
        var sm: Int
        var i: Int
        var r: Int
        var num: Int
        var peso: Int

        try {
            // Calculo do 1o. Digito Verificador
            sm = 0
            peso = 2

            i = 11
            while (i >= 0) {

                // converte o i-ésimo caractere do CNPJ em um número:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posição de '0' na tabela ASCII)
                num = (CNPJ[i].toInt() - 48).toInt()
                sm = sm + num * peso
                peso = peso + 1
                if (peso == 10) peso = 2
                i--
            }

            r = sm % 11
            if (r == 0 || r == 1)
                dig13 = '0'
            else
                dig13 = (11 - r + 48).toChar()

            // Calculo do 2o. Digito Verificador
            sm = 0
            peso = 2
            i = 12
            while (i >= 0) {
                num = (CNPJ[i].toInt() - 48).toInt()
                sm = sm + num * peso
                peso = peso + 1
                if (peso == 10) peso = 2
                i--
            }
            r = sm % 11
            if (r == 0 || r == 1)
                dig14 = '0'
            else
                dig14 = (11 - r + 48).toChar()

            // Verifica se os dígitos calculados conferem com os dígitos informados.
            if (dig13 == CNPJ[12] && dig14 == CNPJ[13])
                return true
            else
                return false

        } catch (erro: InputMismatchException) {
            return false
        }

    }

    fun checkCPF(cpf: String): Boolean {
        val numbers = cpf.replace("\\D".toRegex(), "")

        if (numbers.length != 11) return false
        if (numbers == "00000000000") return false
        if (numbers == "11111111111") return false
        if (numbers == "22222222222") return false
        if (numbers == "33333333333") return false
        if (numbers == "44444444444") return false
        if (numbers == "55555555555") return false
        if (numbers == "66666666666") return false
        if (numbers == "77777777777") return false
        if (numbers == "88888888888") return false
        if (numbers == "99999999999") return false
        if (numbers == "12345678909") return false

        var sum1 = 0
        var sum2 = 0
        var d1: Int
        var d2: Int
        for (i in 0..numbers.length - 2 - 1) {
            val num = Integer.parseInt(numbers[i].toString())
            sum1 += num * (10 - i)
            sum2 += num * (11 - i)
        }
        d1 = sum1 % 11
        d1 = if (d1 < 2) 0 else 11 - d1
        sum2 += d1 * 2
        d2 = sum2 % 11
        d2 = if (d2 < 2) 0 else 11 - d2

        if (d1 != Integer.parseInt(numbers.substring(9, 10))) return false
        if (d2 != Integer.parseInt(numbers.substring(10, 11))) return false

        return true
    }

    fun toCamelCase(string: String): String {
        val sb = StringBuffer(string)
        sb.replace(0, 1, string.substring(0, 1).toUpperCase())
        return sb.toString()
    }

    fun toCamelCaseWords(string: String): String {
        var string = string

        val original = string

        try {
            string = delExtraSpaces(string)

            string = string.replace("(", "( ")
            string = string.replace("(  ", "( ")
            string = string.replace(" )", ")")

            //Log.d("TAG", "toCamelCaseWords " + string);

            string = string.toLowerCase()
            var sb: StringBuffer
            val strBuilder = StringBuilder()
            val array = string.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            for (str in array) {
                sb = StringBuffer(str)
                sb.replace(0, 1, str.substring(0, 1).toUpperCase())
                strBuilder.append(" ").append(sb.toString())
            }

            string = strBuilder.toString().trim { it <= ' ' }
            string = string.replace("( ", "(")

            return string
        } catch (e: Exception) {
            return original
        }

    }

    fun delExtraSpaces(str: String): String {    //custom method to remove multiple space
        var str = str

        str = correctSpaceAfterComma(str)

        val sb = StringBuilder()
        val strSplit = str.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        for (s in strSplit) {

            if (s != "")
            // ignore space
                sb.append(if (sb.length == 0) "" else " ").append(s) // add word with 1 space

        }
        return sb.toString()
    }

    fun correctSpaceAfterComma(str: String): String {
        return str.replace(",", ", ")
    }

        fun isNum(c: Char): Boolean {
            if (c < '0' || c > '9')
                return false
            return true
        }

        fun isLetter(c: Char): Boolean {
            var c = c
            c = Character.toLowerCase(c)
            if (c < 'a' || c > 'z')
                return false
            return true
        }

    fun setCustomFont(ctx: Context, textView: TextView, asset: String): Boolean {
        var tf: Typeface? = null
        try {
            tf = Typeface.createFromAsset(ctx.getAssets(), asset)
        } catch (e: Exception) {
            return false
        }

        textView.typeface = tf
        return true
    }

    fun setPartTextBold(tv: TextView, text: String, boldPart: String) {
        val str = SpannableStringBuilder(text)
        str.setSpan(android.text.style.StyleSpan(Typeface.BOLD),
                text.indexOf(boldPart),
                text.indexOf(boldPart) + boldPart.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        tv.text = str
    }

    fun setUnderlineText(tv: TextView) {
        val content = SpannableString(tv.text.toString())
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        tv.text = content
    }

    fun setMultiPartTextBold(tv: TextView, text: String, boldPart: Array<String>) {
        val str = SpannableStringBuilder(text)

        for (i in boldPart.indices) {
            str.setSpan(android.text.style.StyleSpan(Typeface.BOLD),
                    text.indexOf(boldPart[i]),
                    text.indexOf(boldPart[i]) + boldPart[i].length,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        }
        tv.text = str
    }

    fun setEditTextDecimalFormat(editText: EditText) {
        setEditTextDecimalFormat(editText, false)
    }

    private var mCurrentValue = ""
    fun setEditTextDecimalFormat(editText: EditText, isCurrency: Boolean) {
        mCurrentValue = ""
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                //if(!s.toString().equals(mCurrentValue)) {
                editText.removeTextChangedListener(this)

                val cleanString = s.toString().replace("[R$,.]".toRegex(), "")

                if (cleanString.length == 0)
                    return

                val parsed = java.lang.Double.parseDouble(cleanString)
                val formatted = if (isCurrency)
                    NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(parsed / 100f)
                else
                    (DecimalFormat("#0.0").format(parsed / 10f)).toString()


                editText.setText(formatted.replace(".", ","))
                editText.setSelection(formatted.length)

                editText.addTextChangedListener(this)
                //}
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }

    fun setEditTextUpperCase(editText: EditText) {
        mCurrentValue = ""
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(et: Editable) {
                editText.removeTextChangedListener(this)

                var s = et.toString()
                if (s != s.toUpperCase()) {
                    s = s.toUpperCase()
                    editText.setText(s)
                }
                editText.setSelection(editText.text.length)

                editText.addTextChangedListener(this)

            }
        })
    }
}