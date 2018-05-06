package br.com.mvvm.teste.bussiness.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.mvvm.teste.webservice.domain.Pessoa
import br.com.mvvm.teste.webservice.domain.PessoaBody

class LoginViewModel:ViewModel(){

    var loginRepository: LoginRepository = LoginRepository()


    /**
     * Médoto que consulta se o usuário existe no sistema
     * */
    fun getPessoaAPI(pessoaBody: PessoaBody): MutableLiveData<Pessoa>? {
        return loginRepository.getPessoaAPI(pessoaBody)
    }
}