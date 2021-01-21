package com.raywenderlich.hwappv3mini

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import java.io.File

class Assignment {
    var name: String = ""
    var classin: String = ""
    var due: String = ""


}



class MainActivity : AppCompatActivity() {

    var allAsgt = ArrayList<Assignment>()

    private lateinit var Back_button: Button
    private lateinit var Done_button: Button
    private lateinit var Next_button: Button
    private lateinit var Create_button: Button
    private lateinit var Asgt_input: TextInputEditText
    private lateinit var Class_input: TextInputEditText
    private lateinit var Due_input: TextInputEditText
    private lateinit var Asgt_display: TextView
    private lateinit var Class_display: TextView
    private lateinit var Due_display: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//-----------------------------------------------------------------------------------------------------------------------------------------------------------



//-------------------------------------------------------------------------------------------------

        fun loadData() {
            val sharedPreferences = getSharedPreferences("sharpreftest", Context.MODE_PRIVATE)
            val savedasgt = sharedPreferences.getStringSet("ASS_KEY", null)
            val savedclass = sharedPreferences.getStringSet("ClASS_KEY", null)
            val savedidue = sharedPreferences.getStringSet("DUE_KEY", null)
            if (savedasgt != null && savedclass != null && savedidue != null) {
                for (i in 0..(savedasgt.size-1)) {
                    var newass = Assignment()
                    newass.name = savedasgt.first()
                    savedasgt.remove(savedasgt.first())
                    newass.classin = savedclass.first()
                    savedclass.remove(savedclass.first())
                    newass.due = savedidue.first()
                    savedidue.remove(savedidue.first())
                    allAsgt.add(newass)
                }
            }


        }


//-----------------------------------------------------------------------------------------------------------------------------------------------------------

        fun saveData() {

            val assignments = mutableSetOf("Placeholder")
            for (i in allAsgt.indices) {
                assignments.add(allAsgt[i].name)
            }
            assignments.remove("Placeholder")


            val classes = mutableSetOf("Placeholder")
            for (i in allAsgt.indices) {
                classes.add(allAsgt[i].classin)
            }
            classes.remove("Placeholder")


            val duedates = mutableSetOf("Placeholder")
            for (i in allAsgt.indices) {
                duedates.add(allAsgt[i].due)
            }
            duedates.remove("Placeholder")

            val sharedPreferences = getSharedPreferences("sharpreftest", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.apply {
                putStringSet("ASS_KEY", assignments)
                putStringSet("CLASS_KEY", classes)
                putStringSet("DUE_KEY", duedates)

            }.apply()

        }
//-------------------------------------------------------------------------------------------------------------------------------------------------------------





        loadData()

        Back_button = this.findViewById(R.id.GoBack_button)
        Done_button = findViewById(R.id.MarkAsDone_button)
        Next_button = findViewById(R.id.NextAssgnmnt_button)
        Create_button = findViewById(R.id.NewAssgnmnt_button)
        Asgt_input = findViewById(R.id.NameInput)
        Class_input = findViewById(R.id.ClassInput)
        Due_input = findViewById(R.id.DueInput)
        Asgt_display = findViewById(R.id.Display_Asgt)
        Class_display = findViewById(R.id.Display_Class)
        Due_display = findViewById(R.id.Display_Due)


        fun saveall() {
        }


        var i = 0
        if (allAsgt.size > 0) {
            Asgt_display.text = allAsgt[i].name
            Class_display.text = allAsgt[i].classin
            Due_display.text = allAsgt[i].due
        } else {
            Asgt_display.text = "No Assignments Entered"
            Class_display.text = "No Assignments Entered"
            Due_display.text = "No Assignments Entered"
        }


        Create_button.setOnClickListener {
            var newass = Assignment()
            newass.name = Asgt_input.text.toString()
            newass.classin = Class_input.text.toString()
            newass.due = Due_input.text.toString()
            allAsgt.add(newass)
            Asgt_display.text = allAsgt[i].name
            Class_display.text = allAsgt[i].classin
            Due_display.text = allAsgt[i].due

            saveData()
        }

        Next_button.setOnClickListener {
            if (i == allAsgt.size - 1) {
                i = 0;
                Asgt_display.text = allAsgt[i].name
                Class_display.text = allAsgt[i].classin
                Due_display.text = allAsgt[i].due
            } else {
                i++
                Asgt_display.text = allAsgt[i].name
                Class_display.text = allAsgt[i].classin
                Due_display.text = allAsgt[i].due
            }

        }

        Back_button.setOnClickListener {
            if(allAsgt.size > 0) {
                if (i == 0) {
                    i = allAsgt.size - 1;
                    Asgt_display.text = allAsgt[i].name
                    Class_display.text = allAsgt[i].classin
                    Due_display.text = allAsgt[i].due
                } else {
                    i--
                    Asgt_display.text = allAsgt[i].name
                    Class_display.text = allAsgt[i].classin
                    Due_display.text = allAsgt[i].due
                }
            }
            else {
                Asgt_display.text = ""
                Class_display.text =""
                Due_display.text = ""
            }



        }

        var doneasgts = 0;
        var wipasgts = 0;

        Done_button.setOnClickListener {

            allAsgt[i].due = "COMPLETED"

            System.out.println(allAsgt[i].due)

            doneasgts = 0
            wipasgts = 0
            for (i in allAsgt.indices) {
                if (allAsgt[i].due.equals("COMPLETED")) {
                    doneasgts++
                } else {
                    wipasgts++
                }
            }
            Asgt_display.text = allAsgt[i].name
            Class_display.text = allAsgt[i].classin
            Due_display.text = allAsgt[i].due

            allAsgt.remove(allAsgt[i])

            saveData()


        }


    }
}
