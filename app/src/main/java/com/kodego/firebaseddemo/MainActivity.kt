package com.kodego.firebaseddemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kodego.firebaseddemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var dao = EmployeeDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var dao = EmployeeDao()

        binding.btnSave.setOnClickListener(){
            dao.add(Employee(binding.etName.text.toString(), binding.etSalary.text.toString()))
            Toast.makeText(applicationContext, "Success",Toast.LENGTH_LONG).show()
        }

        binding.btnLoad.setOnClickListener(){
            updateData()
        }

        /*binding.btnDelete.setOnclickListener(){
        * deleteData()*/


    }

    /*
    * private fun deleteData(){
    * dao.remove("")*/

    private fun updateData(){
     var mapData = mutableMapOf<String,String>()
        mapData[""] = ""
        dao.update(key = "",mapData)
    }

    private fun view() {
        dao.get().addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var employees : ArrayList<Employee> = ArrayList<Employee>()
                var dataFromDb = snapshot.children

                for(data in dataFromDb){
                    var id = data.key.toString()
                    var name = data.child("name").value.toString()
                    var salary = data.child("salary").value.toString()

                    var employee = Employee(name, salary)
                    employees.add(employee)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }