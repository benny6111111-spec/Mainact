package tw.edu.pu.csim.tcyang.hello1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import tw.edu.pu.csim.tcyang.hello1.ui.theme.Hello1Theme

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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var canvasWidth by remember { mutableStateOf(0f) }
    var canvasHeight by remember { mutableStateOf(0f) }

    val minionsImage: ImageBitmap =
        ImageBitmap.imageResource(id = R.drawable.minions)

    // 控制動畫：isRunning 決定是否移動，progress 是 0~1 的進度
    var isRunning by remember { mutableStateOf(true) }
    val progress = remember { Animatable(0f) }

    LaunchedEffect(isRunning) {
        if (isRunning) {
            while (true) {
                // 從目前位置繼續滑到終點，剩餘時間依進度計算
                val remaining = ((1f - progress.value) * 3000).toInt()
                progress.animateTo(1f, tween(remaining, easing = LinearEasing))
                progress.snapTo(0f) // 到終點後回到起點重來
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged {
                    canvasWidth = it.width.toFloat()
                    canvasHeight = it.height.toFloat()
                }
        ) {
            // 繪製洋紅色的圓，半徑 100，圓心在畫布中央
            drawCircle(
                color = Color.Magenta,
                radius = 100f,
                center = Offset(size.width / 2, size.height / 2)
            )

            // 繪製藍色空心矩形，左上角 (300,200)，寬度與高度 200
            drawRect(
                color = Color.Blue,
                topLeft = Offset(300f, 200f),
                size = Size(200f, 200f),
                style = Stroke(width = 5f)
            )

            // 繪製紅色線條
            drawLine(
                color = Color.Red,
                start = Offset(200f, 800f),
                end = Offset(size.width, size.height),
                strokeWidth = 10f
            )

            // 紅線的起點與終點
            val startX = 200f
            val startY = 800f
            val endX = size.width
            val endY = size.height

            // 依照 progress 算出線上目前的位置
            val currentX = startX + (endX - startX) * progress.value
            val currentY = startY + (endY - startY) * progress.value

            // 讓小小兵的中心點貼在線上
            val minionsWidth = minionsImage.width
            val minionsHeight = minionsImage.height

            translate(currentX - minionsWidth / 2f, currentY - minionsHeight / 2f) {
                drawImage(minionsImage)
            }
        }

        Text("畫布寬度 $canvasWidth, 長度 $canvasHeight")

        Button(
            onClick = { isRunning = !isRunning },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text(if (isRunning) "停止" else "繼續")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Hello1Theme {
        Greeting("Android")
    }
}