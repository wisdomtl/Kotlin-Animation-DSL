# Kotlin-Animation-DSL
This repository create a series of API to make animation code shorter and more readable, it has the following features:
1. Using just one third of code to create animation compare to origin Android API.
2. The code written by `Kotlin-Animation-DSL` is just like an enghlish article.

## Example
If we want to do an animation like the following: scale textView and translate button at the same time, then stretch imageView. in the end of animation, show a toast.

### Android style
Using Android origin API, the code will like this:
```
PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 1.3f);
PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 1.3f);
ObjectAnimator tvAnimator = ObjectAnimator.ofPropertyValuesHolder(textView, scaleX, scaleY);
tvAnimator.setDuration(300);
tvAnimator.setInterpolator(new LinearInterpolator());

PropertyValuesHolder translationX = PropertyValuesHolder.ofFloat("translationX", 0f, 100f);
ObjectAnimator btnAnimator = ObjectAnimator.ofPropertyValuesHolder(button, translationX);
btnAnimator.setDuration(300);
btnAnimator.setInterpolator(new LinearInterpolator());

ValueAnimator rightAnimator = ValueAnimator.ofInt(ivRight, screenWidth);
rightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        int right = ((int) animation.getAnimatedValue());
        imageView.setRight(right);
    }
});
rightAnimator.setDuration(400);
rightAnimator.setInterpolator(new LinearInterpolator());

AnimatorSet animatorSet = new AnimatorSet();
animatorSet.play(tvAnimator).with(btnAnimator);
animatorSet.play(tvAnimator).before(rightAnimator);
animatorSet.addListener(new Animator.AnimatorListener() {
    @Override
    public void onAnimationStart(Animator animation) {}
    @Override
    public void onAnimationEnd(Animator animation) {
        Toast.makeText(activity,"animation end" ,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onAnimationCancel(Animator animation) {}
    @Override
    public void onAnimationRepeat(Animator animation) {}
});
animatorSet.start();
```
Too long and unreadable!

### DSL style
Let us do it by DSL:
```
animSet {
    objectAnim {
        target = textView
        scaleX = floatArrayOf(1.0f,1.3f)
        scaleY = scaleX
        duration = 300L
        interpolator = LinearInterpolator()
    } with objectAnim {
        target = button
        translationX = floatArrayOf(0f,100f)
        duration = 300
        interpolator = LinearInterpolator()
    } before anim{
        values = intArrayOf(ivRight,screenWidth)
        action = { value -> imageView.right = value as Int }
        duration = 400
        interpolator = LinearInterpolator()
    }
    onEnd = Toast.makeText(activity,"animation end",Toast.LENGTH_SHORT).show()
    start()
}
```
The animations' creation and the relationship between them is define in one method.You can use `with` and `before` to define the sequence of animations just like speaking english.

