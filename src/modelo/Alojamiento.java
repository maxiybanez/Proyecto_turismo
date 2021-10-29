
package modelo;

import java.util.Objects;

public class Alojamiento {
   
   int idAlojamiento;
   Destino destino;
   ExtraAlojamiento extraAloj;
   String tipoAlojamiento;
   float costo;
   String nombre;
   boolean activo;

    public Alojamiento() {
    }

    public Alojamiento(Destino destino, ExtraAlojamiento extraAloj, String tipoAlojamiento, float costo, String nombre, boolean activo) {
        this.destino = destino;
        this.extraAloj = extraAloj;
        this.tipoAlojamiento = tipoAlojamiento;
        this.costo = costo;
        this.nombre = nombre;
        this.activo = activo;
    }

    public Alojamiento(int idAlojamiento, Destino destino, ExtraAlojamiento extraAloj, String tipoAlojamiento, float costo, String nombre, boolean activo) {
        this.idAlojamiento = idAlojamiento;
        this.destino = destino;
        this.extraAloj = extraAloj;
        this.tipoAlojamiento = tipoAlojamiento;
        this.costo = costo;
        this.nombre = nombre;
        this.activo = activo;
    }

    public int getIdAlojamiento() {
        return idAlojamiento;
    }

    public void setIdAlojamiento(int idAlojamiento) {
        this.idAlojamiento = idAlojamiento;
    }

    public Destino getDestino() {
        return destino;
    }

    public void setDestino(Destino destino) {
        this.destino = destino;
    }

    public ExtraAlojamiento getExtraAloj() {
        return extraAloj;
    }

    public void setExtraAloj(ExtraAlojamiento extraAloj) {
        this.extraAloj = extraAloj;
    }

    public String getTipoAlojamiento() {
        return tipoAlojamiento;
    }

    public void setTipoAlojamiento(String tipoAlojamiento) {
        this.tipoAlojamiento = tipoAlojamiento;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.idAlojamiento;
        hash = 17 * hash + Objects.hashCode(this.destino);
        hash = 17 * hash + Objects.hashCode(this.extraAloj);
        hash = 17 * hash + Objects.hashCode(this.tipoAlojamiento);
        hash = 17 * hash + Float.floatToIntBits(this.costo);
        hash = 17 * hash + Objects.hashCode(this.nombre);
        hash = 17 * hash + (this.activo ? 1 : 0);
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
        final Alojamiento other = (Alojamiento) obj;
        if (this.idAlojamiento != other.idAlojamiento) {
            return false;
        }
        if (Float.floatToIntBits(this.costo) != Float.floatToIntBits(other.costo)) {
            return false;
        }
        if (this.activo != other.activo) {
            return false;
        }
        if (!Objects.equals(this.tipoAlojamiento, other.tipoAlojamiento)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.destino, other.destino)) {
            return false;
        }
        if (!Objects.equals(this.extraAloj, other.extraAloj)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Alojamiento{" + "idAlojamiento=" + idAlojamiento + ", destino=" + destino + ", extraAloj=" + extraAloj + ", tipoAlojamiento=" + tipoAlojamiento + ", costo=" + costo + ", nombre=" + nombre + ", activo=" + activo + '}';
    }
   
   
   
}