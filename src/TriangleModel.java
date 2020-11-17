
public class TriangleModel extends Model {
	
	public TriangleModel(float[] p, float[] c) {
		super(p, c);
		if (p.length != 9 || c.length != 9) throw new IllegalArgumentException();
	}
	
	public TriangleModel(Triple[] pos, Triple[] color) {
		super(pos, color);
		if (pos.length != 3 || color.length != 3) throw new IllegalArgumentException();
	}
	
	public TriangleModel(Triple a, Triple b, Triple c, Triple d, Triple e, Triple f) {
		super(new Triple[] {a, b, c}, new Triple[] {d, e, f});
	}

}
