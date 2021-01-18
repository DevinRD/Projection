
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
	
	public String toString() {
		String output = "Vertices: ";
		output += positions[0] + ", " + positions[1] + ", " + positions[2] + "\n";
		
		output += "Colors: ";
		output += colors[0] + ", " + colors[1] + ", " + colors[2];
		
		return output;	
	}

}
