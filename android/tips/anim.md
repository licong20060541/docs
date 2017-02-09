           ViewPropertyAnimator anim = v.animate();
           if (hasScaleChangedFrom(v.getScaleX())) {
                anim.scaleX(scale)
                    .scaleY(scale);
                requiresLayers = true;
            }
            if (hasAlphaChangedFrom(v.getAlpha())) {
                // Use layers if we animate alpha
                anim.alpha(alpha);
                requiresLayers = true;
            }
            if (requiresLayers && allowLayers) {
                anim.withLayer();
            }