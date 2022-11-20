package com.example.a2022swdp_latte_sohyun

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import java.lang.Math.abs
import java.util.*

class MainActivity : AppCompatActivity() {
    var p_num = 3 //참가자 수
    var k = 1 //참가자번호
    val point_list = mutableListOf<Float>()
    var isBlind = false

    fun start(){
        setContentView(R.layout.activity_start)
        var tv_pnum: TextView = findViewById(R.id.tv_pnum)
        var btn_minus: Button = findViewById(R.id.btn_minus)
        var btn_plus: Button = findViewById(R.id.btn_plus)
        var btn_start: Button = findViewById(R.id.btn_start)
        var btn_blind: Button = findViewById(R.id.btn_blind)

        btn_blind.setOnClickListener{
            isBlind= !isBlind
            if(isBlind == true){
                btn_blind.text="Blind 모드 ON"
            }else{
                btn_blind.text="Blind 모드 OFF"
        }
        }

        tv_pnum.text=p_num.toString()
        btn_minus.setOnClickListener {
            p_num--
            if(p_num==0){
                p_num =1
            }
            tv_pnum.text=p_num.toString()
        }
        btn_plus.setOnClickListener {
            p_num++
            tv_pnum.text=p_num.toString()
        }
        btn_start.setOnClickListener{
            main()
        }
    }
    fun main(){
        setContentView(R.layout.activity_main)
        var timerTask: Timer? =null
        var stage = 1
        var sec: Int = 0
        var tv: TextView = findViewById(R.id.tv_pnum)
        var tv_t: TextView = findViewById(R.id.tv_timer)
        var tv_p: TextView = findViewById(R.id.tv_point)
        var tv_people: TextView = findViewById(R.id.tv_people)
        var btn: Button = findViewById(R.id.btn_minus)
        var btn_i: Button = findViewById(R.id.btn_i)
        val random_box = Random()
        val num =random_box.nextInt(1001)
        val bg_main: ConstraintLayout = findViewById(R.id.bg_main)
        // #68FB8792, #57FF5722, #5BF6D77B, #924CAF50, #837ABCF1, #653F51B5, #85673AB7
        val color_list = mutableListOf<String>("#68FB8792", "#57FF5722", "#5BF6D77B", "#924CAF50", "#837ABCF1", "#653F51B5", "#85673AB7")
        var color_index =k%7-1
        if(color_index ==-1){
            color_index=6
        }
        val color_sel= color_list.get(color_index)
        bg_main.setBackgroundColor(Color.parseColor(color_sel))


        tv.text = ((num.toFloat())/100).toString()
        btn.text = "시작"
        tv_people.text="참가자 $k"

        btn_i.setOnClickListener{
            point_list.clear()
            k = 1
            start()
        }

        btn.setOnClickListener {
            stage ++
            if (stage == 2) {
                timerTask =  kotlin.concurrent.timer(period = 10) {
                    sec++
                    runOnUiThread {
                        if (isBlind == false) {
                            tv_t.text = (sec.toFloat() / 100).toString()
                        }else if(isBlind==true && stage ==2) {
                            tv_t.text = "???"
                        }
                    }
                }
                btn.text="정지"
            } else if(stage == 3) {
                tv_t.text = (sec.toFloat() / 100).toString()
                timerTask?.cancel()
                val point = abs(sec-num).toFloat()/100 //결정된 점수 스택
                point_list.add(point)
                tv_p.text = point.toString()
                btn.text= "다음"
                stage =0
            }else if(stage==1){
                if(k < p_num){
                k ++
                main()
            } else{
                end()
                }
        }
    }
    }
    fun end(){
        setContentView(R.layout.activity_end)
        var tv_last: TextView = findViewById(R.id.tv_last)
        var tv_lpoint: TextView = findViewById(R.id.tv_lpoint)
        var btn_init: Button = findViewById(R.id.btn_init)

        tv_lpoint.text=(point_list.maxOrNull()).toString()//최댓값 찾는 함수
        var index_last=point_list.indexOf(point_list.maxOrNull())
        tv_last.text="참가자 "+(index_last+1).toString()

        btn_init.setOnClickListener{
            point_list.clear()
            k = 1
            start()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        start()
    }
}