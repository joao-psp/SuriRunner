/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cefetmg.surirruner.movement.behavior;

import com.cefetmg.surirruner.movement.AlgoritmoMovimentacao;
import com.cefetmg.surirruner.movement.Direcionamento;
import com.cefetmg.surirruner.movement.Pose;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector3;

/**
 *
 * @author aluno
 */
public class Chegar extends AlgoritmoMovimentacao {

    private static final char NOME = 'a';

    public Chegar(float maxVelocidade) {
        this(NOME, maxVelocidade);
    }

    protected Chegar(char nome, float maxVelocidade) {
        super(nome);
        this.maxVelocidade = maxVelocidade;
    }

    @Override
    public Direcionamento guiar(Pose agente) {
        Direcionamento output = new Direcionamento();
        
        Vector3 objetivo = super.alvo.getObjetivo();
        
        Vector3 velocity = new Vector3(objetivo.x - agente.posicao.x,
                            objetivo.y - agente.posicao.y,
                            objetivo.z - agente.posicao.z);
        
        if (velocity.len() < 0) {
            output.velocidade = new Vector3(0,0,0);
            return output;
        }
        
        velocity.scl((float) 3);
        
        if (velocity.len() > maxVelocidade) {
            velocity.nor().scl(maxVelocidade);
        }
        
        output.velocidade = velocity;
        agente.olharNaDirecaoDaVelocidade(velocity);
        
        

        // calcula que direção tomar (configura um objeto Direcionamento 
        // e o retorna)
        // ...
        // super.alvo já contém a posição do alvo
        // agente (parâmetro) é a pose do agente que estamos guiando
        // ...
        return output;
    }

    @Override
    public int getTeclaParaAtivacao() {
        return Keys.A;
    }
    
}
