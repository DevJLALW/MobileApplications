package org.srh.fda.view

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.srh.fda.R // Ensure this import matches your package
import java.util.*

class IntroActivity : BaseActivity() {

    override fun attachBaseContext(newBase: Context?) {
        // Use saved language or fallback to system language
        val savedLanguage = getSavedLanguage(newBase ?: return)
        super.attachBaseContext(setLocale(newBase, savedLanguage))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            IntroScreen(navController)
        }
    }
}

@Composable
fun IntroScreen(navController: NavController) {

    var expanded by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("English") }
    val context = LocalContext.current

    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            Image(
                painter = painterResource(R.drawable.backgroundimg),
                contentDescription = "Background image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Language Dropdown
                Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                    TextButton(onClick = { expanded = true }) {
                        Text(text = stringResource(id = R.string.language) + ": $selectedLanguage")
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(onClick = {
                            setLocale(context, "en")
                            saveLanguagePreference(context, "en")
                            selectedLanguage = "English"
                            expanded = false
                            (context as? AppCompatActivity)?.recreate()
                        }) {
                            Text("English")
                        }

                        DropdownMenuItem(onClick = {
                            setLocale(context, "de")
                            saveLanguagePreference(context, "de")
                            selectedLanguage = "German"
                            expanded = false
                            (context as? AppCompatActivity)?.recreate()
                        }) {
                            Text("Deutsch")
                        }
                    }
                }


                Spacer(modifier = Modifier.height(16.dp))

                Image(
                    painter = painterResource(id = R.drawable.intro_logo),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(text= stringResource(id=R.string.intro_title),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,)
                Spacer(modifier = Modifier.height(32.dp))

                Text(text= stringResource(id=R.string.intro_sub_title),
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp)

                Button(
                    modifier = Modifier.padding(16.dp),
                    onClick = { navController.navigate("login") }
                ) {
                    Text("Login")
                }

                Button(
                    modifier = Modifier.padding(16.dp),
                    onClick = { navController.navigate("register") }
                ) {
                    Text("Register")
                }


            }
        }

}}

fun setLocale(context: Context, language: String): Context {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val resources = context.resources
    val config = Configuration(resources.configuration)
    config.setLocale(locale)

    // For Android 7.0+ (Nougat) and above
    context.createConfigurationContext(config)

    // Update resources directly for backward compatibility
    resources.updateConfiguration(config, resources.displayMetrics)
    return context
}


fun saveLanguagePreference(context: Context, language: String) {
    val sharedPref = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    sharedPref.edit().putString("selected_language", language).apply()
}

/**
 * Retrieve the saved language from SharedPreferences.
 * Defaults to system language if no preference is saved.
 */
fun getSavedLanguage(context: Context): String {
    val sharedPref = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    return sharedPref.getString("selected_language", Locale.getDefault().language) ?: "en"
}


@Preview
@Composable
fun introScreenPreview() {
    val navController = rememberNavController()
    MainScreen(navController = navController)
}