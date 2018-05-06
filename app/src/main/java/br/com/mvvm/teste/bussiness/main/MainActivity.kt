package br.com.mvvm.teste

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.enzoteles.mvvm.app.ApplicationMVVM
import br.com.enzoteles.mvvm.util.ManagerFragment

class MainActivity : AppCompatActivity() {

    lateinit var manager: ManagerFragment
    lateinit var login: LoginFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ApplicationMVVM.mActivity = this
        login = LoginFragment()
        manager = ManagerFragment()
        manager.addFragment(R.id.content, login, "login", false)
    }

}
