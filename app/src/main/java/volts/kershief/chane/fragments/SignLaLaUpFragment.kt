package volts.kershief.chane.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import volts.kershief.chane.Model.User
import volts.kershief.chane.R
import volts.kershief.chane.db.AppDatabase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 *
 */
class SignLaLaUpFragment : Fragment() {

    private var the_fragmentMain = androidx.fragment.app.Fragment()
    private lateinit var db: AppDatabase
    private lateinit var the_emailEditText: EditText
    private lateinit var the_passEditText: EditText
    private var the_user = User()
    private lateinit var the_createAccButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_la_la_up, container, false)

        db = AppDatabase.getInstance(this.requireContext()) as AppDatabase
        the_emailEditText = view.findViewById(R.id.email_sign_up_user_edit_text)
        the_passEditText = view.findViewById(R.id.password_sign_up_user_edit_text)
        the_createAccButton = view.findViewById(R.id.sign_up_button_in_signup_screen)

        var useless = 1

        for (i in 0..10) {
            useless++
        }

        uselessFun()

        the_createAccButton.setOnClickListener {
            if ((the_passEditText.text.toString() == "") || (the_emailEditText.text.toString() == "")) {
                Toast.makeText(this.requireContext(), "Заполните поля", Toast.LENGTH_SHORT).show()
            } else {
                isUserInDb(the_emailEditText.text.toString(), the_passEditText.text.toString())
            }
        }

        return view
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
    fun isUserInDb(email: String, password: String) {

        Observable.fromCallable{ db.userDao().isEmailInDb(email) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it>0) {
                    Toast.makeText(this.requireContext(), "Адрес уже занят", Toast.LENGTH_SHORT).show()
                } else {
                    the_user.setEmail(email)
                    the_user.setPassword(password)
                    saveToDb(the_user)
                    the_fragmentMain = MainInfoLaLaFragment()
                    setFragment(the_fragmentMain)
                }
            }

    }

    private fun setFragment(f: androidx.fragment.app.Fragment) {

        val fm: androidx.fragment.app.FragmentManager = this.requireActivity().supportFragmentManager
        val ft: androidx.fragment.app.FragmentTransaction = fm.beginTransaction()

        ft.replace(R.id.question_container, f)
        ft.commit()

    }

    fun uselessFun() {
        var i = 0

        i++
    }


}
