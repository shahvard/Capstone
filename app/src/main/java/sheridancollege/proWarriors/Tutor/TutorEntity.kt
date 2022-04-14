package sheridancollege.proWarriors.Tutor

import sheridancollege.proWarriors.Student.Student

class TutorEntity {
    companion object {
        lateinit var tutor: Tutor
    }

    init {
        tutor = Tutor()
    }
}