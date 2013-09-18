/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Bancos;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaBancosInterface {
    
    public void crear(Bancos bancos);
    public void editar(Bancos bancos);
    public void borrar(Bancos bancos);
    public Bancos buscarBanco(Object id);
    public List<Bancos> buscarBancos();
    
}
