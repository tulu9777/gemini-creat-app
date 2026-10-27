package com.example

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Collections
import androidx.compose.material.icons.outlined.Whatshot
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.theme.AccentEmerald
import com.example.ui.theme.AccentGold
import com.example.ui.theme.AppThemePreset
import com.example.ui.theme.LocalAppThemePreset
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.TextMutedDark
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryDark
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.UUID
import java.util.concurrent.TimeUnit

// ==========================================
// DATA MODELS
// ==========================================

data class TrendingPrompt(
    val id: String,
    val title: String,
    val category: String,
    val tag: String,
    val demoDrawableRes: Int,
    val prompt: String,
    val description: String,
    val styleEnhancements: List<String> = emptyList()
)

data class GeneratedCreation(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val prompt: String,
    val resultBitmap: Bitmap,
    val originalBitmap: Bitmap? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val isSimulation: Boolean = false
)

sealed interface GenerationUiState {
    data object Idle : GenerationUiState
    data class Loading(val stageText: String, val progress: Float) : GenerationUiState
    data class Success(val creation: GeneratedCreation) : GenerationUiState
    data class Error(val message: String, val canRetry: Boolean = true) : GenerationUiState
}

// ==========================================
// VIEWMODEL
// ==========================================

class PhotoEditorViewModel : ViewModel() {

    private val _currentThemePreset = MutableStateFlow(AppThemePreset.DARK_CONSTANT)
    val currentThemePreset: StateFlow<AppThemePreset> = _currentThemePreset.asStateFlow()

    private val _trendingPrompts = MutableStateFlow<List<TrendingPrompt>>(emptyList())
    val trendingPrompts: StateFlow<List<TrendingPrompt>> = _trendingPrompts.asStateFlow()

    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    private val _currentPrompt = MutableStateFlow("")
    val currentPrompt: StateFlow<String> = _currentPrompt.asStateFlow()

    private val _selectedPhotoUri = MutableStateFlow<Uri?>(null)
    val selectedPhotoUri: StateFlow<Uri?> = _selectedPhotoUri.asStateFlow()

    private val _selectedPhotoBitmap = MutableStateFlow<Bitmap?>(null)
    val selectedPhotoBitmap: StateFlow<Bitmap?> = _selectedPhotoBitmap.asStateFlow()

    private val _selectedAspectRatio = MutableStateFlow("1:1")
    val selectedAspectRatio: StateFlow<String> = _selectedAspectRatio.asStateFlow()

    private val _generationState = MutableStateFlow<GenerationUiState>(GenerationUiState.Idle)
    val generationState: StateFlow<GenerationUiState> = _generationState.asStateFlow()

    private val _creations = MutableStateFlow<List<GeneratedCreation>>(emptyList())
    val creations: StateFlow<List<GeneratedCreation>> = _creations.asStateFlow()

    private val _userApiKey = MutableStateFlow("")
    val userApiKey: StateFlow<String> = _userApiKey.asStateFlow()

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    init {
        loadDefaultTrendingPrompts()
    }

    fun initThemeAndPrefs(context: Context) {
        val prefs = context.getSharedPreferences("gemini_editor_prefs", Context.MODE_PRIVATE)
        // Load Theme
        val savedThemeId = prefs.getString("selected_app_theme", AppThemePreset.DARK_CONSTANT.id)
            ?: AppThemePreset.DARK_CONSTANT.id
        _currentThemePreset.value = AppThemePreset.fromId(savedThemeId)

        // Load API Key
        val savedKey = prefs.getString("gemini_api_key", "") ?: ""
        if (savedKey.isNotBlank()) {
            _userApiKey.value = savedKey
        } else {
            val buildConfigKey = try {
                BuildConfig.GEMINI_API_KEY
            } catch (e: Exception) {
                ""
            }
            if (buildConfigKey.isNotBlank() && buildConfigKey != "MY_GEMINI_API_KEY") {
                _userApiKey.value = buildConfigKey
            }
        }
    }

    fun setTheme(context: Context, preset: AppThemePreset) {
        _currentThemePreset.value = preset
        val prefs = context.getSharedPreferences("gemini_editor_prefs", Context.MODE_PRIVATE)
        prefs.edit().putString("selected_app_theme", preset.id).apply()
    }

    fun saveApiKey(context: Context, key: String) {
        _userApiKey.value = key.trim()
        val prefs = context.getSharedPreferences("gemini_editor_prefs", Context.MODE_PRIVATE)
        prefs.edit().putString("gemini_api_key", key.trim()).apply()
    }

