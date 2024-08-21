package com.ari.prodvizhenie.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.ari.prodvizhenie.auth.data.manager.LocalUserManagerImpl
import com.ari.prodvizhenie.auth.data.manager.TokenManager
import com.ari.prodvizhenie.auth.data.remote.LoginApi
import com.ari.prodvizhenie.auth.data.repository.LoginRepositoryImpl
import com.ari.prodvizhenie.auth.domain.manager.LocalUserManager
import com.ari.prodvizhenie.auth.domain.repository.LoginRepository
import com.ari.prodvizhenie.calendar.data.remote.PostApi
import com.ari.prodvizhenie.calendar.data.repository.PostRepositoryImpl
import com.ari.prodvizhenie.calendar.domain.repository.PostRepository
import com.ari.prodvizhenie.details.data.remote.StatApi
import com.ari.prodvizhenie.details.data.repository.StatsRepositoryImpl
import com.ari.prodvizhenie.details.domain.repository.StatsRepository
import com.ari.prodvizhenie.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager =
        TokenManager(context)

    @Provides
    @Singleton
    fun provideLoginApi(): LoginApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginApi::class.java)
    }

    @Provides
    @Singleton
    fun providePostApi(): PostApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostApi::class.java)
    }

    @Provides
    @Singleton
    fun provideStatsApi(): StatApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StatApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ): LocalUserManager = LocalUserManagerImpl(application)

    @Provides
    @Singleton
    fun provideLoginRepository(
        loginApi: LoginApi
    ): LoginRepository = LoginRepositoryImpl(loginApi)


    @Provides
    @Singleton
    fun providePostRepository(
        postApi: PostApi
    ): PostRepository = PostRepositoryImpl(postApi)

    @Provides
    @Singleton
    fun provideStatRepository(
        statApi: StatApi
    ): StatsRepository = StatsRepositoryImpl(statApi)
}