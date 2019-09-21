package com.cefetmg.surirruner.movement;

import com.badlogic.gdx.math.Vector3;

public class Direcionamento {
    public Vector3 speed;  // velocidade linear
    public double rotation;      // velocidade angular
    
    public Direcionamento() {
        speed = new Vector3();
        rotation = 0;
    }
}
