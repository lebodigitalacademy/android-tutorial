package com.example.androidapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

private lateinit var etUsername: EditText
private lateinit var etPassword: EditText
private lateinit var password: String
private lateinit var userName: String

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etUsername = findViewById(R.id.etUserName)
        etPassword = findViewById(R.id.etPassword)
        var etButton = findViewById<Button>(R.id.btnLogin)

        etButton.setOnClickListener{
            loginUser()
        }
    }

    private fun loginUser() {
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
            .checkUser(User(userName, password))

        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>?, response: Response<ResponseBody?>) {
                var s = ""
                try {
                    s = response.body()!!.string()
                }
                catch (e: IOException) {
                    e.printStackTrace()
                }
                if (s == userName) {
                   val intent = Intent(this@Login,Dashboard::class.java)
                   intent.putExtra("Username",s)
                    Toast.makeText(
                        this@Login,
                        "Successfully logged in",
                        Toast.LENGTH_LONG
                    ).show()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@Login, "User does not exist", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(this@Login, t.message, Toast.LENGTH_LONG).show()
            }
        })

    }

}