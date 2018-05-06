package br.com.mvvm.teste.webservice.domain


data class PessoaComplemento(
    val cod_pessoa_complemento: Int,
    val cod_pessoa: Int,
    val des_estado_civil: String,
    val num_filhos: Int,
    val des_profissao: String,
    val des_meio_transporte: String,
    val vlr_faixa_renda: Int,
    val bol_possui_pet: Boolean,
    val num_quantidade_pet: Int,
    val des_tipo_pet: String,
    val bol_cadastro_completo: Boolean,
    val bol_aceitou_termo: Boolean,
    val bol_proposta_aprovada: Boolean,
    val cod_plu: Int,
    val des_quantidade_pet: String,
    val des_versao_regulamento: String,
    val des_versao_termo_mobile: String,
    val des_versao_termo_site: String,
    val des_versao_termo_totem: String
)