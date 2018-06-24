package arief.com.sampletest.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.widget.Toast
import arief.com.sampletest.MyApplication
import arief.com.sampletest.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userDao = (application as MyApplication).appDatabase.userDao()


        btn_login.setOnClickListener {
            if(!et_username.text.isEmpty() && !et_password.text.isEmpty()){

                Log.d("AF", "login ")
                userDao.login(et_username.text.toString(), et_password.text.toString())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(
                        Consumer {

                            if (it?.size!! > 0) {
                                Log.d("AF ", " login berhasil ")
                                val intent = Intent(this, SecondActivity::class.java)
                                intent.putExtra("is_admin", it[0].is_admin)
                                startActivity(intent)
                                finish()
                            } else {
                                Log.d("AF ", " login gagal ")
                                Toast.makeText(this, "login gagal cek username or password", Snackbar.LENGTH_SHORT).show()
                            }

                        } )

            }
        }

    }


}
