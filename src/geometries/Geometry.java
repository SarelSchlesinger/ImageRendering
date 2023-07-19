package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

public abstract class Geometry extends Intersectable {

    protected Color emission;
    private Material material = new Material();

    protected Geometry(Color emission) {
        this.emission = emission;
    }

    protected Geometry() {
        this.emission = Color.BLACK;
    }

    public Color getEmission() {
        return this.emission;
    }

    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    public abstract Vector getNormal(Point point);

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public Material getMaterial() {
        return this.material;
    }
}
