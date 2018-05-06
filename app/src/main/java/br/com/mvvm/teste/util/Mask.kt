package br.com.izio.util

import android.content.Context
import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.NumberFormat
import java.util.*



/**
 * Created by ricardonuma on 6/30/17.
 */



object Mask {

    var CPF_MASK = "###.###.###-##"
    var CELULAR_MASK = "(##) #### #####"
    var CEP_MASK = "#####-###"

    fun unmask(s: String): String {
        return s.replace("[.]".toRegex(), "").replace("[-]".toRegex(), "")
                .replace("[/]".toRegex(), "").replace("[(]".toRegex(), "")
                .replace("[)]".toRegex(), "").replace("[R$]".toRegex(), "")
                .replace("[$]".toRegex(), "").replace("[,]".toRegex(), "")
    }

    fun insert(mask: String, ediTxt: EditText): TextWatcher {
        return object : TextWatcher {
            internal var isUpdating: Boolean = false
            internal var old = ""

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val str = Mask.unmask(s.toString())
                var mascara = ""
                if (isUpdating) {
                    old = str
                    isUpdating = false
                    return
                }
                var i = 0
                for (m in mask.toCharArray()) {
                    try {
                        if (m == '#' && StringUtils.isNum(str.get(i)) === false)
                            break
                        if (m == 'L' && StringUtils.isLetter(str.get(i)) === false)
                            break
                    } catch (e: Exception) {
                        break
                    }

                    if (m != '#' && m != 'L' && str.length > old.length) {
                        mascara += m
                        continue
                    }
                    try {
                        mascara += Character.toUpperCase(str.get(i))
                    } catch (e: Exception) {
                        break
                    }

                    i++
                }
                isUpdating = true
                ediTxt.setText(mascara)
                ediTxt.setSelection(mascara.length)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
            }

            override fun afterTextChanged(s: Editable) {}
        }
    }

    fun insertMoney(ediTxt: EditText): TextWatcher {
        return object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                       count: Int) {
                ediTxt.removeTextChangedListener(this)

                val cleanString = s.toString().replace("[R$,.]".toRegex(), "")

                val parsed = java.lang.Double.parseDouble(cleanString)
                /*String formatted = NumberFormat.getCurrencyInstance().format((parsed / 100));*/
                val formatted = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(parsed / 100)

                ediTxt.setText(formatted)
                ediTxt.setSelection(formatted.length)
                ediTxt.addTextChangedListener(this)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
            }

            override fun afterTextChanged(s: Editable) {}
        }
    }

    /*public static TextWatcher insertCpfCnpj(final EditText ediTxt, final RegisterClientRequest newClient) {*/
    fun insertCpfCnpj(ediTxt: EditText, mContext: Context): TextWatcher {
        return object : TextWatcher {
            internal var isUpdating: Boolean = false
            internal var old = ""

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val str = Mask.unmask(s.toString())
                var mascara = ""
                if (isUpdating) {
                    old = str
                    isUpdating = false
                    return
                }

                val cpfMask = "###.###.###-##"
                val cnpjMask = "##.###.###/####-##"

                val mask = if (str.length > 11) cnpjMask else cpfMask

                var i = 0
                for (m in mask.toCharArray()) {
                    try {
                        if (m == '#' && TextUtils.isNum(str.get(i)) === false)
                            break
                        if (m == 'L' && TextUtils.isLetter(str.get(i)) === false)
                            break
                    } catch (e: Exception) {
                        break
                    }

                    //					if ((m != '#' && m != 'L') && str.length() > old.length()) {
                    if (m != '#' && str.length > old.length || m != '#' && str.length < old.length
                            && str.length != i) {
                        mascara += m
                        continue
                    }
                    try {
                        mascara += Character.toUpperCase(str.get(i))
                    } catch (e: Exception) {
                        break
                    }

                    i++
                }
                isUpdating = true
                ediTxt.setText(mascara)
                ediTxt.setSelection(mascara.length)

                /*if (mContext is EntryActivity) {
                    mContext.resetField(true)
                }*/

                //Log.d("TAG", "VIEW_TYPE_CPF " + mascara);
                /*newClient.setRegistration(mascara);*/
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
            }

            override fun afterTextChanged(s: Editable) {}
        }
    }

    interface OnZipChangeListener{
        fun onZipTypeDone()
    }

    fun insertZip(ediTxt: EditText, listener: OnZipChangeListener? = null): TextWatcher {
        return object : TextWatcher {
            internal var isUpdating: Boolean = false
            internal var old = ""

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val str = Mask.unmask(s.toString())
                var mascara = ""
                if (isUpdating) {
                    old = str
                    isUpdating = false
                    return
                }

                val zipMask = "#####-###"

                var i = 0
                for (m in zipMask.toCharArray()) {
                    try {
                        if (m == '#' && TextUtils.isNum(str.get(i)) === false)
                            break
                        if (m == 'L' && TextUtils.isLetter(str.get(i)) === false)
                            break
                    } catch (e: Exception) {
                        break
                    }

                    /*if ((m != '#' && m != 'L') && str.length() > old.length()) {*/
                    if (m != '#' && str.length > old.length || m != '#' && str.length < old.length
                            && str.length != i) {
                        mascara += m
                        continue
                    }
                    try {
                        mascara += Character.toUpperCase(str.get(i))
                    } catch (e: Exception) {
                        break
                    }

                    i++
                }
                isUpdating = true
                ediTxt.setText(mascara)
                ediTxt.setSelection(mascara.length)

                if(mascara.length >= 9)
                    listener?.onZipTypeDone()

                //Log.d("TAG", "VIEW_TYPE_CPF " + mascara);
                /*newClient.setRegistration(mascara);*/
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
            }

            override fun afterTextChanged(s: Editable) {}
        }
    }

    fun insertPhone(ediTxt: EditText): TextWatcher {
        return object : TextWatcher {
            internal var isUpdating: Boolean = false
            internal var old = ""

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val str = Mask.unmask(s.toString())
                var mascara = ""
                if (isUpdating) {
                    old = str
                    isUpdating = false
                    return
                }

                val homePhoneMask = "(##)####-####"
                val workPhoneMask = "(##)#####-####"

                val mask = if (str.length > 10) workPhoneMask else homePhoneMask

                var i = 0
                for (m in mask.toCharArray()) {
                    try {
                        if (m == '#' && TextUtils.isNum(str.get(i)) === false)
                            break
                        if (m == 'L' && TextUtils.isLetter(str.get(i)) === false)
                            break
                    } catch (e: Exception) {
                        break
                    }

                    /*if ((m != '#' && m != 'L') && str.length() > old.length()) {*/
                    if (m != '#' && str.length > old.length || m != '#' && str.length < old.length
                            && str.length != i) {
                        mascara += m
                        continue
                    }
                    try {
                        mascara += Character.toUpperCase(str.get(i))
                    } catch (e: Exception) {
                        break
                    }

                    i++
                }
                isUpdating = true
                ediTxt.setText(mascara)
                ediTxt.setSelection(mascara.length)

                //Log.d("TAG", "VIEW_TYPE_CPF " + mascara);
                /*newClient.setRegistration(mascara);*/
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
            }

            override fun afterTextChanged(s: Editable) {}
        }
    }

    fun insertDate(ediTxt: EditText, mContext: Context): TextWatcher {
        return object : TextWatcher {
            internal var isUpdating: Boolean = false
            internal var old = ""

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val str = Mask.unmask(s.toString())
                var mascara = ""
                if (isUpdating) {
                    old = str
                    isUpdating = false
                    return
                }

                val dateMask = "##/##/####"

                var i = 0
                for (m in dateMask.toCharArray()) {
                    try {
                        if (m == '#' && TextUtils.isNum(str.get(i)) === false)
                            break
                        if (m == 'L' && TextUtils.isLetter(str.get(i)) === false)
                            break
                    } catch (e: Exception) {
                        break
                    }

                    /*if ((m != '#' && m != 'L') && str.length() > old.length()) {*/
                    if (m != '#' && str.length > old.length || m != '#' && str.length < old.length
                            && str.length != i) {
                        mascara += m
                        continue
                    }
                    try {
                        mascara += Character.toUpperCase(str.get(i))
                    } catch (e: Exception) {
                        break
                    }

                    i++
                }
                isUpdating = true
                ediTxt.setText(mascara)
                ediTxt.setSelection(mascara.length)

                /*if (mContext is EntryActivity) {
                    mContext.resetField(false)
                }*/

                //Log.d("TAG", "VIEW_TYPE_CPF " + mascara);
                /*newClient.setRegistration(mascara);*/
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
            }

            override fun afterTextChanged(s: Editable) {}
        }
    }

    fun isASign(c: Char): Boolean {
        return c == '.' || c == '-' || c == '/' || c == '(' || c == ')' || c == ',' || c == ' '
    }

    fun mask(mask: String, text: String): String {
        var i = 0
        var mascara = ""
        for (m in mask.toCharArray()) {
            if (m != '#') {
                mascara += m
                continue
            }
            try {
                mascara += text[i]
            } catch (e: Exception) {
                break
            }

            i++
        }

        return mascara
    }

    fun insertMask(ediTxt: EditText, mask: String): TextWatcher {
        return object : TextWatcher {
            internal var isUpdating: Boolean = false
            internal var old = ""

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val str = Mask.unmask(s.toString())
                var mascara = ""
                if (isUpdating) {
                    old = str
                    isUpdating = false
                    return
                }

                var index = 0
                for (i in 0..mask.length - 1) {
                    val m = mask[i]
                    if (m != '#') {
                        if (index == str.length && str.length < old.length) {
                            continue
                        }
                        mascara += m
                        continue
                    }

                    try {
                        mascara += str.get(index)
                    } catch (e: Exception) {
                        break
                    }

                    index++
                }

                if (mascara.length > 0) {
                    var last_char = mascara[mascara.length - 1]
                    var hadSign = false
                    while (isASign(last_char) && str.length == old.length) {
                        mascara = mascara.substring(0, mascara.length - 1)
                        last_char = mascara[mascara.length - 1]
                        hadSign = true
                    }

                    if (mascara.length > 0 && hadSign) {
                        mascara = mascara.substring(0, mascara.length - 1)
                    }
                }

                isUpdating = true
                ediTxt.setText(mascara)
                ediTxt.setSelection(mascara.length)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {}
        }
    }
}