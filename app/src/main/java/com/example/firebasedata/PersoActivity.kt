package com.example.firebasedata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class PersoActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var urlEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var nameTextView: TextView
    private lateinit var imageView: ImageView
    private lateinit var logoutButton: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var db:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perso)

        mAuth = FirebaseAuth.getInstance()


        //getReference shi unda mivutitot romel kods vqmnit am shemtxvevashi userinfos
        db = FirebaseDatabase.getInstance().getReference("UserInfo")
//        db = FirebaseDatabase.getInstance().getReference("UserInfo")

        nameEditText = findViewById(R.id.nameEditText)
        urlEditText = findViewById(R.id.urlEditText)
        saveButton = findViewById(R.id.saveButton)
        nameTextView = findViewById(R.id.nameTextView)
        imageView = findViewById(R.id.imageView)
        logoutButton = findViewById(R.id.loginButton)




        logoutButton.setOnClickListener {
            mAuth.signOut()

            //currentusr und agaxdes null toli

            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

            saveButton.setOnClickListener {
                val name = nameEditText.text.toString()
                val url = urlEditText.text.toString()
                val personInfo = PersonInfo(name, url)
//                val personInfo = PersonInfo(name,url)

                //person info bazashi rom gavagzavnot amisatvis childshi unda chavwerot useer id romelic chawerilia mAuthshi


                if (mAuth.currentUser?.uid != null) {


//                    db.child(mAuth.currentUser?.uid!!).setValue(personInfo).addOnCompleteListener { task ->
//                        if (task.isSuccessful){
//                            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
//                            //edit text da url edit text gavasuftavot
//                            nameEditText.text = null
//                            urlEditText.text = null
                    db.child(mAuth.currentUser?.uid!!).setValue(personInfo).addOnCompleteListener {
                        task ->
                        if (task.isSuccessful){
                            nameEditText.text = null
                            urlEditText.text = null


                        }else{
                            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
                        }
                    }


                }
            }

        if (mAuth.currentUser?.uid != null){

            db.child(mAuth.currentUser?.uid!!).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val p = snapshot.getValue(PersonInfo::class.java)
                    if (p != null){

                        nameTextView.text = p.name

                        //davaimportet glide aqvs metodi with gadaecema konteqsti (this) avqs kide load metodi gadaecema url saidanc chaitvirteba
                        //surati centrecrop centrshi ro moeqces xolo placeholderia methodi romelshic shegviddzlia default surati chavsvat im
                        //shemtxvevashi url rom ar gadaeces da aqvs saboloo metodi into tu sad unda moxdes suratis chatvirtva

                        Glide.with(this@PersoActivity)
                                .load(p.url)
                                .centerCrop()
                                .placeholder(R.drawable.ic_launcher_foreground)
                                .into(imageView)

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@PersoActivity, "error!!", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }
}