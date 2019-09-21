package com.cefetmg.surirruner.movement.behavior;

import com.cefetmg.surirruner.movement.BehaviorAlgorithm;
import com.cefetmg.surirruner.movement.Direcionamento;
import com.cefetmg.surirruner.movement.Pose;
import com.badlogic.gdx.math.Vector3;

public class Flee extends BehaviorAlgorithm {

    private static final char NOME = 'f';

    public Flee(float maxVelocidade) {
        super(NOME);
        this.maxVelocidade = maxVelocidade;
    }

    @Override
    public Direcionamento guide(Pose agente) {
        Direcionamento output = new Direcionamento();
        
        Vector3 objetivo = super.alvo.getObjetivo();
        
        Vector3 velocity = new Vector3(objetivo.x - agente.position.x,
                            objetivo.y - agente.position.y,
                            objetivo.z - agente.position.z).nor().scl(-maxVelocidade);
        
        output.speed = velocity;
        agente.olharNaDirecaoDaVelocidade(velocity);

        return output;
    }

}
