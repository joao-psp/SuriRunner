/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cefetmg.surirunner;

import com.badlogic.gdx.math.MathUtils;

/**
 *
 * @author joaop
 */
public enum ObstaclesEnum {
    ENEMIES, // se pegar perde ponto
    NOT_ENEMIES, // se pegar ganha pontos
    INVENCIBLE_UPGRADE,
    HEART;
    
    public static ObstaclesEnum getRandom() {
        int random = MathUtils.random(0, 10);
        switch(random) {
            case 0:
            case 1:
            case 10:
                return ENEMIES;
            case 2:
                return HEART;
            case 3:
            case 4:
            case 5:
                return NOT_ENEMIES;
            case 9:
                return INVENCIBLE_UPGRADE;
            default:
                return NOT_ENEMIES;
        }
    }
}
