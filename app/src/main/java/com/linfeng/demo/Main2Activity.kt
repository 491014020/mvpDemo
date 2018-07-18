package com.linfeng.demo

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import io.reactivex.Observable
import java.io.File

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        Observable.just("").map { s -> if (s == null) "" else "123" }
                .filter { s -> s == "" }
                .subscribe { s ->
                    Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
                }

        Observable.just(File("")).map { t -> BitmapFactory.decodeFile(t.path) }
                .map { t -> t }
                .flatMap({ t -> Observable.just(t == null) })
                .subscribe { }
    }
}
