package taylor.com.animation_dsl

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator

/**
 * An Animator just like [ObjectAnimator], but it could reverse itself without the limitation of API level.
 * In addition, combing with [AnimSet], it is easy to build much more readable animation code. See at [AnimSet]
 */
class ObjectAnim : Anim() {
    companion object {
        private const val TRANSLATION_X = "translationX"
        private const val TRANSLATION_Y = "translationY"
        private const val SCALE_X = "scaleX"
        private const val SCALE_Y = "scaleY"
        private const val ALPHA = "alpha"
    }

    /**
     * the [ObjectAnim] is about to run
     */
    override var animator: ValueAnimator = ObjectAnimator()

    var translationX: FloatArray? = null
    var translationY: FloatArray? = null
    var scaleX: FloatArray? = null
    var scaleY: FloatArray? = null
    var alpha: FloatArray? = null
    /**
     * the object to be animated which is needed for [ObjectAnimator]
     */
    var target: Any? = null
        set(value) {
            field = value
            (animator as ObjectAnimator).target = value
        }
    /**
     * a list of [PropertyValuesHolder] to describe what kind of animations to run
     */
    private val valuesHolder = mutableListOf<PropertyValuesHolder>()

    /**
     * reverse the value of [ObjectAnimator]
     */
    override fun reverseValues() {
        valuesHolder.forEach { valuesHolder ->
            when (valuesHolder.propertyName) {
                TRANSLATION_X -> translationX?.let {
                    it.reverse()
                    valuesHolder.setFloatValues(*it)
                }
                TRANSLATION_Y -> translationY?.let {
                    it.reverse()
                    valuesHolder.setFloatValues(*it)
                }
                SCALE_X -> scaleX?.let {
                    it.reverse()
                    valuesHolder.setFloatValues(*it)
                }
                SCALE_Y -> scaleY?.let {
                    it.reverse()
                    valuesHolder.setFloatValues(*it)
                }
                ALPHA -> alpha?.let {
                    it.reverse()
                    valuesHolder.setFloatValues(*it)
                }
            }
        }
    }

    /**
     * add available [PropertyValuesHolder] to the list
     */
    fun setPropertyValueHolder() {
        translationX?.let { PropertyValuesHolder.ofFloat(TRANSLATION_X, *it) }?.let { valuesHolder.add(it) }
        translationY?.let { PropertyValuesHolder.ofFloat(TRANSLATION_Y, *it) }?.let { valuesHolder.add(it) }
        scaleX?.let { PropertyValuesHolder.ofFloat(SCALE_X, *it) }?.let { valuesHolder.add(it) }
        scaleY?.let { PropertyValuesHolder.ofFloat(SCALE_Y, *it) }?.let { valuesHolder.add(it) }
        alpha?.let { PropertyValuesHolder.ofFloat(ALPHA, *it) }?.let { valuesHolder.add(it) }
        animator.setValues(*valuesHolder.toTypedArray())
    }
}
