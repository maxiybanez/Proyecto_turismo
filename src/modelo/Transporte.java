
package modelo;

import java.util.Objects;


public class Transporte {

    int idTransporte;
    Destino destino;
    String tipoDeTasporte;
    float costo;
    boolean activo;

    public Transporte() {
    }

    public Transporte(Destino destino, String tipoDeTasporte, float costo, boolean activo) {
        this.destino = destino;
        this.tipoDeTasporte = tipoDeTasporte;
        this.costo = costo;
        this.activo = activo;
    }

    public Transporte(int idTransporte, Destino destino, String tipoDeTasporte, float costo, boolean activo) {
        this.idTransporte = idTransporte;
        this.destino = destino;
        this.tipoDeTasporte = tipoDeTasporte;
        this.costo = costo;
        this.activo = activo;
    }

    public int getIdTransporte() {
        return idTransporte;
    }

    public void setIdTransporte(int idTransporte) {
        this.idTransporte = idTransporte;
    }

    public Destino getDestino() {
        return destino;
    }

    public void setDestino(Destino destino) {
        this.destino = destino;
    }

    public String getTipoDeTasporte() {
        return tipoDeTasporte;
    }

    public void setTipoDeTasporte(String tipoDeTasporte) {
        this.tipoDeTasporte = tipoDeTasporte;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + this.idTransporte;
        hash = 41 * hash + Objects.hashCode(this.destino);
        hash = 41 * hash + Objects.hashCode(this.tipoDeTasporte);
        hash = 41 * hash + Float.floatToIntBits(this.costo);
        hash = 41 * hash + (this.activo ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Transporte other = (Transporte) obj;
        if (this.idTransporte != other.idTransporte) {
            return false;
        }
        if (Float.floatToIntBits(this.costo) != Float.floatToIntBits(other.costo)) {
            return false;
        }
        if (this.activo != other.activo) {
            return false;
        }
        if (!Objects.equals(this.tipoDeTasporte, other.tipoDeTasporte)) {
            return false;
        }
        if (!Objects.equals(this.destino, other.destino)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Transporte{" + "idTransporte=" + idTransporte + ", destino=" + destino + ", tipoDeTasporte=" + tipoDeTasporte + ", costo=" + costo + ", activo=" + activo + '}';
    }
    
    
    
    
}