
public class Triple {
	
	private final float a, b, c;
	
	public static final int length = 3;
	
	public Triple(float a, float b, float c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	public float get(int index) {
		if (index == 0) return a;
		else if (index == 1) return b;
		else if (index == 2) return c;
		else throw new IndexOutOfBoundsException();
	}
	
	public static Triple add(Triple a, Triple b) {
		return new Triple(a.a + b.a, a.b + b.a, a.c + b.c);
	}
	
	public static Triple subtract(Triple a, Triple b) {
		return new Triple(a.a - b.a, a.b - b.a, a.c - b.c);
	}
	
	public static float[] triplesToArray(Triple[] x) {
		float[] fArray = new float[x.length * Triple.length];
		
		for (int i = 0; i < x.length; i++)
			for (int j = 0; j < Triple.length; j++)
				fArray[i*Triple.length + j] = x[i].get(j);
		
		return fArray;
	}
	
	public static Triple[] arrayToTriples(float[] f) {
		if (f.length % 3 != 0) throw new IllegalArgumentException();
		Triple[] t = new Triple[f.length / 3];
		
		for (int i = 0; i < f.length; i += 3)
			t[i / 3] = new Triple(f[i], f[i + 1], f[i + 2]);
		
		return t;
	}
	
	public Triple add(float f) {
		return new Triple(a + f, b + f, c + f);
	}
	
	public String toString() {
		String output = "(";
		
		output += a;
		output += ", " + b;
		output += ", " + c + ")";
		
		return output;
	}

}
