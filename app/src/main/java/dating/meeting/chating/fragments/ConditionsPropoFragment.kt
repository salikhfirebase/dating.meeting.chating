package dating.meeting.chating.fragments


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import dating.meeting.chating.Model.User
import dating.meeting.chating.R
import dating.meeting.chating.db.AppDatabase

// TODO: Rename parameter arguments, choose names that match

/**
 * A simple [Fragment] subclass.
 *
 */
class ConditionsPropoFragment : Fragment() {

    private var the_fragmentMain = androidx.fragment.app.Fragment()
    private lateinit var the_radioGroup: RadioGroup
    private lateinit var the_radioButton: RadioButton
    private lateinit var the_checkedHaveHouse: TextView
    private lateinit var the_checkedHaveCar: TextView
    private lateinit var the_checkedHaveMoto: TextView
    private lateinit var the_makeClear: EditText
    private lateinit var the_nextButton: Button
    private lateinit var the_checkedRadioText: String
    lateinit var db: AppDatabase
    private var the_otherData = ""
    private var the_houseChecked = ""
    private var the_carChecked = ""
    private var the_mohoChecked = ""
    private var the_userId = 0
    var the_user = User()

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_condishions_propo, container, false)

        the_radioGroup = view.findViewById(R.id.radioGroupLast)
        the_checkedHaveHouse = view.findViewById(R.id.have_porke_morke)
        the_checkedHaveCar = view.findViewById(R.id.have_automobile_asd_zxc)
        the_checkedHaveMoto = view.findViewById(R.id.have_bike_wwwer_pro)
        the_makeClear = view.findViewById(R.id.yes_again_oerf_sdsd)
        the_nextButton = view.findViewById(R.id.condishns_sdfg_qwwer_button)
        db = AppDatabase.getInstance(this.requireContext()) as AppDatabase

        Observable.fromCallable { db.userDao().getLastId() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                the_userId = it
            }, {
                Log.d("SaveToDb", "Didn't saved in Registration Fragment", it)
            })

        var useless = 1

        for (i in 0..12) {
            useless++
            useless--
        }

        uselessFun()

        the_nextButton.setOnClickListener {
            the_radioButton = view.findViewById(the_radioGroup.checkedRadioButtonId)
            the_checkedRadioText = the_radioButton.text.toString()
            the_otherData = the_houseChecked + the_carChecked + the_mohoChecked
            userSet()
            if (the_userId != 0) {
                saveToDb()
                the_fragmentMain = LastPrempRegFragment()
                setFragment(the_fragmentMain)
            } else {
                Toast.makeText(this.requireContext(), "Подождите", Toast.LENGTH_SHORT).show()
            }
        }

        the_checkedHaveHouse.setOnClickListener {
            the_houseChecked = if (the_houseChecked == "") {
                    it.setBackgroundColor(Color.parseColor("#F5F5F5"))
                the_checkedHaveHouse.text.toString() + ";"
                } else {
                    it.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    ""
                }
            }
        the_checkedHaveCar.setOnClickListener {
            the_carChecked = if (the_carChecked == "") {
                the_checkedHaveCar.setBackgroundColor(Color.parseColor("#F5F5F5"))
                the_checkedHaveCar.text.toString() + ";"
                } else {
                the_checkedHaveCar.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    ""
                }
            }

        the_checkedHaveMoto.setOnClickListener {
            the_mohoChecked = if (the_mohoChecked == "") {
                    it.setBackgroundColor(Color.parseColor("#F5F5F5"))
                the_checkedHaveMoto.text.toString() + ";"
                } else {
                    it.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    ""
                }
            }


        return view
    }

    fun uselessFun() {
        var i = 2

        i++
        i--
    }


    @SuppressLint("CheckResult")
    fun userSet() {
        the_user.setOtherData(the_otherData)
        the_user.setStatus(the_checkedRadioText)
        the_user.setMakeClear(the_makeClear.text.toString())
    }

    @SuppressLint("CheckResult")
    fun saveToDb() {
        Completable.fromAction { db.userDao().updateotherData(the_user.getOtherData(), the_userId) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {
                Log.d("SaveToDb", "Didn't saved in Registration Fragment", it)
            })
        Completable.fromAction { db.userDao().updateStatus(the_user.getStatus(), the_userId) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {
                Log.d("SaveToDb", "Didn't saved in Registration Fragment", it)
            })
        Completable.fromAction { db.userDao().updateMakeClear(the_user.getMakeClear(), the_userId) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {
                Log.d("SaveToDb", "Didn't saved in Registration Fragment", it)
            })
    }


    private fun setFragment(f: androidx.fragment.app.Fragment) {

        val fm: androidx.fragment.app.FragmentManager = this.requireActivity().supportFragmentManager
        val ft: androidx.fragment.app.FragmentTransaction = fm.beginTransaction()

        ft.replace(R.id.question_container, f)
        ft.commit()

    }



}
