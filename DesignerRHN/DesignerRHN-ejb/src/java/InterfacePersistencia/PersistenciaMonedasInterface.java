/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Monedas;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Viktor
 */
public interface PersistenciaMonedasInterface {
    
    public Monedas buscarMonedaSecuencia(BigInteger secuencia);
    public List<Monedas> listMonedas();
    
}