    private fun loadDefaultTrendingPrompts() {
        val list = listOf(
            TrendingPrompt(
                id = "trend_diwali",
                title = "Diwali Celebration 3D Art",
                category = "Festival",
                tag = "🔥 #1 Viral • 280k uses",
                demoDrawableRes = R.drawable.img_trend_diwali,
                prompt = "Transform into a vibrant festive Diwali portrait in 3D animated Pixar clay art style, glowing clay diyas, sparkles, golden festive fireworks in background, warm enchanting lighting, detailed 3D render",
                description = "Magical festival of lights with glowing diyas and 3D clay animated characters.",
                styleEnhancements = listOf("Volumetric Diyas", "Golden Fireworks", "Pixar Clay Lighting")
            ),
            TrendingPrompt(
                id = "trend_cyberpunk",
                title = "Cyberpunk Neon Odyssey",
                category = "Cyberpunk",
                tag = "⚡ 195k uses",
                demoDrawableRes = R.drawable.img_trend_cyberpunk,
                prompt = "Cyberpunk futuristic city portrait, neon glowing blue and magenta reflections, high-tech holographic visor, rainy neon-lit night city street, cinematic photorealistic lighting 8k",
                description = "High-tech futuristic transformation with neon rain reflections and holographic vibes.",
                styleEnhancements = listOf("Neon Glaze", "Hologram Visor", "Rainy Cyber Street")
            ),
            TrendingPrompt(
                id = "trend_pixar",
                title = "3D Pixar Character Magic",
                category = "3D Style",
                tag = "✨ Editor's Pick",
                demoDrawableRes = R.drawable.img_trend_pixar,
                prompt = "Cute 3D animated Pixar style character portrait with expressive eyes, friendly warm smile, soft studio rim lighting, 3D animated movie render, high detail",
                description = "Turn yourself or your subject into a beloved animated feature film hero.",
                styleEnhancements = listOf("Expressive Eyes", "Soft Rim Light", "Subsurface Scattering")
            ),
            TrendingPrompt(
                id = "trend_royal",
                title = "Royal Traditional Maharaja",
                category = "Royal",
                tag = "🌟 Trending Now",
                demoDrawableRes = R.drawable.img_trend_royal,
                prompt = "Regal Indian royal maharaja portrait in opulent traditional sherwani with golden zari embroidery, layered emerald necklaces, royal palace courtyard with carved arches, cinematic majestic lighting",
                description = "Opulent heritage aristocracy with hand-woven zari sherwani and majestic palace halls.",
                styleEnhancements = listOf("Golden Zari Embroidery", "Palace Courtyard", "Emerald Jewels")
            ),
            TrendingPrompt(
                id = "trend_cinematic",
                title = "Cinematic Film Noir Glow",
                category = "Cinematic",
                tag = "🎬 Cinematic",
                demoDrawableRes = R.drawable.img_trend_cyberpunk,
                prompt = "Moody cinematic 35mm film still portrait, dramatic chiaroscuro Rembrandt lighting, rich color grading, shallow depth of field, anamorphic lens flare, Kodak Portra aesthetic",
                description = "Dramatic movie-poster lighting with rich vintage color grading and deep shadows.",
                styleEnhancements = listOf("35mm Film Grain", "Anamorphic Flare", "Chiaroscuro")
            )
        )
        _trendingPrompts.value = list
    }

    fun selectPrompt(prompt: TrendingPrompt) {
        _currentPrompt.value = prompt.prompt
    }

    fun updatePrompt(text: String) {
        _currentPrompt.value = text
    }

    fun appendStyleEnhancement(style: String) {
        val current = _currentPrompt.value.trim()
        if (current.isBlank()) {
            _currentPrompt.value = style
        } else if (!current.contains(style, ignoreCase = true)) {
            _currentPrompt.value = "$current, $style"
        }
    }

    fun setAspectRatio(ratio: String) {
        _selectedAspectRatio.value = ratio
    }

