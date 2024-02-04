# Inazuma Quiz
Inazuma Quiz es una aplicación de preguntas y respuestas que te permite poner a prueba tus conocimientos de Inazuma Eleven. 
A parte de tener unas preguntas ya predefinidas, también puedes añadir tus propias preguntas para que puedes prestarles el movil a tus amigos para que las respondan.

Esta aplicación se ha desarrollado con Jetpack Compose, siguiendo el patrón de arquitectura MVVM y utilizando la biblioteca de navegación de Jetpack. 
A parte de esto se usa una base de datos local para almacenar las preguntas y los rankings.

## Reglas del juego
- Cada pregunta tiene un tiempo límite de 30 segundos.
- Si el tiempo se agota, se resta 20 puntos y se pasa a la siguiente pregunta.
- Si la respuesta es correcta, se suman los segundos restantes.
- Si la respuesta es incorrecta, se restan 10 puntos.
- Al final de las preguntas, puedes añadir tu partida con tu nombre o dejarlo en blanco para guardarlo como anoniomo.

## Como se vé la aplicación
INSERTAR IMAGENES AQUI

---

Ahora explicaré cosas que he aprendido y que he implementado en la aplicación.

## Índice

### Room
Room es una biblioteca de persistencia que proporciona una capa de abstracción sobre SQLite para permitir un acceso más robusto a la base de datos mientras se aprovechan las características de SQLite.
En Room necesitamos minimo 3 componentes:
- `Entity`: Representa una tabla en la base de datos.
- `Dao`: Contiene los métodos que se utilizan para acceder a la base de datos.
- `Database`: Contiene la base de datos y actúa como punto de acceso principal para la conexión con la base de datos.

#### Ejemplo de implementación

##### ENTITY
```kotlin
@Entity(tableName = "questions")
data class Questions(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    @ColumnInfo(name = "question_type")
    val question: String,
    @ColumnInfo(name = "correct")
    val correct: String,
    @ColumnInfo(name = "wrong1")
    val wrong1: String,
    @ColumnInfo(name = "wrong2")
    val wrong2: String
)
```

##### DAO
```kotlin
@Dao
interface QuestionsDao {

   @Query("SELECT * FROM questions ORDER BY RANDOM() LIMIT 10")
   fun getQuestions(): List<Questions>

   @Insert
   suspend fun insertQuestion(questions: List<Questions>)

   @Insert
   suspend fun insertQuestion(questions: Questions)
}
```
Aquí los inserts son suspend functions porque Room no permite hacer operaciones en la base de datos desde el hilo principal por los que los tendremos que ejecutar a traves de corrutinas.

##### DATABASE
```kotlin
@Database(
   entities = [Questions::class, Ranking::class],
   version = 1,
   exportSchema = false
)
abstract class RoomDatabase: RoomDatabase() {
   abstract fun QuestionsDao():QuestionsDao
   abstract fun RankingDao():RankingDao
}
```

#### Uso de Room(ViewModel)
```kotlin
class QuestionsViewModel(private val questionsDao: QuestionsDao): ViewModel() {
   fun addQuestion(question: Questions) {
      viewModelScope.launch {
         // need to use Dispatchers.IO to insert in database in background thread(if not, app will crash)
         withContext(Dispatchers.IO) {
            questionsDao.insertQuestion(question)
         }
      }
   }
}
```
Al trabajar con room tendremos que pasarle el dao a nuestro ViewModel para que pueda interactuar con la base de datos. 
Esto lo hacemos a través de la inyección de dependencias.

#### Ultimo paso
Desde la main deberemos crear la base de datos y el dao y pasarselo a nuestro ViewModel
```kotlin
val database = Room.databaseBuilder(
   this,
   RoomDatabase::class.java,
   "db_quizz" // nombre de la base de datos
).build()
val questionsViewModel = QuestionsViewModel(database.QuestionsDao())
```


### Navigation
Navigation es la capacidad de moverse entre diferentes pantallas o destinos en tu aplicación. 
La biblioteca de navegación de Jetpack Compose proporciona herramientas y patrones para facilitar la navegación dentro de la interfaz de usuario de tu aplicación. 
Esto es especialmente útil cuando tu aplicación tiene múltiples pantallas y quieres gestionar la transición entre ellas de manera estructurada y eficiente.

#### Ejemplo de Implementación
Este es un ejemplo básico de cómo implementar la navegación en Compose
```kotlin
@Composable
fun Nav(questionsViewModel: QuestionsViewModel, rankingViewModel: RankingViewModel){
   val navController = rememberNavController()
   NavHost(
      navController = navController,
      startDestination = "start"
   ){
      composable("start"){
         StartScreen(navController)
      }
      composable("game"){
         GameScreen(navController, questionsViewModel, rankingViewModel)
      }
      composable("ranking"){
         RankingScreen(navController, rankingViewModel)
      }
      composable("addQuestions"){
         QuestionScreen(navController, questionsViewModel)
      }
   }
}
```

