package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "PARAMETROSESTRUCTURAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParametroEestructuras.findAll", query = "SELECT p FROM ParametrosEstructuras p")})
public class ParametrosEstructuras implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Column(name = "CODIGO")
    private Short codigo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHADESDECAUSADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechadesdecausado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAHASTACAUSADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahastacausado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHASISTEMA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechasistema;
    @JoinColumn(name = "USUARIO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Usuarios usuario;
    @JoinColumn(name = "TIPOTRABAJADOR", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private TiposTrabajadores tipotrabajador;
    @JoinColumn(name = "PROCESO", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private Procesos proceso;
    @JoinColumn(name = "ESTRUCTURA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Estructuras estructura;

    public ParametrosEstructuras() {
    }

    public ParametrosEstructuras(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public ParametrosEstructuras(BigDecimal secuencia, Date fechadesdecausado, Date fechahastacausado, Date fechasistema, BigInteger usuario) {
        this.secuencia = secuencia;
        this.fechadesdecausado = fechadesdecausado;
        this.fechahastacausado = fechahastacausado;
        this.fechasistema = fechasistema;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    public Date getFechadesdecausado() {
        return fechadesdecausado;
    }

    public void setFechadesdecausado(Date fechadesdecausado) {
        this.fechadesdecausado = fechadesdecausado;
    }

    public Date getFechahastacausado() {
        return fechahastacausado;
    }

    public void setFechahastacausado(Date fechahastacausado) {
        this.fechahastacausado = fechahastacausado;
    }

    public Date getFechasistema() {
        return fechasistema;
    }

    public void setFechasistema(Date fechasistema) {
        this.fechasistema = fechasistema;
    }

    public TiposTrabajadores getTipotrabajador() {
        return tipotrabajador;
    }

    public void setTipotrabajador(TiposTrabajadores tipotrabajador) {
        this.tipotrabajador = tipotrabajador;
    }

    public Procesos getProceso() {
        return proceso;
    }

    public void setProceso(Procesos proceso) {
        this.proceso = proceso;
    }

    public Estructuras getEstructura() {
        return estructura;
    }

    public void setEstructura(Estructuras estructura) {
        this.estructura = estructura;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (secuencia != null ? secuencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParametrosEstructuras)) {
            return false;
        }
        ParametrosEstructuras other = (ParametrosEstructuras) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Parametrosestructuras[ secuencia=" + secuencia + " ]";
    }
    
}
