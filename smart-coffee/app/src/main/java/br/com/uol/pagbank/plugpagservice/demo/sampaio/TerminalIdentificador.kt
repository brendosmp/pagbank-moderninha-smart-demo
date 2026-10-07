package br.com.uol.pagbank.plugpagservice.demo.sampaio

import android.content.Context
import androidx.core.content.edit
import java.util.UUID

object TerminalIdentificador {
    private const val PREFS = "terminal_config"
    private const val CHAVE_TERMINAL_ID = "terminal_id"

    fun obter(context: Context): String {
        val preferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

        val idSalvo = preferences.getString(CHAVE_TERMINAL_ID, null)

        if (idSalvo != null)
            return idSalvo

        val novoId = UUID.randomUUID()
            .toString()
            .replace("-", "")
            .uppercase()
            .take(12)

        preferences.edit { putString(CHAVE_TERMINAL_ID, novoId) }

        return novoId
    }
}
