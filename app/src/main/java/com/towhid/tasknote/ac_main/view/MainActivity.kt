package com.towhid.tasknote.ac_main.view

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.towhid.tasknote.R
import com.towhid.tasknote.ac_main.listener.TaskClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope , TaskClickListener {

    lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main


    var productViewModel: ProductViewModel? = null
    var add: ImageView? = null
    var rec_product: RecyclerView? = null
    var recylerAdapterProduct: RecylerAdapterProduct? = null
    var productList: List<Product>? = null
    var empty_text: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
        setContentView(R.layout.activity_main)

        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        rec_product = findViewById<View>(R.id.rec_product) as RecyclerView
        productList = ArrayList<Product>()
        recylerAdapterProduct = RecylerAdapterProduct(this, this, productList)
        rec_product!!.layoutManager = LinearLayoutManager(this)
        rec_product!!.adapter = recylerAdapterProduct
        viewAllProducts()

        add!!.setOnClickListener {
            val dialog = Dialog(this@MainActivity)
            dialog.setContentView(R.layout.dialog_add_product)
            dialog.create()
            dialog.findViewById<View>(R.id.add_button).setOnClickListener {
                val productName = dialog.findViewById<EditText>(R.id.product_name)
                val emergency = dialog.findViewById<CheckBox>(R.id.emergency_button)
                productViewModel.insertProduct(
                    Product(
                        productName.text.toString(),
                        emergency.isChecked
                    )
                )
                dialog.dismiss()
            }
            dialog.show()
        }
        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteProduct(viewHolder.adapterPosition)
            }
        }).attachToRecyclerView(rec_product)


    }

    private fun deleteProduct(position: Int) {
        productViewModel.deleteProduct(productList!![position])
    }

    private fun viewAllProducts() {
        productViewModel.getAllProdects().observe(this@MainActivity,
            Observer<List<Any?>> { products ->
                if (products.isEmpty()) empty_text!!.visibility =
                    View.VISIBLE else empty_text!!.visibility =
                    View.GONE
                productList.clear()
                productList.addAll(products)
                recylerAdapterProduct.notifyDataSetChanged()
                //  Toast.makeText(MainActivity.this, products.size() + "", Toast.LENGTH_SHORT).show();
            })
    }

    override fun onItemClick(product: Product) {
        val dialog = Dialog(this@MainActivity)
        dialog.setContentView(R.layout.dialog_update_product)
        dialog.create()
        val productName = dialog.findViewById<EditText>(R.id.product_name)
        val emergency = dialog.findViewById<CheckBox>(R.id.emergency_button)
        productName.setText(product.getProductName())
        emergency.isChecked = product.isEmergency()
        dialog.findViewById<View>(R.id.update_button).setOnClickListener {
            product.setProductName(productName.text.toString())
            product.setEmergency(emergency.isChecked)
            productViewModel.updateProduct(product)
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}