package br.com.mvvm.teste

import android.app.Fragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.v4.app.FragmentActivity
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.enzoteles.mvvm.app.ApplicationMVVM
import br.com.enzoteles.mvvm.util.ManagerFragment
import br.com.izio.util.Mask
import br.com.mvvm.teste.bussiness.entry.EntryFragment
import br.com.mvvm.teste.bussiness.login.LoginViewModel
import br.com.mvvm.teste.webservice.domain.Pessoa
import br.com.mvvm.teste.webservice.domain.PessoaBody
import kotlinx.android.synthetic.main.login.*

class LoginFragment: Fragment(){

    lateinit var body: PessoaBody
    private var mCpfCnpjMask: TextWatcher?= null
    private var mDateMask: TextWatcher?= null

    lateinit var entry: EntryFragment
    lateinit var manager: ManagerFragment


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view = inflater!!.inflate(R.layout.login, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }



    /**
     * Médoto que inicia a interface com o usuráio
     * */
    fun initUI() {

        var viewModel = ViewModelProviders.of((activity as FragmentActivity)).get(LoginViewModel::class.java)

        entry = EntryFragment()
        manager = ManagerFragment()

        mCpfCnpjMask = Mask.insertCpfCnpj(etCpf, activity)
        mDateMask = Mask.insertDate(etBirthday, activity)

        etCpf.addTextChangedListener(mCpfCnpjMask)
        etBirthday.addTextChangedListener(mDateMask)

        btn_entry.setOnClickListener {
            observerViewModel(viewModel)
        }

    }

    /**
     * Médoto que consulta se o usuário existe no sistema
     * */
    private fun observerViewModel(viewModel: LoginViewModel) {
        body = PessoaBody("72257369220", "1984-06-25")
        //body = PessoaBody(etCpf.toString(), etBirthday.toString())
        val data = viewModel.getPessoaAPI(body)?.observe(ApplicationMVVM.mActivity!!, object : Observer<Pessoa> {
            @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            override fun onChanged(@Nullable response: Pessoa?) {
                if(response != null){
                    manager.replaceFragment(R.id.content, entry, "entry", true)
                }
            }


        })


        etCpf.text.clear()
        etBirthday.text.clear()

    }
}