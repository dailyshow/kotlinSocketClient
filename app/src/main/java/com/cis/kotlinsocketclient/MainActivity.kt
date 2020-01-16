package com.cis.kotlinsocketclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.io.DataInputStream
import java.io.DataOutputStream
import java.lang.Exception
import java.net.Socket

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        btn.setOnClickListener {
            val thread = NetworkTread()
            thread.start()
        }
    }

    inner class NetworkTread : Thread() {
        override fun run() {
            try {
                val socket = Socket("192.168.34.62", 55555)

                val input = socket.getInputStream()
                var dis = DataInputStream(input)

                val output = socket.getOutputStream()
                var dos = DataOutputStream(output)

                // server가 먼저 데이터를 보내고 있기 때문에 받아주는것부터 한다.
                var data1 = dis.readInt()
                var data2 = dis.readDouble()
                var data3 = dis.readUTF()

                dos.writeInt(200)
                dos.writeDouble(22.22)
                dos.writeUTF("클라이언트가 보낸 문자열")

                socket.close()

                runOnUiThread {
                    tv.text = "data1 : ${data1}\n"
                    tv.append("data2 :${data2}\n")
                    tv.append("data2 :${data3}")
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
