package com.example.room

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    var db: ContactDatabase?=null

    private lateinit var surnameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var contactsTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        surnameEditText = findViewById(R.id.surnameEditText)
        phoneEditText = findViewById(R.id.phoneEditText)
        saveButton = findViewById(R.id.saveButton)
        contactsTextView = findViewById(R.id.contactsTextView)
db=ContactDatabase.getDatabase(this)
        }

    override fun onResume() {
        super.onResume()
        saveButton.setOnClickListener {
            val contact = Contact(surnameEditText.text.toString(), phoneEditText.text.toString())
            addContact(db!!, contact)
            readDatabase(db!!)
        }
    }
@OptIn(DelicateCoroutinesApi::class)
private fun addContact(db: ContactDatabase, contact: Contact)= GlobalScope.async { db.contactDao().insert(contact) }

    @OptIn(DelicateCoroutinesApi::class)
  private  fun readDatabase(db: ContactDatabase)= GlobalScope.async {
       contactsTextView.text=""
        val list = db.contactDao().getAllContacts()
        list.forEach { contact ->
            contactsTextView.append(contact.surname + " " + contact.phoneNumber + "\n")

}
    }
}