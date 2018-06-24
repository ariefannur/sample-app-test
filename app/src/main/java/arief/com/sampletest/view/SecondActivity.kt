package arief.com.sampletest.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import arief.com.sampletest.MyApplication
import arief.com.sampletest.R
import arief.com.sampletest.model.Buku
import kotlinx.android.synthetic.main.layout_list.*
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.EditText
import java.util.concurrent.Executors


class SecondActivity : AppCompatActivity() {


    val bukuDao by lazy {
        (application as MyApplication).appDatabase.bukuDao()
    }

    val isAdmin by lazy {
        intent.getBooleanExtra("is_admin", false)
    }

    val dialogBuilder by lazy {
        AlertDialog.Builder(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_list)

        rv_list.layoutManager = LinearLayoutManager(this)

        val dialog = dialogBuku()

        if(isAdmin){
            btn_add.visibility = View.VISIBLE
            btn_add.setOnClickListener {
                dialog.show()
            }

        }else{
            btn_add.visibility = View.GONE
        }

        getData()

    }

    fun getData(){
        bukuDao.getBuku().observe(this, Observer {
            if(it != null) {
                if (it.size > 0 ) {
                    val adapter = Adapter(it)
                    rv_list.adapter = adapter
                    tv_empty.visibility = View.GONE
                }else{
                    tv_empty.visibility = View.VISIBLE
                }

            }
        })
    }

    class Adapter(val list: List<Buku>) :RecyclerView.Adapter<ViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view, null, false))
        }

        override fun getItemCount(): Int {
          return  list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.tvName?.text = list[position].name
            holder.tvQty?.text = list[position].qty.toString()
        }

    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val tvName by lazy {
            itemView?.findViewById<TextView>(R.id.tv_name)
        }

        val tvQty by lazy {
            itemView?.findViewById<TextView>(R.id.tv_qty)
        }

    }

    fun dialogBuku() : AlertDialog{

        val viewDialog = LayoutInflater.from(this).inflate(R.layout.form_buku, null , false)

        val etName = viewDialog.findViewById<EditText>(R.id.et_name)
        val etQty = viewDialog.findViewById<EditText>(R.id.et_qty)

        dialogBuilder.setView(viewDialog)
        dialogBuilder.setPositiveButton("Save", DialogInterface.OnClickListener { dialog, which ->

            val executor = Executors.newSingleThreadExecutor()
            executor.execute(Runnable {
                bukuDao.inserBuku(Buku(0, etName.text.toString(), etQty.text.toString().toInt()))
                getData()
            })

            etName.text.clear()
            etQty.text.clear()
            dialog.dismiss()
        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
        return dialogBuilder.create()
    }
}