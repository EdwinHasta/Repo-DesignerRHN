/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VigenciasReformasLaborales;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface PersistenciaVigenciasReformasLaboralesInterface {
    
    /**
     * Crea un objeto VigenciaReformaLaboral
     * @param vigenciaRefLab Objeto a crear
     */
    public void crear(VigenciasReformasLaborales vigenciaRefLab);
    /**
     * Edita un objeto VigenciaReformaLaboral
     * @param vigenciaRefLab Objeto a editar
     */
    public void editar(VigenciasReformasLaborales vigenciaRefLab);
    /**
     * Edita un objeto VigenciaReformaLaboral
     * @param vigenciaRefLab Objeto a editar
     */
    public void borrar(VigenciasReformasLaborales vigenciaRefLab);
    /**
     * Metodo que obtiene una VigenciaReformaLaboral por medio de la llave primaria ID
     * @param id Llave Primaria ID
     * @return vigenciaRL VigenciaReformaLaboral que cumple con la llave primaria dada
     */
    public VigenciasReformasLaborales buscarVigenciaRefLab(Object id);
    /**
     * Metodo que obtiene todos los elementos de VigenciasReformasLaborales
     * @return listVRL Lista de VigenciasReformasLaborales
     */
    public List<VigenciasReformasLaborales> buscarVigenciasRefLab();
    /**
     * Metodo que obtiene las VigenciasReformasLaborales por medio de la secuencia del Empleado
     * @param secEmpleado Secuencia del Empleado
     * @return vigenciaRLE Lista de VigenciasReformasLaborales del Empleado
     */
    public List<VigenciasReformasLaborales> buscarVigenciasReformasLaboralesEmpleado(BigInteger secEmpleado);
    /**
     * Metodo que obtiene una VigenciaReformaLaboral por la secuencia
     * @param secVRL Secuencia de la VigenciaReformaLaboral
     * @return vigRL VigenciaReformaLaboral que cumple con la secuencia dada
     */
    public VigenciasReformasLaborales buscarVigenciaReformaLaboralSecuencia(BigInteger secVRL);
    
}
