package com.example.projecthomedecor

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import kotlinx.android.synthetic.main.activity_edit_item_admin.*
import kotlinx.android.synthetic.main.fragment_add_items_admin.*
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import java.io.ByteArrayOutputStream
import java.io.InputStream

class EditItemAdminActivity : AppCompatActivity() {
    var productID: String? = null
    override fun onCreate(savedInstanceState: Bundle?,) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_item_admin)
        supportActionBar?.hide()

        productID = intent.getStringExtra("productId").toString()
        val productN = intent.getStringExtra("productName").toString()
        val productP = intent.getStringExtra("productPrice").toString()
        val productA = intent.getStringExtra("balancestock").toString()
        edit_product_id.setText(productID)
        edit_product_id.isEnabled = false
        edit_name_product.setText(productN)
        edt_price_product.setText(productP)
        edt_warehouse_product.setText(productA)


        var room = DatabaseApi.getProductType(productID!!)
        //Toast.makeText(applicationContext,room,Toast.LENGTH_LONG).show()
        if ("ห้องนอน" in room.toString()) {
            cb_edit_bedroom.isChecked = true
        }
        if ("ห้องทำงาน" in room.toString()) {
            cb_edit_workroom.isChecked = true
        }
        if ("ห้องเก็บของ" in room.toString()) {
            cb_edit_storeroom.isChecked = true
        }
        if ("ห้องครัว" in room.toString()) {
            cb_edit_chickenroom.isChecked = true
        }
        if ("ห้องนั่งเล่น" in room.toString()) {
            cb_edit_livingroom.isChecked = true
        }
        if ("ห้องน้ำ" in room.toString()) {
            cb_edit_bathroom.isChecked = true
        }
        if ("ห้องรับแขก" in room.toString()) {
            cb_edit_drawingroom.isChecked = true
        }
        if ("ห้องอาหาร" in room.toString()) {
            cb_edit_foodroom.isChecked = true
        }
        Glide.with(this).load("http://pc.mosmai.me:10080/product/${productID}/1.jpg").signature(
            ObjectKey(System.currentTimeMillis().toString())
        ).skipMemoryCache(true).into(edtimage)
    }

    var imgnum: String? = null
    var img1Uri: Uri? = null

    fun addimage1(view: View) {
        imgnum = "pic1"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED
            ) {
                //permission denied
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                //show popup to request runtime permission
                requestPermissions(permissions, EditItemAdminActivity.PERMISSION_CODE);
            } else {
                //permission already granted
                pickImageFromGallery();
            }
        } else {
            //system OS is < Marshmallow
            pickImageFromGallery();
        }

    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, EditItemAdminActivity.IMAGE_PICK_CODE)
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;

        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            EditItemAdminActivity.PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup granted
                    pickImageFromGallery()
                } else {
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == EditItemAdminActivity.IMAGE_PICK_CODE) {
            if (imgnum == "pic1") {
                edtimage.setImageURI(data?.data)
                img1Uri = data?.data
            }


            var mos = data?.data
            var a = mos?.path as String

            DatabaseApi
        }
    }

    private fun encodeImage(bm: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }


    fun clickUpdateProduct(view: View) {

        var typeArray = arrayListOf<Int>()
        var type_product_add: String = ""

        if (cb_edit_bedroom.isChecked) {
            typeArray.add(1)
            type_product_add += " " + cb_edit_bedroom.text;
        }
        if (cb_edit_workroom.isChecked) {
            typeArray.add(2)
            type_product_add += " " + cb_edit_workroom.text;
        }
        if (cb_edit_storeroom.isChecked) {
            typeArray.add(3)
            type_product_add += " " + cb_edit_storeroom.text;
        }
        if (cb_edit_chickenroom.isChecked) {
            typeArray.add(4)
            type_product_add += " " + cb_edit_chickenroom.text;
        }
        if (cb_edit_livingroom.isChecked) {
            typeArray.add(5)
            type_product_add += " " + cb_edit_livingroom.text;
        }
        if (cb_edit_bathroom.isChecked) {
            typeArray.add(6)
            type_product_add += " " + cb_edit_bathroom.text;
        }
        if (cb_edit_drawingroom.isChecked) {
            typeArray.add(7)
            type_product_add += " " + cb_edit_drawingroom.text;
        }
        if (cb_edit_foodroom.isChecked) {
            typeArray.add(8)
            type_product_add += " " + cb_edit_foodroom.text;
        }

        var type_product_add_new =
            if (type_product_add.isNotEmpty()) type_product_add else "Please select type product."
        var edit_name_product = edit_name_product.text.toString()
        var edt_price_product = edt_price_product.text.toString().toFloat()
        var edt_warehouse_product = edt_warehouse_product.text.toString().toInt()

        var imgLink = arrayListOf<Int>()
        if (img1Uri != null) {
            imgLink.add(1)
        }
        println(productID+"--------------------------------")
        DatabaseApi.removeProduct(productID!!)
        var productid = DatabaseApi.addProduct(
            edit_name_product,
            edt_price_product,
            imgLink.toString(),
            typeArray.toString(),
            edt_warehouse_product,
            productID.toString()
        )
        println(productid+"+++++++++++++++++++++++++++++++++")

        if (img1Uri != null) {
            val imageStream: InputStream? = contentResolver.openInputStream(img1Uri!!)
            val selectedImage = BitmapFactory.decodeStream(imageStream)
            val encodedImage = encodeImage(selectedImage) as String
            Fuel.post("http://pc.mosmai.me:10080/product.php?tokenid=${Global.accessToken.toString()}&product_id=${productid}&img_num=1")
                .header(Headers.CONTENT_TYPE, "application/json")
                .body("${encodedImage}")
                .also { /*println(it) */ }
                .response { result ->
                    Toast.makeText(
                        applicationContext,
                        result.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
        Toast.makeText(applicationContext, "แก้ไขสินค้าในคลังสำเร็จ", Toast.LENGTH_LONG).show()
    }

    fun clickDeleteProduct(view: View) {
        val myBuilder = AlertDialog.Builder(this)
        myBuilder.apply {
            setTitle("ลบสินค้าในคลังสินค้า")
            setMessage("คุณต้องการลบสินค้าในคลังสินค้าหรือไม่?")
            setNegativeButton("ใช่") { dialog, which ->
                DatabaseApi.removeProduct(productID!!)
                Toast.makeText(applicationContext, "ลบรายการสินค้าเรียบร้อยแล้ว", Toast.LENGTH_LONG)
                    .show()

            }
            setPositiveButton("ไม่") { dialog, which -> dialog.cancel() }
            show()
        }

    }


}