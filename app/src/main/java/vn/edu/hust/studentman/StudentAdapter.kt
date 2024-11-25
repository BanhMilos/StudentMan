package vn.edu.hust.studentman

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class StudentAdapter(val students: MutableList<StudentModel>) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

  class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textStudentName: TextView = itemView.findViewById(R.id.text_student_name)
    val textStudentId: TextView = itemView.findViewById(R.id.text_student_id)
    val imageEdit: ImageView = itemView.findViewById(R.id.image_edit)
    val imageRemove: ImageView = itemView.findViewById(R.id.image_remove)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_student_item, parent, false)
    return StudentViewHolder(itemView)
  }

  override fun getItemCount(): Int = students.size

  override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
    val student = students[position]

    holder.textStudentName.text = student.studentName
    holder.textStudentId.text = student.studentId

    holder.imageEdit.setOnClickListener {
      showEditStudentDialog(holder.itemView, student, position)
    }

    holder.imageRemove.setOnClickListener {
      showDeleteStudentDialog(holder.itemView, student, position)
    }
  }

  private fun showEditStudentDialog(view: View, student: StudentModel, position: Int) {
    val dialogView = LayoutInflater.from(view.context).inflate(R.layout.dialog_add_student, null)
    val nameInput = dialogView.findViewById<EditText>(R.id.editTextName)
    val idInput = dialogView.findViewById<EditText>(R.id.editTextId)

    nameInput.setText(student.studentName)
    idInput.setText(student.studentId)

    AlertDialog.Builder(view.context)
      .setTitle("Edit Student")
      .setView(dialogView)
      .setPositiveButton("Save") { _, _ ->
        student.studentName = nameInput.text.toString()
        student.studentId = idInput.text.toString()
        notifyItemChanged(position)
      }
      .setNegativeButton("Cancel", null)
      .show()
  }

  private fun showDeleteStudentDialog(view: View, student: StudentModel, position: Int) {
    AlertDialog.Builder(view.context)
      .setTitle("Delete Student")
      .setMessage("Are you sure you want to delete ${student.studentName}?")
      .setPositiveButton("Yes") { _, _ ->
        val removedStudent = students[position]
        students.removeAt(position)
        notifyItemRemoved(position)

        Snackbar.make(view, "${removedStudent.studentName} removed", Snackbar.LENGTH_LONG)
          .setAction("Undo") {
            students.add(position, removedStudent)
            notifyItemInserted(position)
          }
          .show()
      }
      .setNegativeButton("No", null)
      .show()
  }
}
