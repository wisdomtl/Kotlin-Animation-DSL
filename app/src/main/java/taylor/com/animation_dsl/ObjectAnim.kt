package taylor.com.animation_dsl

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder

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
        private const val ROTATION = "rotation"
        private const val ROTATION_X = "rotationX"
        private const val ROTATION_Y = "rotationY"
    }

    /**
     * the [ObjectAnim] is about to run
     */
    override var animator: Animator = ObjectAnimator()
    private val objectAnimator
        get() = animator as ObjectAnimator

    /**
     * predefine properties for [android.view.View]
     */
    var translationX: FloatArray? = null
    var translationY: FloatArray? = null
    var scaleX: FloatArray? = null
    var scaleY: FloatArray? = null
    var alpha: FloatArray? = null
    var rotation: FloatArray? = null
    var rotationX: FloatArray? = null
    var rotationY: FloatArray? = null
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
    override fun reverse() {
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
                ROTATION -> rotation?.let {
                    it.reverse()
                    valuesHolder.setFloatValues(*it)
                }
                ROTATION_X -> rotationX?.let {
                    it.reverse()
                    valuesHolder.setFloatValues(*it)
                }
                ROTATION_Y -> rotationY?.let {
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
        rotation?.let { PropertyValuesHolder.ofFloat(ROTATION, *it) }?.let { valuesHolder.add(it) }
        rotationX?.let { PropertyValuesHolder.ofFloat(ROTATION_X, *it) }?.let { valuesHolder.add(it) }
        rotationY?.let { PropertyValuesHolder.ofFloat(ROTATION_Y, *it) }?.let { valuesHolder.add(it) }
        objectAnimator.setValues(*valuesHolder.toTypedArray())
    }
}