    fun setSelectedPhoto(context: Context, uri: Uri?) {
        _selectedPhotoUri.value = uri
        if (uri != null) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    _selectedPhotoBitmap.value = bitmap
                } catch (e: Exception) {
                    Log.e("PhotoEditorVM", "Failed to decode photo bitmap", e)
                }
            }
        } else {
            _selectedPhotoBitmap.value = null
        }
    }

    fun setSamplePhoto(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.img_trend_pixar)
                _selectedPhotoBitmap.value = bitmap
                _selectedPhotoUri.value = Uri.parse("android.resource://${context.packageName}/${R.drawable.img_trend_pixar}")
            } catch (e: Exception) {
                Log.e("PhotoEditorVM", "Failed to set sample photo", e)
            }
        }
    }

    fun clearSelectedPhoto() {
        _selectedPhotoUri.value = null
        _selectedPhotoBitmap.value = null
    }

    fun generatePhoto(context: Context) {
        val promptText = _currentPrompt.value.trim()
        if (promptText.isBlank()) {
            _generationState.value = GenerationUiState.Error("Please enter or select a prompt first.")
            return
        }

        val apiKey = _userApiKey.value.trim()
        val hasKey = apiKey.isNotBlank() && apiKey != "MY_GEMINI_API_KEY"

        viewModelScope.launch {
            _generationState.value = GenerationUiState.Loading("Initializing Gemini transformation...", 0.15f)

            try {
                delay(500)
                _generationState.value = GenerationUiState.Loading("Gemini is editing your photo...", 0.45f)

                if (hasKey) {
                    // Real live Gemini / Imagen Cloud API request
                    val resultBitmap = executeGeminiApiCall(apiKey, promptText, _selectedPhotoBitmap.value, _selectedAspectRatio.value)

                    _generationState.value = GenerationUiState.Loading("Finalizing high-resolution details...", 0.90f)
                    delay(300)

                    val creation = GeneratedCreation(
                        title = deriveTitle(promptText),
                        prompt = promptText,
                        resultBitmap = resultBitmap,
                        originalBitmap = _selectedPhotoBitmap.value,
                        isSimulation = false
                    )
                    _creations.value = listOf(creation) + _creations.value
                    _generationState.value = GenerationUiState.Success(creation)
                } else {
                    // Preview Simulation Mode for instant testing without API key
                    delay(1000)
                    _generationState.value = GenerationUiState.Loading("Synthesizing stylized preview (No API key)...", 0.75f)
                    delay(600)

                    val previewBitmap = generateStylizedSimulation(context, _selectedPhotoBitmap.value, promptText)
                    val creation = GeneratedCreation(
                        title = deriveTitle(promptText),
                        prompt = promptText,
                        resultBitmap = previewBitmap,
                        originalBitmap = _selectedPhotoBitmap.value,
                        isSimulation = true
                    )
                    _creations.value = listOf(creation) + _creations.value
                    _generationState.value = GenerationUiState.Success(creation)
                }
            } catch (e: Exception) {
                Log.e("PhotoEditorVM", "Generation error", e)
                _generationState.value = GenerationUiState.Error(
                    e.localizedMessage ?: "Gemini photo generation encountered an issue. Please verify your API key or network."
                )
            }
        }
    }

    private suspend fun executeGeminiApiCall(
        apiKey: String,
        prompt: String,
        inputPhoto: Bitmap?,
        aspectRatio: String
    ): Bitmap = withContext(Dispatchers.IO) {
        val url = "https://generativelanguage.googleapis.com/v1beta/models/imagen-3.0-generate-002:predict?key=$apiKey"

        val effectivePrompt = if (inputPhoto != null) {
            "Professional photo transformation: Transform the uploaded portrait subject into: $prompt. Keep facial resemblance with high quality photorealistic textures, 8k resolution, cinematic lighting, masterwork composition."
        } else {
            "$prompt. High resolution, 8k, photorealistic textures, masterwork cinematic studio lighting."
        }

        val requestJson = JSONObject().apply {
            val instances = JSONArray().apply {
                put(JSONObject().apply {
                    put("prompt", effectivePrompt)
                })
            }
            put("instances", instances)

            val parameters = JSONObject().apply {
                put("sampleCount", 1)
                put("aspectRatio", when (aspectRatio) {
                    "16:9" -> "16:9"
                    "4:3" -> "4:3"
                    "3:4" -> "3:4"
                    else -> "1:1"
                })
                put("outputMimeType", "image/jpeg")
            }
            put("parameters", parameters)
        }

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = requestJson.toString().toRequestBody(mediaType)
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        val response = okHttpClient.newCall(request).execute()
        val responseBody = response.body?.string().orEmpty()

        if (!response.isSuccessful) {
            val errorMessage = try {
                val errObj = JSONObject(responseBody)
                val err = errObj.optJSONObject("error")
                err?.optString("message") ?: "HTTP ${response.code}: ${response.message}"
            } catch (e: Exception) {
                "HTTP ${response.code}: ${response.message}"
            }
            throw RuntimeException(errorMessage)
        }

        val rootJson = JSONObject(responseBody)
        val predictions = rootJson.optJSONArray("predictions")
        if (predictions == null || predictions.length() == 0) {
            throw RuntimeException("No image predictions returned by Gemini Imagen model.")
        }

        val firstPrediction = predictions.getJSONObject(0)
        val bytesBase64 = firstPrediction.optString("bytesBase64Encoded")
        if (bytesBase64.isBlank()) {
            throw RuntimeException("Empty image data received from Gemini API.")
        }

        val imageBytes = Base64.decode(bytesBase64, Base64.DEFAULT)
        val decoded = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            ?: throw RuntimeException("Failed to decode generated image bytes into Bitmap.")
        decoded
    }

    private suspend fun generateStylizedSimulation(
        context: Context,
        inputPhoto: Bitmap?,
        prompt: String
    ): Bitmap = withContext(Dispatchers.Default) {
        val base = inputPhoto ?: BitmapFactory.decodeResource(context.resources, R.drawable.img_trend_pixar)
        val width = base.width.coerceIn(512, 1024)
        val height = base.height.coerceIn(512, 1024)
        val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        val matrix = ColorMatrix()

        val lowerPrompt = prompt.lowercase()
        when {
            lowerPrompt.contains("cyberpunk") || lowerPrompt.contains("neon") -> {
                matrix.set(floatArrayOf(
                    1.4f, 0.0f, 0.3f, 0f, 20f,
                    0.0f, 1.1f, 0.5f, 0f, 30f,
                    0.4f, 0.2f, 1.8f, 0f, 50f,
                    0.0f, 0.0f, 0.0f, 1f, 0f
                ))
            }
            lowerPrompt.contains("diwali") || lowerPrompt.contains("gold") || lowerPrompt.contains("firework") -> {
                matrix.set(floatArrayOf(
                    1.5f, 0.2f, 0.0f, 0f, 40f,
                    0.1f, 1.3f, 0.0f, 0f, 25f,
                    0.0f, 0.1f, 0.8f, 0f, -10f,
                    0.0f, 0.0f, 0.0f, 1f, 0f
                ))
            }
            lowerPrompt.contains("royal") || lowerPrompt.contains("maharaja") -> {
                matrix.set(floatArrayOf(
                    1.3f, 0.1f, 0.0f, 0f, 30f,
                    0.1f, 1.25f, 0.1f, 0f, 20f,
                    0.0f, 0.1f, 0.9f, 0f, 10f,
                    0.0f, 0.0f, 0.0f, 1f, 0f
                ))
            }
            else -> {
                matrix.set(floatArrayOf(
                    1.2f, 0.0f, 0.0f, 0f, 15f,
                    0.0f, 1.2f, 0.0f, 0f, 15f,
                    0.0f, 0.0f, 1.3f, 0f, 20f,
                    0.0f, 0.0f, 0.0f, 1f, 0f
                ))
            }
        }
        paint.colorFilter = ColorMatrixColorFilter(matrix)
        canvas.drawBitmap(base, null, android.graphics.Rect(0, 0, width, height), paint)
        output
    }

    private fun deriveTitle(prompt: String): String {
        val words = prompt.split(" ").filter { it.isNotBlank() }
        return if (words.size > 4) {
            words.take(4).joinToString(" ") + "..."
        } else {
            prompt
        }
    }

    fun dismissError() {
        _generationState.value = GenerationUiState.Idle
    }
}

