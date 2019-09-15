/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cefetmg.surirunner;

/**
 *
 * @author joaop
 */
public enum ObstaclesEnum {
    ENEMIES, // se pegar perde ponto
    NOT_ENEMIES, // se pegar ganha pontos
    MAGNETIC_UPDATE, // se pegar e apertar espaço ativa
    INVENCIBLE_UPDATE;
}
