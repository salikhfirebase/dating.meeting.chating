package volts.kershief.chane.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import volts.kershief.chane.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 *
 */
class PolicyMainFragment : Fragment() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_policy_main, container, false)

        webView = view.findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true

        var useless = 1

        for (i in 0..10) {
            useless++
        }

        uselessFun()

        webView.loadUrl("https://bloomyapp.com/terms/")
        return view
    }

    fun uselessFun() {
        var i = 0

        i++
    }

}
