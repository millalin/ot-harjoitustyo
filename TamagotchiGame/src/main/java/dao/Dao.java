/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Tamagotchi;
import java.sql.SQLException;

/**
 * Tietokantahallintaluokkien perusrakenteen määrittävä rajapinta.
 *
 * @param <T> T
 * @param <K> K
 */
public interface Dao<T, K> {

    Tamagotchi create(T tamagotchi) throws SQLException;

    Tamagotchi update(T tamagotchi) throws SQLException;

}
