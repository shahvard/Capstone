package sheridancollege.proWarriors.Student

import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance

public  class StudentEntity {
   companion object {
      lateinit var student: Student
   }

   init {
      student = Student()
   }



}