// ==========================================
// MAIN ACTIVITY
// ==========================================

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel: PhotoEditorViewModel = viewModel()
            val context = LocalContext.current
            val currentPreset by viewModel.currentThemePreset.collectAsState()

            LaunchedEffect(Unit) {
                viewModel.initThemeAndPrefs(context)
            }

            MyApplicationTheme(preset = currentPreset) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PhotoEditorApp(viewModel = viewModel)
                }
            }
        }
    }
}

// ==========================================
// TOP-LEVEL COMPOSABLE
// ==========================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoEditorApp(viewModel: PhotoEditorViewModel) {
    val context = LocalContext.current
    var currentTab by remember { mutableIntStateOf(0) }
    var showSettingsSheet by remember { mutableStateOf(false) }
    var showThemeSheet by remember { mutableStateOf(false) }

    val userApiKey by viewModel.userApiKey.collectAsState()
    val hasValidKey = userApiKey.isNotBlank() && userApiKey != "MY_GEMINI_API_KEY"
    val currentPreset by viewModel.currentThemePreset.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(
                                        Brush.linearGradient(
                                            listOf(
                                                MaterialTheme.colorScheme.primary,
                                                MaterialTheme.colorScheme.secondary,
                                                MaterialTheme.colorScheme.tertiary
                                            )
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "Gemini Photo Editor & Trends",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = if (hasValidKey) "Gemini AI Engine Active • ${currentPreset.displayName}" else "Theme: ${currentPreset.displayName}",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.secondary,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    },
                    actions = {
                        // Quick Theme Palette Switcher Button
                        IconButton(
                            onClick = { showThemeSheet = true },
                            modifier = Modifier.testTag("theme_switcher_button")
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.18f))
                                    .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Palette,
                                    contentDescription = "Change Theme",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(17.dp)
                                )
                            }
                        }

                        // Key Status Pill
                        Surface(
                            shape = CircleShape,
                            color = if (hasValidKey) MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f) else AccentGold.copy(alpha = 0.15f),
                            border = BorderStroke(
                                1.dp,
                                if (hasValidKey) MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f) else AccentGold.copy(alpha = 0.4f)
                            ),
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .clickable { showSettingsSheet = true }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = if (hasValidKey) Icons.Default.Check else Icons.Default.Key,
                                    contentDescription = null,
                                    tint = if (hasValidKey) MaterialTheme.colorScheme.secondary else AccentGold,
                                    modifier = Modifier.size(13.dp)
                                )
                                Text(
                                    text = if (hasValidKey) "AI Ready" else "Add Key",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (hasValidKey) MaterialTheme.colorScheme.secondary else AccentGold
                                )
                            }
                        }

                        IconButton(
                            onClick = { showSettingsSheet = true },
                            modifier = Modifier.testTag("settings_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        scrolledContainerColor = MaterialTheme.colorScheme.surface
                    )
                )

                // Subtitle Header Banner as required
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FlashOn,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Upload your photo, select a trending prompt, and let Gemini transform it instantly.",
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                tonalElevation = 6.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
            ) {
                NavigationBarItem(
                    selected = currentTab == 0,
                    onClick = { currentTab = 0 },
                    icon = {
                        Icon(
                            imageVector = if (currentTab == 0) Icons.Outlined.Whatshot else Icons.Outlined.Whatshot,
                            contentDescription = "Trending"
                        )
                    },
                    label = { Text("Trending") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                        unselectedIconColor = TextMutedDark,
                        unselectedTextColor = TextMutedDark
                    ),
                    modifier = Modifier.testTag("tab_trending")
                )

                NavigationBarItem(
                    selected = currentTab == 1,
                    onClick = { currentTab = 1 },
                    icon = {
                        Icon(
                            imageVector = if (currentTab == 1) Icons.Default.AutoAwesome else Icons.Outlined.AutoAwesome,
                            contentDescription = "Photo Studio"
                        )
                    },
                    label = { Text("Studio") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.secondary,
                        selectedTextColor = MaterialTheme.colorScheme.secondary,
                        indicatorColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
                        unselectedIconColor = TextMutedDark,
                        unselectedTextColor = TextMutedDark
                    ),
                    modifier = Modifier.testTag("tab_studio")
                )

                NavigationBarItem(
                    selected = currentTab == 2,
                    onClick = { currentTab = 2 },
                    icon = {
                        Icon(
                            imageVector = if (currentTab == 2) Icons.Default.Collections else Icons.Outlined.Collections,
                            contentDescription = "Creations"
                        )
                    },
                    label = { Text("Creations") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.tertiary,
                        selectedTextColor = MaterialTheme.colorScheme.tertiary,
                        indicatorColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f),
                        unselectedIconColor = TextMutedDark,
                        unselectedTextColor = TextMutedDark
                    ),
                    modifier = Modifier.testTag("tab_creations")
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (currentTab) {
                0 -> TrendingFeedScreen(
                    viewModel = viewModel,
                    onUsePromptClicked = { prompt ->
                        viewModel.selectPrompt(prompt)
                        currentTab = 1
                    }
                )
                1 -> PhotoStudioScreen(
                    viewModel = viewModel,
                    onNavigateToCreations = {
                        currentTab = 2
                    },
                    onOpenSettings = {
                        showSettingsSheet = true
                    }
                )
                2 -> CreationsHistoryScreen(
                    viewModel = viewModel,
                    onNewEdit = {
                        currentTab = 1
                    }
                )
            }

            if (showThemeSheet) {
                ThemeSelectionBottomSheet(
                    viewModel = viewModel,
                    onDismiss = { showThemeSheet = false }
                )
            }

            if (showSettingsSheet) {
                SettingsBottomSheet(
                    viewModel = viewModel,
                    onOpenThemePicker = {
                        showSettingsSheet = false
                        showThemeSheet = true
                    },
                    onDismiss = { showSettingsSheet = false }
                )
            }
        }
    }
}

