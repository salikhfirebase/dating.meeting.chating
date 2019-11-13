package dating.meeting.chating

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dating.meeting.chating.fragments.MainInfoPepeFragment
import dating.meeting.chating.fragments.SignWerSamaUpFragment
import dating.meeting.chating.fragments.SingInSsdfFragment

class QuestionairePerdoleActivity : AppCompatActivity() {


    private var fragmentMain = androidx.fragment.app.Fragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionaire_perdole)

        if (intent.getStringExtra("action") == "registration") {
            fragmentMain = SignWerSamaUpFragment()
        }

        if (intent.getStringExtra("action") == "facebook_login"){
            fragmentMain = MainInfoPepeFragment()
        }

        if (intent.getStringExtra("action") == "sign_in") {
            fragmentMain = SingInSsdfFragment()
        }

        var useless = 1

        for (i in 0..10) {
            useless++
        }

        uselessFun()

        setFragment(fragmentMain)
    }

    private fun setFragment(f: androidx.fragment.app.Fragment) {

        val fm: androidx.fragment.app.FragmentManager = supportFragmentManager
        val ft: androidx.fragment.app.FragmentTransaction = fm.beginTransaction()

        ft.replace(R.id.question_container, f)
        ft.commit()

    }

    fun uselessFun() {
        var i = 0

        i++
        i--
    }
}
