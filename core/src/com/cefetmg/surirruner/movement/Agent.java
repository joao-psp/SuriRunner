package com.cefetmg.surirruner.movement;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

/**
 * É uma entidade do jogo que se movimenta com algum comportamento.
 * @author Flávio Coutinho <fegemo@cefetmg.br>
 */
public class Agent {

    public Pose pose;
    private BehaviorAlgorithm comportamento;

    public Color cor;

    public Agent(Vector3 posicao, Color cor) {
        this.pose = new Pose(posicao, 0);
        this.cor = cor;
    }

    public void atualiza(float delta) {
        if (comportamento != null) {
            // pergunta ao algoritmo de movimento (comportamento) 
            // para onde devemos ir
            Direcionamento direcionamento = comportamento.guide(this.pose);

            // faz a simulação física usando novo estado da entidade cinemática
            pose.update(direcionamento, delta);
        }
    }

    public void defineComportamento(BehaviorAlgorithm comportamento) {
        this.comportamento = comportamento;
    }
}