// ==========================================
// THEME SELECTION BOTTOM SHEET
// ==========================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSelectionBottomSheet(
    viewModel: PhotoEditorViewModel,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val currentPreset by viewModel.currentThemePreset.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        scrimColor = Color.Black.copy(alpha = 0.65f),
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Palette,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "Choose App Theme",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Instant real-time color styling",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                IconButton(onClick = onDismiss) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)

            Text(
                text = "Select your preferred visual aesthetic:",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(AppThemePreset.entries) { preset ->
                    val isSelected = currentPreset == preset

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.setTheme(context, preset)
                                Toast.makeText(context, "${preset.displayName} Applied", Toast.LENGTH_SHORT).show()
                            }
                            .testTag("theme_option_${preset.id}"),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.surfaceVariant else preset.surfaceColor
                        ),
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = if (isSelected) preset.primaryColor else preset.surfaceVariantColor
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                // Three preview color swatches: BG, Surface, Primary Accent
                                Row(horizontalArrangement = Arrangement.spacedBy((-6).dp)) {
                                    Box(
                                        modifier = Modifier
                                            .size(26.dp)
                                            .clip(CircleShape)
                                            .background(preset.backgroundColor)
                                            .border(1.5.dp, Color.White.copy(alpha = 0.3f), CircleShape)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .size(26.dp)
                                            .clip(CircleShape)
                                            .background(preset.surfaceColor)
                                            .border(1.5.dp, Color.White.copy(alpha = 0.3f), CircleShape)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .size(26.dp)
                                            .clip(CircleShape)
                                            .background(preset.primaryColor)
                                            .border(1.5.dp, Color.White.copy(alpha = 0.5f), CircleShape)
                                    )
                                }

                                Column {
                                    Text(
                                        text = preset.displayName,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = preset.subtitle,
                                        fontSize = 11.sp,
                                        color = TextSecondaryDark
                                    )
                                }
                            }

                            RadioButton(
                                selected = isSelected,
                                onClick = {
                                    viewModel.setTheme(context, preset)
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = preset.primaryColor,
                                    unselectedColor = TextMutedDark
                                )
                            )
                        }
                    }
                }
            }

            Button(
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Done", fontWeight = FontWeight.Bold)
            }
        }
    }
}

// ==========================================
// SCREEN 1: LIVE ONLINE TRENDING PROMPTS FEED
// ==========================================

