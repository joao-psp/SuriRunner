/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cefetmg.surirruner.movement.behavior;

import com.cefetmg.surirruner.movement.BehaviorAlgorithm;
import com.cefetmg.surirruner.movement.Direcionamento;
import com.cefetmg.surirruner.movement.Pose;
import com.badlogic.gdx.math.Vector3;

/**
 *
 * @author aluno
 */
public class Arrive extends BehaviorAlgorithm {

    private static final char NOME = 'a';

    public Arrive(float maxVelocidade) {
        this(NOME, maxVelocidade);
    }

    protected Arrive(char nome, float maxVelocidade) {
        super(nome);
        this.maxVelocidade = maxVelocidade;
    }

    @Override
    public Direcionamento guide(Pose agente) {
        Direcionamento output = new Direcionamento();
        
        Vector3 objetivo = super.alvo.getObjetivo();
        
        Vector3 velocity = new Vector3(objetivo.x - agente.position.x,
                            objetivo.y - agente.position.y,
                            objetivo.z - agente.position.z);
        
        if (velocity.len() < 0) {
            output.speed = new Vector3(0,0,0);
            return output;
        }
        
        velocity.scl((float) 3);
        
        if (velocity.len() > maxVelocidade) {
            velocity.nor().scl(maxVelocidade);
        }
        
        output.speed = velocity;
        agente.olharNaDirecaoDaVelocidade(velocity);

        return output;
    }
    
}
