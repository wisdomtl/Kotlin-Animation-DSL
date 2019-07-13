package taylor.com.animation_dsl

import android.animation.ValueAnimator

/**
 * An Animator just like [ValueAnimator], but it could reverse itself without the limitation of API level.
 * In addition, combing with [AnimSet], it is easy to build much more readable animation code. See at [AnimSet]
 */
class ValueAnim : Anim() {
    /**
     * the [ValueAnimator] is about to run
     */
    override var animation: ValueAnimator = ValueAnimator()

    /**
     * the animation value
     */
    var values: Any? = null
        set(value) {
            field = value
            value?.let {
                when (it) {
                    is FloatArray -> animation.setFloatValues(*it)
                    is IntArray -> animation.setIntValues(*it)
                    else -> throw IllegalArgumentException("unsupported value type")
                }
            }
        }

    /**
     * [action] describe what to animate
     */
    var action: ((Any) -> Unit)? = null
        set(value) {
            field = value
            animation.addUpdateListener { valueAnimator ->
                valueAnimator.animatedValue.let { value?.invoke(it) }
            }
        }

    /**
     * reverse the value of [ValueAnimator]
     */
    override fun reverseValues() {
        values?.let {
            when (it) {
                is FloatArray -> {
                    it.reverse()
                    animation.setFloatValues(*it)
                }
                is IntArray -> {
                    it.reverse()
                    animation.setIntValues(*it)
                }
                else -> throw IllegalArgumentException("unsupported type of value")
            }
        }
    }
}