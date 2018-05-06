package br.com.mvvm.teste.bussiness.login

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import br.com.enzoteles.mvvm.webservice.api.PessoaApi
import br.com.mvvm.teste.webservice.domain.Pessoa
import br.com.mvvm.teste.webservice.domain.PessoaBody
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import retrofit2.Response

class LoginRepository(){


    /**
     * Médoto que consulta se o usuário existe no sistema
     * */
    fun getPessoaAPI(pessoaBody: PessoaBody): MutableLiveData<Pessoa>? {
        var data: MutableLiveData<Pessoa>?=  MutableLiveData<Pessoa>()
        val api = PessoaApi(pessoaBody)
        api.loadPessoa()?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ pessoa ->
                    data?.value = pessoa
                }, {e->
                    e.printStackTrace()
                    Log.i("ERROR: ", "${e.message}")
                })
        return data

    }

}