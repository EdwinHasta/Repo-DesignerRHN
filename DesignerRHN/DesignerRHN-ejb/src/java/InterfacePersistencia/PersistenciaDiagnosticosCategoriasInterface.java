/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Diagnosticoscategorias;
import java.util.List;

/**
 *
 * @author Viktor
 */
public interface PersistenciaDiagnosticosCategoriasInterface {

    public void crear(Diagnosticoscategorias diagnosticosCategorias);

    public void editar(Diagnosticoscategorias diagnosticosCategorias);

    public void borrar(Diagnosticoscategorias diagnosticosCategorias);

    public List<Diagnosticoscategorias> buscarDiagnosticos();

}
