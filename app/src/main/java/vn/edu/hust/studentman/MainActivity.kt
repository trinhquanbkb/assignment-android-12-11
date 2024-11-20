package vn.edu.hust.studentman

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import vn.edu.hust.studentman.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), DialogPopUp, View.OnClickListener {
  private lateinit var binding: ActivityMainBinding
  val students = mutableListOf(
    StudentModel("Nguyễn Văn An", "SV001"),
    StudentModel("Trần Thị Bảo", "SV002"),
    StudentModel("Lê Hoàng Cường", "SV003"),
    StudentModel("Phạm Thị Dung", "SV004"),
    StudentModel("Đỗ Minh Đức", "SV005"),
    StudentModel("Vũ Thị Hoa", "SV006"),
    StudentModel("Hoàng Văn Hải", "SV007"),
    StudentModel("Bùi Thị Hạnh", "SV008"),
    StudentModel("Đinh Văn Hùng", "SV009"),
    StudentModel("Nguyễn Thị Linh", "SV010"),
    StudentModel("Phạm Văn Long", "SV011"),
    StudentModel("Trần Thị Mai", "SV012"),
    StudentModel("Lê Thị Ngọc", "SV013"),
    StudentModel("Vũ Văn Nam", "SV014"),
    StudentModel("Hoàng Thị Phương", "SV015"),
    StudentModel("Đỗ Văn Quân", "SV016"),
    StudentModel("Nguyễn Thị Thu", "SV017"),
    StudentModel("Trần Văn Tài", "SV018"),
    StudentModel("Phạm Thị Tuyết", "SV019"),
    StudentModel("Lê Văn Vũ", "SV020")
  )
  val studentAdapter = StudentAdapter(students)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    studentAdapter.dialogController = this

    binding.recyclerViewStudents.run {
      adapter = studentAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
    }


    binding.btnAddNew.setOnClickListener {
      createAddDialog("", "")
    }

  }
  @SuppressLint("NotifyDataSetChanged")
  fun createAddDialog(name: String, code: String) {

  }

  override fun addEditStudentDialog(studentName: String, studentCode: String, position: Int?) {
    val name = EditText(this).apply {
      hint = "Fill name..."
      if (studentName.isNotEmpty()) setText(studentName)
    }
    val code = EditText(this).apply {
      hint = "Fill student code..."
      if (studentCode.isNotEmpty()) setText(studentCode)
    }
    val layout = LinearLayout(this).apply {
      orientation = LinearLayout.VERTICAL
      setPadding(50, 40, 50, 10)
      addView(name)
      addView(code)
    }
    val dialog = AlertDialog.Builder(this)
      .setMessage("Please fill")
      .setTitle("Input")
      .setView(layout)
      .setPositiveButton("Submit") { dialog, _->

        val nameInput = name.text.toString().trim()
        val studentCodeInput = code.text.toString().trim()

        if (nameInput.isNotEmpty() && studentCodeInput.isNotEmpty()) {
          if (position == null) {
            students.add(StudentModel(nameInput, studentCodeInput))
          } else {
            students[position] = StudentModel(nameInput, studentCodeInput)
          }
          studentAdapter.notifyDataSetChanged()
          return@setPositiveButton
        }
        dialog.dismiss()
        Toast.makeText(this, "Input is invalid", Toast.LENGTH_SHORT).show()
      }
      .setNegativeButton("Cancel") { dialog, _ ->
        dialog.dismiss()
      }
      .create()
    dialog.show()
  }

  override fun deleteStudentDialog(position: Int) {
    val dialog = AlertDialog.Builder(this)
      .setTitle("Delete")
      .setMessage("Do you want to delete this student")
      .setPositiveButton ("Delete"){ _, _ ->
        val student = students[position]
        students.remove(student)
        studentAdapter.notifyDataSetChanged()
        val mySnackbar = Snackbar.make(binding.root, "Do you want to undo ?", 3000)
        mySnackbar.setAction("Undo", object : View.OnClickListener {
          override fun onClick(view: View?) {
            students.add(position, student)
            studentAdapter.notifyDataSetChanged()
          }
        })
        mySnackbar.show()
      }
      .setNegativeButton("Cancel") { dialog, _ ->
        dialog.dismiss()
      }
    dialog.show()
  }

  override fun onClick(view: View?) {
  }
}