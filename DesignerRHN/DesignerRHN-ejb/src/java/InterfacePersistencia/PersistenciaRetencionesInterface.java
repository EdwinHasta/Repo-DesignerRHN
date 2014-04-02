/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Retenciones;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaRetencionesInterface {

    public void crear(Retenciones retenciones);

    public void editar(Retenciones retenciones);

    public void borrar(Retenciones retenciones);

    public List<Retenciones> buscarRetenciones();

    public List<Retenciones> buscarRetencionesVig(BigInteger secRetencion);
}
