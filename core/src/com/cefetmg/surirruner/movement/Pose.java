package com.cefetmg.surirruner.movement;

import com.badlogic.gdx.math.Vector3;

public class Pose {

    public Vector3 position;
    public float orientation;

    public Pose() {
        this(new Vector3(0, 0, 0), 0);
    }

    public Pose(Vector3 position) {
        this(position, 0);
    }

    public Pose(Vector3 posicao, float orientacao) {
        this.position = posicao;
        this.orientation = orientacao;
    }

    public void olharNaDirecaoDaVelocidade(Vector3 velocidade) {
        if (velocidade.len2() > 0) {
            orientation = (float) Math.atan2(velocidade.y, velocidade.x);
        }
    }

    public void update(Direcionamento guia, float delta) {
        position.add(guia.speed.scl(delta));
        orientation += guia.rotation * delta;
        orientation = orientation % ((float) Math.PI * 2);
    }
}
