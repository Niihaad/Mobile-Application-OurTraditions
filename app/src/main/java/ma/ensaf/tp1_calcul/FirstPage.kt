package ma.ensaf.tp1_calcul

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.StateSet.TAG
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import ma.ensaf.tp1_calcul.Modul.user

class FirstPage : AppCompatActivity() {
    val RC_SIGN_IN: Int = 20
    val authh: FirebaseAuth = FirebaseAuth.getInstance()
    val databasee: FirebaseDatabase = FirebaseDatabase.getInstance()
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_page)

        val btnEmail: Button = findViewById(R.id.buttonEmail)
        val btnCalcul: ImageView = findViewById(R.id.imageView)
        val btnFacebook: Button = findViewById(R.id.buttonFacebook)
        FacebookSdk.sdkInitialize(getApplicationContext())
        val callbackManager: CallbackManager = CallbackManager.Factory.create()
        val login_button: LoginButton = findViewById(R.id.login_button)
        val btnGoogle: Button = findViewById(R.id.buttonGoogle)
        btnCalcul.setOnClickListener({
            val intent = Intent(this,MainActivity_first::class.java)
            startActivity(intent)
        })
        val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        login_button.setReadPermissions("email", "public_profile")
        login_button.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    handleFacebookAccessToken(loginResult.accessToken)
                }

                override fun onCancel() {
                    Log.d(TAG, "facebook:onCancel")
                }

                override fun onError(error: FacebookException) {
                    Log.d(TAG, "facebook:onError", error)
                }
            }
        )

        btnEmail.setOnClickListener({
            val intent = Intent(this, AuthenticationMail::class.java)
            startActivity(intent)
        })

        btnGoogle.setOnClickListener({
            googleSignIn()
        })

        btnFacebook.setOnClickListener({
            // Handle Facebook button click
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val callbackManager: CallbackManager = CallbackManager.Factory.create()
        callbackManager.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
                account.idToken?.let { FirebaseAuth(it) }

                val intent = Intent(this, AfterGgleconnexion::class.java)
                startActivity(intent)

            } catch (e: ApiException) {
                Log.e(TAG, "Google Sign-In failed", e)
                Toast.makeText(this, "Google Sign-In failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser? = auth.currentUser
                    val user1: user = user()

                    if (user != null) {
                        user1.userName(user.displayName)
                    }

                    val database: FirebaseDatabase =
                        FirebaseDatabase.getInstance("https://tp1-calcul-default-rtdb.firebaseio.com/")

                    if (user != null) {
                        database.getReference().child("user").child(user.uid).setValue(user1)
                    }

                    val intent = Intent(this, AfterFbconnection::class.java)
                    intent.putExtra("name", user?.displayName?.toString())
                    startActivity(intent)
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        this, "Authentication failed.", Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun googleSignIn() {
        Log.d(TAG, "googleSignIn: Attempting Google Sign-In")

        // Sign out before initiating the sign-in process
        googleSignInClient.signOut().addOnCompleteListener(this) {
            // After signing out, initiate the sign-in process
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }
    private fun FirebaseAuth(idToken: String) {
        val credential: AuthCredential = GoogleAuthProvider.getCredential(idToken, null)
        authh.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Authentication successful
                    // Do something with the result
                    val result: AuthResult? = task.result
                    val user: FirebaseUser? = authh.currentUser
                    val map: HashMap<String, Any> = HashMap()
                    if (user != null) {
                        map["id"] = user.uid
                        map["name"] = user.displayName ?: ""
                        map["profile"] = user.photoUrl?.toString() ?: ""

                        databasee.getReference().child("user").child(user.uid).setValue(map)
                        val intent = Intent(this, AfterGgleconnexion::class.java)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(
                        this, "Something went wrong", Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

}