#### Estructura del código
- `Composable Nav`:  Este composable actúa como el punto de entrada para la navegación. Contiene un NavHost que define los destinos y sus respectivos composables.
- `rememberNavController()`: La función rememberNavController crea un NavController que retendrá su estado incluso cuando el composable se recomponga. Este NavController se utiliza para navegar entre destinos.
- `Destinos (composable("""))`: Cada destino representa una pantalla en la aplicación. En el ejemplo, tenemos destinos para la pantalla de inicio ("start"), el juego ("game"), el ranking ("ranking"), y añadir preguntas ("addQuestions").
- `Parámetros`: Se pasan instancias del navController y a parte QuestionsViewModel y RankingViewModel a los composables relevantes para que puedan interactuar con los datos y la lógica de la aplicación. Siempre tenemos que darles el navController para que cuando estemos en un destino podamos navegar a otros.

#### Uso de la navegación
- `NavHost y composable`: Estas funciones son proporcionadas por la biblioteca de navegación de Compose. `NavHost` establece el contenedor principal de la navegación, mientras que `composable` define los destinos y los composables asociados a cada destino.
- `navController`: Se pasa a cada composable para que puedan interactuar con la navegación. Pueden utilizar este controlador para navegar a otros destinos.

#### Ejemplo de navegación
```kotlin
navController.navigate("nombreDestino")
```

### Corrutinas en Compose
#### LaunchedEffect
- Se ejecuta cuando el composable es lanzado
```kotlin
LaunchedEffect("getQuestions"){
   questionsViewModel.getQuestions()
}
```

#### DisposableEffect
- Se usa para realizar acciones cuando el composable se une o se desvincula(en este caso)
```kotlin
DisposableEffect(Unit) {
   onDispose {
      // Detener el temporizador cuando el composable se desvincula
      questionsViewModel.resetTimer()
   }
}
```

### CountDownTimer
`CountDownTimer` es una clase proporcionada en Android que te permite realizar acciones repetitivas durante un período de tiempo específico y cuenta regresiva hasta que se completa ese tiempo. 
Es especialmente útil para implementar temporizadores y funciones de cuenta regresiva en aplicaciones Android.

La clase `CountDownTimer` tiene un constructor que toma dos parámetros: el tiempo total de duración del temporizador y el intervalo entre las llamadas a la función onTick. 
La duración total se especifica en milisegundos, y el intervalo también se especifica en milisegundos. 
La clase proporciona dos métodos abstractos que debes sobrescribir:
- `onTick(millisUntilFinished: Long)`: Este método se llama en cada intervalo especificado por el constructor (en el ejemplo de la función startProgressTimer, cada segundo). Proporciona el tiempo restante en milisegundos hasta que el temporizador se complete.
- `onFinish()`: Este método se llama cuando el temporizador llega a cero y se completa.
#### Ejemplo de contador
```kotlin
fun startTimer() {
     Data.counter.value = 31
     timer = object : CountDownTimer(Data.counter.value.toLong() * 1000, 1000) {
         override fun onTick(millisUntilFinished: Long) {
             Data.counter.value--
         }

         override fun onFinish() {
             // Lógica para manejar el tiempo agotado, en este caso, se resta 20 puntos y se pasa a la siguiente pregunta si hay más
             viewModelScope.launch{
                 QuestionsData.buttonState.value = "timeOut"
                 RankingData.score.value -= 20
                 delay(2000)
                 QuestionsData.buttonState.value = "none"
                 QuestionsData.roundQuestion.value += 1
                 Data.buttonEnabled.value = true
                 if (QuestionsData.roundQuestion.value < QuestionsData.questionsToShow.size) {
                     startTimer()
                     startProgressTimer()
                 }
             }
         }
     }.start()
 }

fun resetTimer() {
   timer?.cancel()
   progressTimer?.cancel()
}
 ```

### Cambiar el icono y nombre de la aplicación
1. Cambiar el icono de la aplicación: 
    - Añadir el icono
      - File > New > Image Asset
      - Añadir tu icono
    - Cambiar el icono
      - Abrir el archivo `android/app/src/main/AndroidManifest.xml`
      - Buscar la etiqueta `<application` y el atributo `android:icon=` y cambiar el valor de `@mipmap/ic_launcher` por el nuevo icono
      - Buscar la etiqueta `<application` y el atributo `android:roundIcon=` y cambiar el valor de `@mipmap/ic_launcher_round` por el nuevo icono
      
2. Cambiar el nombre de la aplicación:
   - En el mismo archivo de antes: `android/app/src/main/AndroidManifest.xml`
   - Buscar la etiqueta `<application` y el atributo `android:label=` y darle control + click para ir al archivo `android/app/src/main/res/values/strings.xml`
   - Cambiar el valor de la etiqueta `<string name="app_name">` por el nuevo nombre de la aplicación