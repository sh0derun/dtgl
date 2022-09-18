package dtgl.shader;

public class Uniform {

    private UniformType type;
    private String name;
    private Object value;

    public Uniform(){
        this.type = UniformType.NONE;
        this.name = "";
    }

    public Uniform(UniformType type, String name, Object value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public UniformType getType() {
        return type;
    }

    public void setType(UniformType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Uniform{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
