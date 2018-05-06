package br.com.mvvm.teste.bussiness.main

import android.support.v7.app.AppCompatActivity
import br.com.enzoteles.mvvm.util.ManagerFragment
import br.com.mvvm.teste.LoginFragment
import br.com.mvvm.teste.MainActivity
import br.com.mvvm.teste.R
import junit.framework.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.util.FragmentTestUtil

@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    lateinit var loginFragment: LoginFragment
    lateinit var activity: MainActivity
    @Before
    fun setUp() {
        activity = Robolectric.setupActivity(MainActivity::class.java)
        loginFragment = LoginFragment()
    }

    @Test
    fun initUILogin(){
        FragmentTestUtil.startFragment(loginFragment, AppCompatActivity::class.java)
        assertNotNull(loginFragment)
    }
}