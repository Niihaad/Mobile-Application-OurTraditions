package ma.ensaf.tp1_calcul

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import org.mariuszgromada.math.mxparser.Expression
import java.text.DecimalFormat
class MainActivity2 : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var variable: String
    private lateinit var input: TextView
    private lateinit var output: TextView
    private var storedValue: Double = 0.0
    private var mrValue: Double = 0.0
    private var lastCharEntered: Char = ' '
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        input = findViewById(R.id.textView2)
        output = findViewById(R.id.textView)
        val egale: Button = findViewById(R.id.egale)
        val clearButton: Button = findViewById(R.id.button_clear)
        val clearElement: Button = findViewById(R.id.button_clearelement)
        val number0: Button = findViewById(R.id.button_0)
        val number1: Button = findViewById(R.id.button_1)
        val number2: Button = findViewById(R.id.button_2)
        val number3: Button = findViewById(R.id.button_3)
        val number4: Button = findViewById(R.id.button_4)
        val number5: Button = findViewById(R.id.button_5)
        val number6: Button = findViewById(R.id.button_6)
        val number7: Button = findViewById(R.id.button_7)
        val number8: Button = findViewById(R.id.button_8)
        val number9: Button = findViewById(R.id.button_9)
        val virgule: Button = findViewById(R.id.virgule)
        val modulo: Button = findViewById(R.id.modulo)
        val addition: Button = findViewById(R.id.plus)
        val sustraction: Button = findViewById(R.id.moin)
        val division: Button = findViewById(R.id.devision)
        val fois: Button = findViewById(R.id.fois)
        val Mplus: Button = findViewById(R.id.button_mpls)
        val Mmoins: Button = findViewById(R.id.button_mmoins)
        val MC: Button = findViewById(R.id.button_mc)
        val MR: Button = findViewById(R.id.button_mr)
        val sin: Button=findViewById(R.id.sin)
        val cos : Button=findViewById(R.id.cos)
        val sin1: Button=findViewById(R.id.sin1)
        val cos1 : Button=findViewById(R.id.cos1)
        val tan: Button=findViewById(R.id.tan)
        val tan1 : Button=findViewById(R.id.tan1)
        val ln: Button=findViewById(R.id.ln)
        val exp : Button=findViewById(R.id.exp)
        val x_n : Button=findViewById(R.id.x_n)
        val x_2 : Button=findViewById(R.id.x2)
        val inverse : Button=findViewById(R.id.inverse_x)
        val fact:Button=findViewById(R.id.x_fact)
        val racine:Button=findViewById(R.id.racine)
        val e:Button=findViewById(R.id.e)
        val pi:Button=findViewById(R.id.pi)
        val abs:Button=findViewById(R.id.x_absolu)

        val parenthese : Button = findViewById(R.id.parenthese)
        val drawerlayout : DrawerLayout = findViewById(R.id.drawer_layout)
        val navigationView : NavigationView = findViewById(R.id.nav_view)
        val toolbar : Toolbar = findViewById(R.id.toolbar)
        //   val toggle : ActionBarDrawerToggle =  ActionBarDrawerToggle(this, drawerlayout,toolbar)   //ActionBarDrawerToggle()
        toggle = ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerlayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)

        navigationView.bringToFront()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            if (drawerlayout.isDrawerOpen(GravityCompat.START)) {
                drawerlayout.closeDrawer(GravityCompat.START)
            } else {
                drawerlayout.openDrawer(GravityCompat.START)
            }
        }

        egale.setOnClickListener {
            showResult()
        }

        clearButton.setOnClickListener {
            input.text = ""
            output.text = ""
        }

        clearElement.setOnClickListener {
            val opr = listOf("sqrt", "exp", "ln", "atan", "asin", "acos", "cos", "sin", "tan")

            val inputText = input.text.toString()

            if (inputText.isNotEmpty()) {
                val words = inputText.split(" ")  // Divise l'entrée en mots
                if (words.isNotEmpty()) {
                    val lastWord = words.last()
                    if (opr.contains(lastWord)) {
                        // Si le dernier mot est un opérateur, supprimez-le
                        val updatedInput = inputText.substring(0, inputText.length - lastWord.length).trim()
                        input.text = updatedInput
                        output.text = ""
                        lastCharEntered = ' '
                        return@setOnClickListener
                    }
                }
            }

            // Si aucun opérateur n'a été supprimé, supprimez simplement un caractère à la fois
            if (inputText.isNotEmpty()) {
                input.text = inputText.dropLast(1)
            }
            output.text = ""
            lastCharEntered = ' '
        }


        egale.setOnClickListener {
            showResult()
        }

        clearButton.setOnClickListener {
            input.text = ""
            output.text = ""
        }

        number0.setOnClickListener {
            input.text = Add_data("0")
        }

        number1.setOnClickListener {
            input.text = Add_data("1")
        }

        number2.setOnClickListener {
            input.text = Add_data("2")
        }

        number3.setOnClickListener {
            input.text = Add_data("3")
        }

        number4.setOnClickListener {
            input.text = Add_data("4")
        }

        number5.setOnClickListener {
            input.text = Add_data("5")
        }

        number6.setOnClickListener {
            input.text = Add_data("6")
        }

        number7.setOnClickListener {
            input.text = Add_data("7")
        }

        number8.setOnClickListener {
            input.text = Add_data("8")
        }

        number9.setOnClickListener {
            input.text = Add_data("9")
        }

        virgule.setOnClickListener {
            input.text = Add_data(".")
        }

        addition.setOnClickListener {
            handleOperatorButton('+')
        }

        sustraction.setOnClickListener {
            handleOperatorButton('-')
        }

        fois.setOnClickListener {
            handleOperatorButton('*')
        }

        division.setOnClickListener {
            handleOperatorButton('/')
        }

        modulo.setOnClickListener {
            handleOperatorButton('%')
        }
        sin.setOnClickListener {
            input.text = Add_data("sin(")
        }
        cos.setOnClickListener {
            input.text = Add_data("cos(")
        }
        tan.setOnClickListener {
            input.text = Add_data("tan(")
        }
        tan1.setOnClickListener {
            input.text = Add_data("atan(")
        }
        x_n.setOnClickListener {
            input.text = Add_data("^")
        }
        x_2.setOnClickListener {
            input.text = Add_data("^2")
        }
        ln.setOnClickListener {
            input.text = Add_data("ln(")
        }
        sin1.setOnClickListener {
            input.text = Add_data("asin(")
        }

        cos1.setOnClickListener {
            input.text = Add_data("acos(")
        }
        inverse.setOnClickListener {
            input.text = Add_data("1/")
        }

        fact.setOnClickListener {
            input.text = Add_data("!")
        }
        racine.setOnClickListener {
                input.text = Add_data("sqrt(")
            }
        e.setOnClickListener {
            input.text = Add_data("e")
        }
        pi.setOnClickListener {
            input.text = Add_data("pi")
        }
        abs.setOnClickListener {
            input.text = Add_data("abs(")
        }
        exp.setOnClickListener{
            input.text= Add_data("exp(")
        }



        parenthese.setOnClickListener {
            val inputText = input.text.toString()
            val openParenthesesCount = inputText.count { it == '(' }
            val closeParenthesesCount = inputText.count { it == ')' }

            if (openParenthesesCount == closeParenthesesCount) {
                // Ajouter une parenthèse ouverte
                input.text = Add_data("(")
            } else if (openParenthesesCount > closeParenthesesCount) {
                // Ajouter une parenthèse fermée
                input.text = Add_data(")")
            }
        }

        Mplus.setOnClickListener {
            try {
                val valueToAdd = input.text.toString().toDouble()
                storedValue += valueToAdd
            }
            catch (e: NumberFormatException) {
                // Handle the NumberFormatException, if needed
            }
        }
        Mmoins.setOnClickListener {
            try {
                val valueToSubtract = input.text.toString().toDouble()
                storedValue -= valueToSubtract
            } catch (e: NumberFormatException) {
            }
        }
        MR.setOnClickListener {
            output.text = DecimalFormat("0.######").format(storedValue).toString()
            output.setTextColor(ContextCompat.getColor(this, R.color.btnBackground2))
        }

        MC.setOnClickListener {
            storedValue = 0.0
            output.text = ""
        }
    }
    // Fonction pour ajouter des données à l'entrée
    private fun Add_data(param: String): String {
        variable = input.text.toString()
        return "$variable$param"
    }
    // Fonction pour obtenir l'expression depuis l'entrée
    private fun getInputExpression(): String {
        var expression = input.text.toString().replace(Regex("x"), "*")
        return expression
    }
    private fun handleOperatorButton(operator: Char) {
        val inputText = input.text.toString()
        val operators = listOf('+', '-', '*', '/', '%')  // Liste des opérateurs valides

        if (inputText.isNotEmpty()) {
            // Obtenir le dernier caractère
            val lastChar = inputText.last()

            if (operators.none { it == lastChar }) {
                // Ajouter l'opérateur
                input.text = Add_data(operator.toString())
                lastCharEntered = operator
            }
        }
    }
    // Fonction pour afficher le résultat de l'expression
    private fun showResult() {
        try {
            val expression = getInputExpression()
            val result = Expression(expression).calculate()
            if (result.isNaN()) {
                output.text = "Error"
                output.setTextColor(ContextCompat.getColor(this, R.color.btnBackground2))
                input.text=""
            } else {
                output.text = DecimalFormat("0.######").format(result).toString()
                output.setTextColor(ContextCompat.getColor(this, R.color.btnBackground2))
                input.text=""
            }
        } catch (e: Exception)
        {
            output.text = "Error"

            output.setTextColor(ContextCompat.getColor(this, R.color.btnBackground2))
            input.text=""
        }
    }
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.standard -> {
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }
                R.id.scientifique -> {
                    val intent = Intent(this,MainActivity2::class.java)
                    startActivity(intent)
                }
                R.id.Map -> {
                    // Remplacez le contenu du fragment avec Map_Fragment
                    val fragment = Map_Fragment()
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()
                    val intent = Intent(this,Map_Page::class.java)
                    startActivity(intent)
                }
            }
            val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
            drawer.closeDrawer(GravityCompat.START)
            return false
        }

}
