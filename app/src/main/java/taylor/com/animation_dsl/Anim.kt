package taylor.com.animation_dsl

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator

/**
 * an Animation just like [ValueAnimator], but it could reverse itself without the limitation of API level
 * [ValueAnimator.reverse] is only available to the API level over 26.
 * so this class comes to help.
 */
abstract class Anim {
    /**
     * the real Animator which is about to run
     */
    abstract var animator: ValueAnimator
    var builder: AnimatorSet.Builder? = null
    /**
     * the duration of Animator
     */
    var duration
        get() = 300L
        set(value) {
            animator.duration = value
        }
    /**
     * the interpolator of Animator
     */
    var interpolator
        get() = LinearInterpolator() as Interpolator
        set(value) {
            animator.interpolator = value
        }
    /**
     * start delay of Animator
     */
    var delay
        get() = 0L
        set(value) {
            animator.startDelay = value
        }
    /**
     * reverse the value of [ValueAnimator]
     */
    abstract fun reverseValues()
}