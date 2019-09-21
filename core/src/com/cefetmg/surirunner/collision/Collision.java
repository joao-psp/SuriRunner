package com.cefetmg.surirunner.collision;

import com.badlogic.gdx.math.Rectangle;

public class Collision {
    public static final boolean rectsOverlap(Rectangle r1, Rectangle r2) {
        boolean exp1 = (r1.x <= (r2.x + r2.width)) && ((r1.x + r1.width) >= r2.x);
        boolean exp2 =  (r1.y <= (r2.y + r2.height)) && ((r1.y + r1.height) >= r2.y);
        return exp1 && exp2;
    }
}
