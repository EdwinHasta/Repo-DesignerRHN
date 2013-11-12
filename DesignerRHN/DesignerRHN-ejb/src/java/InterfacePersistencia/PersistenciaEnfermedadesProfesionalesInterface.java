/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.EnfermeadadesProfesionales;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Viktor
 */
public interface PersistenciaEnfermedadesProfesionalesInterface {

    public void crear(EnfermeadadesProfesionales enfermedadesProfesionales);

    public void editar(EnfermeadadesProfesionales enfermedadesProfesionales);

    public void borrar(EnfermeadadesProfesionales enfermedadesProfesionales);

    public EnfermeadadesProfesionales buscarEnfermedadesProfesionales(BigInteger secuencia);

    public List<EnfermeadadesProfesionales> buscarEPPorEmpleado(BigInteger secEmpleado);
}
