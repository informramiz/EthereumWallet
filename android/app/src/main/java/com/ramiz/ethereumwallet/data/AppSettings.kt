package com.ramiz.ethereumwallet.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSettings @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    private object Keys {
        val MNEMONIC = stringPreferencesKey("mnemonic")
    }
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "")

    val isWalletActive: Flow<Boolean>
        get() = context.dataStore.data.map { it[Keys.MNEMONIC] != null }

    val mnemonic: Flow<String?>
        get() = context.dataStore.data.map { it[Keys.MNEMONIC] }

    suspend fun saveMnemonic(mnemonic: String) {
        context.dataStore.edit { preferences ->
            preferences[Keys.MNEMONIC] = mnemonic
        }
    }

    suspend fun clearSettings() {
        context.dataStore.edit { it.clear() }
    }
}
