package primitives;

/**
 * Wrapper class for java.jwt.Color The constructors operate with any
 * non-negative RGB values. The colors are maintained without upper limit of
 * 255. Some additional operations are added that are useful for manipulating
 * light's colors
 * 
 * @author Dan Zilberstein
 */
public class Color {
	/**
	 * The internal fields tx`o maintain RGB components as double numbers from 0 to
	 * whatever...
	 */
	private final Double3 rgb;

	/**
	 * Black color = (0,0,0)
	 */
	public static final Color BLACK = new Color();

	/**
	 * Default constructor - to generate Black Color (privately)
	 */
	private Color() {
		this.rgb = Double3.ZERO;
	}

	/**
	 * Constructor to generate a color according to RGB components Each component in
	 * range 0..255 (for printed white color) or more [for lights]
	 *
	 * @param r Red component
	 * @param g Green component
	 * @param b Blue component
	 */
	public Color(double r, double g, double b) {
		if (r < 0 || g < 0 || b < 0)
			throw new IllegalArgumentException("Negative color component is illegal");
		this.rgb = new Double3(r, g, b);
	}


	/**
	 * Constructor to generate a color according to RGB components Each component in
	 * range 0..255 (for printed white color) or more [for lights]
	 *
	 * @param rgb triad of Red/Green/Blue components 
	 */
	private Color(Double3 rgb) {
		if (rgb.getD1() < 0 || rgb.getD2() < 0 || rgb.getD3() < 0)
			throw new IllegalArgumentException("Negative color component is illegal");
		this.rgb = rgb;
	}

	/**
	 * Constructor on base of java.awt.Color object
	 * 
	 * @param other java.awt.Color's source object
	 */
	public Color(java.awt.Color other) {
		this.rgb = new Double3(other.getRed(), other.getGreen(), other.getBlue());
	}

	public Double3 getRgb() {
		return this.rgb;
	}

	/**
	 * Color getter - returns the color after converting it into java.awt.Color
	 * object During the conversion any component bigger than 255 is set to 255
	 *
	 * @return java.awt.Color object based on this Color RGB components
	 */
	public java.awt.Color getColor() {
		int ir = (int) this.getRgb().getD1();
		int ig = (int) this.getRgb().getD2();
		int ib = (int) this.getRgb().getD3();
		return new java.awt.Color(ir > 255 ? 255 : ir, ig > 255 ? 255 : ig, ib > 255 ? 255 : ib);
	}

	/**
	 * Operation of adding this and one or more other colors (by component)
	 *
	 * @param colors one or more other colors to add
	 * @return new Color object which is a result of the operation
	 */
	public Color add(Color... colors) {
		double rr = this.getRgb().getD1();
		double rg = this.getRgb().getD2();
		double rb = this.getRgb().getD3();
		for (Color c : colors) {
			rr += c.getRgb().getD1();
			rg += c.getRgb().getD2();
			rb += c.getRgb().getD3();
		}
		return new Color(rr, rg, rb);
	}

	/**
	 * Scale the color by a scalar triad per rgb
	 *
	 * @param k scale factor per rgb
	 * @return new Color object which is the result of the operation
	 */
	public Color scale(Double3 k) {
		if (k.getD1() < 0.0 || k.getD2() < 0.0 || k.getD3() < 0.0)
			throw new IllegalArgumentException("Can't scale a color by a negative number");
		return new Color(this.getRgb().product(k));
	}
	
	/**
	 * Scale the color by a scalar
	 *
	 * @param k scale factor
	 * @return new Color object which is the result of the operation
	 */
	public Color scale(double k) {
		if (k < 0.0)
			throw new IllegalArgumentException("Can't scale a color by a negative number");
		return new Color(this.getRgb().scale(k));
	}

	/**
	 * Scale the color by (1 / reduction factor)
	 * 
	 * @param k reduction factor
	 * @return new Color object which is the result of the operation
	 */
	public Color reduce(double k) {
		if (k < 1)
			throw new IllegalArgumentException("Can't scale a color by a by a number lower than 1");
		return new Color(this.getRgb().reduce(k));
	}

	/**
	 * Scale the color by (1 / reduction factor)
	 * 
	 * @param k reduction factor
	 * @return new Color object which is the result of the operation
	 */
	public Color reduce(Double3 k) {
		if (k.getD1() < 1.0 || k.getD2() < 1.0 || k.getD3() < 1.0)
			throw new IllegalArgumentException("Can't scale a color by a by a number lower than 1");
		return new Color(this.getRgb().getD1() / k.getD1(),
						 this.getRgb().getD2() / k.getD2(),
						 this.getRgb().getD3() / k.getD3());
	}

	@Override
	public String toString() {
		return "rgb:" + this.getRgb();
	}
}
