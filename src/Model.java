
public abstract class Model {
	
	protected Triple[] positions;
	protected Triple[] colors;
	
	public Model() {
		
	}
	
	public Model(float[] position, float[] color) {
		positions = Triple.arrayToTriples(position);
		colors = Triple.arrayToTriples(color);
	}
	
	public Model(Triple[] position, Triple[] color) {
		positions = position;
		colors = color;
	}
	
	public float[] getVertices() {
		return Triple.triplesToArray(positions);
	}
	
	public float[] getColors() {
		return Triple.triplesToArray(colors);
	}
	
	public Triple[] getTVertices() {
		return positions;
	}
	
	public Triple[] getTColors() {
		return colors;
	}

}
