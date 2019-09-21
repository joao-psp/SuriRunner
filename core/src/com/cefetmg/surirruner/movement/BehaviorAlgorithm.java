package com.cefetmg.surirruner.movement;

public abstract class BehaviorAlgorithm {

    protected float maxVelocidade;
    public Target alvo;
    private final char nome;

    public BehaviorAlgorithm(char nome) {
        this.nome = nome;
    }

    public abstract Direcionamento guide(Pose agente);

}