@Composable
fun TrendingFeedScreen(
    viewModel: PhotoEditorViewModel,
    onUsePromptClicked: (TrendingPrompt) -> Unit
) {
    val trendingPrompts by viewModel.trendingPrompts.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "🔥 Trending", "Festival", "Cyberpunk", "3D Style", "Royal", "Cinematic")
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    val filteredPrompts = remember(trendingPrompts, searchQuery, selectedCategory) {
        trendingPrompts.filter { item ->
            val matchesSearch = searchQuery.isBlank() ||
                item.title.contains(searchQuery, ignoreCase = true) ||
                item.prompt.contains(searchQuery, ignoreCase = true) ||
                item.category.contains(searchQuery, ignoreCase = true)

            val matchesCategory = when (selectedCategory) {
                "All" -> true
                "🔥 Trending" -> item.tag.contains("Viral") || item.tag.contains("Trending")
                else -> item.category.equals(selectedCategory, ignoreCase = true)
            }

            matchesSearch && matchesCategory
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Search & Filter Section
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search viral prompts (e.g. Diwali, Cyberpunk)...", color = TextMutedDark, fontSize = 13.sp) },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("search_prompts_input")
                )

                // Category Chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categories.forEach { cat ->
                        val isSelected = selectedCategory == cat
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedCategory = cat },
                            label = { Text(cat, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                                selectedLabelColor = MaterialTheme.colorScheme.primary
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = MaterialTheme.colorScheme.surfaceVariant,
                                selectedBorderColor = MaterialTheme.colorScheme.primary
                            ),
                            shape = CircleShape
                        )
                    }
                }
            }
        }

        // Section Title
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Viral Online Trends (${filteredPrompts.size})",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Updated Live",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AccentEmerald
                )
            }
        }

        // List of Cards
        if (filteredPrompts.isEmpty()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(28.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = TextMutedDark,
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            text = "No trending prompts match your search",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        OutlinedButton(
                            onClick = {
                                searchQuery = ""
                                selectedCategory = "All"
                            },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text("Reset Filters")
                        }
                    }
                }
            }
        } else {
            items(filteredPrompts, key = { it.id }) { promptItem ->
                TrendingPromptCard(
                    prompt = promptItem,
                    onUsePrompt = { onUsePromptClicked(promptItem) },
                    onCopyPrompt = {
                        clipboardManager.setText(AnnotatedString(promptItem.prompt))
                        Toast.makeText(context, "Prompt copied to clipboard!", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}

@Composable
fun TrendingPromptCard(
    prompt: TrendingPrompt,
    onUsePrompt: () -> Unit,
    onCopyPrompt: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("trend_card_${prompt.id}"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Demo Image Preview Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 10f)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Image(
                    painter = painterResource(id = prompt.demoDrawableRes),
                    contentDescription = "Demo preview for ${prompt.title}",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Top Tag Badges
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color.Black.copy(alpha = 0.7f),
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
                    ) {
                        Text(
                            text = prompt.tag,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = AccentGold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.85f)
                    ) {
                        Text(
                            text = prompt.category,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
            }

            // Details and Actions
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = prompt.title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = prompt.description,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 16.sp
                )

                // Prompt Container
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f))
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "GEMINI / IMAGEN PROMPT",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                letterSpacing = 1.sp
                            )
                            IconButton(
                                onClick = onCopyPrompt,
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = "Copy Prompt",
                                    tint = TextMutedDark,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = prompt.prompt,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f),
                            lineHeight = 15.sp,
                            fontFamily = FontFamily.Monospace,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                // Action Button
                Button(
                    onClick = onUsePrompt,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("use_prompt_${prompt.id}"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Use This Prompt",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// ==========================================
// SCREEN 2: PHOTO STUDIO & WORKFLOW
// ==========================================

@Composable
fun PhotoStudioScreen(
    viewModel: PhotoEditorViewModel,
    onNavigateToCreations: () -> Unit,
    onOpenSettings: () -> Unit
) {
    val context = LocalContext.current
    val currentPrompt by viewModel.currentPrompt.collectAsState()
    val selectedBitmap by viewModel.selectedPhotoBitmap.collectAsState()
    val selectedRatio by viewModel.selectedAspectRatio.collectAsState()
    val generationState by viewModel.generationState.collectAsState()
    val userApiKey by viewModel.userApiKey.collectAsState()
    val hasValidKey = userApiKey.isNotBlank() && userApiKey != "MY_GEMINI_API_KEY"

    val keyboardController = LocalSoftwareKeyboardController.current

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.setSelectedPhoto(context, uri)
        }
    }

    val aspectRatios = listOf("1:1", "4:3", "16:9")
    val quickPillStyles = listOf("Cinematic 8k", "Pixar 3D", "Cyberpunk Glow", "Royal Palace", "Volumetric Light")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Step 1: Photo Selection Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("1", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                            Text(
                                text = "Select or Upload Photo",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        if (selectedBitmap != null) {
                            TextButton(
                                onClick = { viewModel.clearSelectedPhoto() },
                                colors = ButtonDefaults.textButtonColors(contentColor = TextMutedDark)
                            ) {
                                Text("Remove", fontSize = 12.sp)
                            }
                        }
                    }

                    if (selectedBitmap != null) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(14.dp))
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Image(
                                bitmap = selectedBitmap!!.asImageBitmap(),
                                contentDescription = "Selected Photo",
                                modifier = Modifier
                                    .size(76.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(10.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "Photo Ready to Edit",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = "${selectedBitmap!!.width} x ${selectedBitmap!!.height} px",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                OutlinedButton(
                                    onClick = {
                                        photoPickerLauncher.launch(
                                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        )
                                    },
                                    modifier = Modifier.height(32.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.secondary)
                                ) {
                                    Text("Change Photo", fontSize = 11.sp)
                                }
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(14.dp))
                                .border(BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant), RoundedCornerShape(14.dp))
                                .padding(18.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PhotoCamera,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(36.dp)
                                )
                                Text(
                                    text = "Choose a photo from your gallery",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = "Gemini will preserve your subject and apply viral AI styles.",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center
                                )

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.padding(top = 4.dp)
                                ) {
                                    Button(
                                        onClick = {
                                            photoPickerLauncher.launch(
                                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                            )
                                        },
                                        modifier = Modifier.testTag("upload_gallery_button"),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.primary,
                                            contentColor = Color.White
                                        ),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Icon(Icons.Default.Image, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Gallery Photo", fontSize = 12.sp)
                                    }

                                    OutlinedButton(
                                        onClick = { viewModel.setSamplePhoto(context) },
                                        modifier = Modifier.testTag("sample_photo_button"),
                                        shape = RoundedCornerShape(10.dp),
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.secondary)
                                    ) {
                                        Text("Use Sample", fontSize = 12.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Step 2: Prompt Input & Customization
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.secondary),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("2", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                            Text(
                                text = "Prompt & Style Instructions",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        if (currentPrompt.isNotBlank()) {
                            TextButton(
                                onClick = { viewModel.updatePrompt("") },
                                colors = ButtonDefaults.textButtonColors(contentColor = TextMutedDark)
                            ) {
                                Text("Clear", fontSize = 12.sp)
                            }
                        }
                    }

                    OutlinedTextField(
                        value = currentPrompt,
                        onValueChange = { viewModel.updatePrompt(it) },
                        placeholder = {
                            Text(
                                "Describe your desired edit or select a trending prompt from the feed...",
                                color = TextMutedDark,
                                fontSize = 13.sp
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .testTag("prompt_input"),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.background,
                            unfocusedContainerColor = MaterialTheme.colorScheme.background,
                            focusedBorderColor = MaterialTheme.colorScheme.secondary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                            focusedTextColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedTextColor = MaterialTheme.colorScheme.onBackground
                        )
                    )

                    // Quick Style Enhancer Pills
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "Add Quick Enhancers:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            quickPillStyles.forEach { style ->
                                Surface(
                                    shape = CircleShape,
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant),
                                    modifier = Modifier.clickable {
                                        viewModel.appendStyleEnhancement(style)
                                    }
                                ) {
                                    Text(
                                        text = "+ $style",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.secondary,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }

                    // Aspect Ratio Selector
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Aspect Ratio:",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Medium
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            aspectRatios.forEach { ratio ->
                                val isSelected = selectedRatio == ratio
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isSelected) MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.background,
                                    border = BorderStroke(
                                        1.dp,
                                        if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surfaceVariant
                                    ),
                                    modifier = Modifier.clickable { viewModel.setAspectRatio(ratio) }
                                ) {
                                    Text(
                                        text = ratio,
                                        fontSize = 11.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Generate Action Button & Processing State
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                when (val state = generationState) {
                    is GenerationUiState.Loading -> {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(44.dp),
                                    color = MaterialTheme.colorScheme.primary,
                                    strokeWidth = 4.dp
                                )
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = state.stageText,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = "Synthesizing image with Gemini AI models...",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                LinearProgressIndicator(
                                    progress = { state.progress },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(6.dp)
                                        .clip(CircleShape),
                                    color = MaterialTheme.colorScheme.primary,
                                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                                )
                            }
                        }
                    }

                    is GenerationUiState.Error -> {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.error)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        Icons.Default.ErrorOutline,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                    Text(
                                        text = "Generation Notice",
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.error,
                                        fontSize = 14.sp
                                    )
                                }
                                Text(
                                    text = state.message,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.padding(top = 4.dp)
                                ) {
                                    Button(
                                        onClick = { viewModel.generatePhoto(context) },
                                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text("Retry", fontSize = 12.sp)
                                    }
                                    OutlinedButton(
                                        onClick = onOpenSettings,
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.secondary),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text("Check API Key", fontSize = 12.sp)
                                    }
                                    TextButton(
                                        onClick = { viewModel.dismissError() },
                                        colors = ButtonDefaults.textButtonColors(contentColor = TextMutedDark)
                                    ) {
                                        Text("Dismiss", fontSize = 12.sp)
                                    }
                                }
                            }
                        }
                    }

                    else -> {
                        Button(
                            onClick = {
                                keyboardController?.hide()
                                viewModel.generatePhoto(context)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp)
                                .testTag("generate_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Generate Edited Photo with Gemini",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        if (!hasValidKey) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = MaterialTheme.colorScheme.surface,
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onOpenSettings() }
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        Icons.Default.Key,
                                        contentDescription = null,
                                        tint = AccentGold,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "Tip: Enter your free Gemini API key in Settings for direct cloud inference. Tap here to set up.",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        lineHeight = 15.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Result Card if Success
        if (generationState is GenerationUiState.Success) {
            val creation = (generationState as GenerationUiState.Success).creation
            item {
                ResultDisplayCard(
                    creation = creation,
                    onViewInCreations = onNavigateToCreations
                )
            }
        }
    }
}

