
public class SquareModel extends Model {
	
	// put vertices in clockwise order
	public SquareModel(float[] p, float[] c) {
		if (p.length != 12 || c.length != 12) throw new IllegalArgumentException();
		
		positions = new Triple[6];
		colors = new Triple[6];
		
		positions[0] = new Triple(p[0], p[1], p[2]);
		positions[1] = new Triple(p[3], p[4], p[5]);
		positions[2] = new Triple(p[6], p[7], p[8]);
		positions[3] = new Triple(p[6], p[7], p[8]);
		positions[4] = new Triple(p[9], p[10], p[11]);
		positions[5] = new Triple(p[0], p[1], p[2]);
		
		colors[0] = new Triple(c[0], c[1], c[2]);
		colors[1] = new Triple(c[3], c[4], c[5]);
		colors[2] = new Triple(c[6], c[7], c[8]);
		colors[3] = new Triple(c[6], c[7], c[8]);
		colors[4] = new Triple(c[9], c[10], c[11]);
		colors[5] = new Triple(c[0], c[1], c[2]);
	}
	
	public SquareModel(Triple[] pos, Triple[] col) {
		if (pos.length != 4 || col.length != 4) throw new IllegalArgumentException();
		
		positions = new Triple[6];
		colors = new Triple[6];
		
		positions[0] = pos[0];
		positions[1] = pos[1];
		positions[2] = pos[2];
		positions[3] = pos[2];
		positions[4] = pos[3];
		positions[5] = pos[0];
		
		colors[0] = col[0];
		colors[1] = col[1];
		colors[2] = col[2];
		colors[3] = col[2];
		colors[4] = col[3];
		colors[5] = col[0];
	}
	
	public SquareModel(float x, float y, float z, float width, float r, float g, float b) {
		positions = new Triple[6];
		colors = new Triple[6];
		
		positions[0] = new Triple(x - width/2, y - width/2, z);
		positions[1] = new Triple(x + width/2, y - width/2, z);
		positions[2] = new Triple(x + width/2, y + width/2, z);
		positions[3] = new Triple(x + width/2, y + width/2, z);
		positions[4] = new Triple(x - width/2, y + width/2, z);
		positions[5] = new Triple(x - width/2, y - width/2, z);
		
		Triple color = new Triple(r, g, b);
		for (int i = 0; i < colors.length; i++) colors[i] = color;
	}
	
	public SquareModel(Triple position, float width, Triple color) {
		positions = new Triple[6];
		colors = new Triple[6];
		
		float x = position.get(0);
		float y = position.get(1);
		float z = position.get(2);
		
		positions[0] = new Triple(x - width/2, y - width/2, z);
		positions[1] = new Triple(x + width/2, y - width/2, z);
		positions[2] = new Triple(x + width/2, y + width/2, z);
		positions[3] = new Triple(x + width/2, y + width/2, z);
		positions[4] = new Triple(x - width/2, y + width/2, z);
		positions[5] = new Triple(x - width/2, y - width/2, z);
		
		for (int i = 0; i < colors.length; i++) colors[i] = color;
	}
	
	public SquareModel(TriangleModel t1, TriangleModel t2) {
		float[] p = new float[12];
		float[] c = new float[12];
		
		for (int i = 0; i < p.length; i++)
			p[i] = t1.getVertices()[i];
		for (int i = 0; i < p.length; i++)
			p[i] = t2.getVertices()[i];
		
		for (int i = 0; i < c.length; i++)
			c[i] = t1.getColors()[i];
		for (int i = 0; i < c.length; i++)
			c[i] = t2.getColors()[i];
		
		positions = Triple.arrayToTriples(p);
		colors = Triple.arrayToTriples(c);
	}
	
	public String toString() {
		String output = "Square\n" +
						"Vertices: ";
		output += positions[0] + ", " + positions[1] + ", " + positions[2] + ", " + positions[3] + "\n";
		
		output += "Colors: ";
		output += colors[0] + ", " + colors[1] + ", " + colors[2] + ", " + colors[3];
		
		return output;	
	}

}
