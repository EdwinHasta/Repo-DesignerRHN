/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.PryPlataformas;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaPryPlataformasInterface {

    public void crear(PryPlataformas plataformas);

    public void editar(PryPlataformas plataformas);

    public void borrar(PryPlataformas plataformas);

    public PryPlataformas buscarPryPlataforma(Object id);

    public List<PryPlataformas> buscarPryPlataformas();

    public PryPlataformas buscarPryPlataformaSecuencia(BigInteger secuencia);
}
