package com.example.kotlinchat.network

import android.content.ContentValues
import android.util.Log
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class RequestHttpURLConnection {

    fun request(url: String, params: ContentValues): String{
        var cookie:String

        var urlConn: HttpURLConnection

        val sbParams:StringBuffer = StringBuffer()

        /**
         * 1. StringBuffer에 파라미터 연결
         */

        if(params == null)
            sbParams.append("")
        else{
            var isAnd:Boolean = false
            var key:String
            var value:String

            sbParams.append("{")

            for (parameter in params.valueSet().iterator()){
                key = parameter.key
                value = parameter.value.toString()

                if (key == "cookie"){
                    cookie = value;
                    continue;
                }

                // 파라미터가 두개 이상일 때, 파라미터 사이에 &를 붙인다.
                if (isAnd)
                    sbParams.append(",");

                sbParams.append("\"").append(key).append("\"").append(":").
                    append("\"").append(value).append("\"")

                // 파라미터가 2개 이상이면 isAnd를 true로 바꾸고 다음 루프부터 &를 붙인다.
                if(!isAnd)
                    if(params.size() >= 2)
                        isAnd = true
            }
            sbParams.append("}")
        }

        /**
         * 2. HttpURLConnection을 통해 web의 데이터를 가져온다.
         */
        try {
            val url = URL(url)
            urlConn = url.openConnection() as HttpURLConnection
            // [2-1]. urlConn 설정.`
            urlConn.requestMethod = "POST" // URL 요청에 대한 메소드 설정 : POST  //
            //            urlConn.setRequestProperty("Cookie", cookie)
            urlConn.setRequestProperty("content-type", "application/json;charset=UTF-8")
            val pw = PrintWriter(
                OutputStreamWriter(urlConn.outputStream, "utf-8")
            )
            pw.write(sbParams.toString())
            pw.flush()
            // [2-2]. parameter 전달 및 데이터 읽어오기.
            val strParams =
                sbParams.toString() // sbParams에 정리한 파라미터들을 스트링으로 저장. 예)id=id1&pw=123;
            // [2-3]. 연결 요청 확인.
            // 실패 시 null을 리턴하고 메서드를 종료.
            if (urlConn.responseCode != HttpURLConnection.HTTP_OK) //                Log.d("Connection", "Failed...");
                urlConn.disconnect()
                return ""
            // [2-4]. 읽어온 결과물 리턴.
            // 요청한 URL의 출력물을 BufferReader로 받는다.
            val reader = BufferedReader(
                InputStreamReader(urlConn.inputStream, "utf-8")
            )
            // 출력물의 라인과 그 합에 대한 변수.
            var line: String?
            var page: String? = ""
            // 라인을 받아와 합친다.x`
            while (reader.readLine().also { line = it } != null) { //
                page += line
            }
            Log.d("page context", page)
            urlConn.disconnect()
            return page!!
        } catch (e: MalformedURLException) { // for URL
            e.printStackTrace()
        } catch (e: IOException) { // for openConnection().
            e.printStackTrace()
        } finally {
            Log.d("finally", "request")
        }

        return ""
    }
}