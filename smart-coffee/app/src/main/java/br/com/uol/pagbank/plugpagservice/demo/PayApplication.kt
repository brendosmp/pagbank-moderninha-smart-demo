package br.com.uol.pagbank.plugpagservice.demo

import android.app.Application
import br.com.uol.pagbank.plugpagservice.demo.di.assetModule
import br.com.uol.pagbank.plugpagservice.demo.di.packageManagerModule
import br.com.uol.pagbank.plugpagservice.demo.di.plugpagModule
import br.com.uol.pagbank.plugpagservice.demo.sampaio.TerminalDiscoveryServer
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PayApplication : Application() {
    private lateinit var discoveryServer: TerminalDiscoveryServer

    override fun onCreate() {
        super.onCreate()

        discoveryServer = TerminalDiscoveryServer(this)
        discoveryServer.iniciar()

        startKoin {
            androidContext(this@PayApplication)
            modules(plugpagModule)
            modules(packageManagerModule)
            modules(assetModule)
        }
    }
}
