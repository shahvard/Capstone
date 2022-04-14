package sheridancollege.proWarriors.Tutor

import java.io.Serializable

data class Tutor (val studentId:String? = null,val firstName: String? = null,val lastName: String? = null,val email: String? = null,
                  val address: String? = null, val phoneNo: String? = null, val isStudent: Boolean? = null): Serializable{
}