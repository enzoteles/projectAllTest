package br.com.mvvm.teste.bussiness.login

import br.com.mvvm.teste.LoginFragment
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.util.FragmentTestUtil.startFragment

@RunWith(RobolectricTestRunner::class)
class LoginFragmentTest {

    lateinit var loginFragment: LoginFragment

    @Before
    fun setUp() {
        loginFragment = LoginFragment()
    }

    @Test
    fun verificationUnitFragment(){
        //startFragment(loginFragment)
        assertNotNull(loginFragment)
    }
}