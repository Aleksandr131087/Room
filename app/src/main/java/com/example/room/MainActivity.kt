package com.example.room

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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
private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

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
private fun addContact(db: ContactDatabase, contact: Contact)= GlobalScope.async { db.contactDao().insert(contact) }

  private  fun readDatabase(db: ContactDatabase)= GlobalScope.async {
       contactsTextView.text=""
        val list = db.contactDao().getAllContacts()
        list.forEach { contact ->
            contactsTextView.append(contact.surname + " " + contact.phoneNumber + "\n")

}
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_exit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_exit -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}