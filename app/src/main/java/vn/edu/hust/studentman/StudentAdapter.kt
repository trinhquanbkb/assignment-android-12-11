package vn.edu.hust.studentman

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(val students: List<StudentModel>): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

  lateinit var dialogController: DialogPopUp

  inner class StudentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val textStudentName: TextView = itemView.findViewById(R.id.text_student_name)
    val textStudentId: TextView = itemView.findViewById(R.id.text_student_id)
    val imageEdit: ImageView = itemView.findViewById(R.id.image_edit)
    val imageRemove: ImageView = itemView.findViewById(R.id.image_remove)
    fun onBind(position: Int) {
      imageEdit.setOnClickListener {
        dialogController.addEditStudentDialog(students[position].studentName, students[position].studentId, position)
      }
      imageRemove.setOnClickListener {
        dialogController.deleteStudentDialog(position)
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_student_item,
       parent, false)
    return StudentViewHolder(itemView)
  }

  override fun getItemCount(): Int = students.size

  override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
    val student = students[position]

    holder.onBind(position)

    holder.textStudentName.text = student.studentName
    holder.textStudentId.text = student.studentId
  }
}
interface DialogPopUp {
  fun addEditStudentDialog(studentName: String, studentCode: String, position: Int?);
  fun deleteStudentDialog(position: Int);
}