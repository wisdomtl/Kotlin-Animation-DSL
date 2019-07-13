package taylor.com.animation_dsl

import android.animation.Animator
import android.animation.AnimatorSet
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator

/**
 * a Container for [Anim] just like [AnimatorSet], but it could reverse itself without API level limitation.
 * In addition, it is easy to build much more readable animation code like the following:
 *
 * animSet {
 *      play {
 *          values = floatArrayOf(1.0f, 1.4f)
 *          action = { value -> tv.scaleX = (value as Float) }
 *      } with anim {
 *          values = floatArrayOf(0f, -200f)
 *          action = { value -> tv.translationY = (value as Float) }
 *      }
 *      duration = 200L
 *      interpolator = LinearInterpolator()
 * }
 *
 *
 * if you want to run animation with several properties on one Object,
 * using [ObjectAnim] is more efficient than [ValueAnim], like the following:
 *
 * animSet {
 *      animObject {
 *          target = tvTitle
 *          translationX = floatArrayOf(0f, 200f)
 *          alpha = floatArrayOf(1.0f, 0.3f)
 *          scaleX = floatArrayOf(1.0f, 1.3f)
 *      }
 *      duration = 100L
 *      interpolator = AccelerateInterpolator()
 * }
 *
 */
class AnimSet {
    private val animatorSet = AnimatorSet()
    private val animators by lazy { mutableListOf<Anim>() }
    var interpolator
        get() = LinearInterpolator() as Interpolator
        set(value) {
            animatorSet.interpolator = value
        }
    var duration
        get() = 300L
        set(value) {
            animatorSet.duration = value
        }
    var onRepeat: ((Animator) -> Unit)? = null
    var onEnd: ((Animator) -> Unit)? = null
    var onCancel: ((Animator) -> Unit)? = null
    var onStart: ((Animator) -> Unit)? = null
    var isReverse: Boolean = false

    /**
     * if you want to play animations together, [play] must be the start of the animation creation chain which follows several [anim],
     * like the following:
     *
     * animSet {
     *      play {
     *          value = floatArrayOf(1.0f, 1.4f)
     *          action = { value -> tv.scaleX = (value as Float) }
     *      } with anim {
     *          values = floatArrayOf(0f, -200f)
     *          action = { value -> tv.translationY = (value as Float) }
     *      }
     *      duration = 200L
     *      interpolator = LinearLayoutInterpolator()
     * }
     *
     */
    fun play(animCreation: ValueAnim.() -> Unit): AnimatorSet.Builder = ValueAnim().apply(animCreation).also { animators.add(it) }.let { animatorSet.play(it.getAnim()) }

    /**
     * [anim] create a single [ValueAnim]
     * [with] and [before] is available to combine several [ValueAnim] into one complex animation set by chain-invocation style.
     */
    fun anim(animCreation: ValueAnim.() -> Unit): Animator = ValueAnim().apply(animCreation).also { animators.add(it) }.getAnim()

    /**
     * build an [ObjectAnim] with a much shorter and readable code by DSL
     */
    fun animObject(action: ObjectAnim.() -> Unit) {
        ObjectAnim().apply(action).also { it.setPropertyValueHolder() }.let { animators.add(it) }
    }

    /**
     * reverse the animation
     */
    fun reverse() {
        animators.takeIf { !isReverse }?.forEach { valueAnim -> valueAnim.reverseValues() }
        animatorSet.start()
        isReverse = true
    }

    fun build() {
//        animatorSet.playTogether(animators.map { it.getAnim() })
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
                animation?.let { onRepeat?.invoke(it) }
            }

            override fun onAnimationEnd(animation: Animator?) {
                animation?.let { onEnd?.invoke(it) }
            }

            override fun onAnimationCancel(animation: Animator?) {
                animation?.let { onCancel?.invoke(it) }
            }

            override fun onAnimationStart(animation: Animator?) {
                animation?.let { onStart?.invoke(it) }
            }
        })
    }

    fun start() {
        animators.takeIf { isReverse }?.forEach { valueAnim -> valueAnim.reverseValues() }
        animatorSet.start()
        isReverse = false
    }

    infix fun Animator.before(animator: Animator): Animator = animatorSet.play(this).before(animator).let { animator }
}

infix fun AnimatorSet.Builder.with(animator: Animator): AnimatorSet.Builder = with(animator)


/**
 * build a set of animation with a much shorter and readable code by DSL
 */
fun animSet(creation: AnimSet.() -> Unit) = AnimSet().apply { creation() }.also { it.build() }
