package com.cefetmg.surirunner.collision;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Utilitário para verificação de colisão.
 *
 * @author fegemo <coutinho@decom.cefetmg.br>
 */
public class Collision {

    /**
     * Verifica se dois círculos em 2D estão colidindo.
     * @param c1 círculo 1
     * @param c2 círculo 2
     * @return true se há colisão ou false, do contrário.
     */
    public static final boolean circlesOverlap(Circle c1, Circle c2) {
        Vector2 distance = new Vector2(c1.x - c2.x, c1.y - c2.y );
        float distanceX2 = distance.x*distance.x;
        float distanceY2 = distance.y*distance.y;
        float radius2 = (c1.radius + c2.radius)*(c1.radius + c2.radius);
        return (distanceX2 + distanceY2) <= radius2;
    }

    /**
     * Verifica se dois retângulos em 2D estão colidindo.
     * Esta função pode verificar se o eixo X dos dois objetos está colidindo
     * e então se o mesmo ocorre com o eixo Y.
     * @param r1 retângulo 1
     * @param r2 retângulo 2
     * @return true se há colisão ou false, do contrário.
     */
    public static final boolean rectsOverlap(Rectangle r1, Rectangle r2) {
        boolean exp1 = (r1.x <= (r2.x + r2.width)) && ((r1.x + r1.width) >= r2.x);
        boolean exp2 =  (r1.y <= (r2.y + r2.height)) && ((r1.y + r1.height) >= r2.y);
        return exp1 && exp2;
    }
    
    public static final boolean recCircOverlap (Rectangle r1, Circle c1) {
        Vector2 vecD = new Vector2 (c1.x - (r1.x - r1.width/2), c1.y - (r1.y - r1.height/2));
        vecD = vecD.clamp(r1.width/2, r1.height/2);
        
        Vector2 point = new Vector2(r1.x + vecD.x + r1.width/2, r1.y + vecD.y + r1.height/2);
        Vector2 distancePointCircle = new Vector2(point.x - c1.x, point.y - c1.y);
        
        return distancePointCircle.x*distancePointCircle.x + distancePointCircle.y*distancePointCircle.y <= c1.radius*c1.radius;
    }
}
