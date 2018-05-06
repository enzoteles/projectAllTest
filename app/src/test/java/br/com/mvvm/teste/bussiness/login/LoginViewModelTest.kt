package br.com.mvvm.teste.bussiness.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModelProviders
import br.com.enzoteles.mvvm.app.ApplicationMVVM
import br.com.mvvm.teste.BuildConfig
import br.com.mvvm.teste.MainActivity
import br.com.mvvm.teste.webservice.domain.Pessoa
import br.com.mvvm.teste.webservice.domain.PessoaBody
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class LoginViewModelTest {
    lateinit var body: PessoaBody
    private val vm: LoginViewModel by lazy {
        getViewModel()
    }

    @Before
    fun setUp() {
        body = PessoaBody("72257369220", "1984-06-25")
    }

    @Test
    fun getPessoaAPI() {
        var data: MutableLiveData<Pessoa>? = vm.getPessoaAPI(body)
        assertNotNull(data)


    }

    private fun getViewModel(): LoginViewModel{
        val activity = Robolectric.setupActivity(MainActivity::class.java)
        return ViewModelProviders.of(activity).get(LoginViewModel::class.java)
    }
}











