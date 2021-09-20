package cz.istep.javatest.dto;

public class Framework {

    private Long id;
    private String name;
    private String version;
    private int hypeLevel;

    public Framework(Long id, String name, String version, int hypeLevel) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.hypeLevel = hypeLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getHypeLevel() {
        return hypeLevel;
    }

    public void setHypeLevel(int hypeLevel) {
        this.hypeLevel = hypeLevel;
    }

    @Override
    public String toString() {
        return "creatingFrameworkDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", hypeLevel=" + hypeLevel +
                '}';
    }
}
