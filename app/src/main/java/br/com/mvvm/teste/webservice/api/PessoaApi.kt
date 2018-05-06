package br.com.enzoteles.mvvm.webservice.api

import br.com.mvvm.teste.util.Constant
import br.com.mvvm.teste.webservice.domain.Pessoa
import br.com.mvvm.teste.webservice.domain.PessoaBody
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by enzoteles on 15/03/18.
 */
class PessoaApi(val pessoa: PessoaBody) {

    val service:PessoaApiRest


    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val gson = GsonBuilder().setLenient().create()

        val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS).build()

        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Constant.DOMAIN_URL)
                .client(client)
                .build()
        service = retrofit.create<PessoaApiRest>(PessoaApiRest::class.java)
    }

    /**
     * Médoto que consulta se o usuário existe no sistema
     * */
    fun loadPessoa(): Observable<Pessoa>? {
        return service.response(Constant.ACCESS_TOKEN, pessoa)
    }

}