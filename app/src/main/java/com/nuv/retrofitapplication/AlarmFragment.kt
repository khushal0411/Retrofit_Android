package com.nuv.retrofitapplication

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.nuv.retrofitapplication.constant.Constants
import com.nuv.retrofitapplication.databinding.FragmentAlarmBinding
import java.util.*


@Suppress("DEPRECATION")
class AlarmFragment :Fragment() {

    private val calender: Calendar = Calendar.getInstance()
    private lateinit var binding: FragmentAlarmBinding
    private lateinit var channelId:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding= DataBindingUtil.inflate(inflater,R.layout.fragment_alarm,container,false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

          binding.btnTimePicker.setOnClickListener {
              val currentTime = Calendar.getInstance()
              val hour = currentTime.get(Calendar.HOUR_OF_DAY)
              val min = currentTime.get(Calendar.MINUTE)
              val timePicker =
                  TimePickerDialog(context,
                      { _, hourOfDay, minute ->

                          calender.set(Calendar.HOUR_OF_DAY,hourOfDay)
                          calender.set(Calendar.MINUTE,minute)
                          calender.set(Calendar.SECOND,0)
                          calender.set(Calendar.MILLISECOND,0)
                      }, hour, min, false)
              timePicker.show()


          }

        binding.btnSetAlarm.setOnClickListener {
            binding.tvTimeDisplay.text=calender.time.toLocaleString().subSequence(12,calender.time.toLocaleString().length).toString().uppercase()
            val alarmManager:AlarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context,NotificationReceivers::class.java)
            createNotificationChannel()
            intent.putExtra(Constants.CHANNEL_ID,channelId)
            intent.putExtra("S",binding.etNotificationText.text.toString())
            val pendingIntent= PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calender.timeInMillis,pendingIntent)
            val toast:Toast= Toast.makeText(context,"Alarm set for "+calender.time.toLocaleString().toString().uppercase(),Toast.LENGTH_LONG)
            toast.show()

        }

    }



    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        val name ="Notification Alert"
        channelId=Constants.NOTIFICATION
        val importance:Int=NotificationManager.IMPORTANCE_HIGH
        val notificationChannel = NotificationChannel(channelId,name,importance)
            notificationChannel.description="Channel Notification"
        val notificationManager:NotificationManager= requireActivity().getSystemService(NotificationManager::class.java)
           notificationManager.createNotificationChannel(notificationChannel)
        }
        else
        {
            channelId=Constants.NOTIFICATION
        }

    }
}