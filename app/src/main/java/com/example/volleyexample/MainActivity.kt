package com.example.volleyexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.volleyexample.pojo.User
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    private var lista= MutableLiveData <ArrayList<User>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        jsonParser()

        lista.observe(this, Observer {
            var cuantos = lista.value!!.size
        })

    }

    private fun jsonParser(){
        val url = "https://jsonplaceholder.typicode.com/posts"

        val requestQueue = Volley.newRequestQueue(this)

        val request = JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener {response ->
                try{
                    var listaUsuarios = ArrayList<User>()
                    for (contador in 0..response.length()-1){
                        val readData = response.getJSONObject(contador)
                        val id = readData.getInt("id")
                        val title = readData.getString("title")
                        val body = readData.getString("body")
                        listaUsuarios.add(User(id, title, body))
                    }
                    lista.value = listaUsuarios
                }
                catch(e: JSONException){
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {error->
                error.printStackTrace()
            })

        requestQueue.add(request)
    }
}