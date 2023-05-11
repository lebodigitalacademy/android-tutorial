//package com.example.androidapp
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//
//class Register : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_register2)
//    }
//}

package com.example.androidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Intent;
import android.os.Parcel
import android.os.Parcelable
import android.view.View;
import android.widget.Button
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.jar.Attributes.Name

private lateinit var etUsername: EditText
private lateinit var etPassword: EditText
private lateinit var password: String
private lateinit var userName: String

class Register() : AppCompatActivity(), Parcelable {

//    lateinit var etUsername: EditText
//    lateinit var etPassword: EditText
//    lateinit var btnRegister: Button

    constructor(parcel: Parcel) : this() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //what bind xml is as follows:
        setContentView(R.layout.activity_register)
        etUsername = findViewById(R.id.etRUserName)
        etPassword = findViewById(R.id.etRPassword)
        //btnRegister = findViewById(R.id.btnRegister)
        var etButton = findViewById<Button>(R.id.btnRegister)
        etButton.setOnClickListener{
            registerUser()
            //val Name = etUsername.getText().toString();
            //Toast.makeText(this, Name+"Successfully Registered!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Register> {
        override fun createFromParcel(parcel: Parcel): Register {
            return Register(parcel)
        }

        override fun newArray(size: Int): Array<Register?> {
            return arrayOfNulls(size)
        }
    }

    //Code for registerUser() method
    private fun registerUser() {
        val userName: String = etUsername.getText().toString().trim()
        val password: String = etPassword.getText().toString().trim()
        if (userName.isEmpty()) {
            etUsername.setError("Username is required")
            etUsername.requestFocus()
            return
        }
        else if (password.isEmpty()) {
            etPassword.setError("Password is required")
            etPassword.requestFocus()
            return
        }
        val call: Call<ResponseBody> = RetroFitClient
            .getInstance()
            .api
            .createUser(User(userName, password))
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>?, response: Response<ResponseBody?>) {
                var s = ""
                try {
                    s = response.body()!!.string()
                }
                catch (e: IOException) {
                    e.printStackTrace()
                }
                if (s == "SUCCESS") {
                    Toast.makeText(
                        this@Register,
                        "Successfully registered. Please login",
                        Toast.LENGTH_LONG
                    ).show()
                    startActivity(Intent(this@Register, Login::class.java))
                } else {
                    Toast.makeText(this@Register, "User already exists!", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(this@Register, t.message, Toast.LENGTH_LONG).show()
            }
        })

    }
}