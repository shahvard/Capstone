package sheridancollege.proWarriors.Student

import java.io.Serializable

data class Student (val studentId:String? = null,val firstName: String? = null,val lastName: String? = null,val email: String? = null,
val address: String? = null, val phoneNo: String? = null, val isTutor: Boolean? = null)  : Serializable{

}