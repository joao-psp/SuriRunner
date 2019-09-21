package com.cefetmg.surirruner.movement;

import com.badlogic.gdx.math.Vector3;

public class Target {

    private Vector3 objetivoEstatico;

    private Agent agenteObjetivo;

    public Target(Vector3 posicao) {
        this.objetivoEstatico = posicao;
    }

    public void setObjetivo(Vector3 posicao) {
        this.objetivoEstatico = posicao;
        this.agenteObjetivo = null;
    }

    public void setObjetivo(Agent agente) {
        this.agenteObjetivo = agente;
        this.objetivoEstatico = null;
    }

    public boolean isSeguindoObjetivo() {
        return agenteObjetivo != null;
    }

    public Vector3 getObjetivo() {
        return isSeguindoObjetivo()
                ? agenteObjetivo.pose.position : objetivoEstatico;
    }
}
