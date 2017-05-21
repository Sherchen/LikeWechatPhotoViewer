# 效果预览
![image](https://github.com/Sherchen/LikeWechatPhotoViewer/blob/master/avatar/avatar.gif)

# 重要代码

见utils/ScreenHelp.java

```
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail, and the
        // final bounds are the global visible rectangle of the container view. Also
        // set the container view's offset as the origin for the bounds, since that's
        // the origin for the positioning animation properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        container.getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);
```


```
AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
                .with(ObjectAnimator
                        .ofFloat(expandedImageView, View.SCALE_X, startScale))
                .with(ObjectAnimator
                        .ofFloat(expandedImageView, View.SCALE_Y, startScale));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
```

# 依赖库

[ArchitectureTemplate](https://github.com/Sherchen/ArchitectureTemplate)

[AndroidUtilCode](https://github.com/Sherchen/AndroidUtilCode)


# Thanks

[developer android](https://developer.android.com/training/animation/zoom.html)

[glide](https://github.com/bumptech/glide)

# License

Copyright 2017 Sherchen.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
