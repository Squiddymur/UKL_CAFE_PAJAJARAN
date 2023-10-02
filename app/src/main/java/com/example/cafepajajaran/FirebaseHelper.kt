import com.example.cafepajajaran.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object FirebaseHelper {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    fun createAccount(email: String, password: String, userType: String, onComplete: (Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                onComplete(task.isSuccessful)
            }
    }
    fun Login(email: String, password: String, onComplete: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                onComplete(task.isSuccessful)
            }
    }

    fun getCurrentUser(): User? {
        val currentUser = auth.currentUser
        return if (currentUser != null) {
            User(currentUser.uid, currentUser.email ?: "", "")
        } else {
            null
        }
    }

    fun signOut() {
        auth.signOut()
    }

    fun getUserType(uid: String, onComplete: (String) -> Unit) {
        val userRef = database.child("users").child(uid)
        userRef.child("userType").get().addOnSuccessListener {
            onComplete(it.value.toString())
        }
    }
    fun saveUserTypeToDatabase(uid: String, userType: String, onComplete: (Boolean) -> Unit) {
        val userRef = database.child("users").child(uid).child("userType")
        userRef.setValue(userType)
            .addOnCompleteListener { task ->
                onComplete(task.isSuccessful)
            }
    }
}
