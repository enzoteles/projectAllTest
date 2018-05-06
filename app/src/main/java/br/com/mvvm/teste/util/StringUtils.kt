package br.com.izio.util

import android.content.Context
import android.graphics.Typeface
import android.widget.TextView
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.Normalizer
import java.text.NumberFormat
import java.util.*
import java.util.regex.Pattern


/**
 * Created by ricardonuma on 6/30/17.
 */



object StringUtils {

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

    fun normalize(text: String?): String {

        if (text == null)
            return ""

        var string: String = text
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

    fun cpfCheck(cpf: String): Boolean {
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


    // CPF & CNPJ VALIDATION

    fun cpfCheck2(cpf: String?): Boolean {
        var cpf: String = cpf ?: return false

        cpf = cpf.replace(".", "")
        cpf = cpf.replace("-", "")
        if (cpf.length != 11) {
            return false
        }

        val digito1 = calcularDigitoVerificador(cpf.substring(0, 9), pesosCPF)
        val digito2 = calcularDigitoVerificador(cpf.substring(0, 9) + digito1,
                pesosCPF)
        return cpf == cpf.substring(0, 9) + digito1.toString() +
                digito2.toString()
    }

    fun cnpjCheck(cnpj: String?): Boolean {
        var cnpj: String = cnpj ?: return false

        cnpj = cnpj.replace(".", "")
        cnpj = cnpj.replace("-", "")
        cnpj = cnpj.replace("/", "")
        if (cnpj.length != 14) {
            return false
        }

        val digito1 = calcularDigitoVerificador(cnpj.substring(0, 12), pesosCNPJ)
        val digito2 = calcularDigitoVerificador(cnpj.substring(0, 12) + digito1,
                pesosCNPJ)
        return cnpj == cnpj.substring(0, 12) + digito1.toString() +
                digito2.toString()
    }

    private val pesosCPF = intArrayOf(11, 10, 9, 8, 7, 6, 5, 4, 3, 2)

    private val pesosCNPJ = intArrayOf(6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)

    private fun calcularDigitoVerificador(str: String, peso: IntArray): Int {
        var soma = 0
        var indice = str.length - 1
        var digito: Int
        while (indice >= 0) {
            digito = Integer.parseInt(str.substring(indice, indice + 1))
            soma += digito * peso[peso.size - str.length + indice]
            indice--
        }
        soma = 11 - soma % 11
        return if (soma > 9) 0 else soma
    }


    fun toCamelCase(string: String): String {
        val sb = StringBuffer(string)
        sb.replace(0, 1, string.substring(0, 1).toUpperCase())
        return sb.toString()
    }

    fun toCamelCaseWords(string: String): String {
        var sb: StringBuffer
        val strBuilder = StringBuilder()
        val array = string.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        for (str in array) {
            sb = StringBuffer(str)
            sb.replace(0, 1, str.substring(0, 1).toUpperCase())
            strBuilder.append(" ").append(sb.toString())
        }
        return strBuilder.toString().trim { it <= ' ' }
    }

    fun isNum(c: Char): Boolean {
        if (c < '0' || c > '9')
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

    /*fun toHexString(bytes: ByteArray): String {
        val hexString = StringBuilder()

        for (i in bytes.indices) {
            val hex = Integer.toHexString(0xFF and bytes[i])
            if (hex.length == 1) {
                hexString.append('0')
            }
            hexString.append(hex)
        }

        return hexString.toString()
    }*/

    fun hexStringToByteArray(s: String): ByteArray {
        val len = s.length
        val data = ByteArray(len / 2)
        var i = 0
        while (i < len) {
            data[i / 2] = ((Character.digit(s[i], 16) shl 4) + Character.digit(s[i + 1], 16)).toByte()
            i += 2
        }
        return data
    }


    fun circularLeftShift(value: Int, times: Int): Int {
        val binary = StringUtils.leftPad(Integer.toString(value, 2), 8, '0')

        val bitmap = arrayOfNulls<String>(8)

        for (i in 0..binary!!.length - 1) {
            bitmap[i] = Character.toString(binary!!.get(i))
        }

        for (i in 0..times - 1) {
            circularLeftShiftArray(bitmap)
        }

        var result = ""

        for (bit in bitmap) {
            result += bit
        }

        return Integer.parseInt(result, 2)
    }

    private fun circularLeftShiftArray(bitmap: Array<String?>) {
        val first = bitmap[0]
        System.arraycopy(bitmap, 1, bitmap, 0, bitmap.size - 1)
        bitmap[bitmap.size - 1] = first
    }

    fun isLetter(c: Char): Boolean {
        return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z'
    }

    fun isDigit(c: Char): Boolean {
        return c >= '0' && c <= '9'
    }

    /**
     *
     * The maximum size to which the padding constant(s) can expand.
     */
    private val PAD_LIMIT = 8192
    val SPACE = " "

    fun leftPad(str: String?, size: Int, padChar: Char): String? {
        if (str == null) {
            return null
        }
        val pads = size - str.length
        if (pads <= 0) {
            return str // returns original String when possible
        }
        if (pads > PAD_LIMIT) {
            return leftPad(str, size, padChar.toString())
        }
        return repeat(padChar, pads) + str
    }

    fun leftPad(str: String?, size: Int, padStr: String): String? {
        var padStr = padStr
        if (str == null) {
            return null
        }
        if (padStr.isEmpty()) {
            padStr = SPACE
        }
        val padLen = padStr.length
        val strLen = str.length
        val pads = size - strLen
        if (pads <= 0) {
            return str // returns original String when possible
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            return leftPad(str, size, padStr[0])
        }

        if (pads == padLen) {
            return padStr + str
        } else if (pads < padLen) {
            return padStr.substring(0, pads) + str
        } else {
            val padding = CharArray(pads)
            val padChars = padStr.toCharArray()
            for (i in 0..pads - 1) {
                padding[i] = padChars[i % padLen]
            }
            return String(padding) + str
        }
    }

    fun repeat(ch: Char, repeat: Int): String {
        val buf = CharArray(repeat)
        for (i in repeat - 1 downTo 0) {
            buf[i] = ch
        }
        return String(buf)
    }


    @Throws(IndexOutOfBoundsException::class)
    private fun padding(repeat: Int, padChar: Char): String {
        if (repeat < 0) {
            throw IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat)
        }
        val buf = CharArray(repeat)
        for (i in buf.indices) {
            buf[i] = padChar
        }
        return String(buf)
    }

    fun formatCPF(str: String): String {
        if (str.length == 11) {
            return str.substring(0, 3) + "." + str.substring(3, 6) + "." + str.substring(6, 9) + "-" + str.substring(9)
        }

        return str
    }


    fun formatCurrency(value: String): String {
        val decimalFormat = createDecimalFormat()

        try {
            val doubleValue = java.lang.Double.valueOf(value)!!
            return decimalFormat.format(doubleValue)
        } catch (e: Exception) {
            return ""
        }

    }

    fun formatCurrency(value: Double): String {
        val decimalFormat = createDecimalFormat()
        return decimalFormat.format(value)
    }

    fun formatCurrencyNew(value: Float): String {
        //val decimalFormat = createDecimalFormat()
        return NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(value)
    }

    private fun createDecimalFormat(): DecimalFormat {
        return DecimalFormat("###,###,###,###,##0.00", DecimalFormatSymbols(Locale("pt", "BR")))
    }
}