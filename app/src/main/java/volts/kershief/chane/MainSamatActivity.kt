package volts.kershief.chane

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import com.facebook.*
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import volts.kershief.chane.Model.User
import volts.kershief.chane.db.AppDatabase

class MainSamatActivity : AppCompatActivity() {

    private lateinit var facebookLogin: LoginButton


    private lateinit var callbackManager: CallbackManager

    private lateinit var the_intent1:Intent
    private var the_user = User()
    private lateinit var db: AppDatabase
    var the_userEmail: String = "didn't get"
    var the_handler = Handler()
    val the_REFERRER_DATA = "REFERRER_DATA"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(applicationContext)
        setContentView(R.layout.activity_main_samat)
        ButterKnife.bind(this)
        AppEventsLogger.activateApp(this)
        facebookLogin = findViewById(R.id.facebook_trampam_button)

        facebookLogin.setReadPermissions("email")

        callbackManager = CallbackManager.Factory.create()
        db = AppDatabase.getInstance(this) as AppDatabase

        var useless = 1

        for (i in 0..10) {
            useless++
        }

        uselessFun()

        LoginManager.getInstance().logOut()

        LoginManager.getInstance().registerCallback(callbackManager, object: FacebookCallback<LoginResult>{

            override fun onError(error: FacebookException?) {
                Log.d("MainSamatActivity", error.toString(), error)
            }

            override fun onCancel() {
                Toast.makeText(this@MainSamatActivity, "Авторизация отменена", Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess(result: LoginResult) {
                getUserEmail(AccessToken.getCurrentAccessToken())
                the_handler.postDelayed({ isUserInDb(the_userEmail) }, 1000)
            }

        })

        facebookLogin.setOnClickListener {
            if (AccessToken.getCurrentAccessToken() == null) {
                //Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getUserEmail(token: AccessToken) {

        val request: GraphRequest = GraphRequest.newMeRequest(
            token
        ) { `object`, response ->
            Log.v("MainSamatActivity", response.toString())

            if (`object` != null) {
                the_userEmail = `object`.getString("email")
            }
        }

        val parameters = Bundle()
        parameters.putString("fields", "email")
        request.parameters = parameters
        request.executeAsync()
    }

    fun onCreateAccButtonClick(v: View) {

        the_intent1 = Intent(this, QuestionaireMainQwerActivity::class.java)
        the_intent1.putExtra("action", "registration")
        startActivity(the_intent1)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode,resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    @SuppressLint("CheckResult")
    fun saveToDb(user: User) {

        Completable.fromAction { db.userDao().insert(user) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {
                Log.d("SaveToDb", "Didn't saved in Registration Fragment", it)
            })

    }

    @SuppressLint("CheckResult")
    fun isUserInDb(email: String) {

        Observable.fromCallable{ db.userDao().isEmailInDb(email) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it>0) {
                    the_intent1 = Intent(this, PersonalCabinaActivity::class.java)
                    the_intent1.putExtra("log_in", email)
                    the_intent1.putExtra("reg", 0)
                    startActivity(the_intent1)
                } else {
                    the_user.setEmail(email)
                    saveToDb(the_user)
                    the_intent1 = Intent(this, QuestionaireMainQwerActivity::class.java)
                    the_intent1.putExtra("action", "facebook_login")
                    startActivity(the_intent1)
                }
            }

    }

    fun onSignInButtonClick(v: View) {
        the_intent1 = Intent(this, QuestionaireMainQwerActivity::class.java)
        the_intent1.putExtra("action", "sign_in")
        startActivity(the_intent1)
    }


    fun uselessFun() {
        var i = 0

        i++
    }

}
