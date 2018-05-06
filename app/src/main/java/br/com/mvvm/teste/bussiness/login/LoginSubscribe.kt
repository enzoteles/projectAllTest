package br.com.mvvm.teste.bussiness.login

import android.util.Log
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

class LoginSubscribe<Pessoa>: Subscriber<Pessoa>{
    override fun onComplete() {

    }

    override fun onSubscribe(s: Subscription?) {
        Log.i("Login.onSubscribe ", "${s.toString()}")
    }

    override fun onNext(t: Pessoa) {
        Log.i("Login.onNext ", "${t.toString()}")
    }

    override fun onError(t: Throwable?) {
        Log.i("Login.onError ", "${t.toString()}")
    }

}