// ==========================================
// RESULTS & SHARING COMPONENT
// ==========================================

@Composable
fun ResultDisplayCard(
    creation: GeneratedCreation,
    onViewInCreations: () -> Unit
) {
    val context = LocalContext.current
    var showBeforeAfter by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("result_card"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(AccentEmerald),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    }
                    Text(
                        text = "Gemini Creation Ready!",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                if (creation.isSimulation) {
                    Surface(
                        shape = CircleShape,
                        color = AccentGold.copy(alpha = 0.2f),
                        border = BorderStroke(1.dp, AccentGold.copy(alpha = 0.5f))
                    ) {
                        Text(
                            text = "Preview Mode",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = AccentGold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
            }

            // Image Preview Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                val displayBitmap = if (showBeforeAfter && creation.originalBitmap != null) {
                    creation.originalBitmap
                } else {
                    creation.resultBitmap
                }

                Image(
                    bitmap = displayBitmap.asImageBitmap(),
                    contentDescription = "Generated Edited Photo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Before/After indicator badge
                if (creation.originalBitmap != null) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(10.dp)
                            .clickable { showBeforeAfter = !showBeforeAfter },
                        shape = CircleShape,
                        color = Color.Black.copy(alpha = 0.7f),
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Visibility,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = if (showBeforeAfter) "Showing Original (Tap for Edit)" else "Showing Gemini Edit (Tap for Orig)",
                                fontSize = 10.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            Text(
                text = "\"${creation.prompt}\"",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 15.sp,
                fontFamily = FontFamily.Monospace,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            // Results action buttons: Download to Gallery & Share
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        val saved = saveBitmapToGallery(context, creation.resultBitmap, "Gemini_Edit")
                        if (saved) {
                            Toast.makeText(context, "Saved to Gallery!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Failed to save photo.", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .testTag("download_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Download", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = {
                        shareBitmap(context, creation.resultBitmap, creation.prompt)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .testTag("share_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Share", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// ==========================================
// SCREEN 3: CREATIONS GALLERY
// ==========================================

@Composable
fun CreationsHistoryScreen(
    viewModel: PhotoEditorViewModel,
    onNewEdit: () -> Unit
) {
    val context = LocalContext.current
    val creations by viewModel.creations.collectAsState()

    if (creations.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Collections,
                        contentDescription = null,
                        tint = TextMutedDark,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Text(
                    text = "No Creations Yet",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Your edited photos will appear here. Pick a trending prompt from the feed to begin!",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = onNewEdit,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Start Creating")
                }
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "My Gemini Creations (${creations.size})",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    OutlinedButton(
                        onClick = onNewEdit,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("New Edit", fontSize = 12.sp)
                    }
                }
            }

            items(creations, key = { it.id }) { creation ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("creation_card_${creation.id}"),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Image(
                            bitmap = creation.resultBitmap.asImageBitmap(),
                            contentDescription = creation.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f / 11f)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Text(
                            text = creation.title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            text = creation.prompt,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = {
                                    val saved = saveBitmapToGallery(context, creation.resultBitmap, creation.title)
                                    if (saved) {
                                        Toast.makeText(context, "Saved to Gallery!", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.secondary)
                            ) {
                                Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Save", fontSize = 12.sp)
                            }

                            Button(
                                onClick = {
                                    shareBitmap(context, creation.resultBitmap, creation.prompt)
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                            ) {
                                Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Share", fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// SETTINGS BOTTOM SHEET: GEMINI API KEY & APPEARANCE
// ==========================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsBottomSheet(
    viewModel: PhotoEditorViewModel,
    onOpenThemePicker: () -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val currentApiKey by viewModel.userApiKey.collectAsState()
    val currentPreset by viewModel.currentThemePreset.collectAsState()
    var inputKey by remember { mutableStateOf(currentApiKey) }
    var isKeyVisible by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        scrimColor = Color.Black.copy(alpha = 0.65f),
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .navigationBarsPadding()
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Settings, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                    }
                    Column {
                        Text(
                            text = "App Settings",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "AI engine & appearance options",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Clear, contentDescription = "Close", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)

            // Theme Setting Row
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOpenThemePicker() },
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.background,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(currentPreset.primaryColor.copy(alpha = 0.25f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Palette,
                                contentDescription = null,
                                tint = currentPreset.primaryColor,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "App Theme",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = currentPreset.displayName,
                                fontSize = 11.sp,
                                color = currentPreset.primaryColor,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    OutlinedButton(
                        onClick = onOpenThemePicker,
                        modifier = Modifier.height(32.dp),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 0.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Change", fontSize = 11.sp)
                    }
                }
            }

            // Gemini API Key Section
            Text(
                text = "Enter your Google Gemini API key below. You can get one for free at aistudio.google.com with no credit card required.",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 16.sp
            )

            OutlinedTextField(
                value = inputKey,
                onValueChange = { inputKey = it },
                label = { Text("Gemini API Key") },
                placeholder = { Text("AIzaSy...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("api_key_input"),
                shape = RoundedCornerShape(12.dp),
                visualTransformation = if (isKeyVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { isKeyVisible = !isKeyVisible }) {
                        Icon(
                            imageVector = if (isKeyVisible) Icons.Default.Visibility else Icons.Default.Key,
                            contentDescription = if (isKeyVisible) "Hide Key" else "Show Key",
                            tint = TextMutedDark
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                singleLine = true
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        inputKey = ""
                        viewModel.saveApiKey(context, "")
                        Toast.makeText(context, "API Key removed", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextMutedDark)
                ) {
                    Text("Clear Key")
                }

                Button(
                    onClick = {
                        viewModel.saveApiKey(context, inputKey)
                        Toast.makeText(context, "API Key saved successfully!", Toast.LENGTH_SHORT).show()
                        onDismiss()
                    },
                    modifier = Modifier
                        .weight(1.5f)
                        .testTag("save_api_key_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Save & Apply")
                }
            }
        }
    }
}

// ==========================================
// UTILITY HELPERS
// ==========================================

fun saveBitmapToGallery(context: Context, bitmap: Bitmap, title: String): Boolean {
    return try {
        val resolver = context.contentResolver
        val filename = "${title}_${System.currentTimeMillis()}.jpg"

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, filename)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/GeminiPhotoEditor")
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
        }

        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            ?: return false

        resolver.openOutputStream(uri)?.use { stream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.clear()
            contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
            resolver.update(uri, contentValues, null, null)
        }
        true
    } catch (e: Exception) {
        Log.e("SaveGallery", "Error saving bitmap to gallery", e)
        false
    }
}

fun shareBitmap(context: Context, bitmap: Bitmap, prompt: String) {
    try {
        val cachePath = File(context.cacheDir, "images").apply { mkdirs() }
        val file = File(cachePath, "gemini_edit_${System.currentTimeMillis()}.jpg")
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 95, out)
        }
        val contentUri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/jpeg"
            putExtra(Intent.EXTRA_STREAM, contentUri)
            putExtra(Intent.EXTRA_TEXT, "Created with Gemini Photo Editor & Trends: \"$prompt\"")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share Gemini Creation"))
    } catch (e: Exception) {
        Log.e("ShareBitmap", "Failed to share image", e)
        Toast.makeText(context, "Could not open share dialog.", Toast.LENGTH_SHORT).show()
    }
}
