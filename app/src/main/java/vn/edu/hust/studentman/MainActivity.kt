package vn.edu.hust.studentman

import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

  private val students = mutableListOf(
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

  private lateinit var studentListView: ListView
  private lateinit var studentAdapter: ArrayAdapter<StudentModel>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    supportActionBar?.show()

    studentListView = findViewById(R.id.listViewStudents)
    studentAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, students)
    studentListView.adapter = studentAdapter

    registerForContextMenu(studentListView)
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.menu_add_new -> {
        showAddStudentDialog()
        return true
      }
      else -> return super.onOptionsItemSelected(item)
    }
  }

  private fun showAddStudentDialog() {
    val builder = AlertDialog.Builder(this)
    builder.setTitle("Add New Student")

    val layout = layoutInflater.inflate(R.layout.dialog_add_student, null)

    val nameInput: EditText = layout.findViewById(R.id.editTextName)
    val idInput: EditText = layout.findViewById(R.id.editTextId)

    builder.setView(layout)

    builder.setPositiveButton("Add") { _, _ ->
      val studentName = nameInput.text.toString()
      val studentId = idInput.text.toString()

      if (studentName.isNotEmpty() && studentId.isNotEmpty()) {
        students.add(StudentModel(studentName, studentId))
        studentAdapter.notifyDataSetChanged()
      } else {
        Toast.makeText(this, "Please enter both name and student ID", Toast.LENGTH_SHORT).show()
      }
    }
    builder.setNegativeButton("Cancel", null)
    builder.show()
  }

  override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
    super.onCreateContextMenu(menu, v, menuInfo)
    menuInflater.inflate(R.menu.context_menu, menu)
  }

  override fun onContextItemSelected(item: MenuItem): Boolean {
    val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
    val position = info.position

    return when (item.itemId) {
      R.id.menu_edit -> {
        showEditStudentDialog(position)
        true
      }
      R.id.menu_remove -> {
        students.removeAt(position)
        studentAdapter.notifyDataSetChanged()
        true
      }
      else -> super.onContextItemSelected(item)
    }
  }

  private fun showEditStudentDialog(position: Int) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle("Edit Student")

    val input = EditText(this)
    input.setText(students[position].studentName)
    builder.setView(input)

    builder.setPositiveButton("Save") { _, _ ->
      val studentName = input.text.toString()
      if (studentName.isNotEmpty()) {
        students[position].studentName = studentName
        students[position].studentId = "SV${position + 1}"  // Update the ID based on position
        studentAdapter.notifyDataSetChanged()
      } else {
        Toast.makeText(this, "Please enter a valid student name", Toast.LENGTH_SHORT).show()
      }
    }
    builder.setNegativeButton("Cancel", null)
    builder.show()
  }
}
