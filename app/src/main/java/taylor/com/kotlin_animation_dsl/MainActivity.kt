package taylor.com.kotlin_animation_dsl

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import kotlinx.android.synthetic.main.activity_main.*
import taylor.com.animation_dsl.animSet
import taylor.com.animation_dsl.with

class MainActivity : AppCompatActivity() {

    private val objectAnim by lazy {
        animSet {
            animObject {
                target = tv
                translationX = floatArrayOf(0f, 200f)
                scaleX = floatArrayOf(1f, 1.5f)
                alpha = floatArrayOf(1f, 0.5f)
            }
            duration = 300L
            interpolator = AccelerateInterpolator()
        }
    }

    private val togetherAnim by lazy {
        animSet {
            play {
                values = floatArrayOf(1.0f, 1.4f)
                action = { value -> tv.scaleX = (value as Float) }
            } with anim {
                values = floatArrayOf(0f, -200f)
                action = { value -> tv.translationY = (value as Float) }
            } with anim {
                values = floatArrayOf(1.0f, 0.5f)
                action = { value -> tv.alpha = (value as Float) }
            }
            duration = 200L
            interpolator = AccelerateDecelerateInterpolator()
        }
    }

    private val sequenceAnim by lazy {
        animSet {
            anim {
                values = floatArrayOf(1.0f, 1.5f)
                action = { value -> tv.scaleX = (value as Float) }
            } before anim {
                values = floatArrayOf(1.5f, 1.0f)
                action = { value -> tv.scaleX = (value as Float) }
            } before anim {
                values = floatArrayOf(1.0f, 1.5f)
                action = { value -> tv.scaleX = (value as Float) }
            }
            duration = 400L
            interpolator = LinearInterpolator()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * case:ObjectAnim
         */
        btnObject.setOnClickListener { objectAnim.start() }
        btnObjectReverse.setOnClickListener { objectAnim.reverse() }

        /**
         * case:play animations together by DSL and reverse it
         */
        btnFree.setOnClickListener { togetherAnim.start() }
        btnFreeReverse.setOnClickListener { togetherAnim.reverse() }

        /**
         * case:play animations Sequence by DSL and reverse it
         */
        btnSequence.setOnClickListener { sequenceAnim.start() }
        btnSequenceReverse.setOnClickListener { sequenceAnim.reverse() }
    }

}
