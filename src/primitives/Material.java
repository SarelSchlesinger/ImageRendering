package primitives;

public class Material {

    /**
     * @member Kd - Diffuse component
     * @member Ks - Specular component
     * @member Kr - Reflection component
     * @member Kt - Transparency component
     * @member Shininess - material's shininess level
     */
    private Double3 Kd = Double3.ZERO;
    private Double3 Ks = Double3.ZERO;
    private Double3 Kr = Double3.ZERO;
    private Double3 Kt = Double3.ZERO;
    private int Shininess = 0;

    public Material setKd(double kd) {
        this.Kd = new Double3(kd);
        return this;
    }

    public Material setKd(Double3 kd) {
        this.Kd = kd;
        return this;
    }

    public Double3 getKd() {
        return this.Kd;
    }

    public Material setKs(double ks) {
        this.Ks = new Double3(ks);
        return this;
    }

    public Material setKs(Double3 ks) {
        this.Ks = ks;
        return this;
    }

    public Double3 getKs() {
        return this.Ks;
    }

    public Material setKr(double kr) {
        this.Kr = new Double3(kr);
        return this;
    }

    public Material setKr(Double3 kr) {
        this.Kr = kr;
        return this;
    }

    public Double3 getKr() {
        return this.Kr;
    }

    public Material setKt(double kt) {
        this.Kt = new Double3(kt);
        return this;
    }

    public Material setKt(Double3 kt) {
        this.Kt = kt;
        return this;
    }

    public Double3 getKt() {
        return this.Kt;
    }

    public Material setShininess(int Shininess) {
        this.Shininess = Shininess;
        return this;
    }

    public int getShininess() {
        return this.Shininess;
    }
}
