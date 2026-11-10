package tw.edu.pu.csim.tcyang.hello1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import tw.edu.pu.csim.tcyang.hello1.ui.theme.Hello1Theme
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Hello1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

fun drawCircle(magenta: Any, f: Float, offset: Any) {}

@Composable

fun Greeting(name: String, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        drawCircle(
            color = Color.Magenta,
            radius = 100f,
            center = Offset(size.width / 2, size.height / 2)
        )
        drawRect(
            Color.Blue,
            Offset(300f, 200f),
            Size(200f, 200f),
            style = Stroke(width = 5f) // 使用 style 控制外框樣式
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Hello1Theme {
        Greeting("Android")
    }
}