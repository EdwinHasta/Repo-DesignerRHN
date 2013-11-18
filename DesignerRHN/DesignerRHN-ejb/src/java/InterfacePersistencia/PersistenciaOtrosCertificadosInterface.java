/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.OtrosCertificados;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaOtrosCertificadosInterface {

    public void crear(OtrosCertificados certificados);

    public void editar(OtrosCertificados certificados);

    public void borrar(OtrosCertificados certificados);

    public OtrosCertificados buscarOtroCertificado(Object id);

    public List<OtrosCertificados> buscarOtrosCertificados();

    public OtrosCertificados buscarOtroCertificadoSecuencia(BigInteger secuencia);

    public List<OtrosCertificados> buscarOtrosCertificadosEmpleado(BigInteger secuencia);
}
