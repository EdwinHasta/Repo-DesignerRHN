/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Instituciones;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaInstitucionesInterface {
    public List<Instituciones> instituciones();
    public void crear(Instituciones instituciones);
    public void editar(Instituciones instituciones);
    public void borrar(Instituciones instituciones);
    public Instituciones buscarInstitucion(BigInteger id);

}
