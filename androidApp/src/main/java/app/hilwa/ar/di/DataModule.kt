package app.hilwa.ar.di

import android.content.Context
import android.content.SharedPreferences
import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import app.hilwa.ar.sqldelight.Database
import app.hilwa.ar.table.progress.Progress
import app.hilwa.ar.table.question.Question
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(
    SingletonComponent::class
)
object DataModule {

    @Provides
    fun provideSqlDriver(
        @ApplicationContext context: Context
    ): SqlDriver = AndroidSqliteDriver(
        Database.Schema,
        context,
        "quiz_hilwa_app.db"
    )

    @Provides
    fun provideDatabase(
        sqlDriver: SqlDriver
    ): Database = Database(
        sqlDriver,
        QuestionAdapter = Question.Adapter(
            questionOptionsAdapter = object : ColumnAdapter<List<String>, String> {
                override fun decode(databaseValue: String): List<String> =
                    if (databaseValue.isEmpty()) {
                        listOf()
                    } else {
                        databaseValue.split(",")
                    }

                override fun encode(value: List<String>): String =
                    value.joinToString(separator = ",")

            }
        ),
        ProgressAdapter = Progress.Adapter(
            questionResponseAdapter = object : ColumnAdapter<List<String>, String> {
                override fun decode(databaseValue: String): List<String> =
                    if (databaseValue.isEmpty()) {
                        listOf()
                    } else {
                        databaseValue.split(",")
                    }

                override fun encode(value: List<String>): String =
                    value.joinToString(separator = ",")
            }
        )
    )

    @Provides
    fun provideSharedPreferences(
        @ApplicationContext appContext: Context
    ): SharedPreferences = appContext.getSharedPreferences(
        "tudu",
        Context.MODE_PRIVATE
    )

    @Provides
    fun provideSharedPrefEditor(
        sp: SharedPreferences
    ): SharedPreferences.Editor = sp.edit()

    @Provides
    fun provideFirebaseAuth(
        @ApplicationContext appContext: Context
    ): FirebaseAuth {
        val auth = FirebaseAuth.getInstance()
        auth.setLanguageCode(appContext.resources.configuration.locales[0].language)
        return auth
    }

    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

}