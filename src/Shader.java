
public class Shader {
	
	private String type;
	private String shaderCode;
	
	public Shader(String sType, String code) {
		type = sType;
		shaderCode = code;
	}
	
	public String getType() {
		return type;
	}
	
	public String getCode() {
		return shaderCode;
	}
}
