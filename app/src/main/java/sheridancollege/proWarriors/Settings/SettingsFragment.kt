package sheridancollege.proWarriors.Settings

import android.content.Intent
import android.os.Bundle
import android.provider.Settings.*

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragment
import sheridancollege.proWarriors.R



class SettingsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_settings, container, false)



        // Handle wireless settings button
        view.findViewById<Button>(R.id.wireless_settings).setOnClickListener(View.OnClickListener{
            val i = Intent(ACTION_WIRELESS_SETTINGS)
            startActivity(i) })

        // Handle wifi settings button
        view.findViewById<Button>(R.id.wifi_settings).setOnClickListener(View.OnClickListener{
            val i = Intent(ACTION_WIFI_SETTINGS)
            startActivity(i) })

        // Handle bluetooth settings button
        view.findViewById<Button>(R.id.bluetooth_settings).setOnClickListener(View.OnClickListener{
            val i = Intent(ACTION_BLUETOOTH_SETTINGS)
            startActivity(i) })

        // Handle date settings button
        view.findViewById<Button>(R.id.date_settings).setOnClickListener(View.OnClickListener{
            val i = Intent(ACTION_DATE_SETTINGS)
            startActivity(i) })

        // Handle input method settings button
        view.findViewById<Button>(R.id.input_method_settings).setOnClickListener(View.OnClickListener{
            val i
                    = Intent(ACTION_INPUT_METHOD_SETTINGS)
            startActivity(i) })

        // Handle display settings button
        view.findViewById<Button>(R.id.display_settings).setOnClickListener(View.OnClickListener{
            val i = Intent(ACTION_DISPLAY_SETTINGS)
            startActivity(i) })

        // Handle location settings button
        view.findViewById<Button>(R.id.location_settings).setOnClickListener(View.OnClickListener{

            })

        view.findViewById<Switch>(R.id.darkModeSwitch).setOnCheckedChangeListener({_,isChecked ->
            if(isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            }
        })

        return view

    }


    }


