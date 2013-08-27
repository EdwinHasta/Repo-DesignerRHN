package InterfacePersistencia;

import Entidades.Aficiones;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface PersistenciaAficionesInterface {
    
    public void crear(Aficiones aficiones);
    public void editar(Aficiones aficiones);
    public void borrar(Aficiones aficiones);
    public Aficiones buscarAficion(BigInteger id);
    public List<Aficiones> buscarAficiones();
    //public Aficiones buscarAf(BigDecimal cod);
    public List<Aficiones> buscarAf();
    public short maximoCodigoAficiones();
    public Aficiones buscarAficionCodigo(Short cod);
}
