package geometries;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public abstract class Geometry extends Intersectable {

     protected Color emission;

     protected Geometry(Color emission) {
          this.emission = emission;
     }

     protected Geometry() {
          this.emission = Color.BLACK;
     }

     public Color getEmission() {
          return emission;
     }

     public Geometry setEmission(Color emission) {
          this.emission = emission;
          return this;
     }

     public abstract Vector getNormal(Point point);

}
