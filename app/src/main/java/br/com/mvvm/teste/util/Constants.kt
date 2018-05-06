package br.com.mvvm.teste.util

class Constants{

    internal interface httpcodes {
        companion object {
            val STATUS_OK = 200
            val STATUS_CREATED = 201
            val STATUS_NO_CONTENT = 204
            val STATUS_BAD_REQUEST = 400
            val STATUS_UNAUTHORIZED = 401
            val STATUS_FORBITTEN = 403
            val STATUS_NOT_FOUND = 404
            val STATUS_SERVER_ERROR = 500
        }
    }
}