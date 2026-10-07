package br.com.uol.pagbank.plugpagservice.demo.sampaio

import android.content.Context
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetSocketAddress

class TerminalDiscoveryServer(context: Context) {
    private val context = context.applicationContext

    private val porta = 9091

    private var socket: DatagramSocket? = null

    @Volatile
    private var executando = false

    fun iniciar() {
        if (executando) return

        executando = true

        Thread {
            try {
                val terminalId = TerminalIdentificador.obter(context)

                socket = DatagramSocket(null).apply {
                    reuseAddress = true
                    bind(InetSocketAddress(porta))
                }

                while (executando) {
                    val buffer = ByteArray(1024)
                    val pacote = DatagramPacket(buffer, buffer.size)

                    socket?.receive(pacote)

                    val mensagem = String(pacote.data, 0, pacote.length, Charsets.UTF_8)

                    if (mensagem == terminalId) {
                        val resposta = terminalId.toByteArray(Charsets.UTF_8)

                        val retorno =
                            DatagramPacket(resposta, resposta.size, pacote.address, pacote.port)

                        socket?.send(retorno)
                    }
                }

            } catch (e: Exception) {
            }
        }.start()
    }

    fun parar() {
        executando = false
        socket?.close()
        socket = null
    }
}
