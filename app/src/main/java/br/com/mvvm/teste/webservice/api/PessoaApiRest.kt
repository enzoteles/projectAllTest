package br.com.enzoteles.mvvm.webservice.api

import br.com.mvvm.teste.webservice.domain.Pessoa
import br.com.mvvm.teste.webservice.domain.PessoaBody
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by enzoteles on 15/03/18.
 */
interface PessoaApiRest{

    @Headers("Accept: application/json", "Content-Type: application/json; charset=utf-8")
    @POST("Pessoa/ConsultarPessoa/{token}")
    fun response(@Path("token") token: String?, @Body body: PessoaBody): Observable<Pessoa>


}